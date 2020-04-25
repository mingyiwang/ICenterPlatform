package com.icenter.core.client.json;

import com.google.gwt.core.client.GWT;

import java.util.HashMap;

public final class Converters {

    private final static HashMap<String, JsonConverter<?>> factories = new HashMap<>();

    static {
        add("", null); // Integer
        add("", null); // Double
        add("", null); // String
        add("", null); // Null
        add("", null); // Date
    }

    public static JsonConverter get(String name) {
        return factories.get(name);
    }

    public static void add(String typeName, JsonConverter<?> converter){
        factories.put(typeName, converter);
    }

    public static int size(){
        return factories.size();
    }

    public static JsonConverter<?> get(Class classLiteral){
        GWT.create(classLiteral);
        return null;
    }

}
