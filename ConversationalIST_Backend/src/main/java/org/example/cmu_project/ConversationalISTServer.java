package org.example.cmu_project;

import io.grpc.Server;
import io.grpc.examples.backendserver.*;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;
import io.netty.handler.ssl.SslContext;
import nl.altindag.ssl.SSLFactory;
import nl.altindag.ssl.util.NettySslUtils;
import org.example.cmu_project.enums.ChatType;
import org.example.cmu_project.helpers.ChatroomFileHelper;
import org.example.cmu_project.helpers.GeneralHelper;
import org.example.cmu_project.helpers.UserFileHelper;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ConversationalISTServer {

    private static final Logger logger = Logger.getLogger(ConversationalISTServer.class.getName());
    private static final String KEYSTORE_DIR = "keystores/";
    private static final String TRUSTSTORE_DIR = "truststores/";
    private static final int TIMEOUT = 30;
    private static final int PORT_NUM = 50051;

    //hash map where key is the chatroom name and value is a list with the StreamObserver of all the interested clients
    private static final HashMap<String, List<StreamObserver<messageResponse>>> clientSubscriptionsWifi = new HashMap<>();
    private static final HashMap<String, List<StreamObserver<messageResponse>>> clientSubscriptionsMobileData = new HashMap<>();

    private Server server;

    private void start() throws IOException {
        Path identityStorePath = Paths.get(KEYSTORE_DIR + "server_KeystoreFile.jks");
        Path trustStorePath = Paths.get(TRUSTSTORE_DIR + "server_TruststoreFile.jks");

        SSLFactory sslFactory = SSLFactory.builder()
                .withIdentityMaterial(identityStorePath, "testtest".toCharArray())
                .withTrustMaterial(trustStorePath, "testtest".toCharArray())
                .build();
        SslContext sslContext = GrpcSslContexts.configure(NettySslUtils.forServer(sslFactory)).build();
        /* The port on which the server should run */
        server = NettyServerBuilder.forPort(PORT_NUM)
                .addService(new ServerImpl())
                .sslContext(sslContext)
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
        public void registerUser(registerUserRequest request,StreamObserver<registerUserReply> responseObserver) {

            String user = request.getUser();
            String password_hashed = request.getPasswordHash();
            String ack;

            if(userFileHelper.userExists(user))
                ack = "User name already in use";
            else {
                //add new user to users_info
                userFileHelper.store_info(user + "," + password_hashed);

                //create a csv file for this new user
                userFileHelper.store("",user);

                //set ack to OK
                ack = "OK";
            }

            registerUserReply response = registerUserReply.newBuilder().setAck(ack).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }

        @Override
        public void loginUser(loginUserRequest request,StreamObserver<loginUserReply> responseObserver) {

            String username = request.getUser();
            String password_hashed = request.getPasswordHash();
            String ack;

            if (!userFileHelper.userExists(username)) {
                ack = "Username doesnt exist";
            } else {
                if (!userFileHelper.checkPassword(username,password_hashed)) {
                    ack = "Password is Wrong";
                } else  {
                    ack = "OK";
                }
            }

            loginUserReply response = loginUserReply.newBuilder().setAck(ack).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }

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

            sendMessageToInterestedClientsInWifi(reply, chatroom);
            sendMessageToInterestedClientsInMobileData(reply, chatroom);

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void getAllChatMessages(chatMessageRequest req, StreamObserver<messageResponse> responseObserver){
            logger.info("Got request from client: " + req);

            String chatroom = req.getChatroom();

            List<String> messages = chatroomFileHelper.readFile(chatroom);

            sendMessageStreamToClient(responseObserver, messages, chatroom);
        }

        @Override
        public void getChatMessagesSincePosition(chatMessageFromPosition req, StreamObserver<messageResponse> responseObserver){
            logger.info("Got request from client: " + req);

            int position = req.getPositionOfLastMessage();
            String chatroom = req.getChatroom();

            List<String> messages = chatroomFileHelper.readFile(chatroom);

            List<String> remainMessages = messages.subList(position, messages.size());

            sendMessageStreamToClient(responseObserver, remainMessages, chatroom);
        }

        @Override
        public void createChat(CreateChatRequest request, StreamObserver<CreateChatReply> responseObserver) {

            String chatName = request.getChatroomName();
            String userName = request.getUser();
            String chatType = request.getTypeOfChat();
            logger.info(chatType);

            if(generalHelper.userExists(userName)) {
                if(!generalHelper.chatAlreadyExists(chatName)) {
                    String data = chatName + ",";
                    userFileHelper.store(data, userName);
                    chatroomFileHelper.store("", ChatroomFileHelper.CHATROOM_FILE_BEGIN + chatName);
                    if(chatType.equalsIgnoreCase(ChatType.GEOFENCED.name())) {
                        chatroomFileHelper.store(chatName + "," + userName + "," + chatType + "," + request.getLocation().getLatitude() + "/" + request.getLocation().getLongitude() + "," + request.getRadius(), ChatroomFileHelper.CHATROOM_FILE_INFO);
                    } else if (chatType.equalsIgnoreCase(ChatType.PRIVATE.name())) {
                        chatroomFileHelper.store(chatName+","+userName+ "," + chatType, ChatroomFileHelper.CHATROOM_FILE_INFO);
                    } else if (chatType.equalsIgnoreCase(ChatType.PUBLIC.name())) {
                        chatroomFileHelper.store(chatName+","+userName+ "," + chatType, ChatroomFileHelper.CHATROOM_FILE_INFO);
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
            Location location = request.getUserLocation();
            double longitude = Double.parseDouble(location.getLongitude());
            double latitude = Double.parseDouble(location.getLatitude());

            List<String> user_chats = userFileHelper.getChats(user);
            List<String> chats_info = chatroomFileHelper.readInfoFile();
            List<String> chats_available = new ArrayList<>();

            for (String line: chats_info) {

                logger.info(line);

                String[] split_line = line.split(",");
                String chat_name = split_line[0];
                String type_of_chat = split_line[2];

                if(!user_chats.contains(chat_name)) {
                    if(!type_of_chat.equalsIgnoreCase(ChatType.PRIVATE.name())) {
                        if(type_of_chat.equalsIgnoreCase(ChatType.GEOFENCED.name())) {
                            if(userInChatRadio(latitude,longitude,chat_name))
                                chats_available.add(chat_name);
                        } else {
                            chats_available.add(chat_name);
                        }

                    }
                }


            }

            JoinableChatsReply response = JoinableChatsReply.newBuilder().addAllChats(chats_available).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void joinChat(JoinChatRequest request,StreamObserver<JoinChatReply> responseObserver) {

            String user = request.getUser();
            String chat_name = request.getChatName();

            userFileHelper.store(chat_name+",",user);

            JoinChatReply response = JoinChatReply.newBuilder().setAck("OK").build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void leaveChat(LeaveChatRequest request,StreamObserver<LeaveChatReply> responseObserver) {

            String user = request.getUser();
            String chat_name = request.getChatName();
            String ack;

            try {
                userFileHelper.leaveChat(user,chat_name);
                ack = "OK";
            } catch (IOException e) {
                ack = "ERROR";
                e.printStackTrace();
            }

            LeaveChatReply response = LeaveChatReply.newBuilder().setAck(ack).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }



        @Override
        public void getAllChats(GetChatsRequest request,StreamObserver<GetChatsReply> responseObserver) {

            String user = request.getUser();

            List<String> user_chats = userFileHelper.getChats(user);
            List<String> user_owner_private_chats = new ArrayList<>();

            for (String user_chat : user_chats) {
                logger.info(user_chat);
            }

            List<String> lines_chat_info = chatroomFileHelper.readInfoFile();

            for (String chat : user_chats) {

                for (int i = 0;i<lines_chat_info.size();i++) {
                    String[] line_splitted = lines_chat_info.get(i).split(",");
                    if (line_splitted[0].equals(chat)) {
                        if(line_splitted[1].equals(user) && line_splitted[2].equals("Private")) {
                            user_owner_private_chats.add(chat);
                        }
                    }
                }
            }

            GetChatsReply response = GetChatsReply.newBuilder().addAllUserChats(user_chats).addAllUserOwnerPrivateChats(user_owner_private_chats).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }

        @Override
        public void getChatMembers(GetChatMembersRequest request,StreamObserver<GetChatMembersReply> responseObserver) {

            String chat_name = request.getChatName();
            String chat_owner = chatroomFileHelper.getChatOwner(chat_name);
            System.out.println("owner: " + chat_owner);
            List<String> chat_members = new ArrayList<>();

            List<String> all_users = userFileHelper.allUsers();


            for (String user: all_users) {
                if (!user.equals(chat_owner)) {
                    System.out.println(user);
                    if(userFileHelper.userInChat(user,chat_name)) {
                        chat_members.add(user);
                    }
                }
            }

            System.out.println(chat_members);

            GetChatMembersReply response = GetChatMembersReply.newBuilder().addAllMembers(chat_members).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }

        @Override
        public void getChatType(ChatTypeRequest request,StreamObserver<ChatTypeReply> responseObserver) {

            String chat_name = request.getChatName();

            String chat_type = chatroomFileHelper.getChatType(chat_name);

            ChatTypeReply response = ChatTypeReply.newBuilder().setChatType(chat_type).build();
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

            sendMessageStreamToClient(responseObserver, messagesToSendModified, chatroom);
        }

        @Override
        public void getChatMessagesSincePositionMobileData(chatMessageFromPosition req, StreamObserver<messageResponse> responseObserver){
            logger.info("Got request from client: " + req);

            int position = req.getPositionOfLastMessage();
            String chatroom = req.getChatroom();

            List<String> messages = chatroomFileHelper.readFile(chatroom);

            List<String> remainMessages = messages.subList(position, messages.size());

            List<String> messagesToSend = modifyMessagesToSendForMobileData(remainMessages);

            sendMessageStreamToClient(responseObserver, messagesToSend, chatroom);

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

            sendMessageStreamToClient(responseObserver, messagesToSend, chatroom);
        }

        @Override
        public StreamObserver<listenToChatroom> listenToChatrooms(StreamObserver<messageResponse> responseObserver){
            return new listenToChatroomObserver(responseObserver);
        }

        public StreamObserver<listenToChatroom> listenToChatroomsMobileData(StreamObserver<messageResponse> responseObserver){
            return new listenToChatroomMobileDataObserver(responseObserver);
        }

        @Override
        public void getMessageAtPosition(getMessagePosition req, StreamObserver<messageResponse> responseObserver){
            int position = req.getPosition();
            String chatroom = req.getChatroom();

            List<String> messages = chatroomFileHelper.readFile(chatroom);

            for(String message: messages){

                String[] m = message.split(",");

                int messagePosition = Integer.parseInt(m[4]);
                if(messagePosition == position){
                    messageResponse reply = messageResponse.newBuilder()
                            .setUsername(m[1])
                            .setTimestamp(m[2])
                            .setData(m[0])
                            .setType(Integer.parseInt(m[3]))
                            .setPosition(position)
                            .setChatroom(chatroom)
                            .build();
                    responseObserver.onNext(reply);
                    break;
                }
            }
            responseObserver.onCompleted();
        }

        private void sendMessageStreamToClient(StreamObserver<messageResponse> responseObserver, List<String> messages, String chat) {
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
                        .setChatroom(chat)
                        .build();
                responseObserver.onNext(message);
            }

            responseObserver.onCompleted();
        }

        private boolean userInChatRadio(double user_latitude,double user_longitude,String chat_name) {

            //get chat details about location
             List<String> location_and_radius_string = null;
            System.out.println(location_and_radius_string.get(0) + " + " + location_and_radius_string.get(1));
            String[] split_location = location_and_radius_string.get(0).split("/");
            double radius = Double.parseDouble(location_and_radius_string.get(1));
            double chat_lat = Double.parseDouble(split_location[0]);
            double chat_lon = Double.parseDouble(split_location[1]);

            double el1 = 0.0;
            double el2 = 0.0;

            final int R = 6371; // Radius of the earth

            double latDistance = Math.toRadians(chat_lat - user_latitude);
            double lonDistance = Math.toRadians(chat_lon - user_longitude);
            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(user_latitude)) * Math.cos(Math.toRadians(chat_lat))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = R * c * 1000; // convert to meters

            double height = el1 - el2;

            distance = Math.pow(distance, 2) + Math.pow(height, 2);

            return !(Math.sqrt(distance) > radius);

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
                    data = "";
                }
                finalMessage = data + "," + username + "," + timestamp + "," + type + "," + position;
                messagesToSend.set(i, finalMessage);
            }

            return messagesToSend;
        }


        private void sendMessageToInterestedClientsInMobileData(messageResponse message, String chatroom) {

            messageResponse mobileDataMessage;
            //remove data of the image
            if(message.getType() == 1){
                mobileDataMessage = messageResponse.newBuilder()
                        .setChatroom(message.getChatroom())
                        .setPosition(message.getPosition())
                        .setType(message.getType())
                        .setData("")
                        .setTimestamp(message.getTimestamp())
                        .setUsername(message.getUsername())
                        .build();
            } else {
                mobileDataMessage = messageResponse.newBuilder()
                        .setChatroom(message.getChatroom())
                        .setPosition(message.getPosition())
                        .setType(message.getType())
                        .setData(message.getData())
                        .setTimestamp(message.getTimestamp())
                        .setUsername(message.getUsername())
                        .build();
            }

            List<StreamObserver<messageResponse>> clients = clientSubscriptionsMobileData.get(chatroom);
            if(clients == null || clients.isEmpty()){
                logger.info("No interested Clients"); //how??
            } else {
                for(StreamObserver<messageResponse> client: clients){
                    client.onNext(mobileDataMessage);
                }
            }
        }

        private void sendMessageToInterestedClientsInWifi(messageResponse message, String chatroom) {

            List<StreamObserver<messageResponse>> clients = clientSubscriptionsWifi.get(chatroom);
            if(clients == null || clients.isEmpty()){
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
                logger.info("Got request from client (WiFi): " + listenToChatroom);
                allChats.add(chat);
                logger.info("allChats " + allChats);
                if(clientSubscriptionsWifi.containsKey(chat)){
                    clientSubscriptionsWifi.get(chat).add(responseObserver);
                } else {
                    List<StreamObserver<messageResponse>> clients = new ArrayList<>();
                    clients.add(responseObserver);
                    clientSubscriptionsWifi.put(chat, clients);
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
                    List<StreamObserver<messageResponse>> clients = clientSubscriptionsWifi.get(chat);
                    clients.remove(responseObserver);
                    clientSubscriptionsWifi.put(chat, clients);
                }
            }
        }

        private static class listenToChatroomMobileDataObserver implements StreamObserver<listenToChatroom> {

            private final StreamObserver<messageResponse> responseObserver;
            private List<String> allChats = new ArrayList<>();

            public listenToChatroomMobileDataObserver(StreamObserver<messageResponse> responseObserver) {
                this.responseObserver = responseObserver;
            }

            @Override
            public void onNext(listenToChatroom listenToChatroom) {
                String chat = listenToChatroom.getChatroom();
                logger.info("Got request from client (MobileData): " + listenToChatroom);
                allChats.add(chat);
                logger.info("allChats " + allChats);
                if(clientSubscriptionsMobileData.containsKey(chat)){
                    clientSubscriptionsMobileData.get(chat).add(responseObserver);
                } else {
                    List<StreamObserver<messageResponse>> clients = new ArrayList<>();
                    clients.add(responseObserver);
                    clientSubscriptionsMobileData.put(chat, clients);
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
                    List<StreamObserver<messageResponse>> clients = clientSubscriptionsMobileData.get(chat);
                    clients.remove(responseObserver);
                    clientSubscriptionsMobileData.put(chat, clients);
                }
            }
        }
    }
}
