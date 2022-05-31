package org.elasticsoftware.elasticactors.cloudnative;

import io.micronaut.context.env.Environment;
import io.micronaut.runtime.Micronaut;

public class ActorSystemOperatorApplication {
    public static void main(String[] args) {
        Micronaut.build(args)
                .environments(Environment.KUBERNETES,"operator")
                .eagerInitSingletons(true)
                .mainClass(ActorSystemOperatorApplication.class)
                .start();
    }
}
