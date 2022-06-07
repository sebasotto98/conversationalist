package org.example.cmu_project;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.backendserver.CreateChatReply;
import io.grpc.examples.backendserver.CreateChatRequest;
import io.grpc.examples.backendserver.ServerGrpc;


public class TestClient {

    private static ServerGrpc.ServerBlockingStub ServerBlockingStub;

    public static void main(String[] args) {


            ServerBlockingStub = ServerGrpc.newBlockingStub(ManagedChannelBuilder.forAddress("172.28.128.1",50051).usePlaintext().build());

            //Send Requests
            CreateChatRequest request = CreateChatRequest.newBuilder().setChatroomName("sala1").setUser("tester").build();
            CreateChatReply reply = ServerBlockingStub.createChat(request);
            System.out.println(reply.getAck());






    }

}
