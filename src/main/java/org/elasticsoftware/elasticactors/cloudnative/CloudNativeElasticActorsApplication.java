package org.elasticsoftware.elasticactors.cloudnative;

import io.micronaut.context.env.Environment;
import io.micronaut.runtime.Micronaut;

public class CloudNativeElasticActorsApplication {
    public static void main(String[] args) {
        Micronaut.build(args)
                .environments(Environment.KUBERNETES, args[0])
                .eagerInitSingletons(true)
                .mainClass(CloudNativeElasticActorsApplication.class)
                .start();
    }
}
