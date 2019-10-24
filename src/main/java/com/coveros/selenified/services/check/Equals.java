package com.coveros.selenified.services.check;

import com.coveros.selenified.utilities.Reporter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

import static com.coveros.selenified.utilities.Constants.*;

abstract class Equals extends Check {

    /**
     * Checks the actual response code is equals to the expected response
     * code, and writes that out to the output file
     *
     * @param expectedCode - the expected response code
     */
    @SuppressWarnings("squid:S1201")
    abstract void code(int expectedCode);

    /**
     * Checks the actual response code is equals to the expected response
     * code, and writes that out to the output file
     *
     * @param expectedCode - the expected response code
     */
    int checkCode(int expectedCode) {
        int actualCode = this.response.getCode();
        recordResult("Expected to find a response code of <b>" + expectedCode + ENDB,
                "Found a response code of <b>" + actualCode + ENDB, actualCode == expectedCode);
        return actualCode;
    }

    /**
     * Checks the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json object
     */
    @SuppressWarnings("squid:S1201")
    abstract void objectData(JsonObject expectedJson);

    /**
     * Checks the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json object
     */
    JsonObject checkObjectData(JsonObject expectedJson) {
        JsonObject actualJson = this.response.getObjectData();
        recordResult(EXPECTED_TO_FIND_A_RESPONSE_OF + DIV_I + Reporter.formatHTML(GSON.toJson(expectedJson)) + END_IDIV,
                FOUND + Reporter.formatResponse(this.response), expectedJson.equals(actualJson));
        return actualJson;
    }

    /**
     * Checks the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    @SuppressWarnings("squid:S1201")
    abstract void arrayData(JsonArray expectedJson);

    /**
     * Checks the actual response json payload is equal to the expected
     * response json payload, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    JsonArray checkArrayData(JsonArray expectedJson) {
        JsonArray actualJson = this.response.getArrayData();
        recordResult(EXPECTED_TO_FIND_A_RESPONSE_OF + DIV_I + Reporter.formatHTML(GSON.toJson(expectedJson)) + END_IDIV,
                FOUND + Reporter.formatResponse(this.response), expectedJson.equals(actualJson));
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
    abstract void nestedValue(List<String> jsonKeys, Object expectedValue);

    /**
     * Checks the actual response json payload contains a key with a value equal to the expected
     * value. The jsonCrumbs should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonCrumbs    - the crumbs of json object keys leading to the field with the expected value
     * @param expectedValue - the expected value
     */
    Object checkNestedValue(List<String> jsonCrumbs, Object expectedValue) {
        JsonElement actualValue = this.response.getObjectData();
        for (String jsonCrumb : jsonCrumbs) {
            if (!(actualValue instanceof JsonObject)) {
                actualValue = null;
                break;
            }
            actualValue = actualValue.getAsJsonObject().get(jsonCrumb);
        }
        Object objectVal = castObject(expectedValue, actualValue);
        recordResult(EXPECTED_TO_FIND_A_RESPONSE_OF + STARTI + Reporter.formatHTML(String.join(ARROW, jsonCrumbs)) + ENDI +
                        " with value of: " + DIV_I + Reporter.formatHTML(GSON.toJson(expectedValue)) + END_IDIV,
                FOUND + DIV_I + Reporter.formatHTML(GSON.toJson(objectVal)) + END_IDIV, expectedValue.equals(objectVal));
        return objectVal;
    }

    /**
     * Checks the actual response payload is equal to the expected
     * response payload, and writes that out to the output file
     *
     * @param expectedMessage - the expected response message
     */
    @SuppressWarnings("squid:S1201")
    abstract void message(String expectedMessage);

    /**
     * Checks the actual response payload is equal to the expected
     * response payload, and writes that out to the output file
     *
     * @param expectedMessage - the expected response message
     */
    String checkMessage(String expectedMessage) {
        String actualMessage = this.response.getMessage();
        recordResult(EXPECTED_TO_FIND_A_RESPONSE_OF + STARTI + expectedMessage + ENDI,
                FOUND + STARTI + this.response.getMessage() + ENDI, expectedMessage.equals(actualMessage));
        return actualMessage;
    }

    /**
     * Checks the actual response payload contains the number of elements
     * in an array as expected, and writes that out to the output file
     *
     * @param expectedSize - the expected array size
     */
    abstract void arraySize(int expectedSize);

    /**
     * Checks the actual response payload contains the number of elements
     * in an array as expected, and writes that out to the output file
     *
     * @param expectedSize - the expected array size
     */
    int checkArraySize(int expectedSize) {
        int actualSize = -1;
        if (this.response.getArrayData() != null) {
            actualSize = this.response.getArrayData().size();
        }
        String size = " which has a size of " + STARTI + actualSize + ENDI;
        if (actualSize == -1) {
            size = " which isn't an array";
        }
        recordResult("Expected to find a response to be an array with size of " + STARTI + expectedSize + ENDI,
                FOUND + Reporter.formatResponse(this.response) + size, actualSize == expectedSize);
        return actualSize;
    }

    /**
     * Checks the actual response payload contains the number of elements
     * in an array as expected, and writes that out to the output file
     *
     * @param expectedSize - the expected array size
     */
    abstract void nestedArraySize(List<String> jsonKeys, int expectedSize);

    /**
     * Checks the actual response payload contains a key with a value of the number of elements
     * in an array as expected, and writes that out to the output file
     *
     * @param expectedSize - the expected array size
     */
    int checkNestedArraySize(List<String> jsonCrumbs, int expectedSize) {
        JsonElement actualValue = this.response.getObjectData();
        for (String jsonCrumb : jsonCrumbs) {
            if (!(actualValue instanceof JsonObject)) {
                actualValue = null;
                break;
            }
            actualValue = actualValue.getAsJsonObject().get(jsonCrumb);
        }

        int actualSize = -1;
        if (actualValue instanceof JsonArray) {
            actualSize = actualValue.getAsJsonArray().size();
        }
        String size = " which has a size of " + STARTI + actualSize + ENDI;
        if (actualSize == -1) {
            size = " which isn't an array";
        }
        recordResult(EXPECTED_TO_FIND_A_RESPONSE_OF + STARTI + Reporter.formatHTML(String.join(ARROW, jsonCrumbs)) +
                        ENDI + " to be an array with size of " + STARTI + expectedSize + ENDI,
                FOUND + DIV_I + Reporter.formatHTML(GSON.toJson(actualValue)) + END_IDIV + size,
                actualSize == expectedSize);
        return actualSize;
    }
}
