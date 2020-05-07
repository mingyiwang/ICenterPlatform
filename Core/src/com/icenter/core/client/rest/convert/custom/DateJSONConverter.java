package com.icenter.core.client.rest.convert.custom;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.Date;

public final class DateJSONConverter extends JSONConverter<Date> {

    @Override
    public Date createInstance() {
        return new Date();
    }

    @Override
    public JSONValue convertObjectToJSON(Date object) {
        return object == null ? JSONNull.getInstance() : new JSONString(String.valueOf(object.getTime()));
    }

    @Override
    public Date convertJSONToObject(JSONValue value) {
        if (value == null || value.isNull() != null){
            return null;
        }

        if(value.isString() != null){
           return new Date(Long.parseLong(value.isString().stringValue()));
        }

        return new Date((long)value.isNumber().doubleValue());
    }


    public enum DateKind {
        WCF, JAVA
    }

}
