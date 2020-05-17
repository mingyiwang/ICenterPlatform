package com.icenter.core.client.rest;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.json.JSON;
import com.icenter.core.client.json.JSONParseResult;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.rest.convert.JSONConverter;
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

    protected final <T> void send(String routeName, JSONValue params, final JSONConverter<T> converter, final AsyncCallback<T> callback) {
        Objects.requireNonNull(converter);
        Objects.requireNonNull(callback);
        Objects.requireNonNull(routeName);

        RequestBuilder.Method httpMethod = params == null ? RequestBuilder.GET : RequestBuilder.POST;
        RequestBuilder builder = new RequestBuilder(httpMethod, getServiceEndPoint() + "/" + routeName);
        builder.setHeader("Content-type", "application/json; charset=utf-8");
        builder.setRequestData(params.toString());

        try {
            builder.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onError(Request request, Throwable throwable) {
                    callback.onFailure(throwable);
                }

                @Override
                public void onResponseReceived(Request request, Response response) {
                    if(response.getStatusCode() != Response.SC_OK){
                       callback.onFailure(new Exception(response.getText())); // remote server error
                       return;
                    }

                    // It is not a json message
                    JSONParseResult result = JSON.parseStrict(response.getText());
                    if(!result.isSucceed()){
                        callback.onFailure(result.getError()); // Malformed JSONException
                    }
                    else {
                        T object = null;
                        try {
                            object = converter.convertJSONToObject(result.getResult()); // Exception when convert json to object
                        }
                        catch(Exception error) {
                            // not a expected json message
                            callback.onFailure(error);
                        }

                        callback.onSuccess(object);
                    }
                }
            });
        }
        catch (RequestException e) {
           callback.onFailure(e);
        }
    }


}
