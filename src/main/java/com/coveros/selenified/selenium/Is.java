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

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

import com.coveros.selenified.tools.General;
import com.coveros.selenified.tools.OutputFile;

/**
 * Is checks information about the app in general, not specific to any
 * particular page or element. A boolean is always returning, indicating if an
 * object is present or not
 * 
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class Is {

    private static final Logger log = Logger.getLogger(General.class);

    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private WebDriver driver;

    public Is(WebDriver driver, OutputFile file) {
        this.driver = driver;
        this.file = file;
    }

    // ////////////////////////////////////
    // checking element availability
    // ////////////////////////////////////

    /**
     * Determines if an alert is present on the page. This information will not
     * be logged or recorded.
     *
     * @return boolean - is an alert present
     */
    public boolean alertPresent() {
        return alertPresent(false);
    }

    /**
     * Determines if a confirmation is present on the page. This information
     * will not be logged or recorded.
     *
     * @return boolean - is a confirmation present
     */
    public boolean confirmationPresent() {
        return confirmationPresent(false);
    }

    /**
     * Determines if a prompt is present on the page. This information will not
     * be logged or recorded.
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
     * Determines if an alert is present on the page. This information will only
     * be logged and recorded if the print variable is set to true.
     *
     * @param print
     *            - whether or not to print out this check
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
     * Determines if a confirmation is present on the page. This information
     * will only be logged and recorded if the print variable is set to true.
     *
     * @param print
     *            - whether or not to print out this check
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
     * Determines if a prompt is present on the page. This information will only
     * be logged and recorded if the print variable is set to true.
     *
     * @param print
     *            - whether or not to print out this check
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
     * Determines if a cookie exists in the application with the provided
     * cookieName.
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return boolean - if the cookie is present
     */
    public boolean cookiePresent(String expectedCookieName) {
        boolean isCookiePresent = false;
        if (driver.manage().getCookieNamed(expectedCookieName) != null) {
            isCookiePresent = true;
        }
        return isCookiePresent;
    }

    /**
     * Determines if the provided text(s) are on the current page.
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
     * Determines if the provides text is present in the current page source.
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
}