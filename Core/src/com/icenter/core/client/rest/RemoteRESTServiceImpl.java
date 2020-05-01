package com.icenter.core.client.rest;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.http.SimpleRequestBuilder;
import com.icenter.core.client.lambda.Function;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.rest.convert.JSONConverter;

public abstract class RemoteRESTServiceImpl implements RemoteRESTService {

    private Function<String,String> url = s -> s;
    private String serviceEndpoint = Strings.Empty;

    @Override
    public final String getServiceEndpoint() {
        return serviceEndpoint;
    }

    @Override
    public void initConverters() { }

    public final void setUrl(Function<String, String> url) {
        this.url = url;
    }

    protected final <T> Request send(JSONObject params, JSONConverter<T> converter, AsyncCallback<T> callback) {

        callback.onSuccess(converter.convertJSONToObject(new JSONString("testssss")));

//        RequestBuilder builder = SimpleRequestBuilder.of(RequestBuilder.POST, url.execute(getServiceEndpoint()));
//        builder.setRequestData(params.toString());
//        try {
//            return builder.sendRequest(params.toString(), new RequestCallback() {
//                @Override
//                public void onResponseReceived(Request request, Response response) {
//                    JSONValue resp = JSONParser.parseStrict(response.getText());
//                    //1. check response is Error Response or not
//                    //2. check response is empty value or not
//                    //2. check response is primitive type or not
//                    if (resp.isNull() != null){
//                        callback.onSuccess(null);
//                    }
//                    callback.onSuccess(converter.convertJSONToObject(JSONParser.parseStrict(response.getText())));
//                }
//
//                @Override
//                public void onError(Request request, Throwable throwable) {
//                    callback.onFailure(throwable);
//                }
//            });
//        }
//        catch (RequestException error) {
//            callback.onFailure(error);
//        }

        return null;
    }

    public static native void showMessage(String text)/*-{
        alert(text);
    }-*/;

}
