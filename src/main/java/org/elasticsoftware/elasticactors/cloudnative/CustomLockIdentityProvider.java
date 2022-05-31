package org.elasticsoftware.elasticactors.cloudnative;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.context.env.Environment;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.kubernetes.client.PodNameResolver;
import io.micronaut.kubernetes.client.operator.leaderelection.LockIdentityProvider;
import jakarta.inject.Singleton;

@Singleton
@Replaces(LockIdentityProvider.class)
public class CustomLockIdentityProvider implements LockIdentityProvider {

    private final PodNameResolver podNameResolver;
    private final Environment env;

    public CustomLockIdentityProvider(@NonNull PodNameResolver podNameResolver,@NonNull Environment env) {
        this.podNameResolver = podNameResolver;
        this.env = env;
    }

    @Override
    public String getIdentity() {
        return podNameResolver.getPodName().orElse(env.getRequiredProperty("micronaut.application.name", String.class));
    }
}