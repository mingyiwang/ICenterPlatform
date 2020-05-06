package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.typeinfo.JType;
import com.icenter.core.client.rest.convert.custom.DateJSONConverter;
import com.icenter.core.client.rest.convert.custom.DoubleJSONConverter;
import com.icenter.core.client.rest.convert.custom.IntegerJSONConverter;
import com.icenter.core.client.rest.convert.custom.StringJSONConverter;
import java.util.Date;
import java.util.HashMap;

public final class SimpleConverters {

    private final static HashMap<String, JSONConverter<?>> factories = new HashMap<>();

    static {
        add(Integer.class.getCanonicalName(), new IntegerJSONConverter()); // Integer
        add(Double.class.getCanonicalName(),  new DoubleJSONConverter());  // Double
        add(String.class.getCanonicalName(),  new StringJSONConverter());  // String
        add(Date.class.getCanonicalName(),    new DateJSONConverter());    // Date
    }

    private final static JSONConverter<?> add(String typeName, JSONConverter<?> converter){
        factories.put(typeName, converter);
        return converter;
    }

    public final static boolean has(JType type){
        return factories.containsKey(type.getQualifiedSourceName());
    }

    public final static int size(){
        return factories.size();
    }

    public final static <T extends JSONConverter<?>> T get(String key){
        return (T) factories.get(key);
    }

}
