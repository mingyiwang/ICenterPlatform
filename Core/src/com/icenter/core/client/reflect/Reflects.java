package com.icenter.core.client.reflect;

import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import java.util.List;

public final class Reflects {

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

    public static boolean isList(JType type, TypeOracle types){
        return type.isClassOrInterface() != null && (
               type.isClassOrInterface().isAssignableTo(types.findType(List.class.getCanonicalName()))
        );
    }

    public static boolean isReflectible(JType type, TypeOracle types){
        return isPrimitive(type)
            || (type.isClassOrInterface() != null) && type.isClassOrInterface().isAssignableTo(types.findType(Reflectible.class.getCanonicalName()));
    }

}
