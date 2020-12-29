package org.elasticsoftware.elasticactors.persistentactor.micronaut;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.runtime.Micronaut;

import javax.inject.Singleton;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }

    @Singleton
    static class ObjectMapperBeanEventListener implements BeanCreatedEventListener<ObjectMapper> {
        @Override
        public ObjectMapper onCreated(BeanCreatedEvent<ObjectMapper> event) {
            final ObjectMapper mapper = event.getBean();
            // if we don't set this we fail in native mode on
            // No serializer found for class io.micronaut.discovery.cloud.gcp.GoogleComputeInstanceMetadata
            // and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            return mapper;
        }
    }
}