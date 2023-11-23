package com.adsbdecoder.exception;

/**
 * Custom exception class for decoding errors.
 */
public class CustomDecoderException extends Exception {

    /**
     * Constructs a new CustomDecoderException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public CustomDecoderException(String message, Throwable cause) {
        super(message, cause);
    }
}
