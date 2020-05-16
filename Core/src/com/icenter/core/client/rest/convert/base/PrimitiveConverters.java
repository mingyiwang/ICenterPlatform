package com.icenter.core.client.rest.convert.base;

import java.util.HashMap;

public final class PrimitiveConverters {

    private final static HashMap<String, String> primitive_type_to_convert_map = new HashMap<>();

    static {
        primitive_type_to_convert_map.put(Boolean.class.getCanonicalName(),   BooleanJSONConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Byte.class.getCanonicalName(),      ByteJSONConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Character.class.getCanonicalName(), CharacterJSONConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Double.class.getCanonicalName(),    DoubleJSONConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Float.class.getCanonicalName(),     FloatJSONConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Integer.class.getCanonicalName(),   IntegerJSONConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Long.class.getCanonicalName(),      LongJSONConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(Short.class.getCanonicalName(),     ShortJSONConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(String.class.getCanonicalName(),    StringJSONConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(boolean[].class.getCanonicalName(), PrimitiveBooleanArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(byte[].class.getCanonicalName(),    PrimitiveByteArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(char[].class.getCanonicalName(),    PrimitiveCharArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(double[].class.getCanonicalName(),  PrimitiveDoubleArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(float[].class.getCanonicalName(),   PrimitiveFloatArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(int[].class.getCanonicalName(),     PrimitiveIntArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(long[].class.getCanonicalName(),    PrimitiveLongArrayConverter.class.getCanonicalName());
        primitive_type_to_convert_map.put(short[].class.getCanonicalName(),   PrimitiveShortArrayConverter.class.getCanonicalName());
    }

    public final static String of(String typeName){
        return primitive_type_to_convert_map.get(typeName);
    }

}
