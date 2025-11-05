package com.nmsalone.tinyurl.adapter.in.web.handler;

import com.nmsalone.tinyurl.domain.exception.UrlAlreadyExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlAlreadyExistException.class)
    public ResponseEntity<Map<String, List<String>>> handleUrlAlreadyExists(UrlAlreadyExistException ex) {
        final List<String> errors = List.of(ex.getMessage());
        return new ResponseEntity<>(getErrorsMapping(errors), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    private Map<String,List<String>> getErrorsMapping(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

}
