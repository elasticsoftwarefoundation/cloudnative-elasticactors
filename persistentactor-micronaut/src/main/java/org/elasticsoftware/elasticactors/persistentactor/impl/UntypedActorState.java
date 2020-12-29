package org.elasticsoftware.elasticactors.persistentactor.impl;

import org.elasticsoftware.elasticactors.ActorState;

import java.util.Map;

public final class UntypedActorState implements ActorState<Map<String,Object>> {
    private final Map<String, Object> body;

    public UntypedActorState(Map<String, Object> body) {
        this.body = body;
    }

    @Override
    public Map<String, Object> getBody() {
        return body;
    }
}
