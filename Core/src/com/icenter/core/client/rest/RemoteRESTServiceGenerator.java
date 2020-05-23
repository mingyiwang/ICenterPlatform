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
import com.google.gwt.user.rebind.SourceWriter;
import com.icenter.core.client.primitive.CollectionStream;
import com.icenter.core.client.primitive.Joiner;
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

        // we always knows that service type is a interface.
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
            SourceWriter sw = composerFactory.createSourceWriter(context, pw);
            for (JMethod method : service.getMethods()) {

                /*
                * Recursive Validator used to validates method and variables.
                * **/
                RemoteRESTServiceValidator.validateMethod(logger, types, method);

                List<JParameter> mParams = RemoteRESTServiceValidator.getParamsAsList(method);
                String mParamsSyntxt = Joiner.on(',').join(mParams, p -> p.getType().getParameterizedQualifiedSourceName() + " " + p.getName());
                sw.println(String.format("@Override public void %1$s(%2$s){ ",method.getName(), mParamsSyntxt));
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
                   JSONConverterGenerator.generate(logger, context, RemoteRESTServiceValidator.getReturnObjectType(method))
                ));

                sw.println(String.format("send(\"%1$s\",params, converter,%2$s);",
                   method.getName(),
                   RemoteRESTServiceValidator.getAsyncCallbackParam(method).getName()
                ));

                sw.println("}");
            }
            sw.commit(logger);
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

}
