// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: backendServer.proto

package io.grpc.examples.backendserver;

/**
 * Protobuf type {@code helloworld.NMessagesFromChat}
 */
public final class NMessagesFromChat extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:helloworld.NMessagesFromChat)
    NMessagesFromChatOrBuilder {
private static final long serialVersionUID = 0L;
  // Use NMessagesFromChat.newBuilder() to construct.
  private NMessagesFromChat(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private NMessagesFromChat() {
    chatroom_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new NMessagesFromChat();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private NMessagesFromChat(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            chatroom_ = s;
            break;
          }
          case 16: {

            numberOfMessages_ = input.readInt32();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.grpc.examples.backendserver.BackEndServerProto.internal_static_helloworld_NMessagesFromChat_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.grpc.examples.backendserver.BackEndServerProto.internal_static_helloworld_NMessagesFromChat_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.grpc.examples.backendserver.NMessagesFromChat.class, io.grpc.examples.backendserver.NMessagesFromChat.Builder.class);
  }

  public static final int CHATROOM_FIELD_NUMBER = 1;
  private volatile java.lang.Object chatroom_;
  /**
   * <code>string chatroom = 1;</code>
   * @return The chatroom.
   */
  @java.lang.Override
  public java.lang.String getChatroom() {
    java.lang.Object ref = chatroom_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      chatroom_ = s;
      return s;
    }
  }
  /**
   * <code>string chatroom = 1;</code>
   * @return The bytes for chatroom.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getChatroomBytes() {
    java.lang.Object ref = chatroom_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      chatroom_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int NUMBEROFMESSAGES_FIELD_NUMBER = 2;
  private int numberOfMessages_;
  /**
   * <code>int32 numberOfMessages = 2;</code>
   * @return The numberOfMessages.
   */
  @java.lang.Override
  public int getNumberOfMessages() {
    return numberOfMessages_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getChatroomBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, chatroom_);
    }
    if (numberOfMessages_ != 0) {
      output.writeInt32(2, numberOfMessages_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getChatroomBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, chatroom_);
    }
    if (numberOfMessages_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, numberOfMessages_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof io.grpc.examples.backendserver.NMessagesFromChat)) {
      return super.equals(obj);
    }
    io.grpc.examples.backendserver.NMessagesFromChat other = (io.grpc.examples.backendserver.NMessagesFromChat) obj;

    if (!getChatroom()
        .equals(other.getChatroom())) return false;
    if (getNumberOfMessages()
        != other.getNumberOfMessages()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + CHATROOM_FIELD_NUMBER;
    hash = (53 * hash) + getChatroom().hashCode();
    hash = (37 * hash) + NUMBEROFMESSAGES_FIELD_NUMBER;
    hash = (53 * hash) + getNumberOfMessages();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.grpc.examples.backendserver.NMessagesFromChat parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.grpc.examples.backendserver.NMessagesFromChat parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.grpc.examples.backendserver.NMessagesFromChat parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.grpc.examples.backendserver.NMessagesFromChat parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.grpc.examples.backendserver.NMessagesFromChat parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.grpc.examples.backendserver.NMessagesFromChat parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.grpc.examples.backendserver.NMessagesFromChat parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.grpc.examples.backendserver.NMessagesFromChat parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.grpc.examples.backendserver.NMessagesFromChat parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static io.grpc.examples.backendserver.NMessagesFromChat parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.grpc.examples.backendserver.NMessagesFromChat parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static io.grpc.examples.backendserver.NMessagesFromChat parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(io.grpc.examples.backendserver.NMessagesFromChat prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code helloworld.NMessagesFromChat}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:helloworld.NMessagesFromChat)
      io.grpc.examples.backendserver.NMessagesFromChatOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.grpc.examples.backendserver.BackEndServerProto.internal_static_helloworld_NMessagesFromChat_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.grpc.examples.backendserver.BackEndServerProto.internal_static_helloworld_NMessagesFromChat_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.grpc.examples.backendserver.NMessagesFromChat.class, io.grpc.examples.backendserver.NMessagesFromChat.Builder.class);
    }

    // Construct using io.grpc.examples.backendserver.NMessagesFromChat.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      chatroom_ = "";

      numberOfMessages_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.grpc.examples.backendserver.BackEndServerProto.internal_static_helloworld_NMessagesFromChat_descriptor;
    }

    @java.lang.Override
    public io.grpc.examples.backendserver.NMessagesFromChat getDefaultInstanceForType() {
      return io.grpc.examples.backendserver.NMessagesFromChat.getDefaultInstance();
    }

    @java.lang.Override
    public io.grpc.examples.backendserver.NMessagesFromChat build() {
      io.grpc.examples.backendserver.NMessagesFromChat result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.grpc.examples.backendserver.NMessagesFromChat buildPartial() {
      io.grpc.examples.backendserver.NMessagesFromChat result = new io.grpc.examples.backendserver.NMessagesFromChat(this);
      result.chatroom_ = chatroom_;
      result.numberOfMessages_ = numberOfMessages_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.grpc.examples.backendserver.NMessagesFromChat) {
        return mergeFrom((io.grpc.examples.backendserver.NMessagesFromChat)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.grpc.examples.backendserver.NMessagesFromChat other) {
      if (other == io.grpc.examples.backendserver.NMessagesFromChat.getDefaultInstance()) return this;
      if (!other.getChatroom().isEmpty()) {
        chatroom_ = other.chatroom_;
        onChanged();
      }
      if (other.getNumberOfMessages() != 0) {
        setNumberOfMessages(other.getNumberOfMessages());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      io.grpc.examples.backendserver.NMessagesFromChat parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (io.grpc.examples.backendserver.NMessagesFromChat) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object chatroom_ = "";
    /**
     * <code>string chatroom = 1;</code>
     * @return The chatroom.
     */
    public java.lang.String getChatroom() {
      java.lang.Object ref = chatroom_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        chatroom_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string chatroom = 1;</code>
     * @return The bytes for chatroom.
     */
    public com.google.protobuf.ByteString
        getChatroomBytes() {
      java.lang.Object ref = chatroom_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        chatroom_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string chatroom = 1;</code>
     * @param value The chatroom to set.
     * @return This builder for chaining.
     */
    public Builder setChatroom(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      chatroom_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string chatroom = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearChatroom() {
      
      chatroom_ = getDefaultInstance().getChatroom();
      onChanged();
      return this;
    }
    /**
     * <code>string chatroom = 1;</code>
     * @param value The bytes for chatroom to set.
     * @return This builder for chaining.
     */
    public Builder setChatroomBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      chatroom_ = value;
      onChanged();
      return this;
    }

    private int numberOfMessages_ ;
    /**
     * <code>int32 numberOfMessages = 2;</code>
     * @return The numberOfMessages.
     */
    @java.lang.Override
    public int getNumberOfMessages() {
      return numberOfMessages_;
    }
    /**
     * <code>int32 numberOfMessages = 2;</code>
     * @param value The numberOfMessages to set.
     * @return This builder for chaining.
     */
    public Builder setNumberOfMessages(int value) {
      
      numberOfMessages_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 numberOfMessages = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearNumberOfMessages() {
      
      numberOfMessages_ = 0;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:helloworld.NMessagesFromChat)
  }

  // @@protoc_insertion_point(class_scope:helloworld.NMessagesFromChat)
  private static final io.grpc.examples.backendserver.NMessagesFromChat DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.grpc.examples.backendserver.NMessagesFromChat();
  }

  public static io.grpc.examples.backendserver.NMessagesFromChat getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<NMessagesFromChat>
      PARSER = new com.google.protobuf.AbstractParser<NMessagesFromChat>() {
    @java.lang.Override
    public NMessagesFromChat parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new NMessagesFromChat(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<NMessagesFromChat> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<NMessagesFromChat> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.grpc.examples.backendserver.NMessagesFromChat getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

