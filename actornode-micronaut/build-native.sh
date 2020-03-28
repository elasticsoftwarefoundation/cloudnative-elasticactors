#!/usr/bin/env bash
mvn -s ../../settings.xml package
docker build . -t ${IMAGE}
docker push ${IMAGE}