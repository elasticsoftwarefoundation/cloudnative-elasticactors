package org.elasticsoftware.elasticactors.persistentactor.impl;

import org.elasticsoftware.elasticactors.ActorRef;
import org.elasticsoftware.elasticactors.ActorSystem;
import org.elasticsoftware.elasticactors.ElasticActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
@ParametersAreNonnullByDefault
public class LoggingElasticActor implements ElasticActor<StringActorState, String> {
    private static final Logger logger = LoggerFactory.getLogger(LoggingElasticActor.class);

    @Override
    @Nullable
    public StringActorState onReceive(@Nullable ActorRef sender,
                                       String message,
                                       StringActorState currentState,
                                       ActorSystem actorSystem) {
        logger.info("Received a Message: {}", message);
        return currentState;
    }
}
