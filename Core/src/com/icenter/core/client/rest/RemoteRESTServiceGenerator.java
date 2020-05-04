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
import com.icenter.core.client.rest.convert.custom.IntegerJSONConverter;
import com.icenter.core.client.rest.convert.JSONConverterGenerator;
import com.icenter.core.client.rest.convert.custom.StringJSONConverter;
import java.io.PrintWriter;
import java.util.stream.IntStream;

import static java.util.stream.Stream.of;

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

        String rebindServicePackagePath = target.getPackage().getName();
        String rebindServiceName = target.getName()+"Async"; // Custom rebind service name
        String rebindServiceQualifiedSourceName = rebindServicePackagePath + "." + rebindServiceName;

        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(target.getPackage().getName(), rebindServiceName);
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
        composerFactory.addImport(IntegerJSONConverter.class.getCanonicalName());
        composerFactory.addImport(StringJSONConverter.class.getCanonicalName());
        composerFactory.addImport(Converters.class.getCanonicalName());
        composerFactory.setSuperclass(RemoteRESTServiceImpl.class.getCanonicalName());
        composerFactory.addImplementedInterface(targetTypeName);

        PrintWriter pw  = context.tryCreate(logger, rebindServicePackagePath, rebindServiceName);
        if (pw == null){
            return rebindServiceQualifiedSourceName;
        }
        else {
            SourceWriter sw = composerFactory.createSourceWriter(context, pw);
            of(target.getMethods())
               .forEach(mt -> {
                   if (RemoteRESTServiceHelper.isValidMethod(mt, types)) {
                       JMethodInfo methodInfo = JMethodInfo.of(mt, types);
                       String params = Joiner.on(',').join(methodInfo.getParameters(), p -> p.getType().getParameterizedQualifiedSourceName() + " " + p.getName());
                       sw.println("@Override public void "+ mt.getName() + "(" + params + "){ ");

                       // Generate parameters
                       sw.println("JSONObject params = new JSONObject();");
                       IntStream.range(0, methodInfo.getParameters().size() -1).forEach(i -> {
                           JParameter param = methodInfo.getParameters().get(i);
                           JType paramType  = param.getType();
                           if (JTypeInfo.isPrimitive(paramType)){
                               String converterKey = paramType.isPrimitive() != null
                                       ? paramType.isPrimitive().getQualifiedBoxedSourceName()
                                       : paramType.getQualifiedSourceName();

                               System.out.println("(JSONConverter<" + converterKey + ">)");
                               System.out.println("params.put("+"\""+param.getName()+"\""+","+"((JSONConverter<" +converterKey + ">)"+"Converters.get("+"\""+ converterKey +"\""+")).convertObjectToJSON("+param.getName()+"));");
                               sw.print("params.put("+"\""+param.getName()+"\""+","+"((JSONConverter<" + converterKey + ">)"+"Converters.get("+"\""+ converterKey +"\""+")).convertObjectToJSON("+param.getName()+"));");
                           }
                           else {
                               sw.print("params.put("+"\""+param.getName()+"\""+","
                                       + "new " + JSONConverterGenerator.generate(logger, context, paramType.isClassOrInterface()) + "().convertObjectToJSON("+param.getName()+"));");
                           }
                       });

                       // Generate Return Type converter
                       String converter = JSONConverterGenerator.generate(logger, context, methodInfo.getReturnType());
                       sw.print("JSONConverter converter = new " + converter + "();");
                       sw.println("send(params, converter," + methodInfo.getReturnParameter().getName() + ");");
                       sw.println("}");
                   }
            });

            sw.commit(logger);
            return rebindServiceQualifiedSourceName;
        }
    }

}
