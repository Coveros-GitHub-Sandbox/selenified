/*
 * Copyright 2017 Coveros, Inc.
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

package com.coveros.selenified.output;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.coveros.selenified.output.Assert.Success;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.General;

/**
 * Test Output A custom generated output file outputFile.recording all actions
 * taken
 *
 * @author Max Saperstone
 * @version 2.0.0
 * @lastupdate 5/15/2017
 */
public class LocatorAssert {

    private Action action;
    private OutputFile outputFile;

    // constants
    private static final String EXPECTED = "Expected to find element with ";
    private static final String ELEMENT = "The element with ";
    private static final String CLASS = "class";

    private static final String CHECKED = "</i> is checked on the page";
    private static final String PRESENT = "</i> is not present on the page";

    private static final String NOTCHECKED = "</i> is not checked on the page";
    private static final String NOTINPUT = "</i> is not an input on the page";
    private static final String NOTEDITABLE = "</i> is not editable on the page";

    private static final String VALUE = "</i> has the value of <b>";
    private static final String HASVALUE = "</i> contains the value of <b>";
    private static final String ONLYVALUE = ", only the values <b>";
    private static final String CLASSVALUE = "</i> has a class value of <b>";
    private static final String IS = "</i> is ";

    public LocatorAssert(Action action, OutputFile outputFile) {
        this.action = action;
        this.outputFile = outputFile;
    }

    // /////////////////////////////////////////////////////////////////////////
    // a bunch of methods to negatively check for objects using selenium calls
    // ///////////////////////////////////////////////////////////////////////

    /**
     * checks to see if an element is visible on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementDisplayed(Locator type, String locator, int elementMatch) throws IOException {
        // wait for the element
        if (!action.isElementDisplayed(type, locator, elementMatch)
                && action.waitForElementDisplayed(type, locator, elementMatch) == 1) {
            return 1;
        }
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> visible on the page");
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is present and visible on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element is not visible on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementNotDisplayed(Locator type, String locator, int elementMatch) throws IOException {
        // wait for the element
        if (action.isElementDisplayed(type, locator, elementMatch)
                && action.waitForElementNotDisplayed(type, locator, elementMatch) == 1) {
            return 1;
        }
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> present, but not visible on the page");
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is present and not visible on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an object is checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementChecked(Locator type, String locator, int elementMatch) throws IOException {
        // wait for the element
        if (!action.isElementPresent(type, locator, elementMatch)
                && action.waitForElementPresent(type, locator, elementMatch) == 1) {
            return 1;
        }
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + CHECKED);
        // check for the object to the visible
        if (!action.isElementChecked(type, locator, elementMatch)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTCHECKED, Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + CHECKED, Success.PASS);
        return 0;
    }

    /**
     * checks to see if an object is not checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementNotChecked(Locator type, String locator, int elementMatch) throws IOException {
        // wait for the element
        if (!action.isElementPresent(type, locator, elementMatch)
                && action.waitForElementPresent(type, locator, elementMatch) == 1) {
            return 1;
        }
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + NOTCHECKED);
        // check for the object to the visible
        if (action.isElementChecked(type, locator, elementMatch)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> checked on the page", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTCHECKED, Success.PASS);
        return 0;
    }

    /**
     * checks to see if an object is visible and checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementDisplayedAndChecked(Locator type, String locator, int elementMatch) throws IOException {
        // wait for the element
        if (!action.isElementDisplayed(type, locator, elementMatch)
                && action.waitForElementDisplayed(type, locator, elementMatch) == 1) {
            return 1;
        }
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + CHECKED);
        // check for the object to the visible
        if (!action.isElementChecked(type, locator, elementMatch)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTCHECKED, Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is checked and visible on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an object is visible and not checked on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementDisplayedAndUnchecked(Locator type, String locator, int elementMatch) throws IOException {
        // wait for the element
        if (!action.isElementDisplayed(type, locator, elementMatch)
                && action.waitForElementDisplayed(type, locator, elementMatch) == 1) {
            return 1;
        }
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + NOTCHECKED);
        // check for the object to the visible
        if (action.isElementChecked(type, locator, elementMatch)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + CHECKED, Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is not checked and visible on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if the actual element is editable
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param presence
     *            - what additional attribute is expected from the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    private int checkEditable(Locator type, String locator, int elementMatch, String presence) throws IOException {
        // check for the object to the editable
        if (!action.isElementInput(type, locator, elementMatch)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + IS + presence + " but not an input on the page",
                    Success.FAIL);
            return 1;
        }
        if (!action.isElementEnabled(type, locator, elementMatch)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + IS + presence + " but not editable on the page",
                    Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + IS + presence + " and editable on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if the actual element is editable
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param presence
     *            - what additional attribute is expected from the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    private int checkNotEditable(Locator type, String locator, int elementMatch, String presence) throws IOException {
        // check for the object to the editable
        boolean isElementEnabled = false;
        if (action.isElementInput(type, locator, elementMatch)) {
            isElementEnabled = action.isElementEnabled(type, locator, elementMatch);
        }
        if (isElementEnabled) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + IS + presence + " but editable on the page",
                    Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + IS + presence + " and not editable on the page",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element is editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementEditable(Locator type, String locator, int elementMatch) throws IOException {
        // wait for the element
        if (!action.isElementPresent(type, locator, elementMatch)
                && action.waitForElementPresent(type, locator, elementMatch) == 1) {
            return 1;
        }
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> editable on the page");
        return checkEditable(type, locator, elementMatch, "present");
    }

    /**
     * checks to see if an element is not editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementNotEditable(Locator type, String locator, int elementMatch) throws IOException {
        // wait for the element
        if (!action.isElementPresent(type, locator, elementMatch)
                && action.waitForElementPresent(type, locator, elementMatch) == 1) {
            return 1;
        }
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> not editable on the page");
        return checkNotEditable(type, locator, elementMatch, "present");
    }

    /**
     * checks to see if an element is visible and editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementDisplayedAndEditable(Locator type, String locator, int elementMatch) throws IOException {
        // wait for the element
        if (!action.isElementDisplayed(type, locator, elementMatch)
                && action.waitForElementDisplayed(type, locator, elementMatch) == 1) {
            return 1;
        }
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> visible and editable on the page");
        return checkEditable(type, locator, elementMatch, "visable");
    }

    /**
     * checks to see if an element is visible and not editable on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementDisplayedAndNotEditable(Locator type, String locator, int elementMatch) throws IOException {
        // wait for the element
        if (!action.isElementDisplayed(type, locator, elementMatch)
                && action.waitForElementDisplayed(type, locator, elementMatch) == 1) {
            return 1;
        }
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> visible and not editable on the page");
        return checkNotEditable(type, locator, elementMatch, "visible");
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementHasAttribute(Locator type, String locator, int elementMatch, String attribute)
            throws IOException {
        // wait for the element
        if (!action.isElementPresent(type, locator, elementMatch)
                && action.waitForElementPresent(type, locator, elementMatch) == 1) {
            return 1;
        }
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> with attribute <b>" + attribute + "</b>");
        Map<String, String> attributes = action.getAllAttributes(type, locator, elementMatch);
        Set<String> keys = attributes.keySet();
        String[] array = keys.toArray(new String[keys.size()]);
        // outputFile.record the action
        if (General.doesArrayContain(array, attribute)) {
            outputFile.recordActual(
                    ELEMENT + type + " <i>" + locator + "</i> contains the attribute of <b>" + attribute + "</b>",
                    Success.PASS);
            return 0;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not contain the attribute of <b>"
                + attribute + "</b>" + ONLYVALUE + Arrays.toString(array) + "</b>", Success.FAIL);
        return 1;
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementDoesntHaveAttribute(Locator type, String locator, int elementMatch, String attribute)
            throws IOException {
        // wait for the element
        if (!action.isElementPresent(type, locator, elementMatch)
                && action.waitForElementPresent(type, locator, elementMatch) == 1) {
            return 1;
        }
        outputFile
                .recordExpected(EXPECTED + type + " <i>" + locator + "</i> without attribute <b>" + attribute + "</b>");
        Map<String, String> attributes = action.getAllAttributes(type, locator, elementMatch);
        Set<String> keys = attributes.keySet();
        String[] array = keys.toArray(new String[keys.size()]);
        // outputFile.record the action
        if (General.doesArrayContain(array, attribute)) {
            outputFile.recordActual(
                    ELEMENT + type + " <i>" + locator + "</i> contains the attribute of <b>" + attribute + "</b>",
                    Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not contain the attribute of <b>"
                + attribute + "</b>" + ONLYVALUE + Arrays.toString(array) + "</b>", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element has a particular class
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedClass
     *            - the full expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementHasClass(Locator type, String locator, int elementMatch, String expectedClass)
            throws IOException {
        // wait for the element
        if (!action.isElementPresent(type, locator, elementMatch)
                && action.waitForElementPresent(type, locator, elementMatch) == 1) {
            return 1;
        }
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> with class <b>" + expectedClass + "</b>");
        String actualClass = action.getAttribute(type, locator, elementMatch, CLASS);
        // outputFile.record the action
        if (actualClass.equals(expectedClass)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + CLASSVALUE + expectedClass + "</b>",
                    Success.PASS);
            return 0;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + CLASSVALUE + actualClass + "</b>", Success.FAIL);
        return 1;
    }

    /**
     * checks to see if an element contains a particular class
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedClass
     *            - the expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementContainsClass(Locator type, String locator, int elementMatch, String expectedClass)
            throws IOException {
        // wait for the element
        if (!action.isElementPresent(type, locator, elementMatch)
                && action.waitForElementPresent(type, locator, elementMatch) == 1) {
            return 1;
        }
        outputFile.recordExpected(
                EXPECTED + type + " <i>" + locator + "</i> containing class <b>" + expectedClass + "</b>");
        String actualClass = action.getAttribute(type, locator, elementMatch, CLASS);
        // outputFile.record the action
        if (actualClass.contains(expectedClass)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + CLASSVALUE + actualClass
                    + "</b>, which contains <b>" + expectedClass + "</b>", Success.PASS);
            return 0;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + CLASSVALUE + actualClass + "</b>", Success.FAIL);
        return 1;
    }

    /**
     * checks to see if an element does not contain a particular class
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param unexpectedClass
     *            - the unexpected class value
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkElementDoesntContainClass(Locator type, String locator, int elementMatch, String unexpectedClass)
            throws IOException {
        // wait for the element
        if (!action.isElementPresent(type, locator, elementMatch)
                && action.waitForElementPresent(type, locator, elementMatch) == 1) {
            return 1;
        }
        outputFile.recordExpected(
                EXPECTED + type + " <i>" + locator + "</i> without class <b>" + unexpectedClass + "</b>");
        String actualClass = action.getAttribute(type, locator, elementMatch, CLASS);
        // outputFile.record the action
        if (actualClass.contains(unexpectedClass)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + CLASSVALUE + actualClass
                    + "</b>, which contains <b>" + unexpectedClass + "</b>", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not contain a class value of <b>"
                + unexpectedClass + "</b>", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an option is available to be selected on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param option
     *            the option expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkIfOptionInSelect(Locator type, String locator, int elementMatch, String option) throws IOException {
        // wait for the element
        if (!action.isElementEnabled(type, locator, elementMatch)
                && action.waitForElementEnabled(type, locator, elementMatch) == 1) {
            return 1;
        }
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> with the option <b>" + option
                + "</b> available to be" + " selected on the page");
        // check for the object to the editable
        String[] allOptions = action.getSelectOptions(type, locator, elementMatch);
        if (!Arrays.asList(allOptions).contains(option)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator
                    + "</i> is editable and present but does not contain the option " + "<b>" + option + "</b>",
                    Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator
                + "</i> is editable and present and contains the option " + "<b>" + option + "</b>", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an option is not available to be selected on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param option
     *            the option not expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkIfOptionNotInSelect(Locator type, String locator, int elementMatch, String option)
            throws IOException {
        // wait for the element
        if (!action.isElementEnabled(type, locator, elementMatch)
                && action.waitForElementEnabled(type, locator, elementMatch) == 1) {
            return 1;
        }
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> without the option <b>" + option
                + "</b> available to be" + " selected on the page");
        // check for the object to the editable
        String[] allOptions = action.getSelectOptions(type, locator, elementMatch);
        if (Arrays.asList(allOptions).contains(option)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator
                    + "</i> is editable and present and contains the option " + "<b>" + option + "</b>", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator
                + "</i> is editable and present but does not contain the option " + "<b>" + option + "</b>",
                Success.PASS);
        return 0;
    }

    /**
     * Determines if the element is present
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Boolean: whether the element is present or not
     * @throws IOException
     */
    private boolean isPresent(Locator type, String locator, int elementMatch) throws IOException {
        if (!action.isElementPresent(type, locator, elementMatch)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is an input
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Boolean: whether the element is an input or not
     * @throws IOException
     */
    private boolean isInput(Locator type, String locator, int elementMatch) throws IOException {
        if (!action.isElementInput(type, locator, elementMatch)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTINPUT, Success.FAIL);
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is enabled
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Boolean: whether the element is enabled or not
     * @throws IOException
     */
    private boolean isEnabled(Locator type, String locator, int elementMatch) throws IOException {
        if (!action.isElementEnabled(type, locator, elementMatch)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTEDITABLE, Success.FAIL);
            return false;
        }
        return true;
    }

    /**
     * Determines if the element is present, an input, and enabled
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Boolean: whether the element is present, an input, and enabled or
     *         not
     * @throws IOException
     */
    private boolean isPresentInputEnabled(Locator type, String locator, int elementMatch) throws IOException {
        if (!isPresent(type, locator, elementMatch)) {
            return false;
        }
        if (!isInput(type, locator, elementMatch)) {
            return false;
        }
        return isEnabled(type, locator, elementMatch);
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int compareTextValue(Locator type, String locator, int elementMatch, String expectedValue)
            throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(
                EXPECTED + type + " <i>" + locator + "</i> having a value of <b>" + expectedValue + "</b>");
        // check for the object to the present on the page
        String elementValue;
        if (!isPresent(type, locator, elementMatch)) {
            return 1;
        } else {
            elementValue = action.getText(type, locator, elementMatch);
        }
        if (!elementValue.equals(expectedValue)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int compareTextValueContains(Locator type, String locator, int elementMatch, String expectedValue)
            throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + HASVALUE + expectedValue + "</b>");
        // check for the object to the present on the page
        String elementValue;
        if (!isPresent(type, locator, elementMatch)) {
            return 1;
        } else {
            elementValue = action.getText(type, locator, elementMatch);
        }
        if (!elementValue.contains(expectedValue)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element input value with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int compareInputValue(Locator type, String locator, int elementMatch, String expectedValue)
            throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(
                EXPECTED + type + " <i>" + locator + "</i> having a value of <b>" + expectedValue + "</b>");
        // check for the object to the present on the page
        String elementValue;
        if (!isPresent(type, locator, elementMatch)) {
            return 1;
        } else {
            elementValue = action.getValue(type, locator, elementMatch);
        }
        if (!elementValue.equals(expectedValue)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element css attribute value with the actual css
     * attribute value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the css attribute to be checked
     * @param expectedValue
     *            the expected css value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int compareCssValue(Locator type, String locator, int elementMatch, String attribute, String expectedValue)
            throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> having a css attribute of <i>" + attribute
                + "</i> with a value of <b>" + expectedValue + "</b>");
        // check for the object to the present on the page
        String elementCssValue;
        if (!isPresent(type, locator, elementMatch)) {
            return 1;
        } else {
            elementCssValue = action.getCss(type, locator, elementMatch, attribute);
        }
        if (!elementCssValue.equals(expectedValue)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> has a css attribute of <i>" + attribute
                    + "</i> with the value of <b>" + elementCssValue + "</b>", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> has a css attribute of <i>" + attribute
                + "</i> with the value of <b>" + elementCssValue + "</b>", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element select value exists
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param selectValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkSelectValuePresent(Locator type, String locator, int elementMatch, String selectValue)
            throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(
                EXPECTED + type + " <i>" + locator + "</i> having a select value of <b>" + selectValue + "</b>");
        // check for the object to the present on the page
        String[] elementValues;
        if (!isPresentInputEnabled(type, locator, elementMatch)) {
            return 1;
        } else {
            elementValues = action.getSelectedValues(type, locator, elementMatch);
        }
        if (General.doesArrayContain(elementValues, selectValue)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + HASVALUE + selectValue + "</b>", Success.PASS);
            return 0;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not contain the value of <b>"
                + selectValue + "</b>" + ONLYVALUE + Arrays.toString(elementValues) + "</b>", Success.FAIL);
        return 1;
    }

    /**
     * checks to see if an element select value does not exist
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param selectValue
     *            the unexpected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int checkSelectValueNotPresent(Locator type, String locator, int elementMatch, String selectValue)
            throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(
                EXPECTED + type + " <i>" + locator + "</i> without a select value of <b>" + selectValue + "</b>");
        // check for the object to the present on the page
        String[] elementValues;
        if (!isPresentInputEnabled(type, locator, elementMatch)) {
            return 1;
        } else {
            elementValues = action.getSelectedValues(type, locator, elementMatch);
        }
        if (General.doesArrayContain(elementValues, selectValue)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + HASVALUE + selectValue + "</b>", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not contain the value of <b>"
                + selectValue + "</b>, only the values <b>" + Arrays.toString(elementValues) + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int compareSelectedValue(Locator type, String locator, int elementMatch, String expectedValue)
            throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(
                EXPECTED + type + " <i>" + locator + "</i> having a selected value of <b>" + expectedValue + "</b>");
        // check for the object to the present on the page
        String elementValue;
        if (!isPresentInputEnabled(type, locator, elementMatch)) {
            return 1;
        } else {
            elementValue = action.getSelectedValue(type, locator, elementMatch);
        }
        if (!elementValue.equals(expectedValue)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element select test with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedText
     *            the expected input text of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int compareSelectedText(Locator type, String locator, int elementMatch, String expectedText)
            throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(
                EXPECTED + type + " <i>" + locator + "</i> having a selected text of <b>" + expectedText + "</b>");
        // check for the object to the present on the page
        String elementText;
        if (!isPresentInputEnabled(type, locator, elementMatch)) {
            return 1;
        } else {
            elementText = action.getSelectedText(type, locator, elementMatch);
        }
        if (!elementText.equals(expectedText)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementText + "</b>", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementText + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int compareSelectedValueNotEqual(Locator type, String locator, int elementMatch, String expectedValue)
            throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> not having a selected value of <b>"
                + expectedValue + "</b>");
        // check for the object to the present on the page
        String elementValue;
        if (!isPresentInputEnabled(type, locator, elementMatch)) {
            return 1;
        } else {
            elementValue = action.getSelectedValue(type, locator, elementMatch);
        }
        if (elementValue.equals(expectedValue)) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param expectedValues
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int compareSelectValues(Locator type, String locator, int elementMatch, String... expectedValues)
            throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(
                EXPECTED + type + " <i>" + locator + "</i> with select values of <b>" + expectedValues + "</b>");
        // check for the object to the present on the page
        String[] elementValues;
        if (!isPresentInputEnabled(type, locator, elementMatch)) {
            return 1;
        } else {
            elementValues = action.getSelectOptions(type, locator, elementMatch);
        }
        for (String entry : expectedValues) {
            if (!Arrays.asList(elementValues).contains(entry)) {
                outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not have the select value of <b>"
                        + entry + "</b>", Success.FAIL);
                return 1;
            }
        }
        for (String entry : elementValues) {
            if (!Arrays.asList(expectedValues).contains(entry)) {
                outputFile.recordActual(
                        ELEMENT + type + " <i>" + locator + VALUE + entry + "</b> which was not expected",
                        Success.FAIL);
                return 1;
            }
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValues + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the number of expected attributes from a select value with the
     * actual number of attributes from the element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param numOfOptions
     *            the expected number of options in the select element
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int compareNumOfSelectOptions(Locator type, String locator, int elementMatch, int numOfOptions)
            throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> with number of select values equal to <b>"
                + numOfOptions + "</b>");
        // check for the object to the present on the page
        int elementValues;
        if (!isPresentInputEnabled(type, locator, elementMatch)) {
            return 1;
        } else {
            elementValues = action.getNumOfSelectOptions(type, locator, elementMatch);
        }
        if (elementValues != numOfOptions) {
            outputFile.recordActual(
                    ELEMENT + type + " <i>" + locator + "</i> has <b>" + numOfOptions + "</b>" + " select options",
                    Success.FAIL);
            return 1;
        }
        outputFile.recordActual(
                ELEMENT + type + " <i>" + locator + "</i> has <b>" + numOfOptions + "</b>" + " select options",
                Success.PASS);
        return 0;
    }

    /**
     * compares the number of expected rows with the actual number of rows of a
     * table with from a table element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param numOfRows
     *            the expected number of row elements of a table
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int compareNumOfTableRows(Locator type, String locator, int elementMatch, int numOfRows) throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> with the number of table rows equal to <b>"
                + numOfRows + "</b>");
        // check for the object to the present on the page
        if (!isPresent(type, locator, elementMatch)) {
            return 1;
        }
        int actualNumOfRows = action.getNumOfTableRows(type, locator, elementMatch);
        if (actualNumOfRows != numOfRows) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not have the number of rows <b>"
                    + numOfRows + "</b>. Instead, " + actualNumOfRows + " rows were found", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + "has " + actualNumOfRows + "</b> rows",
                Success.PASS);
        return 0;
    }

    /**
     * compares the number of expected columns with the actual number of columns
     * of a table with from a table element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param numOfColumns
     *            the expected number of column elements of a table
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int compareNumOfTableColumns(Locator type, String locator, int elementMatch, int numOfColumns)
            throws IOException {
        // outputFile.record the action
        outputFile.recordExpected(EXPECTED + type + " <i>" + locator
                + "</i> with the number of table columns equal to <b>" + numOfColumns + "</b>");
        // check for the object to the present on the page
        if (!isPresent(type, locator, elementMatch)) {
            return 1;
        }
        int actualNumOfCols = action.getNumOfTableColumns(type, locator, elementMatch);
        if (actualNumOfCols != numOfColumns) {
            outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not have the number of columns <b>"
                    + numOfColumns + "</b>. Instead, " + actualNumOfCols + " columns were found", Success.FAIL);
            return 1;
        }
        outputFile.recordActual(ELEMENT + type + " <i>" + locator + "has " + actualNumOfCols + "</b> columns",
                Success.PASS);
        return 0;
    }

    /**
     * compares the text of expected table cell with the actual table cell text
     * of a table with from a table element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param row
     *            - the number of the row in the table - note, row numbering
     *            starts at 1, NOT 0
     * @param col
     *            - the number of the column in the table - note, column
     *            numbering starts at 1, NOT 0
     * @return Integer: 1 if a failure and 0 if a pass
     * @throws IOException
     */
    public int compareTableCellText(Locator type, String locator, int elementMatch, int row, int col, String text)
            throws IOException {
        String column = " and column ";
        String element = " within element ";
        // outputFile.record the action
        outputFile.recordExpected("Expected to find cell at row " + row + column + col + element + type + " <i>"
                + locator + "</i> to have the text value of <b>" + text + "</b>");
        // check for the object to the present on the page
        if (!isPresent(type, locator, elementMatch)) {
            return 1;
        }
        String actualText = action.getTableCell(type, locator, elementMatch, row, col).getText();
        if (!actualText.equals(text)) {
            outputFile.recordActual("Cell at row " + row + column + col + element + type + " <i>" + locator
                    + "</i> has the text value of <b>" + actualText + "</b>", Success.FAIL);
            return 1;
        }
        outputFile.recordActual("Cell at row " + row + column + col + element + type + " <i>" + locator
                + "</i> has the text value of <b>" + actualText + "</b>", Success.PASS);
        return 0;
    }
}