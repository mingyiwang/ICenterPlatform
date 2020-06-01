package com.icenter.core.client.rest;

import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.http.HttpCallback;
import com.icenter.core.client.rest.convert.JSONConverter;

public class RemoteRESTServiceCallback implements HttpCallback {

    private JSONConverter converter;
    private AsyncCallback callback;

    public RemoteRESTServiceCallback(JSONConverter converter, AsyncCallback callback) {
        this.converter = converter;
        this.callback = callback;
    }

    @Override
    public void handleError(Throwable error) {
        if(error instanceof RequestException){

        }
        // Service Error
        // JSON Error

    }

    @Override
    public void handleResponse(Response response) {

    }

}
