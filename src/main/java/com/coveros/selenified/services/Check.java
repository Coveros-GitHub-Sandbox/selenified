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

import static com.coveros.selenified.utilities.Constants.*;

/**
 * Check will handle all verifications performed on the actual web services
 * calls themselves. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.2.2
 * @lastupdate 6/25/2019
 */
abstract class Check {

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
     * Checks whether the provided jsonObject contains each of the provided key value pairs
     *
     * @param expectedPairs - a hashmap with string key value pairs expected in the json
     *                      response
     * @param actualValue   - the JsonObject that is being checked for jsonvalues
     * @return boolean - does the provided jsonObject contain all of the key value pairs
     */
    public boolean doesJsonObjectContainPair(Map<String, Object> expectedPairs, JsonObject actualValue) {
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

    ///////////////////////////////////////////////////////////////////
    // checks for our services
    ///////////////////////////////////////////////////////////////////

    /**
     * Checks the actual response code is equals to the expected response
     * code, and writes that out to the output file
     *
     * @param expectedCode - the expected response code
     */
    @SuppressWarnings("squid:S1201")
    abstract void equals(int expectedCode);

    /**
     * Checks the actual response code is equals to the expected response
     * code, and writes that out to the output file
     *
     * @param expectedCode - the expected response code
     */
    int checkEquals(int expectedCode) {
        int actualCode = this.response.getCode();
        if (actualCode == expectedCode) {
            this.reporter.pass("", "Expected to find a response code of <b>" + expectedCode + ENDB, "Found a response code of <b>" + actualCode + ENDB);
        } else {
            this.reporter.fail("", "Expected to find a response code of <b>" + expectedCode + ENDB, "Found a response code of <b>" + actualCode + ENDB);
        }
        return actualCode;
    }

    /**
     * Checks the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json object
     */
    @SuppressWarnings("squid:S1201")
    abstract void equals(JsonObject expectedJson);

    /**
     * Checks the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json object
     */
    JsonObject checkEquals(JsonObject expectedJson) {
        JsonObject actualJson = null;
        if (this.response.getObjectData() != null) {
            actualJson = this.response.getObjectData();
        }
        if (expectedJson.equals(actualJson)) {
            this.reporter.pass("", EXPECTED_TO_FIND + DIV_I + Reporter.formatHTML(GSON.toJson(expectedJson)) + END_IDIV, FOUND + Reporter.formatResponse(this.response));
        } else {
            this.reporter.fail("", EXPECTED_TO_FIND + DIV_I + Reporter.formatHTML(GSON.toJson(expectedJson)) + END_IDIV, FOUND + Reporter.formatResponse(this.response));
        }
        return actualJson;
    }

    /**
     * Checks the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    @SuppressWarnings("squid:S1201")
    abstract void equals(JsonArray expectedJson);

    /**
     * Checks the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    JsonArray checkEquals(JsonArray expectedJson) {
        JsonArray actualJson = null;
        if (this.response.getArrayData() != null) {
            actualJson = this.response.getArrayData();
        }
        if (expectedJson.equals(actualJson)) {
            this.reporter.pass("", EXPECTED_TO_FIND + DIV_I + Reporter.formatHTML(GSON.toJson(expectedJson)) + END_IDIV, FOUND + Reporter.formatResponse(this.response));
        } else {
            this.reporter.fail("", EXPECTED_TO_FIND + DIV_I + Reporter.formatHTML(GSON.toJson(expectedJson)) + END_IDIV, FOUND + Reporter.formatResponse(this.response));
        }
        return actualJson;
    }

    /**
     * Checks the actual response json payload contains a key with a value equal to the expected
     * value. The jsonKeys should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonKeys      - the crumbs of json object keys leading to the field with the expected value
     * @param expectedValue - the expected value
     */
    @SuppressWarnings("squid:S1201")
    abstract void equals(List<String> jsonKeys, Object expectedValue);

    /**
     * Checks the actual response json payload contains a key with a value equal to the expected
     * value. The jsonCrumbs should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonCrumbs    - the crumbs of json object keys leading to the field with the expected value
     * @param expectedValue - the expected value
     */
    Object checkEquals(List<String> jsonCrumbs, Object expectedValue) {
        JsonElement actualValue = this.response.getObjectData();
        for (String jsonCrumb : jsonCrumbs) {
            if (actualValue == null) {
                break;
            }
            if (!(actualValue instanceof JsonObject)) {
                actualValue = null;
                break;
            }
            actualValue = actualValue.getAsJsonObject().get(jsonCrumb);
        }
        Object objectVal = castObject(expectedValue, actualValue);
        if (expectedValue.equals(objectVal)) {
            this.reporter.pass("", EXPECTED_TO_FIND + STARTI + Reporter.formatHTML(String.join(" \uD83E\uDC1A ", jsonCrumbs)) + ENDI + " with value of: " + DIV_I + Reporter.formatHTML(GSON.toJson(expectedValue)) + END_IDIV, FOUND + DIV_I + Reporter.formatHTML(GSON.toJson(objectVal)) + END_IDIV);
        } else {
            this.reporter.fail("", EXPECTED_TO_FIND + STARTI + Reporter.formatHTML(String.join(" \uD83E\uDC1A ", jsonCrumbs)) + ENDI + " with value of: " + DIV_I + Reporter.formatHTML(GSON.toJson(expectedValue)) + END_IDIV, FOUND + DIV_I + Reporter.formatHTML(GSON.toJson(objectVal)) + END_IDIV);
        }
        return objectVal;
    }

    /**
     * Checks the actual response payload is equal to the expected
     * response payload, and writes that out to the output file
     *
     * @param expectedMessage - the expected response message
     */
    @SuppressWarnings("squid:S1201")
    abstract void equals(String expectedMessage);

    /**
     * Checks the actual response payload is equal to the expected
     * response payload, and writes that out to the output file
     *
     * @param expectedMessage - the expected response message
     */
    String checkEquals(String expectedMessage) {
        String actualMessage = null;
        if (this.response.getMessage() != null) {
            actualMessage = this.response.getMessage();
        }
        if (expectedMessage.equals(actualMessage)) {
            this.reporter.pass("", EXPECTED_TO_FIND + STARTI + expectedMessage + ENDI, FOUND + STARTI + this.response.getMessage() + ENDI);
        } else {
            this.reporter.fail("", EXPECTED_TO_FIND + STARTI + expectedMessage + ENDI, FOUND + STARTI + this.response.getMessage() + ENDI);
        }
        return actualMessage;
    }

    /**
     * Checks the actual response json payload contains each of the pair
     * values provided, and writes that to the output file
     *
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    abstract void contains(Map<String, Object> expectedPairs);

    /**
     * Checks the actual response json payload contains each of the pair
     * values provided, and writes that to the output file
     *
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    boolean checkContains(Map<String, Object> expectedPairs) {
        boolean pass = doesJsonObjectContainPair(expectedPairs, this.response.getObjectData());
        if (pass) {
            this.reporter.pass("", EXPECTED_TO_FIND_A_RESPONSE_CONTAINING + DIV_I + Reporter.formatKeyPair(expectedPairs) + END_IDIV, FOUND + Reporter.formatResponse(this.response));
        } else {
            this.reporter.fail("", EXPECTED_TO_FIND_A_RESPONSE_CONTAINING + DIV_I + Reporter.formatKeyPair(expectedPairs) + END_IDIV, FOUND + Reporter.formatResponse(this.response));
        }
        return pass;
    }

    /**
     * Checks the actual response json payload contains to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    abstract void contains(JsonElement expectedJson);

    /**
     * Checks the actual response json payload contains to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    boolean checkContains(JsonElement expectedJson) {
        boolean pass = false;
        if (this.response.getArrayData() != null) {
            pass = this.response.getArrayData().contains(expectedJson);
        }
        if (pass) {
            this.reporter.pass("", EXPECTED_TO_FIND_A_RESPONSE_CONTAINING +
                    DIV_I + Reporter.formatHTML(GSON.toJson(expectedJson)) + END_IDIV, FOUND + Reporter.formatResponse(this.response));
        } else {
            this.reporter.fail("", EXPECTED_TO_FIND_A_RESPONSE_CONTAINING +
                    DIV_I + Reporter.formatHTML(GSON.toJson(expectedJson)) + END_IDIV, FOUND + Reporter.formatResponse(this.response));
        }
        return pass;
    }

    /**
     * Checks the actual response json payload contains a key containing a JsonObject
     * containing each of the pair values provided. The jsonKeys should be passed in
     * as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonKeys      - the crumbs of json object keys leading to the field with the expected value
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    @SuppressWarnings("squid:S1201")
    abstract void contains(List<String> jsonKeys, Map<String, Object> expectedPairs);

    /**
     * Checks the actual response json payload contains a key containing a JsonObject
     * containing each of the pair values provided. The jsonKeys should be passed in
     * as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonCrumbs    - the crumbs of json object keys leading to the field with the expected value
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    boolean checkContains(List<String> jsonCrumbs, Map<String, Object> expectedPairs) {
        JsonObject actualValue = this.response.getObjectData();
        for (String jsonCrumb : jsonCrumbs) {
            if (actualValue == null) {
                break;
            }
            if (actualValue.get(jsonCrumb) instanceof JsonObject) {
                actualValue = actualValue.get(jsonCrumb).getAsJsonObject();
            } else {
                actualValue = null;
                break;
            }
        }
        boolean pass = doesJsonObjectContainPair(expectedPairs, actualValue);
        if (pass) {
            this.reporter.pass("", EXPECTED_TO_FIND + STARTI + Reporter.formatHTML(String.join(" \uD83E\uDC1A ", jsonCrumbs)) + ENDI + " containing: " + DIV_I + Reporter.formatKeyPair(expectedPairs) + END_IDIV, FOUND + DIV_I + Reporter.formatHTML(GSON.toJson(actualValue)) + END_IDIV);
        } else {
            this.reporter.fail("", EXPECTED_TO_FIND + STARTI + Reporter.formatHTML(String.join(" \uD83E\uDC1A ", jsonCrumbs)) + ENDI + " containing: " + DIV_I + Reporter.formatKeyPair(expectedPairs) + END_IDIV, FOUND + DIV_I + Reporter.formatHTML(GSON.toJson(actualValue)) + END_IDIV);
        }
        return pass;
    }

    /**
     * Checks the actual response json payload contains a key containing a JsonElement.
     * The jsonKeys should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonCrumbs   - the crumbs of json object keys leading to the field with the expected value
     * @param expectedJson - the expected response json array
     */
    @SuppressWarnings("squid:S1201")
    abstract void contains(List<String> jsonCrumbs, JsonElement expectedJson);

    /**
     * Checks the actual response json payload contains a key containing a JsonElement.
     * The jsonKeys should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonCrumbs   - the crumbs of json object keys leading to the field with the expected value
     * @param expectedJson - the expected response json array
     */
    boolean checkContains(List<String> jsonCrumbs, JsonElement expectedJson) {
        JsonElement actualValue = this.response.getObjectData();
        for (String jsonCrumb : jsonCrumbs) {
            if (actualValue == null) {
                break;
            }
            if (!(actualValue instanceof JsonObject)) {
                actualValue = null;
                break;
            }
            actualValue = actualValue.getAsJsonObject().get(jsonCrumb);
        }

        boolean pass = false;
        if (actualValue instanceof JsonArray) {
            pass = actualValue.getAsJsonArray().contains(expectedJson);
        }
        if (pass) {
            this.reporter.pass("", EXPECTED_TO_FIND + STARTI + Reporter.formatHTML(String.join(" \uD83E\uDC1A ", jsonCrumbs)) + ENDI + " containing: " + DIV_I + Reporter.formatHTML(GSON.toJson(expectedJson)) + END_IDIV, FOUND + DIV_I + Reporter.formatHTML(GSON.toJson(actualValue)) + END_IDIV);
        } else {
            this.reporter.fail("", EXPECTED_TO_FIND + STARTI + Reporter.formatHTML(String.join(" \uD83E\uDC1A ", jsonCrumbs)) + ENDI + " containing: " + DIV_I + Reporter.formatHTML(GSON.toJson(expectedJson)) + END_IDIV, FOUND + DIV_I + Reporter.formatHTML(GSON.toJson(actualValue)) + END_IDIV);
        }
        return pass;
    }

    /**
     * Checks the actual response json payload contains to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedMessage - the expected response json array
     */
    abstract void contains(String expectedMessage);

    /**
     * Checks the actual response json payload contains to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedMessage - the expected response json array
     */
    boolean checkContains(String expectedMessage) {
        boolean pass = false;
        if (this.response.getMessage() != null) {
            pass = this.response.getMessage().contains(expectedMessage);
        }
        if (pass) {
            this.reporter.pass("", EXPECTED_TO_FIND_A_RESPONSE_CONTAINING + STARTI + expectedMessage + ENDI, FOUND + STARTI + this.response.getMessage() + ENDI);
        } else {
            this.reporter.fail("", EXPECTED_TO_FIND_A_RESPONSE_CONTAINING + STARTI + expectedMessage + ENDI, FOUND + STARTI + this.response.getMessage() + ENDI);
        }
        return pass;
    }
}