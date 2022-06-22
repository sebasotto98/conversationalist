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
  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.registerUserRequest,
      io.grpc.examples.backendserver.registerUserReply> getRegisterUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerUser",
      requestType = io.grpc.examples.backendserver.registerUserRequest.class,
      responseType = io.grpc.examples.backendserver.registerUserReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.registerUserRequest,
      io.grpc.examples.backendserver.registerUserReply> getRegisterUserMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.registerUserRequest, io.grpc.examples.backendserver.registerUserReply> getRegisterUserMethod;
    if ((getRegisterUserMethod = ServerGrpc.getRegisterUserMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getRegisterUserMethod = ServerGrpc.getRegisterUserMethod) == null) {
          ServerGrpc.getRegisterUserMethod = getRegisterUserMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.registerUserRequest, io.grpc.examples.backendserver.registerUserReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.registerUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.registerUserReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("registerUser"))
              .build();
        }
      }
    }
    return getRegisterUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.loginUserRequest,
      io.grpc.examples.backendserver.loginUserReply> getLoginUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "loginUser",
      requestType = io.grpc.examples.backendserver.loginUserRequest.class,
      responseType = io.grpc.examples.backendserver.loginUserReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.loginUserRequest,
      io.grpc.examples.backendserver.loginUserReply> getLoginUserMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.loginUserRequest, io.grpc.examples.backendserver.loginUserReply> getLoginUserMethod;
    if ((getLoginUserMethod = ServerGrpc.getLoginUserMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getLoginUserMethod = ServerGrpc.getLoginUserMethod) == null) {
          ServerGrpc.getLoginUserMethod = getLoginUserMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.loginUserRequest, io.grpc.examples.backendserver.loginUserReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "loginUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.loginUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.loginUserReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("loginUser"))
              .build();
        }
      }
    }
    return getLoginUserMethod;
  }

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

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.chatMessageFromPosition,
      io.grpc.examples.backendserver.messageResponse> getGetChatMessagesSincePositionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getChatMessagesSincePosition",
      requestType = io.grpc.examples.backendserver.chatMessageFromPosition.class,
      responseType = io.grpc.examples.backendserver.messageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.chatMessageFromPosition,
      io.grpc.examples.backendserver.messageResponse> getGetChatMessagesSincePositionMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.chatMessageFromPosition, io.grpc.examples.backendserver.messageResponse> getGetChatMessagesSincePositionMethod;
    if ((getGetChatMessagesSincePositionMethod = ServerGrpc.getGetChatMessagesSincePositionMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getGetChatMessagesSincePositionMethod = ServerGrpc.getGetChatMessagesSincePositionMethod) == null) {
          ServerGrpc.getGetChatMessagesSincePositionMethod = getGetChatMessagesSincePositionMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.chatMessageFromPosition, io.grpc.examples.backendserver.messageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getChatMessagesSincePosition"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.chatMessageFromPosition.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.messageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("getChatMessagesSincePosition"))
              .build();
        }
      }
    }
    return getGetChatMessagesSincePositionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.CreateChatRequest,
      io.grpc.examples.backendserver.CreateChatReply> getCreateChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "createChat",
      requestType = io.grpc.examples.backendserver.CreateChatRequest.class,
      responseType = io.grpc.examples.backendserver.CreateChatReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.CreateChatRequest,
      io.grpc.examples.backendserver.CreateChatReply> getCreateChatMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.CreateChatRequest, io.grpc.examples.backendserver.CreateChatReply> getCreateChatMethod;
    if ((getCreateChatMethod = ServerGrpc.getCreateChatMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getCreateChatMethod = ServerGrpc.getCreateChatMethod) == null) {
          ServerGrpc.getCreateChatMethod = getCreateChatMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.CreateChatRequest, io.grpc.examples.backendserver.CreateChatReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "createChat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.CreateChatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.CreateChatReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("createChat"))
              .build();
        }
      }
    }
    return getCreateChatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.LeaveChatRequest,
      io.grpc.examples.backendserver.LeaveChatReply> getLeaveChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "leaveChat",
      requestType = io.grpc.examples.backendserver.LeaveChatRequest.class,
      responseType = io.grpc.examples.backendserver.LeaveChatReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.LeaveChatRequest,
      io.grpc.examples.backendserver.LeaveChatReply> getLeaveChatMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.LeaveChatRequest, io.grpc.examples.backendserver.LeaveChatReply> getLeaveChatMethod;
    if ((getLeaveChatMethod = ServerGrpc.getLeaveChatMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getLeaveChatMethod = ServerGrpc.getLeaveChatMethod) == null) {
          ServerGrpc.getLeaveChatMethod = getLeaveChatMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.LeaveChatRequest, io.grpc.examples.backendserver.LeaveChatReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "leaveChat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.LeaveChatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.LeaveChatReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("leaveChat"))
              .build();
        }
      }
    }
    return getLeaveChatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.AddUserToChatRequest,
      io.grpc.examples.backendserver.AddUserToChatReply> getAddUserToChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addUserToChat",
      requestType = io.grpc.examples.backendserver.AddUserToChatRequest.class,
      responseType = io.grpc.examples.backendserver.AddUserToChatReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.AddUserToChatRequest,
      io.grpc.examples.backendserver.AddUserToChatReply> getAddUserToChatMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.AddUserToChatRequest, io.grpc.examples.backendserver.AddUserToChatReply> getAddUserToChatMethod;
    if ((getAddUserToChatMethod = ServerGrpc.getAddUserToChatMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getAddUserToChatMethod = ServerGrpc.getAddUserToChatMethod) == null) {
          ServerGrpc.getAddUserToChatMethod = getAddUserToChatMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.AddUserToChatRequest, io.grpc.examples.backendserver.AddUserToChatReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "addUserToChat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.AddUserToChatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.AddUserToChatReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("addUserToChat"))
              .build();
        }
      }
    }
    return getAddUserToChatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.GetChatMembersRequest,
      io.grpc.examples.backendserver.GetChatMembersReply> getGetChatMembersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getChatMembers",
      requestType = io.grpc.examples.backendserver.GetChatMembersRequest.class,
      responseType = io.grpc.examples.backendserver.GetChatMembersReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.GetChatMembersRequest,
      io.grpc.examples.backendserver.GetChatMembersReply> getGetChatMembersMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.GetChatMembersRequest, io.grpc.examples.backendserver.GetChatMembersReply> getGetChatMembersMethod;
    if ((getGetChatMembersMethod = ServerGrpc.getGetChatMembersMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getGetChatMembersMethod = ServerGrpc.getGetChatMembersMethod) == null) {
          ServerGrpc.getGetChatMembersMethod = getGetChatMembersMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.GetChatMembersRequest, io.grpc.examples.backendserver.GetChatMembersReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getChatMembers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.GetChatMembersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.GetChatMembersReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("getChatMembers"))
              .build();
        }
      }
    }
    return getGetChatMembersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.JoinableChatsRequest,
      io.grpc.examples.backendserver.JoinableChatsReply> getGetJoinableChatsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getJoinableChats",
      requestType = io.grpc.examples.backendserver.JoinableChatsRequest.class,
      responseType = io.grpc.examples.backendserver.JoinableChatsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.JoinableChatsRequest,
      io.grpc.examples.backendserver.JoinableChatsReply> getGetJoinableChatsMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.JoinableChatsRequest, io.grpc.examples.backendserver.JoinableChatsReply> getGetJoinableChatsMethod;
    if ((getGetJoinableChatsMethod = ServerGrpc.getGetJoinableChatsMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getGetJoinableChatsMethod = ServerGrpc.getGetJoinableChatsMethod) == null) {
          ServerGrpc.getGetJoinableChatsMethod = getGetJoinableChatsMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.JoinableChatsRequest, io.grpc.examples.backendserver.JoinableChatsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getJoinableChats"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.JoinableChatsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.JoinableChatsReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("getJoinableChats"))
              .build();
        }
      }
    }
    return getGetJoinableChatsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.ChatTypeRequest,
      io.grpc.examples.backendserver.ChatTypeReply> getGetChatTypeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getChatType",
      requestType = io.grpc.examples.backendserver.ChatTypeRequest.class,
      responseType = io.grpc.examples.backendserver.ChatTypeReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.ChatTypeRequest,
      io.grpc.examples.backendserver.ChatTypeReply> getGetChatTypeMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.ChatTypeRequest, io.grpc.examples.backendserver.ChatTypeReply> getGetChatTypeMethod;
    if ((getGetChatTypeMethod = ServerGrpc.getGetChatTypeMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getGetChatTypeMethod = ServerGrpc.getGetChatTypeMethod) == null) {
          ServerGrpc.getGetChatTypeMethod = getGetChatTypeMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.ChatTypeRequest, io.grpc.examples.backendserver.ChatTypeReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getChatType"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.ChatTypeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.ChatTypeReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("getChatType"))
              .build();
        }
      }
    }
    return getGetChatTypeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.GetChatsRequest,
      io.grpc.examples.backendserver.GetChatsReply> getGetAllChatsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllChats",
      requestType = io.grpc.examples.backendserver.GetChatsRequest.class,
      responseType = io.grpc.examples.backendserver.GetChatsReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.GetChatsRequest,
      io.grpc.examples.backendserver.GetChatsReply> getGetAllChatsMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.GetChatsRequest, io.grpc.examples.backendserver.GetChatsReply> getGetAllChatsMethod;
    if ((getGetAllChatsMethod = ServerGrpc.getGetAllChatsMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getGetAllChatsMethod = ServerGrpc.getGetAllChatsMethod) == null) {
          ServerGrpc.getGetAllChatsMethod = getGetAllChatsMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.GetChatsRequest, io.grpc.examples.backendserver.GetChatsReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllChats"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.GetChatsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.GetChatsReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("getAllChats"))
              .build();
        }
      }
    }
    return getGetAllChatsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.JoinChatRequest,
      io.grpc.examples.backendserver.JoinChatReply> getJoinChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "joinChat",
      requestType = io.grpc.examples.backendserver.JoinChatRequest.class,
      responseType = io.grpc.examples.backendserver.JoinChatReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.JoinChatRequest,
      io.grpc.examples.backendserver.JoinChatReply> getJoinChatMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.JoinChatRequest, io.grpc.examples.backendserver.JoinChatReply> getJoinChatMethod;
    if ((getJoinChatMethod = ServerGrpc.getJoinChatMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getJoinChatMethod = ServerGrpc.getJoinChatMethod) == null) {
          ServerGrpc.getJoinChatMethod = getJoinChatMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.JoinChatRequest, io.grpc.examples.backendserver.JoinChatReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "joinChat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.JoinChatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.JoinChatReply.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("joinChat"))
              .build();
        }
      }
    }
    return getJoinChatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.NMessagesFromChat,
      io.grpc.examples.backendserver.messageResponse> getGetLastNMessagesFromChatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getLastNMessagesFromChat",
      requestType = io.grpc.examples.backendserver.NMessagesFromChat.class,
      responseType = io.grpc.examples.backendserver.messageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.NMessagesFromChat,
      io.grpc.examples.backendserver.messageResponse> getGetLastNMessagesFromChatMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.NMessagesFromChat, io.grpc.examples.backendserver.messageResponse> getGetLastNMessagesFromChatMethod;
    if ((getGetLastNMessagesFromChatMethod = ServerGrpc.getGetLastNMessagesFromChatMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getGetLastNMessagesFromChatMethod = ServerGrpc.getGetLastNMessagesFromChatMethod) == null) {
          ServerGrpc.getGetLastNMessagesFromChatMethod = getGetLastNMessagesFromChatMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.NMessagesFromChat, io.grpc.examples.backendserver.messageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getLastNMessagesFromChat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.NMessagesFromChat.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.messageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("getLastNMessagesFromChat"))
              .build();
        }
      }
    }
    return getGetLastNMessagesFromChatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.chatMessageFromPosition,
      io.grpc.examples.backendserver.messageResponse> getGetChatMessagesSincePositionMobileDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getChatMessagesSincePositionMobileData",
      requestType = io.grpc.examples.backendserver.chatMessageFromPosition.class,
      responseType = io.grpc.examples.backendserver.messageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.chatMessageFromPosition,
      io.grpc.examples.backendserver.messageResponse> getGetChatMessagesSincePositionMobileDataMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.chatMessageFromPosition, io.grpc.examples.backendserver.messageResponse> getGetChatMessagesSincePositionMobileDataMethod;
    if ((getGetChatMessagesSincePositionMobileDataMethod = ServerGrpc.getGetChatMessagesSincePositionMobileDataMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getGetChatMessagesSincePositionMobileDataMethod = ServerGrpc.getGetChatMessagesSincePositionMobileDataMethod) == null) {
          ServerGrpc.getGetChatMessagesSincePositionMobileDataMethod = getGetChatMessagesSincePositionMobileDataMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.chatMessageFromPosition, io.grpc.examples.backendserver.messageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getChatMessagesSincePositionMobileData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.chatMessageFromPosition.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.messageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("getChatMessagesSincePositionMobileData"))
              .build();
        }
      }
    }
    return getGetChatMessagesSincePositionMobileDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.messagesBetweenPosition,
      io.grpc.examples.backendserver.messageResponse> getGetMessagesBetweenPositionsMobileDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getMessagesBetweenPositionsMobileData",
      requestType = io.grpc.examples.backendserver.messagesBetweenPosition.class,
      responseType = io.grpc.examples.backendserver.messageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.messagesBetweenPosition,
      io.grpc.examples.backendserver.messageResponse> getGetMessagesBetweenPositionsMobileDataMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.messagesBetweenPosition, io.grpc.examples.backendserver.messageResponse> getGetMessagesBetweenPositionsMobileDataMethod;
    if ((getGetMessagesBetweenPositionsMobileDataMethod = ServerGrpc.getGetMessagesBetweenPositionsMobileDataMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getGetMessagesBetweenPositionsMobileDataMethod = ServerGrpc.getGetMessagesBetweenPositionsMobileDataMethod) == null) {
          ServerGrpc.getGetMessagesBetweenPositionsMobileDataMethod = getGetMessagesBetweenPositionsMobileDataMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.messagesBetweenPosition, io.grpc.examples.backendserver.messageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getMessagesBetweenPositionsMobileData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.messagesBetweenPosition.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.messageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("getMessagesBetweenPositionsMobileData"))
              .build();
        }
      }
    }
    return getGetMessagesBetweenPositionsMobileDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.listenToChatroom,
      io.grpc.examples.backendserver.messageResponse> getListenToChatroomsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listenToChatrooms",
      requestType = io.grpc.examples.backendserver.listenToChatroom.class,
      responseType = io.grpc.examples.backendserver.messageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.listenToChatroom,
      io.grpc.examples.backendserver.messageResponse> getListenToChatroomsMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.listenToChatroom, io.grpc.examples.backendserver.messageResponse> getListenToChatroomsMethod;
    if ((getListenToChatroomsMethod = ServerGrpc.getListenToChatroomsMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getListenToChatroomsMethod = ServerGrpc.getListenToChatroomsMethod) == null) {
          ServerGrpc.getListenToChatroomsMethod = getListenToChatroomsMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.listenToChatroom, io.grpc.examples.backendserver.messageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listenToChatrooms"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.listenToChatroom.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.messageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("listenToChatrooms"))
              .build();
        }
      }
    }
    return getListenToChatroomsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.getMessagePosition,
      io.grpc.examples.backendserver.messageResponse> getGetMessageAtPositionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getMessageAtPosition",
      requestType = io.grpc.examples.backendserver.getMessagePosition.class,
      responseType = io.grpc.examples.backendserver.messageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.getMessagePosition,
      io.grpc.examples.backendserver.messageResponse> getGetMessageAtPositionMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.getMessagePosition, io.grpc.examples.backendserver.messageResponse> getGetMessageAtPositionMethod;
    if ((getGetMessageAtPositionMethod = ServerGrpc.getGetMessageAtPositionMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getGetMessageAtPositionMethod = ServerGrpc.getGetMessageAtPositionMethod) == null) {
          ServerGrpc.getGetMessageAtPositionMethod = getGetMessageAtPositionMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.getMessagePosition, io.grpc.examples.backendserver.messageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getMessageAtPosition"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.getMessagePosition.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.messageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("getMessageAtPosition"))
              .build();
        }
      }
    }
    return getGetMessageAtPositionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.backendserver.listenToChatroom,
      io.grpc.examples.backendserver.messageResponse> getListenToChatroomsMobileDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listenToChatroomsMobileData",
      requestType = io.grpc.examples.backendserver.listenToChatroom.class,
      responseType = io.grpc.examples.backendserver.messageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<io.grpc.examples.backendserver.listenToChatroom,
      io.grpc.examples.backendserver.messageResponse> getListenToChatroomsMobileDataMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.backendserver.listenToChatroom, io.grpc.examples.backendserver.messageResponse> getListenToChatroomsMobileDataMethod;
    if ((getListenToChatroomsMobileDataMethod = ServerGrpc.getListenToChatroomsMobileDataMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getListenToChatroomsMobileDataMethod = ServerGrpc.getListenToChatroomsMobileDataMethod) == null) {
          ServerGrpc.getListenToChatroomsMobileDataMethod = getListenToChatroomsMobileDataMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.backendserver.listenToChatroom, io.grpc.examples.backendserver.messageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listenToChatroomsMobileData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.listenToChatroom.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.backendserver.messageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("listenToChatroomsMobileData"))
              .build();
        }
      }
    }
    return getListenToChatroomsMobileDataMethod;
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
    public void registerUser(io.grpc.examples.backendserver.registerUserRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.registerUserReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterUserMethod(), responseObserver);
    }

    /**
     */
    public void loginUser(io.grpc.examples.backendserver.loginUserRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.loginUserReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoginUserMethod(), responseObserver);
    }

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

    /**
     */
    public void getChatMessagesSincePosition(io.grpc.examples.backendserver.chatMessageFromPosition request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetChatMessagesSincePositionMethod(), responseObserver);
    }

    /**
     */
    public void createChat(io.grpc.examples.backendserver.CreateChatRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.CreateChatReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateChatMethod(), responseObserver);
    }

    /**
     */
    public void leaveChat(io.grpc.examples.backendserver.LeaveChatRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.LeaveChatReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLeaveChatMethod(), responseObserver);
    }

    /**
     */
    public void addUserToChat(io.grpc.examples.backendserver.AddUserToChatRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.AddUserToChatReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddUserToChatMethod(), responseObserver);
    }

    /**
     */
    public void getChatMembers(io.grpc.examples.backendserver.GetChatMembersRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.GetChatMembersReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetChatMembersMethod(), responseObserver);
    }

    /**
     */
    public void getJoinableChats(io.grpc.examples.backendserver.JoinableChatsRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.JoinableChatsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetJoinableChatsMethod(), responseObserver);
    }

    /**
     */
    public void getChatType(io.grpc.examples.backendserver.ChatTypeRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.ChatTypeReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetChatTypeMethod(), responseObserver);
    }

    /**
     */
    public void getAllChats(io.grpc.examples.backendserver.GetChatsRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.GetChatsReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllChatsMethod(), responseObserver);
    }

    /**
     */
    public void joinChat(io.grpc.examples.backendserver.JoinChatRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.JoinChatReply> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getJoinChatMethod(), responseObserver);
    }

    /**
     */
    public void getLastNMessagesFromChat(io.grpc.examples.backendserver.NMessagesFromChat request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLastNMessagesFromChatMethod(), responseObserver);
    }

    /**
     */
    public void getChatMessagesSincePositionMobileData(io.grpc.examples.backendserver.chatMessageFromPosition request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetChatMessagesSincePositionMobileDataMethod(), responseObserver);
    }

    /**
     */
    public void getMessagesBetweenPositionsMobileData(io.grpc.examples.backendserver.messagesBetweenPosition request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMessagesBetweenPositionsMobileDataMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.listenToChatroom> listenToChatrooms(
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getListenToChatroomsMethod(), responseObserver);
    }

    /**
     */
    public void getMessageAtPosition(io.grpc.examples.backendserver.getMessagePosition request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMessageAtPositionMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.listenToChatroom> listenToChatroomsMobileData(
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getListenToChatroomsMobileDataMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRegisterUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.registerUserRequest,
                io.grpc.examples.backendserver.registerUserReply>(
                  this, METHODID_REGISTER_USER)))
          .addMethod(
            getLoginUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.loginUserRequest,
                io.grpc.examples.backendserver.loginUserReply>(
                  this, METHODID_LOGIN_USER)))
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
          .addMethod(
            getGetChatMessagesSincePositionMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.chatMessageFromPosition,
                io.grpc.examples.backendserver.messageResponse>(
                  this, METHODID_GET_CHAT_MESSAGES_SINCE_POSITION)))
          .addMethod(
            getCreateChatMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.CreateChatRequest,
                io.grpc.examples.backendserver.CreateChatReply>(
                  this, METHODID_CREATE_CHAT)))
          .addMethod(
            getLeaveChatMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.LeaveChatRequest,
                io.grpc.examples.backendserver.LeaveChatReply>(
                  this, METHODID_LEAVE_CHAT)))
          .addMethod(
            getAddUserToChatMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.AddUserToChatRequest,
                io.grpc.examples.backendserver.AddUserToChatReply>(
                  this, METHODID_ADD_USER_TO_CHAT)))
          .addMethod(
            getGetChatMembersMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.GetChatMembersRequest,
                io.grpc.examples.backendserver.GetChatMembersReply>(
                  this, METHODID_GET_CHAT_MEMBERS)))
          .addMethod(
            getGetJoinableChatsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.JoinableChatsRequest,
                io.grpc.examples.backendserver.JoinableChatsReply>(
                  this, METHODID_GET_JOINABLE_CHATS)))
          .addMethod(
            getGetChatTypeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.ChatTypeRequest,
                io.grpc.examples.backendserver.ChatTypeReply>(
                  this, METHODID_GET_CHAT_TYPE)))
          .addMethod(
            getGetAllChatsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.GetChatsRequest,
                io.grpc.examples.backendserver.GetChatsReply>(
                  this, METHODID_GET_ALL_CHATS)))
          .addMethod(
            getJoinChatMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.JoinChatRequest,
                io.grpc.examples.backendserver.JoinChatReply>(
                  this, METHODID_JOIN_CHAT)))
          .addMethod(
            getGetLastNMessagesFromChatMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.NMessagesFromChat,
                io.grpc.examples.backendserver.messageResponse>(
                  this, METHODID_GET_LAST_NMESSAGES_FROM_CHAT)))
          .addMethod(
            getGetChatMessagesSincePositionMobileDataMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.chatMessageFromPosition,
                io.grpc.examples.backendserver.messageResponse>(
                  this, METHODID_GET_CHAT_MESSAGES_SINCE_POSITION_MOBILE_DATA)))
          .addMethod(
            getGetMessagesBetweenPositionsMobileDataMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.messagesBetweenPosition,
                io.grpc.examples.backendserver.messageResponse>(
                  this, METHODID_GET_MESSAGES_BETWEEN_POSITIONS_MOBILE_DATA)))
          .addMethod(
            getListenToChatroomsMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.listenToChatroom,
                io.grpc.examples.backendserver.messageResponse>(
                  this, METHODID_LISTEN_TO_CHATROOMS)))
          .addMethod(
            getGetMessageAtPositionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.getMessagePosition,
                io.grpc.examples.backendserver.messageResponse>(
                  this, METHODID_GET_MESSAGE_AT_POSITION)))
          .addMethod(
            getListenToChatroomsMobileDataMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                io.grpc.examples.backendserver.listenToChatroom,
                io.grpc.examples.backendserver.messageResponse>(
                  this, METHODID_LISTEN_TO_CHATROOMS_MOBILE_DATA)))
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
    public void registerUser(io.grpc.examples.backendserver.registerUserRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.registerUserReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void loginUser(io.grpc.examples.backendserver.loginUserRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.loginUserReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoginUserMethod(), getCallOptions()), request, responseObserver);
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

    /**
     */
    public void getChatMessagesSincePosition(io.grpc.examples.backendserver.chatMessageFromPosition request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetChatMessagesSincePositionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createChat(io.grpc.examples.backendserver.CreateChatRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.CreateChatReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateChatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void leaveChat(io.grpc.examples.backendserver.LeaveChatRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.LeaveChatReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLeaveChatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addUserToChat(io.grpc.examples.backendserver.AddUserToChatRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.AddUserToChatReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddUserToChatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getChatMembers(io.grpc.examples.backendserver.GetChatMembersRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.GetChatMembersReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetChatMembersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getJoinableChats(io.grpc.examples.backendserver.JoinableChatsRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.JoinableChatsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetJoinableChatsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getChatType(io.grpc.examples.backendserver.ChatTypeRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.ChatTypeReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetChatTypeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllChats(io.grpc.examples.backendserver.GetChatsRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.GetChatsReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllChatsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void joinChat(io.grpc.examples.backendserver.JoinChatRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.JoinChatReply> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getJoinChatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLastNMessagesFromChat(io.grpc.examples.backendserver.NMessagesFromChat request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetLastNMessagesFromChatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getChatMessagesSincePositionMobileData(io.grpc.examples.backendserver.chatMessageFromPosition request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetChatMessagesSincePositionMobileDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getMessagesBetweenPositionsMobileData(io.grpc.examples.backendserver.messagesBetweenPosition request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetMessagesBetweenPositionsMobileDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.listenToChatroom> listenToChatrooms(
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getListenToChatroomsMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void getMessageAtPosition(io.grpc.examples.backendserver.getMessagePosition request,
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMessageAtPositionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.listenToChatroom> listenToChatroomsMobileData(
        io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getListenToChatroomsMobileDataMethod(), getCallOptions()), responseObserver);
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
    public io.grpc.examples.backendserver.registerUserReply registerUser(io.grpc.examples.backendserver.registerUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.backendserver.loginUserReply loginUser(io.grpc.examples.backendserver.loginUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoginUserMethod(), getCallOptions(), request);
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

    /**
     */
    public java.util.Iterator<io.grpc.examples.backendserver.messageResponse> getChatMessagesSincePosition(
        io.grpc.examples.backendserver.chatMessageFromPosition request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetChatMessagesSincePositionMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.backendserver.CreateChatReply createChat(io.grpc.examples.backendserver.CreateChatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateChatMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.backendserver.LeaveChatReply leaveChat(io.grpc.examples.backendserver.LeaveChatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLeaveChatMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.backendserver.AddUserToChatReply addUserToChat(io.grpc.examples.backendserver.AddUserToChatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddUserToChatMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.backendserver.GetChatMembersReply getChatMembers(io.grpc.examples.backendserver.GetChatMembersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetChatMembersMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.backendserver.JoinableChatsReply getJoinableChats(io.grpc.examples.backendserver.JoinableChatsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetJoinableChatsMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.backendserver.ChatTypeReply getChatType(io.grpc.examples.backendserver.ChatTypeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetChatTypeMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.backendserver.GetChatsReply getAllChats(io.grpc.examples.backendserver.GetChatsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllChatsMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.backendserver.JoinChatReply joinChat(io.grpc.examples.backendserver.JoinChatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getJoinChatMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<io.grpc.examples.backendserver.messageResponse> getLastNMessagesFromChat(
        io.grpc.examples.backendserver.NMessagesFromChat request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetLastNMessagesFromChatMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<io.grpc.examples.backendserver.messageResponse> getChatMessagesSincePositionMobileData(
        io.grpc.examples.backendserver.chatMessageFromPosition request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetChatMessagesSincePositionMobileDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<io.grpc.examples.backendserver.messageResponse> getMessagesBetweenPositionsMobileData(
        io.grpc.examples.backendserver.messagesBetweenPosition request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetMessagesBetweenPositionsMobileDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.backendserver.messageResponse getMessageAtPosition(io.grpc.examples.backendserver.getMessagePosition request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMessageAtPositionMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.registerUserReply> registerUser(
        io.grpc.examples.backendserver.registerUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.loginUserReply> loginUser(
        io.grpc.examples.backendserver.loginUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoginUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.messageResponse> sendMessage(
        io.grpc.examples.backendserver.sendingMessage request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.CreateChatReply> createChat(
        io.grpc.examples.backendserver.CreateChatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateChatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.LeaveChatReply> leaveChat(
        io.grpc.examples.backendserver.LeaveChatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLeaveChatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.AddUserToChatReply> addUserToChat(
        io.grpc.examples.backendserver.AddUserToChatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddUserToChatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.GetChatMembersReply> getChatMembers(
        io.grpc.examples.backendserver.GetChatMembersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetChatMembersMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.JoinableChatsReply> getJoinableChats(
        io.grpc.examples.backendserver.JoinableChatsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetJoinableChatsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.ChatTypeReply> getChatType(
        io.grpc.examples.backendserver.ChatTypeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetChatTypeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.GetChatsReply> getAllChats(
        io.grpc.examples.backendserver.GetChatsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllChatsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.JoinChatReply> joinChat(
        io.grpc.examples.backendserver.JoinChatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getJoinChatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.backendserver.messageResponse> getMessageAtPosition(
        io.grpc.examples.backendserver.getMessagePosition request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMessageAtPositionMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_USER = 0;
  private static final int METHODID_LOGIN_USER = 1;
  private static final int METHODID_SEND_MESSAGE = 2;
  private static final int METHODID_GET_ALL_CHAT_MESSAGES = 3;
  private static final int METHODID_GET_CHAT_MESSAGES_SINCE_POSITION = 4;
  private static final int METHODID_CREATE_CHAT = 5;
  private static final int METHODID_LEAVE_CHAT = 6;
  private static final int METHODID_ADD_USER_TO_CHAT = 7;
  private static final int METHODID_GET_CHAT_MEMBERS = 8;
  private static final int METHODID_GET_JOINABLE_CHATS = 9;
  private static final int METHODID_GET_CHAT_TYPE = 10;
  private static final int METHODID_GET_ALL_CHATS = 11;
  private static final int METHODID_JOIN_CHAT = 12;
  private static final int METHODID_GET_LAST_NMESSAGES_FROM_CHAT = 13;
  private static final int METHODID_GET_CHAT_MESSAGES_SINCE_POSITION_MOBILE_DATA = 14;
  private static final int METHODID_GET_MESSAGES_BETWEEN_POSITIONS_MOBILE_DATA = 15;
  private static final int METHODID_GET_MESSAGE_AT_POSITION = 16;
  private static final int METHODID_LISTEN_TO_CHATROOMS = 17;
  private static final int METHODID_LISTEN_TO_CHATROOMS_MOBILE_DATA = 18;

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
        case METHODID_REGISTER_USER:
          serviceImpl.registerUser((io.grpc.examples.backendserver.registerUserRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.registerUserReply>) responseObserver);
          break;
        case METHODID_LOGIN_USER:
          serviceImpl.loginUser((io.grpc.examples.backendserver.loginUserRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.loginUserReply>) responseObserver);
          break;
        case METHODID_SEND_MESSAGE:
          serviceImpl.sendMessage((io.grpc.examples.backendserver.sendingMessage) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse>) responseObserver);
          break;
        case METHODID_GET_ALL_CHAT_MESSAGES:
          serviceImpl.getAllChatMessages((io.grpc.examples.backendserver.chatMessageRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse>) responseObserver);
          break;
        case METHODID_GET_CHAT_MESSAGES_SINCE_POSITION:
          serviceImpl.getChatMessagesSincePosition((io.grpc.examples.backendserver.chatMessageFromPosition) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse>) responseObserver);
          break;
        case METHODID_CREATE_CHAT:
          serviceImpl.createChat((io.grpc.examples.backendserver.CreateChatRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.CreateChatReply>) responseObserver);
          break;
        case METHODID_LEAVE_CHAT:
          serviceImpl.leaveChat((io.grpc.examples.backendserver.LeaveChatRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.LeaveChatReply>) responseObserver);
          break;
        case METHODID_ADD_USER_TO_CHAT:
          serviceImpl.addUserToChat((io.grpc.examples.backendserver.AddUserToChatRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.AddUserToChatReply>) responseObserver);
          break;
        case METHODID_GET_CHAT_MEMBERS:
          serviceImpl.getChatMembers((io.grpc.examples.backendserver.GetChatMembersRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.GetChatMembersReply>) responseObserver);
          break;
        case METHODID_GET_JOINABLE_CHATS:
          serviceImpl.getJoinableChats((io.grpc.examples.backendserver.JoinableChatsRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.JoinableChatsReply>) responseObserver);
          break;
        case METHODID_GET_CHAT_TYPE:
          serviceImpl.getChatType((io.grpc.examples.backendserver.ChatTypeRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.ChatTypeReply>) responseObserver);
          break;
        case METHODID_GET_ALL_CHATS:
          serviceImpl.getAllChats((io.grpc.examples.backendserver.GetChatsRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.GetChatsReply>) responseObserver);
          break;
        case METHODID_JOIN_CHAT:
          serviceImpl.joinChat((io.grpc.examples.backendserver.JoinChatRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.JoinChatReply>) responseObserver);
          break;
        case METHODID_GET_LAST_NMESSAGES_FROM_CHAT:
          serviceImpl.getLastNMessagesFromChat((io.grpc.examples.backendserver.NMessagesFromChat) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse>) responseObserver);
          break;
        case METHODID_GET_CHAT_MESSAGES_SINCE_POSITION_MOBILE_DATA:
          serviceImpl.getChatMessagesSincePositionMobileData((io.grpc.examples.backendserver.chatMessageFromPosition) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse>) responseObserver);
          break;
        case METHODID_GET_MESSAGES_BETWEEN_POSITIONS_MOBILE_DATA:
          serviceImpl.getMessagesBetweenPositionsMobileData((io.grpc.examples.backendserver.messagesBetweenPosition) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse>) responseObserver);
          break;
        case METHODID_GET_MESSAGE_AT_POSITION:
          serviceImpl.getMessageAtPosition((io.grpc.examples.backendserver.getMessagePosition) request,
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
        case METHODID_LISTEN_TO_CHATROOMS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.listenToChatrooms(
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse>) responseObserver);
        case METHODID_LISTEN_TO_CHATROOMS_MOBILE_DATA:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.listenToChatroomsMobileData(
              (io.grpc.stub.StreamObserver<io.grpc.examples.backendserver.messageResponse>) responseObserver);
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
              .addMethod(getRegisterUserMethod())
              .addMethod(getLoginUserMethod())
              .addMethod(getSendMessageMethod())
              .addMethod(getGetAllChatMessagesMethod())
              .addMethod(getGetChatMessagesSincePositionMethod())
              .addMethod(getCreateChatMethod())
              .addMethod(getLeaveChatMethod())
              .addMethod(getAddUserToChatMethod())
              .addMethod(getGetChatMembersMethod())
              .addMethod(getGetJoinableChatsMethod())
              .addMethod(getGetChatTypeMethod())
              .addMethod(getGetAllChatsMethod())
              .addMethod(getJoinChatMethod())
              .addMethod(getGetLastNMessagesFromChatMethod())
              .addMethod(getGetChatMessagesSincePositionMobileDataMethod())
              .addMethod(getGetMessagesBetweenPositionsMobileDataMethod())
              .addMethod(getListenToChatroomsMethod())
              .addMethod(getGetMessageAtPositionMethod())
              .addMethod(getListenToChatroomsMobileDataMethod())
              .build();
        }
      }
    }
    return result;
  }
}
