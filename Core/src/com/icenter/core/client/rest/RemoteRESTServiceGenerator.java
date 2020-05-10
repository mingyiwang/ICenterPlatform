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
import com.icenter.core.client.primitive.Joiner;
import com.icenter.core.client.reflect.JMethodInfo;
import com.icenter.core.client.reflect.JTypeInfo;
import com.icenter.core.client.rest.convert.*;
import com.icenter.core.client.rest.convert.JSONConverterGenerator;
import com.icenter.core.client.rest.convert.custom.*;
import java.io.PrintWriter;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RemoteRESTServiceGenerator extends Generator {

    public RemoteRESTServiceGenerator() { }

    @Deprecated
    public String generate(TreeLogger logger,
                           GeneratorContext context,
                           String targetTypeName) throws UnableToCompleteException {

        TypeOracle types  = context.getTypeOracle();
        JClassType target = types.findType(targetTypeName);

        if(target == null) {
           throw new UnableToCompleteException();
        }

        if(!target.isAssignableTo(types.findType(RemoteRESTService.class.getCanonicalName()))){
            throw new UnableToCompleteException();
        }

        String remoteServicePackage = target.getPackage().getName();
        String remoteServiceName = target.getName()+"Async"; // Custom rebind service name
        String remoteServiceQualifiedSourceName = remoteServicePackage + "." + remoteServiceName;

        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(target.getPackage().getName(), remoteServiceName);
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
        composerFactory.setSuperclass(RemoteRESTServiceImpl.class.getCanonicalName());
        composerFactory.addImplementedInterface(targetTypeName);

        PrintWriter pw  = context.tryCreate(logger, remoteServicePackage, remoteServiceName);
        if (pw == null){
            return remoteServiceQualifiedSourceName;
        }
        else {
            SourceWriter sw = composerFactory.createSourceWriter(context, pw);
            Stream.of(target.getMethods())
                  .forEach(mt -> {
                       if (RemoteRESTServiceHelper.isValidMethod(mt, types)) {
                           JMethodInfo methodInfo = JMethodInfo.of(mt, types);
                           String params = Joiner.on(',').join(methodInfo.getParameters(), p -> p.getType().getParameterizedQualifiedSourceName() + " " + p.getName());
                           System.out.println("Generating method :" + "@Override public void "+ mt.getName() + "(" + params + "){ " );
                           sw.println("@Override public void "+ mt.getName() + "(" + params + "){ ");

                           // Generate parameters
                           sw.println("JSONObject params = new JSONObject();");
                           IntStream.range(0, methodInfo.getParameters().size() -1).forEach(i -> {
                               JParameter param = methodInfo.getParameters().get(i);
                               JType paramType  = param.getType();
                               if (JTypeInfo.isPrimitive(paramType)){
                                   String qualifiedParamName = paramType.isPrimitive() != null
                                           ? paramType.isPrimitive().getQualifiedBoxedSourceName()
                                           : paramType.getQualifiedSourceName();
                                   sw.print("params.put("+"\""+param.getName()+"\""+","+"((JSONConverter<" + qualifiedParamName + ">)"+"SimpleConverters.get("+"\""+ qualifiedParamName +"\""+")).convertObjectToJSON("+param.getName()+"));");
                               }
                               else {
                                   System.out.println("params.put("+"\""+param.getName()+"\""+","
                                           + "new " + JSONConverterGenerator.generate(logger, context, paramType.isClassOrInterface()) + "().convertObjectToJSON("+param.getName()+"));");

                                   sw.print("params.put("+"\""+param.getName()+"\""+","
                                          + "new " + JSONConverterGenerator.generate(logger, context, paramType.isClassOrInterface()) + "().convertObjectToJSON("+param.getName()+"));");
                               }
                           });

                           // Generate Return Type converter
                           String converter = JSONConverterGenerator.generate(logger, context, methodInfo.getReturnType());
                           sw.print("JSONConverter callbackConverter = new " + converter + "();");
                           sw.println("send(params, callbackConverter," + methodInfo.getReturnParameter().getName() + ");");
                           sw.println("}");
                       }
                  });

            sw.commit(logger);
            return remoteServiceQualifiedSourceName;
        }
    }

}
