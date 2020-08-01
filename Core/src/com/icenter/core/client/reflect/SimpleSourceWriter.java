package com.icenter.core.client.reflect;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.user.rebind.SourceWriter;

public final class SimpleSourceWriter implements SourceWriter {

    private SourceWriter sw;
    private StringBuilder builder;
    private int indent = 0;

    public SimpleSourceWriter(SourceWriter writer){
        this.builder = new StringBuilder();
        this.sw = writer;
    }

    public static SimpleSourceWriter of(SourceWriter writer){
        return new SimpleSourceWriter(writer);
    }

    @Override
    public void beginJavaDocComment() {
        sw.beginJavaDocComment();
    }

    @Override
    public void commit(TreeLogger treeLogger) {
        this.sw.commit(treeLogger);
        this.outdent();
    }

    @Override
    public void endJavaDocComment() {
        sw.endJavaDocComment();
    }

    @Override
    public void indent() {
        this.sw.indent();
        ++this.indent;
    }

    @Override
    public void outdent() {
        this.sw.outdent();
        --this.indent;
    }

    @Override
    public void indentln(String s) {
        this.indent();
        this.println(s);
        this.outdent();
    }

    @Override
    public void indentln(String s, Object... objects) {
        this.indent();
        this.println(s);
        this.outdent();
    }

    @Override
    public void println() {
        this.sw.println();
        this.builder.append("\n");
    }

    @Override
    public void print(String s) {
        this.sw.print(s);

        for(int nl = 0; nl < this.indent; ++nl) {
            this.builder.append("  ");
        }
        this.builder.append(s);
    }

    @Override
    public void print(String s, Object... objects) {
        this.sw.print(s, objects);
        this.builder.append(String.format(s, objects));
    }

    @Override
    public void println(String s) {
        this.sw.println(s);

        for(int nl = 0; nl < this.indent; ++nl) {
            builder.append("  ");
        }
        builder.append(s + "\n");
    }

    @Override
    public void println(String s, Object... objects) {
        sw.println(s, objects);

        for(int nl = 0; nl < this.indent; ++nl) {
            builder.append("  ");
        }
        builder.append(String.format(s, objects)+"\n");
    }

    @Override
    public String toString(){
        return builder.toString();
    }

}
