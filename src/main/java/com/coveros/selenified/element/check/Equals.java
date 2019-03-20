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

import java.util.Arrays;

import static com.coveros.selenified.element.check.Constants.*;

/**
 * Equals extends Check to provide some additional checking capabilities.
 * It will handle all checks performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each check to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. Equals checks that elements have a particular value associated
 * to them.
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 3/19/2019
 */
public interface Equals extends Check {

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Checks that the element has a the expected number of matches on the page, e.g.
     * how many elements match the locator and target provided.
     * This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedMatches the expected number of elements matching the locator
     */
    void matches(int expectedMatches);

    /**
     * Checks that the element has a the expected number of matches on the page, e.g.
     * how many elements match the locator and target provided.
     * If the element isn't present, or the css doesn't contain
     * the desired attribute, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedMatches the expected number of elements matching the locator
     * @param waitFor         - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook        - the amount of time it took for wait for something (assuming we had to wait)
     * @return Integer: the number of elements matching the locator
     */
    default int checkMatches(int expectedMatches, double waitFor, double timeTook) {
        // get the value
        int matchCount = getElement().get().matchCount();
        // record the result
        if (matchCount != expectedMatches) {
            getReporter().fail(getElement().prettyOutput() + " having a match count of <b>" + expectedMatches +
                    "</b>", waitFor, getElement().prettyOutputStart() + " has a match count of <b>" + matchCount + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " having a match count of <b>" + expectedMatches +
                    "</b>", waitFor, getElement().prettyOutputStart() + " has a match count of <b>" + matchCount + "</b>", timeTook);
        }
        return matchCount;
    }

    /**
     * Checks that the element has a css attribute with a value equal to the
     * value provided. If the element isn't present, or the css doesn't contain
     * the desired attribute, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param attribute     - the css attribute to be checked
     * @param expectedValue the expected css value of the passed attribute of the element
     */
    void cssValue(String attribute, String expectedValue);

    /**
     * Checks that the element has a css attribute with a value equal to the
     * value provided. If the element isn't present, or the css doesn't contain
     * the desired attribute, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param attribute     - the css attribute to be checked
     * @param expectedValue the expected css value of the passed attribute of the element
     * @param waitFor       - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook      - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the css value of the attribute passed in. null will be returned if the element's css isn't accessible
     */
    default String checkCssValue(String attribute, String expectedValue, double waitFor, double timeTook) {
        // get the actual css element value
        String elementCssValue = getElement().get().css(attribute);
        // record the result
        if (elementCssValue == null) {
            getReporter().fail(getElement().prettyOutput() + " having a css attribute of <i>" + attribute +
                    "</i> with a value of <b>" + expectedValue + "</b>", waitFor, "Unable to assess the css of " + getElement().prettyOutputEnd(), timeTook);
        } else if (!expectedValue.equals(elementCssValue)) {
            getReporter().fail(getElement().prettyOutput() + " having a css attribute of <i>" + attribute +
                            "</i> with a value of <b>" + expectedValue + "</b>", waitFor,
                    getElement().prettyOutputStart() + " has a css attribute of <i>" + attribute + WITH + elementCssValue +
                            "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " having a css attribute of <i>" + attribute +
                            "</i> with a value of <b>" + expectedValue + "</b>", waitFor,
                    getElement().prettyOutputStart() + " has a css attribute of <i>" + attribute + WITH + elementCssValue +
                            "</b>", timeTook);
        }
        return elementCssValue;
    }

    /**
     * Checks that the element's class equals the provided expected class. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedClass - the full expected class value
     */
    void clazz(String expectedClass);

    /**
     * Checks that the element's class equals the provided expected class. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedClass - the full expected class value
     * @param waitFor       - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook      - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual class of the element. null will be returned if the element isn't present
     */
    default String checkClazz(String expectedClass, double waitFor, double timeTook) {
        // get the actual class value
        String actualClass = getElement().get().attribute(CLASS);
        // record the result
        if (expectedClass == null ? actualClass != null : !expectedClass.equals(actualClass)) {
            getReporter().fail(getElement().prettyOutput() + " with class <b>" + expectedClass + "</b>", waitFor, getElement().prettyOutputStart() + CLASS_VALUE + actualClass + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " with class <b>" + expectedClass + "</b>", waitFor, getElement().prettyOutputStart() + CLASS_VALUE + expectedClass + "</b>", timeTook);
        }
        return actualClass;
    }

    /**
     * Checks that the element has an attribute with a value equals to the
     * value provided. If the element isn't present, or the element does not
     * have the attribute, this will constitute a failure, same as a mismatch.
     * This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param attribute     - the attribute to be checked
     * @param expectedValue - the expected value of the passed attribute of the element
     */
    void attribute(String attribute, String expectedValue);

    /**
     * Checks that the element has an attribute with a value equals to the
     * value provided. If the element isn't present, or the element does not
     * have the attribute, this will constitute a failure, same as a mismatch.
     * This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param attribute     - the attribute to be checked
     * @param expectedValue - the expected value of the passed attribute of the element
     * @param waitFor       - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook      - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the attribute value of the attribute specified from the element. null will be returned if the element isn't present
     */
    default String checkAttribute(String attribute, String expectedValue, double waitFor, double timeTook) {
        // get the actual attribute value
        String elementValue = getElement().get().attribute(attribute);
        // record the result
        if (elementValue == null) {
            getReporter().fail(getElement().prettyOutput() + " having an attribute of <i>" + attribute + " with a value of <b>" +
                    expectedValue + "</b>", waitFor, getElement().prettyOutputStart() + " does not have an attribute of <i>" + attribute + "</i>", timeTook);
        } else if (!elementValue.equals(expectedValue)) {
            getReporter().fail(getElement().prettyOutput() + " having an attribute of <i>" + attribute + " with a value of <b>" +
                            expectedValue + "</b>", waitFor,
                    getElement().prettyOutputStart() + " has an attribute of <i>" + attribute + WITH + elementValue + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " having an attribute of <i>" + attribute + " with a value of <b>" +
                            expectedValue + "</b>", waitFor,
                    getElement().prettyOutputStart() + " has an attribute of <i>" + attribute + WITH + elementValue + "</b>", timeTook);
        }
        return elementValue;
    }

    /**
     * Checks that the element's text equals the provided expected text. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedText the expected value of the element
     */
    void text(String expectedText);

    /**
     * Checks that the element's text equals the provided expected text. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedText - the expected value of the element
     * @param waitFor      - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook     - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual text of the element. null will be returned if the element isn't present
     */
    default String checkText(String expectedText, double waitFor, double timeTook) {
        // check for the object to the present on the page
        String elementText = getElement().get().text();
        // record the result
        if (!expectedText.equals(elementText)) {
            getReporter().fail(getElement().prettyOutput() + " having text of <b>" + expectedText + "</b>", waitFor, getElement().prettyOutputStart() + HAS_VALUE + elementText + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " having text of <b>" + expectedText + "</b>", waitFor, getElement().prettyOutputStart() + HAS_VALUE + elementText + "</b>", timeTook);
        }
        return elementText;
    }

    /**
     * Checks that the element's text in a particular cell equals the provided
     * expected text. If the element isn't present, or a table, this will
     * constitute a failure, same as a mismatch. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param row          - the number of the row in the table - note, row numbering
     *                     starts at 0
     * @param col          - the number of the column in the table - note, column
     *                     numbering starts at 0
     * @param expectedText - what text do we expect to be in the table cell
     */
    void text(int row, int col, String expectedText);

    /**
     * Checks that the element's text in a particular cell equals the provided
     * expected text. If the element isn't present, or a table, this will
     * constitute a failure, same as a mismatch. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param row          - the number of the row in the table - note, row numbering
     *                     starts at 0
     * @param col          - the number of the column in the table - note, column
     *                     numbering starts at 0
     * @param expectedText - what text do we expect to be in the table cell
     * @param waitFor      - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook     - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual text of the table cell. null will be returned if the element isn't present or a table, or table cell doesn't exist
     */
    @SuppressWarnings("squid:S1168")
    default String checkText(int row, int col, String expectedText, double waitFor, double timeTook) {
        String column = " and column ";
        String within = " within element ";
        // record the action, and check for table
        if (!isPresentTable("cell at row " + row + column + col + within + getElement().prettyOutput() +
                " to have the text value of <b>" + expectedText + "</b>", waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the table cell text
        String actualText = getElement().get().tableCell(row, col).get().text();
        // record the result
        if (!actualText.equals(expectedText)) {
            getReporter().fail("cell at row " + row + column + col + within + getElement().prettyOutput() +
                    " to have the text value of <b>" + expectedText + "</b>", waitFor, "Cell at row " + row + column + col + within + getElement().prettyOutput() +
                    " has the text value of <b>" + actualText + "</b>", timeTook);
        } else {
            getReporter().pass("cell at row " + row + column + col + within + getElement().prettyOutput() +
                            " to have the text value of <b>" + expectedText + "</b>", waitFor,
                    "Cell at row " + row + column + col + within + getElement().prettyOutput() + " has the text value of <b>" +
                            actualText + "</b>", timeTook);
        }
        return actualText;
    }

    /**
     * Checks that the element's value equals the provided expected value. If
     * the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue - the expected input value of the element
     */
    void value(String expectedValue);

    /**
     * Checks that the element's value equals the provided expected value. If
     * the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue - the expected input value of the element
     * @param waitFor       - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook      - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual value of the element. null will be returned if the element isn't present or an input
     */
    @SuppressWarnings("squid:S1168")
    default String checkValue(String expectedValue, double waitFor, double timeTook) {
        // record the action and check this is an input element
        if (!isPresentInput(getElement().prettyOutputStart() + " is not an input on the page", waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the element value
        String elementValue = getElement().get().value();
        // record the result
        if (!elementValue.equals(expectedValue)) {
            getReporter().fail(getElement().prettyOutputStart() + " is not an input on the page", waitFor, getElement().prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutputStart() + " is not an input on the page", waitFor, getElement().prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook);
        }
        return elementValue;
    }

    /**
     * Checks that the element's selected option equals the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedText - the expected input text of the element
     */
    void selectedOption(String expectedText);

    /**
     * Checks that the element's selected option equals the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedText - the expected input text of the element
     * @param waitFor      - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook     - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual selected option of the element. null will be returned if the element isn't present or a select
     */
    @SuppressWarnings("squid:S1168")
    default String checkSelectedOption(String expectedText, double waitFor, double timeTook) {
        // record the action and check it's a select
        if (!isPresentSelect(
                getElement().prettyOutput() + " having a selected option of <b>" + expectedText + "</b>", waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the selected text
        String elementText = getElement().get().selectedOption();
        // record the result
        if (!elementText.equals(expectedText)) {
            getReporter().fail(getElement().prettyOutput() + " having a selected option of <b>" + expectedText + "</b>", waitFor, getElement().prettyOutputStart() + HAS_OPTION + elementText + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " having a selected option of <b>" + expectedText + "</b>", waitFor, getElement().prettyOutputStart() + HAS_OPTION + elementText + "</b>", timeTook);
        }
        return elementText;
    }

    /**
     * Checks that the element's selected value equals the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue - the expected input value of the element
     */
    void selectedValue(String expectedValue);

    /**
     * Checks that the element's selected value equals the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue - the expected input value of the element
     * @param waitFor       - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook      - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual selected value of the element. null will be returned if the element isn't present or a select
     */
    @SuppressWarnings("squid:S1168")
    default String checkSelectedValue(String expectedValue, double waitFor, double timeTook) {
        // record the action and check it's a select
        if (!isPresentSelect(
                getElement().prettyOutput() + " having a selected value of <b>" + expectedValue + "</b>", waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the selected value
        String elementValue = getElement().get().selectedValue();
        // record the result
        if (!elementValue.equals(expectedValue)) {
            getReporter().fail(getElement().prettyOutput() + " having a selected value of <b>" + expectedValue + "</b>", waitFor, getElement().prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " having a selected value of <b>" + expectedValue + "</b>", waitFor, getElement().prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook);
        }
        return elementValue;
    }

    /**
     * Checks that the element's select options equal the provided expected
     * options. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedOptions - the expected input value of the element
     */
    void selectOptions(String... expectedOptions);

    /**
     * Checks that the element's select options equal the provided expected
     * options. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedOptions - the expected input value of the element
     * @param waitFor         - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook        - the amount of time it took for wait for something (assuming we had to wait)
     * @return String[]: all of the select options of the element. null will be returned if the element isn't present or a select
     */
    @SuppressWarnings("squid:S1168")
    default String[] checkSelectOptions(String[] expectedOptions, double waitFor, double timeTook) {
        // record the action, and check it's a select
        if (!isPresentSelect(
                getElement().prettyOutput() + " with select options of <b>" + String.join("</b>, <b>" + expectedOptions) + "</b>", waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the actual select options
        String[] elementOptions = getElement().get().selectOptions();
        // record the result
        if (!Arrays.toString(elementOptions).equals(Arrays.toString(expectedOptions))) {
            getReporter().fail(getElement().prettyOutput() + " with select options of <b>" + String.join("</b>, <b>" + expectedOptions) + "</b>", waitFor, getElement().prettyOutputStart() + OPTIONS + Arrays.toString(elementOptions) + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " with select options of <b>" + String.join("</b>, <b>" + expectedOptions) + "</b>", waitFor, getElement().prettyOutputStart() + OPTIONS + Arrays.toString(elementOptions) + "</b>", timeTook);
        }
        return elementOptions;
    }

    /**
     * Checks that the element's select values equal the provided expected
     * values. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValues - the expected input value of the element
     */
    void selectValues(String... expectedValues);

    /**
     * Checks that the element's select values equal the provided expected
     * values. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValues - the expected input value of the element
     * @param waitFor        - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook       - the amount of time it took for wait for something (assuming we had to wait)
     * @return String[]: all of the select values of the element. null will be returned if the element isn't present or a select
     */
    @SuppressWarnings("squid:S1168")
    default String[] checkSelectValues(String[] expectedValues, double waitFor, double timeTook) {
        // record the action, and check it's a select
        if (!isPresentSelect(
                getElement().prettyOutput() + " with select values of <b>" + Arrays.toString(expectedValues) +
                        "</b>", waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the actual select values
        String[] elementValues = getElement().get().selectValues();
        // record the result
        if (!Arrays.toString(elementValues).equals(Arrays.toString(expectedValues))) {
            getReporter().fail(getElement().prettyOutput() + " with select values of <b>" + Arrays.toString(expectedValues) +
                    "</b>", waitFor, getElement().prettyOutputStart() + VALUES + Arrays.toString(elementValues) + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " with select values of <b>" + Arrays.toString(expectedValues) +
                    "</b>", waitFor, getElement().prettyOutputStart() + VALUES + Arrays.toString(elementValues) + "</b>", timeTook);
        }
        return elementValues;
    }
}