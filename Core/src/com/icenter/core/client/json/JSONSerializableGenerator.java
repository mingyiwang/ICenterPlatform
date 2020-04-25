package com.icenter.core.client.json;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.json.client.*;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import java.io.PrintWriter;

public class JSONSerializableGenerator extends Generator {

    @Override
    public String generate(TreeLogger treeLogger, GeneratorContext generatorContext, String s) throws UnableToCompleteException {
        JType targetType = generatorContext.getTypeOracle().findType(s);
        return null;
    }


    private final String createJSONPropertyIfNotExist(){
        return null;
    }

    private final String createJSONConverterIfNotExist(TreeLogger logger, GeneratorContext context, String packageName, JType targetType) {

        JClassType target = context.getTypeOracle().findType(targetType.getParameterizedQualifiedSourceName());
        String proxyClassName = target.getName() + JSONConverter.Name;

        if (Converters.get(targetType.getParameterizedQualifiedSourceName()) != null) {
            return packageName + "." + proxyClassName;
        }

        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(packageName, proxyClassName);
        composerFactory.addImport(JSONValue.class.getCanonicalName());
        composerFactory.addImport(JSONArray.class.getCanonicalName());
        composerFactory.addImport(JSONBoolean.class.getCanonicalName());
        composerFactory.addImport(JSONNull.class.getCanonicalName());
        composerFactory.addImport(JSONNumber.class.getCanonicalName());
        composerFactory.addImport(JSONObject.class.getCanonicalName());
        composerFactory.addImport(JSONString.class.getCanonicalName());
        composerFactory.addImport(target.getParameterizedQualifiedSourceName());
        composerFactory.setSuperclass(JSONConverter.class.getCanonicalName() + "<" + target.getName() + ">");

        PrintWriter pw = context.tryCreate(logger, packageName, proxyClassName);
        if (pw == null) {
            return packageName + "." + proxyClassName;
        }
        else {
            SourceWriter sw = composerFactory.createSourceWriter(context, pw);
            sw.println("@Override public "+ proxyClassName + " createInstance(){ ");
            sw.println("return new " + proxyClassName + "();");
            sw.println("}");

            sw.println("@Override public JSONValue handle(ProxyClassName object){ ");
            sw.println("JSONObject object = new JSONObject();");
//            sw.println("JSONValue value = new JSONValue();");
//            sw.println("if(property.isPrimitive()){object.put(property.getName(),object.property.getterName());}");
//            sw.println("else if(property.isClass()){Streams.of(property).forEach(p -> )}");
            sw.println("return object;" + "}");

            sw.println("@Override public proxyClassName handle(JSONValue value){ ");
            sw.println("T object = createInstance();");
//            sw.println("if(property.isPrimitive()) { object.put(property.getName(),object.property.getterName());}");
//            sw.println("else if(property.isClass()){ Streams.of(property).forEach(p -> )}");
            sw.println("return object;" + "}");

            sw.commit(logger);
        }


        return packageName + "." + proxyClassName;
    }

}
