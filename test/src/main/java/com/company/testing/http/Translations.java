package com.company.testing.http;

import com.company.testing.Unstable;
import io.micronaut.http.HttpStatus;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Unstable
@Documented
@Retention(RUNTIME)
@Target({ElementType.METHOD})
public @interface Translations {

    Translation[] value() default {};

    boolean translateUnmappedErrors() default false;

    HttpStatus translateTo() default HttpStatus.INTERNAL_SERVER_ERROR;

    String description() default "";

    boolean throwNotFound() default false;

    boolean throwPlatformException() default false;
}
