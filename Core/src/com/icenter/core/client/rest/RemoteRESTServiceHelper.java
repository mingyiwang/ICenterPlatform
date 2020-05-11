package com.icenter.core.client.rest;

import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.reflect.Reflects;
import com.icenter.core.client.rest.convert.JSONConvertible;
import com.icenter.core.client.rest.convert.JSONProperty;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

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
        boolean valid = isAsyncCallbackClass(parameters[parameters.length - 1].getType(),types);
        for(int i = 0; i < parameters.length - 1; i++){
            valid = isValidParam(parameters[i], types);
            if(!valid){
                break;
            }
        }

        return valid;
    }

    public static boolean isValidParam(JParameter parameter, TypeOracle types){
        if (Reflects.isPrimitive(parameter.getType()) || Reflects.isArray(parameter.getType())){
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

    public static boolean isAsyncCallbackClass(JType type, TypeOracle types){
        return type.isClassOrInterface() != null
            && type.isClassOrInterface().isAssignableTo(types.findType(AsyncCallback.class.getCanonicalName()));
    }

    public static JParameter getAsyncReturnParameter(JMethod method){
        JParameter[] ps = method.getParameters();
        return ps[ps.length-1];
    }

    public static JClassType getAsyncReturnType(JMethod method) {
        return  getAsyncReturnParameter(method).getType().isParameterized().getTypeArgs()[0];
    }

    public static List<JParameter> getMethodParameters(JMethod method){
        return Arrays.asList(method.getParameters());
    }

    public static List<JSONProperty> getProperties(JType type, TypeOracle types){
        List<JSONProperty> properties = new ArrayList<>();
        JField[] fields   = type.isClassOrInterface().getFields();
        JMethod[] methods = type.isClassOrInterface().getMethods();

        Stream.of(0, fields.length).forEach(i -> {
            JField field = fields[i];
            JType propertyType = field.getType();
            JSONProperty property = new JSONProperty();
            property.setName(field.getName());
            property.setArray(propertyType.isArray() != null);
            property.setClass(propertyType.isClass() != null);
            property.setClassOrInterface(propertyType.isClassOrInterface() != null);
            property.setEnum(propertyType.isEnum() != null);
            property.setGenericType(propertyType.isGenericType() != null);
            property.setInterface(propertyType.isInterface() != null);
            property.setClassOrInterface(propertyType.isClassOrInterface() != null);
            property.setParameterized(propertyType.isParameterized() != null);
            property.setWildCard(propertyType.isWildcard() != null);

        });

        return properties;
    }

    public JMethod hasSetMethod(JField field, JMethod[] methods){
        String name = field.getName();
        String expectedMethodName = "set" + name;
        Stream.of(0, methods.length).forEach(i -> {
            if (expectedMethodName.equals(methods[i].getName())){

            }
        });

        return null;
    }
}
