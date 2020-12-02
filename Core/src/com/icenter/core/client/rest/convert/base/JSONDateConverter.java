package com.icenter.core.client.rest.convert.base;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.Date;

public final class JSONDateConverter extends JSONConverter<Date> {

    @Override
    public JSONValue convertObjectToJSON(Date object) {
        return object == null
             ? JSONNull.getInstance()
             : new JSONString(String.valueOf(object.getTime()))
             ;
    }

    @Override
    public Date convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null){
            return null;
        }

        // the milliseconds since January 1, 1970, 00:00:00 GMT
        if(value.isString() != null){
           return new Date(Long.parseLong(value.isString().stringValue()));
        }

        if(value.isNumber() != null){
           long milliseconds = (long) value.isNumber().doubleValue();
           return new Date(milliseconds);
        }
        return null;
    }

}
