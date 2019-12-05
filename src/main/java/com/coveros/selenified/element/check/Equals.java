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

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;

import java.util.Arrays;

import static com.coveros.selenified.utilities.Constants.*;

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
 * @version 3.3.0
 * @lastupdate 8/08/2019
 */
abstract class Equals extends Check {

    private static final String WITH_VALUE = "</i> with a value of <b>";

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
    abstract void matches(int expectedMatches);

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
    int checkMatches(int expectedMatches, double waitFor, double timeTook) {
        // get the value
        int matchCount = this.element.get().matchCount();
        // record the result
        if (matchCount != expectedMatches) {
            this.reporter.fail(this.element.prettyOutput() + " having a match count of <b>" + expectedMatches +
                    ENDB, waitFor, this.element.prettyOutputStart() + " has a match count of <b>" + matchCount + ENDB, timeTook);
        } else {
            this.reporter.pass(this.element.prettyOutput() + " having a match count of <b>" + expectedMatches +
                    ENDB, waitFor, this.element.prettyOutputStart() + " has a match count of <b>" + matchCount + ENDB, timeTook);
        }
        return matchCount;
    }

    /**
     * Checks that the element's tag name equals the provided expected tag name. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     */
    abstract void tagName(String expectedTagName);

    /**
     * Checks that the element's tag name equals the provided expected tag name. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedTagName - the full expected tag name
     * @param waitFor         - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook        - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual tag name of the element. null will be returned if the element isn't present
     */
    String checkTagName(String expectedTagName, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " with tag name <b>" + expectedTagName + ENDB;
        // get the actual tag name value
        String actualTagName = this.element.get().tagName();
        // record the result
        if (!this.element.is().present()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else if (!expectedTagName.equals(actualTagName)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + " has a tag name of <b>" + actualTagName + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + " has a tag name of <b>" + expectedTagName + ENDB, timeTook);
        }
        return actualTagName;
    }

    /**
     * Checks that the element's location equals the provided expected location. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     */
    abstract void location(Point expectedLocation);

    /**
     * Checks that the element's location equals the provided expected location. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedLocation - the full expected tag name
     * @param waitFor          - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook         - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual tag name of the element. null will be returned if the element isn't present
     */
    Point checkLocation(Point expectedLocation, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " with location <b>" + expectedLocation.toString() + ENDB;
        // get the actual location value
        Point actualLocation = this.element.get().location();
        // record the result
        if (!this.element.is().present()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else if (!expectedLocation.equals(actualLocation)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + " has a location of <b>" + actualLocation.toString() + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + " has a location of <b>" + expectedLocation.toString() + ENDB, timeTook);
        }
        return actualLocation;
    }

    /**
     * Checks that the element's size equals the provided expected size. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     */
    abstract void size(Dimension expectedSize);

    /**
     * Checks that the element's size equals the provided expected size. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedSize - the full expected tag name
     * @param waitFor      - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook     - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual tag name of the element. null will be returned if the element isn't present
     */
    Dimension checkSize(Dimension expectedSize, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " with size <b>" + expectedSize.toString() + ENDB;
        // get the actual location value
        Dimension actualSize = this.element.get().size();
        // record the result
        if (!this.element.is().present()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else if (!expectedSize.equals(actualSize)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + " has a size of <b>" + actualSize.toString() + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + " has a size of <b>" + expectedSize.toString() + ENDB, timeTook);
        }
        return actualSize;
    }

    /**
     * Checks that the element's rectangle equals the provided expected rectangle. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     */
    abstract void rectangle(Rectangle expectedRectangle);

    /**
     * Checks that the element's rectangle equals the provided expected rectangle. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedRectangle - the full expected tag name
     * @param waitFor           - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook          - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the actual tag name of the element. null will be returned if the element isn't present
     */
    Rectangle checkRectangle(Rectangle expectedRectangle, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " with rectangle <b>" + expectedRectangle.toString() + ENDB;
        // get the actual location value
        Rectangle actualRectangle = this.element.get().rectangle();
        // record the result
        if (!this.element.is().present()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else if (!expectedRectangle.equals(actualRectangle)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + " has a rectangle of <b>" + actualRectangle.toString() + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + " has a rectangle of <b>" + expectedRectangle.toString() + ENDB, timeTook);
        }
        return actualRectangle;
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
    abstract void cssValue(String attribute, String expectedValue);

    /**
     * Checks that the element has a css attribute with a value equal to the
     * value provided. If the element isn't present, or the css doesn't contain
     * the desired attribute, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param attribute        - the css attribute to be checked
     * @param expectedCssValue the expected css value of the passed attribute of the element
     * @param waitFor          - if waiting, how long to wait for (set to 0 if no wait is desired)
     * @param timeTook         - the amount of time it took for wait for something (assuming we had to wait)
     * @return String: the css value of the attribute passed in. null will be returned if the element's css isn't accessible
     */
    String checkCssValue(String attribute, String expectedCssValue, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " having a css attribute of <i>" + attribute + WITH_VALUE + expectedCssValue + ENDB;
        // get the actual css element value
        String actualCssValue = this.element.get().css(attribute);
        // record the result
        if (!this.element.is().present()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else if (actualCssValue == null) {
            this.reporter.fail(check, waitFor, "Unable to assess the css of " + this.element.prettyOutputEnd().trim(), timeTook);
        } else if (!expectedCssValue.equals(actualCssValue)) {
            this.reporter.fail(check, waitFor,
                    this.element.prettyOutputStart() + " has a css attribute of <i>" + attribute + WITH + actualCssValue +
                            ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor,
                    this.element.prettyOutputStart() + " has a css attribute of <i>" + attribute + WITH + actualCssValue +
                            ENDB, timeTook);
        }
        return actualCssValue;
    }

    /**
     * Checks that the element's class equals the provided expected class. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. This information will be logged and recorded, with a screenshot
     * for traceability and added debugging support.
     *
     * @param expectedClass - the full expected class value
     */
    abstract void clazz(String expectedClass);

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
    String checkClazz(String expectedClass, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " with class <b>" + expectedClass + ENDB;
        // get the actual class value
        String actualClass = this.element.get().attribute(CLASS);
        // record the result
        if (!this.element.is().present()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else if (actualClass == null) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + " does not have a class attribute", timeTook);
        } else if (!expectedClass.equals(actualClass)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + CLASS_VALUE + actualClass + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + CLASS_VALUE + expectedClass + ENDB, timeTook);
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
    abstract void attribute(String attribute, String expectedValue);

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
    String checkAttribute(String attribute, String expectedValue, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " having an attribute of <i>" + attribute + WITH_VALUE +
                expectedValue + ENDB;
        // get the actual attribute value
        String elementValue = this.element.get().attribute(attribute);
        // record the result
        if (!this.element.is().present()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else if (elementValue == null) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + " does not have an attribute of <i>" + attribute + "</i>", timeTook);
        } else if (!elementValue.equals(expectedValue)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + " has an attribute of <i>" + attribute + WITH + elementValue + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + " has an attribute of <i>" + attribute + WITH + elementValue + ENDB, timeTook);
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
    abstract void text(String expectedText);

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
    String checkText(String expectedText, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " having text of <b>" + expectedText + ENDB;
        // check for the object to the present on the page
        String elementText = this.element.get().text();
        // record the result
        if (!this.element.is().present()) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + IS_NOT_PRESENT, timeTook);
        } else if (!expectedText.equals(elementText)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + HAS_TEXT + elementText + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + HAS_TEXT + elementText + ENDB, timeTook);
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
    abstract void text(int row, int col, String expectedText);

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
    String checkText(int row, int col, String expectedText, double waitFor, double timeTook) {
        String column = " and column ";
        String within = " within element ";
        String check = "cell at row " + row + column + col + within + this.element.prettyOutput() +
                " to have the text of <b>" + expectedText + ENDB;
        // record the action, and check for table
        if (isNotPresentTable(check, waitFor) || doesCellNotExist(row, col, check, waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the table cell text
        String actualText = this.element.get().tableCell(row, col).get().text();
        // record the result
        if (!actualText.equals(expectedText)) {
            this.reporter.fail(check, waitFor, "Cell at row " + row + column + col + within + this.element.prettyOutput() +
                    HAS_TEXT + actualText + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, "Cell at row " + row + column + col + within + this.element.prettyOutput() +
                    HAS_TEXT + actualText + ENDB, timeTook);
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
    abstract void value(String expectedValue);

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
    String checkValue(String expectedValue, double waitFor, double timeTook) {
        String check = this.element.prettyOutputStart() + " having value of <b>" + expectedValue + ENDB;
        // record the action and check this is an input element
        if (isNotPresentInput(check, waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the element value
        String elementValue = this.element.get().value();
        // record the result
        if (!elementValue.equals(expectedValue)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + HAS_VALUE + elementValue + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + HAS_VALUE + elementValue + ENDB, timeTook);
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
    abstract void selectedOption(String expectedText);

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
    String checkSelectedOption(String expectedText, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " having a selected option of <b>" + expectedText + ENDB;
        // record the action and check it's a select
        if (isNotPresentSelect(check, waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the selected text
        String elementText = this.element.get().selectedOption();
        // record the result
        if (!elementText.equals(expectedText)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + HAS_OPTION + elementText + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + HAS_OPTION + elementText + ENDB, timeTook);
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
    abstract void selectedValue(String expectedValue);

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
    String checkSelectedValue(String expectedValue, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " having a selected value of <b>" + expectedValue + ENDB;
        // record the action and check it's a select
        if (isNotPresentSelect(check, waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the selected value
        String elementValue = this.element.get().selectedValue();
        // record the result
        if (!elementValue.equals(expectedValue)) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + HAS_VALUE + elementValue + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + HAS_VALUE + elementValue + ENDB, timeTook);
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
    abstract void selectOptions(String... expectedOptions);

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
    String[] checkSelectOptions(String[] expectedOptions, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " with select options of <b>" + String.join("</b>, <b>" + expectedOptions) + ENDB;
        // record the action, and check it's a select
        if (isNotPresentSelect(check, waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the actual select options
        String[] elementOptions = this.element.get().selectOptions();
        // record the result
        if (!Arrays.toString(elementOptions).equals(Arrays.toString(expectedOptions))) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + OPTIONS + Arrays.toString(elementOptions) + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + OPTIONS + Arrays.toString(elementOptions) + ENDB, timeTook);
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
    abstract void selectValues(String... expectedValues);

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
    String[] checkSelectValues(String[] expectedValues, double waitFor, double timeTook) {
        String check = this.element.prettyOutput() + " with select values of <b>" + Arrays.toString(expectedValues) + ENDB;
        // record the action, and check it's a select
        if (isNotPresentSelect(check, waitFor)) {
            return null;    // returning null to indicate that element isn't present/select, instead of indicating no options exist
        }
        // get the actual select values
        String[] elementValues = this.element.get().selectValues();
        // record the result
        if (!Arrays.toString(elementValues).equals(Arrays.toString(expectedValues))) {
            this.reporter.fail(check, waitFor, this.element.prettyOutputStart() + VALUES + Arrays.toString(elementValues) + ENDB, timeTook);
        } else {
            this.reporter.pass(check, waitFor, this.element.prettyOutputStart() + VALUES + Arrays.toString(elementValues) + ENDB, timeTook);
        }
        return elementValues;
    }
}