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

package com.coveros.selenified.element;

import com.coveros.selenified.OutputFile;
import com.coveros.selenified.OutputFile.Success;

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
    String OPTION = " has the option of <b>";
    String EXPECTED = "Expected to find ";
    String CLASS = "class";

    String NOTINPUT = " is not an input on the page";

    String VALUE = " has the value of <b>";
    String TEXT = " has the text of <b>";
    String HASVALUE = " contains the value of <b>";
    String HASNTVALUE = " does not contain the value of <b>";
    String HASTEXT = " contains the text of <b>";
    String HASNTTEXT = " does not contain the text of <b>";
    String ONLYVALUE = ", only the values <b>";
    String CLASSVALUE = " has a class value of <b>";

    String NOTSELECT = " is not a select on the page";
    String NOTTABLE = " is not a table on the page";

    /**
     * Retrieves the output file that we write all details out to
     *
     * @return OutputFile
     */
    OutputFile getOutputFile();

    /**
     * Retrieves the driver that is used for all selenium actions
     *
     * @return App
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
    default boolean isPresent() {
        if (!getElement().is().present()) {
            getElement().waitFor().present();
            return getElement().is().present();
        }
        return true;
    }

    /**
     * Determines if the element is a select element
     *
     * @return Boolean: whether the element is an select or not
     */
    default boolean isSelect() {
        if (!getElement().is().select()) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTSELECT, Success.FAIL);
            getOutputFile().addError();
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a table element
     *
     * @return Boolean: whether the element is an table or not
     */
    default boolean isTable() {
        if (!getElement().is().table()) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTTABLE, Success.FAIL);
            getOutputFile().addError();
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is a present, and if it is, it is a select
     *
     * @param expected - the expected outcome
     * @return Boolean: whether the element is a select or not
     */
    default boolean isPresentSelect(String expected) {
        // wait for the element
        if (!isPresent()) {
            return true;
        }
        getOutputFile().recordExpected(expected);
        // verify this is a select element
        return !isSelect();
    }

    /**
     * Determines if the element is a present, and if it is, it is a table
     *
     * @param expected - the expected outcome
     * @return Boolean: whether the element is an table or not
     */
    default boolean isPresentTable(String expected) {
        // wait for the element
        if (!isPresent()) {
            return true;
        }
        getOutputFile().recordExpected(expected);
        // verify this is a select element
        return !isTable();
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
    default String[] getAttributes(String attribute, String expected) {
        // wait for the element
        if (!isPresent()) {
            return null;    // returning an empty array could be confused with no attributes
        }
        getOutputFile().recordExpected(EXPECTED + getElement().prettyOutput() + " " + expected + " attribute <b>" + attribute + "</b>");
        // check our attributes
        Map<String, String> attributes = getElement().get().allAttributes();
        if (attributes == null) {
            getOutputFile().recordActual("Unable to assess the attributes of " + getElement().prettyOutputEnd(), Success.FAIL);
            getOutputFile().addError();
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
    default String getValue(String value, String expected) {
        // wait for the element
        if (!isPresent()) {
            return null;
        }
        // getOutputFile().record the element
        getOutputFile().recordExpected(EXPECTED + getElement().prettyOutput() + expected + value + "</b>");
        // verify this is an input element
        if (!getElement().is().input()) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + NOTINPUT, Success.FAIL);
            getOutputFile().addError();
            return null;
        }
        // check for the object to the present on the page
        return getElement().get().value();
    }
}