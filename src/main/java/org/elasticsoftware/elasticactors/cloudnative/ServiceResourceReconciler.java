package org.elasticsoftware.elasticactors.cloudnative;

import io.grpc.*;
import io.kubernetes.client.extended.controller.reconciler.Request;
import io.kubernetes.client.extended.controller.reconciler.Result;
import io.kubernetes.client.openapi.models.V1ConfigMap;
import io.kubernetes.client.openapi.models.V1ConfigMapList;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServiceList;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.kubernetes.client.informer.Informer;
import io.micronaut.kubernetes.client.operator.Operator;
import io.micronaut.kubernetes.client.operator.OperatorResourceLister;
import io.micronaut.kubernetes.client.operator.ResourceReconciler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static io.micronaut.kubernetes.client.informer.Informer.ALL_NAMESPACES;

@Requires(env = "actorsystemshard")
@Operator(informer = @Informer(apiType = V1Service.class, apiListType = V1ServiceList.class, labelSelector = "bux-monitoring"))
public class ServiceResourceReconciler implements ResourceReconciler<V1Service> {
    private static final Logger log = LoggerFactory.getLogger(ServiceResourceReconciler.class);
    @Override
    @NonNull
    public Result reconcile(@NonNull Request request, @NonNull OperatorResourceLister<V1Service> lister) { //
        Optional<V1Service> resource = lister.get(request);
        resource.ifPresent(service -> log.info("Found: " + service.getMetadata().getName()));
        // TODO: get the grpc address from the service
        //Channel channel = ManagedChannelBuilder.forAddress()
        //ActorsystemshardServiceGrpc.newBlockingStub(channel);
        return new Result(false);
    }
}
