package com.icenter.core.client.rest;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.http.HttpCallback;
import com.icenter.core.client.json.JSON;
import com.icenter.core.client.json.JSONParseResult;
import com.icenter.core.client.rest.convert.JSONConverter;
import com.icenter.core.client.rest.error.MalformedJSONException;
import com.icenter.core.client.rest.error.ServiceException;
import com.icenter.core.client.rest.error.UnexpectedJSONException;
import java.util.Objects;

public final class RemoteRESTServiceCallback<T> implements HttpCallback {

    private JSONConverter<T> converter;
    private AsyncCallback<T> callback;

    public RemoteRESTServiceCallback(JSONConverter<T> converter, AsyncCallback<T> callback) {
        Objects.requireNonNull(converter);
        Objects.requireNonNull(callback);

        this.callback  = callback;
        this.converter = converter;
    }

    /**
     * This type of error happens on Internet level such as Request time out, connection lost etc.
     * @param error
     */
    @Override
    public void handleError(Throwable error) {
        callback.onFailure(error);
    }

    /**
     * This type of error happens when response is received.
     * @param response
     */
    @Override
    public void handleResponse(Response response) {
        if(response.getStatusCode() != Response.SC_OK){
           callback.onFailure(new ServiceException(response.getText(), response.getStatusCode())); // ServiceException
           return;
        }

        // It is not a json message
        JSONParseResult result = JSON.parse(response.getText());
        if(!result.isSucceed()){
            callback.onFailure(new MalformedJSONException(response.getText(), result.getError())); // Create MalformedJSONException, Returns is not a JSON
        }
        else {
            T object = null;
            try {
                object = converter.convertJSONToObject(result.getResult());
            }
            catch(Exception error) {
                object = null;
                callback.onFailure(new UnexpectedJSONException(error)); // Create UnexpectedJSONException
            }

            // We dun handle delegate errors
            callback.onSuccess(object);
        }
    }

}
