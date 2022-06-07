package org.example.cmu_project;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.backendserver.*;
import io.grpc.stub.StreamObserver;
import org.example.cmu_project.helpers.ChatroomFileHelper;
import org.example.cmu_project.helpers.FileHelper;
import org.example.cmu_project.helpers.GeneralHelper;
import org.example.cmu_project.helpers.UserFileHelper;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ConversationalISTServer {

    private static final Logger logger = Logger.getLogger(ConversationalISTServer.class.getName());
    private static final int TIMEOUT = 30;
    private static final int PORT_NUM = 50051;

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

            chatroomFileHelper.writeToFile(data, username, timestamp, type, chatroom);

            messageResponse reply = messageResponse.newBuilder()
                    .setData(data)
                    .setTimestamp(timestamp)
                    .setUsername(username)
                    .setChatroom(chatroom)
                    .setType(Integer.parseInt(type))
                    .build();
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

        private void sendMessageStreamToClient(StreamObserver<messageResponse> responseObserver, List<String> messages) {
            String data, username, timestamp, type;
            for (String m: messages) {
                String[] aux = m.split(",");
                data = aux[0];
                username = aux[1];
                timestamp = aux[2];
                type = aux[3];
                messageResponse message = messageResponse.newBuilder()
                        .setUsername(username)
                        .setTimestamp(timestamp)
                        .setData(data)
                        .setType(Integer.parseInt(type))
                        .build();
                responseObserver.onNext(message);
            }

            responseObserver.onCompleted();
        }

        @Override
        public void createChat(CreateChatRequest request, StreamObserver<CreateChatReply> responseObserver) {

            String chatName = request.getChatroomName();
            String user_name = request.getUser();

            if(generalHelper.userExists(user_name)) {
                if(!generalHelper.chatAlreadyExists(chatName)) {
                        userFileHelper.store(chatName,user_name);
                        chatroomFileHelper.store("",chatroomFileHelper.CHATROOM_FILE_BEGIN+chatName);
                        chatroomFileHelper.store(chatName+","+user_name,chatroomFileHelper.CHATROOM_FILE_INFO);
                }
            }

            CreateChatReply response = CreateChatReply.newBuilder().setAck("OK").build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();



        }

        @Override
        public void getLastNMessagesFromChat(NMessagesFromChat request, StreamObserver<messageResponse> responseObserver){
            String chatroom = request.getChatroom();
            int numberOfMsg = request.getNumberOfMessages();

            List<String> messages = chatroomFileHelper.readFile(chatroom);

            List<String> messagesToSend = messages.subList(messages.size() - numberOfMsg, messages.size());

            List<String> messagesToSendModified = modifyMessagesToSendForMobileData(messagesToSend);

            sendMessageStreamToClient(responseObserver, messagesToSendModified);
        }

        private List<String> modifyMessagesToSendForMobileData(List<String> messagesToSend){

            String data, username, timestamp, type, finalMessage;
            for(int i = 0; i < messagesToSend.size(); i++){
                String[] msg = messagesToSend.get(i).split(",");
                data = msg[0];
                username = msg[1];
                timestamp = msg[2];
                type = msg[3];
                //if image then put default image, don't transmit
                if(type.equals("1")){
                    msg[0] = "";
                }
                finalMessage = data + "," + username + "," + timestamp + "," + type;
                messagesToSend.set(i, finalMessage);
            }

            return messagesToSend;
        }
    }
}
