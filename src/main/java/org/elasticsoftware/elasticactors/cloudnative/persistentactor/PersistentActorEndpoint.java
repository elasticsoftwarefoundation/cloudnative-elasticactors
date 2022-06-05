package org.elasticsoftware.elasticactors.cloudnative.persistentactor;

import io.grpc.stub.StreamObserver;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.kubernetes.client.PodNameResolver;
import jakarta.inject.Singleton;
import org.elasticsoftware.elasticactors.cloudnative.*;

@Requires(env = "persistentactor")
@Singleton
public class PersistentActorEndpoint extends PersistentActorServiceGrpc.PersistentActorServiceImplBase {
    private final PodNameResolver podNameResolver;

    public PersistentActorEndpoint(@NonNull PodNameResolver podNameResolver) {
        this.podNameResolver = podNameResolver;
    }

    @Override
    public void handleMessage(HandleMessageRequest request, StreamObserver<HandleMessageResponse> responseObserver) {
        responseObserver.onError(new UnsupportedOperationException());
    }

    @Override
    public void echo(EchoRequest request, StreamObserver<EchoResponse> responseObserver) {
        responseObserver.onNext(EchoResponse.newBuilder()
                .setFrom(podNameResolver.getPodName().orElse("unknown"))
                .setMessage(request.getMessage())
                .build());
        responseObserver.onCompleted();
    }
}
