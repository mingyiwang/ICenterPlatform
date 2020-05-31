package com.icenter.core.client.http;

import com.google.gwt.http.client.RequestBuilder;

public enum HttpMethod {

    DELETE,
    GET,
    HEAD,
    POST, // Indicates Http Post action.
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
        switch(method) {
            case "GET"    : return RequestBuilder.GET;
            case "POST"   : return RequestBuilder.POST;
            case "HEAD"   : return RequestBuilder.HEAD;
            case "PUT"    : return RequestBuilder.PUT;
            case "DELETE" : return RequestBuilder.DELETE;
            default       : return RequestBuilder.POST;
        }
    }

}
