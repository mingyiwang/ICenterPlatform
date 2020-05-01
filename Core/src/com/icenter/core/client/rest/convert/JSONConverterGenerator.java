package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.icenter.core.client.primitive.Joiner;
import com.icenter.core.client.reflect.JTypeInfo;
import java.io.PrintWriter;
import java.util.stream.Stream;

public class JSONConverterGenerator extends Generator {

    private String packageName = "com.icenter.core.client.rest.convert";

    @Override
    public String generate(TreeLogger logger, GeneratorContext context, String targetTypeName) throws UnableToCompleteException {
        JClassType targetType = context.getTypeOracle().findType(targetTypeName);
        targetType.isClassOrInterface();
        String proxyClassName = targetType.getName() + JSONConverter.Name;
        String canonicalName  = Joiner.on('.').join(packageName, proxyClassName);
        return canonicalName;
    }

    private final String getCanonicalName(JClassType targetType, TypeOracle types){
        String proxyClassName = targetType.getName() + JSONConverter.Name;

        JTypeInfo.isPrimitive(targetType);
        JTypeInfo.isCollection(targetType, types);
        JTypeInfo.isList(targetType, types);
        JTypeInfo.isMap(targetType, types);
        JTypeInfo.isQueue(targetType, types);
        return Joiner.on('.').join(packageName, proxyClassName);
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

            sw.println("@Override public JSONValue convertObjectToJSON(ProxyClassName object){ ");
            sw.println("JSONObject object = new JSONObject();");
//            sw.println("JSONValue value = new JSONValue();");
//            sw.println("if(property.isPrimitive()){object.put(property.getName(),object.property.getterName());}");
//            sw.println("else if(property.isClass()){Streams.of(property).forEach(p -> )}");
            sw.println("return object;" + "}");

            sw.println("@Override public proxyClassName convertJSONToObject(JSONValue value){ ");
            sw.println("proxyClassName object = createInstance();");
//          sw.println("if(property.isPrimitive()) { object.put(property.getName(),object.property.getterName());}");
//          sw.println("else if(property.isClass()){ Streams.of(property).forEach(p -> )}");
            sw.println("return object;" + "}");

            sw.commit(logger);
        }


        return packageName + "." + proxyClassName;
    }
    private ClassSourceFileComposerFactory getClassSourceFileComposerFactory(String packageName, JClassType target, String proxyClassName) {
        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(packageName, proxyClassName);
        composerFactory.addImport(JSONValue.class.getCanonicalName());
        composerFactory.addImport(JSONArray.class.getCanonicalName());
        composerFactory.addImport(JSONBoolean.class.getCanonicalName());
        composerFactory.addImport(JSONNull.class.getCanonicalName());
        composerFactory.addImport(JSONNumber.class.getCanonicalName());
        composerFactory.addImport(JSONObject.class.getCanonicalName());
        composerFactory.addImport(JSONString.class.getCanonicalName());
        composerFactory.addImport(target.getParameterizedQualifiedSourceName());
        return composerFactory;
    }

    private final String createJSONPropertyIfNotExist(TreeLogger logger, GeneratorContext context, String packageName, JType targetType){
        JClassType target = context.getTypeOracle().findType(targetType.getParameterizedQualifiedSourceName());
        String proxyClassName = target.getName() + "JSONProperty";
        ClassSourceFileComposerFactory composerFactory = getClassSourceFileComposerFactory(packageName, target, proxyClassName);
        composerFactory.setSuperclass(JSONProperty.class.getCanonicalName());
        PrintWriter pw = context.tryCreate(logger, packageName, proxyClassName);
        if (pw == null) {
            return packageName + "." + proxyClassName;
        }
        else {
            SourceWriter sw = composerFactory.createSourceWriter(context, pw);
            sw.println("public void setUpProperty(String propertyClassName){");

            sw.println("}");
        }
        return packageName + "." + proxyClassName;

    }

    private final String setUpProperty(String propertyClassName, JType type){
        StringBuilder builder = new StringBuilder();
        builder.append("JSONProperty property = new " + propertyClassName + "();");
        if(type.isArray() != null) {
            builder.append(propertyClassName+".setArray(true);");
            JArrayType arrayType = type.isArray();
            JArrayType[] types = arrayType.getSubtypes();
            Stream.of(types).forEach(t -> {
                String propertyClass = createJSONPropertyIfNotExist(null, null, null, t.getComponentType());
                setUpProperty(propertyClass, t.getComponentType());

            });
        }
        if(type.isPrimitive() != null) {
            builder.append(propertyClassName + ".setPrimitive(true);");
        }
        if(type.isClass() != null){
            builder.append(propertyClassName + ".setClass(true);");
        }
        if(type.isGenericType() != null){
            JParameterizedType g = type.isParameterized();
            JClassType[] genericTypes = g.getTypeArgs();
            Stream.of(genericTypes).forEach(t -> {
                String propertyClass = createJSONPropertyIfNotExist(null, null, null, t);
                setUpProperty(propertyClass, t);
            });
        }

        builder.append("return property;");
        return builder.toString();
    }

}
