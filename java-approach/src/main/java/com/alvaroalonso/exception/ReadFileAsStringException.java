package com.alvaroalonso.exception;

public class ReadFileAsStringException extends RuntimeException {
    public ReadFileAsStringException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }
}
