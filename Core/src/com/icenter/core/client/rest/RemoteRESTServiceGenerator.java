package com.icenter.core.client.rest;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.icenter.core.client.primitive.Joiner;
import com.icenter.core.client.reflect.JMethodInfo;
import com.icenter.core.client.rest.convert.*;
import com.icenter.core.client.rest.convert.custom.IntegerJSONConverter;
import com.icenter.core.client.rest.convert.JSONConverterGenerator;
import com.icenter.core.client.rest.convert.custom.StringJSONConverter;

import java.io.PrintWriter;

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

        String packagePath = target.getPackage().getName();
        String proxyClassName = target.getName()+"Async";

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
        composerFactory.addImport(JSONConverter.class.getCanonicalName());
        composerFactory.addImport(IntegerJSONConverter.class.getCanonicalName());
        composerFactory.addImport(StringJSONConverter.class.getCanonicalName());
        composerFactory.addImport(Converters.class.getCanonicalName());
        composerFactory.setSuperclass(RemoteRESTServiceImpl.class.getCanonicalName());
        composerFactory.addImplementedInterface(targetTypeName);

        PrintWriter pw  = context.tryCreate(logger, target.getPackage().getName(), proxyClassName);
        if (pw == null){
            return packagePath + "." + proxyClassName;
        }
        else {
            SourceWriter sw = composerFactory.createSourceWriter(context, pw);
            JMethod[] methods = target.getMethods();

            of(methods)
               .forEach(mt -> {
                   if(RemoteRESTServiceHelper.isValid(mt, types)) {
                       JMethodInfo methodInfo = JMethodInfo.of(mt, types);
                       String params = Joiner.on(',').join(methodInfo.getParameters(), p -> p.getType().getParameterizedQualifiedSourceName() + " " + p.getName());
                       sw.println("@Override public void "+ mt.getName() + "(" + params + "){ ");


//                       sw.println("JSONConverter converter = Converters.get("+ methodInfo.getReturnType().getParameterizedQualifiedSourceName()+");");
//                       methodInfo.getParameters().forEach(p -> {
//                           String converterKey = p.getType().getParameterizedQualifiedSourceName();
//                           sw.println("params.put("+p.getName()+"," + "Converters.get("+converterKey+").convertObjectToJSON(" + p.getName() + ");");
//                       });
//
//

                       String converter = JSONConverterGenerator.generate(logger, context, methodInfo.getReturnType());
                       System.out.println("Converter -->  " + converter);
                       sw.println("JSONObject params = new JSONObject();");
                       sw.print("JSONConverter converter = new "+converter+"();");
                       sw.println("send(params, converter," + methodInfo.getReturnParam().getName() + ");");
                       sw.println("}");
                   }
            });

            sw.commit(logger);
            return target.getPackage().getName() + "."+ proxyClassName;
        }
    }







}
