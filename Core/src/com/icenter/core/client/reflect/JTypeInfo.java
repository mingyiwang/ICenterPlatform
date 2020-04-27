package com.icenter.core.client.reflect;

import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import java.util.*;

public class JTypeInfo {

    public static boolean isPrimitive(JType type){
        return type.isPrimitive() != null;
    }

    public static boolean isBoolean(JType type){
        return isPrimitive(type) && type.isPrimitive() == JPrimitiveType.BOOLEAN;
    }

    public static boolean isSet(JType type, TypeOracle types){
        return type.isClassOrInterface() != null
            && type.isClassOrInterface().isAssignableTo(types.findType(Set.class.getCanonicalName()));
    }

    public static boolean isList(JType type, TypeOracle types){
        return type.isClassOrInterface() != null
                && type.isClassOrInterface().isAssignableTo(types.findType(List.class.getCanonicalName()));
    }

    public static boolean isMap(JType type, TypeOracle types){
        return type.isClassOrInterface() != null
                && type.isClassOrInterface().isAssignableTo(types.findType(Map.class.getCanonicalName()));
    }

    public static boolean isCollection(JType type, TypeOracle types){
        return type.isClassOrInterface() != null
                && type.isClassOrInterface().isAssignableTo(types.findType(Collection.class.getCanonicalName()));
    }

    public static boolean isQueue(JType type, TypeOracle types){
        return type.isClassOrInterface() != null
                && type.isClassOrInterface().isAssignableTo(types.findType(Queue.class.getCanonicalName()));
    }


}
