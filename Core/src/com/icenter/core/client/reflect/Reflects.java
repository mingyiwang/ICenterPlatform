package com.icenter.core.client.reflect;

import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

public class Reflects {

    public static boolean isValid(JType type, TypeOracle types){
        return JTypeInfo.isPrimitive(type)||(type.isClassOrInterface() != null && type.isClassOrInterface().isAssignableTo(types.findType(JReflectable.class.getCanonicalName())));
    }

}
