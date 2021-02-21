package com.alvaroalonso.exception;

public class MapJsonToObjectException extends RuntimeException {
    public MapJsonToObjectException (String message, Exception e) {
        super(message, e);
    }
}
