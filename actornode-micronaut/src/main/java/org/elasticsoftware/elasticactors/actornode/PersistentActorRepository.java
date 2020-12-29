package org.elasticsoftware.elasticactors.actornode;

import org.elasticsoftware.elasticactors.grpc.PersistentActorState;

public interface PersistentActorRepository {
    PersistentActorState getActorState(String actorRef);
}
