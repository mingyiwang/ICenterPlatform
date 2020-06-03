package com.icenter.core.client.http;

public final class HttpHeader {

    private String name;
    private String value;

    public static HttpHeader of(String name){
        HttpHeader header = new HttpHeader();
        header.setName(name);
        return header;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return this.value;
    }

    public HttpHeader val(String value) {
        this.value = value;
        return this;
    }

    private void setName(String name) {
        this.name = name;
    }

    private HttpHeader(){}
}
