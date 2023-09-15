package com.example.demo.exception;

public class ApiRequestExceprion extends RuntimeException{
    public ApiRequestExceprion(String message){
        super(message);
    }
    public ApiRequestExceprion(String message, Throwable cause){
        super(message, cause);
    }
}
