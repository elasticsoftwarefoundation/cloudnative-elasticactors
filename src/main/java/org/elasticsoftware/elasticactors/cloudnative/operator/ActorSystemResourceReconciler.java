package org.elasticsoftware.elasticactors.cloudnative.operator;

import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.extended.controller.reconciler.Request;
import io.kubernetes.client.extended.controller.reconciler.Result;
import io.kubernetes.client.informer.SharedIndexInformer;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.RbacAuthorizationV1Api;
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
import org.elasticsoftware.elasticactors.cloudnative.operator.models.V1ActorSystemSpecPersistentActors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Requires(env = "operator")
@Operator(name = "elasticactors-operator", informer = @Informer(apiType = V1ActorSystem.class, apiListType = V1ActorSystemList.class))
public class ActorSystemResourceReconciler implements ResourceReconciler<V1ActorSystem> {
    private final SharedIndexInformerFactory sharedIndexInformerFactory;
    private final CoreV1Api coreApi;
    private final AppsV1Api appsApi;
    private final RbacAuthorizationV1Api rbacAuthorizationApi;
    private final KafkaBootstrapServiceDetector kafkaBootstrapServiceDetector;
    private static final Logger log = LoggerFactory.getLogger(ActorSystemResourceReconciler.class);


    public ActorSystemResourceReconciler(@NonNull SharedIndexInformerFactory sharedIndexInformerFactory,
                                         @NonNull CoreV1Api coreApi,
                                         @NonNull AppsV1Api appsApi,
                                         @NonNull RbacAuthorizationV1Api rbacAuthorizationApi,
                                         @NonNull KafkaBootstrapServiceDetector kafkaBootstrapServiceDetector) {
        this.sharedIndexInformerFactory = sharedIndexInformerFactory;
        this.coreApi = coreApi;
        this.appsApi = appsApi;
        this.rbacAuthorizationApi = rbacAuthorizationApi;
        this.kafkaBootstrapServiceDetector = kafkaBootstrapServiceDetector;
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
            V1ServiceAccount serviceAccount =
                    new V1ServiceAccountBuilder()
                            .withNewMetadata()
                            .withNamespace(actorSystem.getMetadata().getNamespace())
                            .withName(actorSystem.getMetadata().getName())
                            .addNewOwnerReference()
                            .withName(actorSystem.getMetadata().getName())
                            .withUid(actorSystem.getMetadata().getUid())
                            .withKind(actorSystem.getKind())
                            .withApiVersion(actorSystem.getApiVersion())
                            .endOwnerReference()
                            .addToLabels("io.elasticactors.actorsystem/name", actorSystem.getMetadata().getName())
                            .endMetadata()
                            .build();
            // TODO: we actually only need one of these per namespace, not per actor system
            V1Role role =
                    new V1RoleBuilder()
                            .withNewMetadata()
                            .withName(actorSystem.getMetadata().getName()+"-service-discoverer")
                            .withNamespace(actorSystem.getMetadata().getNamespace())
                            .addNewOwnerReference()
                            .withName(actorSystem.getMetadata().getName())
                            .withUid(actorSystem.getMetadata().getUid())
                            .withKind(actorSystem.getKind())
                            .withApiVersion(actorSystem.getApiVersion())
                            .endOwnerReference()
                            .addToLabels("io.elasticactors.actorsystem/name", actorSystem.getMetadata().getName())
                            .endMetadata()
                            .addNewRule()
                            .addToApiGroups("")
                            .addToResources("services", "endpoints", "configmaps", "secrets", "pods")
                            .addToVerbs("get", "watch", "list")
                            .endRule().build();

            V1RoleBinding roleBinding =
                    new V1RoleBindingBuilder()
                            .withNewMetadata()
                            .withName(actorSystem.getMetadata().getName()+"-service-discoverer")
                            .withNamespace(actorSystem.getMetadata().getNamespace())
                            .addNewOwnerReference()
                            .withName(actorSystem.getMetadata().getName())
                            .withUid(actorSystem.getMetadata().getUid())
                            .withKind(actorSystem.getKind())
                            .withApiVersion(actorSystem.getApiVersion())
                            .endOwnerReference()
                            .addToLabels("io.elasticactors.actorsystem/name", actorSystem.getMetadata().getName())
                            .endMetadata()
                            .addNewSubject()
                            .withKind("ServiceAccount")
                            .withName(actorSystem.getMetadata().getName())
                            .withNamespace(actorSystem.getMetadata().getNamespace())
                            .endSubject()
                            .withNewRoleRef()
                            .withKind("Role")
                            .withName(actorSystem.getMetadata().getName()+"-service-discoverer")
                            .withApiGroup("rbac.authorization.k8s.io")
                            .endRoleRef().build();

            V1Service service =
                    new V1ServiceBuilder()
                            .withNewMetadata()
                            .withName(actorSystem.getMetadata().getName() + "-shards")
                            .addNewOwnerReference()
                            .withName(actorSystem.getMetadata().getName())
                            .withUid(actorSystem.getMetadata().getUid())
                            .withKind(actorSystem.getKind())
                            .withApiVersion(actorSystem.getApiVersion())
                            .endOwnerReference()
                            .addToLabels("io.elasticactors.actorsystem/name", actorSystem.getMetadata().getName())
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
                            .addToLabels("io.elasticactors.actorsystem/name", actorSystem.getMetadata().getName())
                            .endMetadata()
                            .withNewSpec()
                            .withNewSelector().addToMatchLabels("app", actorSystem.getMetadata().getName()).endSelector()
                            .withServiceName(actorSystem.getMetadata().getName() + "-shards")
                            .withReplicas(actorSystem.getSpec().getShards())
                            .withPodManagementPolicy("Parallel")
                            .withNewTemplate()
                            .withNewMetadata()
                            .addToLabels("app", actorSystem.getMetadata().getName())
                            .endMetadata()
                            .withNewSpec()
                            .withServiceAccountName(actorSystem.getMetadata().getName())
                            .withContainers()
                            .addNewContainer()
                            .withName("actorsystemshard")
                            .withImage(Optional.ofNullable(actorSystem.getSpec().getRuntime().getImage()).orElse("elasticactors/cloudnative-elasticactors")+":"+actorSystem.getSpec().getRuntime().getVersion())
                            .addNewArg("actorsystemshard")
                            .addNewArg("-Dkafka.enabled="+kafkaBootstrapServiceDetector.isEnabled())
                            .addNewArg("-Dkafka.bootstrap.servers="+kafkaBootstrapServiceDetector.getBootstrapUrl())
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
                // create shards
                this.coreApi.createNamespacedServiceAccount(actorSystem.getMetadata().getNamespace(), serviceAccount, null, null, null);
                this.rbacAuthorizationApi.createNamespacedRole(actorSystem.getMetadata().getNamespace(), role, null, null, null);
                this.rbacAuthorizationApi.createNamespacedRoleBinding(actorSystem.getMetadata().getNamespace(), roleBinding, null, null, null);
                this.coreApi.createNamespacedService(actorSystem.getMetadata().getNamespace(), service, null, null, null);
                this.appsApi.createNamespacedStatefulSet(actorSystem.getMetadata().getNamespace(), statefulSet, null, null, null);
                // create persistent actors
                for (V1ActorSystemSpecPersistentActors persistentActor : actorSystem.getSpec().getPersistentActors()) {
                    createPersistentActor(actorSystem, persistentActor);
                }
            } catch (ApiException e) {
                log.error(e.getResponseBody(), e);
                return new Result(true);
            }
        }
        return new Result(false);
    }

    private void createPersistentActor(V1ActorSystem actorSystem, V1ActorSystemSpecPersistentActors persistentActor) throws ApiException {
        V1Service service =
                new V1ServiceBuilder()
                        .withNewMetadata()
                        .withName(actorSystem.getMetadata().getName()+"-"+persistentActor.getName().toLowerCase())
                        .addNewOwnerReference()
                        .withName(actorSystem.getMetadata().getName())
                        .withUid(actorSystem.getMetadata().getUid())
                        .withKind(actorSystem.getKind())
                        .withApiVersion(actorSystem.getApiVersion())
                        .endOwnerReference()
                        .addToLabels("io.elasticactors.actorsystem/name", actorSystem.getMetadata().getName())
                        .addToLabels("io.elasticactors.actorsystem/kind","PersistentActor")
                        .addToLabels("io.elasticactors.actorsystem/type",persistentActor.getName())
                        .endMetadata()
                        .withNewSpec()
                        .addToSelector("io.elasticactors.actorsystem/name", actorSystem.getMetadata().getName())
                        .addToSelector("io.elasticactors.actorsystem/kind","PersistentActor")
                        .addToSelector("io.elasticactors.actorsystem/type",persistentActor.getName())
                        .addNewPort()
                        .withName("grpc")
                        .withPort(50051)
                        .withProtocol("TCP")
                        .endPort()
                        .endSpec().build();

        V1DaemonSet daemonSet =
                new V1DaemonSetBuilder()
                        .withNewMetadata()
                        .withName(actorSystem.getMetadata().getName()+"-"+persistentActor.getName().toLowerCase())
                        .addNewOwnerReference()
                        .withName(actorSystem.getMetadata().getName())
                        .withUid(actorSystem.getMetadata().getUid())
                        .withKind(actorSystem.getKind())
                        .withApiVersion(actorSystem.getApiVersion())
                        .endOwnerReference()
                        .addToLabels("io.elasticactors.actorsystem/name", actorSystem.getMetadata().getName())
                        .addToLabels("io.elasticactors.actorsystem/kind","PeristentActor")
                        .addToLabels("io.elasticactors.actorsystem/type",persistentActor.getName())
                        .endMetadata()
                        .withNewSpec()
                        .withNewSelector()
                        .addToMatchLabels("io.elasticactors.actorsystem/name", actorSystem.getMetadata().getName())
                        .addToMatchLabels("io.elasticactors.actorsystem/kind","PersistentActor")
                        .addToMatchLabels("io.elasticactors.actorsystem/type",persistentActor.getName())
                        .endSelector()
                        .withNewTemplate()
                        .withNewMetadata()
                        .addToLabels("io.elasticactors.actorsystem/name", actorSystem.getMetadata().getName())
                        .addToLabels("io.elasticactors.actorsystem/kind","PersistentActor")
                        .addToLabels("io.elasticactors.actorsystem/type",persistentActor.getName())
                        .endMetadata()
                        .withNewSpec()
                        .withServiceAccountName(actorSystem.getMetadata().getName())
                        .withContainers()
                        .addNewContainer()
                        .withName("persistentactor")
                        .withImage(Optional.ofNullable(actorSystem.getSpec().getRuntime().getImage()).orElse("elasticactors/cloudnative-elasticactors")+":"+actorSystem.getSpec().getRuntime().getVersion())
                        .addNewArg("persistentactor")
                        .withNewResources()
                        .addToRequests("cpu", Quantity.fromString("100m"))
                        .addToRequests("memory",Quantity.fromString("256Mi"))
                        .addToLimits("memory",Quantity.fromString("256Mi"))
                        .endResources()
                        .addNewPort()
                        .withName("grpc")
                        .withContainerPort(50051)
                        .withProtocol("TCP")
                        .endPort()
                        .endContainer()
                        .endSpec()
                        .endTemplate()
                        .endSpec().build();

        this.appsApi.createNamespacedDaemonSet(actorSystem.getMetadata().getNamespace(), daemonSet, null, null, null);
        this.coreApi.createNamespacedService(actorSystem.getMetadata().getNamespace(), service, null, null, null);
    }
}
