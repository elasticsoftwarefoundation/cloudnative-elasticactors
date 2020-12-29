package org.elasticsoftware.elasticactors.actornode;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.naming.Named;
import io.micronaut.inject.qualifiers.Qualifiers;
import org.elasticsoftware.elasticactors.grpc.InternalMessage;
import org.elasticsoftware.elasticactors.grpc.PersistentActorState;
import org.elasticsoftware.elasticactors.serialization.protobuf.Messaging;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Prototype
@Introspected
public class ActorShard implements Named {
    private final BlockingQueue<Runnable> actions = new LinkedBlockingQueue<>();
    private final ApplicationContext beanContext;
    private String name;
    private PersistentActorRepository persistentActorRepository;

    public ActorShard(ApplicationContext beanContext) {
        this.beanContext = beanContext;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    void processLoop() {
        try {
            Optional.ofNullable(actions.poll(1, TimeUnit.MILLISECONDS)).ifPresent(Runnable::run);
        } catch(InterruptedException ignored) {

        } catch(Exception e) {

        }
    }

    // TODO: need to ack the message
    void handleMessage(InternalMessage internalMessage) {
        actions.offer(() -> {
            internalMessage.getReceiversList().forEach(actorRef -> {
                // resolve the persistent actor state
                PersistentActorState persistentActorState = persistentActorRepository.getActorState(actorRef);
                // resolve the type and get the
                beanContext.findBean(PersistentActor.class, Qualifiers.byName(persistentActorState.getActorType()))
                        .ifPresentOrElse(
                                persistentActor -> handleMessage(persistentActor, persistentActorState, internalMessage),
                                () -> messageUndeliverable(internalMessage));
            });
        });
    }

    private void handleMessage(PersistentActor actor, PersistentActorState state, InternalMessage message) {
        actor.handleMessage(state, message);
    }

    private void messageUndeliverable(InternalMessage message) {
        // TODO implement
    }
}
