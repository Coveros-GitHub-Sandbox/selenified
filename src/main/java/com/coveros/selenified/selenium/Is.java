package com.coveros.selenified.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.tools.General;

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
        if (driver.manage().getCookieNamed(expectedCookieName) != null) {
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
}