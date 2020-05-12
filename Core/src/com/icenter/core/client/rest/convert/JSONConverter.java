package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.lambda.Function;

public abstract class JSONConverter<T> {

    public final static String Name = JSONConverter.class.getName();
    private Function<String, String> paramNameFormatter = name -> name;

    public JSONConverter(){ }
    public abstract T createInstance();
    public abstract JSONValue convertObjectToJSON(T object);
    public abstract T convertJSONToObject(JSONValue value);

    public T handle(JSONValue value){
        return convertJSONToObject(value);
    }

    public void setParamNameFormatter(Function<String,String> nameFormatter) {
        this.paramNameFormatter = nameFormatter;
    }

    public final Function<String, String> getParamNameFormatter() {
        return this.paramNameFormatter;
    }

    public final String toJSONString(T object){
        return convertObjectToJSON(object).toString();
    }

    private void check(JType type, JSONValue value){
        value.isObject().keySet().size();
    }

}
