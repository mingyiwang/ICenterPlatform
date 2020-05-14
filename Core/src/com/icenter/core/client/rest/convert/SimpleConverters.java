package com.icenter.core.client.rest.convert;

import com.icenter.core.client.rest.convert.base.*;

import java.util.Date;
import java.util.HashMap;

public final class SimpleConverters {

    private final static HashMap<String, JSONConverter<?>> factories = new HashMap<>();

    static {
        add(Boolean.class.getCanonicalName(),   new BooleanJSONConverter());    // Boolean
        add(Byte.class.getCanonicalName(),      new ByteJSONConverter());       // Byte
        add(Character.class.getCanonicalName(), new CharacterJSONConverter());  // Character
        add(Date.class.getCanonicalName(),      new DateJSONConverter());       // Date
        add(Double.class.getCanonicalName(),    new DoubleJSONConverter());     // Double
        add(Float.class.getCanonicalName(),     new FloatJSONConverter());      // Float
        add(Integer.class.getCanonicalName(),   new IntegerJSONConverter());    // Integer
        add(Long.class.getCanonicalName(),      new LongJSONConverter());       // Long
        add(Short.class.getCanonicalName(),     new ShortJSONConverter());      // Short
        add(String.class.getCanonicalName(),    new StringJSONConverter());     // String
    }

    private final static JSONConverter<?> add(String typeName, JSONConverter<?> converter){
        factories.put(typeName, converter);
        return converter;
    }

    public final static int size(){
        return factories.size();
    }

    public final static <T extends JSONConverter<?>> T get(String key){
        return (T) factories.get(key);
    }

}
