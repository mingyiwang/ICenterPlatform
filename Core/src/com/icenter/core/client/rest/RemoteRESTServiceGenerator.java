package com.icenter.core.client.rest;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.*;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.icenter.core.client.rest.convert.Converters;
import com.icenter.core.client.rest.convert.JSONConverter;
import com.icenter.core.client.rest.convert.JSONProperty;
import com.icenter.core.client.primitive.Joiner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Stream.of;

public class RemoteRESTServiceGenerator extends Generator {

    public RemoteRESTServiceGenerator() {

    }

    @Deprecated
    public String generate(TreeLogger treeLogger, GeneratorContext generatorContext, String targetTypeName) throws UnableToCompleteException {
        TypeOracle types  = generatorContext.getTypeOracle();
        JClassType target = types.findType(targetTypeName);

        if(!target.isAssignableTo(types.findType(RemoteRESTService.class.getCanonicalName()))){
            throw new UnableToCompleteException();
        }

        String proxyClassName = target.getName()+"AsyncProxy";
        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(target.getPackage().getName(), proxyClassName);
        composerFactory.addImport(ServiceDefTarget.class.getCanonicalName());
        composerFactory.addImport(IllegalArgumentException.class.getCanonicalName());
        composerFactory.addImport(RequestBuilder.class.getCanonicalName());
        composerFactory.addImport(RequestException.class.getCanonicalName());
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
        composerFactory.addImport(Converters.class.getCanonicalName());
        composerFactory.setSuperclass(RemoteRESTServiceImpl.class.getCanonicalName());
        composerFactory.addImplementedInterface(targetTypeName);

        PrintWriter pw  = generatorContext.tryCreate(treeLogger, target.getPackage().getName(), proxyClassName);
        if (pw == null){
            return target.getPackage().getName() + "." + proxyClassName;
        }
        else {
            SourceWriter sw = composerFactory.createSourceWriter(generatorContext, pw);
            JMethod[] methods = target.getMethods();
            List<JParameter> parameterList = new ArrayList<>();

            for (JMethod mt : methods) {
                if (mt.getReturnType() != JPrimitiveType.VOID){
                     // all remote json service should not have return type.
                     continue;
                }

                JParameter[] parameters = mt.getParameters();
                String params = Joiner.on(',').join(parameters, p -> p.getType().getParameterizedQualifiedSourceName() + " " + p.getName());
                sw.println("@Override public void "+ mt.getName() + "(" + params + "){ ");
                sw.println("JSONObject params = new JSONObject();");
                of (parameters).forEach(p -> {
                    parameterList.add(p);
                    sw.println("params.put("+p.getName()+"," + "Converters.get().convertObjectToJSON(" + p.getName() + ");");
                });

                sw.println("}");
            }

            System.out.println("Generating Converters ----------------------------------------------- ");
            sw.println("@Override public void initConverters() {");
            for (JParameter p : parameterList) {
                 String converterName = createJSONConverterIfNotExist(treeLogger, generatorContext,target.getPackage().getName(), p.getType());
                 String propertyClassName = createJSONPropertyIfNotExist(treeLogger, generatorContext,target.getPackage().getName(), p.getType());
                 sw.println("JSONConverter converter = new " + converterName+"();");
                 sw.println("JSONProperty  property  = new " + propertyClassName +"();");
                 sw.println("converter.setProperty(property)");
                 sw.println("Converters.add("+"\""+p.getType().getParameterizedQualifiedSourceName()+"\"" + ",converter");
                 // System.out.println("Converters.add("+"\""+p.getType().getParameterizedQualifiedSourceName()+"\"" + ",new " + converterName + "());");
            }

            sw.println("}");
            sw.commit(treeLogger);
            System.out.println(sw.toString());

            return target.getPackage().getName()+"."+proxyClassName;
        }
    }


    private final String createJSONConverterIfNotExist(TreeLogger logger, GeneratorContext context, String packageName, JType targetType) {

        JClassType target = context.getTypeOracle().findType(targetType.getParameterizedQualifiedSourceName());
        String proxyClassName = target.getName() + JSONConverter.Name;

        if (Converters.get(targetType.getParameterizedQualifiedSourceName()) != null) {
            return packageName + "." + proxyClassName;
        }

        ClassSourceFileComposerFactory composerFactory = getClassSourceFileComposerFactory(packageName, target, proxyClassName);
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

            sw.println("@Override public proxyClassName convertObjectToJSON(JSONValue value){ ");
            sw.println("T object = createInstance();");
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
