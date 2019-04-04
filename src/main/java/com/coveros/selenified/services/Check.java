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
import com.google.gson.*;

import java.util.Map;

import static com.coveros.selenified.utilities.Constants.*;
import static com.coveros.selenified.utilities.Reporter.DIV_I;
import static com.coveros.selenified.utilities.Reporter.END_IDIV;

/**
 * Check will handle all verifications performed on the actual web services
 * calls themselves. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 3/20/2019
 */
public interface Check {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Retrieves the output file that we write all details out to
     *
     * @return Reporter
     */
    Reporter getReporter();

    /**
     * Retrieves the web services response  object, with all details
     *
     * @return Response
     */
    Response getResponse();

    /**
     * Casts an unknown JsonElement into an object, based on the expected behavior of it
     *
     * @param known   - what value are we expecting
     * @param unknown - what JsonElement are we trying to cast
     * @return Object - the expected object, properly cast
     */
    default Object castObject(Object known, JsonElement unknown) {
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

    ///////////////////////////////////////////////////////////////////
    // checks for our services
    ///////////////////////////////////////////////////////////////////

    /**
     * Checks the actual response code is equals to the expected response
     * code, and writes that out to the output file
     *
     * @param expectedCode - the expected response code
     */
    void equals(int expectedCode);

    /**
     * Checks the actual response code is equals to the expected response
     * code, and writes that out to the output file
     *
     * @param expectedCode - the expected response code
     */
    default int checkEquals(int expectedCode) {
        int actualCode = getResponse().getCode();
        if (actualCode == expectedCode) {
            getReporter().pass("", "Expected to find a response code of <b>" + expectedCode + "</b>", "Found a response code of <b>" + actualCode + "</b>");
        } else {
            getReporter().fail("", "Expected to find a response code of <b>" + expectedCode + "</b>", "Found a response code of <b>" + actualCode + "</b>");
        }
        return actualCode;
    }

    /**
     * Checks the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json object
     */
    void equals(JsonObject expectedJson);

    /**
     * Checks the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json object
     */
    default JsonObject checkEquals(JsonObject expectedJson) {
        boolean pass = false;
        JsonObject actualJson = null;
        if (getResponse().getObjectData() != null) {
            actualJson = getResponse().getObjectData();
            pass = actualJson.equals(expectedJson);
        }
        if (pass) {
            getReporter().pass("", EXPECTED_TO_FIND + DIV_I + Reporter.formatHTML(gson.toJson(expectedJson)) + END_IDIV, FOUND + Reporter.formatResponse(getResponse()));
        } else {
            getReporter().fail("", EXPECTED_TO_FIND + DIV_I + Reporter.formatHTML(gson.toJson(expectedJson)) + END_IDIV, FOUND + Reporter.formatResponse(getResponse()));
        }
        return actualJson;
    }

    /**
     * Checks the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    void equals(JsonArray expectedJson);

    /**
     * Checks the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    default JsonArray checkEquals(JsonArray expectedJson) {
        boolean pass = false;
        JsonArray actualJson = null;
        if (getResponse().getArrayData() != null) {
            actualJson = getResponse().getArrayData();
            pass = actualJson.equals(expectedJson);
        }
        if (pass) {
            getReporter().pass("", EXPECTED_TO_FIND + DIV_I + Reporter.formatHTML(gson.toJson(expectedJson)) + END_IDIV, FOUND + Reporter.formatResponse(getResponse()));
        } else {
            getReporter().fail("", EXPECTED_TO_FIND + DIV_I + Reporter.formatHTML(gson.toJson(expectedJson)) + END_IDIV, FOUND + Reporter.formatResponse(getResponse()));
        }
        return actualJson;
    }

    /**
     * Checks the actual response payload is equal to the expected
     * response payload, and writes that out to the output file
     *
     * @param expectedMessage - the expected response message
     */
    void equals(String expectedMessage);

    /**
     * Checks the actual response payload is equal to the expected
     * response payload, and writes that out to the output file
     *
     * @param expectedMessage - the expected response message
     */
    default String checkEquals(String expectedMessage) {
        boolean pass = false;
        String actualMessage = null;
        if (getResponse().getMessage() != null) {
            actualMessage = getResponse().getMessage();
            pass = actualMessage.equals(expectedMessage);
        }
        if (pass) {
            getReporter().pass("", EXPECTED_TO_FIND + STARTI + expectedMessage + ENDI, FOUND + STARTI + getResponse().getMessage() + ENDI);
        } else {
            getReporter().fail("", EXPECTED_TO_FIND + STARTI + expectedMessage + ENDI, FOUND + STARTI + getResponse().getMessage() + ENDI);
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
    void contains(Map<String, Object> expectedPairs);

    /**
     * Checks the actual response json payload contains each of the pair
     * values provided, and writes that to the output file
     *
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    default boolean checkContains(Map<String, Object> expectedPairs) {
        boolean pass = (getResponse().getObjectData() != null);
        for (Map.Entry<String, Object> entry : expectedPairs.entrySet()) {
            if (getResponse().getObjectData() != null && getResponse().getObjectData().has(entry.getKey())) {
                Object objectVal = castObject(entry.getValue(), getResponse().getObjectData().get(entry.getKey()));
                if (!entry.getValue().equals(objectVal)) {
                    pass = false;
                }
            } else {
                pass = false;
            }
        }
        if (pass) {
            getReporter().pass("", EXPECTED_TO_FIND_A_RESPONSE_CONTAINING + DIV_I + Reporter.formatKeyPair(expectedPairs) + END_IDIV, FOUND + Reporter.formatResponse(getResponse()));
        } else {
            getReporter().fail("", EXPECTED_TO_FIND_A_RESPONSE_CONTAINING + DIV_I + Reporter.formatKeyPair(expectedPairs) + END_IDIV, FOUND + Reporter.formatResponse(getResponse()));
        }
        return pass;
    }

    /**
     * Checks the actual response json payload contains to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    void contains(JsonElement expectedJson);

    /**
     * Checks the actual response json payload contains to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    default boolean checkContains(JsonElement expectedJson) {
        boolean pass = false;
        if (getResponse().getArrayData() != null) {
            pass = getResponse().getArrayData().contains(expectedJson);
        }
        if (pass) {
            getReporter().pass("", EXPECTED_TO_FIND_A_RESPONSE_CONTAINING +
                    DIV_I + Reporter.formatHTML(gson.toJson(expectedJson)) + END_IDIV, FOUND + Reporter.formatResponse(getResponse()));
        } else {
            getReporter().fail("", EXPECTED_TO_FIND_A_RESPONSE_CONTAINING +
                    DIV_I + Reporter.formatHTML(gson.toJson(expectedJson)) + END_IDIV, FOUND + Reporter.formatResponse(getResponse()));
        }
        return pass;
    }

    /**
     * Checks the actual response json payload contains to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedMessage - the expected response json array
     */
    void contains(String expectedMessage);

    /**
     * Checks the actual response json payload contains to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedMessage - the expected response json array
     */
    default boolean checkContains(String expectedMessage) {
        boolean pass = false;
        if (getResponse().getMessage() != null) {
            pass = getResponse().getMessage().contains(expectedMessage);
        }
        if (pass) {
            getReporter().pass("", EXPECTED_TO_FIND_A_RESPONSE_CONTAINING + STARTI + expectedMessage + ENDI, FOUND + STARTI + getResponse().getMessage() + ENDI);
        } else {
            getReporter().fail("", EXPECTED_TO_FIND_A_RESPONSE_CONTAINING + STARTI + expectedMessage + ENDI, FOUND + STARTI + getResponse().getMessage() + ENDI);
        }
        return pass;
    }
}