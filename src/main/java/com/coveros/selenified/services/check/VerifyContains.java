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

package com.coveros.selenified.services.check;

import com.coveros.selenified.services.Response;
import com.coveros.selenified.utilities.Reporter;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

/**
 * Verify will handle all verifications performed on the actual web services
 * calls themselves. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.3.0
 * @lastupdate 6/25/2019
 */
public class VerifyContains extends Contains {

    /**
     * The default constructor passing in the app and output file
     *
     * @param response - the response from the web services call
     * @param reporter - the file to write all logging out to
     */
    public VerifyContains(Response response, Reporter reporter) {
        this.response = response;
        this.reporter = reporter;
    }

    ///////////////////////////////////////////////////////
    // assertions about the page in general
    ///////////////////////////////////////////////////////

    /**
     * Verifies the actual response json payload contains each key provided,
     * and writes that to the output file.
     *
     * @param expectedKeys a list with string keys expected in the json
     *                     response
     */
    @Override
    public void keys(List<String> expectedKeys) {
        checkKeys(expectedKeys);
    }

    /**
     * Verifies the actual response json payload contains each of the pair
     * values provided, and writes that to the output file.
     *
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    @Override
    public void keyValues(Map<String, Object> expectedPairs) {
        checkKeyValues(expectedPairs);
    }

    /**
     * Verifies the actual response json payload contains to the expected json
     * element, and writes that out to the output file.
     *
     * @param expectedJson - the expected response json array
     */
    @Override
    public void value(JsonElement expectedJson) {
        checkValue(expectedJson);
    }

    /**
     * Verifies the actual response json payload contains a key containing a JsonObject
     * containing each of the keys provided. The jsonKeys should be passed in
     * as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonKeys     - the crumbs of json object keys leading to the field with the expected value
     * @param expectedKeys - a list with string keys expected in the json
     *                     response
     */
    @Override
    public void nestedKeys(List<String> jsonKeys, List<String> expectedKeys) {
        checkNestedKeys(jsonKeys, expectedKeys);
    }

    /**
     * Verifies the actual response json payload contains a key containing a JsonObject
     * containing each of the pair values provided. The jsonKeys should be passed in
     * as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonKeys      - the crumbs of json object keys leading to the field with the expected value
     * @param expectedPairs - a hashmap with string key value pairs expected in the json
     *                      response
     */
    @Override
    public void nestedKeyValues(List<String> jsonKeys, Map<String, Object> expectedPairs) {
        checkNestedKeyValues(jsonKeys, expectedPairs);
    }

    /**
     * Verifies the actual response json payload contains a key containing a JsonElement.
     * The jsonKeys should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonKeys     - the crumbs of json object keys leading to the field with the expected value
     * @param expectedJson - the expected response json array
     */
    @Override
    public void nestedValue(List<String> jsonKeys, JsonElement expectedJson) {
        checkNestedValue(jsonKeys, expectedJson);
    }

    /**
     * Verifies the actual response json payload contains to the expected json
     * element, and writes that out to the output file.
     *
     * @param expectedMessage - the expected response json array
     */
    @Override
    public void message(String expectedMessage) {
        checkMessage(expectedMessage);
    }
}