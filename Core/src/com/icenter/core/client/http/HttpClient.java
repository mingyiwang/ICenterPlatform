package com.icenter.core.client.http;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.Checks;
import com.icenter.core.client.primitive.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class HttpClient {

    private List<HttpHeader> headers = new ArrayList<>();
    private HttpMethod method;
    private String url;
    private JSONValue data;
    private HttpResponseHandler handler;

    public final static HttpClient post() {
        HttpClient client = new HttpClient();
        client.setMethod(HttpMethod.POST);
        return client;
    }

    public final static HttpClient post(String url) {
        HttpClient client = new HttpClient();
        client.setMethod(HttpMethod.POST);
        client.setUrl(url);
        return client;
    }

    public final static HttpClient get() {
        HttpClient client = new HttpClient();
        client.setMethod(HttpMethod.GET);
        return client;
    }

    public final static HttpClient get(String url) {
        HttpClient client = new HttpClient();
        client.setMethod(HttpMethod.GET);
        client.setUrl(url);
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

    public HttpClient header(String name, String value){
        this.headers.add(HttpHeader.of(Strings.requireNotNullOrEmpty(name)).val(Strings.requireNotNullOrEmpty(value)));
        return this;
    }

    public HttpClient data(JSONValue data){
        Objects.requireNonNull(data);
        this.data = data;
        return this;
    }

    public HttpClient handler(HttpResponseHandler handler){
        Objects.requireNonNull(handler);
        this.handler = handler;
        return this;
    }

    /***
     * Send request with specified retry times when custom error happens.
     * @param retries
     */
    public final void send(int retries){

    }


    public final void send() {
        Objects.requireNonNull(handler, "Request Handler can not be null.");
        Objects.requireNonNull(data,    "Request data can not be null.");
        Objects.requireNonNull(method,  "Request method can not be null");
        Objects.requireNonNull(url,     "Request url can not be null.");
        Objects.requireNonNull(headers, "Request header can not be null.");
        handler(handler);

        // 1. validates url
        RequestBuilder builder = new RequestBuilder(method.getMethod(), URL.encode(url));

        // 2. add headers
        for(HttpHeader h : headers){
            builder.setHeader(h.getName(), h.getValue());
        }

        // 3. try send request
        try {
            builder.sendRequest(data.toString(), new RequestCallback() {
                @Override
                public void onError(Request request, Throwable exception) {
                    HttpClient.this.handler.handleError(exception);
                    // retries
                }

                @Override
                public void onResponseReceived(Request request, Response response) {
                    HttpClient.this.handler.handleResponse(response);
                }
            });
        }
        catch (RequestException error){
            HttpClient.this.handler.handleError(error);
        }
    }

    private void setMethod(HttpMethod method) {
        this.method = method;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    private HttpClient(){}

}
