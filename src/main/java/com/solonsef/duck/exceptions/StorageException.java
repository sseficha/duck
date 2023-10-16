package com.solonsef.duck.exceptions;

public class StorageException extends RuntimeException {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable exc) {
        super(message, exc);
    }

}
