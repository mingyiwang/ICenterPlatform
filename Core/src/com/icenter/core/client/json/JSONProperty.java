package com.icenter.core.client.json;

import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.json.client.JSONValue;
import com.icenter.core.client.primitive.Strings;
import java.util.ArrayList;
import java.util.List;

public class JSONProperty {

    private JType  propertyType;
    private String propertySetterMethod = Strings.Empty;
    private String propertyGetterMethod = Strings.Empty;
    private List<JSONProperty> properties = new ArrayList<JSONProperty>();

    public String getName(){
        return propertyType.getParameterizedQualifiedSourceName();
    }

    public boolean isClassType(){
        return propertyType.isClass() != null;
    }

    public boolean isPrimitive(){
        return propertyType.isPrimitive() != null;
    }

    public boolean isArray(){
        return propertyType.isArray() != null;
    }

    public void setValue(JSONValue value){
        //1. check property has the same type of value
        //2. check property numbers has the same size of value
    }


}
