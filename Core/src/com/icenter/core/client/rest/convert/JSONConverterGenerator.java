package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.icenter.core.client.reflect.JTypeInfo;
import java.io.PrintWriter;

/***
 * JSONConvertible Object Converter, used to generates object converters, shouldn't use it directly
 */
public final class JSONConverterGenerator  {

    private final static String packagePath = "com.icenter.core.client.rest.convert";

    public final static String generate(TreeLogger logger, GeneratorContext context, JType targetType){
        if(JTypeInfo.isPrimitive(targetType)){
            return generatePrimitive(logger, context, targetType);
        }
        else {
            return generateClass(logger, context, targetType);
        }
    }

    public final static String generatePrimitive(TreeLogger logger, GeneratorContext context, JType primitiveType) {
        String typeQualifiedName = primitiveType.isPrimitive() != null
                            ? primitiveType.isPrimitive().getQualifiedBoxedSourceName()
                            : primitiveType.getQualifiedSourceName();
        return SimpleConverters.get(typeQualifiedName).getClass().getCanonicalName();
    }

    public final static String generateClass(TreeLogger logger, GeneratorContext context, JType targetType) {
        String targetClassName = targetType.isClassOrInterface().getName();
        String sourceName = targetType.isClassOrInterface().getName() + JSONConverter.class.getSimpleName();
        String qualifiedSourceName = packagePath + "." + sourceName;
        ClassSourceFileComposerFactory composer = createClassSourceComoser(targetType, sourceName);
        composer.setSuperclass("JSONConverter<"+targetClassName+">");

        PrintWriter pw = context.tryCreate(logger, packagePath, sourceName);
        if(pw == null) {
           return qualifiedSourceName;
        }
        else {
            String targetQualifiedName = targetType.getQualifiedSourceName();
            SourceWriter sw = composer.createSourceWriter(context, pw);
            // Generate new instance method
            sw.println("@Override public "+ targetType.isClassOrInterface().getName() + " createInstance(){ ");
            // create new instance based on different types.
            sw.println("}");

            sw.println("@Override public JSONValue convertObjectToJSON(" + targetQualifiedName +" object){ ");
            sw.println("JSONObject object = new JSONObject();");
            sw.println("return object;}");

            sw.println("@Override public " + targetQualifiedName + " convertJSONToObject(JSONValue value){ ");
            sw.println(targetQualifiedName + " object = createInstance();");
            sw.println("return object;}");
        }
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


}
