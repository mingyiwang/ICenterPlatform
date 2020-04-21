package com.icenter.core.client.json;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import java.lang.reflect.Field;

public class JsonConverter<T> {

//    public abstract JSONValue convertToJSON(Object object);
//    public abstract Object covertFromJSON(JSONValue json);

    public String formatName(String name){
        return name;
    }

    public <T> JSONValue convertToJSON(T t){
        if(t instanceof Integer){
            return new JSONNumber((Integer)t);
        }


        JSONObject object = new JSONObject();
        Field[] fields = t.getClass().getDeclaredFields();
        fields[0].getName();
        t.getClass().getCanonicalName();
        return new JSONObject();
    }

    JSONValue convertToJSONObject(Object object){

        return null;
    }

    public void ss(){

    }

    class t extends JsonConverter<Integer> {

    }
}
