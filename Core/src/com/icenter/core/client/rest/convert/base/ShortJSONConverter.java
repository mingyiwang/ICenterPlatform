package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class ShortJSONConverter extends JSONConverter<Short> {

    @Override
    public Short createInstance() {
        return null;
    }

    @Override
    public JSONValue convertObjectToJSON(Short object) {
        return object == null ? JSONNull.getInstance() : new JSONNumber(object);
    }

    @Override
    public Short convertJSONToObject(JSONValue value) {
        if (value.isNull() != null){
            return null;
        }

        if (value.isString() != null){
            return Short.parseShort(value.isString().stringValue());
        }

        return (short) value.isNumber().doubleValue();
    }

}
