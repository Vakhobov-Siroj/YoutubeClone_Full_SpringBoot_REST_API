package com.company.exception;

public class AlreadyExistNameAndSurName extends RuntimeException {
    public AlreadyExistNameAndSurName(String massage) {
        super(massage);
    }
}
