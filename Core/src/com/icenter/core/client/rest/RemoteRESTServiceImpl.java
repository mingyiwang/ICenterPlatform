package com.icenter.core.client.rest;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.http.SimpleRequestBuilder;
import com.icenter.core.client.lambda.Function;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.rest.convert.JSONConverter;

public abstract class RemoteRESTServiceImpl implements RemoteRESTService {

    private Function<String, String> urlFormatter = s -> s;
    private String serviceEndpoint = Strings.Empty;

    @Override
    public final String getServiceEndpoint() {
        return this.serviceEndpoint;
    }

    public final void setUrlFormatter(Function<String, String> urlFormatter) {
        this.urlFormatter = urlFormatter;
    }

    public final Function<String,String> getUrlFormatter() {
        return this.urlFormatter;
    }

    protected final <T> Request send(JSONValue params, JSONConverter<T> converter, AsyncCallback<T> callback) {
        RequestBuilder builder = SimpleRequestBuilder.of(RequestBuilder.POST, urlFormatter.execute(getServiceEndpoint()));
        builder.setRequestData(params.toString());
        try {
            return builder.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
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
