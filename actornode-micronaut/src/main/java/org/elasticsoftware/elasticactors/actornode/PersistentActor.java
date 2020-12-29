package org.elasticsoftware.elasticactors.actornode;

import io.grpc.ManagedChannel;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.naming.Named;
import io.micronaut.grpc.channels.GrpcManagedChannelConfiguration;
import io.micronaut.inject.qualifiers.Qualifiers;
import org.elasticsoftware.elasticactors.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Introspected
@EachProperty("elasticactors.persistentactors")
public class PersistentActor implements Named {
    private static final Logger logger = LoggerFactory.getLogger(PersistentActor.class);
    private final String name;
    private final String hostIp;
    private Integer port;
    private Boolean enabled;
    private final ApplicationContext beanContext;
    private ManagedChannel managedChannel;
    private ActorLifecycleServiceGrpc.ActorLifecycleServiceBlockingStub actorLifecycleService;

    public PersistentActor(@Parameter String name,
                           @Value("${host.ip}") String hostIp,
                           ApplicationContext beanContext) {
        this.name = name;
        this.hostIp = hostIp;
        this.beanContext = beanContext;
    }

    @PostConstruct
    public void init() {
        GrpcManagedChannelConfiguration managedChannelConfiguration = beanContext.getBean(GrpcManagedChannelConfiguration.class, Qualifiers.byName(name));
        logger.info("Registered PersistentActor with address {}:{} - {}", hostIp, port, managedChannelConfiguration.getName());
        managedChannel = managedChannelConfiguration.getChannelBuilder().usePlaintext().build();
        managedChannel.resetConnectBackoff();
        actorLifecycleService = ActorLifecycleServiceGrpc.newBlockingStub(managedChannel);
    }

    @PreDestroy
    public void destroy() {
        managedChannel.shutdown();
    }

    public void handleMessage(PersistentActorState state, InternalMessage message) {
        actorLifecycleService.handleMessage(generateRequest(state, message));
    }


    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    public String getHostIp() {
        return hostIp;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    private HandleMessageRequest generateRequest(PersistentActorState state,
                                                 InternalMessage message) {
        return HandleMessageRequest.newBuilder()
                .setState(state)
                .setMessage(message)
                .build();
    }


}
