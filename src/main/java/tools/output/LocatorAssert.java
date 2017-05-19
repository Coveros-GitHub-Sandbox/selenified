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

package tools.output;

import tools.General;
import tools.output.Assert.Success;
import tools.output.Selenium.Locators;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebElement;

/**
 * Test Output A custom generated output file outputFile.recording all actions
 * taken
 *
 * @author Max Saperstone
 * @version 2.0.0
 * @lastupdate 5/15/2017
 */
public class LocatorAssert {

	private Action action;
	private OutputFile outputFile;

	// constants
	private static final String EXPECTED = "Expected to find element with ";
	private static final String ELEMENT = "The element with ";
	private static final String CLASS = "class";

	private static final String CHECKED = "</i> is checked on the page";
	private static final String PRESENT = "</i> is not present on the page";

	private static final String NOTCHECKED = "</i> is not checked on the page";
	private static final String NOTINPUT = "</i> is not an input on the page";
	private static final String NOTEDITABLE = "</i> is not editable on the page";

	private static final String VALUE = "</i> has the value of <b>";
	private static final String HASVALUE = "</i> contains the value of <b>";
	private static final String ONLYVALUE = ", only the values <b>";
	private static final String CLASSVALUE = "</i> has a class value of <b>";

	public LocatorAssert(Action action, OutputFile outputFile) {
		this.action = action;
		this.outputFile = outputFile;
	}

	// /////////////////////////////////////////////////////////////////////////
	// a bunch of methods to negatively check for objects using selenium calls
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * checks to see if an element is visible on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayed(Locators type, String locator) throws IOException {
		// wait for the element
		if (!action.isElementDisplayed(type, locator) && action.waitForElementDisplayed(type, locator) == 1) {
			return 1;
		}
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> visible on the page");
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is present and visible on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element is not visible on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementNotDisplayed(Locators type, String locator) throws IOException {
		// wait for the element
		if (action.isElementDisplayed(type, locator) && action.waitForElementNotDisplayed(type, locator) == 1) {
			return 1;
		}
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> present, but not visible on the page");
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is present and not visible on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an object is checked on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementChecked(Locators type, String locator) throws IOException {
		// wait for the element
		if (!action.isElementPresent(type, locator) && action.waitForElementPresent(type, locator) == 1) {
			return 1;
		}
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + CHECKED);
		// check for our object to the visible
		if (!action.isElementChecked(type, locator)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTCHECKED, Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + CHECKED, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an object is not checked on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementNotChecked(Locators type, String locator) throws IOException {
		// wait for the element
		if (!action.isElementPresent(type, locator) && action.waitForElementPresent(type, locator) == 1) {
			return 1;
		}
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + NOTCHECKED);
		// check for our object to the visible
		if (action.isElementChecked(type, locator)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> checked on the page", Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTCHECKED, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an object is visible and checked on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndChecked(Locators type, String locator) throws IOException {
		// wait for the element
		if (!action.isElementDisplayed(type, locator) && action.waitForElementDisplayed(type, locator) == 1) {
			return 1;
		}
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + CHECKED);
		// check for our object to the visible
		if (!action.isElementChecked(type, locator)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTCHECKED, Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is checked and visible on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an object is visible and not checked on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndUnchecked(Locators type, String locator) throws IOException {
		// wait for the element
		if (!action.isElementDisplayed(type, locator) && action.waitForElementDisplayed(type, locator) == 1) {
			return 1;
		}
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + NOTCHECKED);
		// check for our object to the visible
		if (action.isElementChecked(type, locator)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + CHECKED, Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is not checked and visible on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element is editable on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementEditable(Locators type, String locator) throws IOException {
		// wait for the element
		if (!action.isElementPresent(type, locator) && action.waitForElementPresent(type, locator) == 1) {
			return 1;
		}
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> editable on the page");
		// check for our object to the editable
		if (!action.isElementInput(type, locator)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is present but not an input on the page",
					Success.FAIL);
			return 1;
		}
		if (!action.isElementEnabled(type, locator)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is present but not editable on the page",
					Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is present and editable on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element is not editable on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementNotEditable(Locators type, String locator) throws IOException {
		// wait for the element
		if (!action.isElementPresent(type, locator) && action.waitForElementPresent(type, locator) == 1) {
			return 1;
		}
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> not editable on the page");
		// check for our object to the editable
		boolean isElementEnabled = false;
		boolean isInput = action.isElementInput(type, locator);
		if (isInput) {
			isElementEnabled = action.isElementEnabled(type, locator);
		}
		if (isElementEnabled) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is present but editable on the page",
					Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is present and not editable on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element is visible and editable on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndEditable(Locators type, String locator) throws IOException {
		// wait for the element
		if (!action.isElementDisplayed(type, locator) && action.waitForElementDisplayed(type, locator) == 1) {
			return 1;
		}
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> visible and editable on the page");
		// check for our object to the editable
		if (!action.isElementInput(type, locator)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is visible but not an input on the page",
					Success.FAIL);
			return 1;
		}
		if (!action.isElementEnabled(type, locator)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is visible but not editable on the page",
					Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is visible and editable on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element is visible and not editable on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndNotEditable(Locators type, String locator) throws IOException {
		// wait for the element
		if (!action.isElementDisplayed(type, locator) && action.waitForElementDisplayed(type, locator) == 1) {
			return 1;
		}
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> visible and not editable on the page");
		// check for our object to the editable
		boolean isElementEnabled = false;
		boolean isInput = action.isElementInput(type, locator);
		if (isInput) {
			isElementEnabled = action.isElementEnabled(type, locator);
		}
		if (isElementEnabled) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is visible but editable on the page",
					Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is visible and not editable on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element has an attribute associated with it
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param attribute
	 *            - the attribute to check for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementHasAttribute(Locators type, String locator, String attribute) throws IOException {
		// wait for the element
		if (!action.isElementPresent(type, locator) && action.waitForElementPresent(type, locator) == 1) {
			return 1;
		}
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> with attribute <b>" + attribute + "</b>");
		Map<String, String> attributes = action.getAllAttributes(type, locator);
		Set<String> keys = attributes.keySet();
		String[] array = keys.toArray(new String[keys.size()]);
		// outputFile.record our action
		if (General.doesArrayContain(array, attribute)) {
			outputFile.recordActual(
					ELEMENT + type + " <i>" + locator + "</i> contains the attribute of <b>" + attribute + "</b>",
					Success.PASS);
			return 0;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not contain the attribute of <b>"
				+ attribute + "</b>" + ONLYVALUE + Arrays.toString(array) + "</b>", Success.FAIL);
		return 1;
	}

	/**
	 * checks to see if an element has an attribute associated with it
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param attribute
	 *            - the attribute to check for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDoesntHaveAttribute(Locators type, String locator, String attribute) throws IOException {
		// wait for the element
		if (!action.isElementPresent(type, locator) && action.waitForElementPresent(type, locator) == 1) {
			return 1;
		}
		outputFile
				.recordExpected(EXPECTED + type + " <i>" + locator + "</i> without attribute <b>" + attribute + "</b>");
		Map<String, String> attributes = action.getAllAttributes(type, locator);
		Set<String> keys = attributes.keySet();
		String[] array = keys.toArray(new String[keys.size()]);
		// outputFile.record our action
		if (General.doesArrayContain(array, attribute)) {
			outputFile.recordActual(
					ELEMENT + type + " <i>" + locator + "</i> contains the attribute of <b>" + attribute + "</b>",
					Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not contain the attribute of <b>"
				+ attribute + "</b>" + ONLYVALUE + Arrays.toString(array) + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element has a particular class
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedClass
	 *            - the full expected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementHasClass(Locators type, String locator, String expectedClass) throws IOException {
		// wait for the element
		if (!action.isElementPresent(type, locator) && action.waitForElementPresent(type, locator) == 1) {
			return 1;
		}
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> with class <b>" + expectedClass + "</b>");
		String actualClass = action.getAttribute(type, locator, CLASS);
		// outputFile.record our action
		if (actualClass.equals(expectedClass)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + CLASSVALUE + expectedClass + "</b>",
					Success.PASS);
			return 0;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + CLASSVALUE + actualClass + "</b>", Success.FAIL);
		return 1;
	}

	/**
	 * checks to see if an element contains a particular class
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedClass
	 *            - the expected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementContainsClass(Locators type, String locator, String expectedClass) throws IOException {
		// wait for the element
		if (!action.isElementPresent(type, locator) && action.waitForElementPresent(type, locator) == 1) {
			return 1;
		}
		outputFile.recordExpected(
				EXPECTED + type + " <i>" + locator + "</i> containing class <b>" + expectedClass + "</b>");
		String actualClass = action.getAttribute(type, locator, CLASS);
		// outputFile.record our action
		if (actualClass.contains(expectedClass)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + CLASSVALUE + actualClass
					+ "</b>, which contains <b>" + expectedClass + "</b>", Success.PASS);
			return 0;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + CLASSVALUE + actualClass + "</b>", Success.FAIL);
		return 1;
	}

	/**
	 * checks to see if an element does not contain a particular class
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param unexpectedClass
	 *            - the unexpected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDoesntContainClass(Locators type, String locator, String unexpectedClass)
			throws IOException {
		// wait for the element
		if (!action.isElementPresent(type, locator) && action.waitForElementPresent(type, locator) == 1) {
			return 1;
		}
		outputFile.recordExpected(
				EXPECTED + type + " <i>" + locator + "</i> without class <b>" + unexpectedClass + "</b>");
		String actualClass = action.getAttribute(type, locator, CLASS);
		// outputFile.record our action
		if (actualClass.contains(unexpectedClass)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + CLASSVALUE + actualClass
					+ "</b>, which contains <b>" + unexpectedClass + "</b>", Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not contain a class value of <b>"
				+ unexpectedClass + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an option is available to be selected on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param option
	 *            the option expected in the list
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkIfOptionInSelect(Locators type, String locator, String option) throws IOException {
		// wait for the element
		if (!action.isElementEnabled(type, locator) && action.waitForElementEnabled(type, locator) == 1) {
			return 1;
		}
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> with the option <b>" + option
				+ "</b> available to be" + " selected on the page");
		// check for our object to the editable
		String[] allOptions = action.getSelectOptions(type, locator);
		if (!Arrays.asList(allOptions).contains(option)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator
					+ "</i> is editable and present but does not contain the option " + "<b>" + option + "</b>",
					Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator
				+ "</i> is editable and present and contains the option " + "<b>" + option + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an option is not available to be selected on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param option
	 *            the option not expected in the list
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkIfOptionNotInSelect(Locators type, String locator, String option) throws IOException {
		// wait for the element
		if (!action.isElementEnabled(type, locator) && action.waitForElementEnabled(type, locator) == 1) {
			return 1;
		}
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> without the option <b>" + option
				+ "</b> available to be" + " selected on the page");
		// check for our object to the editable
		String[] allOptions = action.getSelectOptions(type, locator);
		if (Arrays.asList(allOptions).contains(option)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator
					+ "</i> is editable and present and contains the option " + "<b>" + option + "</b>", Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator
				+ "</i> is editable and present but does not contain the option " + "<b>" + option + "</b>",
				Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element value with the actual value from an element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedValue
	 *            the expected value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareTextValue(Locators type, String locator, String expectedValue) throws IOException {
		// outputFile.record our action
		outputFile.recordExpected(
				EXPECTED + type + " <i>" + locator + "</i> having a value of <b>" + expectedValue + "</b>");
		// check for our object to the present on the page
		String elementValue = "";
		boolean isPresent = action.isElementPresent(type, locator);
		if (isPresent) {
			elementValue = action.getText(type, locator);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (!elementValue.equals(expectedValue)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element value with the actual value from an element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedValue
	 *            the expected value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareTextValueContains(Locators type, String locator, String expectedValue) throws IOException {
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + HASVALUE + expectedValue + "</b>");
		// check for our object to the present on the page
		String elementValue = "";
		boolean isPresent = action.isElementPresent(type, locator);
		if (isPresent) {
			elementValue = action.getText(type, locator);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (!elementValue.contains(expectedValue)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element input value with the actual value from an
	 * element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareInputValue(Locators type, String locator, String expectedValue) throws IOException {
		// outputFile.record our action
		outputFile.recordExpected(
				EXPECTED + type + " <i>" + locator + "</i> having a value of <b>" + expectedValue + "</b>");
		// check for our object to the present on the page
		String elementValue = "";
		boolean isPresent = action.isElementPresent(type, locator);
		if (isPresent) {
			elementValue = action.getValue(type, locator);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (!elementValue.equals(expectedValue)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element css attribute value with the actual css
	 * attribute value from an element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param attribute
	 *            - the css attribute to be checked
	 * @param expectedValue
	 *            the expected css value of the passed attribute of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareCssValue(Locators type, String locator, String attribute, String expectedValue)
			throws IOException {
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> having a css attribute of <i>" + attribute
				+ "</i> with a value of <b>" + expectedValue + "</b>");
		// check for our object to the present on the page
		String elementCssValue = "";
		boolean isPresent = action.isElementPresent(type, locator);
		if (isPresent) {
			elementCssValue = action.getCss(type, locator, attribute);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (!elementCssValue.equals(expectedValue)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> has a css attribute of <i>" + attribute
					+ "</i> with the value of <b>" + elementCssValue + "</b>", Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> has a css attribute of <i>" + attribute
				+ "</i> with the value of <b>" + elementCssValue + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element select value exists
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param selectValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkSelectValuePresent(Locators type, String locator, String selectValue) throws IOException {
		// outputFile.record our action
		outputFile.recordExpected(
				EXPECTED + type + " <i>" + locator + "</i> having a select value of <b>" + selectValue + "</b>");
		// check for our object to the present on the page
		String[] elementValues = null;
		boolean isPresent = action.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = action.isElementInput(type, locator);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (isInput) {
			isElementEnabled = action.isElementEnabled(type, locator);
		}
		if (!isInput) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTINPUT, Success.FAIL);
			return 1;
		}
		if (isElementEnabled) {
			elementValues = action.getSelectedValues(type, locator);
		}
		if (!isElementEnabled) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTEDITABLE, Success.FAIL);
			return 1;
		}
		if (General.doesArrayContain(elementValues, selectValue)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + HASVALUE + selectValue + "</b>", Success.PASS);
			return 0;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not contain the value of <b>"
				+ selectValue + "</b>" + ONLYVALUE + Arrays.toString(elementValues) + "</b>", Success.FAIL);
		return 1;
	}

	/**
	 * checks to see if an element select value does not exist
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param selectValue
	 *            the unexpected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkSelectValueNotPresent(Locators type, String locator, String selectValue) throws IOException {
		// outputFile.record our action
		outputFile.recordExpected(
				EXPECTED + type + " <i>" + locator + "</i> without a select value of <b>" + selectValue + "</b>");
		// check for our object to the present on the page
		String[] elementValues = null;
		boolean isPresent = action.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = action.isElementInput(type, locator);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (isInput) {
			isElementEnabled = action.isElementEnabled(type, locator);
		}
		if (!isInput) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTINPUT, Success.FAIL);
			return 1;
		}
		if (isElementEnabled) {
			elementValues = action.getSelectedValues(type, locator);
		}
		if (!isElementEnabled) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTEDITABLE, Success.FAIL);
			return 1;
		}
		if (General.doesArrayContain(elementValues, selectValue)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + HASVALUE + selectValue + "</b>", Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not contain the value of <b>"
				+ selectValue + "</b>, only the values <b>" + Arrays.toString(elementValues) + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element select value with the actual value from an
	 * element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectedValue(Locators type, String locator, String expectedValue) throws IOException {
		// outputFile.record our action
		outputFile.recordExpected(
				EXPECTED + type + " <i>" + locator + "</i> having a selected value of <b>" + expectedValue + "</b>");
		// check for our object to the present on the page
		String elementValue = "";
		boolean isPresent = action.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = action.isElementInput(type, locator);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (isInput) {
			isElementEnabled = action.isElementEnabled(type, locator);
		}
		if (!isInput) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTINPUT, Success.FAIL);
			return 1;
		}
		if (isElementEnabled) {
			elementValue = action.getSelectedValue(type, locator);
		}
		if (!isElementEnabled) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTEDITABLE, Success.FAIL);
			return 1;
		}
		if (!elementValue.equals(expectedValue)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element select test with the actual value from an
	 * element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedText
	 *            the expected input text of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectedText(Locators type, String locator, String expectedText) throws IOException {
		// outputFile.record our action
		outputFile.recordExpected(
				EXPECTED + type + " <i>" + locator + "</i> having a selected text of <b>" + expectedText + "</b>");
		// check for our object to the present on the page
		String elementText = "";
		boolean isPresent = action.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = action.isElementInput(type, locator);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (isInput) {
			isElementEnabled = action.isElementEnabled(type, locator);
		}
		if (!isInput) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTINPUT, Success.FAIL);
			return 1;
		}
		if (isElementEnabled) {
			elementText = action.getSelectedText(type, locator);
		}
		if (!isElementEnabled) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTEDITABLE, Success.FAIL);
			return 1;
		}
		if (!elementText.equals(expectedText)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementText + "</b>", Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementText + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element select value with the actual value from an
	 * element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectedValueNotEqual(Locators type, String locator, String expectedValue) throws IOException {
		// outputFile.record our action
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> not having a selected value of <b>"
				+ expectedValue + "</b>");
		// check for our object to the present on the page
		String elementValue = "";
		boolean isPresent = action.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = action.isElementInput(type, locator);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (isInput) {
			isElementEnabled = action.isElementEnabled(type, locator);
		}
		if (!isInput) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTINPUT, Success.FAIL);
			return 1;
		}
		if (isElementEnabled) {
			elementValue = action.getSelectedValue(type, locator);
		}
		if (!isElementEnabled) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTEDITABLE, Success.FAIL);
			return 1;
		}
		if (elementValue.equals(expectedValue)) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.FAIL);
			return 1;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValue + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the expected attributes from a select value with the actual
	 * attributes from the element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedValues
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectValues(Locators type, String locator, String... expectedValues) throws IOException {
		// outputFile.record our action
		int errors = 0;
		outputFile.recordExpected(
				EXPECTED + type + " <i>" + locator + "</i> with select values of <b>" + expectedValues + "</b>");
		// check for our object to the present on the page
		String[] elementValues = null;
		boolean isPresent = action.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = action.isElementInput(type, locator);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (isInput) {
			isElementEnabled = action.isElementEnabled(type, locator);
		}
		if (!isInput) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTINPUT, Success.FAIL);
			return 1;
		}
		if (isElementEnabled) {
			elementValues = action.getSelectOptions(type, locator);
		}
		if (!isElementEnabled) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + NOTEDITABLE, Success.FAIL);
			return 1;
		}
		for (String entry : expectedValues) {
			if (!Arrays.asList(elementValues).contains(entry)) {
				outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not have the select value of <b>"
						+ entry + "</b>", Success.FAIL);

				errors++;
			}
		}
		for (String entry : elementValues) {
			if (!Arrays.asList(expectedValues).contains(entry)) {
				outputFile.recordActual(
						ELEMENT + type + " <i>" + locator + VALUE + entry + "</b> which was not expected",
						Success.FAIL);

				errors++;
			}
		}
		if (errors > 0) {
			return errors;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + VALUE + elementValues + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the number of expected attributes from a select value with the
	 * actual number of attributes from the element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param numOfOptions
	 *            the expected number of options in the select element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfSelectOptions(Locators type, String locator, int numOfOptions) throws IOException {
		// outputFile.record our action
		int errors = 0;
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> with number of select values equal to <b>"
				+ numOfOptions + "</b>");
		// check for our object to the present on the page
		String[] elementValues = null;
		boolean isPresent = action.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = action.isElementInput(type, locator);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (isInput) {
			isElementEnabled = action.isElementEnabled(type, locator);
		}
		if (!isInput) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is not an input on the page",
					Success.FAIL);
			return 1;
		}
		if (isElementEnabled) {
			elementValues = action.getSelectOptions(type, locator);
		}
		if (!isElementEnabled) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> is not editable on the page",
					Success.FAIL);
			return 1;
		}
		if (elementValues.length != numOfOptions) {
			outputFile.recordActual(
					ELEMENT + type + " <i>" + locator + "</i> has <b>" + numOfOptions + "</b>" + " select options",
					Success.FAIL);

			errors++;
		}
		if (errors > 0) {
			return errors;
		}
		outputFile.recordActual(
				ELEMENT + type + " <i>" + locator + "</i> has <b>" + numOfOptions + "</b>" + " select options",
				Success.PASS);
		return 0;
	}

	/**
	 * compares the number of expected rows with the actual number of rows of a
	 * table with from a table element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param numOfRows
	 *            the expected number of row elements of a table
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfTableRows(Locators type, String locator, int numOfRows) throws IOException {
		// outputFile.record our action
		int errors = 0;
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> with the number of table rows equal to <b>"
				+ numOfRows + "</b>");
		// check for our object to the present on the page
		List<WebElement> elementValues = null;
		boolean isPresent = action.isElementPresent(type, locator);
		if (isPresent) {
			elementValues = action.getTableRows(type, locator);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (elementValues.size() != numOfRows) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not have the number of rows <b>"
					+ numOfRows + "</b> Instead, " + elementValues.size() + " rows were found", Success.FAIL);

			errors++;
		}
		if (errors > 0) {
			return errors;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "has " + elementValues.size() + "</b> rows",
				Success.PASS);
		return 0;
	}

	/**
	 * compares the number of expected columns with the actual number of columns
	 * of a table with from a table element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param numOfColumns
	 *            the expected number of column elements of a table
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfTableColumns(Locators type, String locator, int numOfColumns) throws IOException {
		// outputFile.record our action
		int errors = 0;
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator
				+ "</i> with the number of table columns equal to <b>" + numOfColumns + "</b>");
		// check for our object to the present on the page
		List<WebElement> elementValues = null;
		boolean isPresent = action.isElementPresent(type, locator);
		if (isPresent) {
			elementValues = action.getTableColumns(type, locator);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (elementValues.size() != numOfColumns) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> does not have the number of columns <b>"
					+ numOfColumns + "</b> Instead, " + elementValues.size() + " columns were found", Success.FAIL);

			errors++;
		}
		if (errors > 0) {
			return errors;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator + "has " + elementValues.size() + "</b> columns",
				Success.PASS);
		return 0;
	}

	/**
	 * compares the expected index of row with header to the actual index of row
	 * with header from a table element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param header
	 *            the full text value expected in a th cell
	 * @param expectedIndex
	 *            the expected index of the row with header value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareRowWHeader(Locators type, String locator, String header, int expectedIndex) throws IOException {
		// outputFile.record our action
		int errors = 0;
		outputFile.recordExpected(EXPECTED + type + " <i>" + locator + "</i> with the row with header " + header
				+ " at index <b>" + expectedIndex + "</b>");
		// check for our object to the present on the page
		int rowIndex = 0;
		boolean isPresent = action.isElementPresent(type, locator);
		if (isPresent) {
			rowIndex = action.getTableRowWHeader(type, locator, header);
		}
		if (!isPresent) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + PRESENT, Success.FAIL);
			return 1;
		}
		if (rowIndex != expectedIndex) {
			outputFile.recordActual(ELEMENT + type + " <i>" + locator + "</i> and table row with header " + header
					+ " was not found at the index <b>" + expectedIndex, Success.FAIL);

			errors++;
		}
		if (errors > 0) {
			return errors;
		}
		outputFile.recordActual(ELEMENT + type + " <i>" + locator
				+ "</i> and table row with header was found at the index <b>" + expectedIndex + "</b>", Success.PASS);
		return 0;
	}
}