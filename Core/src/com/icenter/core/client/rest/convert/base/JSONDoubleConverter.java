package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Numbers;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class JSONDoubleConverter extends JSONConverter<Double> {

    @Override
    public Double createInstance() {
        return null;
    }

    @Override
    public JSONValue convertObjectToJSON(Double object) {
        return object == null ? JSONNull.getInstance() : new JSONNumber(object);
    }

    @Override
    public Double convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null){
            return null;
        }

        if (value.isString() != null) {
            return Double.parseDouble(value.isString().stringValue());
        }

        return value.isNumber().doubleValue();

    }

}
