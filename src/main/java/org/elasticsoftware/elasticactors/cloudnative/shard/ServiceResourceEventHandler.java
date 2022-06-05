package org.elasticsoftware.elasticactors.cloudnative.shard;

import io.kubernetes.client.informer.ResourceEventHandler;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServiceList;
import io.micronaut.context.annotation.Requires;
import io.micronaut.kubernetes.client.informer.Informer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Requires(env = "actorsystemshard")
@Informer(apiType = V1Service.class, apiListType = V1ServiceList.class, labelSelectorSupplier = ActorSystemLabelSelectorSupplier.class, resyncCheckPeriod = 10000L)
public class ServiceResourceEventHandler implements ResourceEventHandler<V1Service> {
    private static final Logger log = LoggerFactory.getLogger(ServiceResourceEventHandler.class);
    @Override
    public void onAdd(V1Service obj) {
        log.info("Added service: "+obj.getMetadata().getName());
        // TODO: get the grpc address from the service
        //Channel channel = ManagedChannelBuilder.forAddress()
        //ActorsystemshardServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void onUpdate(V1Service oldObj, V1Service newObj) {
        if(!oldObj.equals(newObj)) {
            log.info("Updated Service " + oldObj.getMetadata().getName());
        }
    }

    @Override
    public void onDelete(V1Service obj, boolean deletedFinalStateUnknown) {
        log.info("Deleted service: "+obj.getMetadata().getName());
    }
}
