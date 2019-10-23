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

import com.coveros.selenified.services.check.*;
import com.coveros.selenified.utilities.Reporter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * A class designed to hold data provided from the HTTP calls.
 *
 * @author Max Saperstone
 * @version 3.3.0
 * @lastupdate 4/4/2019
 */
public class Response {

    private final Map<String, Object> headers;
    private final int code;
    private final JsonObject object;
    private final JsonArray array;
    private final String message;

    // the assert class to check information about the response
    private final AssertContains assertContains;
    private final AssertEquals assertEquals;
    private final AssertExcludes assertExcludes;

    // the verify class to check information about the response
    private final VerifyContains verifyContains;
    private final VerifyEquals verifyEquals;
    private final VerifyExcludes verifyExcludes;

    public Response(Reporter reporter, Map<String, Object> headers, int code, JsonObject object, JsonArray array, String message) {
        this.headers = headers;
        this.code = code;
        this.object = object;
        this.array = array;
        this.message = message;
        this.assertContains = new AssertContains(this, reporter);
        this.assertEquals = new AssertEquals(this, reporter);
        this.assertExcludes = new AssertExcludes(this, reporter);
        this.verifyContains = new VerifyContains(this, reporter);
        this.verifyEquals = new VerifyEquals(this, reporter);
        this.verifyExcludes = new VerifyExcludes(this, reporter);
    }

    /**
     * Asserts that the services response will contain expected data.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented they provide additional traceability, and assist in
     * troubleshooting and debugging failing tests. A failed assert will cause
     * the test to immediately stop on the error.
     */
    public AssertContains assertContains() {
        return assertContains;
    }

    /**
     * Asserts that the services response will equals expected data.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented they provide additional traceability, and assist in
     * troubleshooting and debugging failing tests. A failed assert will cause
     * the test to immediately stop on the error.
     */
    public AssertEquals assertEquals() {
        return assertEquals;
    }

    /**
     * Asserts that the services response will not contain expected data.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented they provide additional traceability, and assist in
     * troubleshooting and debugging failing tests. A failed assert will cause
     * the test to immediately stop on the error.
     */
    public AssertExcludes assertExcludes() {
        return assertExcludes;
    }

    /**
     * Verifies that the services response will contain expected data.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented they provide additional traceability, and assist in
     * troubleshooting and debugging failing tests. A failed assert will cause
     * the test to immediately stop on the error.
     */
    public VerifyContains verifyContains() {
        return verifyContains;
    }

    /**
     * Verifies that the services response will equals expected data.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented they provide additional traceability, and assist in
     * troubleshooting and debugging failing tests. A failed assert will cause
     * the test to immediately stop on the error.
     */
    public VerifyEquals verifyEquals() {
        return verifyEquals;
    }


    /**
     * Verifies that the services response will not contain expected data.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented they provide additional traceability, and assist in
     * troubleshooting and debugging failing tests. A failed assert will cause
     * the test to immediately stop on the error.
     */
    public VerifyExcludes verifyExcludes() {
        return verifyExcludes;
    }

    public Map<String, Object> getHeaders() {
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