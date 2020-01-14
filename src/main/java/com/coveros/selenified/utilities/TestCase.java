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

package com.coveros.selenified.utilities;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * Manages the test cases, including naming conventions and formatting
 *
 * @author Max Saperstone
 * @version 3.3.1
 * @lastupdate 6/28/2019
 */
public class TestCase {

    private TestCase() {
    }

    //constants
    private static final int MAXFILENAMELENGTH = 200;

    /**
     * Generates a random string of alpha-numeric characters
     *
     * @param length the length of the random string
     * @return String: random string of characters
     */
    public static String getRandomString(int length) {
        if (length <= 0) {
            return "";
        }
        String stringChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(stringChars.charAt(rnd.nextInt(stringChars.length())));
        }
        return sb.toString();
    }

    /**
     * Removes all non alphanumeric characters from a provided string
     *
     * @param value - the string to cleanup
     * @return String: the provided string with all alphanumeric characters
     * removed
     */
    public static String removeNonWordCharacters(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("[^a-zA-Z0-9]+", "");
    }

    /**
     * Determines the unique test name, based on the parameters passed in
     *
     * @param method       - the method under test to extract the name from
     * @param dataProvider - an array of objects being passed to the test as data
     *                     providers
     * @return String: a unique name
     */
    public static String getTestName(Method method, Object... dataProvider) {
        //TODO - look at making use of new setTestName and getFactoryParameters
        return getTestName(method.getDeclaringClass().getName(), method.getName(), dataProvider);
    }

    /**
     * Determines the unique test name, based on the parameters passed in
     *
     * @param className    - the class name of the test method as a string
     * @param methodName   - the method name of the test as a string
     * @param dataProvider - an array of objects being passed to the test as data
     *                     providers
     * @return String: a unique name
     */
    @SuppressWarnings("squid:S2116")
    public static String getTestName(String className, String methodName, Object... dataProvider) {
        StringBuilder testName = new StringBuilder(className + "." + methodName);
        if (dataProvider != null && dataProvider.length > 0) {
            addParameters(testName, dataProvider);
            if (testName.toString().length() > MAXFILENAMELENGTH) {
                testName = new StringBuilder(className + "." + methodName + dataProvider.toString().split(";")[1]);
                // purposefully using toString on object to obtain unique random hash
            }
        }
        return testName.toString();
    }

    /**
     * Loops through dataProviders, and adds string value of each one to test case name
     *
     * @param testName     - StringBuilder containing the test case name
     * @param dataProvider - an array of objects being passed to the test as data
     *                     providers
     */
    static void addParameters(StringBuilder testName, Object[] dataProvider) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object data : dataProvider) {
            if (data == null) {
                continue;
            }
            stringBuilder.append(Reporter.capitalizeFirstLetters(removeNonWordCharacters(data.toString())));
        }
        if(stringBuilder.length() > 0) {
            testName.append("WithOption").append(stringBuilder);
        }
    }
}
