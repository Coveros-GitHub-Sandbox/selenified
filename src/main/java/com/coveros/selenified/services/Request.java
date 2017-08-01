package com.coveros.selenified.services;

import java.util.Map;

import com.google.gson.JsonObject;

public class Request {
    private JsonObject data = null;
    private Map<String, String> params = null;
    
    public Request(JsonObject data) {
        this.data = data;
    }
    
    public Request(Map<String, String> params) {
        this.params = params;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
