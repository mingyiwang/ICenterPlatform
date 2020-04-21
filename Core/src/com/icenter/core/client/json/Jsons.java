package com.icenter.core.client.json;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.json.client.*;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import java.io.PrintWriter;
import java.util.HashMap;

public final class Jsons {

   private final static HashMap<String, JsonConverter> factories = new HashMap<>();

   public static JsonConverter get(String name){
       return factories.get(name);
   }

    public static String createIfNotExist(TreeLogger treeLogger, GeneratorContext generatorContext, JType type) {
        if (get(type.getParameterizedQualifiedSourceName()) != null) {
            return type.getParameterizedQualifiedSourceName();
        }

        JClassType target = generatorContext.getTypeOracle().findType(type.getParameterizedQualifiedSourceName());
        String proxyClassName = target.getName()+"JsonConverter";
        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(target.getPackage().getName(), proxyClassName);
        composerFactory.addImport(JSONValue.class.getCanonicalName());
        composerFactory.addImport(JSONArray.class.getCanonicalName());
        composerFactory.addImport(JSONBoolean.class.getCanonicalName());
        composerFactory.addImport(JSONNull.class.getCanonicalName());
        composerFactory.addImport(JSONNumber.class.getCanonicalName());
        composerFactory.addImport(JSONObject.class.getCanonicalName());
        composerFactory.addImport(JSONString.class.getCanonicalName());

        composerFactory.setSuperclass(JsonConverter.class.getCanonicalName()+"<target.getName()>");

        PrintWriter pw  = generatorContext.tryCreate(treeLogger, target.getPackage().getName(), proxyClassName);
        if (pw == null){
            return target.getPackage().getName() + "." + proxyClassName;
        }
        else {

        }

        return type.getParameterizedQualifiedSourceName();
    }

}
