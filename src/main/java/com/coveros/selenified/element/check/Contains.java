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
 * Contains extends Asserts to provide some additional verification
 * capabilities. It will handle all verifications performed on the actual
 * element. These asserts are custom to the framework, and in addition to
 * providing easy object oriented capabilities, they take screenshots with each
 * verification to provide additional traceability, and assist in
 * troubleshooting and debugging failing tests. Contains checks that elements
 * have a particular value associated to them.
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 2/22/2018
 */
public interface Contains extends Check {

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Verifies that the element's class contains the provided expected class.
     * If the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedClass - the expected class value
     */
    void clazz(String expectedClass);

    default String checkClazz(String expectedClass, double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + " containing class <b>" + expectedClass + "</b>", waitFor);
        // get the value
        String actualClass = getElement().get().attribute(CLASS);
        // record the result
        if (actualClass == null || !actualClass.contains(expectedClass)) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + CLASS_VALUE + actualClass + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + CLASS_VALUE + actualClass + "</b>, which contains <b>" +
                    expectedClass + "</b>", timeTook, Success.PASS);
        }
        return actualClass;
    }

    /**
     * Verifies that the element contains the provided expected attribute. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedAttribute - the attribute to check for
     */
    void attribute(String expectedAttribute);

    default String[] checkAttribute(String expectedAttribute, double waitFor, double timeTook) {
        // record the action and get the attributes
        String[] allAttributes = getAttributes(expectedAttribute, "with", waitFor);
        // record the result
        if (allAttributes == null || !Arrays.asList(allAttributes).contains(expectedAttribute)) {
            getOutputFile().recordActual(
                    getElement().prettyOutputStart() + " does not contain the attribute of <b>" + expectedAttribute + "</b>" +
                            ONLY_VALUE + Arrays.toString(allAttributes) + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + " contains the attribute of <b>" + expectedAttribute + "</b>",
                    timeTook, Success.PASS);
        }
        return allAttributes;
    }

    /**
     * Verifies that the element's text contains the provided expected text. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedText the expected value of the element
     */
    void text(String expectedText);

    default String checkText(String expectedText, double waitFor, double timeTook) {
        // record the action
        getOutputFile().recordAction(getElement().prettyOutput() + CONTAINS_TEXT + expectedText + "</b>", waitFor);
        // get the value
        String elementValue = getElement().get().text();
        // record the result
        if (elementValue == null || !elementValue.contains(expectedText)) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + HAS_TEXT + elementValue + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + HAS_TEXT + elementValue + "</b>", timeTook, Success.PASS);
        }
        return elementValue;
    }

    /**
     * Verifies that the element's value contains the provided expected value.
     * If the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    void value(String expectedValue);

    default String checkValue(String expectedValue, double waitFor, double timeTook) {
        // record the action and get the attributes
        String elementValue = getValue(expectedValue, CONTAINS_VALUE, waitFor);
        // record the expected
        if (elementValue == null || !elementValue.contains(expectedValue)) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + HAS_VALUE + elementValue + "</b>", timeTook, Success.PASS);
        }
        return elementValue;
    }

    /**
     * Verifies that the element's options contains the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedOption the option expected in the list
     */
    void selectOption(String expectedOption);

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
        if (allOptions == null || !Arrays.asList(allOptions).contains(expectedOption)) {
            getOutputFile().recordActual(
                    getElement().prettyOutputStart() + " is present but does not contain the option <b>" + expectedOption + "</b>",
                    timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + " is present and contains the option <b>" + expectedOption + "</b>",
                    timeTook, Success.PASS);
        }
        return allOptions;
    }

    /**
     * Verifies that the element's options contains the provided expected value.
     * If the element isn't present or a select, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     */
    void selectValue(String expectedValue);

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
        if (allValues == null || !Arrays.asList(allValues).contains(expectedValue)) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + EXCLUDES_VALUE + expectedValue + "</b>" + ONLY_VALUE +
                    Arrays.toString(allValues) + "</b>", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + CONTAINS_VALUE + expectedValue + "</b>", timeTook, Success.PASS);
        }
        return allValues;
    }

    /**
     * Verifies that the element has the expected number of options. If the
     * element isn't present or a select, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param numOfOptions the expected number of options in the select element
     */
    void selectOptions(int numOfOptions);

    default int checkSelectOptions(int numOfOptions, double waitFor, double timeTook) {
        // record the action, and check for select
        if (!isPresentSelect(getElement().prettyOutput() + " with number of select values equal to <b>" + numOfOptions + "</b>", waitFor)) {
            return -1;
        }
        // get the select options
        int elementValues = getElement().get().numOfSelectOptions();
        // record the expected
        if (elementValues != numOfOptions) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + " has <b>" + numOfOptions + "</b> select options",
                    timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + " has <b>" + numOfOptions + "</b> select options",
                    timeTook, Success.PASS);
        }
        return elementValues;
    }

    /**
     * Verifies that the element has the expected number of columns. If the
     * element isn't present or a table, this will constitute a failure, same as
     * a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param numOfColumns the expected number of column elements of a table
     */
    void columns(int numOfColumns);

    default int checkColumns(int numOfColumns, double waitFor, double timeTook) {
        // record the action, and check for table
        if (!isPresentTable(getElement().prettyOutput() + " with the number of table columns equal to <b>" + numOfColumns + "</b>", waitFor)) {
            return -1;
        }
        // get the table columns
        int actualNumOfCols = getElement().get().numOfTableColumns();
        // record the expected
        if (actualNumOfCols != numOfColumns) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + " does not have the number of columns <b>" + numOfColumns +
                    "</b>. Instead, " + actualNumOfCols + " columns were found", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + " has " + actualNumOfCols + "</b> columns", timeTook, Success.PASS);
        }
        return actualNumOfCols;
    }

    /**
     * Verifies that the element has the expected number of rows. If the element
     * isn't present or a table, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param numOfRows the expected number of row elements of a table
     */
    void rows(int numOfRows);

    default int checkRows(int numOfRows, double waitFor, double timeTook) {
        // record the action, and check for table
        if (!isPresentTable(getElement().prettyOutput() + " with the number of table rows equal to <b>" + numOfRows + "</b>", waitFor)) {
            return -1;
        }
        // get the table columns
        int actualNumOfRows = getElement().get().numOfTableRows();
        // record the expected
        if (actualNumOfRows != numOfRows) {
            getOutputFile().recordActual(getElement().prettyOutputStart() + " does not have the number of rows <b>" + numOfRows +
                    "</b>. Instead, " + actualNumOfRows + " rows were found", timeTook, Success.FAIL);
        } else {
            getOutputFile().recordActual(getElement().prettyOutputStart() + " has " + actualNumOfRows + "</b> rows", timeTook, Success.PASS);
        }
        return actualNumOfRows;
    }
}