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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;
import java.util.Set;

import static com.coveros.selenified.element.check.Constants.*;

/**
 * Check will handle all verifications performed on the actual element. These
 * checks are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each verification to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests.
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 2/21/2019
 */
interface Check {

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
     * Determines if the element is present, and if it is not writes a failure out to the detailed log
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
     * Determines if the element is an input element, and if it is not writes a failure out to the detailed log
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
     * Determines if the element is a select element, and if it is not writes a failure out to the detailed log
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
     * Determines if the element is a table element, and if it is not writes a failure out to the detailed log
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
     * Determines if the element is a present, and if it is, it is an input.
     * Writes out the action and expected outcome to the detailed log. Action is only logged
     * if waitFor is greater than 0 (implying we are waiting)
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
     * Determines if the element is a present, and if it is, it is a select.
     * Writes out the action and expected outcome to the detailed log. Action is only logged
     * if waitFor is greater than 0 (implying we are waiting)
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
     * Determines if the element is a present, and if it is, it is a table.
     * Writes out the action and expected outcome to the detailed log. Action is only logged
     * if waitFor is greater than 0 (implying we are waiting)
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
     * Retrieves all attributes of an element, and writes out the action and expected
     * result of checking for one particular attribute. Action is only logged
     * if waitFor is greater than 0 (implying we are waiting). If the element isn't
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
     * Retrieves the value from the element, and writes out the action and value that is
     * being expected. Action is only logged if waitFor is greater than 0 (implying we
     * are waiting). If the element isn't present or an input, an error will
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


    /**
     * Performs a simple check for the element to be present. The provided wait time will be used
     * and the total time the action took will be returned. Nohing will be logged, but this will
     * be used as part of other methods.
     *
     * @param seconds - how many seconds to wait for
     * @return double: the time waited
     */
    default double elementPresent(double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        WebDriverWait wait = new WebDriverWait(getElement().getDriver(), (long) seconds, DEFAULT_POLLING_INTERVAL);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getElement().defineByElement()));
        return Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
    }
}