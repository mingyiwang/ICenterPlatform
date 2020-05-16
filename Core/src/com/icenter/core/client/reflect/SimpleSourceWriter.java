package com.icenter.core.client.reflect;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.user.rebind.SourceWriter;

public class SimpleSourceWriter {

    private SourceWriter sw;
    private StringBuilder builder = new StringBuilder();

    private SimpleSourceWriter(SourceWriter writer){
        this.sw = writer;
    }

    public static SimpleSourceWriter of(SourceWriter writer){
        return new SimpleSourceWriter(writer);
    }

    public SimpleSourceWriter withStatement(String statement){
        builder.append(statement);
        return this;
    }

    public final void commit(TreeLogger logger){
        sw.print(builder.toString());
        sw.commit(logger);
    }

}
