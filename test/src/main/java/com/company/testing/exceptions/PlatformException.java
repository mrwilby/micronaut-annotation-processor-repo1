package com.company.testing.exceptions;

public class PlatformException extends RuntimeException {

    public PlatformException(PlatformErrorMessage errorMessage, Throwable cause) {

        super(mapToErrorMessage(checkErrorMessageNotNull(errorMessage)), cause);
    }

    private static String mapToErrorMessage(PlatformErrorMessage errorMessage) {

        String message;

        final String prefix = String.format("error: %d", errorMessage.getStatus());

        try {
            message = prefix + " ...";
        }
        catch (IllegalArgumentException e) {
            message = prefix;
        }

        return message;
    }

    private static PlatformErrorMessage checkErrorMessageNotNull(PlatformErrorMessage errorMessage) {

        if (errorMessage == null) {
            throw new IllegalArgumentException("Error message cannot be null");
        }

        return errorMessage;
    }
}
