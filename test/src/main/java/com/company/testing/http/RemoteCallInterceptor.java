package com.company.testing.http;

import com.company.testing.exceptions.PlatformErrorMessage;
import com.company.testing.exceptions.PlatformException;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.convert.ConversionContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class RemoteCallInterceptor implements MethodInterceptor<Object, Object> {

    private static final ArgumentConversionContext<Translation[]>
            CONVERSION_CONTEXT = ConversionContext.of(Translation[].class);

    private static final String MEMBER_NAME_THROW_PLATFORM_EXCEPTION = "throwPlatformException";
    private static final String MEMBER_NAME_VALUE = "value";

    @Inject
    public RemoteCallInterceptor() {
    }

    @Override
    public Object intercept(MethodInvocationContext<Object, Object> context) {

        Optional<AnnotationValue<Translations>> optionalAnnotation = context.findAnnotation(Translations.class);
        if (!optionalAnnotation.isPresent()) {
            return context.proceed();
        }

        readAnnotationMetadata(optionalAnnotation.get());

        return context.proceed();
    }

    private void readAnnotationMetadata(AnnotationValue<Translations> annotationValue) {

        final Optional<Translation[]> values = annotationValue.get(MEMBER_NAME_VALUE, CONVERSION_CONTEXT);
        if (!values.isPresent()) {
            throw new RuntimeException("Missing value");
        }

        final boolean throwPlatformException = annotationValue.get(MEMBER_NAME_THROW_PLATFORM_EXCEPTION, Boolean.class)
                .orElse(false);

        // FIXME: For throwPlatformException, the annotation default is 'false'
        //        but when it is applied in the test case, it's overriden to 'true'.
        //        We therefore expect 'true' and throw if it is is the default (false).
        if (throwPlatformException) {
            final PlatformErrorMessage m = new PlatformErrorMessage();
            throw new PlatformException(m, null);
        }
    }
}

