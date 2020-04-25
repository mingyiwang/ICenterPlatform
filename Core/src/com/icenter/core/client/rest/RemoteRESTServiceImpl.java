package com.icenter.core.client.rest;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.http.RequestBuilderGenerator;
import com.icenter.core.client.json.Converters;

public class RemoteRESTServiceImpl implements RemoteRESTService {


    @Override
    public String getEndPoint() {
        return "";
    }

    @Override public void initConverters() {

    }


    protected Request sendRequest(String url, JSONObject params, String returnTypeName, AsyncCallback<Object> callback ) {
        RequestBuilder builder = RequestBuilderGenerator.of(RequestBuilder.POST, getEndPoint() + url);
        builder.setRequestData(params.toString());
        try {
            return builder.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    callback.onSuccess(Converters.get(returnTypeName)
                                                 .convertJSONToObject(JSONParser.parseStrict(response.getText())));
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
