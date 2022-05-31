package org.elasticsoftware.elasticactors.cloudnative.persistentactor;

import io.grpc.stub.StreamObserver;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import org.elasticsoftware.elasticactors.cloudnative.HandleMessageRequest;
import org.elasticsoftware.elasticactors.cloudnative.HandleMessageResponse;
import org.elasticsoftware.elasticactors.cloudnative.PersistentActorServiceGrpc;

@Requires(env = "persistentactor")
@Singleton
public class PersistentActorEndpoint extends PersistentActorServiceGrpc.PersistentActorServiceImplBase {
    @Override
    public void handleMessage(HandleMessageRequest request, StreamObserver<HandleMessageResponse> responseObserver) {
        responseObserver.onError(new UnsupportedOperationException());
    }
}
