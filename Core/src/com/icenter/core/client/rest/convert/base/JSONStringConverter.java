package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class JSONStringConverter extends JSONConverter<String> {

    @Override
    public JSONValue convertObjectToJSON(String object) {
        return object == null ? JSONNull.getInstance() : new JSONString(object);
    }

    @Override
    public String convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null){
            return null;
        }

        return value.isString().stringValue();
    }

}
