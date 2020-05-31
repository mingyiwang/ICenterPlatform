package com.icenter.core.client.rest.annotation;

import com.icenter.core.client.http.HttpMethod;

public @interface RemoteRestAction {

    HttpMethod Method() default HttpMethod.POST;
    String action()     default "";
    String id()         default ""; // under development
}
