package com.icenter.core.client.rest;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.http.SimpleRequestBuilder;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.rest.convert.JSONConverter;

public abstract class RemoteRESTServiceImpl implements RemoteRESTService {

    private String serviceEntryPoint = Strings.Empty;

    @Override
    public final String getServiceEntryPoint() {
        return this.serviceEntryPoint;
    }

    public final void setServiceEntryPoint(String entryPoint){
        this.serviceEntryPoint = entryPoint;
    }

    protected final <T> Request send(JSONValue params, JSONConverter<T> converter, AsyncCallback<T> callback) {
        RequestBuilder builder = SimpleRequestBuilder.of(RequestBuilder.POST, getServiceEntryPoint());
        builder.setRequestData(params.toString());
        try {
            return builder.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    // 1. check response is ok or not
                    // 2. check response is null or not
                    // 3. check response text is valid json or not
                    // 4. check response is error or not

                    JSONValue resp = JSONParser.parseStrict(response.getText());
                    callback.onSuccess(converter.convertJSONToObject(resp));
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
