package com.andreipetrushin.service.exception;

/**
 * The exception class
 * in Service layer
 *
 * @author Andrei Petrushin
 * @version 1.0.0
 */
public class ServiceLayerException extends Exception {

    public ServiceLayerException() {
    }

    public ServiceLayerException(String message) {
        super(message);
    }

    public ServiceLayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceLayerException(Throwable cause) {
        super(cause);
    }
}

