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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.coveros.selenified.element.check.Constants.*;

/**
 * Contains extends Check to provide some additional checking
 * capabilities. It will handle all checks performed on the actual
 * element. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * check to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests. Contains checks that elements
 * have a particular value associated to them.
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 3/19/2019
 */
public interface Contains extends Check {

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Checks that the element's class contains the provided expected class.
     * If the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedClass - the expected class value
     */
    void clazz(String expectedClass);

    /**
     * Checks that the element's class contains the provided expected class.
     * If the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedClass - the expected class value
     * @param waitFor       - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook      - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual class of the element. null will be returned if the element isn't present
     */
    default String checkClazz(String expectedClass, double waitFor, double timeTook) {
        // get the value
        String actualClass = getElement().get().attribute(CLASS);
        // record the result
        if (actualClass == null || !actualClass.contains(expectedClass)) {
            getReporter().fail(getElement().prettyOutput() + " containing class <b>" + expectedClass + "</b>", waitFor, getElement().prettyOutputStart() + CLASS_VALUE + actualClass + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " containing class <b>" + expectedClass + "</b>", waitFor, getElement().prettyOutputStart() + CLASS_VALUE + actualClass + "</b>, which contains <b>" +
                    expectedClass + "</b>", timeTook);
        }
        return actualClass;
    }

    /**
     * Checks that the element contains the provided expected attribute. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedAttribute - the attribute to check for
     */
    void attribute(String expectedAttribute);

    /**
     * Checks that the element contains the provided expected attribute. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedAttribute - the attribute to check for
     * @param waitFor           - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook          - the amount of time it took for wait for something (assuming we had to wait)
     * @return String[]: all of the attributes of the element. null will be returned if the element isn't present
     */
    default Set<String> checkAttribute(String expectedAttribute, double waitFor, double timeTook) {
        // record the action and get the attributes
        Map<String, String> atts = getElement().get().allAttributes();
        Set<String> allAttributes = new HashSet<>();
        if (atts != null) {
            allAttributes = atts.keySet();
        }
        // record the result
        if (atts == null || !allAttributes.contains(expectedAttribute)) {
            getReporter().fail(getElement().prettyOutput() + " with attribute <b>" + expectedAttribute + "</b>", waitFor,
                    getElement().prettyOutputStart() + " does not contain the attribute of <b>" + expectedAttribute + "</b>" +
                            ONLY_VALUE + String.join(", " + allAttributes) + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " with attribute <b>" + expectedAttribute + "</b>", waitFor,
                    getElement().prettyOutputStart() + " contains the attribute of <b>" + expectedAttribute + "</b>", timeTook);
        }
        return allAttributes;
    }

    /**
     * Checks that the element's text contains the provided expected text. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedText - the expected value of the element
     */
    void text(String expectedText);

    /**
     * Checks that the element's text contains the provided expected text. If
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
        // get the value
        String elementValue = getElement().get().text();
        // record the result
        if (elementValue == null || !elementValue.contains(expectedText)) {
            getReporter().fail(getElement().prettyOutput() + CONTAINS_TEXT + expectedText + "</b>", waitFor, getElement().prettyOutputStart() + HAS_TEXT + elementValue + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + CONTAINS_TEXT + expectedText + "</b>", waitFor, getElement().prettyOutputStart() + HAS_TEXT + elementValue + "</b>", timeTook);
        }
        return elementValue;
    }

    /**
     * Checks that the element's value contains the provided expected value.
     * If the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    void value(String expectedValue);

    /**
     * Checks that the element's value contains the provided expected value.
     * If the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     * @param waitFor       - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook      - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual value of the element. null will be returned if the element isn't present or an input
     */
    default String checkValue(String expectedValue, double waitFor, double timeTook) {
        // record the action and get the attributes
        String elementValue = getElement().get().value();
        // record the expected
        if (elementValue == null || !elementValue.contains(expectedValue)) {
            getReporter().fail(getElement().prettyOutput() + expectedValue + elementValue + "</b>", waitFor, getElement().prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + expectedValue + elementValue + "</b>", waitFor, getElement().prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook);
        }
        return elementValue;
    }

    /**
     * Checks that the element's options contains the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedOption the option expected in the list
     */
    void selectOption(String expectedOption);

    /**
     * Checks that the element's options contains the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedOption the option expected in the list
     * @param waitFor        - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook       - the amount of time it took for wait for something (assuming we had to wait)
     * @return String[]: all of the select options of the element. null will be returned if the element isn't present or a select
     */
    @SuppressWarnings("squid:S1168")
    default String[] checkSelectOption(String expectedOption, double waitFor, double timeTook) {
        // record the action, and check for select
        if (!isPresentSelect(getElement().prettyOutput() + " with the option <b>" + expectedOption +
                "</b> available to be selected on the page", waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the select options
        String[] allOptions = getElement().get().selectOptions();
        // record the expected
        if (!Arrays.asList(allOptions).contains(expectedOption)) {
            getReporter().fail(getElement().prettyOutput() + " with the option <b>" + expectedOption +
                    "</b> available to be selected on the page", waitFor, getElement().prettyOutputStart() +
                    " is present but does not contain the option <b>" + expectedOption + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " with the option <b>" + expectedOption +
                    "</b> available to be selected on the page", waitFor, getElement().prettyOutputStart() +
                    " is present and contains the option <b>" + expectedOption + "</b>", timeTook);
        }
        return allOptions;
    }

    /**
     * Checks that the element's options contains the provided expected value.
     * If the element isn't present or a select, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     */
    void selectValue(String expectedValue);

    /**
     * Checks that the element's options contains the provided expected value.
     * If the element isn't present or a select, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     * @param waitFor       - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook      - the amount of time it took for wait for something (assuming we had to wait)
     * @return String[]: all of the select values of the element. null will be returned if the element isn't present or a select
     */
    @SuppressWarnings("squid:S1168")
    default String[] checkSelectValue(String expectedValue, double waitFor, double timeTook) {
        // record the action, and check for select
        if (!isPresentSelect(getElement().prettyOutput() + " having a select value of <b>" + expectedValue +
                "</b> available to be selected on the page", waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the select values
        String[] allValues = getElement().get().selectValues();
        // record the expected
        if (!Arrays.asList(allValues).contains(expectedValue)) {
            getReporter().fail(getElement().prettyOutput() + " having a select value of <b>" + expectedValue +
                    "</b> available to be selected on the page", waitFor, getElement().prettyOutputStart() + EXCLUDES_VALUE + expectedValue + "</b>" + ONLY_VALUE +
                    Arrays.toString(allValues) + "</b>", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " having a select value of <b>" + expectedValue +
                    "</b> available to be selected on the page", waitFor, getElement().prettyOutputStart() + CONTAINS_VALUE + expectedValue + "</b>", timeTook);
        }
        return allValues;
    }

    /**
     * Checks that the element has the expected number of options. If the
     * element isn't present or a select, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param numOfOptions the expected number of options in the select element
     */
    void selectOptions(int numOfOptions);

    /**
     * Checks that the element has the expected number of options. If the
     * element isn't present or a select, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param numOfOptions the expected number of options in the select element
     * @param waitFor      - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook     - the amount of time it took for wait for something (assuming we had to wait)
     * @return Integer: the number of select options of the element. -1 will be returned if the element isn't present or a select
     */
    default int checkSelectOptions(int numOfOptions, double waitFor, double timeTook) {
        // record the action, and check for select
        if (!isPresentSelect(getElement().prettyOutput() + " with number of select values equal to <b>" + numOfOptions + "</b>", waitFor)) {
            return -1;
        }
        // get the select options
        int elementValues = getElement().get().numOfSelectOptions();
        // record the expected
        if (elementValues != numOfOptions) {
            getReporter().fail(getElement().prettyOutput() + " with number of select values equal to <b>" + numOfOptions + "</b>",
                    waitFor, getElement().prettyOutputStart() + " has <b>" + numOfOptions + "</b> select options", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " with number of select values equal to <b>" + numOfOptions + "</b>",
                    waitFor, getElement().prettyOutputStart() + " has <b>" + numOfOptions + "</b> select options", timeTook);
        }
        return elementValues;
    }

    /**
     * Checks that the element has the expected number of columns. If the
     * element isn't present or a table, this will constitute a failure, same as
     * a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param numOfColumns the expected number of column elements of a table
     */
    void columns(int numOfColumns);

    /**
     * Checks that the element has the expected number of columns. If the
     * element isn't present or a table, this will constitute a failure, same as
     * a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param numOfColumns the expected number of column elements of a table
     * @param waitFor      - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook     - the amount of time it took for wait for something (assuming we had to wait)
     * @return Integer: the number of columns of the element. -1 will be returned if the element isn't present or a table
     */
    default int checkColumns(int numOfColumns, double waitFor, double timeTook) {
        // record the action, and check for table
        if (!isPresentTable(getElement().prettyOutput() + " with the number of table columns equal to <b>" + numOfColumns + "</b>", waitFor)) {
            return -1;
        }
        // get the table columns
        int actualNumOfCols = getElement().get().numOfTableColumns();
        // record the expected
        if (actualNumOfCols != numOfColumns) {
            getReporter().fail(getElement().prettyOutput() + " with the number of table columns equal to <b>" + numOfColumns + "</b>", waitFor,
                    getElement().prettyOutputStart() + " does not have the number of columns <b>" + numOfColumns +
                            "</b>. Instead, " + actualNumOfCols + " columns were found", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " with the number of table columns equal to <b>" + numOfColumns + "</b>", waitFor,
                    getElement().prettyOutputStart() + " has " + actualNumOfCols + "</b> columns", timeTook);
        }
        return actualNumOfCols;
    }

    /**
     * Checks that the element has the expected number of rows. If the element
     * isn't present or a table, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param numOfRows the expected number of row elements of a table
     */
    void rows(int numOfRows);

    /**
     * Checks that the element has the expected number of rows. If the element
     * isn't present or a table, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param numOfRows the expected number of row elements of a table
     * @param waitFor   - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook  - the amount of time it took for wait for something (assuming we had to wait)
     * @return Integer: the number of columns of the element. -1 will be returned if the element isn't present or a table
     */
    default int checkRows(int numOfRows, double waitFor, double timeTook) {
        // record the action, and check for table
        if (!isPresentTable(getElement().prettyOutput() + " with the number of table rows equal to <b>" + numOfRows + "</b>", waitFor)) {
            return -1;
        }
        // get the table columns
        int actualNumOfRows = getElement().get().numOfTableRows();
        // record the expected
        if (actualNumOfRows != numOfRows) {
            getReporter().fail(getElement().prettyOutput() + " with the number of table rows equal to <b>" + numOfRows + "</b>", waitFor,
                    getElement().prettyOutputStart() + " does not have the number of rows <b>" + numOfRows +
                            "</b>. Instead, " + actualNumOfRows + " rows were found", timeTook);
        } else {
            getReporter().pass(getElement().prettyOutput() + " with the number of table rows equal to <b>" + numOfRows + "</b>", waitFor,
                    getElement().prettyOutputStart() + " has " + actualNumOfRows + "</b> rows", timeTook);
        }
        return actualNumOfRows;
    }
}