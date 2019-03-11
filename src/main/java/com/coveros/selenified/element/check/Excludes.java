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

import com.coveros.selenified.OutputFile.Success;

import java.util.Arrays;

import static com.coveros.selenified.element.check.Constants.*;

/**
 * Excludes extends Check to provide some additional checking
 * capabilities. It will handle all checks performed on the actual
 * element. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * check to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests. Excludes checks that elements
 * don't have a particular value associated to them.
 *
 * @author Max Saperstone
 * @version 3.1.1
 * @lastupdate 3/7/2019
 */
public interface Excludes extends Check {

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Checks that the element's class does not contain the provided expected
     * class. If the element isn't present, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param unexpectedClass - the unexpected class value
     */
    void clazz(String unexpectedClass);

    /**
     * Checks that the element's class does not contain the provided expected
     * class. If the element isn't present, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param unexpectedClass - the unexpected class value
     * @param waitFor         - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook        - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual class of the element. null will be returned if the element isn't present
     */
    default String checkClazz(String unexpectedClass, double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + " without class <b>" + unexpectedClass + "</b>", waitFor);
        // check our classes
        String actualClass = getElement().get().attribute(CLASS);
        // record the result
        if (actualClass != null && !actualClass.contains(unexpectedClass)) {
            getOutputFile().recordActual(
                    getElement().prettyOutputStart() + " does not contain a class value of <b>" + unexpectedClass + "</b>",
                    timeTook, Success.PASS);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + CLASS_VALUE + actualClass + "</b>, which contains <b>" +
                    unexpectedClass + "</b>", timeTook, Success.FAIL);
        }
        return actualClass;
    }

    /**
     * Checks that the element does not contain the provided expected
     * attribute. If the element isn't present, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param attribute - the attribute to check for
     */
    void attribute(String attribute);

    /**
     * Checks that the element does not contain the provided expected
     * attribute. If the element isn't present, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param attribute - the attribute to check for
     * @param waitFor   - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook  - the amount of time it took for wait for something (assuming we had to wait)
     * @return String[]: all of the attributes of the element. null will be returned if the element isn't present
     */
    default String[] checkAttribute(String attribute, double waitFor, double timeTook) {
        // record the action and get the attributes
        String[] allAttributes = getAttributes(attribute, "without", waitFor);
        // record the result
        if (allAttributes == null || Arrays.asList(allAttributes).contains(attribute)) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + " contains the attribute of <b>" + attribute + "</b>",
                    timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + " does not contain the attribute of <b>" + attribute + "</b>" +
                    ONLY_VALUE + Arrays.toString(allAttributes) + "</b>", timeTook, Success.PASS);
        }
        return allAttributes;
    }

    /**
     * Checks that the element's text does not contain the provided expected
     * text. If the element isn't present, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedText the expected value of the element
     */
    void text(String expectedText);

    /**
     * Checks that the element's text does not contain the provided expected
     * text. If the element isn't present, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedText the expected value of the element
     * @param waitFor      - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook     - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual text of the element. null will be returned if the element isn't present
     */
    default String checkText(String expectedText, double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + EXCLUDES_TEXT + expectedText + "</b>", waitFor);
        // check for the object to the present on the page
        String elementValue = getElement().get().text();
        // record the result
        if (elementValue == null || elementValue.contains(expectedText)) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + HAS_TEXT + elementValue + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + HAS_TEXT + elementValue + "</b>", timeTook, Success.PASS);
        }
        return elementValue;
    }

    /**
     * Checks that the element's value does not contain the provided expected
     * value. If the element isn't present or an input, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    void value(String expectedValue);

    /**
     * Checks that the element's value does not contain the provided expected
     * value. If the element isn't present or an input, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     * @param waitFor       - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook      - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual value of the element. null will be returned if the element isn't present or an input
     */
    default String checkValue(String expectedValue, double waitFor, double timeTook) {
        // record the action and get our value
        String elementValue = getValue(expectedValue, EXCLUDES_VALUE, waitFor);
        if (elementValue == null || elementValue.contains(expectedValue)) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook, Success.PASS);
        }
        return elementValue;
    }

    /**
     * Checks that the element's options do not contain the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param option the option not expected in the list
     */
    void selectOption(String option);

    /**
     * Checks that the element's options do not contain the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param option   the option not expected in the list
     * @param waitFor  - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook - the amount of time it took for wait for something (assuming we had to wait)
     * @return String[]: all of the select options of the element. null will be returned if the element isn't present or a select
     */
    @SuppressWarnings("squid:S1168")
    default String[] checkSelectOption(String option, double waitFor, double timeTook) {
        // record the action, and check for select
        if (!isPresentSelect(getElement().prettyOutput() + " without the option <b>" + option +
                "</b> available to be selected on the page", waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // check for the object to the editable
        String[] allOptions = getElement().get().selectOptions();
        // record the result
        if (Arrays.asList(allOptions).contains(option)) {
            getOutputFile().recordActual(
                    getElement().prettyOutputStart() + " is editable and present and contains the option <b>" + option +
                            "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(
                    getElement().prettyOutputStart() + " is editable and present but does not contain the option <b>" + option +
                            "</b>", timeTook, Success.PASS);
        }
        return allOptions;
    }

    /**
     * Checks that the element's options do not contain the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param selectValue the unexpected input value of the element
     */
    void selectValue(String selectValue);

    /**
     * Checks that the element's options do not contain the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param selectValue the unexpected input value of the element
     * @param waitFor     - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook    - the amount of time it took for wait for something (assuming we had to wait)
     * @return String[]: all of the select values of the element. null will be returned if the element isn't present or a select
     */
    @SuppressWarnings("squid:S1168")
    default String[] checkSelectValue(String selectValue, double waitFor, double timeTook) {
        // record the action, and check for select
        if (!isPresentSelect(getElement().prettyOutput() + " without a select value of <b>" + selectValue +
                "</b> available to be selected on the page", waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // check for the object to the present on the page
        String[] elementValues = getElement().get().selectValues();
        // record the result
        if (Arrays.asList(elementValues).contains(selectValue)) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + CONTAINS_VALUE + selectValue + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + EXCLUDES_VALUE + selectValue + "</b>, only the values <b>" +
                    Arrays.toString(elementValues) + "</b>", timeTook, Success.PASS);
        }
        return elementValues;
    }
}