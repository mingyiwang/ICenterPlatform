package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.icenter.core.client.primitive.Strings;

public final class JSONProperty {

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
    private String name                = Strings.Empty;
    private JClassType parent;

    private JSONProperty(JClassType parent){
        this.parent = parent;
    }

    public final static JSONProperty of(JField field, JClassType targetType){
        JMethod setMethod = findSetMethod(field, targetType);
        JMethod getMethod = findGetMethod(field, targetType);

        JSONProperty property =  new JSONProperty(targetType);
        property.setName(field.getName());
        property.setSetMethod(setMethod == null ? Strings.Empty : setMethod.getName());
        property.setGetMethod(getMethod == null ? Strings.Empty : getMethod.getName());
        return property;
    }

    public JSONProperty on(JField field){
        JMethod setMethod = findSetMethod(field, parent);
        JMethod getMethod = findGetMethod(field, parent);
        return this;
    }

    private final static JMethod findSetMethod(JField field, JClassType type){
        JMethod[] methods = type.getMethods();
        String name = field.getName();
        for(int i=0 ; i< methods.length; i++){
            String methodName = methods[i].getName();
            if (methodName.startsWith("set") && methodName.toLowerCase().contains(name.toLowerCase())){
                return methods[i];
            }
        }
        return null;
    }

    private final static JMethod findGetMethod(JField field, JClassType type){
        JMethod[] methods = type.getMethods();
        String name = field.getName();
        for(int i=0 ; i< methods.length; i++){
            String methodName = methods[i].getName();
            if ((methodName.startsWith("get") || methodName.startsWith("is")) && methodName.toLowerCase().contains(name.toLowerCase())){
                return methods[i];
            }
        }
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
