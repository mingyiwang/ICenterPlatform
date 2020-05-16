package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.reflect.Reflects;

public final class JClassProperty {

    private String setMethod  = Strings.Empty;
    private String getMethod  = Strings.Empty;
    private String name       = Strings.Empty;

    private JClassProperty(){}

    public final static JClassProperty of(JField field, JClassType targetType){
        JMethod setMethod = findSetMethod(field, targetType);
        JMethod getMethod = findGetMethod(field, targetType);
        return new JClassProperty().setName(field.getName())
                                   .setSetMethod(setMethod == null ? Strings.Empty : setMethod.getName())
                                   .setGetMethod(getMethod == null ? Strings.Empty : getMethod.getName());
    }

    public String getSetMethod() {
        return this.setMethod;
    }

    public JClassProperty setSetMethod(String setMethod) {
        this.setMethod = setMethod;
        return this;
    }

    public String getGetMethod() {
        return this.getMethod;
    }

    public JClassProperty setGetMethod(String getMethod) {
        this.getMethod = getMethod;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public JClassProperty setName(String name) {
        this.name = name;
        return this;
    }

    private final static JMethod findSetMethod(JField field, JClassType type){
        return type.findMethod("set" + Strings.formatMethodName(field.getName()), new JType[]{field.getType()});
    }

    private final static JMethod findGetMethod(JField field, JClassType type){
        return Reflects.isBoolean(field.getType())
             ? type.findMethod("is"  + Strings.formatMethodName(field.getName()), new JType[0])
             : type.findMethod("get" + Strings.formatMethodName(field.getName()), new JType[0])
             ;
    }

}
