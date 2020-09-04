package com.revature.exceptions;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(){
        super();
    }

    public AuthenticationException(String message){
        super(message);

    }
}