package com.icenter.core.client.json.gen;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.icenter.core.client.rest.convert.JSONConverterGenerator;

public class JSONSerializerGenerator extends Generator {

    @Override
    public String generate(TreeLogger treeLogger, GeneratorContext generatorContext, String s) throws UnableToCompleteException {
        JClassType type = generatorContext.getTypeOracle().findType(s);
        return JSONConverterGenerator.generate(treeLogger, generatorContext, type);
    }

}
