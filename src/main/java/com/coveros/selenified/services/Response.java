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

import com.coveros.selenified.utilities.Reporter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * A class designed to hold data provided from the HTTP calls.
 *
 * @author Max Saperstone
 * @version 3.2.1
 * @lastupdate 4/4/2019
 */
public class Response {

    private final Map headers;
    private final int code;
    private final JsonObject object;
    private final JsonArray array;
    private final String message;

    // the assert class to check information about the response
    private final Assert azzert;

    // the verify class to check information about the response
    private final Verify verify;

    public Response(Reporter reporter, Map headers, int code, JsonObject object, JsonArray array, String message) {
        this.headers = headers;
        this.code = code;
        this.object = object;
        this.array = array;
        this.message = message;
        this.azzert = new Assert(this, reporter);
        this.verify = new Verify(this, reporter);
    }

    /**
     * Will handle all assertions performed on the actual response itself.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented they provide additional traceability, and assist in
     * troubleshooting and debugging failing tests. A failed assert will cause
     * the test to immediately stop on the error.
     */
    public Assert azzert() {
        return azzert;
    }

    /**
     * Will handle all verifications performed on the actual response itself.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented they provide additional traceability, and assist in
     * troubleshooting and debugging failing tests. A failed assert will cause
     * the test to immediately stop on the error.
     */
    public Verify verify() {
        return verify;
    }

    public Map getHeaders() {
        return headers;
    }
    
    public int getCode() {
        return code;
    }

    public boolean isData() {
        return object != null || array != null;
    }

    public JsonArray getArrayData() {
        return array;
    }

    public JsonObject getObjectData() {
        return object;
    }

    public String getMessage() {
        return message;
    }
}