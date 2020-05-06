package com.icenter.core.client.rest.convert.custom;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Numbers;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class ShortJSONConverter extends JSONConverter<Short> {

    @Override
    public Short createInstance() {
        return null;
    }

    @Override
    public JSONValue convertObjectToJSON(Short object) {
        return new JSONNumber(object);
    }

    @Override
    public Short convertJSONToObject(JSONValue value) {
        if (value.isNull() != null){
            return null;
        }

        if (value.isNumber() != null){
            return (short) value.isNumber().doubleValue();
        }

        return null;
    }

}
