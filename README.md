## Run locally (make sure local kubectl points to the correct cluster)

mvn mn:run -Dmn.appArgs=operator

## prepare k8s cluster

kubectl apply -f src/main/crds
kubectl apply -f src/main/k8s

## Run operator in cluster (not using manifests)

kubectl run elasticactors-operator --image=elasticactors/cloudnative-elasticactors -- operator

## deploy test-actorsystem (using docker-image from docker hub)

kubectl apply -f src/test/resources


