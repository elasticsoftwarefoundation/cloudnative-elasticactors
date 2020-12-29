package org.elasticsoftware.elasticactors.actornode.grpc;

import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import org.elasticsoftware.elasticactors.grpc.ActorState;
import org.elasticsoftware.elasticactors.grpc.NodeServiceGrpc;
import org.elasticsoftware.elasticactors.grpc.StateRequest;

import javax.inject.Singleton;

@Singleton
public class NodeServiceEndpoint extends NodeServiceGrpc.NodeServiceImplBase {
    @Override
    public void requestState(StateRequest request, StreamObserver<ActorState> responseObserver) {
        ActorState stateReply = ActorState.newBuilder()
                .setState(ByteString.EMPTY)
                .setStateGeneration(0L).build();
        responseObserver.onNext(stateReply);
        responseObserver.onCompleted();
    }
}
