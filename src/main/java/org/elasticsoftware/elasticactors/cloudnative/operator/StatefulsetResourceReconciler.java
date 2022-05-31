package org.elasticsoftware.elasticactors.cloudnative.operator;

import io.kubernetes.client.extended.controller.reconciler.Request;
import io.kubernetes.client.extended.controller.reconciler.Result;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1StatefulSet;
import io.kubernetes.client.openapi.models.V1StatefulSetList;
import io.micronaut.context.annotation.Requires;
import io.micronaut.kubernetes.client.informer.Informer;
import io.micronaut.kubernetes.client.operator.Operator;
import io.micronaut.kubernetes.client.operator.OperatorResourceLister;
import io.micronaut.kubernetes.client.operator.ResourceReconciler;
import org.elasticsoftware.elasticactors.cloudnative.ServiceResourceReconciler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Requires(env = "operator")
@Operator(informer = @Informer(apiType = V1StatefulSet.class, apiListType = V1StatefulSetList.class))
public class StatefulsetResourceReconciler implements ResourceReconciler<V1StatefulSet> {
    private static final Logger log = LoggerFactory.getLogger(StatefulsetResourceReconciler.class);
    @Override
    public Result reconcile(Request request, OperatorResourceLister<V1StatefulSet> lister) {
        Optional<V1StatefulSet> resource = lister.get(request);
        resource.ifPresent(statefulSet -> log.info("Found: " + statefulSet.getMetadata().getName()));
        return new Result(false);
    }
}
