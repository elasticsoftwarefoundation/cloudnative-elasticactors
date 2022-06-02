package org.elasticsoftware.elasticactors.cloudnative;

import io.micronaut.context.env.Environment;
import io.micronaut.core.annotation.ReflectionConfig;
import io.micronaut.core.annotation.TypeHint;
import io.micronaut.runtime.Micronaut;
import org.elasticsoftware.elasticactors.cloudnative.operator.models.*;


public class ElasticActorsOperatorApplication {
    public static void main(String[] args) {
        Micronaut.build(args)
                .environments(Environment.KUBERNETES,"operator")
                .eagerInitSingletons(true)
                .mainClass(ElasticActorsOperatorApplication.class)
                .start();
    }
}
