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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * Check will handle all verifications performed on the actual web services
 * calls themselves. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.3.0
 * @lastupdate 6/25/2019
 */
abstract class Check {

    static final String ARROW = " \uD83E\uDC1A ";

    // this will be the name of the file we write all commands out to
    Reporter reporter;

    // this is the driver that will be used for all selenium actions
    Response response;

    /**
     * Casts an unknown JsonElement into an object, based on the expected behavior of it
     *
     * @param known   - what value are we expecting
     * @param unknown - what JsonElement are we trying to cast
     * @return Object - the expected object, properly cast
     */
    public Object castObject(Object known, JsonElement unknown) {
        if (unknown == null) {
            return null;
        }
        Object objectVal;
        try {
            if (known instanceof String) {
                objectVal = unknown.getAsString();
            } else if (known instanceof Integer) {
                objectVal = unknown.getAsInt();
            } else if (known instanceof Double) {
                objectVal = unknown.getAsDouble();
            } else if (known instanceof Float) {
                objectVal = unknown.getAsFloat();
            } else if (known instanceof Long) {
                objectVal = unknown.getAsLong();
            } else if (known instanceof Boolean) {
                objectVal = unknown.getAsBoolean();
            } else if (known instanceof Byte) {
                objectVal = unknown.getAsByte();
            } else if (known instanceof Character) {
                objectVal = unknown.getAsCharacter();
            } else if (known instanceof JsonArray) {
                objectVal = unknown.getAsJsonArray();
            } else if (known instanceof JsonObject) {
                objectVal = unknown.getAsJsonObject();
            } else {
                objectVal = unknown;
            }
            return objectVal;
        } catch (UnsupportedOperationException | NumberFormatException | IllegalStateException e) {
            return unknown;
        }
    }

    /**
     * Checks whether the provided jsonObject contains each of the provided keys
     *
     * @param expectedkeys - a list with string keys expected in the json
     *                     response
     * @param actualValue  - the JsonObject that is being checked for jsonvalues
     * @return boolean - does the provided jsonObject contain all of the key value pairs
     */
    public boolean doesJsonObjectContainKeys(List<String> expectedkeys, JsonObject actualValue) {
        boolean pass = (actualValue != null || expectedkeys.isEmpty());
        for (String key : expectedkeys) {
            if (actualValue == null || !actualValue.has(key)) {
                pass = false;
            }
        }
        return pass;
    }

    /**
     * Checks whether the provided jsonObject excludes each of the provided keys
     *
     * @param expectedkeys - a list with string keys expected in the json
     *                     response
     * @param actualValue  - the JsonObject that is being checked for jsonvalues
     * @return boolean - does the provided jsonObject contain all of the key value pairs
     */
    public boolean doesJsonObjectExcludeKeys(List<String> expectedkeys, JsonObject actualValue) {
        boolean pass = true;
        for (String key : expectedkeys) {
            if (actualValue != null && actualValue.has(key)) {
                pass = false;
            }
        }
        return pass;
    }

    /**
     * Checks whether the provided jsonObject contains each of the provided key value pairs
     *
     * @param expectedPairs - a hashmap with string key value pairs expected in the json
     *                      response
     * @param actualValue   - the JsonObject that is being checked for jsonvalues
     * @return boolean - does the provided jsonObject contain all of the key value pairs
     */
    public boolean doesJsonObjectContainPairs(Map<String, Object> expectedPairs, JsonObject actualValue) {
        boolean pass = (actualValue != null);
        for (Map.Entry<String, Object> entry : expectedPairs.entrySet()) {
            if (actualValue != null && actualValue.has(entry.getKey())) {
                Object objectVal = castObject(entry.getValue(), actualValue.get(entry.getKey()));
                if (!entry.getValue().equals(objectVal)) {
                    pass = false;
                }
            } else {
                pass = false;
            }
        }
        return pass;
    }

    /**
     * Checks whether the provided jsonObject contains each of the provided key value pairs
     *
     * @param expectedPairs - a hashmap with string key value pairs expected in the json
     *                      response
     * @param actualValue   - the JsonObject that is being checked for jsonvalues
     * @return boolean - does the provided jsonObject contain all of the key value pairs
     */
    public boolean doesJsonObjectExcludePairs(Map<String, Object> expectedPairs, JsonObject actualValue) {
        boolean pass = true;
        for (Map.Entry<String, Object> entry : expectedPairs.entrySet()) {
            if (actualValue != null && actualValue.has(entry.getKey()) && entry.getValue().equals(castObject(entry.getValue(), actualValue.get(entry.getKey())))) {
                pass = false;
            }
        }
        return pass;
    }

    boolean recordResult(String expected, String actual, boolean success) {
        if (success) {
            this.reporter.pass("", expected, actual);
        } else {
            this.reporter.fail("", expected, actual);
        }
        return success;
    }

    //TODO needs matching, does not contain - consider breaking this into assertEquals, assertConains, assertExcludes (to match web)
}