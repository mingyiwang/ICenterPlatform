package com.icenter.core.client.rest.convert;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.lambda.Function;

public abstract class JSONConverter<T> {

    public final static String Name = JSONConverter.class.getName();
    private Function<String, String> nameFormatter = name -> name;
    private JSONProperty property;

    public JSONConverter(){ }

    public abstract T createInstance();
    public abstract JSONValue convertObjectToJSON(T object);
    public abstract T convertJSONToObject(JSONValue value);

    public final void setNameFormatter(Function<String,String> nameFormatter) {
        this.nameFormatter = nameFormatter;
    }

    public final void setProperty(JSONProperty property) {
        this.property = property;
    }

    public String convertObjectToJSONString(T object){
        JSONValue value = convertObjectToJSON(object);
        return value.toString();
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

    public T convertObjectToJSON(JSONValue value, JSONProperty property){
        T object = createInstance();
        return object;
    }


}
