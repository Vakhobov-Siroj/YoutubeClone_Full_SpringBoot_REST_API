package com.company.exception;

public class NotPermissionException extends RuntimeException{
    public NotPermissionException(String message) {
        super(message);
    }
}
