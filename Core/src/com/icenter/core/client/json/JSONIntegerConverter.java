package com.icenter.core.client.json;

import com.google.gwt.json.client.JSONValue;

public class JSONIntegerConverter extends JSONConverter<Integer>{

    @Override
    public Integer createInstance() {
        return 0;
    }

    @Override
    public JSONValue handle(Integer object) {
        return convertIntegerToJSONObject(object);
    }

}
