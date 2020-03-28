package org.elasticsoftware.elasticactors.actorgateway.web;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.elasticsoftware.elasticactors.grpc.NodeServiceGrpc;
import org.elasticsoftware.elasticactors.grpc.StateReply;
import org.elasticsoftware.elasticactors.grpc.StateRequest;

import javax.inject.Inject;

@Controller
public class GatewayController {
    @Inject
    NodeServiceGrpc.NodeServiceBlockingStub nodeService;

    @Get(produces = MediaType.TEXT_PLAIN)
    public String index() {
        StateReply reply = nodeService.requestState(StateRequest.newBuilder().setActorName("test").build());
        return "Got State: "+reply.getStateGeneration();
    }
}
