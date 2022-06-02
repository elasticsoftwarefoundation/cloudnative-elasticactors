package org.elasticsoftware.elasticactors.cloudnative.operator;

import io.kubernetes.client.informer.ResourceEventHandler;
import io.kubernetes.client.openapi.models.V1StatefulSet;
import io.kubernetes.client.openapi.models.V1StatefulSetList;
import io.micronaut.context.annotation.Requires;
import io.micronaut.kubernetes.client.informer.Informer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Requires(env = "operator")
@Informer(apiType = V1StatefulSet.class, apiListType = V1StatefulSetList.class, labelSelector = "io.elasticactors.actorsystem", resyncCheckPeriod = 10000L)
public class StatefulsetResourceEventHandler implements ResourceEventHandler<V1StatefulSet> {
    private static final Logger log = LoggerFactory.getLogger(StatefulsetResourceEventHandler.class);

    @Override
    public void onAdd(V1StatefulSet actorSystemShards) {
        log.info("Added Statefulset "+actorSystemShards.getMetadata().getName());
    }

    @Override
    public void onUpdate(V1StatefulSet oldObj, V1StatefulSet newObj) {
        if(!oldObj.equals(newObj)) {
            log.info("Updated Statefulset " + oldObj.getMetadata().getName());
        }
    }

    @Override
    public void onDelete(V1StatefulSet actorSystemShards, boolean deletedFinalStateUnknown) {
        log.info("Deleted Statefulset "+actorSystemShards.getMetadata().getName());
    }
}
