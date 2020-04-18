package com.icenter.core.client.rest;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

import java.util.Date;

public abstract class RemoteRestServiceObjectParser {

    public JSONValue toJSON(Object object){
        return null;
    }

    public Object toObject(JSONValue jsonValue){
        return null;
    }


    public abstract Object deserializeBlankJSON();
    public abstract Object deserializeNullJSON();


    public abstract Date deserialize_java_util_Date(JSONValue value);
    public abstract JSONValue serialize_java_util_Date(Date date);

    public abstract JSONValue toMap(JSONObject map);
    public abstract JSONObject fromMap(JSONValue raw);


}
