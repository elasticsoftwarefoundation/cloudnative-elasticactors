package org.elasticsoftware.elasticactors.cloudnative.shard;

import jakarta.inject.Singleton;

import java.util.function.Supplier;

@Singleton
public class ActorSystemLabelSelectorSupplier implements Supplier<String> {
    @Override
    public String get() {
        return "io.elasticactors.actorsystem";
    }
}
