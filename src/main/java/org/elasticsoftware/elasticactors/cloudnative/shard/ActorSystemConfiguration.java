package org.elasticsoftware.elasticactors.cloudnative.shard;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;

@Requires(env = "actorsystemshard")
@ConfigurationProperties("actorsystem")
public class ActorSystemConfiguration {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
