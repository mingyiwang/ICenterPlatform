package com.icenter.core.client.rest;

import com.google.gwt.core.ext.typeinfo.*;
import com.icenter.core.client.reflect.JTypeInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class RemoteRESTServiceHelper {

    public static boolean isValid(JMethod method, TypeOracle types){
        if(method.getReturnType() != JPrimitiveType.VOID){
           return false;
        }

        JParameter[] parameters = method.getParameters();
        if(parameters.length == 0){
           return false;
        }

        // Post Method
        JParameter last = parameters[parameters.length -1];
        return JTypeInfo.isAsyncCallback(last.getType(),types);
    }

    public final static List<JParameter> getParams(JMethod method){
        List<JParameter> params = new ArrayList<>();
        IntStream.range(0, method.getParameters().length).forEach(i -> {
            if(i<method.getParameters().length -1){
                params.add(method.getParameters()[i]);
            }
        });
        return params;
    }

    public final static JParameter getAsyncCallbackParameter(JMethod method){
        JParameter[] parameters = method.getParameters();
        return parameters[parameters.length -1];
    }

}
