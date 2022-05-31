package org.elasticsoftware.elasticactors.cloudnative;

import io.micronaut.context.env.Environment;
import io.micronaut.runtime.Micronaut;

public class ActorSystemShardApplication {
    public static void main(String[] args) {
        Micronaut.build(args)
                .environments(Environment.KUBERNETES,"actorsystemshard")
                .eagerInitSingletons(true)
                .mainClass(ActorSystemShardApplication.class)
                .start();
    }
}
