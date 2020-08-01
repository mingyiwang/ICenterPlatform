package com.icenter.core.client.rest.error;

public class ServiceException extends RuntimeException {

    private int statusCode;
    private String message;

    public ServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
