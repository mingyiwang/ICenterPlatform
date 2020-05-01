package com.icenter.core.client.reflect;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public final class JMethodInfo {

    private List<JParameter> parameters = new ArrayList<>();
    private JParameter returnParam;

    public static JMethodInfo of(JMethod method, TypeOracle types){
        JMethodInfo info = new JMethodInfo();
        JParameter[] parameters = method.getParameters();
        int length = parameters.length;

        if(JTypeInfo.isAsyncCallback(parameters[length-1].getType(),types)){
           info.returnParam = parameters[length-1];
        }

        IntStream.range(0, length).forEach(i -> {
            info.parameters.add(method.getParameters()[i]);
        });

        return info;
    }

    public List<JParameter> getParameters() {
        return this.parameters;
    }

    public JParameter getReturnParam() {
        return this.returnParam;
    }

    private boolean hasReturnType(){
        return returnParam != null;
    }

    public JClassType getReturnType() {
        return hasReturnType() ? getReturnParam().getType().isParameterized().getTypeArgs()[0] : null;
    }

}
