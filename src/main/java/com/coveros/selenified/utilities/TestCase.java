/*
 * Copyright 2018 Coveros, Inc.
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
 * @version 3.0.4
 * @lastupdate 1/12/2019
 */
public class TestCase {

    private TestCase() {}

    //constants
    private static final int MAXFILENAMELENGTH = 200;
    private static final String PUBLIC = "public";

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
        String className;
        String packageName = "";
        if (method.getDeclaringClass().getPackage() != null) {
            className = method.getDeclaringClass().toString().substring(6).split("\\.")[1];
            packageName = method.getDeclaringClass().toString().substring(6).split("\\.")[0];
        } else {
            className = method.getDeclaringClass().toString().substring(6);
        }
        return getTestName(packageName, className, method.getName(), dataProvider);
    }

    /**
     * Determines if a dataProvider was actually provided, or just ITestContext
     * or method data is present
     *
     * @param dataProvider - the object array to check - is it truly a data provider?
     * @return Boolean: is the provided object array a data provider?
     */
    private static boolean isRealDataProvider(Object... dataProvider) {
        return dataProvider != null && dataProvider.length > 0 && dataProvider[0] != null &&
                !dataProvider[0].toString().startsWith(PUBLIC);
    }

    /**
     * Determines the unique test name, based on the parameters passed in
     *
     * @param packageName  - the package name of the test method as a string
     * @param className    - the class name of the test method as a string
     * @param methodName   - the method name of the test as a string
     * @param dataProvider - an array of objects being passed to the test as data
     *                     providers
     * @return String: a unique name
     */
    @SuppressWarnings("squid:S2116")
    public static String getTestName(String packageName, String className, String methodName, Object... dataProvider) {
        StringBuilder testName;
        if ("".equals(packageName)) {
            testName = new StringBuilder(className + "_" + methodName);
        } else {
            testName = new StringBuilder(packageName + "_" + className + "_" + methodName);
        }
        String currentName = testName.toString();
        if (isRealDataProvider(dataProvider)) {
            testName.append("WithOption");
            for (Object data : dataProvider) {
                if (data == null || data.toString().startsWith(PUBLIC)) {
                    break;
                }
                testName.append(capitalizeFirstLetters(removeNonWordCharacters(data.toString())));
            }
            currentName = testName.toString();
            if (currentName.length() > MAXFILENAMELENGTH) {
                if ("".equals(packageName)) {
                    currentName = className + "_" + methodName + dataProvider.toString().split(";")[1];
                    // purposefully using toString on object to obtain unique random hash
                } else {
                    currentName =
                            packageName + "_" + className + "_" + methodName + dataProvider.toString().split(";")[1];
                    // purposefully using toString on object to obtain unique random hash
                }
            }
        }
        return currentName;
    }

    /**
     * Capitalizes the first letter of each word in the provided string
     *
     * @param word - the string to be capitalized on
     * @return String: the new string
     */
    public static String capitalizeFirstLetters(String word) {
        if (word == null) {
            return null;
        }
        String out = "";
        for (int i = 0; i < word.length(); i++) {
            if (i == 0) {
                // Capitalize the first letter of the string.
                out = String.format("%s%s", Character.toUpperCase(word.charAt(0)), word.substring(1));
            }
            // Is this character a non-letter? If so
            // then this is probably a word boundary so let's capitalize
            // the next character in the sequence.
            if (!Character.isLetter(out.charAt(i)) && (i + 1) < out.length()) {
                out = String.format("%s%s%s", out.subSequence(0, i + 1), Character.toUpperCase(out.charAt(i + 1)),
                        out.substring(i + 2));
            }
        }
        return out;
    }
}
