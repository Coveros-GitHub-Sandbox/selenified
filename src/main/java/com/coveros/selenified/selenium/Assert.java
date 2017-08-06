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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.selenium.Selenium.Browser;

/**
 * A custom reporting class, which provides logging and screenshots for all
 * verifications. Meant to provide additional traceability on top of TestNG
 * Asserts
 *
 * @author Max Saperstone
 * @version 2.0.1
 * @lastupdate 7/20/2017
 */
public class Assert {

	private Action action;
	private OutputFile outputFile;

	// the is class to determine the state of an element
	private State state;
	// the is class to determine if an element contains something
	private Contains contains;
	// the is class to determine if an element doesn't contain something
	private Excludes excludes;
	// the is class to determine if an element has attributes equal to something
	private Equals equals;

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

	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////

	/**
	 * the detailed test results constructor that will setup the our test output
	 * file for all documentation and information for browser based testing
	 *
	 * @param testsName
	 *            the name of the file we will write out to
	 * @param browser
	 *            the browser we are running the tests with
	 * @param outputDir
	 *            the output directory to store the results
	 */
	public Assert(String outputDir, String testsName, Browser browser) {
		outputFile = new OutputFile(outputDir, testsName, browser);
		state = new State(action, outputFile);
		contains = new Contains(action, outputFile);
		excludes = new Excludes(action, outputFile);
		equals = new Equals(action, outputFile);
	}

	public Assert(String outputDir, String testsName, String serviceURL) {
		outputFile = new OutputFile(outputDir, testsName, serviceURL);
		state = new State(action, outputFile);
		contains = new Contains(action, outputFile);
		excludes = new Excludes(action, outputFile);
		equals = new Equals(action, outputFile);
	}

	public void setAction(Action action) {
		this.action = action;
		state.setAction(action);
		contains.setAction(action);
		excludes.setAction(action);
		equals.setAction(action);
	}

	public void setOutputFile(OutputFile thisOutputFile) {
		outputFile = thisOutputFile;
		state.setOutputFile(outputFile);
		contains.setOutputFile(outputFile);
		excludes.setOutputFile(outputFile);
		equals.setOutputFile(outputFile);
	}

	public OutputFile getOutputFile() {
		return outputFile;
	}

	///////////////////////////////////////////////////////
	// instantiating our additional assert classes for further use
	///////////////////////////////////////////////////////

	public State state() {
		return state;
	}

	public Contains contains() {
		return contains;
	}

	public Excludes excludes() {
		return excludes;
	}

	public Equals equals() { // NOSONAR - purposefully overloading the method
								// call, at least for now
		return equals;
	}

	///////////////////////////////////////////////////////
	// assertions about the page in general
	///////////////////////////////////////////////////////

	/**
	 * compares the actual URL a page is on to the expected URL
	 *
	 * @param expectedURL
	 *            the URL of the page
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int compareURL(String expectedURL) {
		// outputFile.record the action
		outputFile.recordExpected("Expected to be on page with the URL of <i>" + expectedURL + "</i>");
		String actualURL = action.get().location();
		if (!actualURL.equalsIgnoreCase(expectedURL)) {
			outputFile.recordActual("The page URL  reads <b>" + actualURL + "</b>", Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual("The page URL reads <b>" + actualURL + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the actual title a page is on to the expected title
	 *
	 * @param expectedTitle
	 *            the friendly name of the page
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int compareTitle(String expectedTitle) {
		// outputFile.record the action
		outputFile.recordExpected("Expected to be on page with the title of <i>" + expectedTitle + "</i>");
		String actualTitle = action.get().title();
		if (!actualTitle.equalsIgnoreCase(expectedTitle)) {
			outputFile.recordActual("The page title reads <b>" + actualTitle + "</b>", Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual("The page title reads <b>" + actualTitle + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if text is visible on the page
	 *
	 * @param expectedTexts
	 *            the expected text to be visible
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int textPresent(String... expectedTexts) {
		// outputFile.record the action
		int errors = 0;
		for (String expectedText : expectedTexts) {
			outputFile.recordExpected("Expected to find text <b>" + expectedText + "</b> visible on the page");
			// check for the object to the visible
			boolean isPresent = action.is().textPresent(expectedText);
			if (!isPresent) {
				outputFile.recordActual(TEXT + expectedText + "</b> is not present on the page", Success.FAIL);
				outputFile.addError();
				errors++;
			} else {
				outputFile.recordActual(TEXT + expectedText + PRESENT, Success.PASS);
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
	public int textNotPresent(String... expectedTexts) {
		// outputFile.record the action
		int errors = 0;
		for (String expectedText : expectedTexts) {
			outputFile.recordExpected("Expected not to find text <b>" + expectedText + "</b> visible on the page");
			// check for the object to the visible
			boolean isPresent = action.is().textPresent(expectedText);
			if (isPresent) {
				outputFile.recordActual(TEXT + expectedText + PRESENT, Success.FAIL);
				outputFile.addError();
				errors++;
			} else {
				outputFile.recordActual(TEXT + expectedText + "</b> is not present on the page", Success.PASS);
			}
		}
		return errors;
	}

	///////////////////////////////////////////////////////
	// assertions about pop-ups
	///////////////////////////////////////////////////////

	/**
	 * checks to see if an alert is present on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int alertPresent() {
		// outputFile.record the action
		outputFile.recordExpected("Expected to find an alert on the page");
		// check for the object to the visible
		String alert = "";
		boolean isAlertPresent = action.is().alertPresent();
		if (isAlertPresent) {
			alert = action.get().alert();
		}
		if (!isAlertPresent) {
			outputFile.recordActual(NOALERT, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(ALERTTEXT + alert + PRESENT, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an alert is not present on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int alertNotPresent() {
		// outputFile.record the action
		outputFile.recordExpected("Expected not to find an alert on the page");
		// check for the object to the visible
		boolean isAlertPresent = action.is().alertPresent();
		if (isAlertPresent) {
			outputFile.recordActual("An alert is present on the page", Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(NOALERT, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an alert is correct on the page
	 *
	 * @param expectedAlertText
	 *            the expected text of the alert
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int alertPresent(String expectedAlertText) {
		// outputFile.record the action
		outputFile.recordExpected("Expected to find alert with the text <b>" + expectedAlertText + ONPAGE);
		// check for the object to the visible
		String alert = "";
		boolean isAlertPresent = action.is().alertPresent();
		if (isAlertPresent) {
			alert = action.get().alert();
		}
		if (!isAlertPresent) {
			outputFile.recordActual(NOALERT, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		Pattern patt = Pattern.compile(expectedAlertText);
		Matcher m = patt.matcher(alert);
		boolean isCorrect;
		if (expectedAlertText.contains("\\")) {
			isCorrect = m.matches();
		} else {
			isCorrect = alert.equals(expectedAlertText);
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
	 * checks to see if a confirmation is present on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int confirmationPresent() {
		// outputFile.record the action
		outputFile.recordExpected("Expected to find a confirmation on the page");
		// check for the object to the visible
		String confirmation = "";
		boolean isConfirmationPresent = action.is().confirmationPresent();
		if (isConfirmationPresent) {
			confirmation = action.get().confirmation();
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
	public int confirmationNotPresent() {
		// outputFile.record the action
		outputFile.recordExpected("Expected to find a confirmation on the page");
		// check for the object to the visible
		boolean isConfirmationPresent = action.is().confirmationPresent();
		if (isConfirmationPresent) {
			outputFile.recordActual("A confirmation is present on the page", Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(NOCONFIRMATION, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a confirmation is correct on the page
	 *
	 * @param expectedConfirmation
	 *            the expected text of the confirmation
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int confirmationPresent(String expectedConfirmationText) {
		// outputFile.record the action
		outputFile
				.recordExpected("Expected to find confirmation with the text <b>" + expectedConfirmationText + ONPAGE);
		// check for the object to the visible
		String confirmation = "";
		boolean isConfirmationPresent = action.is().confirmationPresent();
		if (isConfirmationPresent) {
			confirmation = action.get().confirmation();
		}
		if (!isConfirmationPresent) {
			outputFile.recordActual(NOCONFIRMATION, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		if (!expectedConfirmationText.equals(confirmation)) {
			outputFile.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(CONFIRMATIONTEXT + confirmation + PRESENT, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a prompt is present on the page
	 *
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int promptPresent() {
		// outputFile.record the action
		outputFile.recordExpected("Expected to find prompt on the page");
		// check for the object to the visible
		String prompt = "";
		boolean isPromptPresent = action.is().promptPresent();
		if (isPromptPresent) {
			prompt = action.get().prompt();
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
	public int promptNotPresent() {
		// outputFile.record the action
		outputFile.recordExpected("Expected not to find prompt on the page");
		// check for the object to the visible
		boolean isPromptPresent = action.is().promptPresent();
		if (isPromptPresent) {
			outputFile.recordActual("A prompt is present on the page", Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(NOPROMPT, Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a prompt is correct on the page
	 *
	 * @param expectedPromptText
	 *            the expected text of the confirmation
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int promptPresent(String expectedPromptText) {
		// outputFile.record the action
		outputFile.recordExpected("Expected to find prompt with the text <b>" + expectedPromptText + ONPAGE);
		// check for the object to the visible
		String prompt = "";
		boolean isPromptPresent = action.is().promptPresent();
		if (isPromptPresent) {
			prompt = action.get().prompt();
		}
		if (!isPromptPresent) {
			outputFile.recordActual(NOPROMPT, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		if (!expectedPromptText.equals(prompt)) {
			outputFile.recordActual(PROMPTTEXT + prompt + PRESENT, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(PROMPTTEXT + prompt + PRESENT, Success.PASS);
		return 0;
	}

	///////////////////////////////////////////////////////
	// assertions about cookies
	///////////////////////////////////////////////////////

	/**
	 * checks to see if a cookie is present on the page
	 *
	 * @param expectedCookieName
	 *            the name of the cookie
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int cookieExists(String expectedCookieName) {
		// outputFile.record the action
		outputFile.recordExpected("Expected to find cookie with the name <b>" + expectedCookieName + STORED);
		// check for the object to the visible
		String cookieValue = "";
		boolean isCookiePresent = action.is().cookiePresent(expectedCookieName);
		if (isCookiePresent) {
			cookieValue = action.get().cookieValue(expectedCookieName);
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
	public int cookieNotExists(String unexpectedCookieName) {
		// outputFile.record the action
		outputFile.recordExpected("Expected to find no cookie with the name <b>" + unexpectedCookieName + STORED);
		// check for the object to the visible
		boolean isCookiePresent = action.is().cookiePresent(unexpectedCookieName);
		if (isCookiePresent) {
			outputFile.recordActual(COOKIE + unexpectedCookieName + STORED, Success.FAIL);
			outputFile.addError();
			return 1;
		}
		outputFile.recordActual(NOCOOKIE + unexpectedCookieName + STORED, Success.PASS);
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
	public int cookieExists(String cookieName, String expectedCookieValue) {
		// outputFile.record the action
		outputFile.recordExpected(
				"Expected to find cookie with the name <b>" + cookieName + VALUE + expectedCookieValue + STORED);
		// check for the object to the visible
		String cookieValue = "";
		boolean isCookiePresent = action.is().cookiePresent(cookieName);
		if (isCookiePresent) {
			cookieValue = action.get().cookieValue(cookieName);
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

	///////////////////////////////////////////////////////////////////
	// this enum will be for a pass/fail
	///////////////////////////////////////////////////////////////////

	/**
	 * An enumeration used to determine if the tests pass or fail
	 * 
	 * @author Max Saperstone
	 *
	 */
	public enum Success {
		PASS, FAIL;

		protected int errors;

		/**
		 * Are errors associated with the enumeration
		 */
		static {
			PASS.errors = 0;
			FAIL.errors = 1;
		}

		/**
		 * Retrieve the errors associated with the enumeration
		 * 
		 * @return Integer: the errors associated with the enumeration
		 */
		public int getErrors() {
			return this.errors;
		}
	}

	/**
	 * An enumeration used to give status for each test step
	 * 
	 * @author Max Saperstone
	 *
	 */
	public enum Result {
		WARNING, SUCCESS, FAILURE, SKIPPED
	}
}