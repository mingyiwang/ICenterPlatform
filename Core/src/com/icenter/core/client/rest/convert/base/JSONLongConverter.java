package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Numbers;
import com.icenter.core.client.rest.convert.JSONConverter;

public final class JSONLongConverter extends JSONConverter<Long> {

    @Override
    public JSONValue convertObjectToJSON(Long object) {
        return object == null ? JSONNull.getInstance() : new JSONNumber(object);
    }

    @Override
    public Long convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull()!= null){
            return null;
        }

        if (value.isString() != null){
            return Long.parseLong(value.isString().stringValue());
        }

        return (long) value.isNumber().doubleValue();
    }


}
