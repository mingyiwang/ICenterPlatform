package com.icenter.core.client.http;

public final class HttpHeader {

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return this.value;
    }

    private void setValue(String value) {
        this.value = value;
    }

    private void setName(String name) {
        this.name = name;
    }

    private HttpHeader(){}
}
