package com.icenter.core.client.rest.convert.custom;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Numbers;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class BooleanJSONConverter extends JSONConverter<Boolean> {

    @Override
    public Boolean createInstance() {
        return Numbers.getDefaultBoolean();
    }

    public JSONValue convertObjectToJSON(Boolean object) {
        return object == null
             ? JSONBoolean.getInstance(false)
             : JSONBoolean.getInstance(object)
             ;
    }

    @Override
    public Boolean convertJSONToObject(JSONValue value) {
        if (value.isBoolean() != null){
            return value.isBoolean().booleanValue();
        }

        return Numbers.getDefaultBoolean();
    }

}
