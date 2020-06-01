package com.icenter.core.client.http;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.Checks;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class HttpClient {

    private List<HttpHeader> headers = new ArrayList<>();
    private HttpMethod method;
    private String url;
    private JSONValue data;

    public final static HttpClient post() {
        HttpClient client = new HttpClient();
        client.setMethod(HttpMethod.POST);
        return client;
    }

    public final static HttpClient get() {
        HttpClient client = new HttpClient();
        client.setMethod(HttpMethod.GET);
        return client;
    }

    public final static HttpClient of(HttpMethod method){
        switch(method){
            case POST : return post();
            case GET  : return get();
            default   : return post();
        }
    }

    public HttpClient url(URL url){
        Objects.requireNonNull(url);
        setUrl(url.toString());
        return this;
    }

    public HttpClient url(String url) {
        Checks.requireNotNullOrEmpty(url);
        setUrl(url);
        return this;
    }

    public HttpClient header(HttpHeader header){
        Objects.requireNonNull(header);
        this.headers.add(header);
        return this;
    }

    public HttpClient data(JSONValue data){
        Objects.requireNonNull(data);
        this.data = data;
        return this;
    }

    public final void send(HttpCallback callback) {
        RequestBuilder builder = new RequestBuilder(method.getMethod(), URL.encode(url));
        headers.forEach(header -> builder.setHeader(header.getName(), header.getValue()));
        try{
            builder.sendRequest(data.toString(), new RequestCallback() {
                @Override
                public void onError(Request request, Throwable exception) {
                    callback.handleError(exception);
                }
                @Override
                public void onResponseReceived(Request request, Response response) {
                    callback.handleResponse(response);
                }
            });
        }
        catch (RequestException error){
            callback.handleError(error);
        }
    }

    private void setMethod(HttpMethod method) {
        this.method = method;
    }
    private void setUrl(String url) {
        this.url = url;
    }
    private HttpClient(){ }

}
