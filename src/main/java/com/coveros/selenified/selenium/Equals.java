package com.coveros.selenified.selenium;

import java.util.Arrays;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.selenium.Assert.Success;
import com.coveros.selenified.selenium.Selenium.Locator;

public class Equals {
    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private Action action;

    // constants
    private static final String EXPECTED = "Expected to find ";
    private static final String CLASS = "class";

    private static final String OPTION = " has the option of <b>";
    private static final String OPTIONS = " has options of <b>";
    private static final String VALUE = " has the value of <b>";
    private static final String VALUES = " has values of <b>";
    private static final String CLASSVALUE = " has a class value of <b>";
    private static final String WITH = " with the value of <b>";

    public Equals(Action action, OutputFile file) {
        this.action = action;
        this.file = file;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setOutputFile(OutputFile file) {
        this.file = file;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * compares the expected element css attribute value with the actual css
     * attribute value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param attribute
     *            - the css attribute to be checked
     * @param expectedValue
     *            the expected css value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int cssValue(Locator type, String locator, String attribute, String expectedValue) {
        return cssValue(new Element(type, locator), attribute, expectedValue);
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
     */
    public int cssValue(Locator type, String locator, int elementMatch, String attribute, String expectedValue) {
        return cssValue(new Element(type, locator, elementMatch), attribute, expectedValue);
    }

    /**
     * compares the expected element attribute value with the actual attribute
     * value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param attribute
     *            - the attribute to be checked
     * @param expectedValue
     *            the expected value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int attribute(Locator type, String locator, String attribute, String expectedValue) {
        return attribute(new Element(type, locator), attribute, expectedValue);
    }

    /**
     * compares the expected element attribute value with the actual attribute
     * value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the attribute to be checked
     * @param expectedValue
     *            the expected value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int attribute(Locator type, String locator, int elementMatch, String attribute, String expectedValue) {
        return attribute(new Element(type, locator, elementMatch), attribute, expectedValue);
    }

    /**
     * checks to see if an element has a particular class
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedClass
     *            - the full expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int classs(Locator type, String locator, String expectedClass) {
        return classs(new Element(type, locator), expectedClass);
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
     */
    public int classs(Locator type, String locator, int elementMatch, String expectedClass) {
        return classs(new Element(type, locator, elementMatch), expectedClass);
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int text(Locator type, String locator, String expectedValue) {
        return text(new Element(type, locator), expectedValue);
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
     */
    public int text(Locator type, String locator, int elementMatch, String expectedValue) {
        return text(new Element(type, locator, elementMatch), expectedValue);
    }

    /**
     * compares the text of expected table cell with the actual table cell text
     * of a table with from a table element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param row
     *            - the number of the row in the table - note, row numbering
     *            starts at 1, NOT 0
     * @param col
     *            - the number of the column in the table - note, column
     *            numbering starts at 1, NOT 0
     * @param text
     *            - what text do we expect to be in the table cell
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int text(Locator type, String locator, int row, int col, String text) {
        return text(new Element(type, locator), row, col, text);
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
     * @param text
     *            - what text do we expect to be in the table cell
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int text(Locator type, String locator, int elementMatch, int row, int col, String text) {
        return text(new Element(type, locator, elementMatch), row, col, text);
    }

    /**
     * compares the expected element input value with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int value(Locator type, String locator, String expectedValue) {
        return value(new Element(type, locator), expectedValue);
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
     */
    public int value(Locator type, String locator, int elementMatch, String expectedValue) {
        return value(new Element(type, locator, elementMatch), expectedValue);
    }

    /**
     * compares the expected element select test with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedText
     *            the expected input text of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectedOption(Locator type, String locator, String expectedText) {
        return selectedOption(new Element(type, locator), expectedText);
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
     */
    public int selectedOption(Locator type, String locator, int elementMatch, String expectedText) {
        return selectedOption(new Element(type, locator, elementMatch), expectedText);
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectedValue(Locator type, String locator, String expectedValue) {
        return selectedValue(new Element(type, locator), expectedValue);
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
     */
    public int selectedValue(Locator type, String locator, int elementMatch, String expectedValue) {
        return selectedValue(new Element(type, locator, elementMatch), expectedValue);
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedValues
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectOptions(Locator type, String locator, String... expectedValues) {
        return selectOptions(new Element(type, locator), expectedValues);
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
     */
    public int selectOptions(Locator type, String locator, int elementMatch, String... expectedValues) {
        return selectOptions(new Element(type, locator, elementMatch), expectedValues);
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param expectedValues
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectValues(Locator type, String locator, String... expectedValues) {
        return selectValues(new Element(type, locator), expectedValues);
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
     */
    public int selectValues(Locator type, String locator, int elementMatch, String... expectedValues) {
        return selectValues(new Element(type, locator, elementMatch), expectedValues);
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    /**
     * compares the expected element css attribute value with the actual css
     * attribute value from an element
     *
     * @param element
     *            - the element to be assessed
     * @param attribute
     *            - the css attribute to be checked
     * @param expectedValue
     *            the expected css value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int cssValue(Element element, String attribute, String expectedValue) {
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + " having a css attribute of <i>" + attribute
                + " with a value of <b>" + expectedValue + "</b>");
        // get the actual css element value
        String elementCssValue = action.get().css(element, attribute);
        if (elementCssValue == null) {
            file.recordActual("Unable to assess the css of " + element.prettyOutput(), Success.FAIL);
            file.addError();
            return 1;
        }
        if (!elementCssValue.equals(expectedValue)) {
            file.recordActual(element.prettyOutputStart() + " has a css attribute of <i>" + attribute + WITH
                    + elementCssValue + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + " has a css attribute of <i>" + attribute + WITH
                + elementCssValue + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element attribute value with the actual attribute
     * value from an element
     *
     * @param element
     *            - the element to be assessed
     * @param attribute
     *            - the attribute to be checked
     * @param expectedValue
     *            the expected value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int attribute(Element element, String attribute, String expectedValue) {
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + " having an attribute of <i>" + attribute
                + " with a value of <b>" + expectedValue + "</b>");
        // get the actual attribute value
        String elementValue = action.get().attribute(element, attribute);
        if (!elementValue.equals(expectedValue)) {
            file.recordActual(
                    element.prettyOutputStart() + " has an attribute of <i>" + attribute + WITH + elementValue + "</b>",
                    Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(
                element.prettyOutputStart() + " has an attribute of <i>" + attribute + WITH + elementValue + "</b>",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element has a particular class
     *
     * @param element
     *            - the element to be assessed
     * @param expectedClass
     *            - the full expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int classs(Element element, String expectedClass) {
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + " with class <b>" + expectedClass + "</b>");
        // get the actual class value
        String actualClass = action.get().attribute(element, CLASS);
        // file.record the action
        if (!actualClass.equals(expectedClass)) {
            file.recordActual(element.prettyOutputStart() + CLASSVALUE + actualClass + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + CLASSVALUE + expectedClass + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param element
     *            - the element to be assessed
     * @param expectedText
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int text(Element element, String expectedText) {
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + " having text of <b>" + expectedText + "</b>");
        // check for the object to the present on the page
        String elementText = action.get().text(element);
        if (!elementText.equals(expectedText)) {
            file.recordActual(element.prettyOutputStart() + VALUE + elementText + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + VALUE + elementText + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the text of expected table cell with the actual table cell text
     * of a table with from a table element
     *
     * @param element
     *            - the table element to be assessed
     * @param row
     *            - the number of the row in the table - note, row numbering
     *            starts at 1, NOT 0
     * @param col
     *            - the number of the column in the table - note, column
     *            numbering starts at 1, NOT 0
     * @param text
     *            - what text do we expect to be in the table cell
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int text(Element element, int row, int col, String text) {
        String column = " and column ";
        String within = " within element ";
        // wait for the table
        if (!Helper.isPresentTable(action, file, element, "Expected to find cell at row " + row + column + col + within
                + element.prettyOutput() + " to have the text value of <b>" + text + "</b>")) {
            return 1;
        }
        // get the table cell text
        String actualText = action.get().tableCell(element, row, col).getText();
        if (!actualText.equals(text)) {
            file.recordActual("Cell at row " + row + column + col + within + element.prettyOutput()
                    + " has the text value of <b>" + actualText + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual("Cell at row " + row + column + col + within + element.prettyOutput()
                + " has the text value of <b>" + actualText + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element input value with the actual value from an
     * element
     *
     * @param element
     *            - the element to be assessed
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int value(Element element, String expectedValue) {
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + " having a value of <b>" + expectedValue + "</b>");
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        // verify this is an input element
        if (!action.is().elementInput(element)) {
            file.recordActual(element.prettyOutputStart() + " is not an input on the page", Success.FAIL);
            file.addError();
            return 1;
        }
        // get the element value
        String elementValue = action.get().value(element);
        if (!elementValue.equals(expectedValue)) {
            file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element select test with the actual value from an
     * element
     *
     * @param element
     *            - the element to be assessed
     * @param expectedText
     *            the expected input text of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectedOption(Element element, String expectedText) {
        // wait for the select
        if (!Helper.isPresentSelect(action, file, element,
                EXPECTED + element.prettyOutput() + " having a selected option of <b>" + expectedText + "</b>")) {
            return 1;
        }
        // get the selected text
        String elementText = action.get().selectedOption(element);
        if (!elementText.equals(expectedText)) {
            file.recordActual(element.prettyOutputStart() + OPTION + elementText + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + OPTION + elementText + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element select value with the actual value from an
     * element
     *
     * @param element
     *            - the element to be assessed
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectedValue(Element element, String expectedValue) {
        // wait for the select
        if (!Helper.isPresentSelect(action, file, element,
                EXPECTED + element.prettyOutput() + " having a selected value of <b>" + expectedValue + "</b>")) {
            return 1;
        }
        // get the selected value
        String elementValue = action.get().selectedValue(element);
        if (!elementValue.equals(expectedValue)) {
            file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param element
     *            - the element to be assessed
     * @param expectedOptions
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectOptions(Element element, String... expectedOptions) {
        // wait for the select
        if (!Helper.isPresentSelect(action, file, element,
                EXPECTED + element.prettyOutput() + " with select options of <b>" + expectedOptions + "</b>")) {
            return 1;
        }
        // get the actual select options
        String[] elementOptions = action.get().selectOptions(element);
        if (!Arrays.toString(elementOptions).equals(Arrays.toString(expectedOptions))) {
            file.recordActual(element.prettyOutputStart() + OPTIONS + Arrays.toString(elementOptions) + "</b>",
                    Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + OPTIONS + Arrays.toString(elementOptions) + "</b>",
                Success.PASS);
        return 0;
    }

    /**
     * compares the expected attributes from a select value with the actual
     * attributes from the element
     *
     * @param element
     *            - the element to be assessed
     * @param expectedValues
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectValues(Element element, String... expectedValues) {
        // wait for the select
        if (!Helper.isPresentSelect(action, file, element, EXPECTED + element.prettyOutput()
                + " with select values of <b>" + Arrays.toString(expectedValues) + "</b>")) {
            return 1;
        }
        // get the actual select values
        String[] elementValues = action.get().selectValues(element);
        if (!Arrays.toString(elementValues).equals(Arrays.toString(expectedValues))) {
            file.recordActual(element.prettyOutputStart() + VALUES + Arrays.toString(elementValues) + "</b>",
                    Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + VALUES + Arrays.toString(elementValues) + "</b>", Success.PASS);
        return 0;
    }
}