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

package com.coveros.selenified.element.check.azzert;

import com.coveros.selenified.OutputFile;
import com.coveros.selenified.element.Element;
import com.coveros.selenified.element.check.Matches;

import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Matches extends Asserts to provide some additional verification capabilities.
 * It will handle all verifications performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each verification to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. Matches checks that elements have a particular value associated
 * to them that fits the provided regular expression.
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 2/21/2019
 */
public class AssertMatches implements Matches {

    // this will be the name of the file we write all commands out to
    private final OutputFile file;

    // this is the element that all actions will be performed on
    private final Element element;

    public AssertMatches(Element element, OutputFile file) {
        this.element = element;
        this.file = file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OutputFile getOutputFile() {
        return file;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element getElement() {
        return element;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Verifies that the element's text matches the regular expression pattern provided. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedPattern the expected pattern of the text of the element
     */
    public void text(String expectedPattern) {
        String text = checkText(expectedPattern, 0, 0);
        assertNotNull("No element found", text);
        assertTrue("Text Mismatch: text of '" + text + "' doesn't match pattern '" + expectedPattern + "'", text.matches(expectedPattern));
    }

    /**
     * Verifies that the element's pattern in a particular cell matches the regular expression
     * pattern provided. If the element isn't present, or a table, this will
     * constitute a failure, same as a mismatch. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param row     - the number of the row in the table - note, row numbering
     *                starts at 1, NOT 0
     * @param col     - the number of the column in the table - note, column
     *                numbering starts at 1, NOT 0
     * @param pattern - what pattern do we expect to be in the table cell
     */
    public void text(int row, int col, String pattern) {
        String text = checkText(row, col, pattern, 0, 0);
        assertNotNull("No element found", text);
        assertTrue("Text Mismatch: text of '" + text + "' doesn't match pattern '" + pattern + "'", text.matches(pattern));
    }

    /**
     * Verifies that the element's value matches the regular expression pattern
     * provided. If the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input value of the element
     */
    public void value(String expectedPattern) {
        String value = checkValue(expectedPattern, 0, 0);
        assertNotNull("No element found", value);
        assertTrue("Value Mismatch: value of '" + value + "' doesn't match pattern '" + expectedPattern + "'", value.matches(expectedPattern));
    }

    /**
     * Verifies that the element's selected option matches the regular expression pattern
     * provided. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input text of the element
     */
    public void selectedOption(String expectedPattern) {
        String selectedOption = checkSelectedOption(expectedPattern, 0, 0);
        assertNotNull("No element found", selectedOption);
        assertTrue("Selected Option Mismatch: option of '" + selectedOption + "' doesn't match pattern '" + expectedPattern + "'",
                selectedOption.matches(expectedPattern));
    }

    /**
     * Verifies that the element's selected value  matches the regular expression pattern
     * provided. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedPattern the expected input value of the element
     */
    public void selectedValue(String expectedPattern) {
        String selectedValue = checkSelectedValue(expectedPattern, 0, 0);
        assertNotNull("No element found", selectedValue);
        assertTrue("Selected Value Mismatch: value of '" + selectedValue + "' doesn't match pattern '" + expectedPattern + "'",
                selectedValue.matches(expectedPattern));
    }
}