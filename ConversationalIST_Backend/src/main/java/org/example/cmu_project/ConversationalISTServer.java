package org.example.cmu_project;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.backendserver.*;
import io.grpc.stub.StreamObserver;
import org.example.cmu_project.helpers.ChatroomFileHelper;
import org.example.cmu_project.helpers.GeneralHelper;
import org.example.cmu_project.helpers.UserFileHelper;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ConversationalISTServer {

    private static final Logger logger = Logger.getLogger(ConversationalISTServer.class.getName());
    private static final int TIMEOUT = 30;
    private static final int PORT_NUM = 50051;

    //hash map where key is the chatroom name and value is a list with the StreamObserver of all the interested clients
    private static HashMap<String, List<StreamObserver<messageResponse>>> clientSubscriptions = new HashMap<>();

    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        server = ServerBuilder.forPort(PORT_NUM)
                .addService(new ServerImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + PORT_NUM);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                ConversationalISTServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(TIMEOUT, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final ConversationalISTServer conversationalISTServer = new ConversationalISTServer();
        conversationalISTServer.start();
        conversationalISTServer.blockUntilShutdown();
    }

    static class ServerImpl extends ServerGrpc.ServerImplBase {

        ChatroomFileHelper chatroomFileHelper = new ChatroomFileHelper();
        UserFileHelper userFileHelper = new UserFileHelper();
        GeneralHelper generalHelper = new GeneralHelper();

        @Override
        public void sendMessage(sendingMessage req, StreamObserver<messageResponse> responseObserver) {
            logger.info("Got request from client: " + req);

            String timestamp = String.valueOf(Timestamp.from(Instant.now()));
            String data = req.getData();
            String username = req.getUsername();
            String chatroom = req.getChatroom();
            String type = String.valueOf(req.getType());

            int position = chatroomFileHelper.readFile(chatroom).size() + 1;

            chatroomFileHelper.writeToFile(data, username, timestamp, type, chatroom, String.valueOf(position));

            //TODO send ack instead of message. Message is sent for for the service that treats it after
            messageResponse reply = messageResponse.newBuilder()
                    .setData(data)
                    .setTimestamp(timestamp)
                    .setUsername(username)
                    .setChatroom(chatroom)
                    .setType(Integer.parseInt(type))
                    .setPosition(position)
                    .build();

            sendMessageToInterestedClients(reply, chatroom);

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void getAllChatMessages(chatMessageRequest req, StreamObserver<messageResponse> responseObserver){
            logger.info("Got request from client: " + req);

            String chatroom = req.getChatroom();

            List<String> messages = chatroomFileHelper.readFile(chatroom);

            sendMessageStreamToClient(responseObserver, messages);
        }

        @Override
        public void getChatMessagesSincePosition(chatMessageFromPosition req, StreamObserver<messageResponse> responseObserver){
            logger.info("Got request from client: " + req);

            int position = req.getPositionOfLastMessage();
            String chatroom = req.getChatroom();

            List<String> messages = chatroomFileHelper.readFile(chatroom);

            List<String> remainMessages = messages.subList(position, messages.size());

            sendMessageStreamToClient(responseObserver, remainMessages);

        }

        @Override
        public void createChat(CreateChatRequest request, StreamObserver<CreateChatReply> responseObserver) {

            String chatName = request.getChatroomName();
            String user_name = request.getUser();
            String type_of_chat = request.getTypeOfChat();

            if(generalHelper.userExists(user_name)) {
                if(!generalHelper.chatAlreadyExists(chatName)) {
                    userFileHelper.store(chatName,user_name);
                    chatroomFileHelper.store("",chatroomFileHelper.CHATROOM_FILE_BEGIN+chatName);
                    if(type_of_chat.equals("GeoFanced")) {
                        chatroomFileHelper.store(chatName+","+user_name+ "," + type_of_chat + "," + request.getLocation().getLatitude()+"/"+request.getLocation().getLongitude() + "," + request.getRadius(),chatroomFileHelper.CHATROOM_FILE_INFO);
                    } else if (type_of_chat.equals("Private")) {
                        //TO-DO (create link to the chat)
                        chatroomFileHelper.store(chatName+","+user_name+ "," + type_of_chat,chatroomFileHelper.CHATROOM_FILE_INFO);
                    } else {
                        chatroomFileHelper.store(chatName+","+user_name+ "," + type_of_chat,chatroomFileHelper.CHATROOM_FILE_INFO);
                    }
                }
            }

            CreateChatReply response = CreateChatReply.newBuilder().setAck("OK").build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }

        @Override
        public void getJoinableChats(JoinableChatsRequest request, StreamObserver<JoinableChatsReply> responseObserver) {

            String user = request.getUser();
            List<String> user_chats = userFileHelper.getChats(user);
            List<String> chats_info = chatroomFileHelper.readInfoFile();
            List<String> chats_available = new ArrayList<>();

            for (String line: chats_info) {

                String[] split_line = line.split(",");
                String chat_name = split_line[0];

                if(!user_chats.contains(chat_name)) {
                    chats_available.add(chat_name);
                }

                String type_of_chat = split_line[2];
            }


            JoinableChatsReply response = JoinableChatsReply.newBuilder().addAllChats(chats_available).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();



        }

        @Override
        public void joinChat(JoinChatRequest request,StreamObserver<JoinChatReply> responseObserver) {

            String user = request.getUser();
            String chat_name = request.getChatName();

            userFileHelper.store(chat_name,user);

            JoinChatReply response = JoinChatReply.newBuilder().setAck("OK").build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }

        @Override
        public void getAllChats(GetChatsRequest request,StreamObserver<GetChatsReply> responseObserver) {

            System.out.println("Recebi pedido");

            String user = request.getUser();

            List<String> user_chats = userFileHelper.getChats(user);
            for (int i = 0;i<user_chats.size();i++) {
                System.out.println(user_chats.get(i));
            }

            GetChatsReply response = GetChatsReply.newBuilder().addAllUserChats(user_chats).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }

        @Override
        public void getLastNMessagesFromChat(NMessagesFromChat req, StreamObserver<messageResponse> responseObserver){
            logger.info("Got request from client: " + req);
            String chatroom = req.getChatroom();
            int numberOfMsg = req.getNumberOfMessages();

            List<String> messages = chatroomFileHelper.readFile(chatroom);

            int beginning = messages.size() - numberOfMsg;
            if(beginning < 0){
                beginning = 0;
            }

            List<String> messagesToSend = messages.subList(beginning, messages.size());

            List<String> messagesToSendModified = modifyMessagesToSendForMobileData(messagesToSend);

            sendMessageStreamToClient(responseObserver, messagesToSendModified);
        }

        @Override
        public void getChatMessagesSincePositionMobileData(chatMessageFromPosition req, StreamObserver<messageResponse> responseObserver){
            logger.info("Got request from client: " + req);

            int position = req.getPositionOfLastMessage();
            String chatroom = req.getChatroom();

            List<String> messages = chatroomFileHelper.readFile(chatroom);

            List<String> remainMessages = messages.subList(position, messages.size());

            List<String> messagesToSend = modifyMessagesToSendForMobileData(remainMessages);

            sendMessageStreamToClient(responseObserver, messagesToSend);

        }

        @Override
        public void getMessagesBetweenPositionsMobileData(messagesBetweenPosition req, StreamObserver<messageResponse> responseObserver){
            logger.info("Got request from client: " + req);

            int position = req.getPositionOfLastMessage();
            String chatroom = req.getChatroom();
            int numberOfMessages = req.getNumberOfMessages();

            int beginning = position-numberOfMessages;
            if(beginning < 0){
                beginning = 0;
            }

            List<String> messages = chatroomFileHelper.readFile(chatroom);

            List<String> remainMessages = messages.subList(beginning, position);

            List<String> messagesToSend = modifyMessagesToSendForMobileData(remainMessages);

            sendMessageStreamToClient(responseObserver, messagesToSend);
        }

        @Override
        public StreamObserver<listenToChatroom> listenToChatrooms(StreamObserver<messageResponse> responseObserver){
            return new listenToChatroomObserver(responseObserver);
        }

        private void sendMessageStreamToClient(StreamObserver<messageResponse> responseObserver, List<String> messages) {
            String data, username, timestamp, type;
            int position;
            for (String m: messages) {
                String[] aux = m.split(",");
                data = aux[0];
                username = aux[1];
                timestamp = aux[2];
                type = aux[3];
                position = Integer.parseInt(aux[4]);
                messageResponse message = messageResponse.newBuilder()
                        .setUsername(username)
                        .setTimestamp(timestamp)
                        .setData(data)
                        .setType(Integer.parseInt(type))
                        .setPosition(position)
                        .build();
                responseObserver.onNext(message);
            }

            responseObserver.onCompleted();
        }

        //removes the image data
        private List<String> modifyMessagesToSendForMobileData(List<String> messagesToSend){

            String data, username, timestamp, type, finalMessage, position;
            for(int i = 0; i < messagesToSend.size(); i++){
                String[] msg = messagesToSend.get(i).split(",");
                data = msg[0];
                username = msg[1];
                timestamp = msg[2];
                type = msg[3];
                position = msg[4];
                //if image then put default image, don't transmit
                if(type.equals("1")){
                    msg[0] = "";
                }
                finalMessage = data + "," + username + "," + timestamp + "," + type + "," + position;
                messagesToSend.set(i, finalMessage);
            }

            return messagesToSend;
        }

        private void sendMessageToInterestedClients(messageResponse message, String chatroom) {

            List<StreamObserver<messageResponse>> clients = clientSubscriptions.get(chatroom);
            if(clients == null){
                logger.info("No interested Clients"); //how??
            } else {
                for(StreamObserver<messageResponse> client: clients){
                    client.onNext(message);
                }
            }
        }

        private static class listenToChatroomObserver implements StreamObserver<listenToChatroom> {

            private final StreamObserver<messageResponse> responseObserver;
            private List<String> allChats = new ArrayList<>();

            public listenToChatroomObserver(StreamObserver<messageResponse> responseObserver) {
                this.responseObserver = responseObserver;
            }

            @Override
            public void onNext(listenToChatroom listenToChatroom) {
                String chat = listenToChatroom.getChatroom();

                allChats.add(chat);

                if(clientSubscriptions.containsKey(chat)){
                    clientSubscriptions.get(chat).add(responseObserver);
                } else {
                    List<StreamObserver<messageResponse>> clients = new ArrayList<>();
                    clients.add(responseObserver);
                    clientSubscriptions.put(chat, clients);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                logger.info(throwable.getMessage());
                removeClient();
            }

            @Override
            public void onCompleted() {
                removeClient();
            }

            private void removeClient(){
                for(String chat: allChats){
                    List<StreamObserver<messageResponse>> clients = clientSubscriptions.get(chat);
                    clients.remove(responseObserver);
                    clientSubscriptions.put(chat, clients);
                }
            }
        }
    }
}
