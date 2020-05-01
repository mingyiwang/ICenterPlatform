package com.icenter.core.client.rest;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.http.SimpleRequestBuilder;
import com.icenter.core.client.rest.convert.JSONConverter;

public abstract class RemoteRESTServiceImpl implements RemoteRESTService {

    @Override
    public String getEndPoint() {
        return "";
    }

    @Override
    public void initConverters() {

    }


    protected final <T> Request send(String url, RequestBuilder.Method method, JSONObject params, JSONConverter<T> converter, AsyncCallback<T> callback) {
        RequestBuilder builder = SimpleRequestBuilder.of(method, getEndPoint() + url);
        builder.setRequestData(params.toString());
        try {
            return builder.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    JSONValue resp = JSONParser.parseStrict(response.getText());
                    //1. check response is Error Response or not
                    //2. check response is empty value or not
                    //2. check response is primitive type or not
                    if (resp.isNull() != null){
                        callback.onSuccess(null);
                    }
                    callback.onSuccess(converter.convertJSONToObject(JSONParser.parseStrict(response.getText())));
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

        return null;
    }



}
