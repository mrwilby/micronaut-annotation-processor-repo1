package com.company.testing;

import io.micronaut.http.HttpStatus;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.BlockingHandler;

import javax.inject.Singleton;
import java.net.URI;

@Singleton
public class RemoteInternalService {

    public static final String HOST_AND_PORT = "http://localhost:8090";

    private Undertow server;

    public void cleanup() {

        try {
            if (this.server != null) {
                this.server.stop();
                this.server = null;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(250);
        }
        catch (InterruptedException e) {
        }
    }

    public void startServer(String path, HttpStatus responseStatus) {

        final RoutingHandler routingHandler = Handlers.routing()
                .post(path,
                        new BlockingHandler((exchange) -> {
                            exchange.setStatusCode(responseStatus.getCode());
                        })
                );

        startServer(routingHandler);
    }

    public void startServer(RoutingHandler routingHandler) {

        try {
            URI host = new URI(HOST_AND_PORT);

            this.server = Undertow.builder()
                    .addHttpListener(host.getPort(), host.getHost())
                    .setHandler(routingHandler)
                    .build();

            this.server.start();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
