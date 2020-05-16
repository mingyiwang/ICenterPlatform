package com.icenter.core.client.rest;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.json.JSON;
import com.icenter.core.client.json.JSONParseResult;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.rest.convert.JSONConverter;
import com.icenter.core.client.rest.error.ServiceError;

import java.util.Objects;

public abstract class RemoteRESTServiceImpl implements RemoteRESTService {

    private String serviceEndPoint = Strings.Empty;

    @Override
    public final String getServiceEndPoint() {
        return this.serviceEndPoint;
    }

    @Override
    public final void setServiceEndPoint(String endPoint){
        this.serviceEndPoint = endPoint;
    }

    protected final <T> void send(String routeName, JSONValue params, JSONConverter<T> converter, AsyncCallback<T> callback) {
        Objects.requireNonNull(converter);
        Objects.requireNonNull(callback);

        RequestBuilder.Method httpMethod = params == null ? RequestBuilder.GET : RequestBuilder.POST;
        RequestBuilder builder = new RequestBuilder(httpMethod, getServiceEndPoint() + "/" + routeName);
        builder.setRequestData(params.toString());
        try {
             builder.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onError(Request request, Throwable throwable) {
                    callback.onFailure(throwable);
                }

                @Override
                public void onResponseReceived(Request request, Response response) {
                    Objects.requireNonNull(response);
                    if (response.getStatusCode() == Response.SC_OK){
                        JSONParseResult result = JSON.parseStrict(response.getText());
                        if(result.isSucceed()){
                           callback.onSuccess(converter.convertJSONToObject(result.getResult()));
                        }
                        else {
                           callback.onFailure(result.getError());
                        }
                        return;
                    }

                    // Try parse server returned custom errors
                    JSONParseResult result = JSON.parseStrict(response.getText());
                    if (result.isSucceed()){
                        JSONValue value = result.getResult();
                        // Parse Service Error
                        ServiceError.parse(value);
                    }
                    else {
                        callback.onFailure(result.getError());
                    }
                }

            });
        }
        catch (RequestException error) {
            callback.onFailure(error);
        }
    }

}
