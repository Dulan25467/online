package com.online.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonExeption.class)
    public ResponseEntity<CommonExeption> handleCommonException(CommonExeption exception) {
        // Set the response status code based on the error code
        HttpStatus status = HttpStatus.BAD_REQUEST; // Default to 400, but you can modify based on errorCode

        // You can customize the response based on your needs, such as adding more information
        return new ResponseEntity<>(exception, status);
    }
}
