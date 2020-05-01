package com.icenter.core.client.rest;

import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.icenter.core.client.reflect.JTypeInfo;

public final class RemoteRESTServiceHelper {

    public static boolean isValid(JMethod method, TypeOracle types){
        if(method.getReturnType() != JPrimitiveType.VOID){
           return false;
        }

        JParameter[] parameters = method.getParameters();
        if(parameters.length == 0){
           // Method must have return type
           return false;
        }


        // Last parameter must be AsyncCallback
        return JTypeInfo.isAsyncCallback(parameters[parameters.length -1].getType(),types);
    }

}
