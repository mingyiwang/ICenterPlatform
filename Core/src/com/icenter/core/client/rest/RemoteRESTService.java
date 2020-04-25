package com.icenter.core.client.rest;

import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Maker Interface for Json Rest Service.
 *
 * */
public interface RemoteRESTService {

    public String getEndPoint();

    public void initConverters();
}
