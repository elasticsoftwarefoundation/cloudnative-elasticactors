package org.elasticsoftware.elasticactors.cloudnative;

import io.micronaut.context.env.Environment;
import io.micronaut.runtime.Micronaut;

public class PersistentActorApplication {
    public static void main(String[] args) {
        Micronaut.build(args)
                .environments(Environment.KUBERNETES,"persistentactor")
                .eagerInitSingletons(true)
                .mainClass(PersistentActorApplication.class)
                .start();
    }
}
