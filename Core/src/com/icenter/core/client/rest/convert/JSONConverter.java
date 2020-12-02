package com.icenter.core.client.rest.convert;

import com.google.gwt.json.client.JSONValue;

public abstract class JSONConverter<T> {

    public JSONConverter(){ }

    /**
     * Create new object
     * */
    public  T createInstance(){
        throw new UnsupportedOperationException();
    }


    /**
     * Convert object to related JSONValue object
     * */
    public abstract JSONValue convertObjectToJSON(T object);


    /**
     * Convert JSONValue to related object
     * */
    public abstract T convertJSONToObject(JSONValue value);



}
