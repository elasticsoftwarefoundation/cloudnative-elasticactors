package org.elasticsoftware.elasticactors.persistentactor.grpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.elasticsoftware.elasticactors.ActorState;
import org.elasticsoftware.elasticactors.ActorSystem;
import org.elasticsoftware.elasticactors.ElasticActor;
import org.elasticsoftware.elasticactors.ElasticActorContainer;
import org.elasticsoftware.elasticactors.grpc.ActorLifecycleResponse;
import org.elasticsoftware.elasticactors.grpc.ActorLifecycleServiceGrpc;
import org.elasticsoftware.elasticactors.grpc.HandleMessageRequest;
import org.elasticsoftware.elasticactors.persistentactor.impl.UntypedActorState;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class ActorLifecycleServiceEndpoint extends ActorLifecycleServiceGrpc.ActorLifecycleServiceImplBase {
    @Inject
    ObjectMapper objectMapper;
    @Inject @Named("elasticActorContainer")
    ElasticActorContainer<ActorState<?>, Object> elasticActorContainer;

    @Override
    public void handleMessage(HandleMessageRequest request, StreamObserver<ActorLifecycleResponse> responseObserver) {
        try {
            // deserialize the state
            ActorState<?> state = elasticActorContainer.deserializeState(request.getState().getState().toByteArray());
            // and the message
            Object message = elasticActorContainer.deserializeMessage(request.getMessage().getPayloadType(), request.getMessage().getPayload().toByteArray());
            ActorState<?> updatedState = elasticActorContainer.getElasticActor().onReceive(null, message, state, null);
            ActorLifecycleResponse response = ActorLifecycleResponse.newBuilder().setState(
                    org.elasticsoftware.elasticactors.grpc.ActorState.newBuilder().setState(ByteString.copyFrom(elasticActorContainer.serializeState(updatedState))).build()).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
      } catch(Exception e) {
            responseObserver.onError(Status.INTERNAL.asException());
        }
    }
}
