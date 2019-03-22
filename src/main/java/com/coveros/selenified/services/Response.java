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

import java.util.Map;

/**
 * A class designed to hold data provided from the HTTP calls.
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 3/20/2019
 */
public class Response {
    private static final String EXPECTED_TO_FIND = "Expected to find a response of: ";
    private int code;
    private JsonObject object = null;
    private JsonArray array = null;
    private String message = null;

    // this will be the name of the file we write all commands out to
    private Reporter reporter;

    // constants
    private static final String FOUND = "Found a response of: ";
    private static final String ENDI = "</i>'";

    // a basic response setup, just with an output file to write information to
    public Response(Reporter reporter) {
        this.reporter = reporter;
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

    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
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
        if (code == expectedCode) {
            reporter.pass("", "Expected to find a response code of <b>" + expectedCode + "</b>", "Found a response code of <b>" + code + "</b>");
        } else {
            reporter.fail("", "Expected to find a response code of <b>" + expectedCode + "</b>", "Found a response code of <b>" + code + "</b>");
        }
    }

    /**
     * Verifies the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json object
     */
    public void assertEquals(JsonObject expectedJson) {
        boolean pass = false;
        if (object != null) {
            pass = object.equals(expectedJson);
        }
        if (pass) {
            reporter.pass("", EXPECTED_TO_FIND + Reporter.formatResponse(new Response(0, expectedJson, null)), FOUND + Reporter.formatResponse(this));
        } else {
            reporter.fail("", EXPECTED_TO_FIND + Reporter.formatResponse(new Response(0, expectedJson, null)), FOUND + Reporter.formatResponse(this));
        }
    }

    /**
     * Verifies the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedArray - the expected response json array
     */
    public void assertEquals(JsonArray expectedArray) {
        boolean pass = false;
        if (array != null) {
            pass = array.equals(expectedArray);
        }
        if (pass) {
            reporter.pass("", EXPECTED_TO_FIND + Reporter.formatResponse(new Response(0, expectedArray, null)), FOUND + Reporter.formatResponse(this));
        } else {
            reporter.fail("", EXPECTED_TO_FIND + Reporter.formatResponse(new Response(0, expectedArray, null)), FOUND + Reporter.formatResponse(this));
        }
    }

    /**
     * Verifies the actual response payload is equal to the expected
     * response payload, and writes that out to the output file
     *
     * @param expectedMessage - the expected response message
     */
    public void assertEquals(String expectedMessage) {
        boolean pass = false;
        if (message != null) {
            pass = message.equals(expectedMessage);
        }
        if (pass) {
            reporter.pass("", "Expected to find a response of: '<i>" + expectedMessage + ENDI, FOUND + "'<i>" + message + ENDI);
        } else {
            reporter.fail("", "Expected to find a response of: '<i>" + expectedMessage + ENDI, FOUND + "'<i>" + message + ENDI);
        }
    }

    /**
     * Casts an unknown JsonElement into an object, based on the expected behavior of it
     *
     * @param known   - what value are we expecting
     * @param unknown - what JsonElement are we trying to cast
     * @return Object - the expected object, properly cast
     */
    public Object castObject(Object known, JsonElement unknown) {
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
     * Verifies the actual response json payload contains each of the pair
     * values provided, and writes that to the output file
     *
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    public void assertContains(Map<String, Object> expectedPairs) {
        StringBuilder expectedString = new StringBuilder();
        boolean pass = (object != null);
        for (Map.Entry<String, Object> entry : expectedPairs.entrySet()) {
            expectedString.append("<div>");
            expectedString.append(entry.getKey());
            expectedString.append(" : ");
            expectedString.append(Reporter.formatHTML(String.valueOf(entry.getValue())));
            expectedString.append("</div>");
            if (object != null && object.has(entry.getKey())) {
                Object objectVal = castObject(entry.getValue(), object.get(entry.getKey()));
                if (!entry.getValue().equals(objectVal)) {
                    pass = false;
                }
            } else {
                pass = false;
            }
        }
        if (pass) {
            reporter.pass("", "Expected to find a response containing: <div><i>" + expectedString.toString() + "</i></div>", FOUND + Reporter.formatResponse(this));
        } else {
            reporter.fail("", "Expected to find a response containing: <div><i>" + expectedString.toString() + "</i></div>", FOUND + Reporter.formatResponse(this));
        }
    }

    /**
     * Verifies the actual response json payload contains to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    public void assertContains(JsonElement expectedJson) {
        boolean pass = false;
        if (array != null) {
            pass = array.contains(expectedJson);
        }
        if (pass) {
            reporter.pass("", "Expected to find a response containing:" +
                    Reporter.formatResponse(new Response(0, expectedJson.getAsJsonObject(), null)), FOUND + Reporter.formatResponse(this));
        } else {
            reporter.fail("", "Expected to find a response containing:" +
                    Reporter.formatResponse(new Response(0, expectedJson.getAsJsonObject(), null)), FOUND + Reporter.formatResponse(this));
        }
    }

    /**
     * Verifies the actual response json payload contains to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedMessage - the expected response json array
     */
    public void assertContains(String expectedMessage) {
        boolean pass = false;
        if (message != null) {
            pass = message.contains(expectedMessage);
        }
        if (pass) {
            reporter.pass("", "Expected to find a response containing: '<i>" + expectedMessage + ENDI, FOUND + "'<i>" + message + ENDI);
        } else {
            reporter.fail("", "Expected to find a response containing: '<i>" + expectedMessage + ENDI, FOUND + "'<i>" + message + ENDI);
        }
    }
}