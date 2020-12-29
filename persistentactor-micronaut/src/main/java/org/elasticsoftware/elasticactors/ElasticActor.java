package org.elasticsoftware.elasticactors;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface ElasticActor<S extends ActorState, M> {
    @Nullable S onReceive(@Nullable ActorRef sender, M message, S currentState, ActorSystem actorSystem);
}
