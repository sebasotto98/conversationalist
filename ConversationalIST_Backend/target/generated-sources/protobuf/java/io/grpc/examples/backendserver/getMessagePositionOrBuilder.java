// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: backendServer.proto

package io.grpc.examples.backendserver;

public interface getMessagePositionOrBuilder extends
    // @@protoc_insertion_point(interface_extends:helloworld.getMessagePosition)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string chatroom = 1;</code>
   * @return The chatroom.
   */
  java.lang.String getChatroom();
  /**
   * <code>string chatroom = 1;</code>
   * @return The bytes for chatroom.
   */
  com.google.protobuf.ByteString
      getChatroomBytes();

  /**
   * <code>int32 position = 2;</code>
   * @return The position.
   */
  int getPosition();
}