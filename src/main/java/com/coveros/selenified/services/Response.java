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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * A class designed to hold data provided from the HTTP calls.
 *
 * @author Max Saperstone
 * @version 2.0.1
 * @lastupdate 8/1/2017
 */
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
