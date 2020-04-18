package com.icenter.core.client.rest;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;

public class RemoteRESTServiceGenerator extends Generator {

    public RemoteRESTServiceGenerator() {

    }

    @Override
    public String generate(TreeLogger treeLogger, GeneratorContext generatorContext, String s) throws UnableToCompleteException {
        try {
            TypeOracle types  = generatorContext.getTypeOracle();
            JClassType target = types.getType(s);
            if(!target.isAssignableTo(types.getType(RemoteRESTService.class.getCanonicalName()))){

            }

            ClassSourceFileComposerFactory factory = new ClassSourceFileComposerFactory(null, null);
            
            factory.setSuperclass(RemoteRESTServiceImpl.class.getCanonicalName());
        }
        catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void GetCategory(String passport, int categoryId, AsyncCallback<String> callback) throws RequestException {
        RequestBuilder builder = new RequestBuilder(null, "");
        builder.setRequestData("");
        builder.setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {

            }

            @Override
            public void onError(Request request, Throwable throwable) {

            }
        });
        builder.send();
    }

}
