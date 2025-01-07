package com.training.restaurant.controllers;

import com.training.restaurant.utils.exceptions.BadRequestException;
import com.training.restaurant.utils.exceptions.ExceptionResponse;
import com.training.restaurant.utils.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    private ExceptionResponse exceptionResponse;
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @Autowired
    public ExceptionHandlerController(ExceptionResponse exceptionResponse) {
        this.exceptionResponse = exceptionResponse;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Void> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException e) {
        createBodyResponse(e.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    private void createBodyResponse(String message){
        exceptionResponse.restart();
        logger.error(message);
        exceptionResponse.error = true;
        exceptionResponse.message = message;
    }
}
