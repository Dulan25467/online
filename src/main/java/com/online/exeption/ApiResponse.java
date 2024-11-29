package com.online.exeption;

public class ApiResponse {
    private String message;
    private boolean success;
    private Object data;

    // Constructor for success responses
    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Constructor for responses with data (e.g., after registration)
    public ApiResponse(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
