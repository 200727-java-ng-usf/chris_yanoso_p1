package com.revature.exceptions;

public class ResourcePersistenceException extends RuntimeException {

    public ResourcePersistenceException() {
        super();
    }

    public ResourcePersistenceException(String message){
        super(message);
    }
}
