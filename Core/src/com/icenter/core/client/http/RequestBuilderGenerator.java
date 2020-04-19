package com.icenter.core.client.http;

import com.google.gwt.http.client.RequestBuilder;

public class RequestBuilderGenerator {


    public static RequestBuilder of(RequestBuilder.Method method, String url){
        return new RequestBuilder(method, url);
    }



}
