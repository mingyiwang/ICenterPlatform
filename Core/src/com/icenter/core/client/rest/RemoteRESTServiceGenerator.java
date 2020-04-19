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

import java.io.PrintWriter;

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
        if(pw == null){
            return target.getPackage().getName() + "." + proxyClassName;
        }
        else {
            SourceWriter sw = composerFactory.createSourceWriter(generatorContext, pw);

            JMethod[] methods = target.getMethods();
            for (JMethod mt :methods) {
                 if (mt.getReturnType() == JPrimitiveType.VOID){
                     JParameter[] parameters = mt.getParameters();
                     Joiner joiner = Joiner.on(',');
                     String params = joiner.join(parameters, p -> p.getType().getParameterizedQualifiedSourceName() + " " + p.getName());

                     sw.println("public void "+ mt.getName() + "(" + params + "){ ");
                     sw.indent();
                     sw.print("alert11(\"" + params + "\");");
                     sw.outdent();
                     sw.println("}");
                 }
            }

            sw.println("public static native void alert11(String text)/*-{");
            sw.indent();
            sw.outdent();
            sw.println("alert(text);");
            sw.println("}-*/;");

            System.out.println(sw.toString());

            sw.commit(treeLogger);


            return target.getPackage().getName()+"."+proxyClassName;
        }

    }

    private void generateMethod(){
        alert11("");
    }


    public static native void alert11(String text)/*-{
        alert(text);
    }-*/;
}
