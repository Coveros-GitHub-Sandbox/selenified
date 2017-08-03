package com.coveros.selenified.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.log4testng.Logger;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.General;

public class Is {

	private static final Logger log = Logger.getLogger(General.class);

	// this will be the name of the file we write all commands out to
	private OutputFile file;

	// what locator actions are available in webdriver
	// this is the driver that will be used for all selenium actions
	private WebDriver driver;
	
	// the wait class to retrieve information about the element
	private Get get;
	
    // constants
	private static final String CHECKING = "Checking for ";
	private static final String PRESENT = " to be present";
	private static final String DISPLAYED = " to be displayed";
	private static final String ENABLED = " to be enabled";
	private static final String SELECTED = " to have something selected";

	public Is(WebDriver driver, OutputFile file) {
		this.driver = driver;
		this.file = file;
		this.get = new Get(driver, file);
	}

	// ////////////////////////////////////
	// checking element availability
	// ////////////////////////////////////

	/**
	 * a method for checking if an element is present
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementPresent(Element element) {
		return elementPresent(element, false);
	}

	/**
	 * a method for checking if an element is present
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementPresent(Locator type, String locator) {
		return elementPresent(new Element(type, locator), false);
	}

	/**
	 * a method for checking if an element is present
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementPresent(Locator type, String locator, boolean print) {
		return elementPresent(new Element(type, locator), print);
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
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementPresent(Locator type, String locator, int elementMatch) {
		return elementPresent(new Element(type, locator, elementMatch), false);
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
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementPresent(Locator type, String locator, int elementMatch, boolean print) {
		return elementPresent(new Element(type, locator, elementMatch), print);
	}

	/**
	 * a method for checking if an element is an input; it needs to be an input,
	 * select, or textarea
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return boolean: whether the element is an input or not
	 */
	public boolean elementInput(Element element) {
		return elementInput(element, false);
	}

	/**
	 * a method for checking if an element is an input; it needs to be an input,
	 * select, or textarea
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return boolean: whether the element is an input or not
	 */
	public boolean elementInput(Locator type, String locator) {
		return elementInput(new Element(type, locator), false);
	}

	/**
	 * a method for checking if an element is an input; it needs to be an input,
	 * select, or textarea
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementInput(Locator type, String locator, boolean print) {
		return elementInput(new Element(type, locator), print);
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
	 * @return boolean: whether the element is an input or not
	 */
	public boolean elementInput(Locator type, String locator, int elementMatch) {
		return elementInput(new Element(type, locator, elementMatch), false);
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
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementInput(Locator type, String locator, int elementMatch, boolean print) {
		return elementInput(new Element(type, locator, elementMatch), print);
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementEnabled(Element element) {
		return elementEnabled(element, false);
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementEnabled(Locator type, String locator) {
		return elementEnabled(new Element(type, locator), false);
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementEnabled(Locator type, String locator, boolean print) {
		return elementEnabled(new Element(type, locator), print);
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
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementEnabled(Locator type, String locator, int elementMatch) {
		return elementEnabled(new Element(type, locator, elementMatch), false);
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
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementEnabled(Locator type, String locator, int elementMatch, boolean print) {
		return elementEnabled(new Element(type, locator, elementMatch), print);
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return boolean: whether the element is checked or not
	 */
	public boolean elementChecked(Element element) {
		return elementChecked(element, false);
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return boolean: whether the element is checked or not
	 */
	public boolean elementChecked(Locator type, String locator) {
		return elementChecked(new Element(type, locator), false);
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is checked or not
	 */
	public boolean elementChecked(Locator type, String locator, boolean print) {
		return elementChecked(new Element(type, locator), print);
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
	 * @return boolean: whether the element is checked or not
	 */
	public boolean elementChecked(Locator type, String locator, int elementMatch) {
		return elementChecked(new Element(type, locator, elementMatch), false);
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
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is checked or not
	 */
	public boolean elementChecked(Locator type, String locator, int elementMatch, boolean print) {
		return elementChecked(new Element(type, locator, elementMatch), print);
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param element
	 *            - the element to be waited for
	 * @return boolean: whether the element is displayed or not
	 */
	public boolean elementDisplayed(Element element) {
		return elementDisplayed(element, false);
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return boolean: whether the element is displayed or not
	 */
	public boolean elementDisplayed(Locator type, String locator) {
		return elementDisplayed(new Element(type, locator), false);
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is displayed or not
	 */
	public boolean elementDisplayed(Locator type, String locator, boolean print) {
		return elementDisplayed(new Element(type, locator), print);
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
	 * @return boolean: whether the element is displayed or not
	 */
	public boolean elementDisplayed(Locator type, String locator, int elementMatch) {
		return elementDisplayed(new Element(type, locator, elementMatch), false);
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
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is displayed or not
	 */
	public boolean elementDisplayed(Locator type, String locator, int elementMatch, boolean print) {
		return elementDisplayed(new Element(type, locator, elementMatch), print);
	}
	
	/**
	 * determine if something is selected in a drop down
	 * 
	 * @param element
	 *            - the element to be waited for
	 * @return boolean: is something selected or not
	 */
	public boolean somethingSelected(Element element) {
		return somethingSelected(element, false);
	}

	/**
	 * determine if something is selected in a drop down
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * 
	 * @return boolean: is something selected or not
	 */
	public boolean somethingSelected(Locator type, String locator) {
		return somethingSelected(new Element(type, locator), false);
	}

	/**
	 * determine if something is selected in a drop down
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param print
	 *            - whether or not to print out this wait statement
	 * @return boolean: is something selected or not
	 */
	public boolean somethingSelected(Locator type, String locator, boolean print) {
		return somethingSelected(new Element(type, locator), print);
	}

	/**
	 * determine if something is selected in a drop down
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @return boolean: is something selected or not
	 */
	public boolean somethingSelected(Locator type, String locator, int elementMatch) {
		return somethingSelected(new Element(type, locator, elementMatch), false);
	}

	/**
	 * determine if something is selected in a drop down
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param elementMatch
	 *            - if there are multiple matches of the selector, this is which
	 *            match (starting at 0) to interact with
	 * @param print
	 *            - whether or not to print out this wait statement
	 * @return boolean: is something selected or not
	 */
	public boolean somethingSelected(Locator type, String locator, int elementMatch, boolean print) {
		return somethingSelected(new Element(type, locator, elementMatch), print);
	}

	/**
	 * a method to check if an alert is present
	 *
	 * @return boolean - is an alert present
	 */
	public boolean alertPresent() {
		return alertPresent(false);
	}

	/**
	 * a method to check if a confirmation is present
	 *
	 * @return boolean - is a confirmation present
	 */
	public boolean confirmationPresent() {
		return confirmationPresent(false);
	}

	/**
	 * a method to check if a prompt is present
	 *
	 * @return boolean - is a prompt present
	 */
	public boolean promptPresent() {
		return promptPresent(false);
	}

	///////////////////////////////////////////////////
	// Our actual full implementation of the above overloaded methods
	///////////////////////////////////////////////////

	/**
	 * a method to check if an alert is present
	 *
	 * @param print
	 *            - whether or not to print out this wait statement
	 * @return boolean - is an alert present
	 */
	public boolean alertPresent(boolean print) {
		boolean isPresent = false;
		try {
			driver.switchTo().alert();
			isPresent = true;
		} catch (NoAlertPresentException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected("Checking for alert to be present");
		}
		return isPresent;
	}

	/**
	 * a method to check if a confirmation is present
	 *
	 * @param print
	 *            - whether or not to print out this wait statement
	 * @return boolean - is a confirmation present
	 */
	public boolean confirmationPresent(boolean print) {
		boolean isPresent = false;
		try {
			driver.switchTo().alert();
			isPresent = true;
		} catch (NoAlertPresentException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected("Checking for confirmation to be present");
		}
		return isPresent;
	}

	/**
	 * a method to check if a prompt is present
	 *
	 * @param print
	 *            - whether or not to print out this wait statement
	 * @return boolean - is a prompt present
	 */
	public boolean promptPresent(boolean print) {
		boolean isPresent = false;
		try {
			driver.switchTo().alert();
			isPresent = true;
		} catch (NoAlertPresentException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected("Checking for prompt to be present");
		}
		return isPresent;
	}

	/**
	 * a method to determine if a cookie by a particular name is present or not
	 *
	 * @param expectedCookieName
	 *            - the name of the cookie
	 * @return boolean - if the cookie is present
	 */
	public boolean cookiePresent(String expectedCookieName) {
		boolean isCookiePresent = false;
		if (get.cookie(expectedCookieName) != null) {
			isCookiePresent = true;
		}
		return isCookiePresent;
	}

	/**
	 * the generic selenium is text present functionality implemented
	 *
	 * @param expectedText
	 *            - the text we are expecting to be present on the page
	 * @return boolean - whether or not the text is present
	 */
	public boolean textPresent(String expectedText) {
		try {
			String bodyText = driver.findElement(By.tagName("body")).getText();
			return bodyText.contains(expectedText);
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	/**
	 * a specialized selenium is text present in the page source functionality
	 *
	 * @param expectedText
	 *            - the text we are expecting to be present on the page
	 * @return boolean - whether or not the text is present
	 */
	public boolean textPresentInSource(String expectedText) {
		try {
			return driver.getPageSource().contains(expectedText);
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	/**
	 * a method for checking if an element is present
	 *
	 * @param element
	 *            - the element to be checked
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementPresent(Element element, boolean print) {
		element.setDriver(driver);
		boolean isPresent = false;
		try {
			element.getWebElement().getText();
			isPresent = true;
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected(CHECKING + element.prettyOutput() + PRESENT);
		}
		return isPresent;
	}

	/**
	 * a method for checking if an element is an input; it needs to be an input,
	 * select, or textarea
	 *
	 * @param element
	 *            - the element to be checked
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementInput(Element element, boolean print) {
		element.setDriver(driver);
		boolean isInput = false;
		try {
			WebElement webElement = element.getWebElement();
			if ("input".equalsIgnoreCase(webElement.getTagName())
					|| "textarea".equalsIgnoreCase(webElement.getTagName())
					|| "select".equalsIgnoreCase(webElement.getTagName())) {
				isInput = true;
			}
		} catch (NoSuchElementException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected(CHECKING + element.prettyOutput() + " to be an input element");
		}
		return isInput;
	}

	/**
	 * a method for checking if an element is enabled
	 *
	 * @param element
	 *            - the element to be checked
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is present or not
	 */
	public boolean elementEnabled(Element element, boolean print) {
		element.setDriver(driver);
		boolean isEnabled = false;
		try {
			isEnabled = element.getWebElement().isEnabled();
		} catch (NoSuchElementException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected(CHECKING + element.prettyOutput() + ENABLED);
		}
		return isEnabled;
	}

	/**
	 * a method for checking if an element is checked
	 *
	 * @param element
	 *            - the element to be checked
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is checked or not
	 */
	public boolean elementChecked(Element element, boolean print) {
		element.setDriver(driver);
		boolean isChecked = false;
		try {
			isChecked = element.getWebElement().isSelected();
		} catch (NoSuchElementException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected(CHECKING + element.prettyOutput() + " to be checked");
		}
		return isChecked;
	}

	/**
	 * a method for checking if an element is displayed
	 *
	 * @param element
	 *            - the element to be checked
	 * @param print
	 *            - whether or not to printout the action
	 * @return boolean: whether the element is displayed or not
	 */
	public boolean elementDisplayed(Element element, boolean print) {
		element.setDriver(driver);
		boolean isDisplayed = false;
		try {
			isDisplayed = element.getWebElement().isDisplayed();
		} catch (NoSuchElementException e) {
			log.error(e);
		}
		if (print) {
			file.recordExpected(CHECKING + element.prettyOutput() + DISPLAYED);
		}
		return isDisplayed;
	}

	/**
	 * determine if something is selected from a drop down menu
	 * 
	 * @param element
	 *            - the element to be checked
	 * @param print
	 *            - whether or not to printout the action
	 * @return Boolean: was something selected in the drop down
	 */
	public boolean somethingSelected(Element element, boolean print) {
		element.setDriver(driver);
		boolean isSelected = false;
		if (elementInput(element, false)) {
			WebElement webElement = element.getWebElement();
			if ("input".equalsIgnoreCase(webElement.getTagName())) {
				isSelected = webElement.isSelected();
			} else if ("select".equalsIgnoreCase(webElement.getTagName())) {
				isSelected = get.selectedValues(element).length > 0;
			}
		}
		if (print) {
			file.recordExpected(CHECKING + element.prettyOutput() + SELECTED);
		}
		return isSelected;
	}
}
