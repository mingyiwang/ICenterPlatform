package com.icenter.core.client.rest.parse;


import com.google.gwt.json.client.JSONValue;

public abstract class ObjectParser {

    public abstract JSONValue parse(Object object);
    public abstract Object parse(JSONValue json);

}
