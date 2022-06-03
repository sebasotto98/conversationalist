package io.grpc.examples.backendserver;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.46.0)",
    comments = "Source: backendServer.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ServerGrpc {

  private ServerGrpc() {}

  public static final String SERVICE_NAME = "helloworld.Server";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.sendingMessage,
      io.grpc.examples.backendserver.messageResponse> getSendMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "sendMessage",
      requestType = io.grpc.examples.backendserver.sendingMessage.class,
      responseType = io.grpc.examples.backendserver.messageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.sendingMessage,
      io.grpc.examples.backendserver.messageResponse> getSendMessageMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.sendingMessage, io.grpc.examples.backendserver.messageResponse> getSendMessageMethod;
    if ((getSendMessageMethod = ServerGrpc.getSendMessageMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getSendMessageMethod = ServerGrpc.getSendMessageMethod) == null) {
          ServerGrpc.getSendMessageMethod = getSendMessageMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.sendingMessage, io.grpc.examples.backendserver.messageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.sendingMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.messageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("sendMessage"))
              .build();
        }
      }
    }
    return getSendMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.chatMessageRequest,
      io.grpc.examples.backendserver.messageResponse> getGetAllChatMessagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllChatMessages",
      requestType = io.grpc.examples.backendserver.chatMessageRequest.class,
      responseType = io.grpc.examples.backendserver.messageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.chatMessageRequest,
      io.grpc.examples.backendserver.messageResponse> getGetAllChatMessagesMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.chatMessageRequest, io.grpc.examples.backendserver.messageResponse> getGetAllChatMessagesMethod;
    if ((getGetAllChatMessagesMethod = ServerGrpc.getGetAllChatMessagesMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getGetAllChatMessagesMethod = ServerGrpc.getGetAllChatMessagesMethod) == null) {
          ServerGrpc.getGetAllChatMessagesMethod = getGetAllChatMessagesMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.chatMessageRequest, io.grpc.examples.backendserver.messageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllChatMessages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.chatMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.messageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("getAllChatMessages"))
              .build();
        }
      }
    }
    return getGetAllChatMessagesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerStub>() {
        @java.lang.Override
        public ServerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerStub(channel, callOptions);
        }
      };
    return ServerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerBlockingStub>() {
        @java.lang.Override
        public ServerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerBlockingStub(channel, callOptions);
        }
      };
    return ServerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerFutureStub>() {
        @java.lang.Override
        public ServerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerFutureStub(channel, callOptions);
        }
      };
    return ServerFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class ServerImplBase implements io.grpc.BindableService {

    /**
     */
    public void sendMessage(io.grpc.examples.backendserver.sendingMessage request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendMessageMethod(), responseObserver);
    }

    /**
     */
    public void getAllChatMessages(io.grpc.examples.backendserver.chatMessageRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllChatMessagesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendMessageMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.sendingMessage,
                io.grpc.examples.backendserver.messageResponse>(
                  this, METHODID_SEND_MESSAGE)))
          .addMethod(
            getGetAllChatMessagesMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.chatMessageRequest,
                io.grpc.examples.backendserver.messageResponse>(
                  this, METHODID_GET_ALL_CHAT_MESSAGES)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class ServerStub extends io.grpc.stub.AbstractAsyncStub<ServerStub> {
    private ServerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerStub(channel, callOptions);
    }

    /**
     */
    public void sendMessage(io.grpc.examples.backendserver.sendingMessage request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllChatMessages(io.grpc.examples.backendserver.chatMessageRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetAllChatMessagesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class ServerBlockingStub extends io.grpc.stub.AbstractBlockingStub<ServerBlockingStub> {
    private ServerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.examples.backendserver.messageResponse sendMessage(io.grpc.examples.backendserver.sendingMessage request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<io.grpc.examples.backendserver.messageResponse> getAllChatMessages(
        io.grpc.examples.backendserver.chatMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetAllChatMessagesMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class ServerFutureStub extends io.grpc.stub.AbstractFutureStub<ServerFutureStub> {
    private ServerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.messageResponse> sendMessage(
        io.grpc.examples.backendserver.sendingMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_MESSAGE = 0;
  private static final int METHODID_GET_ALL_CHAT_MESSAGES = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_MESSAGE:
          serviceImpl.sendMessage((io.grpc.examples.backendserver.sendingMessage) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_CHAT_MESSAGES:
          serviceImpl.getAllChatMessages((io.grpc.examples.backendserver.chatMessageRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ServerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.grpc.examples.backendserver.BackEndServerProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Server");
    }
  }

  private static final class ServerFileDescriptorSupplier
      extends ServerBaseDescriptorSupplier {
    ServerFileDescriptorSupplier() {}
  }

  private static final class ServerMethodDescriptorSupplier
      extends ServerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServerMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ServerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServerFileDescriptorSupplier())
              .addMethod(getSendMessageMethod())
              .addMethod(getGetAllChatMessagesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
