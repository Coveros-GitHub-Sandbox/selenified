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
import com.coveros.selenified.services.Call.Method;
import com.coveros.selenified.utilities.Property;
import com.coveros.selenified.utilities.Reporter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.codec.binary.Base64;
import org.testng.log4testng.Logger;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * A class designed to make HTTP calls. This is wrapped by the Action and Assert
 * classes to ensure calls are properly written to logs, and data can be easily
 * accessed
 *
 * @author Max Saperstone
 * @version 3.2.1
 * @lastupdate 4/4/2019
 */
public class HTTP {

    private static final Logger log = Logger.getLogger(HTTP.class);
    private static final String MULTIPART = "multipart/form-data; boundary=";
    private static final String BOUNDARY = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
    private static final String NEWLINE = "\r\n";

    private static final String CONTENT_TYPE = "Content-Type";

    private final Reporter reporter;
    private final String serviceBaseUrl;
    private String user = "";
    private String pass = "";
    private Map<String, Object> extraHeaders = new HashMap<>();
    private ContentType contentType = ContentType.JSON;

    /**
     * An enum for handling multiple content types. This is specifically only capable for handling json
     * and formdata currently
     */
    public enum ContentType {
        JSON("application/json; charset=UTF-8"), FORMDATA(MULTIPART + BOUNDARY);
        private String contentTypeString;

        ContentType(String contentTypeString) {
            this.contentTypeString = contentTypeString;
        }

        public String getContentType() {
            return contentTypeString;
        }
    }

    /**
     * Instantiates a HTTP session for making web service calls without any
     * authentication
     *
     * @param reporter       - the output file to write everything to
     * @param serviceBaseUrl - the base url of the services location
     */
    public HTTP(Reporter reporter, String serviceBaseUrl) {
        this.reporter = reporter;
        this.serviceBaseUrl = serviceBaseUrl;
    }

    /**
     * Instantiates a HTTP session for making web service calls with basic
     * username/password authentication
     *
     * @param reporter       - the output file to write everything to
     * @param serviceBaseUrl - the base url of the services location
     * @param user           - the username required for authentication
     * @param pass           - the password required for authentication
     */
    public HTTP(Reporter reporter, String serviceBaseUrl, String user, String pass) {
        this.reporter = reporter;
        this.serviceBaseUrl = serviceBaseUrl;
        this.user = user;
        this.pass = pass;
    }

    /**
     * Sets the content type. Currently only application/json and multipart/form-data are supported, but we
     * are looking to add support for several other forms in the future
     *
     * @param contentType - the content type to set
     */
    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    /**
     * Adds the desired headers via a map. These should just be key-value pairs, as many as are desired to set.
     * Content-length, Content-Type, and accept are already set, but can be overridden with these values
     *
     * @param headers - the key-value pair of headers to set
     */
    public void addHeaders(Map<String, Object> headers) {
        this.extraHeaders.putAll(headers);
    }

    /**
     * Clears out any custom set headers
     */
    public void resetHeaders() {
        this.extraHeaders = new HashMap<>();
    }

    /**
     * Builds the headers to be passed in the HTTP call
     *
     * @return Map: a mapping of the headers in key to values
     */
    public Map<String, Object> getHeaders() {
        Map<String, Object> map = new HashMap<>();
        map.put("Content-length", "0");
        map.put(CONTENT_TYPE, contentType.getContentType());
        map.put("Accept", "application/json");
        for (Map.Entry<String, Object> entry : extraHeaders.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * Adds the desired credentials. These should just be a simple, unencrypted/hashed username and password
     * pair
     *
     * @param user - the username required for authentication
     * @param pass - the password required for authentication
     */
    public void addCredentials(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    /**
     * Retrieves the reporter set in the HTTP object, to be used for custom logging
     *
     * @return Reporter
     */
    public Reporter getReporter() {
        return reporter;
    }

    /**
     * Retrieves the base url of the services location
     *
     * @return String: the base url of the services location
     */
    public String getServiceBaseUrl() {
        return serviceBaseUrl;
    }

    /**
     * Retrieves the username used for authentication with the application. If
     * none was set, an empty string will be returned
     *
     * @return user: the username required for authentication
     */
    public String getUser() {
        return user;
    }

    /**
     * Retrieves the password used for authentication with the application. If
     * none was set, an empty string will be returned
     *
     * @return pass: the password required for authentication
     */
    public String getPass() {
        return pass;
    }

    /**
     * Determines whether or not authentication should be used, by checking to
     * see if both username and password are set
     *
     * @return Boolean: are both the username and password set
     */
    public boolean useCredentials() {
        return !this.user.isEmpty() && !this.pass.isEmpty();
    }

    /**
     * A basic http get call
     *
     * @param service - the endpoint of the service under test
     * @param request - the parameters to be passed to the endpoint for the service
     *                call
     * @return Response: the response provided from the http call
     */
    public Response get(String service, Request request) throws IOException {
        return call(Method.GET, service, request, null);
    }

    /**
     * A basic http post call
     *
     * @param service - the endpoint of the service under test
     * @param request - the parameters to be passed to the endpoint for the service
     *                call
     * @param file    - a file to upload, accompanied with the post
     * @return Response: the response provided from the http call
     */
    public Response post(String service, Request request, File file) throws IOException {
        return call(Method.POST, service, request, file);
    }

    /**
     * A basic http put call
     *
     * @param service - the endpoint of the service under test
     * @param request - the parameters to be passed to the endpoint for the service
     *                call
     * @param file    - a file to upload, accompanied with the post
     * @return Response: the response provided from the http call
     */
    public Response put(String service, Request request, File file) throws IOException {
        return call(Method.PUT, service, request, file);
    }

    /**
     * A basic http delete call
     *
     * @param service - the endpoint of the service under test
     * @param request - the parameters to be passed to the endpoint for the service
     *                call
     * @param file    - a file to upload, accompanied with the post
     * @return Response: the response provided from the http call
     */
    public Response delete(String service, Request request, File file) throws IOException {
        return call(Method.DELETE, service, request, file);
    }

    /**
     * Returns a string representation of the parameters, able to be appended to the url
     *
     * @param request - the parameters to be passed to the endpoint for the service
     *                call
     * @return String: the string friendly url parameter representation
     */
    public String getRequestParams(Request request) {
        StringBuilder params = new StringBuilder();
        if (request != null && request.getUrlParams() != null) {
            params.append("?");
            for (String key : request.getUrlParams().keySet()) {
                params.append(key);
                params.append("=");
                params.append(String.valueOf(request.getUrlParams().get(key)));
                params.append("&");
            }
            params.deleteCharAt(params.length() - 1);
        }
        return params.toString();
    }

    /**
     * Setups up the header and basic connection information
     *
     * @param connection - the connection to add headers to
     */
    private void setupHeaders(HttpURLConnection connection) {
        for (Map.Entry<String, Object> entry : getHeaders().entrySet()) {
            connection.setRequestProperty(entry.getKey(), String.valueOf(entry.getValue()));
        }
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setAllowUserInteraction(false);
    }

    /**
     * A basic generic http call
     *
     * @param method  - what method are we calling
     * @param service - the endpoint of the service under test
     * @param request - the parameters to be passed to the endpoint for the service
     *                call
     * @param file    - is there a file to upload as well
     * @return Response: the response provided from the http call
     */
    private Response call(Method method, String service, Request request, File file) throws IOException {
        URL url = new URL(this.serviceBaseUrl + service + getRequestParams(request));
        HttpURLConnection connection = getConnection(url);
        connection.setRequestMethod(method.toString());
        setupHeaders(connection);
        if (useCredentials()) {
            String userpass = user + ":" + pass;
            String encoding = new String(Base64.encodeBase64(userpass.getBytes()));
            connection.setRequestProperty("Authorization", "Basic " + encoding);
        }
        connection.connect();
        if ((request != null && request.isPayload()) || file != null) {
            if (this.contentType == ContentType.FORMDATA) {
                writeMultipartDataRequest(connection, request, file);
            } else if (this.contentType == ContentType.JSON) {
                writeJsonDataRequest(connection, request);
            } else {
                throw new InvalidHTTPException("Content-Type '" + connection.getRequestProperty(CONTENT_TYPE) +
                        "' not currently supported by Selenified. Current supported types are " +
                        "'application/json' and 'multipart/form-data'.");
            }
        }
        return getResponse(connection);
    }

    /**
     * Opens the URL connection, and if a proxy is provided, uses the proxy to establish the connection
     *
     * @param url - the url to connect to
     * @return HttpURLConnection: our established http connection
     * @throws IOException: if the connection can't be established, an IOException is thrown
     */
    private HttpURLConnection getConnection(URL url) throws IOException {
        Proxy proxy = Proxy.NO_PROXY;
        if (Property.isProxySet()) {
            SocketAddress addr = new InetSocketAddress(Property.getProxyHost(), Property.getProxyPort());
            proxy = new Proxy(Proxy.Type.HTTP, addr);
        }
        return (HttpURLConnection) url.openConnection(proxy);
    }

    /**
     * Pushes request data to the open http connection
     *
     * @param connection - the open connection of the http call
     * @param request    - the parameters to be passed to the endpoint for the service
     *                   call
     */
    private void writeJsonDataRequest(HttpURLConnection connection, Request request) {
        try (OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream())) {
            if (request != null && request.getJsonPayload() != null) {
                wr.write(request.getJsonPayload().toString());
            }
            wr.flush();
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Pushes request data to the open http connection
     *
     * @param connection - the open connection of the http call
     * @param request    - the parameters to be passed to the endpoint for the service
     *                   call
     * @param file       - the file to upload with the request
     */
    private void writeMultipartDataRequest(HttpURLConnection connection, Request request, File file) {
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(NEWLINE);
            if (request != null && request.getJsonPayload() != null) {
                wr.writeBytes(NEWLINE + "--" + BOUNDARY + NEWLINE);
                wr.writeBytes("Content-Disposition: form-data; name=\"data\"");
                wr.writeBytes(NEWLINE + NEWLINE);
                wr.writeBytes(request.getJsonPayload().toString());
            }
            if (request != null && request.getMultipartData() != null) {
                for (String param : request.getMultipartData().keySet()) {
                    wr.writeBytes(NEWLINE + "--" + BOUNDARY + NEWLINE);
                    wr.writeBytes("Content-Disposition: form-data; name=\"" + param + "\"");
                    wr.writeBytes(NEWLINE + NEWLINE);
                    wr.writeBytes(request.getMultipartData().get(param).toString());
                }
            }
            if (file != null) {
                if (!file.exists()) {
                    log.error("Unable to upload file with http call, as file can't be found: " + file.getName());
                } else {
                    wr.writeBytes(NEWLINE + "--" + BOUNDARY + NEWLINE);
                    wr.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"");
                    wr.writeBytes(NEWLINE);
                    wr.writeBytes("Content-Type: " + Files.probeContentType(file.toPath()));
                    wr.writeBytes(NEWLINE + NEWLINE);
                    byte[] bytes = Files.readAllBytes(file.toPath());
                    wr.write(bytes);
                }
            }
            wr.writeBytes(NEWLINE + "--" + BOUNDARY + "--" + NEWLINE);
            wr.flush();
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Extracts the response data from the open http connection
     *
     * @param connection - the open connection of the http call
     * @return Response: the response provided from the http call
     */
    @SuppressWarnings({"squid:S3776", "squid:S2093"})
    private Response getResponse(HttpURLConnection connection) {
        int status;
        Map headers;
        try {
            status = connection.getResponseCode();
            headers = connection.getHeaderFields();
        } catch (IOException e) {
            log.error(e);
            return null;
        }
        JsonObject object = null;
        JsonArray array = null;
        String data = null;
        BufferedReader rd = null;
        // unable to use the try-with-resources block, as the rd needs to be read in the finally, and can't be closed
        try {
            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            log.warn(e);
            try {
                rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            } catch (NullPointerException ee) {
                log.warn(ee);
            }
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
                JsonParser parser = new JsonParser();
                try {
                    object = (JsonObject) parser.parse(data);
                } catch (JsonSyntaxException | ClassCastException e) {
                    log.debug(e);
                    try {
                        array = (JsonArray) parser.parse(data);
                    } catch (JsonSyntaxException | ClassCastException ee) {
                        log.debug(ee);
                    }
                }
            }
        }
        return new Response(reporter, headers, status, object, array, data);
    }
}