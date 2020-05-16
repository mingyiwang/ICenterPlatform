package com.icenter.core.client.rest;

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class ServiceCallback<T> implements AsyncCallback<T> {

    @Override
    public void onFailure(Throwable throwable) {
        // handle throwable


    }

}
