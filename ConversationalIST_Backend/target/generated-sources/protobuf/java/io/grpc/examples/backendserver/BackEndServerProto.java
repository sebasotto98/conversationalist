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

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023backendServer.proto\022\nhelloworld\"J\n\027cha" +
      "tMessageFromPosition\022\020\n\010chatroom\030\001 \001(\t\022\035" +
      "\n\025positionOfLastMessage\030\002 \001(\005\"&\n\022chatMes" +
      "sageRequest\022\020\n\010chatroom\030\001 \001(\t\"c\n\016sending" +
      "Message\022\020\n\010username\030\001 \001(\t\022\021\n\ttimestamp\030\002" +
      " \001(\t\022\020\n\010chatroom\030\003 \001(\t\022\014\n\004data\030\004 \001(\t\022\014\n\004" +
      "type\030\005 \001(\005\"d\n\017messageResponse\022\020\n\010usernam" +
      "e\030\001 \001(\t\022\021\n\ttimestamp\030\002 \001(\t\022\020\n\010chatroom\030\003" +
      " \001(\t\022\014\n\004data\030\004 \001(\t\022\014\n\004type\030\005 \001(\0052\217\002\n\006Ser" +
      "ver\022H\n\013sendMessage\022\032.helloworld.sendingM" +
      "essage\032\033.helloworld.messageResponse\"\000\022U\n" +
      "\022getAllChatMessages\022\036.helloworld.chatMes" +
      "sageRequest\032\033.helloworld.messageResponse" +
      "\"\0000\001\022d\n\034getChatMessagesSincePosition\022#.h" +
      "elloworld.chatMessageFromPosition\032\033.hell" +
      "oworld.messageResponse\"\0000\001B<\n\036io.grpc.ex" +
      "amples.backendserverB\022BackEndServerProto" +
      "P\001\242\002\003HLWb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_helloworld_chatMessageFromPosition_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_helloworld_chatMessageFromPosition_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_chatMessageFromPosition_descriptor,
        new java.lang.String[] { "Chatroom", "PositionOfLastMessage", });
    internal_static_helloworld_chatMessageRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_helloworld_chatMessageRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_chatMessageRequest_descriptor,
        new java.lang.String[] { "Chatroom", });
    internal_static_helloworld_sendingMessage_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_helloworld_sendingMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_sendingMessage_descriptor,
        new java.lang.String[] { "Username", "Timestamp", "Chatroom", "Data", "Type", });
    internal_static_helloworld_messageResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_helloworld_messageResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_messageResponse_descriptor,
        new java.lang.String[] { "Username", "Timestamp", "Chatroom", "Data", "Type", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
