package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class JSONIntegerConverter extends JSONConverter<Integer> {

    @Override
    public JSONValue convertObjectToJSON(Integer object) {
        return object == null ? JSONNull.getInstance() : new JSONNumber(object);
    }

    @Override
    public Integer convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull()!= null){
            return null;
        }

        if (value.isString() != null){
            return Integer.valueOf(value.isString().stringValue());
        }

        return (int) value.isNumber().doubleValue();
    }

}
