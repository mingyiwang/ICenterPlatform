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
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.icenter.core.client.primitive.ArrayStream;
import com.icenter.core.client.primitive.CollectionStream;
import com.icenter.core.client.primitive.Joiner;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.reflect.SimpleSourceWriter;
import com.icenter.core.client.rest.annotation.RemoteRestAction;
import com.icenter.core.client.rest.convert.*;
import com.icenter.core.client.rest.convert.JSONConverterGenerator;
import com.icenter.core.client.rest.convert.base.*;
import java.io.PrintWriter;
import java.util.*;

/**
 * JSONConverter Generator class, should not use it directly
 * **/
public final class RemoteRESTServiceGenerator extends Generator {

    private final static String ServiceSuffix = "Async";

    public RemoteRESTServiceGenerator() { }

    @Deprecated
    public String generate(TreeLogger logger,
                           GeneratorContext context,
                           String targetTypeName) throws UnableToCompleteException {

        TypeOracle types = context.getTypeOracle();

        // we always knows that service type must be an interface.
        JClassType service = types.findType(targetTypeName);

        RemoteRESTServiceValidator.validateService(logger, context.getTypeOracle(), service);

        String remoteServicePackage = service.getPackage().getName();
        String remoteServiceName = service.getName() + ServiceSuffix; // Custom rebind service class name
        String remoteServiceQualifiedSourceName = remoteServicePackage + "." + remoteServiceName;
        ClassSourceFileComposerFactory composerFactory = createServiceSourceFileComposerFactory(targetTypeName, service, remoteServiceName);

        PrintWriter pw  = context.tryCreate(logger, remoteServicePackage, remoteServiceName);
        if (pw == null){
            return remoteServiceQualifiedSourceName;
        }
        else {
            SimpleSourceWriter sw = new SimpleSourceWriter(composerFactory.createSourceWriter(context, pw));
            for (JMethod method : service.getMethods()) {

                /*
                * Recursive Validator used to validate methods and variables.
                * **/
                RemoteRESTServiceValidator.validateMethod(logger, types, method);

                List<JParameter> mParams = getParamsAsList(method);
                String mParamsText = Joiner.on(',').join(mParams, p -> p.getType().getParameterizedQualifiedSourceName() + " " + p.getName());
                sw.println(String.format("@Override\npublic void %1$s(%2$s){ ",method.getName(), mParamsText));
                sw.indent();
                sw.println("JSONObject params = new JSONObject();");

                final int size = mParams.size();
                CollectionStream.of(mParams).forEach((i, parameter)-> {
                    if(i != size -1){
                        sw.println(String.format("params.put(\"%1$s\",new %2$s().convertObjectToJSON(%1$s));",
                            parameter.getName(),
                            JSONConverterGenerator.generate(logger, context, parameter.getType())
                        ));
                    }
                });

                sw.println(String.format("JSONConverter converter = new %1$s();",
                   JSONConverterGenerator.generate(logger, context, getReturnObjectType(method))
                ));

                RemoteRestAction endpoint = method.getAnnotation(RemoteRestAction.class);

                sw.println(String.format("send(\"%1$s\",\"%2$s\", params, converter,%3$s);",
                   endpoint == null ? method.getName() : Strings.of(endpoint.action(), method.getName()),
                   endpoint == null ? Strings.Empty    : Strings.of(endpoint.Method().getMethod(), Strings.Empty),
                   getAsyncCallbackParamName(method)
                ));

                sw.outdent();
                sw.println("}");
            }
            sw.commit(logger);

            System.out.println(sw.toString());
            return remoteServiceQualifiedSourceName;
        }
    }

    private final ClassSourceFileComposerFactory createServiceSourceFileComposerFactory(String targetTypeName, JClassType target, String remoteServiceName) {
        ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(target.getPackage().getName(), remoteServiceName);
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
        composerFactory.addImport(JProperty.class.getCanonicalName());
        composerFactory.addImport(AsyncCallback.class.getCanonicalName());
        composerFactory.addImport(RemoteRESTService.class.getCanonicalName());
        composerFactory.addImport(RemoteRESTServiceImpl.class.getCanonicalName());
        composerFactory.addImport(JSONConverter.class.getCanonicalName());
        composerFactory.addImport(JSONBooleanConverter.class.getCanonicalName());
        composerFactory.addImport(JSONByteConverter.class.getCanonicalName());
        composerFactory.addImport(JSONCharacterConverter.class.getCanonicalName());
        composerFactory.addImport(JSONDateConverter.class.getCanonicalName());
        composerFactory.addImport(JSONDoubleConverter.class.getCanonicalName());
        composerFactory.addImport(JSONFloatConverter.class.getCanonicalName());
        composerFactory.addImport(JSONIntegerConverter.class.getCanonicalName());
        composerFactory.addImport(JSONLongConverter.class.getCanonicalName());
        composerFactory.addImport(JSONShortConverter.class.getCanonicalName());
        composerFactory.addImport(JSONStringConverter.class.getCanonicalName());
        composerFactory.addImport(List.class.getCanonicalName());
        composerFactory.addImport(ArrayList.class.getCanonicalName());
        composerFactory.addImport(Set.class.getCanonicalName());
        composerFactory.addImport(HashSet.class.getCanonicalName());
        composerFactory.addImport(Map.class.getCanonicalName());
        composerFactory.addImport(HashMap.class.getCanonicalName());
        composerFactory.setSuperclass(RemoteRESTServiceImpl.class.getCanonicalName());
        composerFactory.addImplementedInterface(targetTypeName);
        return composerFactory;
    }

    private final static List<JParameter> getParamsAsList(JMethod method){
        return Arrays.asList(method.getParameters());
    }

    private final static JParameter getAsyncCallbackParam(JMethod method){
        return (JParameter) ArrayStream.of(method.getParameters()).last();
    }

    private final static String getAsyncCallbackParamName(JMethod method){
        return getAsyncCallbackParam(method).getName();
    }

    private final static JClassType getReturnObjectType(JMethod method) {
        return getAsyncCallbackParam(method).getType().isParameterized().getTypeArgs()[0];
    }

}
