package com.akintomiwa.pokedox.exception;

public class ExternalServerException extends RuntimeException {
    private static String errorMessage = "Please try again, something went wrong on one of the servers we rely on! 😥";

    public ExternalServerException() {
        super(errorMessage);
    }
    public ExternalServerException(String message) {
        super(message.isEmpty() ? errorMessage : message);
    }
}
