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

    public final static JMethodInfo of(JMethod method, TypeOracle types){
        JMethodInfo info = new JMethodInfo();
        JParameter[] parameters = method.getParameters();
        int length = parameters.length;

        IntStream.range(0, length).forEach(i -> {
            info.parameters.add(method.getParameters()[i]);
            if (i == length -1 && JTypeInfo.isAsyncCallback(parameters[i].getType(),types)){
                info.returnParam = parameters[i];
            }
        });
        return info;
    }

    public List<JParameter> getParameters() {
        return this.parameters;
    }

    public JParameter getReturnParameter() {
        return this.returnParam;
    }

    private boolean hasReturnType(){
        return returnParam != null;
    }

    public JClassType getReturnType() {
        return hasReturnType() ? getReturnParameter().getType().isParameterized().getTypeArgs()[0] : null;
    }

}
