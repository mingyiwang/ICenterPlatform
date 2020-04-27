package com.icenter.core.client.rest.convert;

import com.google.gwt.core.client.GWT;

import java.util.Date;
import java.util.HashMap;

public final class Converters {

    private final static HashMap<String, JSONConverter<?>> factories = new HashMap<>();

    static {
        add(Integer.class.getCanonicalName(), new IntegerJSONConverter()); // Integer
        add(Double.class.getCanonicalName(),  new IntegerJSONConverter()); // Double
        add(String.class.getCanonicalName(),  new IntegerJSONConverter()); // String
        add("java.lang.null",        new IntegerJSONConverter()); // Null
        add(Date.class.getCanonicalName(),    new IntegerJSONConverter()); // Date
    }

    public static JSONConverter get(String name) {
        return factories.get(name);
    }

    public static void add(String typeName, JSONConverter<?> converter){
        factories.put(typeName, converter);
    }

    public static int size(){
        return factories.size();
    }

    public static JSONConverter<?> get(Class classLiteral){
        if(factories.containsKey(classLiteral.getCanonicalName())){
           return get(classLiteral.getCanonicalName());
        }

        JSONConverter converter = GWT.create(classLiteral);
        return converter;
    }

}
