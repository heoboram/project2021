package com.project2021.web.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.MissingFormatArgumentException;

@RestControllerAdvice
public class  CustomizedResponseEntityExceptionHandler  {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleCommonException(WebRequest request, Exception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingFormatArgumentException.class)
    public ResponseEntity<Object> handleMisingFormatArgumentException(WebRequest request, Exception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("error", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
