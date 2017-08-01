package com.coveros.selenified.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Response {
    private int code;
    private JsonObject object = null;
    private JsonArray array = null;
    private String message;
    
    public Response(int code) {
        this.code = code;
    }
    
    public Response(int code, JsonObject object, String message) {
        this.code = code;
        this.object = object;
        this.message = message;
    }
    
    public Response(int code, JsonArray array, String message) {
        this.code = code;
        this.array = array;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    public boolean isData() {
        return object != null || array != null;
    }
    
    public JsonArray getArrayData() {
        return array;
    }

    public void setArrayData(JsonArray array) {
        this.array = array;
    }
    
    public JsonObject getObjectData() {
        return object;
    }
    
    public void setObjectData(JsonObject object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
