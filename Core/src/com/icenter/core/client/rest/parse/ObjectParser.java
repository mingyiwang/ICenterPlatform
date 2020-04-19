package com.icenter.core.client.rest.parse;


import com.google.gwt.json.client.JSONValue;

public abstract class ObjectParser {

    public abstract Object toObject(JSONValue jsonValue);
    public abstract JSONValue toJSONValue(Object object);

}
