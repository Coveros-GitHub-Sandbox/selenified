package com.coveros.selenified.services.check;

import com.coveros.selenified.utilities.Reporter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import static com.coveros.selenified.utilities.Constants.*;

abstract class Excludes extends Check {

    /**
     * Checks the actual response json payload excludes each key provided,
     * and writes that to the output file
     *
     * @param expectedKeys a list with string keys expected in the json
     *                     response
     */
    abstract void keys(List<String> expectedKeys);

    /**
     * Checks the actual response json payload excludes each key provided,
     * and writes that to the output file
     *
     * @param expectedKeys a list with string keys expected in the json
     *                     response
     */
    boolean checkKeys(List<String> expectedKeys) {
        return recordResult(EXPECTED_TO_FIND_A_RESPONSE_EXCLUDING + STARTB + String.join(" ", expectedKeys) + ENDB,
                FOUND + Reporter.formatResponse(this.response), doesJsonObjectExcludeKeys(expectedKeys, this.response.getObjectData()));
    }

    /**
     * Checks the actual response json payload excludes each of the pair
     * values provided, and writes that to the output file
     *
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    abstract void keyValues(Map<String, Object> expectedPairs);

    /**
     * Checks the actual response json payload excludes each of the pair
     * values provided, and writes that to the output file
     *
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    boolean checkKeyValues(Map<String, Object> expectedPairs) {
        return recordResult(EXPECTED_TO_FIND_A_RESPONSE_EXCLUDING + DIV_I + Reporter.formatKeyPair(expectedPairs) + END_IDIV,
                FOUND + Reporter.formatResponse(this.response), doesJsonObjectExcludePairs(expectedPairs, this.response.getObjectData()));
    }

    /**
     * Checks the actual response json payload excludes to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    abstract void value(JsonElement expectedJson);

    /**
     * Checks the actual response json payload excludes to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedJson - the expected response json array
     */
    boolean checkValue(JsonElement expectedJson) {
        boolean pass = true;
        if (this.response.getArrayData() != null) {
            pass = !this.response.getArrayData().contains(expectedJson);
        }
        return recordResult(EXPECTED_TO_FIND_A_RESPONSE_EXCLUDING + DIV_I + Reporter.formatHTML(GSON.toJson(expectedJson)) + END_IDIV,
                FOUND + Reporter.formatResponse(this.response), pass);
    }

    /**
     * Checks the actual response json payload excludes a key excluding a JsonObject
     * excluding each of the pair values provided. The jsonKeys should be passed in
     * as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonKeys      - the crumbs of json object keys leading to the field with the expected value
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    @SuppressWarnings("squid:S1201")
    abstract void nestedKeyValues(List<String> jsonKeys, Map<String, Object> expectedPairs);

    /**
     * Checks the actual response json payload excludes a key excluding a JsonObject
     * excluding each of the pair values provided. The jsonKeys should be passed in
     * as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonCrumbs    - the crumbs of json object keys leading to the field with the expected value
     * @param expectedPairs a hashmap with string key value pairs expected in the json
     *                      response
     */
    boolean checkNestedKeyValues(List<String> jsonCrumbs, Map<String, Object> expectedPairs) {
        JsonObject actualValue = this.response.getObjectData();
        for (String jsonCrumb : jsonCrumbs) {
            if (actualValue != null && actualValue.get(jsonCrumb) instanceof JsonObject) {
                actualValue = actualValue.get(jsonCrumb).getAsJsonObject();
            } else {
                actualValue = null;
                break;
            }
        }
        return recordResult(EXPECTED_TO_FIND_A_RESPONSE_OF + STARTI + Reporter.formatHTML(String.join(ARROW, jsonCrumbs)) +
                        ENDI + EXCLUDING + DIV_I + Reporter.formatKeyPair(expectedPairs) + END_IDIV,
                FOUND + DIV_I + Reporter.formatHTML(GSON.toJson(actualValue)) + END_IDIV,
                doesJsonObjectExcludePairs(expectedPairs, actualValue));
    }

    /**
     * Checks the actual response json payload excludes a key excluding a JsonElement.
     * The jsonKeys should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonCrumbs   - the crumbs of json object keys leading to the field with the expected value
     * @param expectedJson - the expected response json array
     */
    @SuppressWarnings("squid:S1201")
    abstract void nestedValue(List<String> jsonCrumbs, JsonElement expectedJson);

    /**
     * Checks the actual response json payload excludes a key excluding a JsonElement.
     * The jsonKeys should be passed in as crumbs of the keys leading to the field with
     * the expected value. This result will be written out to the output file.
     *
     * @param jsonCrumbs   - the crumbs of json object keys leading to the field with the expected value
     * @param expectedJson - the expected response json array
     */
    boolean checkNestedValue(List<String> jsonCrumbs, JsonElement expectedJson) {
        JsonElement actualValue = this.response.getObjectData();
        for (String jsonCrumb : jsonCrumbs) {
            if (!(actualValue instanceof JsonObject)) {
                actualValue = null;
                break;
            }
            actualValue = actualValue.getAsJsonObject().get(jsonCrumb);
        }

        boolean pass = true;
        if (actualValue instanceof JsonArray) {
            pass = !actualValue.getAsJsonArray().contains(expectedJson);
        }
        return recordResult(EXPECTED_TO_FIND_A_RESPONSE_OF + STARTI + Reporter.formatHTML(String.join(ARROW, jsonCrumbs)) +
                        ENDI + EXCLUDING + DIV_I + Reporter.formatHTML(GSON.toJson(expectedJson)) + END_IDIV,
                FOUND + DIV_I + Reporter.formatHTML(GSON.toJson(actualValue)) + END_IDIV, pass);
    }

    /**
     * Checks the actual response json payload excludes to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedMessage - the expected response json array
     */
    abstract void message(String expectedMessage);

    /**
     * Checks the actual response json payload excludes to the expected json
     * element, and writes that out to the output file
     *
     * @param expectedMessage - the expected response json array
     */
    boolean checkMessage(String expectedMessage) {
        boolean pass = true;
        if (this.response.getMessage() != null) {
            pass = !this.response.getMessage().contains(expectedMessage);
        }
        return recordResult(EXPECTED_TO_FIND_A_RESPONSE_EXCLUDING + STARTI + expectedMessage + ENDI,
                FOUND + STARTI + this.response.getMessage() + ENDI, pass);
    }
}
