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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import static com.coveros.selenified.utilities.Constants.GSON;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Assert will handle all verifications performed on the actual web services
 * calls themselves. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.2.2
 * @lastupdate 6/25/2019
 */
public class Assert extends Check {

    /**
     * The default constructor passing in the app and output file
     *
     * @param response - the response from the web services call
     * @param reporter - the file to write all logging out to
     */
    public Assert(Response response, Reporter reporter) {
        this.response = response;
        this.reporter = reporter;
    }

    ///////////////////////////////////////////////////////
    // assertions about the page in general
    ///////////////////////////////////////////////////////

    /**
     * Asserts the actual response code is equals to the expected response
     * code, and writes that out to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param expectedCode - the expected response code
     */
    @Override
    @SuppressWarnings("squid:S1201")
    public void equals(int expectedCode) {
        assertEquals("Code Mismatch", expectedCode, checkEquals(expectedCode));
    }

    /**
     * Asserts the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param expectedJson - the expected response json object
     */
    @Override
    @SuppressWarnings("squid:S1201")
    public void equals(JsonObject expectedJson) {
        assertEquals("JsonObject Response Mismatch", expectedJson, checkEquals(expectedJson));
    }

    /**
     * Asserts the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param expectedJson - the expected response json array
     */
    @Override
    @SuppressWarnings("squid:S1201")
    public void equals(JsonArray expectedJson) {
        assertEquals("JsonArray Response Mismatch", expectedJson, checkEquals(expectedJson));
    }

    /**
     * Asserts the actual response json payload contains a key with a value equal to the expected
     * value. The jsonKeys should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param jsonKeys      - the crumbs of json object keys leading to the field with the expected value
     * @param expectedValue - the expected value
     */
    @Override
    @SuppressWarnings("squid:S1201")
    public void equals(List<String> jsonKeys, Object expectedValue) {
        assertEquals("JsonElement Response Mismatch", expectedValue, checkEquals(jsonKeys, expectedValue));
    }

    /**
     * Asserts the actual response payload is equal to the expected
     * response payload, and writes that out to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param expectedMessage - the expected response message
     */

    @Override
    @SuppressWarnings("squid:S1201")
    public void equals(String expectedMessage) {
        assertEquals("Response Message Mismatch", expectedMessage, checkEquals(expectedMessage));
    }

    /**
     * Asserts the actual response json payload contains each of the pair
     * values provided, and writes that to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    @Override
    public void contains(Map<String, Object> expectedPairs) {
        assertTrue("Expected to find " + Reporter.formatKeyPair(expectedPairs), checkContains(expectedPairs));
    }

    /**
     * Asserts the actual response json payload contains a key containing a JsonObject
     * containing each of the pair values provided. The jsonKeys should be passed in
     * as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param jsonKeys      - the crumbs of json object keys leading to the field with the expected value
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    public void contains(List<String> jsonKeys, Map<String, Object> expectedPairs) {
        assertTrue("Expected to find " + Reporter.formatKeyPair(expectedPairs), checkContains(jsonKeys, expectedPairs));
    }

    /**
     * Asserts the actual response json payload contains a key containing a JsonElement.
     * The jsonKeys should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param jsonKeys     - the crumbs of json object keys leading to the field with the expected value
     * @param expectedJson - the expected response json array
     */
    public void contains(List<String> jsonKeys, JsonElement expectedJson) {
        assertTrue("Expected to find " + GSON.toJson(expectedJson), checkContains(jsonKeys, expectedJson));
    }

    /**
     * Asserts the actual response json payload contains to the expected json
     * element, and writes that out to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param expectedJson - the expected response json array
     */
    @Override
    public void contains(JsonElement expectedJson) {
        assertTrue("Expected to find " + GSON.toJson(expectedJson), checkContains(expectedJson));
    }

    /**
     * Asserts the actual response json payload contains to the expected json
     * element, and writes that out to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param expectedMessage - the expected response json array
     */
    @Override
    public void contains(String expectedMessage) {
        assertTrue("Expected to find '" + expectedMessage + "'", checkContains(expectedMessage));
    }
}