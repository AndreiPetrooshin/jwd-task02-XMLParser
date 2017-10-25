package com.andreipetrushin.dao.exception;

/**
 * The exception class
 * in DAO layer
 *
 * @author Andrei Petrushin
 * @version 1.0.0
 */
public class DAOLayerException extends Exception {


    public DAOLayerException() {
    }

    public DAOLayerException(String message){
        super(message);
    }

    public DAOLayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOLayerException(Throwable cause) {
        super(cause);
    }
}

