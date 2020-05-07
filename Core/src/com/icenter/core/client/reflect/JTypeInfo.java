package com.icenter.core.client.reflect;

import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.*;

public final class JTypeInfo {

    public static JPrimitiveType Boolean = JPrimitiveType.BOOLEAN;

    public static boolean isPrimitive(JType type){
        return type.isPrimitive() != null
            || String.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Character.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Byte.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Short.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Integer.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Long.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Float.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Double.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Boolean.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Short.class.getCanonicalName().equals(type.getQualifiedSourceName());
    }

    public static boolean isString(JType type){
        return type.getQualifiedSourceName().equals(String.class.getCanonicalName());
    }

    public static boolean isDate(JType type){
        return Date.class.getCanonicalName().equals(type.getQualifiedSourceName());
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
