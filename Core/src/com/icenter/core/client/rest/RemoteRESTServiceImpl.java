package com.icenter.core.client.rest;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.icenter.core.client.http.HttpClient;
import com.icenter.core.client.http.MimeTypes;
import com.icenter.core.client.http.Urls;
import com.icenter.core.client.json.JSON;
import com.icenter.core.client.json.JSONParseResult;
import com.icenter.core.client.primitive.Strings;
import com.icenter.core.client.http.HttpMethod;
import com.icenter.core.client.rest.convert.JSONConverter;
import java.util.Objects;

public abstract class RemoteRESTServiceImpl implements RemoteRESTService {

    private String serviceEndPoint = Strings.Empty;

    @Override
    public final String getServiceEndPoint() {
        return this.serviceEndPoint;
    }

    @Override
    public final void setServiceEndPoint(String endPoint){
        this.serviceEndPoint = endPoint;
    }

    protected final <T> void send(String action, String httpMethod, JSONValue params, final JSONConverter<T> converter, final AsyncCallback<T> callback) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(httpMethod);
        Objects.requireNonNull(converter);
        Objects.requireNonNull(callback);

        HttpClient.of(HttpMethod.of(httpMethod))
                  //.url(URL.encode(Urls.addSlashIfNeeded(getServiceEndPoint()) + Strings.toUpperCase(action,0)))
                  .send(params, new RemoteRESTServiceCallback(converter, callback));

        RequestBuilder builder = new RequestBuilder(HttpMethod.getMethod(httpMethod), URL.encode(Urls.addSlashIfNeeded(getServiceEndPoint()) + Strings.toUpperCase(action,0)));
        builder.setHeader("Content-type", MimeTypes.Json.Json);

        builder.setRequestData(params.toString());

        try {
            builder.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onError(Request request, Throwable throwable) {
                    callback.onFailure(throwable);
                }

                @Override
                public void onResponseReceived(Request request, Response response) {
                    if(response.getStatusCode() != Response.SC_OK){
                       callback.onFailure(new Exception(response.getText())); // Create RemoteServiceException, Probably caused by remote service
                       return;
                    }

                    // It is not a json message
                    JSONParseResult result = JSON.parse(response.getText());
                    if(!result.isSucceed()){
                        callback.onFailure(result.getError()); // Create MalformedJSONException, Returns is not a JSON
                    }
                    else {
                        T object;
                        try {
                            object = converter.convertJSONToObject(result.getResult());
                        }
                        catch(Exception error) {
                            // not a expected json message
                            object = null;
                            callback.onFailure(error); // Create UnexpectedJSONException
                        }
                        callback.onSuccess(object);
                    }
                }
            });
        }
        catch (RequestException e) {
           callback.onFailure(e); // Create HTTPRelatedRequest exceptions
        }
    }

}
