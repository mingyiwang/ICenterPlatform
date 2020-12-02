package com.icenter.core.client.rest.error;

public final class ServiceException extends RuntimeException {

    private int statusCode;

    public ServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ServiceException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public ServiceException(Throwable cause, int statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int statusCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.statusCode = statusCode;
    }

    public final int getStatusCode() { return this.statusCode; }


}
