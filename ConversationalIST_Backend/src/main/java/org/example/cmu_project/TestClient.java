package org.example.cmu_project;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.backendserver.*;

import java.util.List;


public class TestClient {

    private static ServerGrpc.ServerBlockingStub ServerBlockingStub;

    public static void main(String[] args) {


        ServerBlockingStub = ServerGrpc.newBlockingStub(ManagedChannelBuilder.forAddress("172.28.128.1",50051).usePlaintext().build());

        //Send Requests
        JoinChatRequest request = JoinChatRequest.newBuilder().setUser("da2").setChatName("newQ").build();
        JoinChatReply reply = ServerBlockingStub.joinChat(request);
        System.out.println(reply.getAck());






    }

}
