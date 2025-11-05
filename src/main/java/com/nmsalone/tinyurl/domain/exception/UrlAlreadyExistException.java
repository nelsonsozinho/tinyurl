package com.nmsalone.tinyurl.domain.exception;

public class UrlAlreadyExistException extends RuntimeException {

    public UrlAlreadyExistException(String message) {
        super(message);
    }

}
