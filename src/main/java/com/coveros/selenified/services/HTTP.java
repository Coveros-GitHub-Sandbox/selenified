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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import org.testng.log4testng.Logger;

import com.coveros.selenified.tools.General;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A class designed to make HTTP calls. This is wrapped by the Action and Assert
 * classes to ensure calls are properly written to logs, and data can be easily
 * accessed
 *
 * @author Max Saperstone
 * @version 2.0.1
 * @lastupdate 8/1/2017
 */
public class HTTP {

    private static final Logger log = Logger.getLogger(General.class);

    private static final String PATCH = "PATCH";

    private String serviceBaseUrl;
    private String user = "";
    private String pass = "";

    public HTTP(String serviceBaseUrl) {
        this.serviceBaseUrl = serviceBaseUrl;
    }

    public HTTP(String serviceBaseUrl, String user, String pass) {
        this.serviceBaseUrl = serviceBaseUrl;
        this.user = user;
        this.pass = pass;
    }

    public String getServiceBaseUrl() {
        return serviceBaseUrl;
    }

    public void setServiceBaseUrl(String serviceBaseUrl) {
        this.serviceBaseUrl = serviceBaseUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public boolean useCredentials() {
        return !this.user.isEmpty() && !this.pass.isEmpty();
    }

    /**
     * A basic http get call
     * 
     * @param service
     *            - the endpoint of the service under test
     * @return Response: the response provided from the http call
     */
    public Response get(String service) {
        return get(service, null);
    }

    /**
     * A basic http get call
     * 
     * @param service
     *            - the endpoint of the service under test
     * @param request
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response get(String service, Request request) {
        return call("GET", service, request);
    }

    /**
     * A basic http post call
     * 
     * @param service
     *            - the endpoint of the service under test
     * @param request
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response post(String service, Request request) {
        return call("POST", service, request);
    }

    /**
     * A basic http put call
     * 
     * @param service
     *            - the endpoint of the service under test
     * @param request
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response put(String service, Request request) {
        return call("PUT", service, request);
    }

    /**
     * A basic http patch call
     * 
     * @param service
     *            - the endpoint of the service under test
     * @param request
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response patch(String service, Request request) {
        return call(PATCH, service, request);
    }

    /**
     * A basic http delete call
     * 
     * @param service
     *            - the endpoint of the service under test
     * @return Response: the response provided from the http call
     */
    public Response delete(String service) {
        return delete(service, null);
    }

    /**
     * A basic http delete call
     * 
     * @param service
     *            - the endpoint of the service under test
     * @param request
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    public Response delete(String service, Request request) {
        return call("DELETE", service, request);
    }

    /**
     * A basic generic http call
     * 
     * @param call
     *            - what method are we calling
     * @param service
     *            - the endpoint of the service under test
     * @param request
     *            - the parameters to be passed to the endpoint for the service
     *            call
     * @return Response: the response provided from the http call
     */
    private Response call(String call, String service, Request request) {
        StringBuilder params = new StringBuilder();
        if (request != null && request.getParams() != null) {
            params.append("?");
            for (String key : request.getParams().keySet()) {
                params.append(key);
                params.append("=");
                params.append(request.getParams().get(key));
                params.append("&");
            }
        }
        HttpURLConnection connection = null;
        try {
            URL url = new URL(this.serviceBaseUrl + service + params.toString());
            connection = (HttpURLConnection) url.openConnection();
            String method = call;
            if (PATCH.equals(method)) {
                connection.setRequestProperty("X-HTTP-Method-Override", PATCH);
                method = "POST";
            }
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-length", "0");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            if (useCredentials()) {
                String userpass = user + ":" + pass;
                String encoding = new String(Base64.encodeBase64(userpass.getBytes()));
                connection.setRequestProperty("Authorization", "Basic " + encoding);
            }
            connection.connect();
            if (request != null && request.getData() != null) {
                writeDataRequest(connection, request);
            }
            return getResponse(connection);
        } catch (IOException e) {
            log.error(e);
        } finally {
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
        return null;
    }

    /**
     * pushes request data to the open http connection
     * 
     * @param connection
     *            - the open connection of the http call
     * @param request
     *            - the parameters to be passed to the endpoint for the service
     *            call
     */
    private void writeDataRequest(HttpURLConnection connection, Request request) {
        try (OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());) {
            wr.write(request.getData().toString());
            wr.flush();
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * extracts the response data from the open http connection
     * 
     * @param connection
     *            - the open connection of the http call
     * @return Response: the response provided from the http call
     */
    private Response getResponse(HttpURLConnection connection) {
        int status;
        try {
            status = connection.getResponseCode();
        } catch (IOException e) {
            log.error(e);
            return null;
        }
        Response response = new Response(status);
        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        String data = "";
        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            log.warn(e);
            rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        } finally {
            StringBuilder sb = new StringBuilder();
            String line;
            if (rd != null) {
                try {
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (IOException e) {
                    log.error(e);
                }
                try {
                    rd.close();
                } catch (IOException e) {
                    log.error(e);
                }
                data = sb.toString();
                response.setMessage(data);
                JsonParser parser = new JsonParser();
                try {
                    object = (JsonObject) parser.parse(data);
                    response.setObjectData(object);
                } catch (ClassCastException e) {
                    log.debug(e);
                    array = (JsonArray) parser.parse(data);
                    response.setArrayData(array);
                }
            }
        }
        return response;
    }
}