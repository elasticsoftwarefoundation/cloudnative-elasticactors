package org.elasticsoftware.elasticactors.persistentactor.impl;

import org.elasticsoftware.elasticactors.ActorState;

public class StringActorState implements ActorState<String> {
    private final String body;

    public StringActorState(String body) {
        this.body = body;
    }

    @Override
    public String getBody() {
        return body;
    }
}
