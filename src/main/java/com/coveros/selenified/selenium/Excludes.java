package com.coveros.selenified.selenium;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.selenium.Assert.Success;
import com.coveros.selenified.selenium.Selenium.Locator;

public class Excludes {
    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private Action action;

    // constants
    private static final String EXPECTED = "Expected to find ";
    private static final String CLASS = "class";

    private static final String NOTINPUT = " is not an input on the page";

    private static final String VALUE = " has the value of <b>";
    private static final String HASVALUE = " contains the value of <b>";
    private static final String ONLYVALUE = ", only the values <b>";
    private static final String CLASSVALUE = " has a class value of <b>";

    public Excludes(Action action, OutputFile file) {
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
     * checks to see if an element has an attribute associated with it
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param attribute
     *            - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int attribute(Locator type, String locator, String attribute) {
        return attribute(new Element(type, locator), attribute);
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
     */
    public int attribute(Locator type, String locator, int elementMatch, String attribute) {
        return attribute(new Element(type, locator, elementMatch), attribute);
    }

    /**
     * checks to see if an element does not contain a particular class
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param unexpectedClass
     *            - the unexpected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int classs(Locator type, String locator, String unexpectedClass) {
        return classs(new Element(type, locator), unexpectedClass);
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
     */
    public int classs(Locator type, String locator, int elementMatch, String unexpectedClass) {
        return classs(new Element(type, locator, elementMatch), unexpectedClass);
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
    public int value(Locator type, String locator, String expectedValue) {
        return value(new Element(type, locator), expectedValue);
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
    public int value(Locator type, String locator, int elementMatch, String expectedValue) {
        return value(new Element(type, locator, elementMatch), expectedValue);
    }

    /**
     * checks to see if an option is not available to be selected on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param option
     *            the option not expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectOption(Locator type, String locator, String option) {
        return selectOption(new Element(type, locator), option);
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
     */
    public int selectOption(Locator type, String locator, int elementMatch, String option) {
        return selectOption(new Element(type, locator, elementMatch), option);
    }

    /**
     * checks to see if an element select value does not exist
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param selectValue
     *            the unexpected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectValue(Locator type, String locator, String selectValue) {
        return selectValue(new Element(type, locator), selectValue);
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
     */
    public int selectValue(Locator type, String locator, int elementMatch, String selectValue) {
        return selectValue(new Element(type, locator, elementMatch), selectValue);
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    /**
     * checks to see if an element does not contain a particular class
     *
     * @param element
     *            - the element to be assessed
     * @param unexpectedClass
     *            - the unexpected class value
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int classs(Element element, String unexpectedClass) {
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        file.recordExpected(EXPECTED + element.prettyOutput() + " without class <b>" + unexpectedClass + "</b>");
        // check our classes
        String actualClass = action.get().attribute(element, CLASS);
        // file.record the action
        if (actualClass.contains(unexpectedClass)) {
            file.recordActual(element.prettyOutputStart() + CLASSVALUE + actualClass + "</b>, which contains <b>"
                    + unexpectedClass + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(
                element.prettyOutputStart() + " does not contain a class value of <b>" + unexpectedClass + "</b>",
                Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element has an attribute associated with it
     *
     * @param element
     *            - the element to be assessed
     * @param attribute
     *            - the attribute to check for
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int attribute(Element element, String attribute) {
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        file.recordExpected(EXPECTED + element.prettyOutput() + " without attribute <b>" + attribute + "</b>");
        // check our attributes
        Map<String, String> attributes = action.get().allAttributes(element);
        if (attributes == null) {
            file.recordActual("Unable to assess the attributes of " + element.prettyOutput(), Success.FAIL);
            file.addError();
            return 1;
        }
        Set<String> keys = attributes.keySet();
        String[] allAttributes = keys.toArray(new String[keys.size()]);
        // file.record the action
        if (Arrays.asList(allAttributes).contains(attribute)) {
            file.recordActual(element.prettyOutputStart() + " contains the attribute of <b>" + attribute + "</b>",
                    Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + " does not contain the attribute of <b>" + attribute + "</b>"
                + ONLYVALUE + Arrays.toString(allAttributes) + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param element
     *            - the element to be assessed
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int text(Element element, String expectedValue) {
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + HASVALUE + expectedValue + "</b>");
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        // check for the object to the present on the page
        String elementValue = action.get().text(element);
        if (elementValue.contains(expectedValue)) {
            file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.PASS);
        return 0;
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param element
     *            - the element to be assessed
     * @param expectedValue
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int value(Element element, String expectedValue) {
        // file.record the action
        file.recordExpected(EXPECTED + element.prettyOutput() + HASVALUE + expectedValue + "</b>");
        // wait for the element
        if (!action.is().elementPresent(element) && action.waitFor().elementPresent(element) == 1) {
            return 1;
        }
        // verify this is an input element
        if (!action.is().elementInput(element)) {
            file.recordActual(element.prettyOutputStart() + NOTINPUT, Success.FAIL);
            file.addError();
            return 1;
        }
        // check for the object to the present on the page
        String elementValue = action.get().value(element);
        if (elementValue.contains(expectedValue)) {
            file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + VALUE + elementValue + "</b>", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an option is not available to be selected on the page
     *
     * @param element
     *            - the element to be assessed
     * @param option
     *            the option not expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectOption(Element element, String option) {
        // wait for the select
        if (!Helper.isPresentSelect(action, file, element, EXPECTED + element.prettyOutput() + " without the option <b>"
                + option + "</b> available to be selected on the page")) {
            return 1;
        }
        // check for the object to the editable
        String[] allOptions = action.get().selectOptions(element);
        if (Arrays.asList(allOptions).contains(option)) {
            file.recordActual(element.prettyOutputStart() + " is editable and present and contains the option " + "<b>"
                    + option + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + " is editable and present but does not contain the option "
                + "<b>" + option + "</b>", Success.PASS);
        return 0;
    }

    /**
     * checks to see if an element select value does not exist
     *
     * @param element
     *            - the element to be assessed
     * @param selectValue
     *            the unexpected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public int selectValue(Element element, String selectValue) {
        // wait for the select
        if (!Helper.isPresentSelect(action, file, element, EXPECTED + element.prettyOutput()
                + " without a select value of <b>" + selectValue + "</b> available to be selected on the page")) {
            return 1;
        }
        // check for the object to the present on the page
        String[] elementValues = action.get().selectValues(element);
        if (Arrays.asList(elementValues).contains(selectValue)) {
            file.recordActual(element.prettyOutputStart() + HASVALUE + selectValue + "</b>", Success.FAIL);
            file.addError();
            return 1;
        }
        file.recordActual(element.prettyOutputStart() + " does not contain the value of <b>" + selectValue
                + "</b>, only the values <b>" + Arrays.toString(elementValues) + "</b>", Success.PASS);
        return 0;
    }
}