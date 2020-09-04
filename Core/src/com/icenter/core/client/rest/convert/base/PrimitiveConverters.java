package com.icenter.core.client.rest.convert.base;

import java.util.HashMap;

public final class PrimitiveConverters {

    private final static HashMap<String, String> primitive_type_to_convert_map = new HashMap<>(20);

    static {
        primitive_type_to_convert_map.put(String.class.getCanonicalName(),    JSONStringConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Boolean.class.getCanonicalName(),   JSONBooleanConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Byte.class.getCanonicalName(),      JSONByteConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Character.class.getCanonicalName(), JSONCharConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Double.class.getCanonicalName(),    JSONDoubleConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Float.class.getCanonicalName(),     JSONFloatConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Integer.class.getCanonicalName(),   JSONIntegerConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Long.class.getCanonicalName(),      JSONLongConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Short.class.getCanonicalName(),     JSONShortConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(boolean[].class.getCanonicalName(), JSONBooleanArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(byte[].class.getCanonicalName(),    JSONByteArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(char[].class.getCanonicalName(),    JSONCharArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(double[].class.getCanonicalName(),  JSONDoubleArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(float[].class.getCanonicalName(),   JSONFloatArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(int[].class.getCanonicalName(),     JSONIntegerArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(long[].class.getCanonicalName(),    JSONLongArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(short[].class.getCanonicalName(),   JSONShortArrayConverter.class.getCanonicalName());
    }

    public final static String of(String typeName){
        return primitive_type_to_convert_map.get(typeName);
    }

}
