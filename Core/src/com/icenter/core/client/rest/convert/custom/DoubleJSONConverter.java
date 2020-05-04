package com.icenter.core.client.rest.convert.custom;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Numbers;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class DoubleJSONConverter extends JSONConverter<Double> {

    @Override
    public Double createInstance() {
        return Numbers.getDefaultDouble();
    }

    @Override
    public JSONValue convertObjectToJSON(Double object) {
        return new JSONNumber(object);
    }

    @Override
    public Double convertJSONToObject(JSONValue value) {
        if (value.isNull() != null){
            return null;
        }

        if (value.isNumber() != null){
            return value.isNumber().doubleValue();
        }

        return null;
    }

}
