package com.icenter.core.client.rest;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
                        JSONParseResult result = null;
                        if(result.isSucceed()){
                           callback.onSuccess(converter.convertJSONToObject(result.getResult()));
                        } else {
                           callback.onFailure(result.getError());
                        }
                        return;
                    }

                    // Try parse server returned custom errors
                    JSONParseResult result = null;
                    if (result.isSucceed()){
                        callback.onSuccess(null);
                    } else {
                        callback.onFailure(result.getError());
                    }
                }

            });
        }
        catch (RequestException error) {
            callback.onFailure(error);
        }
    }

    class JSONParseResult {

        private JSONValue result;
        private boolean succeed;
        private JSONException error;

        private JSONParseResult(boolean succeed, JSONValue value, JSONException error){
            this.succeed = succeed;
            this.result = value;
            this.error = error;
        }

        public JSONParseResult tryParse(String text){
            try {
                JSONValue value = JSONParser.parseStrict(text);
                return new JSONParseResult(true, value, null);
            } catch(JSONException error){
                return new JSONParseResult(false, null, error);
            }
        }

        public JSONValue getResult() {
            return result;
        }

        public boolean isSucceed() {
            return succeed;
        }

        public JSONException getError() {
            return error;
        }
    }

}
