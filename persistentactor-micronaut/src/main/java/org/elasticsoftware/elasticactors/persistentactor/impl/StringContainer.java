package org.elasticsoftware.elasticactors.persistentactor.impl;

import org.elasticsoftware.elasticactors.ElasticActor;
import org.elasticsoftware.elasticactors.ElasticActorContainer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;

@Singleton @Named("elasticActorContainer")
public class StringContainer implements ElasticActorContainer<StringActorState, String> {
    private final ElasticActor<StringActorState, String> elasticActor;

    @Inject
    public StringContainer(ElasticActor<StringActorState, String> elasticActor) {
        this.elasticActor = elasticActor;
    }

    @Override
    public StringActorState deserializeState(byte[] stateBytes) {
        return new StringActorState(new String(stateBytes, StandardCharsets.UTF_8));
    }

    @Override
    public byte[] serializeState(StringActorState state) {
        return state.getBody().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public ElasticActor<StringActorState, String> getElasticActor() {
        return elasticActor;
    }

    @Override
    public String deserializeMessage(String messageType, byte[] messageBytes) {
        return new String(messageBytes, StandardCharsets.UTF_8);
    }

    @Override
    public byte[] serializeMessage(String message) {
        return message.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getMessageType(String message) {
        return "text/plain; charset=utf-8";
    }
}
