package com.company.testing;

import io.micronaut.context.annotation.Requires;

@Requires(property = "micronaut.security.enabled", value = "true", defaultValue = "false")
public @interface SecurityInfrastructure {
}
