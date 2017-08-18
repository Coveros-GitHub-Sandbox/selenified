/*
 * Copyright 2017 Coveros, Inc.
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

import org.testng.log4testng.Logger;

import com.coveros.selenified.OutputFile;
import com.coveros.selenified.OutputFile.Result;

/**
 * Performs the general web service method calls, and provides a simple access
 * to the HTTP class
 * 
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 *
 */
public class Call {
    private static final Logger log = Logger.getLogger(Call.class);

    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // what services will we be interacting with
    private HTTP http;

    // constants
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String PATCH = "PATCH";
    private static final String DELETE = "DELETE";

    public Call(HTTP http, OutputFile file) {
        this.http = http;
        this.file = file;
    }

    ///////////////////////////////////////////////////////////////////
    // some simple actions for our services
    ///////////////////////////////////////////////////////////////////

    /**
     * Performs a get http call and writes the call and response information to
     * the output file
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @return Response: the response provided from the http call
     */
    public Response get(String endpoint) {
        return call(GET, endpoint, null);
    }

    /**
     * Performs a get http call and writes the call and response information to
     * the output file
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response get(String endpoint, Request params) {
        return call(GET, endpoint, params);
    }

    /**
     * Performs a post http call and writes the call and response information to
     * the output file
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response post(String endpoint, Request params) {
        return call(POST, endpoint, params);
    }

    /**
     * Performs a put http call and writes the call and response information to
     * the output file
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response put(String endpoint, Request params) {
        return call(PUT, endpoint, params);
    }

    /**
     * Performs a patch http call and writes the call and response information
     * to the output file
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response patch(String endpoint, Request params) {
        return call(PATCH, endpoint, params);
    }

    /**
     * Performs a delete http call and writes the call and response information
     * to the output file
     * 
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response delete(String endpoint, Request params) {
        return call(DELETE, endpoint, params);
    }

    /**
     * Performs an http call and writes the call and response information to the
     * output file
     * 
     * @param call
     *            - what http method call is being made. should be in all caps
     * @param endpoint
     *            - the endpoint of the service under test
     * @param params
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    private Response call(String call, String endpoint, Request params) {
        StringBuilder action = new StringBuilder();
        action.append("Making <i>" + call + "</i> call to <i>" + http.getServiceBaseUrl() + endpoint + "</i>");
        action.append(appendCredentials());
        action.append(file.outputRequestProperties(params));
        String expected = "<i>" + call + "</i> call was made successfully";
        Response response = new Response(file);
        try {
            switch (call) {
            case GET:
                response = http.get(endpoint, params);
                break;
            case POST:
                response = http.post(endpoint, params);
                break;
            case PUT:
                response = http.put(endpoint, params);
                break;
            case PATCH:
                response = http.patch(endpoint, params);
                break;
            case DELETE:
                response = http.delete(endpoint, params);
                break;
            default:
                log.error("Unknown method call named");
            }
            response.setOutputFile(file);
            file.recordAction(action.toString(), expected, expected, Result.SUCCESS);
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action.toString(), expected, "<i>" + call + "</i> call failed. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            response = new Response(0);
            response.setOutputFile(file);
            return response;
        }
        return response;
    }

    /**
     * Looks for the simple login credentials, username and password, and if
     * they are both set, turns that into a string which will be formatted for
     * HTML to be printed into the output file
     * 
     * @return String: an HTML formated string with the username and password -
     *         if they are both set
     */
    private String appendCredentials() {
        StringBuilder credentials = new StringBuilder();
        if (http.useCredentials()) {
            credentials.append("<br/> with credentials: ");
            credentials.append("<div><i>");
            credentials.append("Username: " + http.getUser());
            credentials.append("</div><div>");
            credentials.append("Password: " + http.getPass());
            credentials.append("</i></div>");
        }
        return credentials.toString();
    }
}