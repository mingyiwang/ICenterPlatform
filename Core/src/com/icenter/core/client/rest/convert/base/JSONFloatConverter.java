package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Numbers;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class JSONFloatConverter extends JSONConverter<Float> {

    @Override
    public JSONValue convertObjectToJSON(Float object) {
        return object == null ? JSONNull.getInstance() : new JSONNumber(object);
    }

    @Override
    public Float convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null){
            return null;
        }

        if (value.isString() != null) {
            return Float.parseFloat(value.isString().stringValue());
        }

        return (float) value.isNumber().doubleValue();
    }

}
