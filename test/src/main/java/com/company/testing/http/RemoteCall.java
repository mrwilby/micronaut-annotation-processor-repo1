package com.company.testing.http;

import com.company.testing.SecurityInfrastructure;
import io.micronaut.aop.Around;
import io.micronaut.context.annotation.Type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SecurityInfrastructure
@Around
@Type(RemoteCallInterceptor.class)
public @interface RemoteCall {
}
