package com.icenter.core.client.rest;

import com.google.gwt.core.ext.typeinfo.*;
import com.icenter.core.client.reflect.JTypeInfo;
import com.icenter.core.client.rest.convert.JSONConvertible;
import java.io.Serializable;

public final class RemoteRESTServiceHelper {

    public static boolean isValidMethod(JMethod method, TypeOracle types){

        // Return type must be in the last parameter
        if(method.getReturnType() != JPrimitiveType.VOID){
           return false;
        }

        // Method must have return type
        JParameter[] parameters = method.getParameters();
        if(parameters.length == 0){
           return false;
        }

        // Check has return type or not
        boolean valid = JTypeInfo.isAsyncCallback(parameters[parameters.length - 1].getType(),types);

        for(int i = 0; i < parameters.length - 1; i++){
            JType type = parameters[i].getType();
            if (JTypeInfo.isPrimitive(type)){
                valid = true;
            }
            else {
                valid =  type.isClassOrInterface() != null && (
                         type.isClassOrInterface().isAssignableTo(types.findType(Serializable.class.getCanonicalName()))
                      || type.isClassOrInterface().isAssignableTo(types.findType(JSONConvertible.class.getCanonicalName()))
                );
            }

            if(!valid){
                break;
            }
        }

        return valid;
    }

}
