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

package com.coveros.selenified.element.check.wait;

import com.coveros.selenified.OutputFile;
import com.coveros.selenified.element.Element;
import com.coveros.selenified.element.check.Constants;
import com.coveros.selenified.element.check.Equals;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;

import static com.coveros.selenified.element.check.Constants.CLASS;
import static com.coveros.selenified.element.check.Constants.DEFAULT_POLLING_INTERVAL;
import static com.coveros.selenified.element.check.Constants.ELEMENT_NOT_SELECT;

/**
 * WaitForEquals implements Equals to provide some additional wait capabilities.
 * It will handle all waits performed on the actual element. These
 * waits are custom to the framework, and in addition to providing easy object
 * oriented capabilities, they take screenshots with each failed wait to
 * provide additional traceability, and assist in troubleshooting and debugging
 * failing tests. Equals checks that elements have a particular value associated
 * to them.
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 2/21/2019
 */
public class WaitForEquals implements Equals {

    // this will be the name of the file we write all commands out to
    private final OutputFile file;

    // this is the element that all actions will be performed on
    private final Element element;

    // the default wait for the system
    private double defaultWait = 5.0;

    public WaitForEquals(Element element, OutputFile file) {
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

    /**
     * Changes the default wait time from 5.0 seconds to some custom number.
     *
     * @param seconds - how many seconds should WaitFor wait for the condition to be
     *                met
     */
    public void changeDefaultWait(double seconds) {
        defaultWait = seconds;
    }

    // ///////////////////////////////////////
    // waiting functionality
    // ///////////////////////////////////////

    /**
     * Waits for the element has a the expected number of matches on the page, e.g.
     * how many elements match the locator and target provided.
     * The default wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedMatches the expected number of elements matching the locator
     */
    public void matches(int expectedMatches) {
        matches(expectedMatches, defaultWait);
    }

    /**
     * Waits for the element has a css attribute with a value equal to the
     * value provided. If the element isn't present, or the css doesn't contain
     * the desired attribute, this will constitute a failure, same as a
     * mismatch. The default wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param attribute     - the css attribute to be checked
     * @param expectedValue the expected css value of the passed attribute of the element
     */
    public void cssValue(String attribute, String expectedValue) {
        cssValue(attribute, expectedValue, defaultWait);
    }

    /**
     * Waits for the element's class equals the provided expected class. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. The default wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedClass - the full expected class value
     */
    public void clazz(String expectedClass) {
        clazz(expectedClass, defaultWait);
    }

    /**
     * Waits for the element has an attribute with a value equals to the
     * value provided. If the element isn't present, or the element does not
     * have the attribute, this will constitute a failure, same as a mismatch.
     * The default wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param attribute     - the attribute to be checked
     * @param expectedValue the expected value of the passed attribute of the element
     */
    public void attribute(String attribute, String expectedValue) {
        attribute(attribute, expectedValue, defaultWait);
    }

    /**
     * Waits for the element's text equals the provided expected text. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. The default wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedText the expected value of the element
     */
    public void text(String expectedText) {
        text(expectedText, defaultWait);
    }

    /**
     * Waits for the element's text in a particular cell equals the provided
     * expected text. If the element isn't present, or a table, this will
     * constitute a failure, same as a mismatch.
     * The default wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param row  - the number of the row in the table - note, row numbering
     *             starts at 1, NOT 0
     * @param col  - the number of the column in the table - note, column
     *             numbering starts at 1, NOT 0
     * @param text - what text do we expect to be in the table cell
     */
    public void text(int row, int col, String text) {
        text(row, col, text, defaultWait);
    }

    /**
     * Waits for the element's value equals the provided expected value. If
     * the element isn't present or an input, this will constitute a failure,
     * same as a mismatch.
     * The default wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     */
    public void value(String expectedValue) {
        value(expectedValue, defaultWait);
    }

    /**
     * Waits for the element's selected option equals the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch.
     * The default wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedText the expected input text of the element
     */
    public void selectedOption(String expectedText) {
        selectedOption(expectedText, defaultWait);
    }

    /**
     * Waits for the element's selected value equals the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch.
     * The default wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue the expected input value of the element
     */
    public void selectedValue(String expectedValue) {
        selectedValue(expectedValue, defaultWait);
    }

    /**
     * Waits for the element's select options equal the provided expected
     * options. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch.
     * The default wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedOptions the expected input value of the element
     */
    public void selectOptions(String... expectedOptions) {
        selectOptions(expectedOptions, defaultWait);
    }

    /**
     * Waits for the element's select values equal the provided expected
     * values. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch.
     * The default wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedValues the expected input value of the element
     */
    public void selectValues(String... expectedValues) {
        selectValues(expectedValues, defaultWait);
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    /**
     * Waits for the element has a the expected number of matches on the page, e.g.
     * how many elements match the locator and target provided.
     * The provided wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedMatches - the expected number of elements matching the locator
     * @param seconds         - how many seconds to wait for
     */
    public void matches(int expectedMatches, double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            if (expectedMatches > 0) {
                elementPresent(seconds);
            }
            while (element.get().matchCount() != expectedMatches && System.currentTimeMillis() < end) ;
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkMatches(expectedMatches, seconds, timeTook);
            if (element.get().matchCount() != expectedMatches) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkMatches(expectedMatches, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element has a css attribute with a value equal to the
     * value provided. If the element isn't present, or the css doesn't contain
     * the desired attribute, this will constitute a failure, same as a
     * mismatch. The provided wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param attribute     - the css attribute to be checked
     * @param expectedValue - the expected css value of the passed attribute of the element
     * @param seconds       - how many seconds to wait for
     */
    public void cssValue(String attribute, String expectedValue, double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            elementPresent(seconds);
            while (!expectedValue.equals(element.get().css(attribute)) && System.currentTimeMillis() < end) ;
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkCssValue(attribute, expectedValue, seconds, timeTook);
            if (!expectedValue.equals(element.get().css(attribute))) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkCssValue(attribute, expectedValue, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element's class equals the provided expected class. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. The provided wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedClass - the full expected class value
     * @param seconds       - how many seconds to wait for
     */
    public void clazz(String expectedClass, double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            elementPresent(seconds);
            String clazz = element.get().attribute(CLASS);
            while (!(expectedClass == null ? clazz == null : expectedClass.equals(clazz)) && System.currentTimeMillis() < end)
                ;
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkClazz(expectedClass, seconds, timeTook);
            if (!(expectedClass == null ? clazz == null : expectedClass.equals(clazz))) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkClazz(expectedClass, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element has an attribute with a value equals to the
     * value provided. If the element isn't present, or the element does not
     * have the attribute, this will constitute a failure, same as a mismatch.
     * The provided wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param attribute     - the attribute to be checked
     * @param expectedValue - the expected value of the passed attribute of the element
     * @param seconds       - how many seconds to wait for
     */
    public void attribute(String attribute, String expectedValue, double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            elementPresent(seconds);
            while (!expectedValue.equals(element.get().attribute(attribute)) && System.currentTimeMillis() < end) ;
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkAttribute(attribute, expectedValue, seconds, timeTook);
            if (!expectedValue.equals(element.get().attribute(attribute))) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkAttribute(attribute, expectedValue, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element's text equals the provided expected text. If
     * the element isn't present, this will constitute a failure, same as a
     * mismatch. The provided wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedText - the expected value of the element
     * @param seconds      - how many seconds to wait for
     */
    public void text(String expectedText, double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            double timeTook = elementPresent(seconds);
            WebDriverWait wait = new WebDriverWait(element.getDriver(), (long) (seconds - timeTook), DEFAULT_POLLING_INTERVAL);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(element.defineByElement(), expectedText));
            timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkText(expectedText, seconds, timeTook);
        } catch (TimeoutException e) {
            checkText(expectedText, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element's text in a particular cell equals the provided
     * expected text. If the element isn't present, or a table, this will
     * constitute a failure, same as a mismatch.
     * The provided wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param row          - the number of the row in the table - note, row numbering
     *                     starts at 1, NOT 0
     * @param col          - the number of the column in the table - note, column
     *                     numbering starts at 1, NOT 0
     * @param expectedText - what text do we expect to be in the table cell
     * @param seconds      - how many seconds to wait for
     */
    public void text(int row, int col, String expectedText, double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            elementPresent(seconds);
            while (!element.get().tableCell(row, col).get().text().equals(expectedText) && System.currentTimeMillis() < end) ;
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkText(row, col, expectedText, seconds, timeTook);
            if (!element.get().tableCell(row, col).get().text().equals(expectedText)) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkText(row, col, expectedText, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element's value equals the provided expected value. If
     * the element isn't present or an input, this will constitute a failure,
     * same as a mismatch.
     * The provided wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue - the expected input value of the element
     * @param seconds       - how many seconds to wait for
     */
    public void value(String expectedValue, double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            double timeTook = elementPresent(seconds);
            WebDriverWait wait = new WebDriverWait(element.getDriver(), (long) (seconds - timeTook), DEFAULT_POLLING_INTERVAL);
            wait.until(ExpectedConditions.textToBePresentInElementValue(element.defineByElement(), expectedValue));
            timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkValue(expectedValue, seconds, timeTook);
        } catch (TimeoutException e) {
            checkValue(expectedValue, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element's selected option equals the provided expected
     * option. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch.
     * The provided wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedText - the expected input text of the element
     * @param seconds      - how many seconds to wait for
     */
    public void selectedOption(String expectedText, double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            elementPresent(seconds);
            if( !element.is().select()) {
                throw new TimeoutException(ELEMENT_NOT_SELECT);
            }
            while (!element.get().selectedOption().equals(expectedText) && System.currentTimeMillis() < end) ;
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkSelectedOption(expectedText, seconds, timeTook);
            if (!element.get().selectedOption().equals(expectedText)) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkSelectedOption(expectedText, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element's selected value equals the provided expected
     * value. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch.
     * The provided wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedValue - the expected input value of the element
     * @param seconds       - how many seconds to wait for
     */
    public void selectedValue(String expectedValue, double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            elementPresent(seconds);
            if( !element.is().select()) {
                throw new TimeoutException(ELEMENT_NOT_SELECT);
            }
            while (!element.get().selectedValue().equals(expectedValue) && System.currentTimeMillis() < end) ;
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkSelectedValue(expectedValue, seconds, timeTook);
            if (!element.get().selectedValue().equals(expectedValue)) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkSelectedValue(expectedValue, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element's select options equal the provided expected
     * options. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch.
     * The provided wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedOptions - the expected input value of the element
     * @param seconds         - how many seconds to wait for
     */
    public void selectOptions(String[] expectedOptions, double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            elementPresent(seconds);
            if( !element.is().select()) {
                throw new TimeoutException(ELEMENT_NOT_SELECT);
            }
            while (!Arrays.toString(element.get().selectOptions()).equals(Arrays.toString(expectedOptions)) && System.currentTimeMillis() < end)
                ;
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkSelectOptions(expectedOptions, seconds, timeTook);
            if (!Arrays.toString(element.get().selectOptions()).equals(Arrays.toString(expectedOptions))) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkSelectOptions(expectedOptions, seconds, seconds);
            file.addError();
        }
    }

    /**
     * Waits for the element's select values equal the provided expected
     * values. If the element isn't present or a select, this will constitute a
     * failure, same as a mismatch.
     * The provided wait time will be used and if the element doesn't
     * have the desired match count at that time, it will fail, and log
     * the issue with a screenshot for traceability and added debugging support.
     *
     * @param expectedValues - the expected input value of the element
     * @param seconds        - how many seconds to wait for
     */
    public void selectValues(String[] expectedValues, double seconds) {
        double end = System.currentTimeMillis() + (seconds * 1000);
        try {
            elementPresent(seconds);
            if( !element.is().select()) {
                throw new TimeoutException(ELEMENT_NOT_SELECT);
            }
            while (!Arrays.toString(element.get().selectValues()).equals(Arrays.toString(expectedValues)) && System.currentTimeMillis() < end)
                ;
            double timeTook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000) / 1000;
            checkSelectValues(expectedValues, seconds, timeTook);
            if (!Arrays.toString(element.get().selectValues()).equals(Arrays.toString(expectedValues))) {
                file.addError();
            }
        } catch (TimeoutException e) {
            checkSelectValues(expectedValues, seconds, seconds);
            file.addError();
        }
    }
}