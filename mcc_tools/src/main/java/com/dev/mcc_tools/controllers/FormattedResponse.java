package com.dev.mcc_tools.controllers;

public class FormattedResponse {

    private int statusCode;
    private boolean success;
    private Object data;


    public FormattedResponse(int statusCode, boolean success , Object data) {
        this.statusCode = statusCode;
        this.success = success;
        this.data = data;
    }

    public FormattedResponse() {

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
