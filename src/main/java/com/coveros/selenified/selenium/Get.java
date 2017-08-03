package com.coveros.selenified.selenium;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.General;

public class Get {
    private static final Logger log = Logger.getLogger(General.class);

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private WebDriver driver;

    // the is class to determine if something exists
    private Is is;
    // the wait class to determine if we need to wait for something
    private Wait wait;

    // constants
    private static final String VALUE = "value";

    public Get(WebDriver driver, OutputFile file) {
        this.driver = driver;
        this.is = new Is(driver, file);
        this.wait = new Wait(driver, file);
    }

    // ////////////////////////////////////
    // checking element availability
    // ////////////////////////////////////

    /**
     * get the option from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String: the option from the select element
     */
    public String selectedText(Locator type, String locator) {
        return selectedText(new Element(type, locator));
    }

    /**
     * get the option from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String: the option from the select element
     */
    public String selectedText(Locator type, String locator, int elementMatch) {
        return selectedText(new Element(type, locator, elementMatch));
    }

    /**
     * get the options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String[]: the options from the select element
     */
    public String[] selectedTexts(Locator type, String locator) {
        return selectedTexts(new Element(type, locator));
    }

    /**
     * get the options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String[]: the options from the select element
     */
    public String[] selectedTexts(Locator type, String locator, int elementMatch) {
        return selectedTexts(new Element(type, locator, elementMatch));
    }

    /**
     * get the option from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String: the option from the select element
     */
    public String selectedValue(Locator type, String locator) {
        return selectedValue(new Element(type, locator));
    }

    /**
     * get the option from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String: the option from the select element
     */
    public String selectedValue(Locator type, String locator, int elementMatch) {
        return selectedValue(new Element(type, locator, elementMatch));
    }

    /**
     * get the options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String[]: the options from the select element
     */
    public String[] selectedValues(Locator type, String locator) {
        return selectedValues(new Element(type, locator));
    }

    /**
     * get the options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String[]: the options from the select element
     */
    public String[] selectedValues(Locator type, String locator, int elementMatch) {
        return selectedValues(new Element(type, locator, elementMatch));
    }

    /**
     * the generic selenium get text from an element functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String - the text of the element
     */
    public String text(Locator type, String locator) {
        return text(new Element(type, locator));
    }

    /**
     * the generic selenium get text from an element functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String - the text of the element
     */
    public String text(Locator type, String locator, int elementMatch) {
        return text(new Element(type, locator, elementMatch));
    }

    /**
     * the generic selenium get value from an element functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String - the text of the element
     */
    public String value(Locator type, String locator) {
        return value(new Element(type, locator));
    }

    /**
     * the generic selenium get value from an element functionality implemented
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String - the text of the element
     */
    public String value(Locator type, String locator, int elementMatch) {
        return value(new Element(type, locator, elementMatch));
    }

    /**
     * a function to return one css attribute of the provided element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String css(Locator type, String locator, String attribute) {
        return css(new Element(type, locator), attribute);
    }

    /**
     * a function to return one css attribute of the provided element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String css(Locator type, String locator, int elementMatch, String attribute) {
        return css(new Element(type, locator, elementMatch), attribute);
    }

    /**
     * a function to return one attribute of the provided element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String attribute(Locator type, String locator, String attribute) {
        return attribute(new Element(type, locator), attribute);
    }

    /**
     * a function to return one attribute of the provided element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String attribute(Locator type, String locator, int elementMatch, String attribute) {
        return attribute(new Element(type, locator, elementMatch), attribute);
    }

    /**
     * a function to return all attributes of the provided element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String - the value of the css attribute
     */
    public Map<String, String> allAttributes(Locator type, String locator) {
        return allAttributes(new Element(type, locator));
    }

    /**
     * a function to return all attributes of the provided element
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String - the value of the css attribute
     */
    public Map<String, String> allAttributes(Locator type, String locator, int elementMatch) {
        return allAttributes(new Element(type, locator, elementMatch));
    }

    /**
     * a way to execute custom javascript functions
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param javascriptFunction
     *            - the javascript function that is going to be executed
     * @return Object: any resultant output from the javascript command
     */
    public Object eval(Locator type, String locator, String javascriptFunction) {
        return eval(new Element(type, locator), javascriptFunction);
    }

    /**
     * a way to execute custom javascript functions
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param javascriptFunction
     *            - the javascript function that is going to be executed
     * @return Object: any resultant output from the javascript command
     */
    public Object eval(Locator type, String locator, int elementMatch, String javascriptFunction) {
        return eval(new Element(type, locator, elementMatch), javascriptFunction);
    }

    /**
     * get the number of options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of select options
     */
    public int numOfSelectOptions(Locator type, String locator) {
        return numOfSelectOptions(new Element(type, locator));
    }

    /**
     * get the number of options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of select options
     */
    public int numOfSelectOptions(Locator type, String locator, int elementMatch) {
        return numOfSelectOptions(new Element(type, locator, elementMatch));
    }

    /**
     * get the number of columns of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of table columns
     */
    public int numOfTableColumns(Locator type, String locator) {
        return numOfTableColumns(new Element(type, locator));
    }

    /**
     * get the number of columns of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of table columns
     */
    public int numOfTableColumns(Locator type, String locator, int elementMatch) {
        return numOfTableColumns(new Element(type, locator, elementMatch));
    }

    /**
     * get the number of rows of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of table rows
     */
    public int numOfTableRows(Locator type, String locator) {
        return numOfTableRows(new Element(type, locator));
    }

    /**
     * get the number of rows of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of table rows
     */
    public int numOfTableRows(Locator type, String locator, int elementMatch) {
        return numOfTableRows(new Element(type, locator, elementMatch));
    }

    /**
     * get the options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return String[]: the options from the select element
     */
    public String[] selectOptions(Locator type, String locator) {
        return selectOptions(new Element(type, locator));
    }

    /**
     * get the options from the select drop down
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return String[]: the options from the select element
     */
    public String[] selectOptions(Locator type, String locator, int elementMatch) {
        return selectOptions(new Element(type, locator, elementMatch));
    }

    /**
     * get the contents of a specific cell
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
     * @return WebElement: the cell element object, and all associated values
     *         with it
     */
    public WebElement tableCell(Locator type, String locator, int row, int col) {
        return tableCell(new Element(type, locator), row, col);
    }

    /**
     * get the contents of a specific cell
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
     * @return WebElement: the cell element object, and all associated values
     *         with it
     */
    public WebElement tableCell(Locator type, String locator, int elementMatch, int row, int col) {
        return tableCell(new Element(type, locator, elementMatch), row, col);
    }

    /**
     * get a specific column from a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param colNum
     *            - the column number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     */
    public List<WebElement> tableColumn(Locator type, String locator, int colNum) {
        return tableColumn(new Element(type, locator), colNum);
    }

    /**
     * get a specific column from a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param colNum
     *            - the column number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     */
    public List<WebElement> tableColumn(Locator type, String locator, int elementMatch, int colNum) {
        return tableColumn(new Element(type, locator, elementMatch), colNum);
    }

    /**
     * get the columns of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return List: a list of the table columns as WebElements
     */
    public List<List<WebElement>> tableColumns(Locator type, String locator) {
        return tableColumns(new Element(type, locator));
    }

    /**
     * get the columns of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return List: a list of the table columns as WebElements
     */
    public List<List<WebElement>> tableColumns(Locator type, String locator, int elementMatch) {
        return tableColumns(new Element(type, locator, elementMatch));
    }

    /**
     * get a specific column from a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param rowNum
     *            - the row number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     */
    public List<WebElement> tableRow(Locator type, String locator, int rowNum) {
        return tableRow(new Element(type, locator), rowNum);
    }

    /**
     * get a specific column from a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param rowNum
     *            - the row number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the columns as WebElements
     */
    public List<WebElement> tableRow(Locator type, String locator, int elementMatch, int rowNum) {
        return tableRow(new Element(type, locator, elementMatch), rowNum);
    }

    /**
     * get the rows of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return List: a list of the table rows as WebElements
     */
    public List<WebElement> tableRows(Locator type, String locator) {
        return tableRows(new Element(type, locator));
    }

    /**
     * get the rows of a table
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return List: a list of the table rows as WebElements
     */
    public List<WebElement> tableRows(Locator type, String locator, int elementMatch) {
        return tableRows(new Element(type, locator, elementMatch));
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    /**
     * is the passed in element present, and if it is, is it an input
     * 
     * @param element
     *            - the element to be checked
     * @return Boolean: is the element present AND an input
     */
    private boolean isPresentInput(Element element) {
        // wait for element to be present
        if (!is.elementPresent(element, false)) {
            wait.forElementPresent(element, 5);
        }
        if (!is.elementPresent(element, false)) {
            return false;
        }
        return is.elementInput(element, false);
    }

    /**
     * get the option from the select drop down
     *
     * @param element
     *            - the element to obtain information from
     * @return String: the option from the select element
     */
    public String selectedText(Element element) {
        if (!isPresentInput(element)) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        Select dropdown = new Select(webElement);
        WebElement option = dropdown.getFirstSelectedOption();
        return option.getText();
    }

    /**
     * get the options from the select drop down
     *
     * @param element
     *            - the element to obtain information from
     * @return String[]: the options from the select element
     */
    public String[] selectedTexts(Element element) {
        if (!isPresentInput(element)) {
            return new String[0];
        }
        WebElement webElement = element.getWebElement();
        Select dropdown = new Select(webElement);
        List<WebElement> options = dropdown.getAllSelectedOptions();
        String[] stringOptions = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            stringOptions[i] = options.get(i).getText();
        }
        return stringOptions;
    }

    /**
     * get the option value from the select drop down
     *
     * @param element
     *            - the element to obtain information from
     * @return String: the options from the select element
     */
    public String selectedValue(Element element) {
        if (!isPresentInput(element)) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        Select dropdown = new Select(webElement);
        WebElement option = dropdown.getFirstSelectedOption();
        return option.getAttribute(VALUE);
    }

    /**
     * get the option values from the select drop down
     *
     * @param element
     *            - the element to obtain information from
     * @return String[]: the options from the select element
     */
    public String[] selectedValues(Element element) {
        if (!isPresentInput(element)) {
            return new String[0];
        }
        WebElement webElement = element.getWebElement();
        Select dropdown = new Select(webElement);
        List<WebElement> options = dropdown.getAllSelectedOptions();
        String[] stringOptions = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            stringOptions[i] = options.get(i).getAttribute(VALUE);
        }
        return stringOptions;
    }

    /**
     * the generic selenium get text from an element functionality implemented
     *
     * @param element
     *            - the element to obtain information from
     * @return String - the text of the element
     */
    public String text(Element element) {
        if (!is.elementPresent(element)) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        return webElement.getText();
    }

    /**
     * the generic selenium get value from an element functionality implemented
     *
     * @param element
     *            - the element to obtain information from
     * @return String - the text of the element
     */
    public String value(Element element) {
        if (!is.elementInput(element)) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        return webElement.getAttribute(VALUE);
    }

    /**
     * a function to return one css attribute of the provided element
     *
     * @param element
     *            - the element to obtain information from
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String css(Element element, String attribute) {
        if (!is.elementPresent(element)) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        return webElement.getCssValue(attribute);
    }

    /**
     * a function to return one attribute of the provided element
     *
     * @param element
     *            - the element to obtain information from
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String attribute(Element element, String attribute) {
        if (!is.elementPresent(element)) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        return webElement.getAttribute(attribute);
    }

    /**
     * a function to return all attributes of the provided element
     *
     * @param element
     *            - the element to obtain information from
     * @return String - the value of the css attribute
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> allAttributes(Element element) {
        if (!is.elementPresent(element)) {
            return new HashMap<>();
        }
        try {
            WebElement webElement = element.getWebElement();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Map<String, String>) js.executeScript(
                    "var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
                    webElement);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a way to execute custom javascript functions
     * 
     * @param element
     *            - the element to obtain information from
     * @param javascriptFunction
     *            - the javascript function that is going to be executed
     * @return Object: any resultant output from the javascript command
     */
    public Object eval(Element element, String javascriptFunction) {
        if (!is.elementPresent(element)) {
            return null;
        }
        try {
            WebElement webElement = element.getWebElement();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return js.executeScript(javascriptFunction, webElement);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * get the number of options from the select drop down
     *
     * @param element
     *            - the element to obtain information from
     * @return Integer: the number of select options
     */
    public int numOfSelectOptions(Element element) {
        // wait for element to be present
        if (!is.elementPresent(element)) {
            wait.forElementPresent(element);
        }
        if (!is.elementPresent(element)) {
            return 0;
        }
        WebElement webElement = element.getWebElement();
        List<WebElement> allOptions = webElement.findElements(By.tagName("option"));
        return allOptions.size();
    }

    /**
     * get the options from the select drop down
     *
     * @param element
     *            - the element to obtain information from
     * @return String[]: the options from the select element
     */
    public String[] selectOptions(Element element) {
        // wait for element to be present
        if (!is.elementPresent(element)) {
            wait.forElementPresent(element);
        }
        if (!is.elementPresent(element)) {
            return new String[0];
        }
        WebElement webElement = element.getWebElement();
        List<WebElement> allOptions = webElement.findElements(By.tagName("option"));
        String[] options = new String[allOptions.size()];
        for (int i = 0; i < allOptions.size(); i++) {
            options[i] = allOptions.get(i).getAttribute(VALUE);
        }
        return options;
    }

    /**
     * get the rows of a table
     *
     * @param element
     *            - the element to obtain information from
     * @return List: a list of the table rows as WebElements
     */
    public List<WebElement> tableRows(Element element) {
        // wait for element to be present
        if (!is.elementPresent(element)) {
            wait.forElementPresent(element);
        }
        if (!is.elementPresent(element)) {
            return new ArrayList<>();
        }
        WebElement webElement = element.getWebElement();
        // this locator may need to be updated
        return webElement.findElements(By.tagName("tr"));
    }

    /**
     * get the number of rows of a table
     *
     * @param element
     *            - the element to obtain information from
     * @return Integer: the number of table rows
     */
    public int numOfTableRows(Element element) {
        List<WebElement> rows = tableRows(element);
        return rows.size();
    }

    /**
     * get the columns of a table
     *
     * @param element
     *            - the element to obtain information from
     * @return List: a list of the table columns as WebElements
     */
    public List<List<WebElement>> tableColumns(Element element) {
        // wait for element to be present
        if (!is.elementPresent(element)) {
            wait.forElementPresent(element);
        }
        if (!is.elementPresent(element)) {
            return new ArrayList<>();
        }
        List<WebElement> rows = tableRows(element);
        List<WebElement> row = tableRow(element, 1);
        List<List<WebElement>> columns = new ArrayList<>();
        for (int i = 0; i < row.size(); i++) {
            List<WebElement> column = new ArrayList<>();
            for (int j = 0; j < rows.size(); j++) {
                List<WebElement> cells = tableRow(element, j);
                column.add(cells.get(i));
            }
            columns.add(column);
        }
        return columns;
    }

    /**
     * get the number of columns of a table
     *
     * @param element
     *            - the element to obtain information from
     * @return Integer: the number of table columns
     */
    public int numOfTableColumns(Element element) {
        List<List<WebElement>> columns = tableColumns(element);
        return columns.size();
    }

    /**
     * get a specific row from a table
     *
     * @param element
     *            - the element to obtain information from
     * @param rowNum
     *            - the row number of the table to obtain - note, row numbering
     *            starts at 1, NOT 0
     * @return List: a list of the table cells in the row as WebElements
     */
    public List<WebElement> tableRow(Element element, int rowNum) {
        List<WebElement> rows = tableRows(element);
        if (rows.size() < rowNum) {
            return new ArrayList<>();
        }
        WebElement thisRow = rows.get(rowNum);

        List<WebElement> cells = thisRow.findElements(By.xpath(".//th|.//td"));
        List<WebElement> row = new ArrayList<>();
        for (WebElement cell : cells) {
            row.add(cell);
        }
        return row;
    }

    /**
     * get a specific column from a table
     *
     * @param element
     *            - the element to obtain information from
     * @param colNum
     *            - the column number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the column as WebElements
     */
    public List<WebElement> tableColumn(Element element, int colNum) {
        List<List<WebElement>> columns = tableColumns(element);
        if (columns.size() < colNum) {
            return new ArrayList<>();
        }
        return columns.get(colNum);
    }

    /**
     * get the contents of a specific cell
     *
     * @param element
     *            - the element to obtain information from
     * @param rowNum
     *            - the number of the row in the table - note, row numbering
     *            starts at 1, NOT 0
     * @param colNum
     *            - the number of the column in the table - note, column
     *            numbering starts at 1, NOT 0
     * @return WebElement: the cell element object, and all associated values
     *         with it
     */
    public WebElement tableCell(Element element, int rowNum, int colNum) {
        List<WebElement> row = tableRow(element, rowNum);
        if (row.size() < colNum) {
            return null;
        }
        return row.get(colNum);
    }

    /**
     * An extension of the selenium functionality to retrieve the current url of
     * the application
     *
     * @return String - current url
     */
    public String location() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * An extension of the selenium functionality to retrieve the current title
     * of the application
     *
     * @return String - title
     */
    public String title() {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * An extension of the selenium functionality to retrieve the html source of
     * the application
     *
     * @return String - page source
     */
    public String htmlSource() {
        try {
            return driver.getPageSource();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a way to execute custom javascript functions
     *
     * @param javascriptFunction
     *            - the javascript function that is going to be executed
     * @return Object: any resultant output from the javascript command
     */
    public Object eval(String javascriptFunction) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return js.executeScript(javascriptFunction);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a method to return the content of an alert
     *
     * @return String - the content of an alert
     */
    public String alert() {
        if (!is.alertPresent()) {
            wait.forAlertPresent();
        }
        if (!is.alertPresent()) {
            return null;
        }
        try {
            Alert alert = driver.switchTo().alert();
            return alert.getText();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a method to return the content of a confirmation
     *
     * @return String - the content of the confirmation
     */
    public String confirmation() {
        if (!is.confirmationPresent()) {
            wait.forConfirmationPresent();
        }
        if (!is.confirmationPresent()) {
            return null;
        }
        try {
            Alert alert = driver.switchTo().alert();
            return alert.getText();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a method to return the content of a prompt
     *
     * @return String - the content of the prompt
     */
    public String prompt() {
        if (!is.promptPresent()) {
            wait.forPromptPresent();
        }
        if (!is.promptPresent()) {
            return null;
        }
        try {
            Alert alert = driver.switchTo().alert();
            return alert.getText();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a method to get the full cookie by a particular name
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return Cookie - the cookie
     */
    public Cookie cookie(String expectedCookieName) {
        try {
            return driver.manage().getCookieNamed(expectedCookieName);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a method to get the value of a particular cookie
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return String - the value of the cookie
     */
    public String cookieValue(String expectedCookieName) {
        Cookie cookie = cookie(expectedCookieName);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * a method to get the path of a particular cookie
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return String - the path of the cookie
     */
    public String cookiePath(String expectedCookieName) {
        Cookie cookie = cookie(expectedCookieName);
        if (cookie != null) {
            return cookie.getPath();
        }
        return null;
    }

    /**
     * a method to get the domain of a particular cookie
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return String - the domain of the cookie
     */
    public String cookieDomain(String expectedCookieName) {
        Cookie cookie = cookie(expectedCookieName);
        if (cookie != null) {
            return cookie.getDomain();
        }
        return null;
    }

    /**
     * a method to get the expriation of a particular cookie
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return String - the expiration of the cookie
     */
    public Date cookieExpiration(String expectedCookieName) {
        Cookie cookie = cookie(expectedCookieName);
        if (cookie != null) {
            return cookie.getExpiry();
        }
        return null;
    }
}