package com.company.testing;

import com.company.testing.exceptions.PlatformErrorMessage;
import com.company.testing.http.RemoteCall;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client(value = "/", errorType = PlatformErrorMessage.class)
@RemoteCall
public interface RemotePrimaryServiceClient {

    @Post(value = "/public", produces = MediaType.TEXT_PLAIN, consumes = MediaType.TEXT_PLAIN)
    HttpResponse<String> invoke(@Body String path);
}
