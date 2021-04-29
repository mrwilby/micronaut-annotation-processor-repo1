package com.company.testing;

import com.company.testing.http.RemoteCall;
import com.company.testing.http.Translation;
import com.company.testing.http.Translations;
import com.company.testing.utilities.LogSeverity;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client(value = RemoteInternalService.HOST_AND_PORT)
@RemoteCall
public interface RemoteInternalServiceClient {

    @Post("/internal/no-error")
    @Translations(translateUnmappedErrors = true, translateTo = HttpStatus.SERVICE_UNAVAILABLE)
    HttpResponse<String> returnOk();

    @Post("/internal/any-remote-error")
    @Translations(translateUnmappedErrors = true, translateTo = HttpStatus.FORBIDDEN)
    HttpResponse<String> returnError();

    @Post("/internal/translate-specific-errors")
    @Translations(
            translateUnmappedErrors = true,
            translateTo = HttpStatus.PRECONDITION_REQUIRED,
            value = {
                    @Translation(is = HttpStatus.PAYMENT_REQUIRED),
                    @Translation(from = HttpStatus.BAD_REQUEST, to = HttpStatus.UNAUTHORIZED),
                    @Translation(from = HttpStatus.CONFLICT, to = HttpStatus.UNAUTHORIZED),
                    @Translation(from = HttpStatus.TOO_MANY_REQUESTS, to = HttpStatus.FORBIDDEN)
            }
    )
    HttpResponse<String> translateSpecificErrors();

    @Post("/internal/remote-401-to-200")
    @Translations(
            value = {
                    @Translation(from = HttpStatus.UNAUTHORIZED, to = HttpStatus.OK)
            }
    )
    HttpResponse<String> remote401to200();

    @Post("/internal/remote-404-to-403")
    @Translations(
            value = {
                    @Translation(
                            from = HttpStatus.NOT_FOUND,
                            to = HttpStatus.FORBIDDEN,
                            description = "You are not allowed"
                    )
            }
    )
    HttpResponse<String> remote404to403();

    @Post("/internal/remote-404-to-403/primitive")
    @Translations(
            value = {
                    @Translation(
                            from = HttpStatus.NOT_FOUND,
                            to = HttpStatus.FORBIDDEN
                    )
            }
    )
    String remote404to403Primitive();

    @Post("/internal/remote-403-to-401")
    @Translations(
            value = {
                    @Translation(from = HttpStatus.FORBIDDEN, to = HttpStatus.UNAUTHORIZED)
            }
    )
    String remote403to401();

    @Post("/internal/remote-401-to-404")
    @Translations(
            value = {
                    @Translation(
                            from = HttpStatus.UNAUTHORIZED,
                            to = HttpStatus.NOT_FOUND,
                            description = "Custom"
                    )
            }
    )
    HttpResponse<?> remote401to404();

    @Post("/internal/remote-401-to-404/throw")
    @Translations(
            throwNotFound = true,
            value = {
                    @Translation(
                            from = HttpStatus.UNAUTHORIZED,
                            to = HttpStatus.NOT_FOUND,
                            description = "Custom"
                    )
            }
    )
    HttpResponse<?> remote401to404Throw();

    @Post("/internal/remote-pass-through-401")
    @Translations(
            value = {
                    @Translation(is = HttpStatus.UNAUTHORIZED, propagate = true)
            }
    )
    String remotePassThrough401();

    @Post("/internal/remote-pass-through-403")
    @Translations(
            value = {
                    @Translation(is = HttpStatus.FORBIDDEN, propagate = true)
            }
    )
    String remotePassThrough403();

    @Post("/internal/remote-propagate-negative")
    @Translations(
            value = {
                    @Translation(
                            // The test case never returns this error
                            from = HttpStatus.METHOD_NOT_ALLOWED,
                            to = HttpStatus.METHOD_NOT_ALLOWED
                    )
            }
    )
    String remotePropagateNegative();

    @Post("/internal/throw-404-not-found/no-remapping/http-response")
    @Translations(throwNotFound = true)
    HttpResponse<String> throwNotFoundNoMappingHttpResponse();

    @Post("/internal/throw-404-not-found/no-remapping/primitive")
    @Translations(throwNotFound = true, description = "Custom Description")
    String throwNotFoundNoMappingPrimitiveWithCustomDescription();

    @Post("/internal/throw-404-not-found/with-remapping/http-response")
    @Translations(
            throwNotFound = true,
            value = {
                    @Translation(
                            from = HttpStatus.NOT_FOUND,
                            to = HttpStatus.METHOD_NOT_ALLOWED
                    )
            }
    )
    HttpResponse<String> throwNotFoundWithRemapping();

    @Post("/internal/throw-platform-exception/404")
    @Translations(
            throwPlatformException = true,
            translateUnmappedErrors = true,
            translateTo = HttpStatus.UNAUTHORIZED,
            value = {
                    @Translation(from = HttpStatus.NOT_FOUND, to = HttpStatus.FORBIDDEN)
            }
    )
    HttpResponse<String> throwPlatformExceptionOn404();

    @Post("/internal/throw-platform-exception/409")
    @Translations(
            throwPlatformException = true,
            translateUnmappedErrors = true,
            translateTo = HttpStatus.UNAUTHORIZED,
            value = {
                    @Translation(from = HttpStatus.CONFLICT, to = HttpStatus.FORBIDDEN)
            }
    )
    HttpResponse<String> throwPlatformExceptionOn409();

    @Post("/internal/swallow/409-conflict/http-response")
    @Translations(
            value = {
                    @Translation(
                            is = HttpStatus.CONFLICT,
                            swallow = true,
                            logMessage = "Conflict swallowed",
                            logMessageSeverity = LogSeverity.ERROR
                    )
            }
    )
    HttpResponse<String> swallowConflictHttpResponse();

    @Post("/internal/swallow/409-conflict/primitive")
    @Translations(
            value = {
                    @Translation(
                            is = HttpStatus.CONFLICT,
                            swallow = true
                    )
            }
    )
    String swallowConflictPrimitive(); // developer error, must return HttpResponse
}
