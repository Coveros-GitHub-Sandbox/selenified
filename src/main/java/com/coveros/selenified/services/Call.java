/*
 * Copyright 2019 Coveros, Inc.
 *
 * This file is part of Selenified.
 *
 * Selenified is licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.coveros.selenified.services;

import com.coveros.selenified.exceptions.InvalidHTTPException;
import com.coveros.selenified.exceptions.InvalidReporterException;
import com.coveros.selenified.utilities.Reporter;
import com.coveros.selenified.services.HTTP.ContentType;

import java.io.File;
import java.util.Map;

/**
 * Performs the general web service method calls, and provides a simple access
 * to the HTTP class
 *
 * @author Max Saperstone
 * @version 3.2.2
 * @lastupdate 4/4/2019
 */
public class Call {

    // this will be the name of the file we write all commands out to
    private final Reporter reporter;

    // what services will we be interacting with
    private final HTTP http;

    protected enum Method {GET, POST, PUT, PATCH, DELETE}

    public Call(HTTP http, Map<String, Object> headers) throws InvalidHTTPException, InvalidReporterException {
        if (http == null) {
            throw new InvalidHTTPException("Need to provide a valid HTTP to make calls against");
        } else {
            this.http = http;
        }
        if (http.getReporter() == null) {
            throw new InvalidReporterException("Need to provide a valid reporter for logging");
        } else {
            this.reporter = http.getReporter();
        }
        if (headers != null) {
            addHeaders(headers);
        }
    }

    public Reporter getReporter() {
        return this.reporter;
    }

    /**
     * Sets the content type. Currently only application/json and multipart/form-data are supported, but we
     * are looking to add support for several other forms in the future
     *
     * @param contentType - the content type to set
     */
    public void setContentType(ContentType contentType) {
        http.setContentType(contentType);
    }

    /**
     * Adds the desired headers via a map. These should just be key-value pairs, as many as are desired to set.
     * Content-length, Content-Type, and accept are already set, but can be overridden with these values
     *
     * @param headers - the key-value pair of headers to set
     */
    public void addHeaders(Map<String, Object> headers) {
        http.addHeaders(headers);
    }

    /**
     * Clears out any custom set headers
     */
    public void resetHeaders() {
        http.resetHeaders();
    }

    /**
     * Adds the desired credentials. These should just be a simple, unencrypted/hashed username and password
     * pair
     *
     * @param user - the username required for authentication
     * @param pass - the password required for authentication
     */
    public void addCredentials(String user, String pass) {
        http.addCredentials(user, pass);
    }

    ///////////////////////////////////////////////////////////////////
    // some simple actions for our services
    ///////////////////////////////////////////////////////////////////

    /**
     * Performs a get http call and writes the call and response information to
     * the output file
     *
     * @param endpoint - the endpoint of the service under test
     * @return Response: the response provided from the http call
     */
    public Response get(String endpoint) {
        return call(Method.GET, endpoint, null, null);
    }

    /**
     * Performs a get http call and writes the call and response information to
     * the output file
     *
     * @param endpoint - the endpoint of the service under test
     * @param params   - the parameters to be passed to the endpoint for the service
     *                 call
     * @return Response: the response provided from the http call
     */
    public Response get(String endpoint, Request params) {
        return call(Method.GET, endpoint, params, null);
    }

    /**
     * Performs a post http call and writes the call and response information to
     * the output file
     *
     * @param endpoint - the endpoint of the service under test
     * @param params   - the parameters to be passed to the endpoint for the service
     *                 call
     * @return Response: the response provided from the http call
     */
    public Response post(String endpoint, Request params) {
        return call(Method.POST, endpoint, params, null);
    }

    /**
     * Performs a post http call and writes the call and response information to
     * the output file
     *
     * @param endpoint - the endpoint of the service under test
     * @param params   - the parameters to be passed to the endpoint for the service
     *                 call
     * @param file     - an input file to be provided with the call
     * @return Response: the response provided from the http call
     */
    public Response post(String endpoint, Request params, File file) {
        return call(Method.POST, endpoint, params, file);
    }

    /**
     * Performs a put http call and writes the call and response information to
     * the output file
     *
     * @param endpoint - the endpoint of the service under test
     * @param params   - the parameters to be passed to the endpoint for the service
     *                 call
     * @return Response: the response provided from the http call
     */
    public Response put(String endpoint, Request params) {
        return call(Method.PUT, endpoint, params, null);
    }

    /**
     * Performs a put http call and writes the call and response information to
     * the output file
     *
     * @param endpoint - the endpoint of the service under test
     * @param params   - the parameters to be passed to the endpoint for the service
     *                 call
     * @param file     - an input file to be provided with the call
     * @return Response: the response provided from the http call
     */
    public Response put(String endpoint, Request params, File file) {
        return call(Method.PUT, endpoint, params, file);
    }

    /**
     * Performs a patch http call and writes the call and response information to
     * the output file
     *
     * @param endpoint - the endpoint of the service under test
     * @param params   - the parameters to be passed to the endpoint for the service
     *                 call
     * @return Response: the response provided from the http call
     */
    public Response patch(String endpoint, Request params) {
        return call(Method.PATCH, endpoint, params, null);
    }

    /**
     * Performs a patch http call and writes the call and response information to
     * the output file
     *
     * @param endpoint - the endpoint of the service under test
     * @param params   - the parameters to be passed to the endpoint for the service
     *                 call
     * @param file     - an input file to be provided with the call
     * @return Response: the response provided from the http call
     */
    public Response patch(String endpoint, Request params, File file) {
        return call(Method.PATCH, endpoint, params, file);
    }

    /**
     * Performs a delete http call and writes the call and response information
     * to the output file
     *
     * @param endpoint - the endpoint of the service under test
     * @return Response: the response provided from the http call
     */
    public Response delete(String endpoint) {
        return call(Method.DELETE, endpoint, null, null);
    }

    /**
     * Performs a delete http call and writes the call and response information
     * to the output file
     *
     * @param endpoint - the endpoint of the service under test
     * @param params   - the parameters to be passed to the endpoint for the service
     *                 call
     * @return Response: the response provided from the http call
     */
    public Response delete(String endpoint, Request params) {
        return call(Method.DELETE, endpoint, params, null);
    }

    /**
     * Performs a delete http call and writes the call and response information
     * to the output file
     *
     * @param endpoint - the endpoint of the service under test
     * @param params   - the parameters to be passed to the endpoint for the service
     *                 call
     * @param file     - an input file to be provided with the call
     * @return Response: the response provided from the http call
     */
    public Response delete(String endpoint, Request params, File file) {
        return call(Method.DELETE, endpoint, params, file);
    }

    /**
     * Performs an http call and writes the call and response information to the
     * output file
     *
     * @param method     - what http method call is being made. should be in all caps
     * @param endpoint - the endpoint of the service under test
     * @param params   - the parameters to be passed to the endpoint for the service
     *                 call
     * @return Response: the response provided from the http call
     */
    private Response call(Method method, String endpoint, Request params, File inputFile) {
        StringBuilder action = new StringBuilder();
        action.append("Making <i>");
        action.append(method.toString());
        action.append("</i> call to <i>");
        action.append(http.getServiceBaseUrl()).append(endpoint).append(http.getRequestParams(params));
        action.append("</i>");
        action.append("<div class='indent'>");
        action.append(Reporter.getCredentialStringOutput(http));
        action.append(Reporter.getRequestHeadersOutput(http));
        action.append(Reporter.getRequestPayloadOutput(params, inputFile));
        action.append("</div>");
        String expected = "<i>" + method + "</i> call was performed";
        Response response = null;
        try {
            switch (method) {
                case GET:
                    response = http.get(endpoint, params);
                    break;
                case POST:
                    response = http.post(endpoint, params, inputFile);
                    break;
                case PUT:
                    response = http.put(endpoint, params, inputFile);
                    break;
                case PATCH:
                    response = http.patch(endpoint, params, inputFile);
                    break;
                case DELETE:
                    response = http.delete(endpoint, params, inputFile);
                    break;
            }
            String actual = expected;
            actual += "<div class='indent'>";
            actual += Reporter.getResponseHeadersOutput(response);
            actual += Reporter.getResponseCodeOutput(response);
            actual += Reporter.getResponseOutput(response);
            actual += "</div>";
            reporter.pass(action.toString(), expected, actual);
        } catch (Exception e) {
            reporter.fail(action.toString(), expected, "<i>" + method + "</i> call failed. " + e.getMessage());
        }
        return response;
    }
}