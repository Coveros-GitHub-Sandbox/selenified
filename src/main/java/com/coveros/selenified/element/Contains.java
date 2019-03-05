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

import java.util.Arrays;

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
public class Contains extends Assert {

    public Contains(Element element, OutputFile file) {
        this.element = element;
        this.file = file;
    }

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
    public void clazz(String expectedClass) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + " containing class <b>" + expectedClass + "</b>");
        // get the class
        String actualClass = element.get().attribute(CLASS);
        // file.record the element
        if (actualClass == null || !actualClass.contains(expectedClass)) {
            file.recordActual(element.prettyOutputStart() + CLASSVALUE + actualClass + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(
                element.prettyOutputStart() + CLASSVALUE + actualClass + "</b>, which contains <b>" + expectedClass +
                        "</b>", Success.PASS);
    }

    /**
     * Verifies that the element contains the provided expected attribute. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param attribute - the attribute to check for
     */
    public void attribute(String attribute) {
        String[] allAttributes = getAttributes(attribute, "with");
        if (allAttributes == null) {
            return;
        }
        // file.record the element
        if (!Arrays.asList(allAttributes).contains(attribute)) {
            file.recordActual(
                    element.prettyOutputStart() + " does not contain the attribute of <b>" + attribute + "</b>" +
                            ONLYVALUE + Arrays.toString(allAttributes) + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + " contains the attribute of <b>" + attribute + "</b>",
                Success.PASS);
    }

    /**
     * Verifies that the element's text contains the provided expected text. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    public void text(String expectedValue) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + HASTEXT + expectedValue + "</b>");
        // check for the object to the present on the page
        String elementValue = element.get().text();
        if (!elementValue.contains(expectedValue)) {
            file.recordActual(element.prettyOutputStart() + TEXT + elementValue + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + TEXT + elementValue + "</b>", Success.PASS);
    }

    /**
     * Verifies that the element's value contains the provided expected value.
     * If the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected value of the element
     */
    public void value(String expectedValue) {
        String elementValue = getValue(expectedValue, HASVALUE);
        if (elementValue == null) {
            return;
        }
        if (!elementValue.contains(expectedValue)) {
            file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.PASS);
    }

    /**
     * Verifies that the element's options contains the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param option the option expected in the list
     */
    public void selectOption(String option) {
        // wait for the select
        if (!isPresentSelect(EXPECTED + element.prettyOutput() + " with the option <b>" + option +
                "</b> available to be selected on the page")) {
            return;
        }
        // check for the object to the editable
        String[] allOptions = element.get().selectOptions();
        if (!Arrays.asList(allOptions).contains(option)) {
            file.recordActual(
                    element.prettyOutputStart() + " is present but does not contain the option <b>" + option + "</b>",
                    Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + " is present and contains the option <b>" + option + "</b>",
                Success.PASS);
    }

    /**
     * Verifies that the element's options contains the provided expected value.
     * If the element isn't present or a select, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param selectValue the expected input value of the element
     */
    public void selectValue(String selectValue) {
        // wait for the select
        if (!isPresentSelect(EXPECTED + element.prettyOutput() + " having a select value of <b>" + selectValue +
                "</b> available to be selected on the page")) {
            return;
        }
        // check for the object to the present on the page
        String[] elementValues = element.get().selectValues();
        if (!Arrays.asList(elementValues).contains(selectValue)) {
            file.recordActual(element.prettyOutputStart() + HASNTVALUE + selectValue + "</b>" + ONLYVALUE +
                    Arrays.toString(elementValues) + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + HASVALUE + selectValue + "</b>", Success.PASS);
    }

    /**
     * Verifies that the element has the expected number of options. If the
     * element isn't present or a select, this will constitute a failure, same
     * as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param numOfOptions the expected number of options in the select element
     */
    public void selectOptions(int numOfOptions) {
        // wait for the select
        if (!isPresentSelect(
                EXPECTED + element.prettyOutput() + " with number of select values equal to <b>" + numOfOptions +
                        "</b>")) {
            return;
        }
        // check for the object to the present on the page
        int elementValues = element.get().numOfSelectOptions();
        if (elementValues != numOfOptions) {
            file.recordActual(element.prettyOutputStart() + " has <b>" + numOfOptions + "</b> select options",
                    Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + " has <b>" + numOfOptions + "</b> select options",
                Success.PASS);
    }

    /**
     * Verifies that the element has the expected number of columns. If the
     * element isn't present or a table, this will constitute a failure, same as
     * a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param numOfColumns the expected number of column elements of a table
     */
    public void columns(int numOfColumns) {
        // wait for the table
        if (!isPresentTable(
                EXPECTED + element.prettyOutput() + " with the number of table columns equal to <b>" + numOfColumns +
                        "</b>")) {
            return;
        }
        int actualNumOfCols = element.get().numOfTableColumns();
        if (actualNumOfCols != numOfColumns) {
            file.recordActual(element.prettyOutputStart() + " does not have the number of columns <b>" + numOfColumns +
                    "</b>. Instead, " + actualNumOfCols + " columns were found", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + " has " + actualNumOfCols + "</b> columns", Success.PASS);
    }

    /**
     * Verifies that the element has the expected number of rows. If the element
     * isn't present or a table, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param numOfRows the expected number of row elements of a table
     */
    public void rows(int numOfRows) {
        // wait for the table
        if (!isPresentTable(
                EXPECTED + element.prettyOutput() + " with the number of table rows equal to <b>" + numOfRows +
                        "</b>")) {
            return;
        }
        int actualNumOfRows = element.get().numOfTableRows();
        if (actualNumOfRows != numOfRows) {
            file.recordActual(element.prettyOutputStart() + " does not have the number of rows <b>" + numOfRows +
                    "</b>. Instead, " + actualNumOfRows + " rows were found", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + " has " + actualNumOfRows + "</b> rows", Success.PASS);
    }
}