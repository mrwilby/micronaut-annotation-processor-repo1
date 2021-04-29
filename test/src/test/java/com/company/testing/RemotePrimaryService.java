package com.company.testing;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import javax.inject.Inject;

@Controller
public class RemotePrimaryService {

    @Inject
    private RemoteInternalServiceClient internalServiceClient;

    @Status(HttpStatus.ACCEPTED)
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post(uri = "/public", produces = MediaType.TEXT_PLAIN, consumes = MediaType.TEXT_PLAIN)
    public HttpResponse<?> invokeInternalService(@Body String internalServicePath) {

        switch (internalServicePath) {

            case "/internal/throw-platform-exception/404":
                this.internalServiceClient.throwPlatformExceptionOn404();
                break;

            default:
                throw new RuntimeException("Not implemented");
        }

        return HttpResponse.serverError(); // never reached
    }
}
