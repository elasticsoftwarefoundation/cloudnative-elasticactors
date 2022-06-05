package org.elasticsoftware.elasticactors.cloudnative.operator;

import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.extended.controller.reconciler.Request;
import io.kubernetes.client.extended.controller.reconciler.Result;
import io.kubernetes.client.informer.SharedIndexInformer;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.kubernetes.client.informer.Informer;
import io.micronaut.kubernetes.client.informer.SharedIndexInformerFactory;
import io.micronaut.kubernetes.client.operator.Operator;
import io.micronaut.kubernetes.client.operator.OperatorResourceLister;
import io.micronaut.kubernetes.client.operator.ResourceReconciler;
import org.elasticsoftware.elasticactors.cloudnative.operator.models.V1ActorSystem;
import org.elasticsoftware.elasticactors.cloudnative.operator.models.V1ActorSystemList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Requires(env = "operator")
@Operator(name = "elasticactors-operator", informer = @Informer(apiType = V1ActorSystem.class, apiListType = V1ActorSystemList.class))
public class ActorSystemResourceReconciler implements ResourceReconciler<V1ActorSystem> {
    private final SharedIndexInformerFactory sharedIndexInformerFactory;
    private final CoreV1Api coreApi;
    private final AppsV1Api appsApi;
    private static final Logger log = LoggerFactory.getLogger(ActorSystemResourceReconciler.class);

    public ActorSystemResourceReconciler(@NonNull SharedIndexInformerFactory sharedIndexInformerFactory,
                                         @NonNull CoreV1Api coreApi,
                                         @NonNull AppsV1Api appsApi) {
        this.sharedIndexInformerFactory = sharedIndexInformerFactory;
        this.coreApi = coreApi;
        this.appsApi = appsApi;
    }

    @NonNull
    @Override
    public Result reconcile(@NonNull Request request,@NonNull OperatorResourceLister<V1ActorSystem> lister) {
        Optional<V1ActorSystem> resource = lister.get(request);
        return resource.map(this::createActorSystem).orElseGet(() -> new Result(false));
    }

    private Result createActorSystem(V1ActorSystem actorSystem) {
        SharedIndexInformer<V1StatefulSet> statefulSetIndex = sharedIndexInformerFactory.getExistingSharedIndexInformer(actorSystem.getMetadata().getNamespace(), V1StatefulSet.class);
        V1StatefulSet existingStatefulSet = statefulSetIndex.getIndexer().getByKey(actorSystem.getMetadata().getNamespace()+"/"+actorSystem.getMetadata().getName());
        if(existingStatefulSet == null) {
            log.info("Configuring: " + actorSystem.getMetadata().getName());
            V1Service service =
                    new V1ServiceBuilder()
                            .withNewMetadata()
                            .withName(actorSystem.getMetadata().getName() + "-headless")
                            .addNewOwnerReference()
                            .withName(actorSystem.getMetadata().getName())
                            .withUid(actorSystem.getMetadata().getUid())
                            .withKind(actorSystem.getKind())
                            .withApiVersion(actorSystem.getApiVersion())
                            .endOwnerReference()
                            .addToLabels("io.elasticactors.actorsystem", actorSystem.getMetadata().getName())
                            .endMetadata()
                            .withNewSpec()
                            .withClusterIP("None")
                            .addToSelector("app", actorSystem.getMetadata().getName())
                            .endSpec().build();

            V1StatefulSet statefulSet =
                    new V1StatefulSetBuilder()
                            .withNewMetadata()
                            .withName(actorSystem.getMetadata().getName())
                            .addNewOwnerReference()
                            .withName(actorSystem.getMetadata().getName())
                            .withUid(actorSystem.getMetadata().getUid())
                            .withKind(actorSystem.getKind())
                            .withApiVersion(actorSystem.getApiVersion())
                            .endOwnerReference()
                            .addToLabels("io.elasticactors.actorsystem", actorSystem.getMetadata().getName())
                            .endMetadata()
                            .withNewSpec()
                            .withNewSelector().addToMatchLabels("app", actorSystem.getMetadata().getName()).endSelector()
                            .withServiceName(actorSystem.getMetadata().getName() + "-headless")
                            .withReplicas(actorSystem.getSpec().getShards())
                            .withPodManagementPolicy("Parallel")
                            .withNewTemplate()
                            .withNewMetadata()
                            .addToLabels("app", actorSystem.getMetadata().getName())
                            .endMetadata()
                            .withNewSpec()
                            .withContainers()
                            .addNewContainer()
                            .withName("actorsystemshard")
                            .withImage("elasticactors/cloudnative-elasticactors:0.1")
                            .addNewArg("actorsystemshard")
                            .addNewArg("-Dkafka.enabled=true")
                            .addNewArg("-Dkafka.bootstrap.servers=akces-kafka-bootstrap.strimzi:9092")
                            .withNewResources()
                            .addToRequests("cpu", Quantity.fromString("100m"))
                            .addToRequests("memory",Quantity.fromString("512Mi"))
                            .addToLimits("memory",Quantity.fromString("512Mi"))
                            .endResources()
                            .endContainer()
                            .endSpec()
                            .endTemplate()
                            .endSpec()
                            .build();

            try {
                this.coreApi.createNamespacedService(actorSystem.getMetadata().getNamespace(), service, null, null, null);
                this.appsApi.createNamespacedStatefulSet(actorSystem.getMetadata().getNamespace(), statefulSet, null, null, null);
            } catch (ApiException e) {
                log.error(e.getResponseBody(), e);
                return new Result(true);
            }
        }
        return new Result(false);
    }
}
