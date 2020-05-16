package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.icenter.core.client.Checks;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.reflect.Reflects;

/**
 * Helper class used to store property name and methods
 * **/
public final class JSONProperty {

    private String setMethod  = Strings.Empty;
    private String getMethod  = Strings.Empty;
    private String name       = Strings.Empty;

    private JSONProperty(){}

    public final static JSONProperty of(JField field, JClassType targetType){
        JMethod setMethod = findSetMethod(field, targetType);
        JMethod getMethod = findGetMethod(field, targetType);
        return new JSONProperty().setName(field.getName())
                                 .setSetMethod(setMethod == null ? Strings.Empty : setMethod.getName())
                                 .setGetMethod(getMethod == null ? Strings.Empty : getMethod.getName());
    }

    public String getSetMethod() {
        return this.setMethod;
    }

    public JSONProperty setSetMethod(String setMethod) {
        this.setMethod = setMethod;
        return this;
    }

    public String getGetMethod() {
        return this.getMethod;
    }

    public JSONProperty setGetMethod(String getMethod) {
        this.getMethod = getMethod;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public JSONProperty setName(String name) {
        this.name = name;
        return this;
    }

    private final static JMethod findSetMethod(JField field, JClassType type){
        return type.findMethod("set" + formatMethodName(field.getName()), new JType[]{field.getType()});
    }

    private final static JMethod findGetMethod(JField field, JClassType type){
        return Reflects.isBoolean(field.getType())
             ? type.findMethod("is"  + formatMethodName(field.getName()), new JType[0])
             : type.findMethod("get" + formatMethodName(field.getName()), new JType[0])
             ;
    }

    private final static String formatMethodName(String name){
        Checks.requireNotEmpty(name);
        char[] copies = name.toCharArray();
        copies[0] = Character.toUpperCase(copies[0]);
        return String.valueOf(copies);
    }

}
