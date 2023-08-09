package com.dev.mcc_tools.controllers;

import java.util.HashMap;

public class ErrorResponse extends FormattedResponse {

    private int statusCode;
    private boolean success;
    private HashMap<String, String> message;


    public ErrorResponse(int statusCode, boolean success , HashMap<String, String> message) {
        super();
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HashMap<String, String> getMessage() {
        return message;
    }

    public void setMessage(HashMap<String, String> message) {
        this.message = message;
    }
}
