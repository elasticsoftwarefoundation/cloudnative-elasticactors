package org.elasticsoftware.elasticactors;

public interface ElasticActorContainer<S extends ActorState<?>, M> {
    S deserializeState(byte[] stateBytes);

    byte[] serializeState(S state);

    ElasticActor<S, M> getElasticActor();

    M deserializeMessage(String messageType, byte[] messageBytes);

    byte[] serializeMessage(M message);

    String getMessageType(M message);

}
