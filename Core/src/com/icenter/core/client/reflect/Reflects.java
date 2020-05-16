package com.icenter.core.client.reflect;

import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import java.util.Date;
import java.util.List;
import java.util.Map;

public final class Reflects {

    public final static boolean isPrimitive(JType type){
        if (type.isPrimitive() != null){
           return true;
        }
        return String.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Character.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Byte.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Short.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Integer.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Long.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Float.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Double.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Boolean.class.getCanonicalName().equals(type.getQualifiedSourceName())
            || Short.class.getCanonicalName().equals(type.getQualifiedSourceName())
            ;
    }

    public final static boolean isBoolean(JType type){
        if (type.isPrimitive() != null && type.isPrimitive() == JPrimitiveType.BOOLEAN){
            return true;
        }
        return Boolean.class.getCanonicalName().equals(type.getQualifiedSourceName());
    }

    public final static boolean isArray(JType type){
        return type.isArray() != null;
    }

    public final static boolean isEnum(JType type){
        return type.isEnum() != null;
    }

    public final static boolean isDate(JType type, TypeOracle types){
        return type.isClassOrInterface() != null
            && type.isClassOrInterface().isAssignableTo(types.findType(Date.class.getCanonicalName()));
    }

    public final static boolean isList(JType type, TypeOracle types){
        return type.isClassOrInterface() != null
            && type.isClassOrInterface().isAssignableTo(types.findType(List.class.getCanonicalName()));
    }

    public final static boolean isMap(JType type, TypeOracle types){
        return type.isClassOrInterface() != null
            && type.isClassOrInterface().isAssignableTo(types.findType(Map.class.getCanonicalName()));
    }

}
