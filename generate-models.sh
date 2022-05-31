LOCAL_MANIFEST_FILE=$(pwd)/src/main/crds/crds.yaml
docker run \
  --rm \
  -v "$LOCAL_MANIFEST_FILE":"$LOCAL_MANIFEST_FILE" \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v "$(pwd)":"$(pwd)" \
  -ti \
  --network host \
  ghcr.io/yue9944882/crd-model-gen:v1.0.6 \
  /generate.sh \
  -u $LOCAL_MANIFEST_FILE \
  -n io.elasticactors \
  -p org.elasticsoftware.elasticactors.cloudnative.operator \
  -o "$(pwd)"