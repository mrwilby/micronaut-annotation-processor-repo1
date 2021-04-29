package com.company.testing.exceptions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlatformErrorMessage {

    private final int status;
    private final String description;
    private final Collection<String> errors;
    private final PlatformErrorCategory category;

    public PlatformErrorMessage() {

        this(
                500,
                new PlatformErrorCategory("Error"),
                "",
                null
        );
    }

    @JsonCreator
    public PlatformErrorMessage(@JsonProperty("status") int statusCode,
                                @JsonProperty("message") PlatformErrorCategory category,
                                @JsonProperty("description") String description,
                                @JsonProperty("errors") Collection<String> errors) {

        this.status = statusCode;
        this.category = checkNotNull(category, "Invalid category");
        this.description = description;

        List<String> errorList = new ArrayList<>();
        if (errors != null && errors.size() > 0) {
            errorList.addAll(errors);
        }

        this.errors = errorList;
    }

    public int getStatus() {
        return this.status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlatformErrorMessage that = (PlatformErrorMessage) o;
        return description.equals(that.description) &&
                Objects.equals(errors, that.errors) &&
                category.equals(that.category) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, errors, category, status);
    }

    private static <T> T checkNotNull(T object, String errorMessage) {

        if (object == null) {
            throw new NullPointerException(errorMessage);
        }
        return object;
    }
}
