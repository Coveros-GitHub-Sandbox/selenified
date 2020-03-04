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

import com.coveros.selenified.utilities.Reporter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

import static com.coveros.selenified.utilities.Constants.*;

/**
 * Contains will handle all checks performed on the actual web services
 * calls themselves involving whether or not the response has certain information.
 * These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.3.1
 * @lastupdate 1/6/2020
 */
abstract class Matches extends Check {

    /**
     * Checks the actual response code is matching the expected response
     * code, and writes that out to the output file
     *
     * @param expectedPattern - the expected pattern of the response code
     */
    abstract void code(String expectedPattern);

    /**
     * Checks the actual response code is matching the expected response
     * code, and writes that out to the output file
     *
     * @param expectedPattern - the expected pattern of the response code
     */
    int checkCode(String expectedPattern) {
        int actualCode = this.response.getCode();
        recordResult("Expected to find a response code matching a pattern of: '<i>" + expectedPattern + ENDI,
                "Found a response code of <b>" + actualCode + ENDB, String.valueOf(actualCode).matches(expectedPattern));
        return actualCode;
    }

    /**
     * Checks the actual response json payload contains a key with a value matching the expected
     * value. The jsonKeys should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonKeys        - the crumbs of json object keys leading to the field with the expected value
     * @param expectedPattern - the expected pattern of the  value
     */
    abstract void nestedValue(List<String> jsonKeys, String expectedPattern);

    /**
     * Checks the actual response json payload contains a key with a value matching the expected
     * value. The jsonCrumbs should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonCrumbs      - the crumbs of json object keys leading to the field with the expected value
     * @param expectedPattern - the expected pattern of the value
     */
    String checkNestedValue(List<String> jsonCrumbs, String expectedPattern) {
        JsonElement actualValue = this.response.getObjectData();
        for (String jsonCrumb : jsonCrumbs) {
            if (!(actualValue instanceof JsonObject)) {
                actualValue = null;
                break;
            }
            actualValue = actualValue.getAsJsonObject().get(jsonCrumb);
        }
        String stringValue = String.valueOf(actualValue);
        if (actualValue != null) {
            try {
                stringValue = actualValue.getAsString();
            } catch (UnsupportedOperationException e) {
                log.info(e);
            }
        }
        recordResult(EXPECTED_TO_FIND_A_RESPONSE_OF + STARTI + Reporter.formatHTML(String.join(ARROW, jsonCrumbs)) + ENDI +
                        " matching a pattern of: " + DIV_I + expectedPattern + END_IDIV,
                FOUND + DIV_I + Reporter.formatHTML(GSON.toJson(actualValue)) + END_IDIV, stringValue.matches(expectedPattern));
        return stringValue;
    }

    /**
     * Checks the actual response payload matches the expected
     * response payload, and writes that out to the output file
     *
     * @param expectedPattern - the expected pattern of the response message
     */
    abstract void message(String expectedPattern);

    /**
     * Checks the actual response payload matches the expected
     * response payload, and writes that out to the output file
     *
     * @param expectedPattern - the expected pattern of the response message
     */
    String checkMessage(String expectedPattern) {
        String actualMessage = this.response.getMessage();
        recordResult(EXPECTED_TO_FIND_A_RESPONSE_MATCHING + STARTI + expectedPattern + ENDI,
                FOUND + STARTI + this.response.getMessage() + ENDI, actualMessage != null && actualMessage.matches(expectedPattern));
        return actualMessage;
    }
}
