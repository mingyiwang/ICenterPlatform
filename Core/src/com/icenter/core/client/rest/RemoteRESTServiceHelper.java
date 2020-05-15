package com.icenter.core.client.rest;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.reflect.Reflects;
import com.icenter.core.client.rest.convert.JSONConvertible;
import java.io.Serializable;
import java.util.*;

public final class RemoteRESTServiceHelper {


    public final static void validateMethod(TreeLogger logger, TypeOracle types, JMethod method) throws UnableToCompleteException {
        if(method.getReturnType() != JPrimitiveType.VOID){
           logger.log(TreeLogger.Type.ERROR, method.getName() + " should not have return type.");
           throw new UnableToCompleteException();
        }

        // Method must have return type
        JParameter[] parameters = method.getParameters();
        if(parameters.length == 0){
           logger.log(TreeLogger.Type.ERROR, method.getName() + " should have return type.");
           throw new UnableToCompleteException();
        }

        boolean valid = isAsyncCallbackClass(parameters[parameters.length - 1].getType(), types);
        if(!valid){
            logger.log(TreeLogger.Type.ERROR, method.getName() + " should have return type.");
            throw new UnableToCompleteException();
        }

        for(int i = 0; i < parameters.length - 1; i++){
            valid = isValidParameter(parameters[i], types);
            if(!valid){
               logger.log(TreeLogger.Type.ERROR, parameters[i].getName() + " is not supported variable type.");
               throw new UnableToCompleteException();
            }
        }

    }

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
        boolean valid = isAsyncCallbackClass(parameters[parameters.length - 1].getType(), types);
        for(int i = 0; i < parameters.length - 1; i++){
            valid = isValidParameter(parameters[i], types);
            if(!valid){
                break;
            }
        }

        return valid;
    }

    public static boolean isValidParameter(JParameter parameter, TypeOracle types){
        return isValidType(parameter.getType(), types);
    }

    public static void validateType(TreeLogger logger, TypeOracle types, JType type) throws UnableToCompleteException {
        boolean valid = isValidType(type, types);
        if(!valid){
            logger.log(TreeLogger.Type.ERROR, type.getParameterizedQualifiedSourceName() + " is not supported type.");
            throw new UnableToCompleteException();
        }
    }

    public static boolean isValidType(JType type, TypeOracle types){
        if (Reflects.isPrimitive(type) || Reflects.isEnum(type) || Reflects.isDate(type,types)){
            return true;
        }

        JClassType classType = type.isClassOrInterface();
        if (classType == null) {
            return false;
        }

        if (Reflects.isList(type, types) || Reflects.isMap(type,types) || Reflects.isArray(type)){
            return true;
        }

        // classType.getConstructors(); should have default constructor?
        if(!classType.isDefaultInstantiable()){
            return false;
        }

        JField[] fields = classType.getFields();
        for (JField f : fields) {
             isValidType(f.getType(), types);
        }

        return classType.isAssignableTo(types.findType(Serializable.class.getCanonicalName()))
            || classType.isAssignableTo(types.findType(JSONConvertible.class.getCanonicalName()))
            ;
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
        return getAsyncReturnParameter(method).getType().isParameterized().getTypeArgs()[0];
    }

    public static List<JParameter> getMethodParameters(JMethod method){
        return Arrays.asList(method.getParameters());
    }

}
