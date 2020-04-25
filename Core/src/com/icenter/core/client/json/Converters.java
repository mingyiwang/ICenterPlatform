package com.icenter.core.client.json;

import com.google.gwt.core.client.GWT;
import java.util.HashMap;

public final class Converters {

    private final static HashMap<String, JSONConverter<?>> factories = new HashMap<>();

    static {
        add(Integer.class.getCanonicalName(), new JSONIntegerConverter()); // Integer
        add("", null); // Double
        add("", null); // String
        add("", null); // Null
        add("", null); // Date
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

        return GWT.create(classLiteral);
    }

}
