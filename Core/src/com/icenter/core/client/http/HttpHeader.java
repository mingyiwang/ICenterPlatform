package com.icenter.core.client.http;

import com.google.gwt.http.client.Header;

public final class HttpHeader extends Header {

    private String name;
    private String value;

    public static HttpHeader of(String name){
        HttpHeader header = new HttpHeader();
        header.setName(name);
        return header;
    }

    public HttpHeader val(String value) {
        this.value = value;
        return this;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final String getValue() {
        return this.value;
    }

    private HttpHeader(){}

}
