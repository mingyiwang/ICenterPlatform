package com.icenter.core.client.rest;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;

public final class RemoteRESTServiceImpl implements RemoteRESTService {

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

    /*public Request sendRequest(String methodName,
                               JSONObject params,
                               final JSONObjectProxy responseObjProxy,
                               AsyncCallback<?> callback ) {
        RequestBuilder builder = new RequestBuilder();
        try {
            return new JSONRequestImpl(builder.sendRequest(params.toString(), new JSONRequestCallback(callback) {
                public Object deserialize(String response) {
                    if (response.length() == 0) {
                        return responseObjProxy.deserializeBlankJSON();
                    }
                    return responseObjProxy.handle(JSONParser.parseLenient(response));
                }
            }));
        }
        catch (RequestException error) {
            callback.onFailure(error);
        }
        return null;
    }*/

}
