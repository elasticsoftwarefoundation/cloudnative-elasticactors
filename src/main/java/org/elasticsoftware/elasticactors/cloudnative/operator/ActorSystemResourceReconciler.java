package org.elasticsoftware.elasticactors.cloudnative.operator;

import io.kubernetes.client.extended.controller.reconciler.Request;
import io.kubernetes.client.extended.controller.reconciler.Result;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.kubernetes.client.informer.Informer;
import io.micronaut.kubernetes.client.operator.Operator;
import io.micronaut.kubernetes.client.operator.OperatorResourceLister;
import io.micronaut.kubernetes.client.operator.ResourceReconciler;
import org.elasticsoftware.elasticactors.cloudnative.operator.models.V1ActorSystem;
import org.elasticsoftware.elasticactors.cloudnative.operator.models.V1ActorSystemList;

@Requires(env = "operator")
@Operator(informer = @Informer(apiType = V1ActorSystem.class, apiListType = V1ActorSystemList.class))
public class ActorSystemResourceReconciler implements ResourceReconciler<V1ActorSystem> {
    @NonNull
    @Override
    public Result reconcile(@NonNull Request request,@NonNull OperatorResourceLister<V1ActorSystem> lister) {
        return new Result(false);
    }
}
