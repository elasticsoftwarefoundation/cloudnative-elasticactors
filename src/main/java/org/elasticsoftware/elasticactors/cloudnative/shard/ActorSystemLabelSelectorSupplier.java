package org.elasticsoftware.elasticactors.cloudnative.shard;

import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

import java.util.function.Supplier;

@Requires(env = "actorsystemshard")
@Singleton
public class ActorSystemLabelSelectorSupplier implements Supplier<String> {
    private final ActorSystemConfiguration actorSystemConfiguration;

    public ActorSystemLabelSelectorSupplier(ActorSystemConfiguration actorSystemConfiguration) {
        this.actorSystemConfiguration = actorSystemConfiguration;
    }

    @Override
    public String get() {
        return "io.elasticactors.actorsystem/name="+actorSystemConfiguration.getName()+",io.elasticactors.actorsystem/kind=PersistentActor";
    }
}
