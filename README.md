# cloudnative-elasticactors

* make sure skaffold and kubectl are on the $PATH
* gcloud auth configure-docker
* skaffold config set default-repo eu.gcr.io/<your-gke-workspace>
* skaffold config set --kube-context <your-gke-cluster-context>
* kubectl config set-context  <your-gke-cluster-context> --namespace=my-namespace