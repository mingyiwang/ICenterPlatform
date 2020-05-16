package com.icenter.core.client.rest.convert.base;

import java.util.HashMap;

public final class PrimitiveConverters {

    private final static HashMap<String, String> type_to_convert_map = new HashMap<>();

    static{
        type_to_convert_map.put(Boolean.class.getCanonicalName(),   BooleanJSONConverter.class.getCanonicalName());
        type_to_convert_map.put(Byte.class.getCanonicalName(),      ByteJSONConverter.class.getCanonicalName());
        type_to_convert_map.put(Character.class.getCanonicalName(), CharacterJSONConverter.class.getCanonicalName());
        type_to_convert_map.put(Double.class.getCanonicalName(),    DoubleJSONConverter.class.getCanonicalName());
        type_to_convert_map.put(Float.class.getCanonicalName(),     FloatJSONConverter.class.getCanonicalName());
        type_to_convert_map.put(Integer.class.getCanonicalName(),   IntegerJSONConverter.class.getCanonicalName());
        type_to_convert_map.put(Long.class.getCanonicalName(),      LongJSONConverter.class.getCanonicalName());
        type_to_convert_map.put(Short.class.getCanonicalName(),     ShortJSONConverter.class.getCanonicalName());
        type_to_convert_map.put(String.class.getCanonicalName(),    StringJSONConverter.class.getCanonicalName());
        type_to_convert_map.put(Integer.class.getCanonicalName()+"[]", PrimitiveIntArrayConverter.class.getCanonicalName());
    }

    public final static String of(String typeName){
        return type_to_convert_map.get(typeName);
    }

}
