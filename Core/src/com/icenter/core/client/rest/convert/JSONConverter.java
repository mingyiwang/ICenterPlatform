package com.icenter.core.client.rest.convert;

import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.lambda.Function;

public abstract class JSONConverter<T> {

    public final static String Name = JSONConverter.class.getName();
    private Function<String, String> paramNameFormatter = name -> name;
    private JSONProperty property;

    public JSONConverter(){ }
    public abstract T createInstance();
    public abstract JSONValue convertObjectToJSON(T object);
    public abstract T convertJSONToObject(JSONValue value);

    public final void setParamNameFormatter(Function<String,String> nameFormatter) {
        this.paramNameFormatter = nameFormatter;
    }

    public final void setProperty(JSONProperty property) {
        this.property = property;
    }

    public final String toJSONString(T object){
        return convertObjectToJSON(object).toString();
    }

}
