package com.icenter.core.client.rest.error;

public class ServiceException extends RuntimeException {

    private int status;

    public ServiceException(String message, int statusCode) {
        super(message);
        this.status = statusCode;
    }

    public int getStatusCode() {
        return status;
    }

}
