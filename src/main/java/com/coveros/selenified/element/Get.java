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

package com.coveros.selenified.element;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Get retrieves information about a particular element. If an object isn't
 * present, null will be returned
 *
 * @author Max Saperstone
 * @version 3.1.0
 * @lastupdate 2/14/2019
 */
public class Get {
    private static final Logger log = Logger.getLogger(Get.class);
    // constants
    private static final String VALUE = "value";
    // the overall app that we are interacting with
    private final App app;
    // what element are we trying to interact with on the page
    private final Element element;
    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private final WebDriver driver;

    public Get(App app, WebDriver driver, Element element) {
        this.app = app;
        this.driver = driver;
        this.element = element;
    }

    // ////////////////////////////////////
    // checking element availability
    // ////////////////////////////////////

    /**
     * Retrieves the number of elements on the page, that match this element's
     * description
     *
     * @return Integer: how many element match the selector
     */
    public int matchCount() {
        return element.getWebElements().size();
    }

    /**
     * Determines if the element is both present and a select.
     *
     * @return Boolean: is the element present AND an input
     */
    private boolean isNotPresentSelect() {
        return !element.is().present() || !element.is().select();
    }

    /**
     * Retrieves the selected option for the element. If the element isn't
     * present or a select, a null value will be returned.
     *
     * @return String: the option from the select element
     */
    public String selectedOption() {
        if (isNotPresentSelect()) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        Select dropdown = new Select(webElement);
        WebElement option = dropdown.getFirstSelectedOption();
        return option.getText();
    }

    /**
     * Retrieves the selected options for the element. If the element isn't
     * present or a select, a null value will be returned.
     *
     * @return String[]: the options from the select element
     */
    @SuppressWarnings("squid:S1168")
    public String[] selectedOptions() {
        if (isNotPresentSelect()) {
            return null;    // returning an empty array could be confused with no options selected
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
     * Retrieves the selected value for the element. If the element isn't
     * present or a select, a null value will be returned.
     *
     * @return String: the options from the select element
     */
    public String selectedValue() {
        if (isNotPresentSelect()) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        Select dropdown = new Select(webElement);
        WebElement option = dropdown.getFirstSelectedOption();
        return option.getAttribute(VALUE);
    }

    /**
     * Retrieves the selected values for the element. If the element isn't
     * present or a select, a null value will be returned.
     *
     * @return String[]: the options from the select element
     */
    @SuppressWarnings("squid:S1168")
    public String[] selectedValues() {
        if (isNotPresentSelect()) {
            return null;    // returning an empty array could be confused with no values selected
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
     * Retrieves the text of the element. If the element isn't present, a null
     * value will be returned.
     *
     * @return String: the text of the element
     */
    public String text() {
        if (!element.is().present()) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        return webElement.getText();
    }

    /**
     * Retrieves the value of the element. If the element isn't present, or
     * isn't an input, a null value will be returned.
     *
     * @return String: the text of the element
     */
    public String value() {
        if (!element.is().present() || !element.is().input()) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        return webElement.getAttribute(VALUE);
    }

    /**
     * Retrieves the provided css attribute of the element. If the element isn't
     * present, the css attribute doesn't exist, or the attributes can't be
     * accessed, a null value will be returned.
     *
     * @param attribute - the css attribute to be returned
     * @return String: the value of the css attribute
     */
    public String css(String attribute) {
        if (!element.is().present()) {
            return null;
        }
        try {
            WebElement webElement = element.getWebElement();
            return webElement.getCssValue(attribute);
        } catch (NoSuchMethodError | Exception e) {
            log.warn(e);
            return null;
        }
    }

    /**
     * Retrieves the provided attribute of the element. If the element isn't
     * present, the attribute doesn't exist, or the attributes can't be
     * accessed, a null value will be returned.
     *
     * @param attribute - the css attribute to be returned
     * @return String: the value of the css attribute
     */
    public String attribute(String attribute) {
        if (!element.is().present()) {
            return null;
        }
        try {
            WebElement webElement = element.getWebElement();
            return webElement.getAttribute(attribute);
        } catch (NoSuchMethodError | Exception e) {
            log.warn(e);
            return null;
        }
    }

    /**
     * Retrieves all attributes of the element. If the element isn't present, or
     * the attributes can't be accessed, a null value will be returned.
     *
     * @return String: the value of the css attribute
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> allAttributes() {
        if (!element.is().present()) {
            return null;
        }
        try {
            WebElement webElement = element.getWebElement();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Map<String, String>) js.executeScript(
                    "var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
                    webElement);
        } catch (NoSuchMethodError | Exception e) {
            log.warn(e);
            return null;
        }
    }

    /**
     * Executes a provided script on the element, and returns the output of that
     * script. If the element doesn't exist, or there is an error executing this
     * script, a null value will be returned.
     *
     * @param javascriptFunction - the javascript function that is going to be executed
     * @return Object: any resultant output from the javascript command
     */
    public Object eval(String javascriptFunction) {
        if (!element.is().present()) {
            return null;
        }
        try {
            WebElement webElement = element.getWebElement();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return js.executeScript(javascriptFunction, webElement);
        } catch (NoSuchMethodError | Exception e) {
            log.warn(e);
            return null;
        }
    }

    /**
     * Retrieves the number of select options in the element. If the element
     * isn't present or a select, the returned response will be negative 1.
     *
     * @return Integer: how many select options are available in the select
     * element
     */
    public int numOfSelectOptions() {
        if (isNotPresentSelect()) {
            return -1;
        }
        WebElement webElement = element.getWebElement();
        Select dropdown = new Select(webElement);
        List<WebElement> options = dropdown.getOptions();
        return options.size();
    }

    /**
     * Retrieves the select options in the element. If the element isn't present
     * or a select, a null value will be returned.
     *
     * @return String[]: the options from the select element
     */
    @SuppressWarnings("squid:S1168")
    public String[] selectOptions() {
        if (isNotPresentSelect()) {
            return null;    // returning an empty array could be confused with no options available
        }
        WebElement webElement = element.getWebElement();
        Select dropdown = new Select(webElement);
        List<WebElement> options = dropdown.getOptions();
        String[] stringOptions = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            stringOptions[i] = options.get(i).getText();
        }
        return stringOptions;
    }

    /**
     * Retrieves the select options in the element. If the element isn't present
     * or a select, a null value will be returned.
     *
     * @return String[]: the options from the select element
     */
    @SuppressWarnings("squid:S1168")
    public String[] selectValues() {
        if (isNotPresentSelect()) {
            return null;    // returning an empty array could be confused with no options available
        }
        WebElement webElement = element.getWebElement();
        Select dropdown = new Select(webElement);
        List<WebElement> options = dropdown.getOptions();
        String[] stringOptions = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            stringOptions[i] = options.get(i).getAttribute(VALUE);
        }
        return stringOptions;
    }

    /**
     * Retrieves the number of rows in the element. If the element isn't present
     * or a table, the returned response will be negative one
     *
     * @return Integer: the number of rows the table has
     */
    public int numOfTableRows() {
        Element rows = tableRows();
        if (rows == null) {
            return -1;
        }
        return rows.get().matchCount();
    }

    /**
     * Retrieves the rows in the element. If the element isn't present or a
     * table, a null value will be returned. The rows will be returned in one element
     * and they can be iterated through, or selecting using the `setMatch` method
     *
     * @return List: a list of the table rows as WebElements
     */
    @SuppressWarnings("squid:S1168")
    public Element tableRows() {
        if (!element.is().present()) {
            return null;    // returning an empty array could be confused with no rows
        }
        if (!element.is().table()) {
            return null;    // returning an empty array could be confused with no rows
        }
        return element.findChild(app.newElement(Locator.TAGNAME, "tr"));
    }

    /**
     * Retrieves the number of columns in the element. If the element isn't
     * present or a table, the returned response will be negative one
     *
     * @return Integer: the number of columns the table has
     */
    public int numOfTableColumns() {
        Element rows = tableRows();
        if (rows == null) {
            return -1;
        }
        Element thCells = rows.findChild(app.newElement(Locator.TAGNAME, "th"));
        Element tdCells = rows.findChild(app.newElement(Locator.TAGNAME, "td"));
        return thCells.get().matchCount() + tdCells.get().matchCount();
    }

    /**
     * Retrieves a specific row from the element. If the element isn't present
     * or a table, or doesn't have that many rows a null value will be returned.
     *
     * @param rowNum - the row number of the table to obtain - note, row numbering
     *               starts at 0
     * @return List: a list of the table cells in the row as WebElements
     */
    @SuppressWarnings("squid:S1168")
    public Element tableRow(int rowNum) {
        Element rows = tableRows();
        if (rows == null) {
            return null;
        }
        if (numOfTableRows() < rowNum) {
            return null;
        }
        return rows.get(rowNum);
    }

    /**
     * Retrieves a specific cell from the element. If the element isn't present
     * or a table, or the row and cell combination doesn't exist, a null value
     * will be returned.
     *
     * @param rowNum - the number of the row in the table - note, row numbering
     *               starts at 0
     * @param colNum - the number of the column in the table - note, column
     *               numbering starts at 0
     * @return WebElement: the cell element object, and all associated values
     * with it
     */
    public Element tableCell(int rowNum, int colNum) {
        Element row = tableRow(rowNum);
        if (row == null || numOfTableColumns() < colNum) {
            return null;
        }
        Element thCells = row.findChild(app.newElement(Locator.TAGNAME, "th"));
        Element tdCells = row.findChild(app.newElement(Locator.TAGNAME, "td"));
        if (thCells.get().matchCount() > colNum) {
            return thCells.get(colNum);
        } else {
            return tdCells.get(colNum - thCells.get().matchCount());
        }
    }

    /**
     * Retrieves the xpath associated with the particular element. If the
     * element doesn't exist, a null value will be returned
     *
     * @return String: the element's xpath
     */
    public String xPath() {
        return (String) eval(
                "gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return c.tagName}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]).toLowerCase();");
    }
}