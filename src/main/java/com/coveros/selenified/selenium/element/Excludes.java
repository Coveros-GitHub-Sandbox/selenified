package com.coveros.selenified.selenium.element;

import java.util.Arrays;

import com.coveros.selenified.tools.OutputFile;
import com.coveros.selenified.tools.OutputFile.Success;

public class Excludes extends Assert {

    public Excludes(Element element, OutputFile file) {
        this.element = element;
        this.file = file;
    }

    // ///////////////////////////////////////
    // assessing functionality
    // ///////////////////////////////////////

    /**
     * checks to see if an element does not contain a particular class
     *
     * @param unexpectedClass
     *            - the unexpected class value
     */
    public void clazz(String unexpectedClass) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        file.recordExpected(EXPECTED + element.prettyOutput() + " without class <b>" + unexpectedClass + "</b>");
        // check our classes
        String actualClass = element.get().attribute(CLASS);
        // file.record the element
        if (actualClass.contains(unexpectedClass)) {
            file.recordActual(element.prettyOutputStart() + CLASSVALUE + actualClass + "</b>, which contains <b>"
                    + unexpectedClass + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(
                element.prettyOutputStart() + " does not contain a class value of <b>" + unexpectedClass + "</b>",
                Success.PASS);
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param attribute
     *            - the attribute to check for
     */
    public void attribute(String attribute) {
        String[] allAttributes = getAttributes(attribute, "without");
        if (allAttributes == null) {
            return;
        }
        // file.record the element
        if (Arrays.asList(allAttributes).contains(attribute)) {
            file.recordActual(element.prettyOutputStart() + " contains the attribute of <b>" + attribute + "</b>",
                    Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + " does not contain the attribute of <b>" + attribute + "</b>"
                + ONLYVALUE + Arrays.toString(allAttributes) + "</b>", Success.PASS);
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param expectedValue
     *            the expected value of the element
     */
    public void text(String expectedValue) {
        // wait for the element
        if (!isPresent()) {
            return;
        }
        // file.record the element
        file.recordExpected(EXPECTED + element.prettyOutput() + HASNTTEXT + expectedValue + "</b>");
        // check for the object to the present on the page
        String elementValue = element.get().text();
        if (elementValue.contains(expectedValue)) {
            file.recordActual(element.prettyOutputStart() + TEXT + elementValue + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + TEXT + elementValue + "</b>", Success.PASS);
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param expectedValue
     *            the expected value of the element
     */
    public void value(String expectedValue) {
        String elementValue = getValue(expectedValue, HASNTVALUE);
        if (elementValue == null) {
            return;
        }
        if (elementValue.contains(expectedValue)) {
            file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.PASS);
    }

    /**
     * checks to see if an option is not available to be selected on the page
     *
     * @param option
     *            the option not expected in the list
     */
    public void selectOption(String option) {
        // wait for the select
        if (!isPresentSelect(EXPECTED + element.prettyOutput() + " without the option <b>" + option
                + "</b> available to be selected on the page")) {
            return;
        }
        // check for the object to the editable
        String[] allOptions = element.get().selectOptions();
        if (Arrays.asList(allOptions).contains(option)) {
            file.recordActual(element.prettyOutputStart() + " is editable and present and contains the option " + "<b>"
                    + option + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + " is editable and present but does not contain the option "
                + "<b>" + option + "</b>", Success.PASS);
    }

    /**
     * checks to see if an element select value does not exist
     *
     * @param selectValue
     *            the unexpected input value of the element
     */
    public void selectValue(String selectValue) {
        // wait for the select
        if (!isPresentSelect(EXPECTED + element.prettyOutput() + " without a select value of <b>" + selectValue
                + "</b> available to be selected on the page")) {
            return;
        }
        // check for the object to the present on the page
        String[] elementValues = element.get().selectValues();
        if (Arrays.asList(elementValues).contains(selectValue)) {
            file.recordActual(element.prettyOutputStart() + HASVALUE + selectValue + "</b>", Success.FAIL);
            file.addError();
            return;
        }
        file.recordActual(element.prettyOutputStart() + HASNTVALUE + selectValue + "</b>, only the values <b>"
                + Arrays.toString(elementValues) + "</b>", Success.PASS);
    }
}