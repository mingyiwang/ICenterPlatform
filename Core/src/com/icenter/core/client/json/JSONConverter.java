package com.icenter.core.client.json;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;

public abstract class JSONConverter<T> {

    public final static String Name = JSONConverter.class.getName();
    private JSONProperty property;

    public JSONConverter(){ }

    public abstract T createInstance();
    public abstract JSONValue handle(T object);

    public void setProperty(JSONProperty property) {
        this.property = property;
    }

    public JSONValue convertToJSONObject(T t) {
        return handle(t);
    }

    public T convertJSONToObject(JSONValue value){
        T object = createInstance();
        return object;
    }

    public String formatName(String name){
        return name;
    }

    public int convertToInt(JSONValue value){
        return (int) value.isNumber().doubleValue();
    }

    public JSONValue convertIntToJSONObject(int object){
        return new JSONNumber(object);
    }

    public Integer convertToInteger(JSONValue value){
        return (int) value.isNumber().doubleValue();
    }

    public JSONValue convertIntegerToJSONObject(int object){
        return new JSONNumber(object);
    }

    public T handle(JSONValue value, JSONProperty property){
        T object = createInstance();
        return object;
    }


}
