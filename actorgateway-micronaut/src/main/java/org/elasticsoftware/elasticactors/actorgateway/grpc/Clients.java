package org.elasticsoftware.elasticactors.actorgateway.grpc;

import io.grpc.ManagedChannel;
import io.micronaut.context.annotation.Factory;
import io.micronaut.grpc.annotation.GrpcChannel;
import org.elasticsoftware.elasticactors.grpc.NodeServiceGrpc;

import javax.inject.Singleton;

@Factory
public class Clients {
    @Singleton
    NodeServiceGrpc.NodeServiceBlockingStub nodeServiceClient(@GrpcChannel("http://actornode-micronaut:50051") ManagedChannel channel) {
        return NodeServiceGrpc.newBlockingStub(channel);
    }
}
