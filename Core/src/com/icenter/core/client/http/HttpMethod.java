package com.icenter.core.client.http;

import com.google.gwt.http.client.RequestBuilder;

public enum HttpMethod {

    DELETE,
    GET,
    HEAD,
    POST,
    PUT;

    public RequestBuilder.Method getMethod() {
        switch(this) {
            case GET    : return RequestBuilder.GET;
            case POST   : return RequestBuilder.POST;
            case HEAD   : return RequestBuilder.HEAD;
            case PUT    : return RequestBuilder.PUT;
            case DELETE : return RequestBuilder.DELETE;
            default     : return RequestBuilder.POST;
        }
    }

    public static RequestBuilder.Method getMethod(String method){
        switch(method.toUpperCase()) {
            case "GET"    : return RequestBuilder.GET;
            case "POST"   : return RequestBuilder.POST;
            case "HEAD"   : return RequestBuilder.HEAD;
            case "PUT"    : return RequestBuilder.PUT;
            case "DELETE" : return RequestBuilder.DELETE;
            default       : return RequestBuilder.POST;
        }
    }

    public final static HttpMethod of(String method){
        switch(method.toUpperCase()) {
            case "GET"    : return HttpMethod.GET;
            case "POST"   : return HttpMethod.POST;
            case "HEAD"   : return HttpMethod.HEAD;
            case "PUT"    : return HttpMethod.PUT;
            case "DELETE" : return HttpMethod.DELETE;
            default       : return HttpMethod.POST;
        }
    }

}
