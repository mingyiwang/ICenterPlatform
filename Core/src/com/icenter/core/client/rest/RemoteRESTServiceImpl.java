package com.icenter.core.client.rest;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.icenter.core.client.rest.parse.ObjectParser;

public class RemoteRESTServiceImpl implements RemoteRESTService {

    @Override
    public String getSerializationPolicyName() {
        return null;
    }

    @Override
    public String getServiceEntryPoint() {
        return null;
    }

    @Override
    public void setRpcRequestBuilder(RpcRequestBuilder rpcRequestBuilder) {

    }

    @Override
    public void setServiceEntryPoint(String s) {

    }

    public Request sendRequest(String methodName,
                               JSONObject params,
                               final ObjectParser objectParser,
                               AsyncCallback<Object> callback ) {

        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, methodName);
        try {
            return builder.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                     callback.onSuccess(objectParser.parse(JSONParser.parseStrict(response.getText())));
                }

                @Override
                public void onError(Request request, Throwable throwable) {

                }
            });
        }
        catch (RequestException error) {
            callback.onFailure(error);
        }
        return null;
    }

}
