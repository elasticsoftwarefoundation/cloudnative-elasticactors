#FROM oracle/graalvm-ce:20.0.0-java8 as graalvm
# For JDK 11
FROM oracle/graalvm-ce:20.3.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/persistentactor-micronaut
WORKDIR /home/app/persistentactor-micronaut

RUN native-image --no-server -cp target/persistentactor-micronaut-*.jar

FROM registry.access.redhat.com/ubi8/ubi-minimal:8.1
EXPOSE 8080
COPY --from=graalvm /home/app/persistentactor-micronaut/persistentactor-micronaut /app/persistentactor-micronaut
ENTRYPOINT ["/app/persistentactor-micronaut"]
