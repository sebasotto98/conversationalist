// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: backendServer.proto

package io.grpc.examples.backendserver;

public interface messageResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:helloworld.messageResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string username = 1;</code>
   * @return The username.
   */
  java.lang.String getUsername();
  /**
   * <code>string username = 1;</code>
   * @return The bytes for username.
   */
  com.google.protobuf.ByteString
      getUsernameBytes();

  /**
   * <code>string timestamp = 2;</code>
   * @return The timestamp.
   */
  java.lang.String getTimestamp();
  /**
   * <code>string timestamp = 2;</code>
   * @return The bytes for timestamp.
   */
  com.google.protobuf.ByteString
      getTimestampBytes();

  /**
   * <code>string chatroom = 3;</code>
   * @return The chatroom.
   */
  java.lang.String getChatroom();
  /**
   * <code>string chatroom = 3;</code>
   * @return The bytes for chatroom.
   */
  com.google.protobuf.ByteString
      getChatroomBytes();

  /**
   * <code>string data = 4;</code>
   * @return The data.
   */
  java.lang.String getData();
  /**
   * <code>string data = 4;</code>
   * @return The bytes for data.
   */
  com.google.protobuf.ByteString
      getDataBytes();

  /**
   * <code>int32 type = 5;</code>
   * @return The type.
   */
  int getType();

  /**
   * <code>int32 position = 6;</code>
   * @return The position.
   */
  int getPosition();
}
