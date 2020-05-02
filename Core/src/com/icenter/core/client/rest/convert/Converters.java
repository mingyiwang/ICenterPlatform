package com.icenter.core.client.rest.convert;

import com.google.gwt.core.client.GWT;
import com.icenter.core.client.rest.convert.custom.DoubleJSONConverter;
import com.icenter.core.client.rest.convert.custom.IntegerJSONConverter;
import com.icenter.core.client.rest.convert.custom.StringJSONConverter;
import java.util.Date;
import java.util.HashMap;

public final class Converters {

    private final static HashMap<String, JSONConverter<?>> factories = new HashMap<>();

    static {
        add(Integer.class.getCanonicalName(), new IntegerJSONConverter()); // Integer
        add(Double.class.getCanonicalName(),  new DoubleJSONConverter());  // Double
        add(String.class.getCanonicalName(),  new StringJSONConverter());  // String
        add(Date.class.getCanonicalName(),    new IntegerJSONConverter()); // Date
    }

    public final static JSONConverter<?> getOrCreateIfNotExist(Class classLiteral){
        if(factories.containsKey(classLiteral.getCanonicalName())){
            return factories.get(classLiteral.getCanonicalName());
        }
        return add(classLiteral.getCanonicalName(), GWT.create(classLiteral));
    }

    public final static JSONConverter<?> add(String typeName, JSONConverter<?> converter){
        factories.put(typeName, converter);
        return converter;
    }

    public final static int size(){
        return factories.size();
    }

}
