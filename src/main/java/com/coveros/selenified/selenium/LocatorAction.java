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

package com.coveros.selenified.selenium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.log4testng.Logger;

import com.coveros.selenified.exceptions.InvalidLocatorTypeException;
import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.output.Assert.Result;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.General;

/**
 * Selenium Webdriver Before each action is performed a screenshot is taken
 * After each check is performed a screenshot is taken These are all placed into
 * the output file
 *
 * @author Max Saperstone
 * @version 2.0.0
 * @lastupdate 5/15/2017
 */
public class LocatorAction {

	private static final Logger log = Logger.getLogger(General.class);

	// this will be the name of the file we write all commands out to
	private OutputFile file;

	// what locator actions are available in webdriver
	// this is the driver that will be used for all selenium actions
	private WebDriver driver;

	// constants
	private static final String VALUE = "value";
	private static final String IN = "' in ";

	private static final String WAIT = "Wait up to ";
	private static final String WAITING = "After waiting ";
	private static final String WAITED = "Waited ";
	private static final String SECONDS = " seconds for ";

	private static final String CHECKING = "Checking for ";

	private static final String PRESENT = " to be present";
	private static final String DISPLAYED = " to be displayed";
	private static final String ENABLED = " to be enabled";
	private static final String SELECTED = " to have something selected";

	private static final String NOTPRESENT = " as it is not present";
	private static final String NOTDISPLAYED = " as it is not displayed";
	private static final String NOTENABLED = " as it is not enabled";
	private static final String NOTINPUT = " as it is not an input";

	private static final String CANTTYPE = "Unable to type in ";
	private static final String CANTMOVE = "Unable to move to ";

	/**
	 * the constructor, determining which browser use and how to run the
	 * browser: either grid or standalone
	 *
	 * @param driver
	 *            - the webdriver used to control the browser
	 * @param file
	 *            - the TestOutput file. This is provided by the
	 *            SeleniumTestBase functionality
	 */
	public LocatorAction(WebDriver driver, OutputFile file) {
		this.driver = driver;
		this.file = file;
	}

	/**
	 * a method for waiting until an element is present
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param seconds
	 *            : the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementPresent(Locator type, String locator, int elementMatch, double seconds)
			throws IOException {
		String action = WAIT + seconds + SECONDS + type + " " + locator + PRESENT;
		String expected = type + " " + locator + " is present";
		// wait for up to XX seconds for the error message
		double end = System.currentTimeMillis() + (seconds * 1000);
		while (System.currentTimeMillis() < end) {
			try { // If results have been returned, the results are displayed in
					// a drop down.
				getWebElement(type, locator, elementMatch).getText();
				break;
			} catch (NoSuchElementException | StaleElementReferenceException e) {
				log.error(e);
			}
		}
		double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
		timetook = timetook / 1000;
		if (!isElementPresent(type, locator, elementMatch, false)) {
			file.recordAction(action, expected, WAITING + timetook + SECONDS + type + " " + locator + " is not present",
					Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, WAITED + timetook + SECONDS + type + " " + locator + PRESENT,
				Result.SUCCESS);
		return 0;
	}

	/**
	 * a method for waiting until an element is no longer present
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param seconds
	 *            : the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotPresent(Locator type, String locator, int elementMatch, double seconds)
			throws IOException {
		String action = WAIT + seconds + SECONDS + type + " " + locator + " to not be present";
		String expected = type + " " + locator + " is not present";
		// wait for up to XX seconds for the error message
		double end = System.currentTimeMillis() + (seconds * 1000);
		while (System.currentTimeMillis() < end) {
			if (!isElementPresent(type, locator, elementMatch, false)) {
				break;
			}
		}
		double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
		timetook = timetook / 1000;
		if (isElementPresent(type, locator, elementMatch, false)) {
			file.recordAction(action, expected,
					WAITING + timetook + SECONDS + type + " " + locator + " is still present", Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, WAITED + timetook + SECONDS + type + " " + locator + " to not be present",
				Result.SUCCESS);
		return 0;
	}

	/**
	 * a method for waiting until an element is displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param seconds
	 *            : the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementDisplayed(Locator type, String locator, int elementMatch, double seconds)
			throws IOException {
		String action = WAIT + seconds + SECONDS + type + " " + locator + DISPLAYED;
		String expected = type + " " + locator + " is displayed";
		double start = System.currentTimeMillis();
		if (!isElementPresent(type, locator, elementMatch, false)) {
			int success = waitForElementPresent(type, locator, elementMatch, seconds);
			if (success == 1) {
				return success;
			}
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		if (!element.isDisplayed()) {
			// wait for up to XX seconds
			double end = System.currentTimeMillis() + (seconds * 1000);
			while (System.currentTimeMillis() < end) {
				if (element.isDisplayed()) {
					break;
				}
			}
		}
		double timetook = (System.currentTimeMillis() - start) / 1000;
		if (!element.isDisplayed()) {
			file.recordAction(action, expected,
					WAITING + timetook + SECONDS + type + " " + locator + " is not displayed", Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, WAITED + timetook + SECONDS + type + " " + locator + DISPLAYED,
				Result.SUCCESS);
		return 0;
	}

	/**
	 * a method for waiting until an element is not displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param seconds
	 *            : the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotDisplayed(Locator type, String locator, int elementMatch, double seconds)
			throws IOException {
		// this might fail if the element disappears completely
		String action = WAIT + seconds + SECONDS + type + " " + locator + " to not be displayed";
		String expected = type + " " + locator + " is not displayed";
		double start = System.currentTimeMillis();
		WebElement element = getWebElement(type, locator, elementMatch);
		if (element.isDisplayed()) {
			// wait for up to XX seconds
			double end = System.currentTimeMillis() + (seconds * 1000);
			while (System.currentTimeMillis() < end) {
				if (!element.isDisplayed()) {
					break;
				}
			}
		}
		double timetook = (System.currentTimeMillis() - start) / 1000;
		if (element.isDisplayed()) {
			file.recordAction(action, expected,
					WAITING + timetook + SECONDS + type + " " + locator + " is still displayed", Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, WAITED + timetook + SECONDS + type + " " + locator + " to not be displayed",
				Result.SUCCESS);
		return 0;
	}

	/**
	 * a method for waiting until an element is enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param seconds
	 *            : the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementEnabled(Locator type, String locator, int elementMatch, double seconds)
			throws IOException {
		String action = WAIT + seconds + SECONDS + type + " " + locator + ENABLED;
		String expected = type + " " + locator + " is enabled";
		double start = System.currentTimeMillis();
		if (!isElementPresent(type, locator, elementMatch, false)) {
			waitForElementPresent(type, locator, elementMatch, seconds);
		}
		if (!isElementEnabled(type, locator, elementMatch, false)) {
			WebElement element = getWebElement(type, locator, elementMatch);
			// wait for up to XX seconds for the error message
			double end = System.currentTimeMillis() + (seconds * 1000);
			while (System.currentTimeMillis() < end) {
				// If results have been returned, the results are displayed
				// in a drop down.
				if (element.isEnabled()) {
					break;
				}
			}
		}
		double timetook = (System.currentTimeMillis() - start) / 1000;
		if (!isElementEnabled(type, locator, elementMatch, false)) {
			file.recordAction(action, expected, WAITING + timetook + SECONDS + type + " " + locator + " is not enabled",
					Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, WAITED + timetook + SECONDS + type + " " + locator + ENABLED,
				Result.SUCCESS);
		return 0;
	}

	/**
	 * a method for waiting until an element is not enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param seconds
	 *            : the number of seconds to wait
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int waitForElementNotEnabled(Locator type, String locator, int elementMatch, double seconds)
			throws IOException {
		// this might fail if the element is no longer present
		String action = WAIT + seconds + SECONDS + type + " " + locator + " to not be enabled";
		String expected = type + " " + locator + " is not enabled";
		double start = System.currentTimeMillis();
		WebElement element = getWebElement(type, locator, elementMatch);
		if (element.isEnabled()) {
			// wait for up to XX seconds
			double end = System.currentTimeMillis() + (seconds * 1000);
			while (System.currentTimeMillis() < end) {
				if (!element.isEnabled()) {
					break;
				}
			}
		}
		double timetook = (System.currentTimeMillis() - start) / 1000;
		if (element.isEnabled()) {
			file.recordAction(action, expected,
					WAITING + timetook + SECONDS + type + " " + locator + " is still enabled", Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, WAITED + timetook + SECONDS + type + " " + locator + " to not be enabled",
				Result.SUCCESS);
		return 0;
	}

	/**
	 * a method for checking if an element is present
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param print
	 *            : whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementPresent(Locator type, String locator, int elementMatch, boolean print) throws IOException {
		boolean isPresent = false;
		try {
			getWebElement(type, locator, elementMatch).getText();
			isPresent = true;
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected(CHECKING + type + " " + locator + PRESENT);
		}
		return isPresent;
	}

	/**
	 * a method for checking if an element is an input; it needs to be an input,
	 * select, or textarea
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param print
	 *            : whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementInput(Locator type, String locator, int elementMatch, boolean print) throws IOException {
		boolean isInput = false;
		try {
			WebElement element = getWebElement(type, locator, elementMatch);
			if ("input".equalsIgnoreCase(element.getTagName()) || "textarea".equalsIgnoreCase(element.getTagName())
					|| "select".equalsIgnoreCase(element.getTagName())) {
				isInput = true;
			}
		} catch (NoSuchElementException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected(CHECKING + type + " " + locator + " to be an input element");
		}
		return isInput;
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param print
	 *            : whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 * @throws IOException
	 */
	public boolean isElementEnabled(Locator type, String locator, int elementMatch, boolean print) throws IOException {
		boolean isEnabled = false;
		try {
			isEnabled = getWebElement(type, locator, elementMatch).isEnabled();
		} catch (NoSuchElementException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected(CHECKING + type + " " + locator + ENABLED);
		}
		return isEnabled;
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param print
	 *            : whether or not to printout the action
	 * @return boolean: whether the element is checked or not
	 * @throws IOException
	 */
	public boolean isElementChecked(Locator type, String locator, int elementMatch, boolean print) throws IOException {
		boolean isChecked = false;
		try {
			isChecked = getWebElement(type, locator, elementMatch).isSelected();
		} catch (NoSuchElementException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected(CHECKING + type + " " + locator + " to be checked");
		}
		return isChecked;
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param print
	 *            : whether or not to printout the action
	 * @return boolean: whether the element is displayed or not
	 * @throws IOException
	 */
	public boolean isElementDisplayed(Locator type, String locator, int elementMatch, boolean print)
			throws IOException {
		boolean isDisplayed = false;
		try {
			isDisplayed = getWebElement(type, locator, elementMatch).isDisplayed();
		} catch (NoSuchElementException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected(CHECKING + type + " " + locator + DISPLAYED);
		}
		return isDisplayed;
	}

	/**
	 * determine if something is selected from a drop down menu
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Boolean: was something selected in the drop down
	 * @throws IOException
	 */
	public boolean isSomethingSelected(Locator type, String locator, int elementMatch, boolean print)
			throws IOException {
		boolean isSelected = false;
		if (isElementInput(type, locator, elementMatch, false)) {
			WebElement element = getWebElement(type, locator, elementMatch);
			if ("input".equalsIgnoreCase(element.getTagName())) {
				isSelected = element.isSelected();
			} else if ("select".equalsIgnoreCase(element.getTagName())) {
				isSelected = getSelectedValues(type, locator, elementMatch).length > 0;
			}
		}
		if (print) {
			file.recordExpected(CHECKING + type + " " + locator + SELECTED);
		}
		return isSelected;
	}

	// ///////////////////////////////////
	// selenium retreval functions
	// ///////////////////////////////////

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
	 * @throws IOException
	 */
	public int getNumOfSelectOptions(Locator type, String locator, int elementMatch) throws IOException {
		// wait for element to be present
		if (!isElementPresent(type, locator, elementMatch, false)) {
			waitForElementPresent(type, locator, elementMatch, 5);
		}
		if (!isElementPresent(type, locator, elementMatch, false)) {
			return 0;
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		List<WebElement> allOptions = element.findElements(By.tagName("option"));
		return allOptions.size();
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
	 * @throws IOException
	 */
	public String[] getSelectOptions(Locator type, String locator, int elementMatch) throws IOException {
		// wait for element to be present
		if (!isElementPresent(type, locator, elementMatch, false)) {
			waitForElementPresent(type, locator, elementMatch, 5);
		}
		if (!isElementPresent(type, locator, elementMatch, false)) {
			return new String[0];
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		List<WebElement> allOptions = element.findElements(By.tagName("option"));
		String[] options = new String[allOptions.size()];
		for (int i = 0; i < allOptions.size(); i++) {
			options[i] = allOptions.get(i).getAttribute(VALUE);
		}
		return options;
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
	 * @throws IOException
	 */
	public List<WebElement> getTableRows(Locator type, String locator, int elementMatch) throws IOException {
		// wait for element to be present
		if (!isElementPresent(type, locator, elementMatch, false)) {
			waitForElementPresent(type, locator, elementMatch, 5);
		}
		if (!isElementPresent(type, locator, elementMatch, false)) {
			return new ArrayList<>();
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		// this locator may need to be updated
		return element.findElements(By.tagName("tr"));
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
	 * @throws IOException
	 */
	public List<List<WebElement>> getTableColumns(Locator type, String locator, int elementMatch) throws IOException {
		// wait for element to be present
		if (!isElementPresent(type, locator, elementMatch, false)) {
			waitForElementPresent(type, locator, elementMatch, 5);
		}
		if (!isElementPresent(type, locator, elementMatch, false)) {
			return new ArrayList<>();
		}
		List<WebElement> rows = getTableRows(type, locator, elementMatch);
		List<WebElement> row = getTableRow(type, locator, elementMatch, 1);
		List<List<WebElement>> columns = new ArrayList<>();
		for (int i = 0; i < row.size(); i++) {
			List<WebElement> column = new ArrayList<>();
			for (int j = 0; j < rows.size(); j++) {
				List<WebElement> cells = getTableRow(type, locator, elementMatch, j);
				column.add(cells.get(i));
			}
			columns.add(column);
		}
		return columns;
	}

	/**
	 * get a specific row from a table
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param rowNum
	 *            : the row number of the table to obtain - note, row numbering
	 *            starts at 1, NOT 0
	 * @return List: a list of the table cells in the row as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableRow(Locator type, String locator, int elementMatch, int rowNum) throws IOException {
		List<WebElement> rows = getTableRows(type, locator, elementMatch);
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
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param colNum
	 *            : the column number of the table to obtain - note, column
	 *            numbering starts at 1, NOT 0
	 * @return List: a list of the table cells in the column as WebElements
	 * @throws IOException
	 */
	public List<WebElement> getTableColumn(Locator type, String locator, int elementMatch, int colNum)
			throws IOException {
		List<List<WebElement>> columns = getTableColumns(type, locator, elementMatch);
		if (columns.size() < colNum) {
			return new ArrayList<>();
		}
		return columns.get(colNum);
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
	 * @param rowNum
	 *            : the number of the row in the table - note, row numbering
	 *            starts at 1, NOT 0
	 * @param colNum
	 *            : the number of the column in the table - note, column
	 *            numbering starts at 1, NOT 0
	 * @return WebElement: the cell element object, and all associated values
	 *         with it
	 * @throws IOException
	 */
	public WebElement getTableCell(Locator type, String locator, int elementMatch, int rowNum, int colNum)
			throws IOException {
		List<WebElement> row = getTableRow(type, locator, elementMatch, rowNum);
		if (row.size() < colNum) {
			return null;
		}
		return row.get(colNum);
	}

	// //////////////////////////////////
	// extra base selenium functionality
	// //////////////////////////////////

	private boolean isPresentInput(Locator type, String locator, int elementMatch) throws IOException {
		// wait for element to be present
		if (!isElementPresent(type, locator, elementMatch, false)) {
			waitForElementPresent(type, locator, elementMatch, 5);
		}
		if (!isElementPresent(type, locator, elementMatch, false)) {
			return false;
		}
		return isElementInput(type, locator, elementMatch, false);
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
	 * @throws IOException
	 */
	public String getSelectedText(Locator type, String locator, int elementMatch) throws IOException {
		if (!isPresentInput(type, locator, elementMatch)) {
			return null;
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		Select dropdown = new Select(element);
		WebElement option = dropdown.getFirstSelectedOption();
		return option.getText();
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
	 * @throws IOException
	 */
	public String[] getSelectedTexts(Locator type, String locator, int elementMatch) throws IOException {
		if (!isPresentInput(type, locator, elementMatch)) {
			return new String[0];
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		Select dropdown = new Select(element);
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
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return String: the options from the select element
	 * @throws IOException
	 */
	public String getSelectedValue(Locator type, String locator, int elementMatch) throws IOException {
		if (!isPresentInput(type, locator, elementMatch)) {
			return null;
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		Select dropdown = new Select(element);
		WebElement option = dropdown.getFirstSelectedOption();
		return option.getAttribute(VALUE);
	}

	/**
	 * get the option values from the select drop down
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return String[]: the options from the select element
	 * @throws IOException
	 */
	public String[] getSelectedValues(Locator type, String locator, int elementMatch) throws IOException {
		if (!isPresentInput(type, locator, elementMatch)) {
			return new String[0];
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		Select dropdown = new Select(element);
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
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return String - the text of the element
	 * @throws IOException
	 */
	public String getText(Locator type, String locator, int elementMatch) throws IOException {
		if (!isElementPresent(type, locator, elementMatch, false)) {
			return null;
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		return element.getText();
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
	 * @throws IOException
	 */
	public String getValue(Locator type, String locator, int elementMatch) throws IOException {
		if (!isElementInput(type, locator, elementMatch, false)) {
			return null;
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		return element.getAttribute(VALUE);
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
	 * @throws IOException
	 */
	public String getCss(Locator type, String locator, int elementMatch, String attribute) throws IOException {
		if (!isElementPresent(type, locator, elementMatch, false)) {
			return null;
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		return element.getCssValue(attribute);
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
	 * @throws IOException
	 */
	public String getAttribute(Locator type, String locator, int elementMatch, String attribute) throws IOException {
		if (!isElementPresent(type, locator, elementMatch, false)) {
			return null;
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		return element.getAttribute(attribute);
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
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getAllAttributes(Locator type, String locator, int elementMatch) throws IOException {
		if (!isElementPresent(type, locator, elementMatch, false)) {
			return new HashMap<>();
		}
		try {
			WebElement element = getWebElement(type, locator, elementMatch);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return (Map<String, String>) js.executeScript(
					"var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",
					element);
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	/**
	 * a way to execute custom javascript functions
	 *
	 * @param javascriptFunction
	 * @return Object: any resultant output from the javascript command
	 * @throws IOException
	 */
	public Object getEval(Locator type, String locator, int elementMatch, String javascriptFunction)
			throws IOException {
		if (!isElementPresent(type, locator, elementMatch, false)) {
			return null;
		}
		try {
			WebElement element = getWebElement(type, locator, elementMatch);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript(javascriptFunction, element);
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	/**
	 * checks to see if the element is present. If it isn't, it'll wait for 5
	 * seconds for the element to be present
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login'] <<<<<<<
	 *            HEAD
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element present? =======
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer - the number of errors encountered while executing these
	 *         steps >>>>>>> 0da6403478880421bb126c6ffcccf5eccdb2e6ae
	 * @throws IOException
	 */
	private boolean isPresent(Locator type, String locator, int elementMatch, String action, String expected,
			String extra) throws IOException {
		// wait for element to be present
		if (!isElementPresent(type, locator, elementMatch, false)) {
			waitForElementPresent(type, locator, elementMatch, 5);
		}
		if (!isElementPresent(type, locator, elementMatch, false)) {
			file.recordAction(action, expected, extra + type + " " + locator + NOTPRESENT, Result.FAILURE);
			file.addError();
			// indicates element not present
			return false;
		}
		return true;
	}

	/**
	 * checks to see if the element is displayed. If it isn't, it'll wait for 5
	 * seconds for the element to be displayed
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element displayed?
	 * @throws IOException
	 */
	private boolean isDisplayed(Locator type, String locator, int elementMatch, String action, String expected,
			String extra) throws IOException {
		// wait for element to be displayed
		if (!isElementDisplayed(type, locator, elementMatch, false)) {
			waitForElementDisplayed(type, locator, elementMatch, 5);
		}
		if (!isElementDisplayed(type, locator, elementMatch, false)) {
			file.recordAction(action, expected, extra + type + " " + locator + NOTDISPLAYED, Result.FAILURE);
			file.addError();
			// indicates element not displayed
			return false;
		}
		return true;
	}

	/**
	 * checks to see if the element is enabled. If it isn't, it'll wait for 5
	 * seconds for the element to be enabled
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element enabled?
	 * @throws IOException
	 */
	private boolean isEnabled(Locator type, String locator, int elementMatch, String action, String expected,
			String extra) throws IOException {
		// wait for element to be displayed
		if (!isElementEnabled(type, locator, elementMatch, false)) {
			waitForElementEnabled(type, locator, elementMatch, 5);
		}
		if (!isElementEnabled(type, locator, elementMatch, false)) {
			file.recordAction(action, expected, extra + type + " " + locator + NOTENABLED, Result.FAILURE);
			file.addError();
			// indicates element not enabled
			return false;
		}
		return true;
	}

	/**
	 * checks to see if the element is an input.
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element enabled?
	 * @throws IOException
	 */
	private boolean isInput(Locator type, String locator, int elementMatch, String action, String expected,
			String extra) throws IOException {
		// wait for element to be displayed
		if (!isElementInput(type, locator, elementMatch, false)) {
			file.recordAction(action, expected, extra + type + " " + locator + NOTINPUT, Result.FAILURE);
			file.addError();
			// indicates element not an input
			return false;
		}
		return true;
	}

	/**
	 * does a check to see if something is present, displayed, and enabled. This
	 * returns true if all three are true, otherwise, it returns false
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element present, displayed, and enabled?
	 * @throws IOException
	 */
	private boolean isPresentDisplayedEnabled(Locator type, String locator, int elementMatch, String action,
			String expected, String extra) throws IOException {
		// wait for element to be present
		if (!isPresent(type, locator, elementMatch, action, expected, extra)) {
			return false;
		}
		// wait for element to be displayed
		if (!isDisplayed(type, locator, elementMatch, action, expected, extra)) {
			return false;
		}
		// wait for element to be enabled
		return isEnabled(type, locator, elementMatch, action, expected, extra);
	}

	/**
	 * does a check to see if something is present, enabled, and an input. This
	 * returns true if all three are true, otherwise, it returns false
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element present, enabled, and an input?
	 * @throws IOException
	 */
	private boolean isPresentEnabledInput(Locator type, String locator, int elementMatch, String action,
			String expected, String extra) throws IOException {
		// wait for element to be present
		if (!isPresent(type, locator, elementMatch, action, expected, extra)) {
			return false;
		}
		// wait for element to be enabled
		if (!isEnabled(type, locator, elementMatch, action, expected, extra)) {
			return false;
		}
		return isInput(type, locator, elementMatch, action, expected, extra);
	}

	/**
	 * does a check to see if something is present, displayed, enabled, and an
	 * input. This returns true if all four are true, otherwise, it returns
	 * false
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param action
	 *            - what action is occurring
	 * @param expected
	 *            - what is the expected result
	 * @param extra
	 *            - what actually is occurring
	 * @return Boolean: is the element present, displayed, enabled, and an
	 *         input?
	 * @throws IOException
	 */
	private boolean isPresentDisplayedEnabledInput(Locator type, String locator, int elementMatch, String action,
			String expected, String extra) throws IOException {
		// wait for element to be present
		if (!isPresent(type, locator, elementMatch, action, expected, extra)) {
			return false;
		}
		// wait for element to be displayed
		if (!isDisplayed(type, locator, elementMatch, action, expected, extra)) {
			return false;
		}
		// wait for element to be enabled
		if (!isEnabled(type, locator, elementMatch, action, expected, extra)) {
			return false;
		}
		return isInput(type, locator, elementMatch, action, expected, extra);
	}

	// ///////////////////////////////////
	// selenium actions functionality
	// ///////////////////////////////////

	/**
	 * the generic selenium click functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int click(Locator type, String locator, int elementMatch) throws IOException {
		String cantClick = "Unable to click ";
		String action = "Clicking " + type + " " + locator;
		String expected = type + " " + locator + " is present, displayed, and enabled to be clicked";
		if (!isPresentDisplayedEnabled(type, locator, elementMatch, action, expected, cantClick)) {
			return 1;
		}
		try {
			WebElement element = getWebElement(type, locator, elementMatch);
			Actions selAction = new Actions(driver);
			selAction.click(element).perform();
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantClick + type + " " + locator + ". " + e.getMessage(),
					Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, "Clicked " + type + " " + locator, Result.SUCCESS);
		return 0;
	}

	/**
	 * the generic selenium submit functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int submit(Locator type, String locator, int elementMatch) throws IOException {
		String cantSubmit = "Unable to submit ";
		String action = "Submitting " + type + " " + locator;
		String expected = type + " " + locator + " is present, displayed, and enabled to be submitted	";
		if (!isPresentDisplayedEnabled(type, locator, elementMatch, action, expected, cantSubmit)) {
			return 1;
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		try {
			element.submit();
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantSubmit + type + " " + locator + ". " + e.getMessage(),
					Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, "Submitted " + type + " " + locator, Result.SUCCESS);
		return 0;
	}

	/**
	 * a method to simulate the mouse hovering over an element
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int hover(Locator type, String locator, int elementMatch) throws IOException {
		String cantHover = "Unable to hover over ";
		String action = "Hovering over " + type + " " + locator;
		String expected = type + " " + locator + " is present, and displayed to be hovered over";
		// wait for element to be present
		if (!isPresent(type, locator, elementMatch, action, expected, cantHover)) {
			return 1;
		}
		// wait for element to be displayed
		if (!isDisplayed(type, locator, elementMatch, action, expected, cantHover)) {
			return 1;
		}
		try {
			Actions selAction = new Actions(driver);
			WebElement element = getWebElement(type, locator, elementMatch);
			selAction.moveToElement(element).perform();
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantHover + type + " " + locator + ". " + e.getMessage(),
					Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, "Hovered over " + type + " " + locator, Result.SUCCESS);
		return 0;
	}

	/**
	 * a custom selenium functionality to apply a blur to an element
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int blur(Locator type, String locator, int elementMatch) throws IOException {
		String cantFocus = "Unable to focus on ";
		String action = "Focusing, then unfocusing (blurring) on " + type + " " + locator;
		String expected = type + " " + locator + " is present, displayed, and enabled to be blurred";
		if (!isPresentDisplayedEnabledInput(type, locator, elementMatch, action, expected, cantFocus)) {
			return 1;
		}
		try {
			WebElement element = getWebElement(type, locator, elementMatch);
			element.sendKeys("\t");
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantFocus + type + " " + locator + ". " + e.getMessage(),
					Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, "Focused, then unfocused (blurred) on " + type + " " + locator,
				Result.SUCCESS);
		return 0;
	}

	/**
	 * type functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param text
	 *            : the text to be typed in
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int type(Locator type, String locator, int elementMatch, String text) throws IOException {
		String action = "Typing text '" + text + IN + type + " " + locator;
		String expected = type + " " + locator + " is present, displayed, and enabled to have text " + text
				+ " typed in";
		if (!isPresentEnabledInput(type, locator, elementMatch, action, expected, CANTTYPE)) {
			return 1;
		}
		try {
			WebElement element = getWebElement(type, locator, elementMatch);
			element.sendKeys(text);
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, CANTTYPE + type + " " + locator + ". " + e.getMessage(),
					Result.FAILURE);
			file.addError();
			return 1;
		}
		if (!isElementDisplayed(type, locator, elementMatch, false)) {
			file.recordAction(action, expected, "Typed text '" + text + IN + type + " " + locator
					+ ". <b>THIS ELEMENT WAS NOT DISPLAYED. THIS MIGHT BE AN ISSUE.</b>", Result.WARNING);
		} else {
			file.recordAction(action, expected, "Typed text '" + text + IN + type + " " + locator, Result.SUCCESS);
		}
		return 0;
	}

	/**
	 * the generic selenium type functionality implemented for specific keys
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param key
	 *            : the key to be pressed
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int type(Locator type, String locator, int elementMatch, Keys key) throws IOException {
		String action = "Typing key '" + key + IN + type + " " + locator;
		String expected = type + " " + locator + " is present, displayed, and enabled to have text " + key + " entered";
		if (!isPresentEnabledInput(type, locator, elementMatch, action, expected, CANTTYPE)) {
			return 1;
		}
		try {
			WebElement element = getWebElement(type, locator, elementMatch);
			element.sendKeys(key);
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, CANTTYPE + type + " " + locator + ". " + e.getMessage(),
					Result.FAILURE);
			file.addError();
			return 1;
		}
		if (!isElementDisplayed(type, locator, elementMatch, false)) {
			file.recordAction(action, expected, "Typed text '" + key + IN + type + " " + locator
					+ ". <b>THIS ELEMENT WAS NOT DISPLAYED. THIS MIGHT BE AN ISSUE.</b>", Result.WARNING);
		} else {
			file.recordAction(action, expected, "Typed text '" + key + IN + type + " " + locator, Result.SUCCESS);
		}
		return 0;
	}

	/**
	 * the generic selenium clear functionality implemented
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int clear(Locator type, String locator, int elementMatch) throws IOException {
		String cantClear = "Unable to clear ";
		String action = "Clearing text in " + type + " " + locator;
		String expected = type + " " + locator + " is present, displayed, and enabled to have text cleared";
		if (!isPresentDisplayedEnabledInput(type, locator, elementMatch, action, expected, cantClear)) {
			return 1;
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		try {
			element.clear();
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantClear + type + " " + locator + ". " + e.getMessage(),
					Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, "Cleared text in " + type + " " + locator, Result.SUCCESS);
		return 0;
	}

	/**
	 * the generic select selenium functionality
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param value
	 *            - the select option to be selected
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int select(Locator type, String locator, int elementMatch, String value) throws IOException {
		String cantSelect = "Unable to select ";
		String action = "Selecting " + value + " in " + type + " " + locator;
		String expected = type + " " + locator + " is present, displayed, and enabled to have the value " + value
				+ " selected";
		if (!isPresentDisplayedEnabledInput(type, locator, elementMatch, action, expected, cantSelect)) {
			return 1;
		}
		// ensure the option exists
		if (!Arrays.asList(getSelectOptions(type, locator, elementMatch)).contains(value)) {
			file.recordAction(action, expected, cantSelect + value + " in " + type + " " + locator
					+ " as that option isn't present. Available options are:<i><br/>" + "&nbsp;&nbsp;&nbsp;"
					+ String.join("<br/>&nbsp;&nbsp;&nbsp;", getSelectOptions(type, locator, elementMatch)) + "</i>",
					Result.FAILURE);
			file.addError();
			return 1;
		}
		// do the select
		try {
			WebElement element = getWebElement(type, locator, elementMatch);
			Select dropdown = new Select(element);
			dropdown.selectByValue(value);
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantSelect + type + " " + locator + ". " + e.getMessage(),
					Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, "Selected " + value + " in " + type + " " + locator, Result.SUCCESS);
		return 0;
	}

	/**
	 * A private method to throw an error if moving to an element was
	 * unsuccessful
	 * 
	 * @param e
	 *            - the exception that was thrown
	 * @param action
	 *            - what is the action occurring
	 * @param expected
	 *            - what is the expected outcome of said action
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	private int cantMove(Exception e, String action, String expected, Locator type, String locator) {
		log.error(e);
		file.recordAction(action, expected, CANTMOVE + type + " " + locator + ". " + e.getMessage(), Result.FAILURE);
		file.addError();
		return 1;
	}

	/**
	 * A private method to determine if the element moved towards is now
	 * currently visible on the screen
	 * 
	 * @param action
	 *            - what is the action occurring
	 * @param expected
	 *            - what is the expected outcome of said action
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	private int isMoved(String action, String expected, Locator type, String locator, int elementMatch)
			throws IOException {
		if (!isElementDisplayed(type, locator, elementMatch, false)) {
			file.recordAction(action, expected, type + " " + locator + " is not present on visible page",
					Result.FAILURE);
			file.addError();
			return 1; // indicates element not visible
		}
		file.recordAction(action, expected, type + " " + locator + " is present on visible page", Result.SUCCESS);
		return 0; // indicates element successfully moved to
	}

	/**
	 * An extension of the basic Selenium action of 'moveToElement' This will
	 * scroll or move the page to ensure the element is visible
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int move(Locator type, String locator, int elementMatch) throws IOException {
		String action = "Moving screen to " + type + " " + locator;
		String expected = type + " " + locator + " is now present on the visible page";
		// wait for element to be present
		if (!isPresent(type, locator, elementMatch, action, expected, CANTMOVE)) {
			return 1;
		}
		try {
			WebElement element = getWebElement(type, locator, elementMatch);
			Actions builder = new Actions(driver);
			builder.moveToElement(element);
		} catch (Exception e) {
			return cantMove(e, action, expected, type, locator);
		}
		return isMoved(action, expected, type, locator, elementMatch);
	}

	/**
	 * An extension of the basic Selenium action of 'moveToElement' This will
	 * scroll or move the page to ensure the element is visible
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param position
	 *            - how many pixels above the element to scroll to
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 */
	public int move(Locator type, String locator, int elementMatch, long position) throws IOException {
		String action = "Moving screen to " + position + " pixels above " + type + " " + locator;
		String expected = type + " " + locator + " is now present on the visible page";
		// wait for element to be present
		if (!isPresent(type, locator, elementMatch, action, expected, CANTMOVE)) {
			return 1;
		}

		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			WebElement element = getWebElement(type, locator, elementMatch);
			long elementPosition = element.getLocation().getY();
			long newPosition = elementPosition - position;
			jse.executeScript("window.scrollBy(0, " + newPosition + ")");
		} catch (Exception e) {
			return cantMove(e, action, expected, type, locator);
		}
		return isMoved(action, expected, type, locator, elementMatch);
	}

	/**
	 * a function to switch to a frame using the element
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 */
	public int selectFrame(Locator type, String locator, int elementMatch) throws IOException {
		String cantSelect = "Unable to focus on frame ";
		String action = "Focusing on frame " + type + " " + locator;
		String expected = "Frame " + type + " " + locator + " is present, displayed, and focused";
		// wait for element to be present
		if (!isPresent(type, locator, elementMatch, action, expected, cantSelect)) {
			return 1;
		}
		// wait for element to be displayed
		if (!isDisplayed(type, locator, elementMatch, action, expected, cantSelect)) {
			return 1;
		}
		WebElement element = getWebElement(type, locator, elementMatch);
		try {
			driver.switchTo().frame(element);
		} catch (Exception e) {
			log.error(e);
			file.recordAction(action, expected, cantSelect + type + " " + locator + ". " + e.getMessage(),
					Result.FAILURE);
			file.addError();
			return 1;
		}
		file.recordAction(action, expected, "Focused on frame " + type + " " + locator, Result.SUCCESS);
		return 0;
	}

	//////////////////////////////////////////////////////
	// obtaining element values
	//////////////////////////////////////////////////////

	/**
	 * a method to determine selenium's By object using selenium webdriver
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return By: the selenium object
	 * @throws InvalidLocatorTypeException
	 */
	private By defineByElement(Locator type, String locator) throws InvalidLocatorTypeException {
		// consider adding strengthening
		By byElement;
		switch (type) { // determine which locator type we are interested in
		case XPATH:
			byElement = By.xpath(locator);
			break;
		case ID:
			byElement = By.id(locator);
			break;
		case NAME:
			byElement = By.name(locator);
			break;
		case CLASSNAME:
			byElement = By.className(locator);
			break;
		case CSS:
			byElement = By.cssSelector(locator);
			break;
		case LINKTEXT:
			byElement = By.linkText(locator);
			break;
		case PARTIALLINKTEXT:
			byElement = By.partialLinkText(locator);
			break;
		case TAGNAME:
			byElement = By.tagName(locator);
			break;
		default:
			throw new InvalidLocatorTypeException(type + " is not a valid locator type");
		}
		return byElement;
	}

	/**
	 * a method to grab the identified matching web element using selenium
	 * webdriver
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param elementMatch
	 *            - the number of the matching element, starting the count at 0;
	 * @return WebElement: the element object, and all associated values with it
	 * @throws InvalidLocatorTypeException
	 */
	private WebElement getWebElement(Locator type, String locator, int elementMatch)
			throws InvalidLocatorTypeException {
		List<WebElement> elements = getWebElements(type, locator);
		if (elements.size() > elementMatch) {
			return elements.get(elementMatch);
		}
		return getWebElement(type, locator);
	}

	/**
	 * a method to grab the first matching web element using selenium webdriver
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return WebElement: the element object, and all associated values with it
	 * @throws InvalidLocatorTypeException
	 */
	private WebElement getWebElement(Locator type, String locator) throws InvalidLocatorTypeException {
		By byElement = defineByElement(type, locator);
		return driver.findElement(byElement);
	}

	/**
	 * a method to grab all matching web elements using selenium webdriver
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return List<WebElement>: a list of element objects, and all associated
	 *         values with them
	 * @throws InvalidLocatorTypeException
	 */
	private List<WebElement> getWebElements(Locator type, String locator) throws InvalidLocatorTypeException {
		By byElement = defineByElement(type, locator);
		return driver.findElements(byElement);
	}

	/**
	 * a method to determine how many elements match the selector
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: how many element match the selector
	 * @throws InvalidLocatorTypeException
	 */
	public int getElementMatchCount(Locator type, String locator) throws InvalidLocatorTypeException {
		return getWebElements(type, locator).size();
	}
}