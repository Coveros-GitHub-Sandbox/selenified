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

import com.coveros.selenified.element.Element;
import com.coveros.selenified.utilities.Reporter;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;

/**
 * VerifyEquals implements Equals to provide some additional verification capabilities.
 * It will handle all verifications performed on the actual element. These
 * asserts are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each verification to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. Equals checks that elements have a particular value associated
 * to them.
 *
 * @author Max Saperstone
 * @version 3.2.2
 * @lastupdate 8/08/2019
 */
public class VerifyEquals extends Equals {

    /**
     * The default constructor passing in the element and output file
     *
     * @param element  - the element under test
     * @param reporter - the file to write all logging out to
     */
    public VerifyEquals(Element element, Reporter reporter) {
        this.element = element;
        this.reporter = reporter;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * Verifies that the element has a the expected number of matches on the page, e.g.
     * how many elements match the locator and target provided.
     * This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedMatches the expected number of elements matching the locator
     */
    public void matches(int expectedMatches) {
        checkMatches(expectedMatches, 0, 0);
    }

    /**
     * Asserts that the element's tag name equals the provided expected tag name. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     */
    public void tagName(String expectedTagName) {
        checkTagName(expectedTagName, 0, 0);
    }

    /**
     * Asserts that the element's location equals the provided expected location. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     */
    public void location(Point expectedLocation) {
        checkLocation(expectedLocation, 0, 0);
    }

    /**
     * Asserts that the element's size equals the provided expected size. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     */
    public void size(Dimension expectedSize) {
        checkSize(expectedSize, 0, 0);
    }

    /**
     * Asserts that the element's rectangle equals the provided expected rectangle. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     */
    public void rectangle(Rectangle expectedRectangle) {
        checkRectangle(expectedRectangle, 0, 0);
    }

    /**
     * Verifies that the element has a css attribute with a value equal to the
     * value provided. If the element isn't present, or the css doesn't contain
     * the desired attribute, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param attribute     - the css attribute to be checked
     * @param expectedValue the expected css value of the passed attribute of the element
     */
    public void cssValue(String attribute, String expectedValue) {
        checkCssValue(attribute, expectedValue, 0, 0);
    }

    /**
     * Verifies that the element's class equals the provided expected class. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedClass - the full expected class value
     */
    public void clazz(String expectedClass) {
        checkClazz(expectedClass, 0, 0);
    }

    /**
     * Verifies that the element has an attribute with a value equals to the
     * value provided. If the element isn't present, or the element does not
     * have the attribute, this will constitute a failure, same as a mismatch.
     * This information will be logged and recorded, with a screenshot for
     * traceability and added debugging support.
     *
     * @param attribute     - the attribute to be checked
     * @param expectedValue the expected value of the passed attribute of the element
     */
    public void attribute(String attribute, String expectedValue) {
        checkAttribute(attribute, expectedValue, 0, 0);
    }

    /**
     * Verifies that the element's text equals the provided expected text. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedText the expected value of the element
     */
    public void text(String expectedText) {
        checkText(expectedText, 0, 0);
    }

    /**
     * Verifies that the element's text in a particular cell equals the provided
     * expected text. If the element isn't present, or a table, this will
     * constitute a failure, same as a mismatch. This information will be logged
     * and recorded, with a screenshot for traceability and added debugging
     * support.
     *
     * @param row  - the number of the row in the table - note, row numbering
     *             starts at 1, NOT 0
     * @param col  - the number of the column in the table - note, column
     *             numbering starts at 1, NOT 0
     * @param text - what text do we expect to be in the table cell
     */
    public void text(int row, int col, String text) {
        checkText(row, col, text, 0, 0);
    }

    /**
     * Verifies that the element's value equals the provided expected value. If
     * the element isn't present or an input, this will constitute a failure,
     * same as a mismatch. This information will be logged and recorded, with a
     * screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     */
    public void value(String expectedValue) {
        checkValue(expectedValue, 0, 0);
    }

    /**
     * Verifies that the element's selected option equals the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedText the expected input text of the element
     */
    public void selectedOption(String expectedText) {
        checkSelectedOption(expectedText, 0, 0);
    }

    /**
     * Verifies that the element's selected value equals the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     */
    public void selectedValue(String expectedValue) {
        checkSelectedValue(expectedValue, 0, 0);
    }

    /**
     * Verifies that the element's select options equal the provided expected
     * options. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedOptions the expected input value of the element
     */
    public void selectOptions(String... expectedOptions) {
        checkSelectOptions(expectedOptions, 0, 0);
    }

    /**
     * Verifies that the element's select values equal the provided expected
     * values. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch. This information will be logged and
     * recorded, with a screenshot for traceability and added debugging support.
     *
     * @param expectedValues the expected input value of the element
     */
    public void selectValues(String... expectedValues) {
        checkSelectValues(expectedValues, 0, 0);
    }
}