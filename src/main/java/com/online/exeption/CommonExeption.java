package com.online.exeption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonExeption extends RuntimeException {
    private String message;
    private String errorCode;
    private String status;
    private String timestamp;
    private String path;
    private Map<String, String> errors = new HashMap<>();

    // Constructor to create an exception with a message
    public CommonExeption(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    // Method to add errors
    public void addError(String key, String value) {
        this.errors.put(key, value);
    }

    // Method to set the response status
    public void setStatus(String status) {
        this.status = status;
    }

    // Method to set error code
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    // Utility method to populate the error response
    public static CommonExeption createError(String message, String errorCode, String path) {
        CommonExeption exception = new CommonExeption(message);
        exception.setErrorCode(errorCode);
        exception.setStatus("400"); // Default to 400, can be changed based on logic
        exception.setPath(path);
        return exception;
    }
}