package com.solonsef.duck.rest;

import com.solonsef.duck.exceptions.EntityNotFoundException;
import com.solonsef.duck.exceptions.StorageException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<GlobalErrorResponse> handleEntityNotFoundException(EntityNotFoundException exc) {
        GlobalErrorResponse response = new GlobalErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<GlobalErrorResponse> handleConstraintViolationException(ConstraintViolationException exc) {
        GlobalErrorResponse response = new GlobalErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<GlobalErrorResponse> handleStorageException(StorageException exc) {
        exc.printStackTrace();
        GlobalErrorResponse response = new GlobalErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exc.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<GlobalErrorResponse> handleException(Exception exc) {
        exc.printStackTrace();
        GlobalErrorResponse response = new GlobalErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Oops something went wrong :(", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
