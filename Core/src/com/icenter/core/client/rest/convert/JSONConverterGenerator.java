package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.reflect.JFieldStream;
import com.icenter.core.client.reflect.Reflects;
import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.core.client.rest.RemoteRESTServiceHelper;
import com.icenter.core.client.rest.RemoteRESTServiceImpl;
import com.icenter.core.client.rest.convert.base.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/***
 * JSONConvertible Object Converter, used to generates object converters,
 * shouldn't use it directly not responsible for any cost of direct use.
 */
public final class JSONConverterGenerator  {

    // classes generated should be under below package path.
    private final static String packagePath = "com.icenter.core.client.rest.convert";

    /**
     * Returns newly generated JSONConverter class based the targetType.
     * */
    public final static String generate(TreeLogger logger, GeneratorContext context, JType targetType) {
        if (!RemoteRESTServiceHelper.isValidType(targetType, context.getTypeOracle())) {
             System.out.println(targetType.getSimpleSourceName() + " is not valid type.");
             return Strings.Empty;
        }

        if(Reflects.isPrimitive(targetType)){
           return generatePrimitive(logger,context, targetType);
        }
        else if(Reflects.isArray(targetType)){
           return generateArray(logger,context,targetType.isArray());
        }
        else if(Reflects.isList(targetType,context.getTypeOracle())){
           return generateList(logger,context, targetType.isClassOrInterface());
        }
        else if(Reflects.isMap(targetType, context.getTypeOracle())){
           return generateMap(logger, context, targetType.isClassOrInterface());
        }
        else {
           return generateClass(logger,context,targetType.isClassOrInterface());
        }
    }

    private final static String generatePrimitive(TreeLogger logger, GeneratorContext context, JType primitiveType)  {
        String typeQualifiedName = primitiveType.isPrimitive() != null
                                 ? primitiveType.isPrimitive().getQualifiedBoxedSourceName()
                                 : primitiveType.getQualifiedSourceName();

        return SimpleConverters.get(typeQualifiedName).getClass().getCanonicalName();
    }

    private final static String generateArray(TreeLogger logger, GeneratorContext context, JArrayType targetType)  {
        JType componentType = targetType.getComponentType();

        String sourceName = componentType.getSimpleSourceName() + "Array" + JSONConverter.class.getSimpleName();
        String qualifiedSourceName = packagePath + "." + sourceName;

        ClassSourceFileComposerFactory composer = createSourceComposer(sourceName);
        composer.addImport(AbstractArrayJSONConverter.class.getCanonicalName());
        composer.addImport(componentType.getQualifiedSourceName());
        composer.setSuperclass(AbstractArrayJSONConverter.class.getCanonicalName()+"<" + componentType.getQualifiedSourceName() + ">");

        PrintWriter pw = context.tryCreate(logger, packagePath, sourceName);
        if(pw == null) {
           return qualifiedSourceName;
        }
        else {
            SourceWriter sw = composer.createSourceWriter(context, pw);
            System.out.println("@Override public " + JSONConverter.class.getCanonicalName()+"<" + componentType.getQualifiedSourceName() + "> createConverter(){ ");

            sw.println("@Override public " + JSONConverter.class.getCanonicalName()+"<" + componentType.getQualifiedSourceName() + "> createConverter(){ ");
            sw.println("return new " + JSONConverterGenerator.generate(logger, context, componentType) + "();");
            sw.println("}");
            sw.commit(logger);
        }
        return qualifiedSourceName;
    }

    private final static String generateMap(TreeLogger logger, GeneratorContext context, JClassType targetType) {
        JClassType keyType = getTypeArg(targetType);
        JClassType valueType = getSecondTypeArg(targetType);

        String sourceName = keyType.getName() + valueType.getName()+ "Map" + JSONConverter.class.getSimpleName();
        String qualifiedSourceName = packagePath + "." + sourceName;

        ClassSourceFileComposerFactory composer = createSourceComposer(sourceName);
        composer.addImport(AbstractMapJSONConverter.class.getCanonicalName());
        composer.addImport(keyType.getParameterizedQualifiedSourceName());
        composer.addImport(valueType.getParameterizedQualifiedSourceName());
        composer.setSuperclass(AbstractMapJSONConverter.class.getCanonicalName() + "<" + keyType.getName()+"," + valueType.getName()+ ">");

        PrintWriter pw = context.tryCreate(logger, packagePath, sourceName);
        if(pw == null) {
            return qualifiedSourceName;
        }
        else {
            SourceWriter sw = composer.createSourceWriter(context, pw);
            sw.println("@Override public JSONConverter<" + keyType.getParameterizedQualifiedSourceName() + "> createKeyConverter(){ ");
            sw.println("return new " + JSONConverterGenerator.generate(logger, context, keyType) + "();");
            sw.println("}");

            sw.println("@Override public JSONConverter<" + valueType.getParameterizedQualifiedSourceName() + "> createValueConverter(){ ");
            sw.println("return new " + JSONConverterGenerator.generate(logger, context, valueType) + "();");
            sw.println("}");
            sw.commit(logger);
        }
        return qualifiedSourceName;
    }

    private final static String generateList(TreeLogger logger, GeneratorContext context, JClassType targetType) {
        JClassType componentType = getTypeArg(targetType);
        String sourceName = componentType.getName() +"List" + JSONConverter.class.getSimpleName();
        String qualifiedSourceName = packagePath + "." + sourceName;

        ClassSourceFileComposerFactory composer = createSourceComposer(sourceName);
        composer.addImport(AbstractListJSONConverter.class.getCanonicalName());
        composer.addImport(componentType.getParameterizedQualifiedSourceName());
        composer.setSuperclass(AbstractListJSONConverter.class.getCanonicalName() + "<" + componentType.getParameterizedQualifiedSourceName() + ">");

        PrintWriter pw = context.tryCreate(logger, packagePath, sourceName);
        if(pw == null) {
            return qualifiedSourceName;
        }
        else {
            SourceWriter sw = composer.createSourceWriter(context, pw);
            System.out.println("@Override public JSONConverter<" + componentType.getParameterizedQualifiedSourceName() + "> createConverter(){ ");
            sw.println("@Override public JSONConverter<" + componentType.getParameterizedQualifiedSourceName() + "> createConverter(){ ");
            sw.println("return new " + JSONConverterGenerator.generate(logger, context, componentType) + "();");
            sw.println("}");
            sw.commit(logger);
        }
        return qualifiedSourceName;
    }

    private final static String generateClass(TreeLogger logger, GeneratorContext context, JClassType targetType) {
        String targetTypeClassName = targetType.getName();
        String sourceName = targetTypeClassName + JSONConverter.class.getSimpleName();
        String qualifiedSourceName = packagePath + "." + sourceName;

        ClassSourceFileComposerFactory composer = createSourceComposer(sourceName);
        composer.addImport(targetType.getQualifiedSourceName());
        composer.setSuperclass(JSONConverter.class.getName() + "<" + targetType.getQualifiedSourceName() + ">");

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

            sw.println("@Override public JSONValue convertObjectToJSON(" + targetTypeQualifiedName +" instance){ ");
            sw.println("if (instance == null) { return JSONNull.getInstance(); }"); //should we handle null value?
            sw.println("JSONObject jsonObject = new JSONObject();");
            JFieldStream.of(targetType.isClassOrInterface()).forEach((f, p)-> {
                String converterSourceName = JSONConverterGenerator.generate(logger, context, f.getType());
                sw.println("jsonObject.put("+"\""+f.getName()+"\"" + "," + "new " + converterSourceName +"().convertObjectToJSON("+ "instance."+p.getGetMethod()+"()));");
            });
            sw.println("return jsonObject;}");

            // Generate Json to object method
            sw.println("@Override public " + targetTypeQualifiedName + " convertJSONToObject(JSONValue value){ ");
            sw.println("JSONObject jsonObject = value.isObject();");
            sw.println(targetTypeQualifiedName + " instance = createInstance();");

            JFieldStream.of(targetType.isClassOrInterface()).forEach((f, p)-> {
                String converterSourceName = JSONConverterGenerator.generate(logger, context, f.getType());
                sw.println("instance." + p.getSetMethod() + "(new " + converterSourceName +"().convertJSONToObject("+ "jsonObject.get("+"\""+f.getName()+"\""+")));");
            });

            sw.println("return instance;}");
            sw.commit(logger);
        }

        return qualifiedSourceName;
    }

    private final static ClassSourceFileComposerFactory createSourceComposer(String sourceName) {
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

    private static JClassType getTypeArg(JType target){
        return target.isParameterized().getTypeArgs()[0];
    }

    private static JClassType getSecondTypeArg(JType target){
        return target.isParameterized().getTypeArgs()[1];
    }

}
