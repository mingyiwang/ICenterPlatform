package com.icenter.core.client.json;


import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;

public abstract class JsonConverterGenerator {


    public JSONObject generate(TreeLogger treeLogger, GeneratorContext generatorContext, JType type)
            throws UnableToCompleteException {

        JClassType targetClass = generatorContext.getTypeOracle().findType(type.getQualifiedSourceName());
        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(targetClass.getPackage().getName(), type.getQualifiedSourceName()+"JSONConvert");
        composerFactory.addImport(ServiceDefTarget.class.getCanonicalName());
        composerFactory.addImport(IllegalArgumentException.class.getCanonicalName());
        composerFactory.addImport(JSONParser.class.getCanonicalName());
        composerFactory.addImport(JSONValue.class.getCanonicalName());
        composerFactory.addImport(JSONArray.class.getCanonicalName());
        composerFactory.addImport(JSONBoolean.class.getCanonicalName());
        composerFactory.addImport(JSONNull.class.getCanonicalName());
        composerFactory.addImport(JSONNumber.class.getCanonicalName());
        composerFactory.addImport(JSONObject.class.getCanonicalName());
        composerFactory.addImport(JSONString.class.getCanonicalName());
        composerFactory.addImport(AsyncCallback.class.getCanonicalName());
        composerFactory.setSuperclass(getClass().getCanonicalName());

        return null;
    }



}
