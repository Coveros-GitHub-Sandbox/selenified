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

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.coveros.selenified.exceptions.InvalidLocatorTypeException;
import com.coveros.selenified.selenium.Selenium.Locator;

public class Element {

	private Locator type;
	private String locator;
	private int elementMatch = 0;
	
	private WebDriver driver;

	public Element(Locator type, String locator) {
		this.setType(type);
		this.setLocator(locator);
	}
	
	public Element(Locator type, String locator, int elementMatch) {
		this.setType(type);
		this.setLocator(locator);
		this.setElementMatch(elementMatch);
	}

	public void setType(Locator type) {
		this.type = type;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}
	
	public void setElementMatch(int elementMatch) {
		this.elementMatch = elementMatch;
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public Locator getType() {
		return type;
	}

	public String getLocator() {
		return locator;
	}
	
	public int getElementMatch() {
		return elementMatch;
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
	public String prettyOutputStart() {
		return "Element with <i>" + type.toString() + "</i> of <i>" + locator + "</i>";
	}
	
	public String prettyOutputLowercase() {
		String output = prettyOutputStart();
		return Character.toLowerCase(output.charAt(0)) + output.substring(1);
	}
	
	public String prettyOutput() {
		return " " + prettyOutputLowercase() + " ";
	}
	
	public String prettyOutputEnd() {
		return prettyOutputLowercase() + ".";
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
	private By defineByElement() {
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
			byElement = By.id("");
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
	public WebElement getWebElement() {
		List<WebElement> elements = getWebElements();
		if (elements.size() > elementMatch) {
			return elements.get(elementMatch);
		}
		return driver.findElement(defineByElement());
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
	public List<WebElement> getWebElements() {
		return driver.findElements(defineByElement());
	}

	/**
	 * a method to determine how many elements match the selector
	 * 
	 * @param type
	 *            - the locator type e.g. Locator.id, Locator.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: how many element match the selector
	 */
	public int getElementMatchCount() {
		return getWebElements().size();
	}
}