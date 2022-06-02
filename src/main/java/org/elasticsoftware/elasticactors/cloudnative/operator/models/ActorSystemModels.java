package org.elasticsoftware.elasticactors.cloudnative.operator.models;

import io.micronaut.core.annotation.ReflectionConfig;
import io.micronaut.core.annotation.TypeHint;

@ReflectionConfig(
        type = V1ActorSystem.class,
        accessType = {TypeHint.AccessType.ALL_PUBLIC_METHODS, TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS, TypeHint.AccessType.ALL_DECLARED_FIELDS}
)
@ReflectionConfig(
        type = V1ActorSystemList.class,
        accessType = {TypeHint.AccessType.ALL_PUBLIC_METHODS, TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS, TypeHint.AccessType.ALL_DECLARED_FIELDS}
)
@ReflectionConfig(
        type = V1ActorSystemSpec.class,
        accessType = {TypeHint.AccessType.ALL_PUBLIC_METHODS, TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS, TypeHint.AccessType.ALL_DECLARED_FIELDS}
)
@ReflectionConfig(
        type = V1ActorSystemSpecActorGateway.class,
        accessType = {TypeHint.AccessType.ALL_PUBLIC_METHODS, TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS, TypeHint.AccessType.ALL_DECLARED_FIELDS}
)
@ReflectionConfig(
        type = V1ActorSystemSpecPersistentActors.class,
        accessType = {TypeHint.AccessType.ALL_PUBLIC_METHODS, TypeHint.AccessType.ALL_DECLARED_CONSTRUCTORS, TypeHint.AccessType.ALL_DECLARED_FIELDS}
)
public class ActorSystemModels {
}
