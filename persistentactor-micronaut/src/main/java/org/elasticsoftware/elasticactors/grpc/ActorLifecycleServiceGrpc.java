package org.elasticsoftware.elasticactors.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.37.1)",
    comments = "Source: services.proto")
public final class ActorLifecycleServiceGrpc {

  private ActorLifecycleServiceGrpc() {}

  public static final String SERVICE_NAME = "org.elasticsoftware.elasticactors.grpc.ActorLifecycleService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.elasticsoftware.elasticactors.grpc.HandleMessageRequest,
      org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse> getHandleMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "HandleMessage",
      requestType = org.elasticsoftware.elasticactors.grpc.HandleMessageRequest.class,
      responseType = org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.elasticsoftware.elasticactors.grpc.HandleMessageRequest,
      org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse> getHandleMessageMethod() {
    io.grpc.MethodDescriptor<org.elasticsoftware.elasticactors.grpc.HandleMessageRequest, org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse> getHandleMessageMethod;
    if ((getHandleMessageMethod = ActorLifecycleServiceGrpc.getHandleMessageMethod) == null) {
      synchronized (ActorLifecycleServiceGrpc.class) {
        if ((getHandleMessageMethod = ActorLifecycleServiceGrpc.getHandleMessageMethod) == null) {
          ActorLifecycleServiceGrpc.getHandleMessageMethod = getHandleMessageMethod =
              io.grpc.MethodDescriptor.<org.elasticsoftware.elasticactors.grpc.HandleMessageRequest, org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "HandleMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.elasticsoftware.elasticactors.grpc.HandleMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ActorLifecycleServiceMethodDescriptorSupplier("HandleMessage"))
              .build();
        }
      }
    }
    return getHandleMessageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ActorLifecycleServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ActorLifecycleServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ActorLifecycleServiceStub>() {
        @java.lang.Override
        public ActorLifecycleServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ActorLifecycleServiceStub(channel, callOptions);
        }
      };
    return ActorLifecycleServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ActorLifecycleServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ActorLifecycleServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ActorLifecycleServiceBlockingStub>() {
        @java.lang.Override
        public ActorLifecycleServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ActorLifecycleServiceBlockingStub(channel, callOptions);
        }
      };
    return ActorLifecycleServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ActorLifecycleServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ActorLifecycleServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ActorLifecycleServiceFutureStub>() {
        @java.lang.Override
        public ActorLifecycleServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ActorLifecycleServiceFutureStub(channel, callOptions);
        }
      };
    return ActorLifecycleServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The service definition.
   * </pre>
   */
  public static abstract class ActorLifecycleServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * handle incoming message
     * </pre>
     */
    public void handleMessage(org.elasticsoftware.elasticactors.grpc.HandleMessageRequest request,
        io.grpc.stub.StreamObserver<org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHandleMessageMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHandleMessageMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                org.elasticsoftware.elasticactors.grpc.HandleMessageRequest,
                org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse>(
                  this, METHODID_HANDLE_MESSAGE)))
          .build();
    }
  }

  /**
   * <pre>
   * The service definition.
   * </pre>
   */
  public static final class ActorLifecycleServiceStub extends io.grpc.stub.AbstractAsyncStub<ActorLifecycleServiceStub> {
    private ActorLifecycleServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ActorLifecycleServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ActorLifecycleServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * handle incoming message
     * </pre>
     */
    public void handleMessage(org.elasticsoftware.elasticactors.grpc.HandleMessageRequest request,
        io.grpc.stub.StreamObserver<org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHandleMessageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The service definition.
   * </pre>
   */
  public static final class ActorLifecycleServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ActorLifecycleServiceBlockingStub> {
    private ActorLifecycleServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ActorLifecycleServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ActorLifecycleServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * handle incoming message
     * </pre>
     */
    public org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse handleMessage(org.elasticsoftware.elasticactors.grpc.HandleMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHandleMessageMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The service definition.
   * </pre>
   */
  public static final class ActorLifecycleServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ActorLifecycleServiceFutureStub> {
    private ActorLifecycleServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ActorLifecycleServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ActorLifecycleServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * handle incoming message
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse> handleMessage(
        org.elasticsoftware.elasticactors.grpc.HandleMessageRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHandleMessageMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HANDLE_MESSAGE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ActorLifecycleServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ActorLifecycleServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HANDLE_MESSAGE:
          serviceImpl.handleMessage((org.elasticsoftware.elasticactors.grpc.HandleMessageRequest) request,
              (io.grpc.stub.StreamObserver<org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse>) responseObserver);
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

  private static abstract class ActorLifecycleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ActorLifecycleServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.elasticsoftware.elasticactors.grpc.Services.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ActorLifecycleService");
    }
  }

  private static final class ActorLifecycleServiceFileDescriptorSupplier
      extends ActorLifecycleServiceBaseDescriptorSupplier {
    ActorLifecycleServiceFileDescriptorSupplier() {}
  }

  private static final class ActorLifecycleServiceMethodDescriptorSupplier
      extends ActorLifecycleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ActorLifecycleServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ActorLifecycleServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ActorLifecycleServiceFileDescriptorSupplier())
              .addMethod(getHandleMessageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
