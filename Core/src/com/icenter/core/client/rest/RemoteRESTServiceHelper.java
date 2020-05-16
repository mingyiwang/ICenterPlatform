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

        JParameter[] parameters = method.getParameters();
        int length = parameters.length;
        if (length == 0){
            logger.log(TreeLogger.Type.ERROR, method.getName() + " should have return type.");
            throw new UnableToCompleteException();
        }

        // Validates Parameters
        for(int i = 0; i < length - 1; i++){
            validateType(logger, types, parameters[i].getType());
        }

        // Validates return type
        validateReturnType(logger, types, parameters[length - 1].getType());
    }

    public final static void validateType(TreeLogger logger, TypeOracle types, JType type) throws UnableToCompleteException {
        if (type.isWildcard() != null || type.isAnnotation() != null|| type.isTypeParameter() != null|| type.isRawType() != null){
            logger.log(TreeLogger.Type.ERROR, type.getQualifiedSourceName() + " is not supported.");
            throw new UnableToCompleteException();
        }

        if (Reflects.isPrimitive(type) || Reflects.isEnum(type) || Reflects.isDate(type,types)){
            return;
        }
        else if (Reflects.isArray(type)){
            JType componentType = type.isArray().getComponentType();
            int rank = type.isArray().getRank();
            if (rank > 1) {
                logger.log(TreeLogger.Type.ERROR, "Multi-dimension Array of ["+ type.getQualifiedSourceName() + "] is not supported.");
                throw new UnableToCompleteException();
            }

            validateType(logger, types, componentType);
        }
        else if (Reflects.isList(type, types)){
            if (type.isClassOrInterface().isParameterized() == null){
                logger.log(TreeLogger.Type.ERROR, "Non Generic List is not supported.");
                throw new UnableToCompleteException();
            }

            JClassType[] typeArgs = type.isClassOrInterface().isParameterized().getTypeArgs();
            if(typeArgs == null || typeArgs.length == 0){
               logger.log(TreeLogger.Type.ERROR, "Non Generic List is not supported.");
               throw new UnableToCompleteException();
            }
            validateType(logger, types, typeArgs[0]);
        }
        else if (Reflects.isMap(type,types)){
            if (type.isClassOrInterface().isParameterized() == null){
                logger.log(TreeLogger.Type.ERROR, "Non Generic Map is not supported.");
                throw new UnableToCompleteException();
            }

            JClassType[] typeArgs = type.isClassOrInterface().isParameterized().getTypeArgs();
            if (typeArgs == null || typeArgs.length != 2){
                logger.log(TreeLogger.Type.ERROR, "Non Generic Map is not supported.");
                throw new UnableToCompleteException();
            }

            validateType(logger, types, typeArgs[0]);
            validateType(logger, types, typeArgs[1]);
        }
        else if (type.isClassOrInterface() != null){
            JClassType classType = type.isClassOrInterface();
            if(!classType.isAssignableTo(types.findType(Serializable.class.getCanonicalName()))
            && !classType.isAssignableTo(types.findType(JSONConvertible.class.getCanonicalName()))){
                logger.log(TreeLogger.Type.ERROR, type.getQualifiedSourceName()+ " is Non JSONConvertible Class.");
                throw new UnableToCompleteException();
            }

            if(!classType.isDefaultInstantiable()) {
                logger.log(TreeLogger.Type.ERROR, type.getQualifiedSourceName()+ " does not have default constructor.");
                throw new UnableToCompleteException();
            }

            JField[] fields = classType.getFields();
            for (JField f : fields) {
                 validateType(logger, types, f.getType());
            }
        }
        else {
            logger.log(TreeLogger.Type.ERROR, type.getQualifiedSourceName()+ " is not supported.");
            throw new UnableToCompleteException();
        }
    }

    public static void validateReturnType(TreeLogger logger, TypeOracle types, JType type) throws UnableToCompleteException{
        if(type.isClassOrInterface() == null){
           logger.log(TreeLogger.Type.ERROR, "Null return type is not supported.");
           throw new UnableToCompleteException();
        }

        if(!type.isClassOrInterface().isAssignableTo(types.findType(AsyncCallback.class.getCanonicalName()))){
           logger.log(TreeLogger.Type.ERROR, type.getQualifiedSourceName()+ " must extends AsyncCallback Class.");
           throw new UnableToCompleteException();
        }

        if(type.isClassOrInterface().isParameterized() == null){
           logger.log(TreeLogger.Type.ERROR, "Non generic Async return type is not supported.");
           throw new UnableToCompleteException();
        }

        if(type.isClassOrInterface().isParameterized().getTypeArgs() == null
        || type.isClassOrInterface().isParameterized().getTypeArgs().length == 0){
            logger.log(TreeLogger.Type.ERROR, "Non generic Async return type is not supported.");
            throw new UnableToCompleteException();
        }

        validateType(logger, types, type.isClassOrInterface().isParameterized().getTypeArgs()[0]);
    }

    public static JParameter getReturnParameter(JMethod method){
        JParameter[] ps = method.getParameters();
        return ps[ps.length-1];
    }

    public static JClassType getReturnType(JMethod method) {
        return getReturnParameter(method).getType().isParameterized().getTypeArgs()[0];
    }

    public static List<JParameter> getMethodParameters(JMethod method){
        return Arrays.asList(method.getParameters());
    }

}
