package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.icenter.core.client.reflect.Reflects;
import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.core.client.rest.RemoteRESTServiceImpl;
import com.icenter.core.client.rest.convert.base.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/***
 * JSONConvertible Object Converter, used to generates object converters,
 * shouldn't use it directly not responsible for any cost of direct use.
 */
public final class JSONConverterGenerator  {

    private final static String packagePath = "com.icenter.core.client.rest.convert";

    public final static String generate(TreeLogger logger, GeneratorContext context, JType targetType){
        if(Reflects.isPrimitive(targetType)){
           return generatePrimitive(targetType);
        }
        else if(Reflects.isArray(targetType)){
            return generateArray(logger, context, targetType);
        }
        else if(Reflects.isList(targetType, context.getTypeOracle())){
           return generateList(logger, context, targetType);
        }
        else {
           return generateClass(logger, context, targetType);
        }
    }

    private final static String generatePrimitive(JType primitiveType) {
        String typeQualifiedName = primitiveType.isPrimitive() != null
                                 ? primitiveType.isPrimitive().getQualifiedBoxedSourceName()
                                 : primitiveType.getQualifiedSourceName();
        return SimpleConverters.get(typeQualifiedName).getClass().getCanonicalName();
    }

    private final static String generateArray(TreeLogger logger, GeneratorContext context, JType targetType) {
        JType componentType = targetType.isArray().getComponentType();
        String sourceName = componentType.getSimpleSourceName() + "Array" + JSONConverter.class.getSimpleName();
        String qualifiedSourceName = packagePath + "." + sourceName;

        ClassSourceFileComposerFactory composer = createJSONConverterClassComposer(targetType, sourceName);
        composer.addImport(AbstractListJSONConverter.class.getCanonicalName());
        composer.addImport(componentType.getParameterizedQualifiedSourceName());
        composer.setSuperclass("AbstractArrayJSONConverter<" + componentType.getParameterizedQualifiedSourceName() + ">");
        PrintWriter pw = context.tryCreate(logger, packagePath, sourceName);
        if(pw == null) {
            return qualifiedSourceName;
        }
        else {
            SourceWriter sw = composer.createSourceWriter(context, pw);
            sw.println("@Override public JSONConverter<" + componentType.getParameterizedQualifiedSourceName() + "> getConverter(){ ");
            sw.println("return new " + JSONConverterGenerator.generate(logger, context, componentType) + "();");
            sw.println("}");
            sw.commit(logger);
        }
        return qualifiedSourceName;
    }

    private final static String generateList(TreeLogger logger, GeneratorContext context, JType targetType) {
        JClassType componentType  = getComponentType(targetType);
        String sourceName = componentType.getName() +"List" + JSONConverter.class.getSimpleName();
        String qualifiedSourceName = packagePath + "." + sourceName;

        ClassSourceFileComposerFactory composer = createJSONConverterClassComposer(targetType, sourceName);
        composer.addImport(AbstractListJSONConverter.class.getCanonicalName());
        composer.addImport(componentType.getParameterizedQualifiedSourceName());

        composer.setSuperclass("AbstractListJSONConverter<" + componentType.getName() + ">");
        PrintWriter pw = context.tryCreate(logger, packagePath, sourceName);
        if(pw == null) {
            return qualifiedSourceName;
        }
        else {
            SourceWriter sw = composer.createSourceWriter(context, pw);
            sw.println("@Override public JSONConverter<" + componentType.isClassOrInterface().getParameterizedQualifiedSourceName() + "> getConverter(){ ");
            sw.println("return new " + JSONConverterGenerator.generate(logger, context, componentType) + "();");
            sw.println("}");
            sw.commit(logger);
        }
        return qualifiedSourceName;
    }

    private final static String generateClass(TreeLogger logger, GeneratorContext context, JType targetType) {
        String targetTypeClassName = targetType.isClassOrInterface().getName();
        String sourceName = targetTypeClassName + JSONConverter.class.getSimpleName();

        String qualifiedSourceName = packagePath + "." + sourceName;
        ClassSourceFileComposerFactory composer = createJSONConverterClassComposer(targetType, sourceName);
        composer.addImport(targetType.getQualifiedSourceName());
        composer.setSuperclass("JSONConverter<"+ targetType.getQualifiedSourceName() +">");

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

            // Generate object to json method
            System.out.println("@Override public JSONValue convertObjectToJSON(" + targetTypeQualifiedName +" instance){ ");
            sw.println("@Override public JSONValue convertObjectToJSON(" + targetTypeQualifiedName +" instance){ ");
            sw.println("if (instance == null) {return JSONNull.getInstance();}");//should we handle null value?
            sw.println("JSONObject jsonObject = new JSONObject();");

            JField[] fields = targetType.isClassOrInterface().getFields();
            Stream.of(fields).forEach(f -> {
                String converterName = JSONConverterGenerator.generate(logger, context, f.getType());
                sw.println("jsonObject.put("+"\""+f.getName()+"\"" + "," + "new " + converterName +"().convertObjectToJSON("+ "instance."+f.getName()+"));");
            });
            sw.println("return jsonObject;}");

            // Generate Json to object method
            sw.println("@Override public " + targetTypeQualifiedName + " convertJSONToObject(JSONValue value){ ");
            sw.println(targetTypeQualifiedName + " instance = createInstance();");
            JField[] properties = targetType.isClassOrInterface().getFields();
            Stream.of(properties).forEach(f -> {
                String converterName = JSONConverterGenerator.generate(logger, context, f.getType());
                sw.println("instance."+f.getName()+"\"" + "," + "new " + converterName +"().convertJSONToObject("+ "value.isObject().get("+f.getName()+"));");
            });
            sw.println("return instance;}");
            sw.commit(logger);
        }

        return qualifiedSourceName;
    }

    private final static ClassSourceFileComposerFactory createJSONConverterClassComposer(JType targetType, String sourceName) {
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
        composerFactory.addImport(List.class.getCanonicalName());
        composerFactory.addImport(ArrayList.class.getCanonicalName());
        return composerFactory;
    }

    private static JClassType getComponentType(JType target){
        if (target.isParameterized() != null){
            return target.isParameterized().getTypeArgs()[0];
        }
        return getComponentType(target.isClassOrInterface().getSuperclass());
    }

    private static JClassType getValueComponentTypeOfMap(JType target){
        if (target.isParameterized() != null){
            return target.isParameterized().getTypeArgs()[1];
        }
        return getValueComponentTypeOfMap(target.isClassOrInterface().getSuperclass());
    }

    private static String getSetValueMethod(JField field){
        return "set" + field.getName();
    }

    private static String getGetValueMethod(JField field){
        return "get" + field.getName();
    }

}
