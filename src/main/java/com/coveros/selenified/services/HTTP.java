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

public class HTTP {

    private static final Logger log = Logger.getLogger(General.class);

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

    public String getuser() {
        return user;
    }

    public void setuser(String user) {
        this.user = user;
    }

    public String getpass() {
        return pass;
    }

    public void setpass(String pass) {
        this.pass = pass;
    }

    public Response get(String service) {
        return get(service, null);
    }

    public Response get(String service, Request request) {
        return call("GET", service, request);
    }

    public Response post(String service, Request request) {
        return call("POST", service, request);
    }

    public Response put(String service, Request request) {
        return call("PUT", service, request);
    }

    public Response patch(String service, Request request) {
        return call("PATCH", service, request);
    }

    public Response delete(String service) {
        return delete(service, null);
    }

    public Response delete(String service, Request request) {
        return call("DELETE", service, request);
    }

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
            if ("PATCH".equals(call)) {
                connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
                call = "POST";
            }
            connection.setRequestMethod(call);
            connection.setRequestProperty("Content-length", "0");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            if (!"".equals(this.user) && !"".equals(this.pass)) {
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

    private void writeDataRequest(HttpURLConnection connection, Request request) {
        try (OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());) {
            wr.write(request.getData().toString());
            wr.flush();
        } catch (IOException e) {
            log.error(e);
        }
    }

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