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

import java.util.List;

import static com.coveros.selenified.utilities.Constants.DOES_NOT_MATCH_PATTERN;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Assert will handle all verifications performed on the actual web services
 * calls themselves. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they assist in
 * troubleshooting and debugging failing tests.
 *
 * @author Max Saperstone
 * @version 3.3.1
 * @lastupdate 10/24/2019
 */
public class AssertMatches extends Matches {

    /**
     * The default constructor passing in the app and output file
     *
     * @param response - the response from the web services call
     * @param reporter - the file to write all logging out to
     */
    public AssertMatches(Response response, Reporter reporter) {
        this.response = response;
        this.reporter = reporter;
    }

    ///////////////////////////////////////////////////////
    // assertions about the page in general
    ///////////////////////////////////////////////////////

    /**
     * Asserts the actual response code matches the expected response
     * code, and writes that out to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param expectedPattern - the expected pattern of the response code
     */
    @Override
    public void code(String expectedPattern) {
        int code = checkCode(expectedPattern);
        assertTrue("Code Mismatch: code of '" + code + DOES_NOT_MATCH_PATTERN + expectedPattern + "'", String.valueOf(code).matches(expectedPattern));
    }

    /**
     * Asserts the actual response json payload contains a key with a value matching the expected
     * value. The jsonKeys should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param jsonKeys        - the crumbs of json object keys leading to the field with the expected value
     * @param expectedPattern - the expected pattern of the value
     */
    @Override
    public void nestedValue(List<String> jsonKeys, String expectedPattern) {
        String nestedValue = checkNestedValue(jsonKeys, expectedPattern);
        assertTrue("JsonElement Response Mismatch: nested value of '" + nestedValue + DOES_NOT_MATCH_PATTERN + expectedPattern + "'", nestedValue.matches(expectedPattern));
    }

    /**
     * Asserts the actual response payload matches the expected
     * response payload, and writes that out to the output file. If this fails, the code will
     * immediately exit, and record the error.
     *
     * @param expectedPattern - the expected pattern of the message
     */
    @Override
    public void message(String expectedPattern) {
        String message = checkMessage(expectedPattern);
        assertTrue("Response Message Mismatch: message of '" + message + DOES_NOT_MATCH_PATTERN + expectedPattern, message!= null && message.matches(expectedPattern));
    }
}