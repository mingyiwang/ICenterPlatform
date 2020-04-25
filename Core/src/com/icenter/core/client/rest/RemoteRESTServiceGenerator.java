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
import com.icenter.core.client.json.Converters;
import com.icenter.core.client.json.JsonConverter;
import com.icenter.core.client.primitive.Joiner;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
                 if (mt.getReturnType() == JPrimitiveType.VOID){
                     JParameter[] parameters = mt.getParameters();

                     Joiner joiner = Joiner.on(',');
                     String params = joiner.join(parameters, p -> p.getType().getParameterizedQualifiedSourceName() + " " + p.getName());
                     of(parameters).forEach(p -> {
                         parameterList.add(p);
                     });

                     sw.println("public void "+ mt.getName() + "(" + params + "){ ");
                     sw.println("JSONObject params = new JSONObject();");
                     of(parameters).forEach(p -> {
                         sw.println("JSONProperty property = getJSONProperty();");
                         sw.println("params.put("+p.getName()+"," + "Converters.get().handle(property," + p.getName() + ");");
                     });

                     sw.println("}");
                 }
            }

            System.out.println("Generating Converters ----------------------------------------------- ");
            sw.println("@Override public void initConverters() {");
            for (JParameter p : parameterList) {
                 String converterName = createJSONConverterIfNotExist(treeLogger, generatorContext,target.getPackage().getName(),p.getType());
                 String propertyName  = createJSONPropertyIfNotExist();

                 sw.println("JSONProperty property   = new " + propertyName +"();");
                 sw.println("JSONConverter converter = new " + converterName+"();");
                 sw.println("converter.setProperty(property)");
                 sw.println("Converters.add("+"\""+p.getType().getParameterizedQualifiedSourceName()+"\"" + ",converter");

                 // init JsonConverter
                 System.out.println("Converters.add("+"\""+p.getType().getParameterizedQualifiedSourceName()+"\"" + ",new " + converterName + "());");
            }

            sw.println("}");
            sw.commit(treeLogger);
            System.out.println(sw.toString());

            return target.getPackage().getName()+"."+proxyClassName;
        }
    }

    private final String createJSONPropertyIfNotExist(){
        return null;
    }

    private final String createJSONConverterIfNotExist(TreeLogger logger, GeneratorContext context, String packageName, JType targetType) {

        JClassType target = context.getTypeOracle().findType(targetType.getParameterizedQualifiedSourceName());
        String proxyClassName = target.getName() + JsonConverter.Name;

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
        composerFactory.setSuperclass(JsonConverter.class.getCanonicalName() + "<" + target.getName() + ">");

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
