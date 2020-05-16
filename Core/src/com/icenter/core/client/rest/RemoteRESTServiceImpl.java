package com.icenter.core.client.rest;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.http.SimpleRequestBuilder;
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

    protected final <T> void send(JSONValue params, JSONConverter<T> converter, AsyncCallback<T> callback) {
        Objects.requireNonNull(converter);
        Objects.requireNonNull(callback);

        RequestBuilder.Method httpMethod = params == null ? RequestBuilder.GET : RequestBuilder.POST;
        RequestBuilder builder = new RequestBuilder(httpMethod, getServiceEndPoint());
        builder.setRequestData(params.toString());

        try {
             builder.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    Objects.requireNonNull(response);

                    if (response.getStatusCode() != Response.SC_OK){
                        //1. response is json
                        //2. unexpected error message
                        response.getText();
                        return;
                    }

                    // 3. check response text is valid json or not
                    // 4. check response is error or not
                    try {
                        callback.onSuccess(converter.convertJSONToObject(JSONParser.parseStrict(response.getText())));
                    }
                    catch(JSONException jsonError){
                        // handle error
                    }
                    catch(Exception error){
                        // handle error
                    }
                }

                @Override
                public void onError(Request request, Throwable throwable) {
                    callback.onFailure(throwable);
                }
            });
        }
        catch (RequestException error) {
            callback.onFailure(error);
        }
    }

}
