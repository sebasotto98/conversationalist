package org.example.cmu_project;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.examples.backendserver.ServerGrpc;
import io.grpc.examples.backendserver.messageResponse;
import io.grpc.examples.backendserver.sendingMessage;
import io.grpc.examples.backendserver.chatMessageRequest;
import io.grpc.stub.StreamObserver;
import org.example.cmu_project.helpers.ChatroomFileHelper;
import org.example.cmu_project.helpers.FileHelper;

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
                    .setData(req.getData())
                    .setTimestamp(timestamp)
                    .setUsername(req.getUsername())
                    .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void getAllChatMessages(chatMessageRequest req, StreamObserver<messageResponse> responseObserver){
            logger.info("Got request from client: " + req);

            String data, username, timestamp, type;
            String chatroom = req.getChatroom();

            List<String> messages = chatroomFileHelper.readFile(chatroom);

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
    }
}
