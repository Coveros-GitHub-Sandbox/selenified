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

import com.google.gson.JsonElement;

import java.util.Map;

/**
 * A class designed to hold data needed to provide to the HTTP calls.
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 8/30/2018
 */
public class Request {
    private Map<String, Object> urlParams = null;
    private JsonElement jsonPayload = null;
    private Map<String, Object> multipartData = null;

    public JsonElement getJsonPayload() {
        return jsonPayload;
    }

    public Request setJsonPayload(JsonElement jsonPayload) {
        this.jsonPayload = jsonPayload;
        return this;
    }

    public Map<String, Object> getUrlParams() {
        return urlParams;
    }

    public Request setUrlParams(Map<String, Object> urlParams) {
        this.urlParams = urlParams;
        return this;
    }

    public Map<String, Object> getMultipartData() {
        return multipartData;
    }

    public Request setMultipartData(Map<String, Object> multipartData) {
        this.multipartData = multipartData;
        return this;
    }

    /**
     * Determines if either the jsonPayload or multipart data is set
     *
     * @return Boolean: true if either jsonpayload or multipartdata is set
     */
    public boolean isPayload() {
        return (jsonPayload != null || multipartData != null);
    }
}
