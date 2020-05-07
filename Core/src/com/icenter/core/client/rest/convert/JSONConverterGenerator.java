package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.icenter.core.client.reflect.JTypeInfo;
import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.core.client.rest.RemoteRESTServiceImpl;
import com.icenter.core.client.rest.convert.custom.*;
import java.io.PrintWriter;
import java.util.stream.Stream;

/***
 * JSONConvertible Object Converter, used to generates object converters, shouldn't use it directly
 */
public final class JSONConverterGenerator  {

    private final static String packagePath = "com.icenter.core.client.rest.convert";

    public final static String generate(TreeLogger logger, GeneratorContext context, JType targetType){
        if (SimpleConverters.get(targetType.getQualifiedSourceName()) != null) {
            return SimpleConverters.get(targetType.getQualifiedSourceName()).getClass().getCanonicalName();
        }

        if(JTypeInfo.isPrimitive(targetType)){
           return generatePrimitive(logger, context, targetType);
        }
        else {
           return generateClass(logger, context, targetType);
        }
    }

    private final static String generatePrimitive(TreeLogger logger, GeneratorContext context, JType primitiveType) {
        String typeQualifiedName = primitiveType.isPrimitive() != null
                            ? primitiveType.isPrimitive().getQualifiedBoxedSourceName()
                            : primitiveType.getQualifiedSourceName();
        return SimpleConverters.get(typeQualifiedName).getClass().getCanonicalName();
    }

    private final static String generateClass(TreeLogger logger, GeneratorContext context, JType targetType) {
        String targetTypeClassName = targetType.isClassOrInterface().getName();
        String sourceName = targetTypeClassName + JSONConverter.class.getSimpleName();

        String qualifiedSourceName = packagePath + "." + sourceName;
        ClassSourceFileComposerFactory composer = createClassSourceComposer(targetType, sourceName);
        composer.setSuperclass("JSONConverter<"+ targetTypeClassName +">");

        PrintWriter pw = context.tryCreate(logger, packagePath, sourceName);
        if(pw == null) {
           return qualifiedSourceName;
        }
        else {
            String targetTypeQualifiedName = targetType.getQualifiedSourceName();
            SourceWriter sw = composer.createSourceWriter(context, pw);
            // Generate new instance method

            sw.println("@Override public "+ targetType.isClassOrInterface().getName() + " createInstance(){ ");
            sw.println("return new " + targetTypeQualifiedName + "();");
            sw.println("}");

            sw.println("@Override public JSONValue convertObjectToJSON(" + targetTypeQualifiedName +" instance){ ");
            sw.println("if (instance == null) {return JSONNull.getInstance();}");
            sw.println("JSONObject jsonObject = new JSONObject();");
            JField[] fields = targetType.isClassOrInterface().getFields();

            Stream.of(fields).forEach(f -> {
                sw.println("jsonObject.put("+"\""+f.getName()+"\"" + ","
                         + "new " + JSONConverterGenerator.generate(logger, context, f.getType())+"().convertObjectToJSON("+
                           "instance."+f.getName()+"));");
            });

            sw.println("return jsonObject;}");
            sw.println("@Override public " + targetTypeQualifiedName + " convertJSONToObject(JSONValue value){ return createInstance();}");
            sw.commit(logger);
        }

        return qualifiedSourceName;
    }

    private final static ClassSourceFileComposerFactory createClassSourceComposer(JType targetType, String sourceName) {
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
        composerFactory.addImport(RemoteRESTService.class.getCanonicalName());
        composerFactory.addImport(RemoteRESTServiceImpl.class.getCanonicalName());
        composerFactory.addImport(JSONConverter.class.getCanonicalName());
        composerFactory.addImport(BooleanJSONConverter.class.getCanonicalName());
        composerFactory.addImport(ByteJSONConverter.class.getCanonicalName());
        composerFactory.addImport(CharacterJSONConverter.class.getCanonicalName());
        composerFactory.addImport(DateJSONConverter.class.getCanonicalName());
        composerFactory.addImport(DoubleJSONConverter.class.getCanonicalName());
        composerFactory.addImport(FloatJSONConverter.class.getCanonicalName());
        composerFactory.addImport(IntegerJSONConverter.class.getCanonicalName());
        composerFactory.addImport(LongJSONConverter.class.getCanonicalName());
        composerFactory.addImport(ShortJSONConverter.class.getCanonicalName());
        composerFactory.addImport(StringJSONConverter.class.getCanonicalName());
        composerFactory.addImport(SimpleConverters.class.getCanonicalName());
        composerFactory.addImport(targetType.isClassOrInterface().getParameterizedQualifiedSourceName());
        return composerFactory;
    }


}
