package com.icenter.core.client.rest;

import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.reflect.Reflects;
import com.icenter.core.client.rest.convert.JSONConvertible;
import java.io.Serializable;
import java.util.*;

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
        boolean valid = isAsyncCallback(parameters[parameters.length - 1].getType(),types);

        for(int i = 0; i < parameters.length - 1; i++){
            valid = isValidParam(parameters[i], types);
            if(!valid){
                break;
            }
        }

        return valid;
    }

    public static boolean isValidParam(JParameter parameter, TypeOracle types){
        if (Reflects.isPrimitive(parameter.getType())){
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

    public static boolean isAsyncCallback(JType type, TypeOracle types){
        return type.isClassOrInterface() != null
                && type.isClassOrInterface().isAssignableTo(types.findType(AsyncCallback.class.getCanonicalName()));
    }

    public static JParameter getAsyncReturnParameter(JMethod method){
        JParameter[] ps = method.getParameters();
        return ps[ps.length-1];
    }

    public static JClassType getAsyncReturnType(JMethod method) {
        JParameter[] ps = method.getParameters();
        JParameter last = ps[ps.length-1];
        return last.getType().isParameterized().getTypeArgs()[0];
    }

    public static List<JParameter> getMethodParameters(JMethod method){
        return Arrays.asList(method.getParameters());
    }

}
