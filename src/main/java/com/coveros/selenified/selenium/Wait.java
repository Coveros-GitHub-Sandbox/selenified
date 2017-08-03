package com.coveros.selenified.selenium;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.log4testng.Logger;

import com.coveros.selenified.output.Assert.Result;
import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.General;

public class Wait {

    private static final Logger log = Logger.getLogger(General.class);

    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private WebDriver driver;

    // the is class to determine if something exists, so the wait can end
    private Is is;

    // constants
    private static final String SECONDS = " seconds";
    private static final String WAITED = "Waited ";
    private static final double WAITFOR = 5;
    private static final String UPTO = "Wait up to ";
    private static final String WAITING = "After waiting ";
    private static final String SECONDS_FOR = " seconds for ";

    private static final String PRESENT = " to be present";
    private static final String DISPLAYED = " to be displayed";
    private static final String ENABLED = " to be enabled";

    public Wait(WebDriver driver, OutputFile file) {
        this.driver = driver;
        this.file = file;
        this.is = new Is(driver, file);
    }

    // ///////////////////////////////////////
    // waiting functionality
    // ///////////////////////////////////////

    /**
     * a method for allowing Selenium to pause for a set amount of time
     *
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int wait(double seconds) {
        String action = "Wait " + seconds + SECONDS;
        String expected = WAITED + seconds + SECONDS;
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            log.error(e);
            file.recordAction(action, expected, "Failed to wait " + seconds + SECONDS + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            Thread.currentThread().interrupt();
            return 1;
        }
        file.recordAction(action, expected, WAITED + seconds + SECONDS, Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until an element is present for a maximum of 5
     * seconds
     * 
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementPresent(Element element) {
        return forElementPresent(element, WAITFOR);
    }

    /**
     * a method for waiting until an element is present for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementPresent(Locator type, String locator) {
        return forElementPresent(new Element(type, locator), WAITFOR);
    }

    /**
     * a method for waiting until an element is present
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementPresent(Locator type, String locator, double seconds) {
        return forElementPresent(new Element(type, locator), seconds);
    }

    /**
     * a method for waiting until an element is present for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementPresent(Locator type, String locator, int elementMatch) {
        return forElementPresent(new Element(type, locator, elementMatch), WAITFOR);
    }

    /**
     * a method for waiting until an element is present for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementPresent(Locator type, String locator, int elementMatch, double seconds) {
        return forElementPresent(new Element(type, locator, elementMatch), seconds);
    }

    /**
     * a method for waiting until an element is no longer present for a maximum
     * of 5 seconds
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotPresent(Element element) {
        return forElementNotPresent(element, WAITFOR);
    }

    /**
     * a method for waiting until an element is no longer present for a maximum
     * of 5 seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotPresent(Locator type, String locator) {
        return forElementNotPresent(new Element(type, locator), WAITFOR);
    }

    /**
     * a method for waiting until an element is no longer present
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotPresent(Locator type, String locator, double seconds) {
        return forElementNotPresent(new Element(type, locator), seconds);
    }

    /**
     * a method for waiting until an element is no longer present for a maximum
     * of 5 seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotPresent(Locator type, String locator, int elementMatch) {
        return forElementNotPresent(new Element(type, locator, elementMatch), WAITFOR);
    }

    /**
     * a method for waiting until an element is no longer present for a maximum
     * of 5 seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotPresent(Locator type, String locator, int elementMatch, double seconds) {
        return forElementNotPresent(new Element(type, locator, elementMatch), seconds);
    }

    /**
     * a method for waiting until an element is displayed for a maximum of 5
     * seconds
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementDisplayed(Element element) {
        return forElementDisplayed(element, WAITFOR);
    }

    /**
     * a method for waiting until an element is displayed for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementDisplayed(Locator type, String locator) {
        return forElementDisplayed(new Element(type, locator), WAITFOR);
    }

    /**
     * a method for waiting until an element is displayed
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementDisplayed(Locator type, String locator, double seconds) {
        return forElementDisplayed(new Element(type, locator), seconds);
    }

    /**
     * a method for waiting until an element is displayed for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementDisplayed(Locator type, String locator, int elementMatch) {
        return forElementDisplayed(new Element(type, locator, elementMatch), WAITFOR);
    }

    /**
     * a method for waiting until an element is displayed for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementDisplayed(Locator type, String locator, int elementMatch, double seconds) {
        return forElementDisplayed(new Element(type, locator, elementMatch), seconds);
    }

    /**
     * a method for waiting until an element is not displayed for a maximum of 5
     * seconds
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotDisplayed(Element element) {
        return forElementNotDisplayed(element, WAITFOR);
    }

    /**
     * a method for waiting until an element is not displayed for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotDisplayed(Locator type, String locator) {
        return forElementNotDisplayed(new Element(type, locator), WAITFOR);
    }

    /**
     * a method for waiting until an element is not displayed
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotDisplayed(Locator type, String locator, double seconds) {
        return forElementNotDisplayed(new Element(type, locator), seconds);
    }

    /**
     * a method for waiting until an element is not displayed for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotDisplayed(Locator type, String locator, int elementMatch) {
        return forElementNotDisplayed(new Element(type, locator, elementMatch), WAITFOR);
    }

    /**
     * a method for waiting until an element is not displayed for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotDisplayed(Locator type, String locator, int elementMatch, double seconds) {
        return forElementNotDisplayed(new Element(type, locator, elementMatch), seconds);
    }

    /**
     * a method for waiting until an element is enabled for a maximum of 5
     * seconds
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementEnabled(Element element) {
        return forElementEnabled(element, WAITFOR);
    }

    /**
     * a method for waiting until an element is enabled for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementEnabled(Locator type, String locator) {
        return forElementEnabled(new Element(type, locator), WAITFOR);
    }

    /**
     * a method for waiting until an element is enabled
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementEnabled(Locator type, String locator, double seconds) {
        return forElementEnabled(new Element(type, locator), seconds);
    }

    /**
     * a method for waiting until an element is enabled for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementEnabled(Locator type, String locator, int elementMatch) {
        return forElementEnabled(new Element(type, locator, elementMatch), WAITFOR);
    }

    /**
     * a method for waiting until an element is enabled for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementEnabled(Locator type, String locator, int elementMatch, double seconds) {
        return forElementEnabled(new Element(type, locator, elementMatch), seconds);
    }

    /**
     * a method for waiting until an element is not enabled for a maximum of 5
     * seconds
     *
     * @param element
     *            - the element to be waited for
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotEnabled(Element element) {
        return forElementNotEnabled(element, WAITFOR);
    }

    /**
     * a method for waiting until an element is not enabled for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotEnabled(Locator type, String locator) {
        return forElementNotEnabled(new Element(type, locator), WAITFOR);
    }

    /**
     * a method for waiting until an element is not enabled
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotEnabled(Locator type, String locator, double seconds) {
        return forElementNotEnabled(new Element(type, locator), seconds);
    }

    /**
     * a method for waiting until an element is not enabled for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotEnabled(Locator type, String locator, int elementMatch) {
        return forElementNotEnabled(new Element(type, locator, elementMatch), WAITFOR);
    }

    /**
     * a method for waiting until an element is not enabled for a maximum of 5
     * seconds
     *
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param elementMatch
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotEnabled(Locator type, String locator, int elementMatch, double seconds) {
        return forElementNotEnabled(new Element(type, locator, elementMatch), seconds);
    }

    /**
     * a method for waiting up to 5 seconds for an alert is present
     *
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forAlertPresent() {
        return forAlertPresent(WAITFOR);
    }

    /**
     * a method for waiting up to 5 seconds for a confirmation is present
     *
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forConfirmationPresent() {
        return forConfirmationPresent(WAITFOR);
    }

    /**
     * a method for waiting up to 5 seconds for a prompt is present
     *
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forPromptPresent() {
        return forPromptPresent(WAITFOR);
    }

    ///////////////////////////////////////////////////
    // Our actual full implementation of the above overloaded methods
    ///////////////////////////////////////////////////

    /**
     * a method for waiting until an element is present
     * 
     * @param element
     *            - the element to be waited for
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forElementPresent(Element element, double seconds) {
        element.setDriver(driver);
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + PRESENT;
        String expected = element.prettyOutputStart() + "is present";
        // wait for up to XX seconds for the error message
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (System.currentTimeMillis() < end) {
            try { // If results have been returned, the results are displayed in
                    // a drop down.
                element.getWebElement().getText();
                break;
            } catch (NoSuchElementException | StaleElementReferenceException e) {
                log.error(e);
            }
        }
        double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
        timetook = timetook / 1000;
        if (!is.elementPresent(element, false)) {
            file.recordAction(action, expected,
                    WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is not present", Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + PRESENT,
                Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until an element is no longer present
     *
     * @param element
     *            - the element to be waited for
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotPresent(Element element, double seconds) {
        element.setDriver(driver);
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + " to not be present";
        String expected = element.prettyOutputStart() + " is not present";
        // wait for up to XX seconds for the error message
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (System.currentTimeMillis() < end) {
            if (!is.elementPresent(element, false)) {
                break;
            }
        }
        double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
        timetook = timetook / 1000;
        if (is.elementPresent(element, false)) {
            file.recordAction(action, expected,
                    WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is still present", Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected,
                WAITED + timetook + SECONDS_FOR + element.prettyOutput() + " to not be present", Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until an element is displayed
     *
     * @param element
     *            - the element to be waited for
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int forElementDisplayed(Element element, double seconds) {
        element.setDriver(driver);
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + DISPLAYED;
        String expected = element.prettyOutputStart() + " is displayed";
        double start = System.currentTimeMillis();
        if (!is.elementPresent(element, false)) {
            int success = forElementPresent(element, seconds);
            if (success == 1) {
                return success;
            }
        }
        WebElement webElement = element.getWebElement();
        if (!webElement.isDisplayed()) {
            // wait for up to XX seconds
            double end = System.currentTimeMillis() + (seconds * 1000);
            while (System.currentTimeMillis() < end) {
                if (webElement.isDisplayed()) {
                    break;
                }
            }
        }
        double timetook = (System.currentTimeMillis() - start) / 1000;
        if (!webElement.isDisplayed()) {
            file.recordAction(action, expected,
                    WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is not displayed", Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + DISPLAYED,
                Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until an element is not displayed
     *
     * @param element
     *            - the element to be waited for
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotDisplayed(Element element, double seconds) {
        element.setDriver(driver);
        // this might fail if the element disappears completely
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + " to not be displayed";
        String expected = element.prettyOutputStart() + " is not displayed";
        double start = System.currentTimeMillis();
        WebElement webElement = element.getWebElement();
        if (webElement.isDisplayed()) {
            // wait for up to XX seconds
            double end = System.currentTimeMillis() + (seconds * 1000);
            while (System.currentTimeMillis() < end) {
                if (!webElement.isDisplayed()) {
                    break;
                }
            }
        }
        double timetook = (System.currentTimeMillis() - start) / 1000;
        if (webElement.isDisplayed()) {
            file.recordAction(action, expected,
                    WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is still displayed", Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected,
                WAITED + timetook + SECONDS_FOR + element.prettyOutput() + " to not be displayed", Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until an element is enabled
     *
     * @param element
     *            - the element to be waited for
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int forElementEnabled(Element element, double seconds) {
        element.setDriver(driver);
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + ENABLED;
        String expected = element.prettyOutputStart() + " is enabled";
        double start = System.currentTimeMillis();
        if (!is.elementPresent(element, false)) {
            forElementPresent(element, seconds);
        }
        if (!is.elementEnabled(element, false)) {
            WebElement webElement = element.getWebElement();
            // wait for up to XX seconds for the error message
            double end = System.currentTimeMillis() + (seconds * 1000);
            while (System.currentTimeMillis() < end) {
                // If results have been returned, the results are displayed
                // in a drop down.
                if (webElement.isEnabled()) {
                    break;
                }
            }
        }
        double timetook = (System.currentTimeMillis() - start) / 1000;
        if (!is.elementEnabled(element, false)) {
            file.recordAction(action, expected,
                    WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is not enabled", Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, WAITED + timetook + SECONDS_FOR + element.prettyOutput() + ENABLED,
                Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until an element is not enabled
     *
     * @param element
     *            - the element to be waited for
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer - the number of errors encountered while executing these
     *         steps
     */
    public int forElementNotEnabled(Element element, double seconds) {
        element.setDriver(driver);
        // this might fail if the element is no longer present
        String action = UPTO + seconds + SECONDS_FOR + element.prettyOutput() + " to not be enabled";
        String expected = element.prettyOutputStart() + " is not enabled";
        double start = System.currentTimeMillis();
        WebElement webElement = element.getWebElement();
        if (webElement.isEnabled()) {
            // wait for up to XX seconds
            double end = System.currentTimeMillis() + (seconds * 1000);
            while (System.currentTimeMillis() < end) {
                if (!webElement.isEnabled()) {
                    break;
                }
            }
        }
        double timetook = (System.currentTimeMillis() - start) / 1000;
        if (webElement.isEnabled()) {
            file.recordAction(action, expected,
                    WAITING + timetook + SECONDS_FOR + element.prettyOutput() + " is still enabled", Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected,
                WAITED + timetook + SECONDS_FOR + element.prettyOutput() + " to not be enabled", Result.SUCCESS);
        return 0;
    }

    /**
     * waits for a popup to be present, and then returns the amount of time it
     * waited
     * 
     * @param seconds
     *            - maximum time to wait in seconds
     * @return double - the total time waited
     */
    private double forPopup(double seconds) {
        // wait for up to XX seconds for the error message
        double end = System.currentTimeMillis() + (seconds * 1000);
        while (System.currentTimeMillis() < end) {
            try { // If results have been returned, the results are displayed in
                    // a drop down.
                driver.switchTo().alert();
                break;
            } catch (NoAlertPresentException e) {
                log.error(e);
            }
        }
        double timetook = Math.min((seconds * 1000) - (end - System.currentTimeMillis()), seconds * 1000);
        return timetook / 1000;
    }

    /**
     * a method for waiting until an alert is present
     *
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forAlertPresent(double seconds) {
        String action = UPTO + seconds + " seconds for an alert to be present";
        String expected = "An alert is present";
        double timetook = forPopup(seconds);
        if (!is.alertPresent()) {
            file.recordAction(action, expected, WAITING + timetook + " seconds, an alert is not present",
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, WAITED + timetook + " seconds for an alert to be present", Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until a confirmation is present
     *
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forConfirmationPresent(double seconds) {
        String action = UPTO + seconds + " seconds for a confirmation to be present";
        String expected = "A confirmation is present";
        double timetook = forPopup(seconds);
        if (!is.confirmationPresent()) {
            file.recordAction(action, expected, WAITING + timetook + " seconds, a confirmation is not present",
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, WAITED + timetook + " seconds for a confirmation to be present",
                Result.SUCCESS);
        return 0;
    }

    /**
     * a method for waiting until a prompt is present
     *
     * @param seconds
     *            - the number of seconds to wait
     * @return Integer: the number of errors encountered while executing these
     *         steps
     */
    public int forPromptPresent(double seconds) {
        String action = UPTO + seconds + " seconds for a prompt to be present";
        String expected = "A prompt is present";
        double timetook = forPopup(seconds);
        if (!is.promptPresent()) {
            file.recordAction(action, expected, WAITING + timetook + " seconds, a prompt is not present",
                    Result.FAILURE);
            file.addError();
            return 1;
        }
        file.recordAction(action, expected, WAITED + timetook + " seconds for a prompt to be present", Result.SUCCESS);
        return 0;
    }
}