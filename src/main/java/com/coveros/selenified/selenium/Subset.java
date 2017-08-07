package com.coveros.selenified.selenium;

import com.coveros.selenified.selenium.Selenium.Locator;

public interface Subset {

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
    public default int attribute(Locator type, String locator, String attribute) {
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
    public default int attribute(Locator type, String locator, int elementMatch, String attribute) {
        return attribute(new Element(type, locator, elementMatch), attribute);
    }

    /**
     * checks to see if an element contains a particular class
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param classs
     *            - the expected class value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public default int classs(Locator type, String locator, String classs) {
        return classs(new Element(type, locator), classs);
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
     * @param classs
     *            - the expected class value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public default int classs(Locator type, String locator, int elementMatch, String classs) {
        return classs(new Element(type, locator, elementMatch), classs);
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param text
     *            the expected text of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public default int text(Locator type, String locator, String text) {
        return text(new Element(type, locator), text);
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
     * @param text
     *            the expected text of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public default int text(Locator type, String locator, int elementMatch, String text) {
        return text(new Element(type, locator, elementMatch), text);
    }

    /**
     * compares the expected element value with the actual value from an element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param value
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public default int value(Locator type, String locator, String value) {
        return value(new Element(type, locator), value);
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
     * @param value
     *            the expected value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public default int value(Locator type, String locator, int elementMatch, String value) {
        return value(new Element(type, locator, elementMatch), value);
    }

    /**
     * checks to see if an option is available to be selected on the page
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param selectOption
     *            the option expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public default int selectOption(Locator type, String locator, String selectOption) {
        return selectOption(new Element(type, locator), selectOption);
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
     * @param selectOption
     *            the option expected in the list
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public default int selectOption(Locator type, String locator, int elementMatch, String selectOption) {
        return selectOption(new Element(type, locator, elementMatch), selectOption);
    }

    /**
     * checks to see if an element select value exists
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param selectValue
     *            the expected input value of the element
     * @return Integer: 1 if a failure and 0 if a pass
     */
    public default int selectValue(Locator type, String locator, String selectValue) {
        return selectValue(new Element(type, locator), selectValue);
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
     */
    public default int selectValue(Locator type, String locator, int elementMatch, String selectValue) {
        return selectValue(new Element(type, locator, elementMatch), selectValue);
    }

    public abstract int attribute(Element element, String attribute);

    public abstract int classs(Element element, String classs);

    public abstract int text(Element element, String text);

    public abstract int value(Element element, String value);

    public abstract int selectOption(Element element, String selectOption);

    public abstract int selectValue(Element element, String selectValue);
}