package org.example.cmu_project;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.helloworld.CreateChatReply;
import io.grpc.examples.helloworld.CreateChatRequest;
import io.grpc.examples.helloworld.GreeterGrpc;

import static io.grpc.examples.helloworld.GreeterGrpc.newBlockingStub;

public class TestClient {

    private static GreeterGrpc.GreeterBlockingStub GreeterBlockingStub;

    public static void main(String[] args) {


               GreeterBlockingStub = GreeterGrpc.newBlockingStub(ManagedChannelBuilder.forAddress("192.168.1.238",50051).usePlaintext().build());

                //Send Requests
                CreateChatRequest request = CreateChatRequest.newBuilder().setChatroomName("sala1").setUser("tester").build();
                CreateChatReply reply = GreeterBlockingStub.createChat(request);
                System.out.println(reply.getAck());




    }

}
