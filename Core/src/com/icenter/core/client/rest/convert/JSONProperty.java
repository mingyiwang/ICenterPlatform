package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.icenter.core.client.primitive.Strings;

public final class JSONProperty {

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

    private final static JMethod findSetMethod(JField field, JClassType type){
        JMethod[] methods = type.getMethods();
        String name = field.getName();
        for(int i=0 ; i< methods.length; i++){
            String methodName = methods[i].getName();
            if(methodName.startsWith("set")){
                if (Strings.equalsIgnoreCase(methodName.substring(3),name)){
                    return methods[i];
                }
            }
        }
        return null;
    }

    private final static JMethod findGetMethod(JField field, JClassType type){
        JMethod[] methods = type.getMethods();
        String name = field.getName();
        for(int i=0 ; i< methods.length; i++){
            String methodName = methods[i].getName();
            if (methodName.startsWith("get")){
                if(Strings.equalsIgnoreCase(methodName.substring(3),name)) {
                    return methods[i];
                }
            }
            if (methodName.startsWith("is")){
                if (Strings.equalsIgnoreCase(methodName.substring(2), name)){
                    return methods[i];
                }
            }
        }
        return null;
    }

}
