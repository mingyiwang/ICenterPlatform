package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Numbers;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class JSONBooleanConverter extends JSONConverter<Boolean> {

    @Override
    public Boolean createInstance() {
        return Numbers.getDefaultBoolean();
    }

    @Override
    public JSONValue convertObjectToJSON(Boolean object) {
        return object == null
             ? JSONBoolean.getInstance(false)
             : JSONBoolean.getInstance(object)
             ;
    }

    @Override
    public Boolean convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null) {
            return Boolean.FALSE;
        }

        if (value.isString() != null) {
            return Boolean.parseBoolean(value.isString().stringValue());
        }

        return value.isBoolean() != null
             ? value.isBoolean().booleanValue()
             : Boolean.FALSE
             ;
    }

}
