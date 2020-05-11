package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.reflect.Reflects;

import java.util.stream.IntStream;

public class JSONProperty {

    private boolean isArray            = false;
    private boolean isClass            = false;
    private boolean isClassOrInterface = false;
    private boolean isEnum             = false;
    private boolean isGenericType      = false;
    private boolean isInterface        = false;
    private boolean isParameterized    = false;
    private boolean isPrimitive        = false;
    private boolean isWildCard         = false;
    private String setMethod           = Strings.Empty;
    private String getMethod           = Strings.Empty;
    private String name = Strings.Empty;

    public final static JSONProperty of(JField field, JClassType targetType){
        JSONProperty property =  new JSONProperty();
        property.setName(field.getName());
        property.setSetMethod(findSetMethod(field, targetType).getName());
        property.setGetMethod(findGetMethod(field, targetType).getName());
        return property;
    }

    private static JMethod findSetMethod(JField field, JClassType type){
        JMethod[] methods = type.getMethods();
        String name = field.getName();

        return null;
    }

    private static JMethod findGetMethod(JField field, JClassType type){
        JMethod[] methods = type.getMethods();
        String name = field.getName();
        return null;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public boolean isClass() {
        return isClass;
    }

    public void setClass(boolean aClass) {
        isClass = aClass;
    }

    public boolean isClassOrInterface() {
        return isClassOrInterface;
    }

    public void setClassOrInterface(boolean classOrInterface) {
        isClassOrInterface = classOrInterface;
    }

    public boolean isEnum() {
        return isEnum;
    }

    public void setEnum(boolean anEnum) {
        isEnum = anEnum;
    }

    public boolean isGenericType() {
        return isGenericType;
    }

    public void setGenericType(boolean genericType) {
        isGenericType = genericType;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean anInterface) {
        isInterface = anInterface;
    }

    public boolean isParameterized() {
        return isParameterized;
    }

    public void setParameterized(boolean parameterized) {
        isParameterized = parameterized;
    }

    public boolean isPrimitive() {
        return isPrimitive;
    }

    public void setPrimitive(boolean primitive) {
        isPrimitive = primitive;
    }

    public boolean isWildCard() {
        return isWildCard;
    }

    public void setWildCard(boolean wildCard) {
        isWildCard = wildCard;
    }

    public String getSetMethod() {
        return setMethod;
    }

    public void setSetMethod(String setMethod) {
        this.setMethod = setMethod;
    }

    public String getGetMethod() {
        return getMethod;
    }

    public void setGetMethod(String getMethod) {
        this.getMethod = getMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
