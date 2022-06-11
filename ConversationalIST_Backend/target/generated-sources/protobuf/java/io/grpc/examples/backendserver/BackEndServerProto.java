// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: backendServer.proto

package io.grpc.examples.backendserver;

public final class BackEndServerProto {
  private BackEndServerProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_registerUserRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_registerUserRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_registerUserReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_registerUserReply_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_listenToChatroom_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_listenToChatroom_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_messagesBetweenPosition_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_messagesBetweenPosition_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_chatMessageFromPosition_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_chatMessageFromPosition_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_chatMessageRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_chatMessageRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_sendingMessage_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_sendingMessage_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_messageResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_messageResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_CreateChatRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_CreateChatRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_CreateChatReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_CreateChatReply_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_Location_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_Location_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_NMessagesFromChat_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_NMessagesFromChat_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_JoinableChatsRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_JoinableChatsRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_JoinableChatsReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_JoinableChatsReply_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_JoinChatRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_JoinChatRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_JoinChatReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_JoinChatReply_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_GetChatsRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_GetChatsRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_GetChatsReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_GetChatsReply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023backendServer.proto\022\nhelloworld\"#\n\023reg" +
      "isterUserRequest\022\014\n\004user\030\001 \001(\t\" \n\021regist" +
      "erUserReply\022\013\n\003ack\030\001 \001(\t\"$\n\020listenToChat" +
      "room\022\020\n\010chatroom\030\001 \001(\t\"d\n\027messagesBetwee" +
      "nPosition\022\020\n\010chatroom\030\001 \001(\t\022\035\n\025positionO" +
      "fLastMessage\030\002 \001(\005\022\030\n\020numberOfMessages\030\003" +
      " \001(\005\"J\n\027chatMessageFromPosition\022\020\n\010chatr" +
      "oom\030\001 \001(\t\022\035\n\025positionOfLastMessage\030\002 \001(\005" +
      "\"&\n\022chatMessageRequest\022\020\n\010chatroom\030\001 \001(\t" +
      "\"c\n\016sendingMessage\022\020\n\010username\030\001 \001(\t\022\021\n\t" +
      "timestamp\030\002 \001(\t\022\020\n\010chatroom\030\003 \001(\t\022\014\n\004dat" +
      "a\030\004 \001(\t\022\014\n\004type\030\005 \001(\005\"v\n\017messageResponse" +
      "\022\020\n\010username\030\001 \001(\t\022\021\n\ttimestamp\030\002 \001(\t\022\020\n" +
      "\010chatroom\030\003 \001(\t\022\014\n\004data\030\004 \001(\t\022\014\n\004type\030\005 " +
      "\001(\005\022\020\n\010position\030\006 \001(\005\"\206\001\n\021CreateChatRequ" +
      "est\022\014\n\004user\030\001 \001(\t\022\025\n\rchatroom_name\030\002 \001(\t" +
      "\022\024\n\014type_of_chat\030\003 \001(\t\022&\n\010location\030\004 \001(\013" +
      "2\024.helloworld.Location\022\016\n\006radius\030\005 \001(\t\"\036" +
      "\n\017CreateChatReply\022\013\n\003ack\030\001 \001(\t\"/\n\010Locati" +
      "on\022\020\n\010latitude\030\001 \001(\t\022\021\n\tlongitude\030\002 \001(\t\"" +
      "?\n\021NMessagesFromChat\022\020\n\010chatroom\030\001 \001(\t\022\030" +
      "\n\020numberOfMessages\030\002 \001(\005\"$\n\024JoinableChat" +
      "sRequest\022\014\n\004user\030\001 \001(\t\"#\n\022JoinableChatsR" +
      "eply\022\r\n\005chats\030\001 \003(\t\"2\n\017JoinChatRequest\022\014" +
      "\n\004user\030\001 \001(\t\022\021\n\tchat_name\030\002 \001(\t\"\034\n\rJoinC" +
      "hatReply\022\013\n\003ack\030\001 \001(\t\"\037\n\017GetChatsRequest" +
      "\022\014\n\004user\030\001 \001(\t\"#\n\rGetChatsReply\022\022\n\nuser_" +
      "chats\030\001 \003(\t2\245\010\n\006Server\022P\n\014registerUser\022\037" +
      ".helloworld.registerUserRequest\032\035.hellow" +
      "orld.registerUserReply\"\000\022H\n\013sendMessage\022" +
      "\032.helloworld.sendingMessage\032\033.helloworld" +
      ".messageResponse\"\000\022U\n\022getAllChatMessages" +
      "\022\036.helloworld.chatMessageRequest\032\033.hello" +
      "world.messageResponse\"\0000\001\022d\n\034getChatMess" +
      "agesSincePosition\022#.helloworld.chatMessa" +
      "geFromPosition\032\033.helloworld.messageRespo" +
      "nse\"\0000\001\022J\n\ncreateChat\022\035.helloworld.Creat" +
      "eChatRequest\032\033.helloworld.CreateChatRepl" +
      "y\"\000\022V\n\020getJoinableChats\022 .helloworld.Joi" +
      "nableChatsRequest\032\036.helloworld.JoinableC" +
      "hatsReply\"\000\022G\n\013getAllChats\022\033.helloworld." +
      "GetChatsRequest\032\031.helloworld.GetChatsRep" +
      "ly\"\000\022D\n\010joinChat\022\033.helloworld.JoinChatRe" +
      "quest\032\031.helloworld.JoinChatReply\"\000\022Z\n\030ge" +
      "tLastNMessagesFromChat\022\035.helloworld.NMes" +
      "sagesFromChat\032\033.helloworld.messageRespon" +
      "se\"\0000\001\022n\n&getChatMessagesSincePositionMo" +
      "bileData\022#.helloworld.chatMessageFromPos" +
      "ition\032\033.helloworld.messageResponse\"\0000\001\022m" +
      "\n%getMessagesBetweenPositionsMobileData\022" +
      "#.helloworld.messagesBetweenPosition\032\033.h" +
      "elloworld.messageResponse\"\0000\001\022T\n\021listenT" +
      "oChatrooms\022\034.helloworld.listenToChatroom" +
      "\032\033.helloworld.messageResponse\"\000(\0010\001B<\n\036i" +
      "o.grpc.examples.backendserverB\022BackEndSe" +
      "rverProtoP\001\242\002\003HLWb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_helloworld_registerUserRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_helloworld_registerUserRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_registerUserRequest_descriptor,
        new java.lang.String[] { "User", });
    internal_static_helloworld_registerUserReply_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_helloworld_registerUserReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_registerUserReply_descriptor,
        new java.lang.String[] { "Ack", });
    internal_static_helloworld_listenToChatroom_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_helloworld_listenToChatroom_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_listenToChatroom_descriptor,
        new java.lang.String[] { "Chatroom", });
    internal_static_helloworld_messagesBetweenPosition_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_helloworld_messagesBetweenPosition_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_messagesBetweenPosition_descriptor,
        new java.lang.String[] { "Chatroom", "PositionOfLastMessage", "NumberOfMessages", });
    internal_static_helloworld_chatMessageFromPosition_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_helloworld_chatMessageFromPosition_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_chatMessageFromPosition_descriptor,
        new java.lang.String[] { "Chatroom", "PositionOfLastMessage", });
    internal_static_helloworld_chatMessageRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_helloworld_chatMessageRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_chatMessageRequest_descriptor,
        new java.lang.String[] { "Chatroom", });
    internal_static_helloworld_sendingMessage_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_helloworld_sendingMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_sendingMessage_descriptor,
        new java.lang.String[] { "Username", "Timestamp", "Chatroom", "Data", "Type", });
    internal_static_helloworld_messageResponse_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_helloworld_messageResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_messageResponse_descriptor,
        new java.lang.String[] { "Username", "Timestamp", "Chatroom", "Data", "Type", "Position", });
    internal_static_helloworld_CreateChatRequest_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_helloworld_CreateChatRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_CreateChatRequest_descriptor,
        new java.lang.String[] { "User", "ChatroomName", "TypeOfChat", "Location", "Radius", });
    internal_static_helloworld_CreateChatReply_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_helloworld_CreateChatReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_CreateChatReply_descriptor,
        new java.lang.String[] { "Ack", });
    internal_static_helloworld_Location_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_helloworld_Location_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_Location_descriptor,
        new java.lang.String[] { "Latitude", "Longitude", });
    internal_static_helloworld_NMessagesFromChat_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_helloworld_NMessagesFromChat_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_NMessagesFromChat_descriptor,
        new java.lang.String[] { "Chatroom", "NumberOfMessages", });
    internal_static_helloworld_JoinableChatsRequest_descriptor =
      getDescriptor().getMessageTypes().get(12);
    internal_static_helloworld_JoinableChatsRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_JoinableChatsRequest_descriptor,
        new java.lang.String[] { "User", });
    internal_static_helloworld_JoinableChatsReply_descriptor =
      getDescriptor().getMessageTypes().get(13);
    internal_static_helloworld_JoinableChatsReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_JoinableChatsReply_descriptor,
        new java.lang.String[] { "Chats", });
    internal_static_helloworld_JoinChatRequest_descriptor =
      getDescriptor().getMessageTypes().get(14);
    internal_static_helloworld_JoinChatRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_JoinChatRequest_descriptor,
        new java.lang.String[] { "User", "ChatName", });
    internal_static_helloworld_JoinChatReply_descriptor =
      getDescriptor().getMessageTypes().get(15);
    internal_static_helloworld_JoinChatReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_JoinChatReply_descriptor,
        new java.lang.String[] { "Ack", });
    internal_static_helloworld_GetChatsRequest_descriptor =
      getDescriptor().getMessageTypes().get(16);
    internal_static_helloworld_GetChatsRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_GetChatsRequest_descriptor,
        new java.lang.String[] { "User", });
    internal_static_helloworld_GetChatsReply_descriptor =
      getDescriptor().getMessageTypes().get(17);
    internal_static_helloworld_GetChatsReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_GetChatsReply_descriptor,
        new java.lang.String[] { "UserChats", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
