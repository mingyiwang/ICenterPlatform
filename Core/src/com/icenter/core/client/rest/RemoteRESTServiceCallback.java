package com.icenter.core.client.rest;

import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.http.HttpHandler;
import com.icenter.core.client.json.JSON;
import com.icenter.core.client.json.JSONParseResult;
import com.icenter.core.client.rest.convert.JSONConverter;
import com.icenter.core.client.rest.error.MalformedJSONException;
import com.icenter.core.client.rest.error.ServiceException;
import com.icenter.core.client.rest.error.UnexpectedJSONException;
import java.util.Objects;

public final class RemoteRESTServiceCallback<T> implements HttpHandler {

    private JSONConverter<T> converter;
    private AsyncCallback<T> callback;

    public RemoteRESTServiceCallback(JSONConverter<T> converter, AsyncCallback<T> callback) {
        Objects.requireNonNull(converter);
        Objects.requireNonNull(callback);

        this.converter = converter;
        this.callback  = callback;
    }

    /**
     * This type of error happens on Internet level such as Request time out, connection lost etc.
     * It should't be handled by AsyncCallback class.
     * @param error
     */
    @Override
    public void handleError(Throwable error) {
        if(error instanceof RequestException){

        }
        // callback.onFailure(error);
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

        JSONParseResult result = JSON.parse(response.getText());

        // It is not a json message
        if(!result.isSucceed()){
            callback.onFailure(new MalformedJSONException(response.getText() + " is not a valid json.", result.getError())); // Create MalformedJSONException, Returns is not a JSON
        }
        else {
            T object;
            try {
                object = converter.convertJSONToObject(result.getResult());
            }
            catch(Exception error) {
                object = null;
                callback.onFailure(new UnexpectedJSONException(response.getText() + " is not expected json.", error)); // Create UnexpectedJSONException
            }

            // We dun handle delegate errors
            callback.onSuccess(object);
        }
    }

}
