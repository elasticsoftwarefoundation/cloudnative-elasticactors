package org.elasticsoftware.elasticactors.actornode;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.env.Environment;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.inject.qualifiers.Qualifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Context
@ConfigurationProperties("elasticactors")
@Introspected
public class ActorSystemNode {
    private static final Logger logger = LoggerFactory.getLogger(ActorSystemNode.class);
    private final ApplicationContext beanContext;
    private final String nodeName;
    private String name;
    private Integer nodes;
    private Integer shards;

    public ActorSystemNode(@Value("${actornode.name}") String nodeName,
                           ApplicationContext beanContext) {
        this.nodeName = nodeName;
        this.beanContext = beanContext;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getShards() {
        return shards;
    }

    public void setShards(Integer shards) {
        this.shards = shards;
    }

    public Integer getNodes() {
        return nodes;
    }

    public void setNodes(Integer nodes) {
        this.nodes = nodes;
    }

    @PostConstruct
    public void init() {
        logger.info("Starting ActorSystemNode {} {} {} {} ",nodeName, name, nodes, shards);
        int shards = getShardsPerNode();
        int startingShard = getOrdinal() * shards;
        for (int i = 0; i < shards; i++) {
            ActorShard actorShard = beanContext.createBean(ActorShard.class);
            actorShard.setName("shard-"+(i+startingShard));
            logger.info("Created ActorShard {}", actorShard.getName());
            beanContext.inject(actorShard);
        }
    }

    private Integer getOrdinal() {
        return Integer.parseInt(nodeName.substring(nodeName.lastIndexOf('-')+1));
    }

    private Integer getShardsPerNode() {
        return shards / nodes;
    }

}
