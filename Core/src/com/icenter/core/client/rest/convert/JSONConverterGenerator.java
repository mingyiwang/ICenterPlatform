package com.icenter.core.client.rest.convert;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.icenter.core.client.lambda.BiAction;
import com.icenter.core.client.reflect.Reflects;
import com.icenter.core.client.rest.RemoteRESTService;
import com.icenter.core.client.rest.RemoteRESTServiceImpl;
import com.icenter.core.client.rest.convert.base.*;
import java.io.PrintWriter;
import java.util.*;

/***
 * JSONConvertible Object Converter, used to generates object converters,
 * shouldn't use it directly. It does't do any validation against Variables.
 */
public final class JSONConverterGenerator  {

    // classes generated should be under below package path.
    private final static String packagePath = "com.icenter.core.client.rest.convert";

    /**
     * Returns newly generated JSONConverter class based the targetType.
     * */
    public final static String generate(TreeLogger logger, GeneratorContext context, JType targetType) {
        if(Reflects.isPrimitive(targetType)){
           return generatePrimitive(logger, context, targetType);
        } else if(Reflects.isArray(targetType)){
           return generateArray(logger, context,targetType.isArray());
        } else if(Reflects.isList(targetType,context.getTypeOracle())){
           return generateList(logger, context, targetType.isClassOrInterface());
        } else if(Reflects.isMap(targetType, context.getTypeOracle())){
           return generateMap(logger,  context, targetType.isClassOrInterface());
        } else {
           return generateClass(logger, context, targetType.isClassOrInterface());
        }
    }

    private final static String generatePrimitive(TreeLogger logger, GeneratorContext context, JType primitiveType)  {
        String typeQualifiedName = primitiveType.isPrimitive() != null
                                 ? primitiveType.isPrimitive().getQualifiedBoxedSourceName()
                                 : primitiveType.getQualifiedSourceName();
        return PrimitiveConverters.of(typeQualifiedName);
    }

    private final static String generateArray(TreeLogger logger, GeneratorContext context, JArrayType targetType)  {
        JType  componentType = targetType.getComponentType();
        String componentTypeQualifiedName = componentType.isPrimitive() != null
               ? componentType.isPrimitive().getQualifiedBoxedSourceName()
               : componentType.getQualifiedSourceName();

        // Primitive arrays: int[], double[] etc.
        if (componentType.isPrimitive() != null ) {
            return PrimitiveConverters.of(targetType.getParameterizedQualifiedSourceName());
        }

        String sourceName = getConverterSourceName(context.getTypeOracle(), targetType);
        String qualifiedConverterSourceName = packagePath + "." + sourceName;

        ClassSourceFileComposerFactory composer = createSourceComposer(sourceName);
        composer.addImport(AbstractJSONArrayConverter.class.getCanonicalName());
        composer.addImport(componentTypeQualifiedName);
        composer.setSuperclass(AbstractJSONArrayConverter.class.getCanonicalName()+ "<" + componentTypeQualifiedName + ">");

        PrintWriter pw = context.tryCreate(logger, packagePath, sourceName);
        if(pw == null) {
           return qualifiedConverterSourceName;
        }
        else {
            SourceWriter sw = composer.createSourceWriter(context, pw);
            sw.println(String.format("@Override public JSONConverter<%1$s> createConverter(){ ",
               componentTypeQualifiedName
            ));

            sw.indentln(String.format("return new %1$s();",JSONConverterGenerator.generate(logger, context, componentType)));
            sw.println("}");
            sw.commit(logger);
        }
        return qualifiedConverterSourceName;
    }

    private final static String generateMap(TreeLogger logger, GeneratorContext context, JClassType targetType){
        JClassType keyType   = targetType.isParameterized().getTypeArgs()[0];
        JClassType valueType = targetType.isParameterized().getTypeArgs()[1];

        String sourceName = getConverterSourceName(context.getTypeOracle(), targetType);
        String qualifiedSourceName = packagePath + "." + sourceName;

        ClassSourceFileComposerFactory composer = createSourceComposer(sourceName);
        composer.addImport(AbstractJSONMapConverter.class.getCanonicalName());
        composer.addImport(valueType.getQualifiedSourceName());
        composer.addImport(keyType.getQualifiedSourceName());
        composer.setSuperclass(AbstractJSONMapConverter.class.getCanonicalName() + "<" + keyType.getQualifiedSourceName() + "," + valueType.getParameterizedQualifiedSourceName()+ ">");

        PrintWriter pw = context.tryCreate(logger, packagePath, sourceName);
        if(pw == null) {
            return qualifiedSourceName;
        }
        else {
            SourceWriter sw = composer.createSourceWriter(context, pw);
            sw.println(String.format("@Override public JSONConverter<%1$s> createKeyConverter(){ ", keyType.getQualifiedSourceName()));
            sw.println(String.format("return new %1$s();", JSONConverterGenerator.generate(logger, context, keyType)));
            sw.println("}");

            sw.println(String.format("@Override public JSONConverter<%1$s> createValueConverter(){ ",valueType.getParameterizedQualifiedSourceName()));
            sw.println(String.format("return new %1$s();",JSONConverterGenerator.generate(logger, context, valueType)));
            sw.println("}");
            sw.commit(logger);
        }
        return qualifiedSourceName;
    }

    private final static String generateList(TreeLogger logger, GeneratorContext context, JClassType targetType){
        JClassType componentType = targetType.isParameterized().getTypeArgs()[0];
        String componentQualifiedSourceName = componentType.getParameterizedQualifiedSourceName();

        String sourceName = getConverterSourceName(context.getTypeOracle(), targetType);
        String qualifiedSourceName = packagePath + "." + sourceName;

        ClassSourceFileComposerFactory composer = createSourceComposer(sourceName);
        composer.addImport(AbstractJSONListConverter.class.getCanonicalName());
        composer.addImport(componentType.getQualifiedSourceName());
        composer.setSuperclass(AbstractJSONListConverter.class.getCanonicalName() + "<" + componentQualifiedSourceName + ">");

        PrintWriter pw = context.tryCreate(logger, packagePath, sourceName);
        if(pw == null) {
            return qualifiedSourceName;
        }
        else {
            SourceWriter sw = composer.createSourceWriter(context, pw);
            sw.println(String.format("@Override public JSONConverter<%1$s> createConverter(){ ", componentType.getParameterizedQualifiedSourceName()));
            System.out.println(String.format("@Override public JSONConverter<%1$s> createConverter(){ ", componentType.getParameterizedQualifiedSourceName()));
            sw.println(String.format("return new %1$s();", JSONConverterGenerator.generate(logger, context, componentType)));
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
        composer.setSuperclass("JSONConverter<" + targetType.getQualifiedSourceName() + ">");

        PrintWriter pw = context.tryCreate(logger, packagePath, sourceName);
        if(pw == null) {
           return qualifiedSourceName;
        }
        else {
            String targetTypeQualifiedName = targetType.getQualifiedSourceName();
            SourceWriter sw = composer.createSourceWriter(context, pw);

            // Generate new instance method
            sw.println(String.format("@Override public %1$s createInstance(){ ", targetType.getName()));
            sw.println(String.format("return new %1$s();", targetTypeQualifiedName));
            sw.println("}");

            // Convert object to json method
            sw.println(String.format("@Override public JSONValue convertObjectToJSON(%1$s instance){ ", targetTypeQualifiedName));
            sw.println("if (instance == null) { return JSONNull.getInstance(); }"); //should we handle null value?
            sw.println("JSONObject jsonObject = new JSONObject();");
            forEach(targetType.isClassOrInterface(),(f, p) -> {
                sw.indentln(String.format("jsonObject.put(\"%1$s\", new %2$s().convertObjectToJSON(instance.%3$s()));",
                   f.getName(),
                   JSONConverterGenerator.generate(logger, context, f.getType()),
                   p.getGetMethod()
                ));
            });
            sw.println("return jsonObject;}");

            // Convert Json to object method
            sw.println(String.format("@Override public %1$s convertJSONToObject(JSONValue value){ ", targetTypeQualifiedName));
            sw.println("JSONObject jsonObject = value.isObject();");
            sw.println(String.format("%1$s instance = createInstance();", targetTypeQualifiedName));

            forEach(targetType.isClassOrInterface(),(f, p)-> {
                sw.println(String.format("instance.%1$s(new %2$s().convertJSONToObject(jsonObject.get(\"%3$s\")));",
                   p.getSetMethod(),
                   JSONConverterGenerator.generate(logger, context, f.getType()),
                   f.getName()
                ));

                System.out.println(String.format("instance.%1$s(new %2$s().convertJSONToObject(jsonObject.get(\"%3$s\")));",
                        p.getSetMethod(),
                        JSONConverterGenerator.generate(logger, context, f.getType()),
                        f.getName()
                ));
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
        composerFactory.addImport(JProperty.class.getCanonicalName());
        composerFactory.addImport(AsyncCallback.class.getCanonicalName());
        composerFactory.addImport(RemoteRESTService.class.getCanonicalName());
        composerFactory.addImport(RemoteRESTServiceImpl.class.getCanonicalName());
        composerFactory.addImport(JSONConverter.class.getCanonicalName());
        composerFactory.addImport(JSONBooleanConverter.class.getCanonicalName());
        composerFactory.addImport(JSONByteConverter.class.getCanonicalName());
        composerFactory.addImport(JSONCharConverter.class.getCanonicalName());
        composerFactory.addImport(JSONDateConverter.class.getCanonicalName());
        composerFactory.addImport(JSONDoubleConverter.class.getCanonicalName());
        composerFactory.addImport(JSONFloatConverter.class.getCanonicalName());
        composerFactory.addImport(JSONIntegerConverter.class.getCanonicalName());
        composerFactory.addImport(JSONLongConverter.class.getCanonicalName());
        composerFactory.addImport(JSONShortConverter.class.getCanonicalName());
        composerFactory.addImport(JSONStringConverter.class.getCanonicalName());
        composerFactory.addImport(List.class.getCanonicalName());
        composerFactory.addImport(ArrayList.class.getCanonicalName());
        composerFactory.addImport(Map.class.getCanonicalName());
        composerFactory.addImport(HashMap.class.getCanonicalName());
        return composerFactory;
    }

    private final static void forEach(JClassType classType, BiAction<JField, JProperty> action){
        Objects.requireNonNull(classType);
        Objects.requireNonNull(action);

        JField[] fields = classType.getFields();
        int len = fields.length;
        if(len == 0) {
           return ; // Type doesn't have fields?
        }

        for(int i =0; i<len; i++){
            action.run(fields[i], JProperty.of(fields[i], classType));
        }
    }

    // Todo: Use java convention to create class name.
    private final static String getConverterSourceName(TypeOracle types, JType type){
        String sourceName;
        if(Reflects.isArray(type)){
           sourceName = "Array_of_"+type.isArray().getComponentType().getSimpleSourceName()+"_JSONConverter";
        }
        else if(Reflects.isList(type, types)){
           sourceName = "List_Of_"+type.isClassOrInterface().isParameterized().getTypeArgs()[0].getSimpleSourceName()+"_JSONConverter";
        }
        else if(Reflects.isMap(type, types)){
           JType[] typeArgs = type.isParameterized().getTypeArgs();
           sourceName = "Map_of_" + typeArgs[0].getSimpleSourceName() + "_To_" + typeArgs[1].getSimpleSourceName()+"_JSONConverter";
        }
        else {
           sourceName = type.isClassOrInterface().getSimpleSourceName()+"_JSONConverter";
        }

        System.out.println("Converter Name ---> " + sourceName);
        return sourceName;
    }

}
