package com.icenter.core.client.rest;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.http.RequestBuilderGenerator;
import com.icenter.core.client.rest.parse.ObjectParser;

public class RemoteRESTServiceImpl implements RemoteRESTService {

    @Override
    public String getEndPoint() {
        return "";
    }

    protected Request sendRequest(String methodName, JSONObject params, final ObjectParser objectParser, AsyncCallback<Object> callback ) {
        RequestBuilder builder = RequestBuilderGenerator.of(RequestBuilder.POST, methodName);
        builder.setRequestData(params.toString());

        try {
            return builder.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    Object obj = objectParser.toObject(JSONParser.parseStrict(response.getText()));
                    callback.onSuccess(obj);
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
