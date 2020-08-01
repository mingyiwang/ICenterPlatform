package com.icenter.core.client.reflect;

import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.icenter.core.client.primitive.Primitives;
import java.util.*;

public final class Reflects {

    public final static boolean isPrimitive(JType type){
        if (type == null) {
            return false;
        }

        /***
         * Primitive Type
         */
        if (type.isPrimitive() != null){
            return true;
        }

        /***
         * Object Type
         */
        String typeQualifiedSourceName = type.getQualifiedSourceName();
        return Primitives.CanonicalNames._String.equals(typeQualifiedSourceName)
            || Primitives.CanonicalNames._Character.equals(typeQualifiedSourceName)
            || Primitives.CanonicalNames._Byte.equals(typeQualifiedSourceName)
            || Primitives.CanonicalNames._Short.equals(typeQualifiedSourceName)
            || Primitives.CanonicalNames._Integer.equals(typeQualifiedSourceName)
            || Primitives.CanonicalNames._Long.equals(typeQualifiedSourceName)
            || Primitives.CanonicalNames._Float.equals(typeQualifiedSourceName)
            || Primitives.CanonicalNames._Double.equals(typeQualifiedSourceName)
            || Primitives.CanonicalNames._Boolean.equals(typeQualifiedSourceName)
            ;
    }

    public final static boolean isBoolean(JType type){
        if (type == null){
            return false;
        }

        if (type.isPrimitive() != null
         && type.isPrimitive() == JPrimitiveType.BOOLEAN){
            return true;
        }

        return Primitives.CanonicalNames._Boolean.equals(type.getQualifiedSourceName());
    }

    public final static boolean isArray(JType type){
        return type != null
            && type.isArray() != null;
    }

    public final static boolean isEnum(JType type){
        return type != null
            && type.isEnum() != null;
    }

    public final static boolean isDate(JType type, TypeOracle types){
        return type != null
            && type.isClassOrInterface() != null
            && type.isClassOrInterface().isAssignableTo(types.findType(Date.class.getCanonicalName()));
    }

    public final static boolean isList(JType type, TypeOracle types){
        return type != null
            && type.isClassOrInterface() != null
            && type.isClassOrInterface().isAssignableTo(types.findType(List.class.getCanonicalName()));
    }

    public final static boolean isMap(JType type, TypeOracle types){
        return type != null
            && type.isClassOrInterface() != null
            && type.isClassOrInterface().isAssignableTo(types.findType(Map.class.getCanonicalName()));
    }

    public final static boolean isQueue(JType type, TypeOracle types){
        return type != null
            && type.isClassOrInterface() != null
            && type.isClassOrInterface().isAssignableTo(types.findType(Queue.class.getCanonicalName()));
    }

    public final static boolean isSet(JType type, TypeOracle types){
        return type != null
            && type.isClassOrInterface() != null
            && type.isClassOrInterface().isAssignableTo(types.findType(Set.class.getCanonicalName()));
    }

}
