#FROM oracle/graalvm-ce:20.0.0-java8 as graalvm
# For JDK 11
FROM oracle/graalvm-ce:20.0.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/actornode-micronaut
WORKDIR /home/app/actornode-micronaut

RUN native-image --no-server -cp target/actornode-micronaut-*.jar

FROM registry.access.redhat.com/ubi8/ubi-minimal:8.1
EXPOSE 8080
COPY --from=graalvm /home/app/actornode-micronaut/actornode-micronaut /app/actornode-micronaut
ENTRYPOINT ["/app/actornode-micronaut"]
