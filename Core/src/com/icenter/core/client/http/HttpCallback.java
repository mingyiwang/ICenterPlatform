package com.icenter.core.client.http;

import com.google.gwt.http.client.Response;

public interface HttpCallback {

    void handleError(Throwable error);
    void handleResponse(Response response);

}
