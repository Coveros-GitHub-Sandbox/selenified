/*
 * Copyright 2018 Coveros, Inc.
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

import com.coveros.selenified.OutputFile;
import com.coveros.selenified.OutputFile.Success;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * A class designed to hold data provided from the HTTP calls.
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class Response {
    private int code;
    private JsonObject object = null;
    private JsonArray array = null;
    private String message = null;

    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // constants
    private static final String FOUND = "Found a response of:";

    // a basic response setup, just with an output file to write information to
    public Response(OutputFile file) {
        this.file = file;
    }

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

    public void setOutputFile(OutputFile file) {
        this.file = file;
    }

    ///////////////////////////////////////////////////////////////////
    // some comparisons for our services
    ///////////////////////////////////////////////////////////////////

    /**
     * Verifies the actual response code is equals to the expected response
     * code, and writes that out to the output file
     *
     * @param expectedCode - the expected response code
     */
    public void assertEquals(int expectedCode) {
        Success success = (code == expectedCode) ? Success.PASS : Success.FAIL;
        file.recordExpected("Expected to find a response code of <b>" + expectedCode + "</b>");
        file.recordActual("Found a response code of <b>" + code + "</b>", success);
        file.addErrors(success.getErrors());
    }

    /**
     * Verifies the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json object
     */
    public void assertEquals(JsonObject expectedJson) {
        Success success = Success.FAIL;
        if (object != null) {
            success = object.equals(expectedJson) ? Success.PASS : Success.FAIL;
        }
        file.recordExpected(
                "Expected to find a response of:" + file.formatResponse(new Response(0, expectedJson, null)));
        file.recordActual(FOUND + file.formatResponse(this), success);
        file.addErrors(success.getErrors());
    }

    /**
     * Verifies the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedArray - the expected response json array
     */
    public void assertEquals(JsonArray expectedArray) {
        Success success = Success.FAIL;
        if (array != null) {
            success = array.equals(expectedArray) ? Success.PASS : Success.FAIL;
        }
        file.recordExpected(
                "Expected to find a response of:" + file.formatResponse(new Response(0, expectedArray, null)));
        file.recordActual(FOUND + file.formatResponse(this), success);
        file.addErrors(success.getErrors());
    }

    /**
     * Verifies the actual response json payload contains each of the pair
     * values provided, and writes that to the output file
     *
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    public void assertContains(Map<String, String> expectedPairs) {
        StringBuilder expectedString = new StringBuilder();
        Success success = (object == null) ? Success.FAIL : Success.PASS;
        for (Map.Entry<String, String> entry : expectedPairs.entrySet()) {
            expectedString.append("<div>");
            expectedString.append(entry.getKey());
            expectedString.append(" : ");
            expectedString.append(entry.getValue());
            expectedString.append("</div>");
            if (object != null && (!object.has(entry.getKey()) ||
                    !object.get(entry.getKey()).getAsString().equals(entry.getValue()))) {
                success = Success.FAIL;
            }
        }
        file.recordExpected(
                "Expected to find a response containing: <div><i>" + expectedString.toString() + "</i></div>");
        file.recordActual(FOUND + file.formatResponse(this), success);
        file.addErrors(success.getErrors());
    }

    /**
     * Verifies the actual response json payload contains a key with a value
     * equals to the expected json element, and writes that out to the output
     * file
     *
     * @param key          - a String key value expected in the result
     * @param expectedJson - the expected response json object
     */
    public void assertContains(String key, JsonElement expectedJson) {
        Success success = Success.FAIL;
        if (object != null && object.has(key)) {
            success = object.get(key).equals(expectedJson) ? Success.PASS : Success.FAIL;
        }
        file.recordExpected("Expected to find a response with key <i>" + key + "</i> equal to: " +
                file.formatResponse(new Response(0, expectedJson.getAsJsonObject(), null)));
        file.recordActual(FOUND + file.formatResponse(this), success);
        file.addErrors(success.getErrors());
    }

    /**
     * Verifies the actual response json payload contains to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    public void assertContains(JsonElement expectedJson) {
        Success success = Success.FAIL;
        if (array != null) {
            success = array.contains(expectedJson) ? Success.PASS : Success.FAIL;
        }
        file.recordExpected("Expected to find a response containing:" +
                file.formatResponse(new Response(0, expectedJson.getAsJsonObject(), null)));
        file.recordActual(FOUND + file.formatResponse(this), success);
        file.addErrors(success.getErrors());
    }
}