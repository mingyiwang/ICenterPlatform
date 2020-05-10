package com.icenter.core.client.rest;

import com.google.gwt.core.ext.typeinfo.*;
import com.icenter.core.client.reflect.JTypeInfo;
import com.icenter.core.client.rest.convert.JSONConvertible;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
            valid = isValidParam(parameters[i], types);
            if(!valid){
                break;
            }
        }

        return valid;
    }

    public static boolean isValidParam(JParameter parameter, TypeOracle types){
        if (JTypeInfo.isPrimitive(parameter.getType())){
            return true;
        }
        else {
            return parameter.getType().isClassOrInterface() != null && (
                    parameter.getType().isClassOrInterface().isAssignableTo(types.findType(Serializable.class.getCanonicalName()))
                            || parameter.getType().isClassOrInterface().isAssignableTo(types.findType(JSONConvertible.class.getCanonicalName()))
                            || parameter.getType().isClassOrInterface().isAssignableTo(types.findType(List.class.getCanonicalName()))
                            || parameter.getType().isClassOrInterface().isAssignableTo(types.findType(Map.class.getCanonicalName()))
            );
        }
    }

}
