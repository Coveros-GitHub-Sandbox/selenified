package com.coveros.selenified.selenium.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import com.coveros.selenified.tools.General;

public class Get {
    private static final Logger log = Logger.getLogger(General.class);

    // what element are we trying to interact with on the page
    private Element element;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private WebDriver driver;

    // constants
    private static final String VALUE = "value";
    private static final String OPTION = "option";

    public Get(WebDriver driver, Element element) {
        this.driver = driver;
        this.element = element;
    }

    // ////////////////////////////////////
    // checking element availability
    // ////////////////////////////////////

    /**
     * a method to determine how many elements match the selector
     * 
     * @return Integer: how many element match the selector
     */
    public int matchCount() {
        return element.getWebElements().size();
    }

    /**
     * is the passed in element present, and if it is, is it an input
     * 
     * @param element
     *            - the element to be checked
     * @return Boolean: is the element present AND an input
     */
    private boolean isPresentInput() {
        // wait for element to be present
        if (!element.is().present(false)) {
            element.waitFor().present(5);
        }
        if (!element.is().present(false)) {
            return false;
        }
        return element.is().input(false);
    }

    /**
     * get the option from the select drop down
     *
     * @return String: the option from the select element
     */
    public String selectedOption() {
        if (!isPresentInput()) {
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
     * @return String[]: the options from the select element
     */
    public String[] selectedOptions() {
        if (!isPresentInput()) {
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
     * @return String: the options from the select element
     */
    public String selectedValue() {
        if (!isPresentInput()) {
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
     * @return String[]: the options from the select element
     */
    public String[] selectedValues() {
        if (!isPresentInput()) {
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
     * @return String - the text of the element
     */
    public String text() {
        if (!element.is().present()) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        return webElement.getText();
    }

    /**
     * the generic selenium get value from an element functionality implemented
     *
     * @return String - the text of the element
     */
    public String value() {
        if (!element.is().input()) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        return webElement.getAttribute(VALUE);
    }

    /**
     * a function to return one css attribute of the provided element
     *
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String css(String attribute) {
        if (!element.is().present()) {
            return null;
        }
        try {
            WebElement webElement = element.getWebElement();
            return webElement.getCssValue(attribute);
        } catch (NoSuchMethodError | Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * a function to return one attribute of the provided element
     *
     * @param attribute
     *            - the css attribute to be returned
     * @return String - the value of the css attribute
     */
    public String attribute(String attribute) {
        if (!element.is().present()) {
            return null;
        }
        WebElement webElement = element.getWebElement();
        return webElement.getAttribute(attribute);
    }

    /**
     * a function to return all attributes of the provided element
     *
     * @return String - the value of the css attribute
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
        if (!element.is().present()) {
            return null;
        }
        try {
            WebElement webElement = element.getWebElement();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return js.executeScript(javascriptFunction, webElement);
        } catch (NoSuchMethodError | Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * get the number of options from the select drop down
     *
     */
    public int numOfSelectOptions() {
        // wait for element to be present
        if (!element.is().present()) {
            element.waitFor().present();
        }
        if (!element.is().present()) {
            return 0;
        }
        WebElement webElement = element.getWebElement();
        List<WebElement> allOptions = webElement.findElements(By.tagName(OPTION));
        return allOptions.size();
    }

    /**
     * get the options from the select drop down
     *
     * @return String[]: the options from the select element
     */
    public String[] selectOptions() {
        // wait for element to be present
        if (!element.is().present()) {
            element.waitFor().present();
        }
        if (!element.is().present()) {
            return new String[0];
        }
        WebElement webElement = element.getWebElement();
        List<WebElement> allOptions = webElement.findElements(By.tagName(OPTION));
        String[] options = new String[allOptions.size()];
        for (int i = 0; i < allOptions.size(); i++) {
            options[i] = allOptions.get(i).getText();
        }
        return options;
    }

    /**
     * get the values from the select drop down
     *
     * @return String[]: the options from the select element
     */
    public String[] selectValues() {
        // wait for element to be present
        if (!element.is().present()) {
            element.waitFor().present();
        }
        if (!element.is().present()) {
            return new String[0];
        }
        WebElement webElement = element.getWebElement();
        List<WebElement> allOptions = webElement.findElements(By.tagName(OPTION));
        String[] options = new String[allOptions.size()];
        for (int i = 0; i < allOptions.size(); i++) {
            options[i] = allOptions.get(i).getAttribute(VALUE);
        }
        return options;
    }

    /**
     * get the rows of a table
     *
     * @return List: a list of the table rows as WebElements
     */
    public List<WebElement> tableRows() {
        // wait for element to be present
        if (!element.is().present()) {
            element.waitFor().present();
        }
        if (!element.is().present()) {
            return new ArrayList<>();
        }
        WebElement webElement = element.getWebElement();
        // this locator may need to be updated
        return webElement.findElements(By.tagName("tr"));
    }

    /**
     * get the number of rows of a table
     *
     */
    public int numOfTableRows() {
        List<WebElement> rows = tableRows();
        return rows.size();
    }

    /**
     * get the columns of a table
     *
     * @return List: a list of the table columns as WebElements
     */
    public List<List<WebElement>> tableColumns() {
        // wait for element to be present
        if (!element.is().present()) {
            element.waitFor().present();
        }
        if (!element.is().present()) {
            return new ArrayList<>();
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
     * get the number of columns of a table
     *
     */
    public int numOfTableColumns() {
        List<List<WebElement>> columns = tableColumns();
        return columns.size();
    }

    /**
     * get a specific row from a table
     *
     * @param rowNum
     *            - the row number of the table to obtain - note, row numbering
     *            starts at 1, NOT 0
     * @return List: a list of the table cells in the row as WebElements
     */
    public List<WebElement> tableRow(int rowNum) {
        List<WebElement> rows = tableRows();
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
     * @param colNum
     *            - the column number of the table to obtain - note, column
     *            numbering starts at 1, NOT 0
     * @return List: a list of the table cells in the column as WebElements
     */
    public List<WebElement> tableColumn(int colNum) {
        List<List<WebElement>> columns = tableColumns();
        if (columns.size() < colNum) {
            return new ArrayList<>();
        }
        return columns.get(colNum);
    }

    /**
     * get the contents of a specific cell
     *
     * @param rowNum
     *            - the number of the row in the table - note, row numbering
     *            starts at 1, NOT 0
     * @param colNum
     *            - the number of the column in the table - note, column
     *            numbering starts at 1, NOT 0
     * @return WebElement: the cell element object, and all associated values
     *         with it
     */
    public WebElement tableCell(int rowNum, int colNum) {
        List<WebElement> row = tableRow(rowNum);
        if (row.size() < colNum) {
            return null;
        }
        return row.get(colNum);
    }
}