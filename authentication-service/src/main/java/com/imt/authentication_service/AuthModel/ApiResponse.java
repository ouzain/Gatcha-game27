package com.imt.authentication_service.AuthModel;

public class ApiResponse {
    private String message;
    private boolean success;
    private Object data;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }
}

