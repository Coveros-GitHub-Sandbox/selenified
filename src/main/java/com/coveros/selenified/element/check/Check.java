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

package com.coveros.selenified.element.check;

import com.coveros.selenified.OutputFile;
import com.coveros.selenified.OutputFile.Success;
import com.coveros.selenified.element.Element;

import java.util.Map;
import java.util.Set;

/**
 * Assert will handle all verifications performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each verification to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests.
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 2/21/2019
 */
interface Check {

    // constants
    String HAS_OPTION = " has the option of <b>";
    String CLASS = "class";

    String HAS_VALUE = " has the value of <b>";
    String HAS_TEXT = " has the text of <b>";
    String CONTAINS_VALUE = " contains the value of <b>";
    String EXCLUDES_VALUE = " does not contain the value of <b>";
    String CONTAINS_TEXT = " contains the text of <b>";
    String EXCLUDES_TEXT = " does not contain the text of <b>";
    String ONLY_VALUE = ", only the values <b>";
    String CLASS_VALUE = " has a class value of <b>";

    String IS_NOT_PRESENT = " is not present on the page";
    String IS_NOT_INPUT = " is not an input on the page";
    String IS_NOT_SELECT = " is not a select on the page";
    String IS_NOT_TABLE = " is not a table on the page";

    /**
     * Retrieves the output file that we write all details out to
     *
     * @return OutputFile
     */
    OutputFile getOutputFile();

    /**
     * Retrieves the element that is used for all selenium actions and checks
     *
     * @return Element
     */
    Element getElement();

    ///////////////////////////////////////////////////////
    // assertions about the element in general
    ///////////////////////////////////////////////////////

    /**
     * Determines if the element is present, and if it is not, waits up to the
     * default time (5 seconds) for the element
     *
     * @return Boolean: whether the element is present or not
     */
    default boolean isPresent(double waitFor) {
        if (!getElement().is().present()) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_PRESENT, waitFor, Success.FAIL);
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is an input element
     *
     * @return Boolean: whether the element is an input or not
     */
    default boolean isInput(double waitFor) {
        if (!getElement().is().input()) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_INPUT, waitFor, Success.FAIL);
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a select element
     *
     * @return Boolean: whether the element is an select or not
     */
    default boolean isSelect(double waitFor) {
        if (!getElement().is().select()) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_SELECT, waitFor, Success.FAIL);
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a table element
     *
     * @return Boolean: whether the element is an table or not
     */
    default boolean isTable(double waitFor) {
        if (!getElement().is().table()) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + IS_NOT_TABLE, waitFor, Success.FAIL);
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a present, and if it is, it is an input
     *
     * @param expected - the expected outcome
     * @return Boolean: whether the element is a select or not
     */
    default boolean isPresentInput(String expected, double waitFor) {
        getOutputFile().recordAction(expected, waitFor);
        // verify this is a select element
        return (isPresent(waitFor) && isInput(waitFor));
    }

    /**
     * Determines if the element is a present, and if it is, it is a select
     *
     * @param expected - the expected outcome
     * @return Boolean: whether the element is a select or not
     */
    default boolean isPresentSelect(String expected, double waitFor) {
        getOutputFile().recordAction(expected, waitFor);
        // verify this is a select element
        return (isPresent(waitFor) && isSelect(waitFor));
    }

    /**
     * Determines if the element is a present, and if it is, it is a table
     *
     * @param expected - the expected outcome
     * @return Boolean: whether the element is an table or not
     */
    default boolean isPresentTable(String expected, double waitFor) {
        getOutputFile().recordAction(expected, waitFor);
        // verify this is a select element
        return (isPresent(waitFor) && isTable(waitFor));
    }

    /**
     * Retrieves all attributes of an element, and writes out the expected
     * result of checking for one particular attribute. If the element isn't
     * present, or the attributes can't be determined an error will be logged
     * and null will be returned
     *
     * @param attribute - the attribute to check for
     * @param expected  - is the attribute expected to be present, or not present
     * @return String[]: the list of all attributes from the element;
     */
    @SuppressWarnings("squid:S1168")
    default String[] getAttributes(String attribute, String expected, double waitFor) {
        getOutputFile().recordAction(getElement().prettyOutput() + " " + expected + " attribute <b>" + attribute + "</b>", waitFor);
        // check our attributes
        Map<String, String> attributes = getElement().get().allAttributes();
        if (attributes == null) {
            return null;    // returning an empty array could be confused with no attributes
        }
        Set<String> keys = attributes.keySet();
        return keys.toArray(new String[keys.size()]);
    }

    /**
     * Retrieves the value from the element, and writes out the value that is
     * being expected. If the element isn't present or an input, an error will
     * be logged and null will be returned
     *
     * @param value    the expected value of the element
     * @param expected - is the attribute expected to be present, or not present
     * @return String: the actual value from the input element
     */
    default String getValue(String value, String expected, double waitFor) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + expected + value + "</b>", waitFor);
        // verify this is an input element
        if (!getElement().is().input()) {
            return null;        //return null, as it's not an input element
        }
        // check for the object to the present on the page
        return getElement().get().value();
    }
}