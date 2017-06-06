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

package com.coveros.selenified.output;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Element;
import com.coveros.selenified.selenium.Selenium.Browsers;
import com.coveros.selenified.selenium.Selenium.Locators;

/**
 * Test Output A custom generated output file outputFile.recording all actions
 * taken
 *
 * @author Max Saperstone
 * @version 2.0.0
 * @lastupdate 5/15/2017
 */
public class Assert {

	private Action action;
	private OutputFile outputFile;

	private LocatorAssert locatorAssert;

	// constants
	private static final String ONPAGE = "</b> on the page";
	private static final String NOALERT = "No alert is present on the page";
	private static final String ALERTTEXT = "An alert with text <b>";
	private static final String NOCONFIRMATION = "No confirmation is present on the page";
	private static final String CONFIRMATIONTEXT = "A confirmation with text <b>";
	private static final String NOPROMPT = "No prompt is present on the page";
	private static final String PROMPTTEXT = "A prompt with text <b>";

	private static final String STORED = "</b> is stored for the page";
	private static final String VALUE = "</b> and a value of <b>";
	private static final String COOKIE = "A cookie with the name <b>";
	private static final String NOCOOKIE = "No cookie with the name <b>";

	private static final String TEXT = "The text <b>";
	private static final String PRESENT = "</b> is present on the page";
	private static final String VISIBLE = "</b> is visible on the page";

	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////

	/**
	 * the complex test out constructor taking in all test document information
	 *
	 * @param testsName
	 *            the name of the file we will write out to
	 * @param browser
	 *            the browser we are running our tests with
	 * @param outputDir
	 *            the output directory to store the results
	 */
	public Assert(String testsName, Browsers browser, String outputDir) {
		outputFile = new OutputFile(outputDir, testsName, browser);
	}

	public void setSeleniumHelper(Action thisSelHelper) {
		action = thisSelHelper;
		locatorAssert = new LocatorAssert(action, outputFile);
	}

	public void setOutputFile(OutputFile thisOutputFile) {
		outputFile = thisOutputFile;
	}

	public OutputFile getOutputFile() {
		return outputFile;
	}

	/**
	 * checks to see if an alert is correct on the page
	 *
	 * @param expectedAlert
	 *            the expected text of the alert
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkAlert(String expectedAlert) {
		// outputFile.record our action
		outputFile.recordExpected("Expected to find alert with the text <b>" + expectedAlert + ONPAGE);
		// check for our object to the visible
		String alert = "";
		boolean isAlertPresent = action.isAlertPresent();
		if (isAlertPresent) {
			alert = action.getAlert();
		}
		if (!isAlertPresent) {
			outputFile.recordActual(NOALERT, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		Pattern patt = Pattern.compile(expectedAlert);
		Matcher m = patt.matcher(alert);
		boolean isCorrect;
		if (expectedAlert.contains("\\")) {
			isCorrect = m.matches();
		} else {
			isCorrect = alert.equals(expectedAlert);
		}
		if (!isCorrect) {
			outputFile.recordActual(ALERTTEXT + alert + PRESENT, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(ALERTTEXT + alert + PRESENT, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an alert is present on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkAlertPresent() {
		// outputFile.record our action
		outputFile.recordExpected("Expected to find an alert on the page");
		// check for our object to the visible
		String alert = "";
		boolean isAlertPresent = action.isAlertPresent();
		if (isAlertPresent) {
			alert = action.getAlert();
		}
		if (!isAlertPresent) {
			outputFile.recordActual(NOALERT, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(ALERTTEXT + alert + PRESENT, Success.PASS);
		return 0;
	}

	// /////////////////////////////////////////////////////////////////////////
	// a bunch of methods to positively check for objects using selenium calls
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * checks to see if an alert is not present on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkAlertNotPresent() {
		// outputFile.record our action
		outputFile.recordExpected("Expected not to find an alert on the page");
		// check for our object to the visible
		boolean isAlertPresent = action.isAlertPresent();
		if (isAlertPresent) {
			outputFile.recordActual("An alert is present on the page", Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(NOALERT, Success.PASS);
		return 0;
	}

	// /////////////////////////////////////////////////////////////////////////
	// a bunch of methods to negatively check for objects using selenium calls
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * checks to see if a confirmation is correct on the page
	 *
	 * @param expectedConfirmation
	 *            the expected text of the confirmation
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkConfirmation(String expectedConfirmation) {
		// outputFile.record our action
		outputFile.recordExpected("Expected to find confirmation with the text <b>" + expectedConfirmation + ONPAGE);
		// check for our object to the visible
		String confirmation = "";
		boolean isConfirmationPresent = action.isConfirmationPresent();
		if (isConfirmationPresent) {
			confirmation = action.getConfirmation();
		}
		if (!isConfirmationPresent) {
			outputFile.recordActual(NOCONFIRMATION, Success.FAIL);
			outputFile.addError();
			outputFile.addError();
			return 1;
		}
		if (!expectedConfirmation.equals(confirmation)) {
			outputFile.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a confirmation is present on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkConfirmationPresent() {
		// outputFile.record our action
		outputFile.recordExpected("Expected to find a confirmation on the page");
		// check for our object to the visible
		String confirmation = "";
		boolean isConfirmationPresent = action.isConfirmationPresent();
		if (isConfirmationPresent) {
			confirmation = action.getConfirmation();
		}
		if (!isConfirmationPresent) {
			outputFile.recordActual(NOCONFIRMATION, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a confirmation is not present on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkConfirmationNotPresent() {
		// outputFile.record our action
		outputFile.recordExpected("Expected to find a confirmation on the page");
		// check for our object to the visible
		boolean isConfirmationPresent = action.isConfirmationPresent();
		if (isConfirmationPresent) {
			outputFile.recordActual("A confirmation is present on the page", Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(NOCONFIRMATION, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a prompt is correct on the page
	 *
	 * @param expectedPrompt
	 *            the expected text of the confirmation
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkPrompt(String expectedPrompt) {
		// outputFile.record our action
		outputFile.recordExpected("Expected to find prompt with the text <b>" + expectedPrompt + ONPAGE);
		// check for our object to the visible
		String prompt = "";
		boolean isPromptPresent = action.isPromptPresent();
		if (isPromptPresent) {
			prompt = action.getPrompt();
		}
		if (!isPromptPresent) {
			outputFile.recordActual(NOPROMPT, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		if (!expectedPrompt.equals(prompt)) {
			outputFile.recordActual(PROMPTTEXT + prompt + PRESENT, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(PROMPTTEXT + prompt + PRESENT, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a prompt is present on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkPromptPresent() {
		// outputFile.record our action
		outputFile.recordExpected("Expected to find prompt on the page");
		// check for our object to the visible
		String prompt = "";
		boolean isPromptPresent = action.isPromptPresent();
		if (isPromptPresent) {
			prompt = action.getPrompt();
		}
		if (!isPromptPresent) {
			outputFile.recordActual(NOPROMPT, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(PROMPTTEXT + prompt + PRESENT, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a prompt is not present on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkPromptNotPresent() {
		// outputFile.record our action
		outputFile.recordExpected("Expected not to find prompt on the page");
		// check for our object to the visible
		boolean isPromptPresent = action.isPromptPresent();
		if (isPromptPresent) {
			outputFile.recordActual("A prompt is present on the page", Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(NOPROMPT, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a cookie is correct for the page
	 *
	 * @param cookieName
	 *            the name of the cookie
	 * @param expectedCookieValue
	 *            the expected value of the cookie
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkCookie(String cookieName, String expectedCookieValue) {
		// outputFile.record our action
		outputFile.recordExpected(
				"Expected to find cookie with the name <b>" + cookieName + VALUE + expectedCookieValue + STORED);
		// check for our object to the visible
		String cookieValue = "";
		boolean isCookiePresent = action.isCookiePresent(cookieName);
		if (isCookiePresent) {
			cookieValue = action.getCookieValue(cookieName);
		}
		if (!isCookiePresent) {
			outputFile.recordActual(NOCOOKIE + cookieName + STORED, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		if (!cookieValue.equals(expectedCookieValue)) {
			outputFile.recordActual(COOKIE + cookieName + "</b> is stored for the page, but the value "
					+ "of the cookie is " + cookieValue, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(COOKIE + cookieName + VALUE + cookieValue + STORED, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a cookie is present on the page
	 *
	 * @param expectedCookieName
	 *            the name of the cookie
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkCookiePresent(String expectedCookieName) {
		// outputFile.record our action
		outputFile.recordExpected("Expected to find cookie with the name <b>" + expectedCookieName + STORED);
		// check for our object to the visible
		String cookieValue = "";
		boolean isCookiePresent = action.isCookiePresent(expectedCookieName);
		if (isCookiePresent) {
			cookieValue = action.getCookieValue(expectedCookieName);
		}
		if (!isCookiePresent) {
			outputFile.recordActual(NOCOOKIE + expectedCookieName + STORED, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(COOKIE + expectedCookieName + VALUE + cookieValue + STORED, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a cookie is not present on the page
	 *
	 * @param unexpectedCookieName
	 *            the name of the cookie
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkCookieNotPresent(String unexpectedCookieName) {
		// outputFile.record our action
		outputFile.recordExpected("Expected to find no cookie with the name <b>" + unexpectedCookieName + STORED);
		// check for our object to the visible
		boolean isCookiePresent = action.isCookiePresent(unexpectedCookieName);
		if (isCookiePresent) {
			outputFile.recordActual(COOKIE + unexpectedCookieName + STORED, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(NOCOOKIE + unexpectedCookieName + STORED, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element is visible on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayed(Element element) throws IOException {
		return checkElementDisplayed(element.getType(), element.getLocator());
	}

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
		return checkElementDisplayed(type, locator, 0);
	}

	/**
	 * checks to see if an element is visible on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayed(Element element, int elementMatch) throws IOException {
		return checkElementDisplayed(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * checks to see if an element is visible on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayed(Locators type, String locator, int elementMatch) throws IOException {
		int errors = locatorAssert.checkElementDisplayed(type, locator, elementMatch);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an element is not visible on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementNotDisplayed(Element element) throws IOException {
		return checkElementNotDisplayed(element.getType(), element.getLocator());
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
		return checkElementNotDisplayed(type, locator, 0);
	}

	/**
	 * checks to see if an element is not visible on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementNotDisplayed(Element element, int elementMatch) throws IOException {
		return checkElementNotDisplayed(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * checks to see if an element is not visible on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementNotDisplayed(Locators type, String locator, int elementMatch) throws IOException {
		int errors = locatorAssert.checkElementNotDisplayed(type, locator, elementMatch);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an object is checked on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementChecked(Element element) throws IOException {
		return checkElementChecked(element.getType(), element.getLocator());
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
		return checkElementChecked(type, locator, 0);

	}

	/**
	 * checks to see if an object is checked on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementChecked(Element element, int elementMatch) throws IOException {
		return checkElementChecked(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * checks to see if an object is checked on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementChecked(Locators type, String locator, int elementMatch) throws IOException {
		int errors = locatorAssert.checkElementChecked(type, locator, elementMatch);
		outputFile.addErrors(errors);
		return errors;

	}

	/**
	 * checks to see if an object is not checked on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementNotChecked(Element element) throws IOException {
		return checkElementNotChecked(element.getType(), element.getLocator());
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
		return checkElementNotChecked(type, locator, 0);
	}

	/**
	 * checks to see if an object is not checked on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementNotChecked(Element element, int elementMatch) throws IOException {
		return checkElementNotChecked(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * checks to see if an object is not checked on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementNotChecked(Locators type, String locator, int elementMatch) throws IOException {
		int errors = locatorAssert.checkElementNotChecked(type, locator, elementMatch);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an object is visible and checked on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndChecked(Element element) throws IOException {
		return checkElementDisplayedAndChecked(element.getType(), element.getLocator());
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
		return checkElementDisplayedAndChecked(type, locator, 0);
	}

	/**
	 * checks to see if an object is visible and checked on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndChecked(Element element, int elementMatch) throws IOException {
		return checkElementDisplayedAndChecked(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * checks to see if an object is visible and checked on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndChecked(Locators type, String locator, int elementMatch) throws IOException {
		int errors = locatorAssert.checkElementDisplayedAndChecked(type, locator, elementMatch);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an object is visible and not checked on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndUnchecked(Element element) throws IOException {
		return checkElementDisplayedAndUnchecked(element.getType(), element.getLocator());
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
		return checkElementDisplayedAndUnchecked(type, locator, 0);
	}

	/**
	 * checks to see if an object is visible and not checked on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndUnchecked(Element element, int elementMatch) throws IOException {
		return checkElementDisplayedAndUnchecked(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * checks to see if an object is visible and not checked on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndUnchecked(Locators type, String locator, int elementMatch) throws IOException {
		int errors = locatorAssert.checkElementDisplayedAndUnchecked(type, locator, elementMatch);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an element is editable on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementEditable(Element element) throws IOException {
		return checkElementEditable(element.getType(), element.getLocator());
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
		return checkElementEditable(type, locator, 0);
	}

	/**
	 * checks to see if an element is editable on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementEditable(Element element, int elementMatch) throws IOException {
		return checkElementEditable(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * checks to see if an element is editable on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementEditable(Locators type, String locator, int elementMatch) throws IOException {
		int errors = locatorAssert.checkElementEditable(type, locator, elementMatch);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an element is not editable on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementNotEditable(Element element) throws IOException {
		return checkElementNotEditable(element.getType(), element.getLocator());
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
		return checkElementNotEditable(type, locator, 0);
	}

	/**
	 * checks to see if an element is not editable on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementNotEditable(Element element, int elementMatch) throws IOException {
		return checkElementNotEditable(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * checks to see if an element is not editable on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementNotEditable(Locators type, String locator, int elementMatch) throws IOException {
		int errors = locatorAssert.checkElementNotEditable(type, locator, elementMatch);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an element is visible and editable on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndEditable(Element element) throws IOException {
		return checkElementDisplayedAndEditable(element.getType(), element.getLocator());
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
		return checkElementDisplayedAndEditable(type, locator, 0);
	}

	/**
	 * checks to see if an element is visible and editable on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndEditable(Element element, int elementMatch) throws IOException {
		return checkElementDisplayedAndEditable(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * checks to see if an element is visible and editable on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndEditable(Locators type, String locator, int elementMatch) throws IOException {
		int errors = locatorAssert.checkElementDisplayedAndEditable(type, locator, elementMatch);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an element is visible and not editable on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndNotEditable(Element element) throws IOException {
		return checkElementDisplayedAndNotEditable(element.getType(), element.getLocator());
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
		return checkElementDisplayedAndNotEditable(type, locator, 0);
	}

	/**
	 * checks to see if an element is visible and not editable on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndNotEditable(Element element, int elementMatch) throws IOException {
		return checkElementDisplayedAndNotEditable(element.getType(), element.getLocator(), elementMatch);
	}

	/**
	 * checks to see if an element is visible and not editable on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDisplayedAndNotEditable(Locators type, String locator, int elementMatch) throws IOException {
		int errors = locatorAssert.checkElementDisplayedAndNotEditable(type, locator, elementMatch);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an element has an attribute associated with it
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param attribute
	 *            - the attribute to check for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementHasAttribute(Element element, String attribute) throws IOException {
		return checkElementHasAttribute(element.getType(), element.getLocator(), attribute);
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
		return checkElementHasAttribute(type, locator, 0, attribute);
	}

	/**
	 * checks to see if an element has an attribute associated with it
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param attribute
	 *            - the attribute to check for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementHasAttribute(Element element, int elementMatch, String attribute) throws IOException {
		return checkElementHasAttribute(element.getType(), element.getLocator(), elementMatch, attribute);
	}

	/**
	 * checks to see if an element has an attribute associated with it
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param attribute
	 *            - the attribute to check for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementHasAttribute(Locators type, String locator, int elementMatch, String attribute)
			throws IOException {
		int errors = locatorAssert.checkElementHasAttribute(type, locator, elementMatch, attribute);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an element has an attribute associated with it
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param attribute
	 *            - the attribute to check for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDoesntHaveAttribute(Element element, String attribute) throws IOException {
		return checkElementDoesntHaveAttribute(element.getType(), element.getLocator(), attribute);
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
		return checkElementDoesntHaveAttribute(type, locator, 0, attribute);
	}

	/**
	 * checks to see if an element has an attribute associated with it
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param attribute
	 *            - the attribute to check for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDoesntHaveAttribute(Element element, int elementMatch, String attribute) throws IOException {
		return checkElementDoesntHaveAttribute(element.getType(), element.getLocator(), elementMatch, attribute);
	}

	/**
	 * checks to see if an element has an attribute associated with it
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param attribute
	 *            - the attribute to check for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDoesntHaveAttribute(Locators type, String locator, int elementMatch, String attribute)
			throws IOException {
		int errors = locatorAssert.checkElementDoesntHaveAttribute(type, locator, elementMatch, attribute);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an element has a particular class
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param expectedClass
	 *            - the full expected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementHasClass(Element element, String expectedClass) throws IOException {
		return checkElementHasClass(element.getType(), element.getLocator(), expectedClass);
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
		return checkElementHasClass(type, locator, 0, expectedClass);
	}

	/**
	 * checks to see if an element has a particular class
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedClass
	 *            - the full expected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementHasClass(Element element, int elementMatch, String expectedClass) throws IOException {
		return checkElementHasClass(element.getType(), element.getLocator(), elementMatch, expectedClass);
	}

	/**
	 * checks to see if an element has a particular class
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedClass
	 *            - the full expected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementHasClass(Locators type, String locator, int elementMatch, String expectedClass)
			throws IOException {
		int errors = locatorAssert.checkElementHasClass(type, locator, elementMatch, expectedClass);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an element contains a particular class
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param expectedClass
	 *            - the expected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementContainsClass(Element element, String expectedClass) throws IOException {
		return checkElementContainsClass(element.getType(), element.getLocator(), expectedClass);
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
		return checkElementContainsClass(type, locator, 0, expectedClass);
	}

	/**
	 * checks to see if an element contains a particular class
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedClass
	 *            - the expected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementContainsClass(Element element, int elementMatch, String expectedClass) throws IOException {
		return checkElementContainsClass(element.getType(), element.getLocator(), elementMatch, expectedClass);
	}

	/**
	 * checks to see if an element contains a particular class
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedClass
	 *            - the expected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementContainsClass(Locators type, String locator, int elementMatch, String expectedClass)
			throws IOException {
		int errors = locatorAssert.checkElementContainsClass(type, locator, elementMatch, expectedClass);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an element does not contain a particular class
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param unexpectedClass
	 *            - the unexpected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDoesntContainClass(Element element, String unexpectedClass) throws IOException {
		return checkElementDoesntContainClass(element.getType(), element.getLocator(), unexpectedClass);
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
		return checkElementDoesntContainClass(type, locator, 0, unexpectedClass);
	}

	/**
	 * checks to see if an element does not contain a particular class
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param unexpectedClass
	 *            - the unexpected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDoesntContainClass(Element element, int elementMatch, String unexpectedClass)
			throws IOException {
		return checkElementDoesntContainClass(element.getType(), element.getLocator(), elementMatch, unexpectedClass);
	}

	/**
	 * checks to see if an element does not contain a particular class
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param unexpectedClass
	 *            - the unexpected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkElementDoesntContainClass(Locators type, String locator, int elementMatch, String unexpectedClass)
			throws IOException {
		int errors = locatorAssert.checkElementDoesntContainClass(type, locator, elementMatch, unexpectedClass);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an option is available to be selected on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param option
	 *            the option expected in the list
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkIfOptionInSelect(Element element, String option) throws IOException {
		return checkIfOptionInSelect(element.getType(), element.getLocator(), option);
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
		return checkIfOptionInSelect(type, locator, 0, option);
	}

	/**
	 * checks to see if an option is available to be selected on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param option
	 *            the option expected in the list
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkIfOptionInSelect(Element element, int elementMatch, String option) throws IOException {
		return checkIfOptionInSelect(element.getType(), element.getLocator(), elementMatch, option);
	}

	/**
	 * checks to see if an option is available to be selected on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param option
	 *            the option expected in the list
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkIfOptionInSelect(Locators type, String locator, int elementMatch, String option)
			throws IOException {
		int errors = locatorAssert.checkIfOptionInSelect(type, locator, elementMatch, option);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an option is not available to be selected on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param option
	 *            the option not expected in the list
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkIfOptionNotInSelect(Element element, String option) throws IOException {
		return checkIfOptionNotInSelect(element.getType(), element.getLocator(), option);
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
		return checkIfOptionNotInSelect(type, locator, 0, option);
	}

	/**
	 * checks to see if an option is not available to be selected on the page
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param option
	 *            the option not expected in the list
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkIfOptionNotInSelect(Element element, int elementMatch, String option) throws IOException {
		return checkIfOptionNotInSelect(element.getType(), element.getLocator(), elementMatch, option);
	}

	/**
	 * checks to see if an option is not available to be selected on the page
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param option
	 *            the option not expected in the list
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkIfOptionNotInSelect(Locators type, String locator, int elementMatch, String option)
			throws IOException {
		int errors = locatorAssert.checkIfOptionNotInSelect(type, locator, elementMatch, option);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if text is visible on the page
	 *
	 * @param expectedTexts
	 *            the expected text to be visible
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkTextVisible(String... expectedTexts) {
		// outputFile.record our action
		int errors = 0;
		for (String expectedText : expectedTexts) {
			outputFile.recordExpected("Expected to find text <b>" + expectedText + "</b> visible on the page");
			// check for our object to the visible
			boolean isPresent = action.isTextPresent(expectedText);
			if (!isPresent) {
				outputFile.recordActual(TEXT + expectedText + "</b> is not visible on the page", Success.FAIL);
				outputFile.addError();
				errors++;
			} else {
				outputFile.recordActual(TEXT + expectedText + VISIBLE, Success.PASS);
			}
		}
		return errors;
	}

	/**
	 * checks to see if text is not visible on the page
	 *
	 * @param expectedTexts
	 *            the expected text to be invisible
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkTextNotVisible(String... expectedTexts) {
		// outputFile.record our action
		int errors = 0;
		for (String expectedText : expectedTexts) {
			outputFile.recordExpected("Expected not to find text <b>" + expectedText + "</b> visible on the page");
			// check for our object to the visible
			boolean isPresent = action.isTextPresent(expectedText);
			if (isPresent) {
				outputFile.recordActual(TEXT + expectedText + VISIBLE, Success.FAIL);
				outputFile.addError();
				errors++;
			} else {
				outputFile.recordActual(TEXT + expectedText + "</b> is not visible on the page", Success.PASS);
			}
		}
		return errors;
	}

	/**
	 * checks to see if text is visible on the page
	 *
	 * @param expectedTexts
	 *            the expected text to be visible
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkTextVisibleOR(String... expectedTexts) {
		// outputFile.record our action
		int errors = 0;
		boolean isPresent = false;
		String foundText = "";
		StringBuilder allTexts = new StringBuilder();
		for (String expectedText : expectedTexts) {
			allTexts.append("<b>" + expectedText + "</b> or ");
		}
		allTexts.setLength(allTexts.length() - 4);
		outputFile.recordExpected("Expected to find text " + allTexts + " visible on the page");
		// check for our object to the visible
		for (String expectedText : expectedTexts) {
			isPresent = action.isTextPresent(expectedText);
			if (isPresent) {
				foundText = expectedText;
				break;
			}
		}
		if (!isPresent) {
			outputFile.recordActual(
					"None of the texts " + allTexts.toString().replace(" or ", ", ") + " are visible on the page",
					Success.FAIL);
			outputFile.addError();
			errors++;
			return errors;
		}
		outputFile.recordActual(TEXT + foundText + VISIBLE, Success.PASS);
		return errors;
	}

	/**
	 * compares the expected element value with the actual value from an element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param expectedValue
	 *            the expected value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareTextValue(Element element, String expectedValue) throws IOException {
		return compareTextValue(element.getType(), element.getLocator(), expectedValue);
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
		return compareTextValue(type, locator, 0, expectedValue);
	}

	/**
	 * compares the expected element value with the actual value from an element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedValue
	 *            the expected value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareTextValue(Element element, int elementMatch, String expectedValue) throws IOException {
		return compareTextValue(element.getType(), element.getLocator(), elementMatch, expectedValue);
	}

	/**
	 * compares the expected element value with the actual value from an element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedValue
	 *            the expected value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareTextValue(Locators type, String locator, int elementMatch, String expectedValue)
			throws IOException {
		int errors = locatorAssert.compareTextValue(type, locator, elementMatch, expectedValue);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * compares the expected element value with the actual value from an element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param expectedValue
	 *            the expected value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareTextValueContains(Element element, String expectedValue) throws IOException {
		return compareTextValueContains(element.getType(), element.getLocator(), expectedValue);
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
		return compareTextValueContains(type, locator, 0, expectedValue);
	}

	/**
	 * compares the expected element value with the actual value from an element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedValue
	 *            the expected value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareTextValueContains(Element element, int elementMatch, String expectedValue) throws IOException {
		return compareTextValueContains(element.getType(), element.getLocator(), elementMatch, expectedValue);
	}

	/**
	 * compares the expected element value with the actual value from an element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedValue
	 *            the expected value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareTextValueContains(Locators type, String locator, int elementMatch, String expectedValue)
			throws IOException {
		int errors = locatorAssert.compareTextValueContains(type, locator, elementMatch, expectedValue);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * compares the actual title a page is on to the expected title
	 *
	 * @param expectedTitle
	 *            the friendly name of the page
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int compareTitle(String expectedTitle) {
		// outputFile.record our action
		outputFile.recordExpected("Expected to be on page with the title of <i>" + expectedTitle + "</i>");
		String actualTitle = action.getTitle();
		if (!actualTitle.equalsIgnoreCase(expectedTitle)) {
			outputFile.recordActual("The page title reads <b>" + actualTitle + "</b>", Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual("The page title reads <b>" + actualTitle + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the actual URL a page is on to the expected URL
	 *
	 * @param expectedURL
	 *            the URL of the page
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int compareURL(String expectedURL) {
		// outputFile.record our action
		outputFile.recordExpected("Expected to be on page with the URL of <i>" + expectedURL + "</i>");
		String actualURL = action.getLocation();
		if (!actualURL.equalsIgnoreCase(expectedURL)) {
			outputFile.recordActual("The page URL  reads <b>" + actualURL + "</b>", Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual("The page URL reads <b>" + actualURL + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element input value with the actual value from an
	 * element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareInputValue(Element element, String expectedValue) throws IOException {
		return compareInputValue(element.getType(), element.getLocator(), expectedValue);
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
		return compareInputValue(type, locator, 0, expectedValue);
	}

	/**
	 * compares the expected element input value with the actual value from an
	 * element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareInputValue(Element element, int elementMatch, String expectedValue) throws IOException {
		return compareInputValue(element.getType(), element.getLocator(), elementMatch, expectedValue);
	}

	/**
	 * compares the expected element input value with the actual value from an
	 * element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareInputValue(Locators type, String locator, int elementMatch, String expectedValue)
			throws IOException {
		int errors = locatorAssert.compareInputValue(type, locator, elementMatch, expectedValue);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * compares the expected element css attribute value with the actual css
	 * attribute value from an element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param attribute
	 *            - the css attribute to be checked
	 * @param expectedValue
	 *            the expected css value of the passed attribute of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareCssValue(Element element, String attribute, String expectedValue) throws IOException {
		return compareCssValue(element.getType(), element.getLocator(), attribute, expectedValue);
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
		return compareCssValue(type, locator, 0, attribute, expectedValue);
	}

	/**
	 * compares the expected element css attribute value with the actual css
	 * attribute value from an element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param attribute
	 *            - the css attribute to be checked
	 * @param expectedValue
	 *            the expected css value of the passed attribute of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareCssValue(Element element, int elementMatch, String attribute, String expectedValue)
			throws IOException {
		return compareCssValue(element.getType(), element.getLocator(), elementMatch, attribute, expectedValue);
	}

	/**
	 * compares the expected element css attribute value with the actual css
	 * attribute value from an element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param attribute
	 *            - the css attribute to be checked
	 * @param expectedValue
	 *            the expected css value of the passed attribute of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareCssValue(Locators type, String locator, int elementMatch, String attribute, String expectedValue)
			throws IOException {
		int errors = locatorAssert.compareCssValue(type, locator, elementMatch, attribute, expectedValue);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an element select value exists
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param selectValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkSelectValuePresent(Element element, String selectValue) throws IOException {
		return checkSelectValuePresent(element.getType(), element.getLocator(), selectValue);
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
		return checkSelectValuePresent(type, locator, 0, selectValue);
	}

	/**
	 * checks to see if an element select value exists
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param selectValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkSelectValuePresent(Element element, int elementMatch, String selectValue) throws IOException {
		return checkSelectValuePresent(element.getType(), element.getLocator(), elementMatch, selectValue);
	}

	/**
	 * checks to see if an element select value exists
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param selectValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkSelectValuePresent(Locators type, String locator, int elementMatch, String selectValue)
			throws IOException {
		int errors = locatorAssert.checkSelectValuePresent(type, locator, elementMatch, selectValue);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * checks to see if an element select value does not exist
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param selectValue
	 *            the unexpected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkSelectValueNotPresent(Element element, String selectValue) throws IOException {
		return checkSelectValueNotPresent(element.getType(), element.getLocator(), selectValue);
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
		return checkSelectValueNotPresent(type, locator, 0, selectValue);
	}

	/**
	 * checks to see if an element select value does not exist
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param selectValue
	 *            the unexpected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkSelectValueNotPresent(Element element, int elementMatch, String selectValue) throws IOException {
		return checkSelectValueNotPresent(element.getType(), element.getLocator(), elementMatch, selectValue);
	}

	/**
	 * checks to see if an element select value does not exist
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param selectValue
	 *            the unexpected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int checkSelectValueNotPresent(Locators type, String locator, int elementMatch, String selectValue)
			throws IOException {
		int errors = locatorAssert.checkSelectValueNotPresent(type, locator, elementMatch, selectValue);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * compares the expected element select value with the actual value from an
	 * element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectedValue(Element element, String expectedValue) throws IOException {
		return compareSelectedValue(element.getType(), element.getLocator(), expectedValue);
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
		return compareSelectedValue(type, locator, 0, expectedValue);
	}

	/**
	 * compares the expected element select value with the actual value from an
	 * element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectedValue(Element element, int elementMatch, String expectedValue) throws IOException {
		return compareSelectedValue(element.getType(), element.getLocator(), elementMatch, expectedValue);
	}

	/**
	 * compares the expected element select value with the actual value from an
	 * element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectedValue(Locators type, String locator, int elementMatch, String expectedValue)
			throws IOException {
		int errors = locatorAssert.compareSelectedValue(type, locator, elementMatch, expectedValue);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * compares the expected element select test with the actual value from an
	 * element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param expectedText
	 *            the expected input text of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectedText(Element element, String expectedText) throws IOException {
		return compareSelectedText(element.getType(), element.getLocator(), expectedText);
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
		return compareSelectedText(type, locator, 0, expectedText);
	}

	/**
	 * compares the expected element select test with the actual value from an
	 * element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedText
	 *            the expected input text of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectedText(Element element, int elementMatch, String expectedText) throws IOException {
		return compareSelectedText(element.getType(), element.getLocator(), elementMatch, expectedText);
	}

	/**
	 * compares the expected element select test with the actual value from an
	 * element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedText
	 *            the expected input text of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectedText(Locators type, String locator, int elementMatch, String expectedText)
			throws IOException {
		int errors = locatorAssert.compareSelectedText(type, locator, elementMatch, expectedText);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * compares the expected element select value with the actual value from an
	 * element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectedValueNotEqual(Element element, String expectedValue) throws IOException {
		return compareSelectedValueNotEqual(element.getType(), element.getLocator(), expectedValue);
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
		return compareSelectedValueNotEqual(type, locator, 0, expectedValue);
	}

	/**
	 * compares the expected element select value with the actual value from an
	 * element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectedValueNotEqual(Element element, int elementMatch, String expectedValue)
			throws IOException {
		return compareSelectedValueNotEqual(element.getType(), element.getLocator(), elementMatch, expectedValue);
	}

	/**
	 * compares the expected element select value with the actual value from an
	 * element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectedValueNotEqual(Locators type, String locator, int elementMatch, String expectedValue)
			throws IOException {
		int errors = locatorAssert.compareSelectedValueNotEqual(type, locator, elementMatch, expectedValue);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * compares the expected attributes from a select value with the actual
	 * attributes from the element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param expectedValues
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectValues(Element element, String... expectedValues) throws IOException {
		return compareSelectValues(element.getType(), element.getLocator(), expectedValues);
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
		return compareSelectValues(type, locator, 0, expectedValues);
	}

	/**
	 * compares the expected attributes from a select value with the actual
	 * attributes from the element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedValues
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectValues(Element element, int elementMatch, String... expectedValues) throws IOException {
		return compareSelectValues(element.getType(), element.getLocator(), elementMatch, expectedValues);
	}

	/**
	 * compares the expected attributes from a select value with the actual
	 * attributes from the element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param expectedValues
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareSelectValues(Locators type, String locator, int elementMatch, String... expectedValues)
			throws IOException {
		int errors = locatorAssert.compareSelectValues(type, locator, elementMatch, expectedValues);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * compares the expected attributes from a select value with the actual
	 * attributes from the element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param numOfOptions
	 *            the expected number of options in the select element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfSelectOptions(Element element, int numOfOptions) throws IOException {
		return compareNumOfSelectOptions(element.getType(), element.getLocator(), numOfOptions);
	}

	/**
	 * compares the expected attributes from a select value with the actual
	 * attributes from the element
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
		return compareNumOfSelectOptions(type, locator, 0, numOfOptions);
	}

	/**
	 * compares the expected attributes from a select value with the actual
	 * attributes from the element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param numOfOptions
	 *            the expected number of options in the select element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfSelectOptions(Element element, int elementMatch, int numOfOptions) throws IOException {
		return compareNumOfSelectOptions(element.getType(), element.getLocator(), elementMatch, numOfOptions);
	}

	/**
	 * compares the expected attributes from a select value with the actual
	 * attributes from the element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param numOfOptions
	 *            the expected number of options in the select element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfSelectOptions(Locators type, String locator, int elementMatch, int numOfOptions)
			throws IOException {
		int errors = locatorAssert.compareNumOfSelectOptions(type, locator, elementMatch, numOfOptions);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * compares the number of expected rows with the actual number of rows of a
	 * table with from a table element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param numOfRows
	 *            the number of rows in a table
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfTableRows(Element element, int numOfRows) throws IOException {
		return compareNumOfTableRows(element.getType(), element.getLocator(), numOfRows);
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
	 *            the number of rows in a table
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfTableRows(Locators type, String locator, int numOfRows) throws IOException {
		return compareNumOfTableRows(type, locator, 0, numOfRows);
	}

	/**
	 * compares the number of expected rows with the actual number of rows of a
	 * table with from a table element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param numOfRows
	 *            the number of rows in a table
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfTableRows(Element element, int elementMatch, int numOfRows) throws IOException {
		return compareNumOfTableRows(element.getType(), element.getLocator(), elementMatch, numOfRows);
	}

	/**
	 * compares the number of expected rows with the actual number of rows of a
	 * table with from a table element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param numOfRows
	 *            the number of rows in a table
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfTableRows(Locators type, String locator, int elementMatch, int numOfRows)
			throws IOException {
		int errors = locatorAssert.compareNumOfTableRows(type, locator, elementMatch, numOfRows);
		outputFile.addErrors(errors);
		return errors;
	}

	/**
	 * compares the number of expected columns with the actual number of columns
	 * of a table with from a table element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param numOfColumns
	 *            the number of columns in a table
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfTableColumns(Element element, int numOfColumns) throws IOException {
		return compareNumOfTableColumns(element.getType(), element.getLocator(), numOfColumns);
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
	 *            the number of columns in a table
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfTableColumns(Locators type, String locator, int numOfColumns) throws IOException {
		return compareNumOfTableColumns(type, locator, 0, numOfColumns);
	}

	/**
	 * compares the number of expected columns with the actual number of columns
	 * of a table with from a table element
	 *
	 * @param element
	 *            - the element to be waited for
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param numOfColumns
	 *            the number of columns in a table
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfTableColumns(Element element, int elementMatch, int numOfColumns) throws IOException {
		return compareNumOfTableColumns(element.getType(), element.getLocator(), elementMatch, numOfColumns);
	}

	/**
	 * compares the number of expected columns with the actual number of columns
	 * of a table with from a table element
	 *
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param numOfColumns
	 *            the number of columns in a table
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws IOException
	 */
	public int compareNumOfTableColumns(Locators type, String locator, int elementMatch, int numOfColumns)
			throws IOException {
		int errors = locatorAssert.compareNumOfTableColumns(type, locator, elementMatch, numOfColumns);
		outputFile.addErrors(errors);
		return errors;
	}

	///////////////////////////////////////////////////////////////////
	// this enum will be for a pass/fail
	///////////////////////////////////////////////////////////////////

	public enum Success {
		PASS, FAIL
	}

	public enum Result {
		WARNING, SUCCESS, FAILURE, SKIPPED
	}
}