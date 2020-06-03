package com.icenter.core.client.rest;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.http.*;
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

    protected final <T> void send(String action, String httpMethod, JSONValue params, final JSONConverter<T> converter, final AsyncCallback<T> callback) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(httpMethod);
        Objects.requireNonNull(converter);
        Objects.requireNonNull(callback);

        HttpClient.of(HttpMethod.of(httpMethod))
                  .url(Urls.addSlashIfNeeded(getServiceEndPoint()) + Strings.toUpperCase(action,0))
                  .data(params)
                  .header("Content-type", MimeTypes.Json.Json)
                  .handler(new RemoteRESTServiceCallback(converter, callback))
                  .send();

    }

}
