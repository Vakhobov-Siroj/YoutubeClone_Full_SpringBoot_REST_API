package com.company.exception;

public class AlreadyExistPhone extends RuntimeException {
    public AlreadyExistPhone(String massage) {
        super(massage);
    }
}
