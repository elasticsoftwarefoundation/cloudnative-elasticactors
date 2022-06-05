package org.elasticsoftware.elasticactors.cloudnative.operator;

import io.kubernetes.client.informer.ResourceEventHandler;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServiceList;
import io.micronaut.context.annotation.Requires;
import io.micronaut.kubernetes.client.informer.Informer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Requires(env = "operator")
@Informer(apiType = V1Service.class, apiListType = V1ServiceList.class,  labelSelector = "app.kubernetes.io/name=kafka", namespace = Informer.ALL_NAMESPACES)
public class KafkaBootstrapServiceDetector implements ResourceEventHandler<V1Service> {
    private static final Logger log = LoggerFactory.getLogger(KafkaBootstrapServiceDetector.class);

    private String bootstrapUrl = null;
    @Override
    public void onAdd(V1Service service) {
        // we must have a none headless service and the name must include "bootstrap"
        if(!"None".equals(service.getSpec().getClusterIP()) && service.getMetadata().getName().contains("bootstrap")) {
            log.info("Found kafka bootstrap service: " + service.getMetadata().getNamespace()+"/"+service.getMetadata().getName());
            // TODO port 9092 is assumed here
            this.bootstrapUrl = service.getMetadata().getName()+"."+service.getMetadata().getNamespace()+":9092";
        }

    }

    @Override
    public void onUpdate(V1Service oldObj, V1Service newObj) {

    }

    @Override
    public void onDelete(V1Service obj, boolean deletedFinalStateUnknown) {
        // TODO: what to do if the boostrap service is deleted?
    }

    public boolean isEnabled() {
        return bootstrapUrl != null;
    }

    public String getBootstrapUrl() {
        return bootstrapUrl;
    }
}
