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
import com.icenter.core.client.rest.convert.*;
import com.icenter.core.client.rest.convert.JSONConverterGenerator;
import com.icenter.core.client.rest.convert.base.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * JSONConverter Generator class, should not use it directly
 * **/
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
        ClassSourceFileComposerFactory composerFactory = createClassSourceFileComposerFactory(targetTypeName, target, remoteServiceName);

        PrintWriter pw  = context.tryCreate(logger, remoteServicePackage, remoteServiceName);
        if (pw == null){
            return remoteServiceQualifiedSourceName;
        }
        else {
            SourceWriter sw = composerFactory.createSourceWriter(context, pw);
            for (JMethod mt : target.getMethods()) {
                if(!RemoteRESTServiceHelper.isValidMethod(mt, types)){
                    logger.log(TreeLogger.Type.INFO,mt.getName() + " is not a supported method.");
                    continue;
                }

                List<JParameter> methodParameters = RemoteRESTServiceHelper.getMethodParameters(mt);
                String params = Joiner.on(',').join(methodParameters, p -> p.getType().getParameterizedQualifiedSourceName() + " " + p.getName());
                sw.println("@Override public void "+ mt.getName() + "(" + params + "){ ");

                sw.println("JSONObject params = new JSONObject();");
                int size = methodParameters.size();
                for (int i = 0; i < size - 1; i++){
                     JParameter parameter = methodParameters.get(i);
                     JType parameterType = parameter.getType();
                     String converterSourceName = JSONConverterGenerator.generate(logger, context, parameterType);
                     sw.print("params.put("+"\""+ parameter.getName()+"\""+"," + "new " + converterSourceName + "().convertObjectToJSON("+ parameter.getName()+"));");
                }

                String converterSourceName = JSONConverterGenerator.generate(logger, context, RemoteRESTServiceHelper.getAsyncReturnType(mt));
                sw.print("JSONConverter converter = new " + converterSourceName + "();");
                sw.println("send(params, converter," + RemoteRESTServiceHelper.getAsyncReturnParameter(mt).getName() + ");");
                sw.println("}");
            }

            sw.commit(logger);
            return remoteServiceQualifiedSourceName;
        }
    }

//    private String getConverter(SourceWriter sw, TreeLogger logger, GeneratorContext context, JType targetTypeName){
//        String key = targetTypeName.getParameterizedQualifiedSourceName();
//        if(SimpleConverters.get(key) == null){
//           String converter = JSONConverterGenerator.generate(logger, context, targetTypeName);
//           sw.println("SimpleConverters.add(\"" + key +"\", new " + converter + "());");
//        }
//        return "SimpleConverters.get(\""+key+"\")";
//    }

    private ClassSourceFileComposerFactory createClassSourceFileComposerFactory(String targetTypeName, JClassType target, String remoteServiceName) {
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
        composerFactory.addImport(List.class.getCanonicalName());
        composerFactory.addImport(ArrayList.class.getCanonicalName());
        composerFactory.addImport(Set.class.getCanonicalName());
        composerFactory.addImport(Map.class.getCanonicalName());
        composerFactory.setSuperclass(RemoteRESTServiceImpl.class.getCanonicalName());
        composerFactory.addImplementedInterface(targetTypeName);
        return composerFactory;
    }

}
