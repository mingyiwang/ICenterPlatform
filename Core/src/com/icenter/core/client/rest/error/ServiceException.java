package com.icenter.core.client.rest.error;

public class ServiceException extends RuntimeException {

    private int statusCode;

    public ServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
