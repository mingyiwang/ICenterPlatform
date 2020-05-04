package com.icenter.core.client.reflect;

import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.*;

public final class JTypeInfo {

    public static JPrimitiveType Boolean = JPrimitiveType.BOOLEAN;

    public static boolean isPrimitive(JType type){
        return type.isPrimitive() != null ||
               type.getQualifiedSourceName().equals(String.class.getCanonicalName())    ||
               type.getQualifiedSourceName().equals(Character.class.getCanonicalName()) ||
               type.getQualifiedSourceName().equals(Byte.class.getCanonicalName())      ||
               type.getQualifiedSourceName().equals(Short.class.getCanonicalName())     ||
               type.getQualifiedSourceName().equals(Integer.class.getCanonicalName())   ||
               type.getQualifiedSourceName().equals(Long.class.getCanonicalName())      ||
               type.getQualifiedSourceName().equals(Float.class.getCanonicalName())     ||
               type.getQualifiedSourceName().equals(Double.class.getCanonicalName())    ||
               type.getQualifiedSourceName().equals(Boolean.class.getCanonicalName())
        ;
    }

    public static boolean isBoolean(JType type){
        return type.isPrimitive() == JPrimitiveType.BOOLEAN
            || type.getQualifiedSourceName().equals(Boolean.class.getCanonicalName())
            ;
    }

    public static boolean isString(JType type){
        return type.getQualifiedSourceName().equals(String.class.getCanonicalName());
    }

    public static boolean isDate(JType type){
        return type.getQualifiedSourceName().equals(Date.class.getCanonicalName());
    }

    public static boolean isArray(JType type){
        return type.isArray() != null;
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

    public static boolean isAsyncCallback(JType type, TypeOracle types){
        return type.isClassOrInterface() != null
            && type.isClassOrInterface().isAssignableTo(types.findType(AsyncCallback.class.getCanonicalName()));
    }

    public static JType get(JType type){
        if(type.isParameterized() != null){
           return type.isParameterized().getTypeArgs()[0];
        }
        return null;
    }

}
