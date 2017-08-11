package com.coveros.selenified.selenium.element;

import java.util.Arrays;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.output.OutputFile.Success;

public class Equals {

    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // what element are we trying to interact with on the page
    private Element element;

    // a class used to determine if elements are selects or tables
    private Helper helper;

    // constants
    private static final String EXPECTED = "Expected to find ";
    private static final String CLASS = "class";

    private static final String OPTION = " has the option of <b>";
    private static final String OPTIONS = " has options of <b>";
    private static final String VALUE = " has the value of <b>";
    private static final String VALUES = " has values of <b>";
    private static final String CLASSVALUE = " has a class value of <b>";
    private static final String WITH = " with the value of <b>";

    public Equals(Element element, OutputFile file) {
        this.element = element;
        this.file = file;
        this.helper = new Helper(element, file);
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * compares the expected element css attribute value with the actual css
     * attribute value from an element
     *
     * @param attribute
     *            - the css attribute to be checked
     * @param expectedValue
     *            the expected css value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int cssValue(String attribute, String expectedValue) {
        // wait for the element
        if (!element.is().present() && element.waitFor().present() == 1) {
            return 1;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + " having a css attribute of <i>" + attribute
                + " with a value of <b>" + expectedValue + "</b>");
        // get the actual css element value
        String elementCssValue = element.get().css(attribute);
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
     * @param attribute
     *            - the attribute to be checked
     * @param expectedValue
     *            the expected value of the passed attribute of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int attribute(String attribute, String expectedValue) {
        // wait for the element
        if (!element.is().present() && element.waitFor().present() == 1) {
            return 1;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + " having an attribute of <i>" + attribute
                + " with a value of <b>" + expectedValue + "</b>");
        // get the actual attribute value
        String elementValue = element.get().attribute(attribute);
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
     * @param expectedClass
     *            - the full expected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int classs(String expectedClass) {
        // wait for the element
        if (!element.is().present() && element.waitFor().present() == 1) {
            return 1;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + " with class <b>" + expectedClass + "</b>");
        // get the actual class value
        String actualClass = element.get().attribute(CLASS);
        // file.record the element
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
     * @param expectedText
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int text(String expectedText) {
        // wait for the element
        if (!element.is().present() && element.waitFor().present() == 1) {
            return 1;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + " having text of <b>" + expectedText + "</b>");
        // check for the object to the present on the page
        String elementText = element.get().text();
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
    public int text(int row, int col, String text) {
        String column = " and column ";
        String within = " within element ";
        // wait for the table
        if (!helper.isPresentTable("Expected to find cell at row " + row + column + col + within
                + element.prettyOutput() + " to have the text value of <b>" + text + "</b>")) {
            return 1;
        }
        // get the table cell text
        String actualText = element.get().tableCell(row, col).getText();
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
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int value(String expectedValue) {
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + " having a value of <b>" + expectedValue + "</b>");
        // wait for the element
        if (!element.is().present() && element.waitFor().present() == 1) {
            return 1;
        }
        // verify this is an input element
        if (!element.is().input()) {
            file.recordActual(element.prettyOutputStart() + " is not an input on the page", Success.FAIL);
            file.addError();
            return 1;
        }
        // get the element value
        String elementValue = element.get().value();
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
     * @param expectedText
     *            the expected input text of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectedOption(String expectedText) {
        // wait for the select
        if (!helper.isPresentSelect(
                EXPECTED + element.prettyOutput() + " having a selected option of <b>" + expectedText + "</b>")) {
            return 1;
        }
        // get the selected text
        String elementText = element.get().selectedOption();
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
     * @param expectedValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectedValue(String expectedValue) {
        // wait for the select
        if (!helper.isPresentSelect(
                EXPECTED + element.prettyOutput() + " having a selected value of <b>" + expectedValue + "</b>")) {
            return 1;
        }
        // get the selected value
        String elementValue = element.get().selectedValue();
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
     * @param expectedOptions
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectOptions(String... expectedOptions) {
        // wait for the select
        if (!helper.isPresentSelect(
                EXPECTED + element.prettyOutput() + " with select options of <b>" + expectedOptions + "</b>")) {
            return 1;
        }
        // get the actual select options
        String[] elementOptions = element.get().selectOptions();
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
     * @param expectedValues
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectValues(String... expectedValues) {
        // wait for the select
        if (!helper.isPresentSelect(EXPECTED + element.prettyOutput() + " with select values of <b>"
                + Arrays.toString(expectedValues) + "</b>")) {
            return 1;
        }
        // get the actual select values
        String[] elementValues = element.get().selectValues();
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