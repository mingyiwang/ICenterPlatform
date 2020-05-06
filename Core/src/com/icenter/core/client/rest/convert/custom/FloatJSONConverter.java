package com.icenter.core.client.rest.convert.custom;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Numbers;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class FloatJSONConverter extends JSONConverter<Float> {

    @Override
    public Float createInstance() {
        return Numbers.getDefaultFloat();
    }

    @Override
    public JSONValue convertObjectToJSON(Float object) {
        return new JSONNumber(object);
    }

    @Override
    public Float convertJSONToObject(JSONValue value) {
        if (value.isNull() != null){
            return null;
        }

        if (value.isNumber() != null){
            return (float) value.isNumber().doubleValue();
        }

        return null;
    }

}
