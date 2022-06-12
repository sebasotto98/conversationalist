package org.example.cmu_project;

import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.backendserver.*;

public class TestClient {

    public static void main(String[] args) {

        ServerGrpc.ServerBlockingStub serverBlockingStub = ServerGrpc.newBlockingStub(ManagedChannelBuilder.forAddress("172.28.128.1", 50051).usePlaintext().build());

        //Send Requests
        JoinChatRequest request = JoinChatRequest.newBuilder().setUser("da2").setChatName("newQ").build();
        JoinChatReply reply = serverBlockingStub.joinChat(request);
        System.out.println(reply.getAck());
    }

}
