package com.company.testing;

import com.company.testing.exceptions.PlatformException;
import io.micronaut.http.HttpStatus;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@MicronautTest
public class InterceptorTest {

    @Inject
    private RemotePrimaryServiceClient primaryService;

    @Inject
    private RemoteInternalService internalService;

    @Inject
    private RemoteInternalServiceClient internalServiceClient;

    @Inject
    private EmbeddedApplication<?> application;

    @AfterEach
    public void shutdownServer() {
        this.internalService.cleanup();
    }

    @Test
    public void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    public void testThrowsPlatformException() {

        final String path = "/internal/throw-platform-exception/404";
        this.internalService.startServer(path, HttpStatus.NOT_FOUND);

        try {
            this.internalServiceClient.throwPlatformExceptionOn404();
            Assertions.fail();
        }
        catch (PlatformException e) {
            Assertions.assertNotNull(e);
        }
    }
}
