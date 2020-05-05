package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.reflect.JTypeInfo;

/***
 * JSONConvertible Object Converter, used to generates object converters, shouldn't use it directly
 */
public final class JSONConverterGenerator  {

    private final static String packagePath = "com.icenter.core.client.rest.convert";

    public final static String generate(TreeLogger logger, GeneratorContext context, JType targetType){
        if(JTypeInfo.isPrimitive(targetType)){
            return generatePrimitive(logger, context, targetType);
        }
        else if(JTypeInfo.isMap(targetType, context.getTypeOracle())){
            return generateMap(logger, context, targetType);
        }
        else if(JTypeInfo.isArray(targetType)){
            return generateArray(logger, context, targetType);
        }
        else if(JTypeInfo.isList(targetType, context.getTypeOracle())){
            return generateList(logger, context, targetType);
        }
        else {
            return generateClass(logger, context, targetType);
        }
    }

    public final static String generatePrimitive(TreeLogger logger, GeneratorContext context, JType primitiveType) {
        String converterKey = primitiveType.isPrimitive() != null
                            ? primitiveType.isPrimitive().getQualifiedBoxedSourceName()
                            : primitiveType.getQualifiedSourceName();
        return Converters.get(converterKey).getClass().getCanonicalName();
    }

    public final static String generateArray(TreeLogger logger, GeneratorContext context, JType targetType){
        JArrayType arrayType = targetType.isArray();
        return generate(logger, context, arrayType.getComponentType());
    }

    public final static String generateList(TreeLogger logger, GeneratorContext context, JType targetType){
        JParameterizedType classType = targetType.isParameterized();
        JType key = classType.getTypeArgs()[0];
        JType value = classType.getTypeArgs()[1];
        ClassSourceFileComposerFactory composer = createClassSourceComoser(targetType, classType.getName());

        return Strings.Empty;
    }

    public final static String generateMap(TreeLogger logger, GeneratorContext context, JType targetType){
        JParameterizedType classType = targetType.isParameterized();
        JType key   = classType.getTypeArgs()[0];
        JType value = classType.getTypeArgs()[1];
        ClassSourceFileComposerFactory composer = createClassSourceComoser(targetType, classType.getName());
        // Generates key and value converters

        return Strings.Empty;
    }

    public final static String generateClass(TreeLogger logger, GeneratorContext context, JType targetType) {
        String sourceName = targetType.isClassOrInterface().getName() + "JSONConverter";
        String qualifiedSourceName = packagePath + "." + sourceName;
        JClassType classType = targetType.isClass();
        ClassSourceFileComposerFactory composer = createClassSourceComoser(targetType, sourceName);

        return qualifiedSourceName;
    }

    private final static ClassSourceFileComposerFactory createClassSourceComoser(JType targetType, String sourceName) {
        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(packagePath, sourceName);
        composerFactory.addImport(JSONParser.class.getCanonicalName());
        composerFactory.addImport(JSONValue.class.getCanonicalName());
        composerFactory.addImport(JSONArray.class.getCanonicalName());
        composerFactory.addImport(JSONBoolean.class.getCanonicalName());
        composerFactory.addImport(JSONNull.class.getCanonicalName());
        composerFactory.addImport(JSONNumber.class.getCanonicalName());
        composerFactory.addImport(JSONObject.class.getCanonicalName());
        composerFactory.addImport(JSONString.class.getCanonicalName());
        composerFactory.addImport(JSONProperty.class.getCanonicalName());
        composerFactory.addImport(AsyncCallback.class.getCanonicalName());
        composerFactory.addImport(JSONConverter.class.getCanonicalName());
        composerFactory.addImport(targetType.isClassOrInterface().getParameterizedQualifiedSourceName());
        return composerFactory;
    }

//    private final String getCanonicalName(JClassType targetType, TypeOracle types){
//        String proxyClassName = targetType.getName() + JSONConverter.Name;
//
//        JTypeInfo.isPrimitive(targetType);
//        JTypeInfo.isCollection(targetType, types);
//        JTypeInfo.isList(targetType, types);
//        JTypeInfo.isMap(targetType, types);
//        JTypeInfo.isQueue(targetType, types);
//        return Joiner.on('.').join(packagePath, proxyClassName);
//    }
//
//    private final String createJSONConverterIfNotExist(TreeLogger logger, GeneratorContext context, String packagePath, JType targetType) {
//
//        JClassType target = context.getTypeOracle().findType(targetType.getParameterizedQualifiedSourceName());
//        String proxyClassName = target.getName() + JSONConverter.Name;
//
//        if (Converters.getOrCreateIfNotExist(targetType.getParameterizedQualifiedSourceName()) != null) {
//            return packagePath + "." + proxyClassName;
//        }
//
//        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(packagePath, proxyClassName);
//        composerFactory.addImport(JSONValue.class.getCanonicalName());
//        composerFactory.addImport(JSONArray.class.getCanonicalName());
//        composerFactory.addImport(JSONBoolean.class.getCanonicalName());
//        composerFactory.addImport(JSONNull.class.getCanonicalName());
//        composerFactory.addImport(JSONNumber.class.getCanonicalName());
//        composerFactory.addImport(JSONObject.class.getCanonicalName());
//        composerFactory.addImport(JSONString.class.getCanonicalName());
//        composerFactory.addImport(target.getParameterizedQualifiedSourceName());
//        composerFactory.setSuperclass(JSONConverter.class.getCanonicalName() + "<" + target.getName() + ">");
//
//        PrintWriter pw = context.tryCreate(logger, packagePath, proxyClassName);
//        if (pw == null) {
//            return packagePath + "." + proxyClassName;
//        }
//        else {
//            SourceWriter sw = composerFactory.createSourceWriter(context, pw);
//            sw.println("@Override public "+ proxyClassName + " createInstance(){ ");
//            sw.println("return new " + proxyClassName + "();");
//            sw.println("}");
//
//            sw.println("@Override public JSONValue convertObjectToJSON(ProxyClassName object){ ");
//            sw.println("JSONObject object = new JSONObject();");
////            sw.println("JSONValue value = new JSONValue();");
////            sw.println("if(property.isPrimitive()){object.put(property.getName(),object.property.getterName());}");
////            sw.println("else if(property.isClass()){Streams.of(property).forEach(p -> )}");
//            sw.println("return object;" + "}");
//
//            sw.println("@Override public proxyClassName convertJSONToObject(JSONValue value){ ");
//            sw.println("proxyClassName object = createInstance();");
////          sw.println("if(property.isPrimitive()) { object.put(property.getName(),object.property.getterName());}");
////          sw.println("else if(property.isClass()){ Streams.of(property).forEach(p -> )}");
//            sw.println("return object;" + "}");
//
//            sw.commit(logger);
//        }
//
//
//        return packagePath + "." + proxyClassName;
//    }
//    private ClassSourceFileComposerFactory getClassSourceFileComposerFactory(String packagePath, JClassType target, String proxyClassName) {
//        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(packagePath, proxyClassName);
//        composerFactory.addImport(JSONValue.class.getCanonicalName());
//        composerFactory.addImport(JSONArray.class.getCanonicalName());
//        composerFactory.addImport(JSONBoolean.class.getCanonicalName());
//        composerFactory.addImport(JSONNull.class.getCanonicalName());
//        composerFactory.addImport(JSONNumber.class.getCanonicalName());
//        composerFactory.addImport(JSONObject.class.getCanonicalName());
//        composerFactory.addImport(JSONString.class.getCanonicalName());
//        composerFactory.addImport(target.getParameterizedQualifiedSourceName());
//        return composerFactory;
//    }
//
//    private final String createJSONPropertyIfNotExist(TreeLogger logger, GeneratorContext context, String packagePath, JType targetType){
//        JClassType target = context.getTypeOracle().findType(targetType.getParameterizedQualifiedSourceName());
//        String proxyClassName = target.getName() + "JSONProperty";
//        ClassSourceFileComposerFactory composerFactory = getClassSourceFileComposerFactory(packagePath, target, proxyClassName);
//        composerFactory.setSuperclass(JSONProperty.class.getCanonicalName());
//        PrintWriter pw = context.tryCreate(logger, packagePath, proxyClassName);
//        if (pw == null) {
//            return packagePath + "." + proxyClassName;
//        }
//        else {
//            SourceWriter sw = composerFactory.createSourceWriter(context, pw);
//            sw.println("public void setUpProperty(String propertyClassName){");
//
//            sw.println("}");
//        }
//        return packagePath + "." + proxyClassName;
//
//    }
//
//    private final String setUpProperty(String propertyClassName, JType type){
//        StringBuilder builder = new StringBuilder();
//        builder.append("JSONProperty property = new " + propertyClassName + "();");
//        if(type.isArray() != null) {
//            builder.append(propertyClassName+".setArray(true);");
//            JArrayType arrayType = type.isArray();
//            JArrayType[] types = arrayType.getSubtypes();
//            Stream.of(types).forEach(t -> {
//                String propertyClass = createJSONPropertyIfNotExist(null, null, null, t.getComponentType());
//                setUpProperty(propertyClass, t.getComponentType());
//
//            });
//        }
//        if(type.isPrimitive() != null) {
//            builder.append(propertyClassName + ".setPrimitive(true);");
//        }
//        if(type.isClass() != null){
//            builder.append(propertyClassName + ".setClass(true);");
//        }
//        if(type.isGenericType() != null){
//            JParameterizedType g = type.isParameterized();
//            JClassType[] genericTypes = g.getTypeArgs();
//            Stream.of(genericTypes).forEach(t -> {
//                String propertyClass = createJSONPropertyIfNotExist(null, null, null, t);
//                setUpProperty(propertyClass, t);
//            });
//        }
//
//        builder.append("return property;");
//        return builder.toString();
//    }

}
