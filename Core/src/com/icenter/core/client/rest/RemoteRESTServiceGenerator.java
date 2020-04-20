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
import com.icenter.core.client.json.JsonConverter;
import com.icenter.core.client.json.Jsons;
import com.icenter.core.client.primitive.Joiner;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.*;

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
        composerFactory.setSuperclass(RemoteRESTServiceImpl.class.getCanonicalName());
        composerFactory.addImplementedInterface(targetTypeName);

        PrintWriter pw  = generatorContext.tryCreate(treeLogger, target.getPackage().getName(), proxyClassName);
        if (pw == null){
            return target.getPackage().getName() + "." + proxyClassName;
        }
        else {
            SourceWriter sw = composerFactory.createSourceWriter(generatorContext, pw);
            JMethod[] methods = target.getMethods();
            for (JMethod mt : methods) {
                 if (mt.getReturnType() == JPrimitiveType.VOID){
                     JParameter[] parameters = mt.getParameters();

                     Joiner joiner = Joiner.on(',');
                     String params = joiner.join(parameters, p -> p.getType().getParameterizedQualifiedSourceName() + " " + p.getName());

                     sw.println("public void "+ mt.getName() + "(" + params + "){ ");
                     sw.indent();

                     sw.println("JSONObject object = new JSONObject();");
                     Stream.of(parameters).forEach(p -> {
                         String jsonConverterKey = Jsons.createIfNotExist(treeLogger, generatorContext, p.getType());
                         String.format("object.put(%1,Json.get(%2).convertToJSON(%3));", p.getName(), jsonConverterKey, p.getName());
                     });

                     JParameter p = Arrays.stream(parameters).findFirst().get();

                     JTypeParameter[] ps = p.getType().isGenericType().getTypeParameters();
                     Jsons.createIfNotExist(treeLogger, generatorContext, p.getType());
                     sw.println("sendRequest("+mt.getName()+", object, Jsons.get(typeName), callbackName)");
                     sw.outdent();
                     sw.println("}");
                 }
            }

            System.out.println(sw.toString());
            sw.commit(treeLogger);
            return target.getPackage().getName()+"."+proxyClassName;
        }
    }

    private void generateObjectProxy(){

    }

    private void generateMethod(){
        alert11("");
    }



    public static native void alert11(String text)/*-{
        alert(text);
    }-*/;

}
