/*
 * Copyright 2018 Coveros, Inc.
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

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Get retrieves information about a particular element. If an object isn't
 * present, null will be returned
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/17/2018
 */
public class Get {
    private static final Logger log = Logger.getLogger(Get.class);

    // what element are we trying to interact with on the page
    private final Element element;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private final WebDriver driver;

    // constants
    private static final String VALUE = "value";

    public Get(WebDriver driver, Element element) {
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
    private boolean isPresentSelect() {
        return element.is().present() && element.is().select();
    }

    /**
     * Retrieves the selected option for the element. If the element isn't
     * present or a select, a null value will be returned.
     *
     * @return String: the option from the select element
     */
    public String selectedOption() {
        if (!isPresentSelect()) {
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
        if (!isPresentSelect()) {
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
        if (!isPresentSelect()) {
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
        if (!isPresentSelect()) {
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
            return new HashMap<>();
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
     * isn't present or a select, the returned response will be zero.
     *
     * @return Integer: how many select options are available in the select
     * element
     */
    public int numOfSelectOptions() {
        if (!isPresentSelect()) {
            return 0;
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
        if (!isPresentSelect()) {
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
        if (!isPresentSelect()) {
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
     * or a table, the returned response will be zero
     *
     * @return Integer: the number of rows the table has
     */
    public int numOfTableRows() {
        List<WebElement> rows = tableRows();
        if (rows == null) {
            return 0;
        }
        return rows.size();
    }

    /**
     * Retrieves the rows in the element. If the element isn't present or a
     * table, a null value will be returned.
     *
     * @return List: a list of the table rows as WebElements
     */
    @SuppressWarnings("squid:S1168")
    public List<WebElement> tableRows() {
        if (!element.is().present()) {
            return null;    // returning an empty array could be confused with no rows
        }
        if (!element.is().table()) {
            return null;    // returning an empty array could be confused with no rows
        }
        WebElement webElement = element.getWebElement();
        // this locator may need to be updated
        return webElement.findElements(By.tagName("tr"));
    }

    /**
     * Retrieves the number of columns in the element. If the element isn't
     * present or a table, the returned response will be zero
     *
     * @return Integer: the number of columns the table has
     */
    public int numOfTableColumns() {
        List<List<WebElement>> columns = tableColumns();
        if (columns == null) {
            return 0;
        }
        return columns.size();
    }

    /**
     * Retrieves the columns in the element. If the element isn't present or a
     * table, a null value will be returned.
     *
     * @return List: a list of the table columns as WebElements
     */
    @SuppressWarnings("squid:S1168")
    public List<List<WebElement>> tableColumns() {
        if (!element.is().present()) {
            return null;    // returning an empty array could be confused with no rows
        }
        if (!element.is().table()) {
            return null;    // returning an empty array could be confused with no columns
        }
        List<WebElement> rows = tableRows();
        List<WebElement> row = tableRow(1);
        List<List<WebElement>> columns = new ArrayList<>();
        for (int i = 0; i < row.size(); i++) {
            List<WebElement> column = new ArrayList<>();
            for (int j = 0; j < rows.size(); j++) {
                List<WebElement> cells = tableRow(j);
                column.add(cells.get(i));
            }
            columns.add(column);
        }
        return columns;
    }

    /**
     * Retrieves a specific row from the element. If the element isn't present
     * or a table, a null value will be returned. If the specified row is out of
     * range, an empty list is returned
     *
     * @param rowNum - the row number of the table to obtain - note, row numbering
     *               starts at 1, NOT 0
     * @return List: a list of the table cells in the row as WebElements
     */
    @SuppressWarnings("squid:S1168")
    public List<WebElement> tableRow(int rowNum) {
        List<WebElement> rows = tableRows();
        if (rows == null) {
            return null;    // returning an empty array could be confused with a row out of range
        }
        if (rows.size() < rowNum) {
            return new ArrayList<>();
        }
        WebElement thisRow = rows.get(rowNum);
        List<WebElement> cells = thisRow.findElements(By.xpath(".//th|.//td"));
        List<WebElement> row = new ArrayList<>();
        row.addAll(cells);
        return row;
    }

    /**
     * Retrieves a specific column from the element. If the element isn't
     * present or a table, a null value will be returned. If the specified row
     * is out of range, an empty list is returned
     *
     * @param colNum - the column number of the table to obtain - note, column
     *               numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the column as WebElements
     */
    @SuppressWarnings("squid:S1168")
    public List<WebElement> tableColumn(int colNum) {
        List<List<WebElement>> columns = tableColumns();
        if (columns == null) {
            return null;    // returning an empty array could be confused with a column out of range
        }
        if (columns.size() < colNum) {
            return new ArrayList<>();
        }
        return columns.get(colNum);
    }

    /**
     * Retrieves a specific cell from the element. If the element isn't present
     * or a table, or the row and cell combination doesn't exist, a null value
     * will be returned.
     *
     * @param rowNum - the number of the row in the table - note, row numbering
     *               starts at 1, NOT 0
     * @param colNum - the number of the column in the table - note, column
     *               numbering starts at 1, NOT 0
     * @return WebElement: the cell element object, and all associated values
     * with it
     */
    public WebElement tableCell(int rowNum, int colNum) {
        List<WebElement> row = tableRow(rowNum);
        if (row == null || row.size() < colNum) {
            return null;
        }
        return row.get(colNum);
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