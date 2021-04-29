package com.company.testing.http;

import com.company.testing.Unstable;
import com.company.testing.utilities.LogSeverity;
import io.micronaut.http.HttpStatus;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Unstable
@Documented
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Repeatable(Translations.class)
public @interface Translation {

    HttpStatus from() default HttpStatus.I_AM_A_TEAPOT;

    HttpStatus to() default HttpStatus.I_AM_A_TEAPOT;

    HttpStatus is() default HttpStatus.I_AM_A_TEAPOT;

    boolean propagate() default false;

    boolean swallow() default false;

    String logMessage() default "";

    LogSeverity logMessageSeverity() default LogSeverity.WARNING;

    String description() default "";
}
