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

package com.coveros.selenified.application;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.log4testng.Logger;

import com.coveros.selenified.OutputFile;
import com.coveros.selenified.OutputFile.Result;
import com.coveros.selenified.Browser;
import com.coveros.selenified.Locator;
import com.coveros.selenified.element.Element;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.utilities.TestSetup;

/**
 * App is an instance of the browser based application that is under test.
 * 
 * Pages should be build out of this object (if using the page object model
 * (POM)), so that several pages make up an app. Within each page, multiple
 * elements should be created. In this way, we can act on our app, page, or
 * element directly. If you choose not to use the POM, then simply build your
 * elements directly out of your app.
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class App {

    private static final Logger log = Logger.getLogger(TestSetup.class);

    // this will be the name of the file we write all commands out to
    private OutputFile file;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private WebDriver driver;

    // what browsers are we interested in implementing
    // this is the browser that we are using
    private Browser browser;
    private DesiredCapabilities capabilities;

    // keeps track of the initial window open
    private String parentWindow;

    // the is class to determine if something exists
    private Is is;
    // the wait class to determine if we need to wait for something
    private WaitFor waitFor;
    // the get class to retrieve information about the app
    private Get get;
    // the assert class to verify information about the app
    private Assert azzert;

    // constants
    private static final String WAITED = "Waited ";
    private static final String SECONDS = " seconds";
    private static final String AVAILABLE = "</b> is available and selected";
    private static final String NOTSELECTED = "</b> was unable to be selected";
    private static final String FRAME = "Frame <b>";

    /**
     * Sets up the app object. Browser, and Output are defined here, which will
     * control actions and all logging and records
     *
     * @param browser
     *            - the Browser we are running the test on
     * @param capabilities
     *            - what browser capabilities are desired
     * @param file
     *            - the TestOutput file. This is provided by the
     *            SeleniumTestBase functionality
     * @throws InvalidBrowserException
     *             If a browser that is not one specified in the
     *             Selenium.Browser class is used, this exception will be thrown
     * @throws MalformedURLException
     *             If the provided hub address isn't a URL, this exception will
     *             be thrown
     */
    public App(Browser browser, DesiredCapabilities capabilities, OutputFile file)
            throws InvalidBrowserException, MalformedURLException {
        if (browser == null) {
            this.browser = Browser.NONE;
        } else {
            this.browser = browser;
        }

        if (capabilities == null) {
            this.capabilities = new DesiredCapabilities();
        } else {
            this.capabilities = capabilities;
        }
        this.file = file;

        // if we want to test remotely
        if (System.getProperty("hub") != null) {
            driver = new RemoteWebDriver(new URL(System.getProperty("hub") + "/wd/hub"), this.capabilities);
        } else {
            this.capabilities.setJavascriptEnabled(true);
            driver = TestSetup.setupDriver(this.browser, this.capabilities);
        }

        is = new Is(driver);
        waitFor = new WaitFor(driver, file);
        get = new Get(driver);
        azzert = new Assert(this, file);
    }

    /**
     * setups a new element which is located on the page
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @return Element: a page element to interact with
     */
    public Element newElement(Locator type, String locator) {
        return new Element(driver, file, type, locator);
    }

    /**
     * setups a new element which is located on the page
     * 
     * @param type
     *            - the locator type e.g. Locator.id, Locator.xpath
     * @param locator
     *            - the locator string e.g. login, //input[@id='login']
     * @param match
     *            - if there are multiple matches of the selector, this is which
     *            match (starting at 0) to interact with
     * @return Element: a page element to interact with
     */
    public Element newElement(Locator type, String locator, int match) {
        return new Element(driver, file, type, locator, match);
    }

    ///////////////////////////////////////////////////////
    // instantiating our additional action classes for further use
    ///////////////////////////////////////////////////////

    /**
     * Checks information about the app in general, not specific to any
     * particular page or element. A boolean is always returning, indicating if
     * an object is present or not
     */
    public Is is() {
        return is;
    }

    /**
     * Performs dynamic waits on the app in general, until a particular
     * condition of the application is met, not one for a particular page or
     * element. Nothing is ever returned. The default wait is 5 seconds, but can
     * be overridden. If the condition is not met in the allotted time, still
     * nothing is returned, but an error is logged
     */
    public WaitFor waitFor() {
        return waitFor;
    }

    /**
     * Retrieves information about the app in general, not specific to any
     * particular page or element. If an object isn't present, null will be
     * returned
     */
    public Get get() {
        return get;
    }

    /**
     * Will handle all verifications performed on the actual application itself.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented capabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests.
     */
    public Assert azzert() {
        return azzert;
    }

    ////////////////////////////////////////////
    // Ability to interact with important elements
    ////////////////////////////////////////////

    /**
     * Retrieves the Selenium driver instance
     *
     * @return WebDriver: access to the driver controlling the browser via
     *         webdriver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Retrieves the browser being used for this particular test
     *
     * @return Browser: the enum of the browser
     */
    public Browser getBrowser() {
        return browser;
    }

    /**
     * Retrieves the output file responsible for logging all actions and
     * assertions associated with this particular test
     *
     * @return OutputFile: the file recording all actions
     */
    public OutputFile getOutputFile() {
        return file;
    }

    /**
     * Retrieves the browser (and potentially device) capabilities setup for
     * this particular test
     *
     * @return capabilities: the listing of set capabilities
     */
    public DesiredCapabilities getCapabilities() {
        return capabilities;
    }

    /**
     * Ends the current Selenium driver instance, which will end the test. No
     * additional actions or asserts can be performed after this, as the browser
     * will be terminated as well
     */
    public void killDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    ////////////////////////////////////////////
    // perform actions on the page itself, not the elements
    ////////////////////////////////////////////

    /**
     * Pauses the test for a set amount of time
     *
     * @param seconds
     *            - the number of seconds to wait
     */
    public void wait(double seconds) {
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
            return;
        }
        file.recordAction(action, expected, WAITED + seconds + SECONDS, Result.SUCCESS);
    }

    /**
     * Navigates to a new url
     *
     * @param url
     *            - the URL to navigate to
     */
    public void goToURL(String url) {
        String action = "Loading " + url;
        String expected = "Loaded " + url;
        double start = System.currentTimeMillis();
        try {
            driver.get(url);
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "Fail to Load " + url + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            return;
        }
        double timetook = System.currentTimeMillis() - start;
        timetook = timetook / 1000;
        file.recordAction(action, expected, "Loaded " + url + " in " + timetook + SECONDS, Result.SUCCESS);
    }

    /**
     * Takes a full screenshot of the entire page
     *
     * @param imageName
     *            - the name of the image typically generated via functions from
     *            TestOutput.generateImageName
     */
    public void takeScreenshot(String imageName) {
        if (browser == Browser.HTMLUNIT || browser == Browser.NONE) {
            return;
        }
        try {
            // take a screenshot
            File srcFile;
            if (System.getProperty("hubAddress") != "LOCAL") {
                WebDriver augemented = new Augmenter().augment(driver);
                srcFile = ((TakesScreenshot) augemented).getScreenshotAs(OutputType.FILE);
            } else {
                srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            }
            // now we need to save the file
            FileUtils.copyFile(srcFile, new File(imageName));
        } catch (Exception e) {
            log.error("Error taking screenshot: " + e);
        }
    }

    /**
     * Sends a key combination both as control and command (PC and Mac
     * compatibile)
     * 
     * @param action
     *            - the action occurring
     * @param expected
     *            - the expected result
     * @param fail
     *            - the failed result
     * @param key
     *            - what key to send along with control and/or command
     * @return Boolean: returns a true if the keys were successfully sent, a
     *         false if they were not
     */
    private boolean sendControlAndCommand(String action, String expected, String fail, String key) {
        try {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + key);
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.COMMAND + key);
        } catch (Exception e) {
            file.recordAction(action, expected, fail + e.getMessage(), Result.FAILURE);
            file.addError();
            log.error(e);
            return false;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
        return true;
    }

    /**
     * Opens a new tab, and have it selected. Note, no content will be present
     * on this new tab, use the goToURL method to open load some content
     * 
     * @return Boolean: returns a true if a tab was successfully opened, a false
     *         if it was not.
     */
    public boolean openTab() {
        return sendControlAndCommand("Opening new tab", "New tab is opened", "New tab was unable to be opened. ", "t");
    }

    /**
     * Opens a new tab, and have it selected. The page provided will be loaded
     * 
     * @param url
     *            - the url to load once the new tab is opened and selected
     */
    public void openTab(String url) {
        if (!openTab()) {
            return;
        }
        goToURL(url);
    }

    /**
     * Switches to the next available tab. If the last tab is already selected,
     * it will loop back to the first tab. This is an alternative to
     * switchToNewWindow, as this works better for some systems and environments
     * that others.
     */
    public void switchNextTab() {
        String action = "Switching to next tab ";
        String expected = "Next tab <b>" + AVAILABLE;
        try {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.PAGE_DOWN));
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.COMMAND, Keys.PAGE_DOWN));
        } catch (Exception e) {
            file.recordAction(action, expected, "Next tab <b>" + NOTSELECTED + ". " + e.getMessage(), Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Switch to the previous available tab. If the first tab is already
     * selected, it will loop back to the last tab. This is an alternative to
     * switchToNewWindow, as this works better for some systems and environments
     * that others.
     */
    public void switchPreviousTab() {
        String action = "Switching to previous tab ";
        String expected = "Previous tab <b>" + AVAILABLE;
        try {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.PAGE_UP));
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.COMMAND, Keys.PAGE_UP));
        } catch (Exception e) {
            file.recordAction(action, expected, "Previous tab <b>" + NOTSELECTED + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Closes the current tab. Note that if this is the only tab open, this will
     * end the test. No additional actions or asserts can be performed after
     * this, as the browser will be terminated as well
     */
    public void closeTab() {
        sendControlAndCommand("Closing currently open tab", "Tab is closed", "Tab was unable to be closed. ", "w");
    }

    /**
     * Go back one page in the current test's browser history
     */
    public void goBack() {
        String action = "Going back one page";
        String expected = "Previous page from browser history is loaded";
        try {
            driver.navigate().back();
        } catch (Exception e) {
            file.recordAction(action, expected, "Browser was unable to go back one page. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Go forward one page in the current test's browser history
     */
    public void goForward() {
        String action = "Going forward one page";
        String expected = "Next page from browser history is loaded";
        try {
            driver.navigate().forward();
        } catch (Exception e) {
            file.recordAction(action, expected, "Browser was unable to go forward one page. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Reloads the current page
     */
    public void refresh() {
        String action = "Reloading current page";
        String expected = "Page is refreshed";
        try {
            driver.navigate().refresh();
        } catch (Exception e) {
            file.recordAction(action, expected, "Browser was unable to be refreshed. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Reloads the current page while clearing the cache. This only works for
     * certain browsers, as the command/control and F5 key combination is used
     * to perform the action. Note that each time a test is run, nothing is
     * currently in the cache, as each test is run in a completely
     * new/fresh/clean session
     */
    public void refreshHard() {
        String action = "Reloading current page while clearing the cache";
        String expected = "Cache is cleared, and the page is refreshed";
        try {
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.F5));
            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.COMMAND, Keys.F5));
        } catch (Exception e) {
            file.recordAction(action, expected,
                    "There was a problem clearing the cache and reloading the page. " + e.getMessage(), Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Adds a cookie to the application for this particular test
     * 
     * @param cookie
     *            - the details of the cookie to set
     */
    public void setCookie(Cookie cookie) {
        String domain = cookie.getDomain();
        Date expiry = cookie.getExpiry();
        String name = cookie.getName();
        String path = cookie.getPath();
        String value = cookie.getValue();

        String action = "Setting up cookie with attributes:<div><table><tbody><tr><td>Domain</td><td>" + domain
                + "</tr><tr><td>Expiration</td><td>" + expiry.toString() + "</tr><tr><td>Name</td><td>" + name
                + "</tr><tr><td>Path</td><td>" + path + "</tr><tr><td>Value</td><td>" + value
                + "</tr></tbody></table></div><br/>";
        String expected = "Cookie is added";
        try {
            driver.manage().addCookie(cookie);
        } catch (Exception e) {
            file.recordAction(action, expected, "Unable to add cookie. " + e.getMessage(), Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Deletes a stored cookie, indicated by the cookieName for this particular
     * test. If the cookie by the provided name isn't present, than an error
     * will be logged and recorded
     * 
     * @param cookieName
     *            - the name of the cookie to delete
     */
    public void deleteCookie(String cookieName) {
        String action = "Deleting cookie <i>" + cookieName + "</i>";
        String expected = "Cookie <i>" + cookieName + "</i> is removed";
        Cookie cookie = driver.manage().getCookieNamed(cookieName);
        if (cookie == null) {
            file.recordAction(action, expected,
                    "Unable to remove cookie <i>" + cookieName + "</i> as it doesn't exist.", Result.FAILURE);
            file.addError();
            return;
        }
        try {
            driver.manage().deleteCookieNamed(cookieName);
        } catch (Exception e) {
            file.recordAction(action, expected, "Unable to remove cookie <i>" + cookieName + "</i>. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Delete all stored cookies for this particular test
     */

    public void deleteAllCookies() {
        String action = "Deleting all cookies";
        String expected = "All cookies are removed";
        try {
            driver.manage().deleteAllCookies();
        } catch (Exception e) {
            file.recordAction(action, expected, "Unable to remove all cookies. " + e.getMessage(), Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Maximizes the current window
     */

    public void maximize() {
        String action = "Maximizing browser";
        String expected = "Browser is maximized";
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            file.recordAction(action, expected, "Browser was unable to be maximized. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * An custom script to scroll to a given position on the page, using
     * javascript. If the browser being used doesn't support javascript, or the
     * page isn't long enough to support scrolling to the desired position, than
     * an error will be logged and recorded
     *
     * @param desiredPosition
     *            - the position on the page to scroll to
     */
    public void scroll(int desiredPosition) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        Long initialPosition = (Long) jse.executeScript("return window.scrollY;");

        String action = "Scrolling page from " + initialPosition + " to " + desiredPosition;
        String expected = "Page is now set at position " + desiredPosition;

        jse.executeScript("window.scrollBy(0, " + desiredPosition + ")");

        Long newPosition = (Long) jse.executeScript("return window.scrollY;");

        if (newPosition != desiredPosition) {
            file.recordAction(action, expected, "Page is set at position " + newPosition, Result.FAILURE);
            file.addError();
            return; // indicates page didn't scroll properly
        }
        file.recordAction(action, expected, "Page is now set at position " + newPosition, Result.SUCCESS);
    }

    /**
     * Switches to the next window. This is an alternative to switchNextTab or
     * switchPreviousTab, as this works better for some systems and environments
     * that others.
     */

    public void switchToNewWindow() {
        String action = "Switching to the new window";
        String expected = "New window is available and selected";
        try {
            parentWindow = driver.getWindowHandle();
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
        } catch (Exception e) {
            file.recordAction(action, expected, "New window was unable to be selected. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Switches to the originally opened window. This is an alternative to
     * switchNextTab or switchPreviousTab, as this works better for some systems
     * and environments that others.
     */

    public void switchToParentWindow() {
        String action = "Switching back to parent window";
        String expected = "Parent window is available and selected";
        try {
            driver.switchTo().window(parentWindow);
        } catch (Exception e) {
            file.recordAction(action, expected, "Parent window was unable to be selected. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Close the currently selected window. After this window is closed, ensure
     * that focus is shifted to the next window using switchToNewWindow or
     * switchToParentWindow methods. This is an alternative to closeTab, as this
     * works better for some systems and environments that others.
     */

    public void closeCurrentWindow() {
        String action = "Closing currently selected window";
        String expected = "Current window is closed";
        try {
            driver.close();
        } catch (Exception e) {
            file.recordAction(action, expected, "Current window was unable to be closed. " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Select a frame by its (zero-based) index. That is, if a page has three
     * frames, the first frame would be at index 0, the second at index 1 and
     * the third at index 2. Once the frame has been selected, all subsequent
     * calls on the WebDriver interface are made to that frame.
     * 
     * @param frameNumber
     *            - the frame number, starts at 0
     */
    public void selectFrame(int frameNumber) {
        String action = "Switching to frame <b>" + frameNumber + "</b>";
        String expected = FRAME + frameNumber + AVAILABLE;
        try {
            driver.switchTo().frame(frameNumber);
        } catch (Exception e) {
            file.recordAction(action, expected, FRAME + frameNumber + NOTSELECTED + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    /**
     * Select a frame by its name or ID. Frames located by matching name
     * attributes are always given precedence over those matched by ID.
     * 
     * @param frameIdentifier
     *            - the frame name or ID
     */
    public void selectFrame(String frameIdentifier) {
        String action = "Switching to frame <b>" + frameIdentifier + "</b>";
        String expected = FRAME + frameIdentifier + AVAILABLE;
        try {
            driver.switchTo().frame(frameIdentifier);
        } catch (Exception e) {
            file.recordAction(action, expected, FRAME + frameIdentifier + NOTSELECTED + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            log.error(e);
            return;
        }
        file.recordAction(action, expected, expected, Result.SUCCESS);
    }

    //////////////////////////
    // pop-up interactions
    /////////////////////////

    /**
     * Accept (click 'OK' on) whatever popup is present on the page
     * 
     * @param action
     *            - the action occurring
     * @param expected
     *            - the expected result
     * @param popup
     *            - the element we are interacting with
     */
    private void accept(String action, String expected, String popup) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "Unable to click 'OK' on the " + popup + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, "Clicked 'OK' on the " + popup, Result.SUCCESS);
    }

    /**
     * Dismiss (click 'Cancel' on) whatever popup is present on the page
     * 
     * @param action
     *            - the action occurring
     * @param expected
     *            - the expected result
     * @param popup
     *            - the element we are interacting with
     */
    private void dismiss(String action, String expected, String popup) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "Unable to click 'Cancel' on the " + popup + ". " + e.getMessage(),
                    Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, "Clicked 'Cancel' on the " + popup, Result.SUCCESS);
    }

    /**
     * Determines if a confirmation is present or not, and can be interacted
     * with. If it's not present, an indication that the confirmation can't be
     * clicked on is written to the log file
     * 
     * @param action
     *            - the action occurring
     * @param expected
     *            - the expected result
     * 
     * @return Boolean: is a confirmation actually present or not.
     */
    private boolean isConfirmation(String action, String expected) {
        // wait for element to be present
        if (!is.confirmationPresent()) {
            waitFor.confirmationPresent();
        }
        if (!is.confirmationPresent()) {
            file.recordAction(action, expected, "Unable to click confirmation as it is not present", Result.FAILURE);
            return false; // indicates element not present
        }
        return true;
    }

    /**
     * Determines if a prompt is present or not, and can be interacted with. If
     * it's not present, an indication that the confirmation can't be clicked on
     * is written to the log file
     * 
     * @param action
     *            - the action occurring
     * @param expected
     *            - the expected result
     * @param perform
     *            - the action occurring to the prompt
     * @return Boolean: is a prompt actually present or not.
     */
    private boolean isPrompt(String action, String expected, String perform) {
        // wait for element to be present
        if (!is.promptPresent()) {
            waitFor.promptPresent();
        }
        if (!is.promptPresent()) {
            file.recordAction(action, expected, "Unable to " + perform + " prompt as it is not present",
                    Result.FAILURE);
            return false; // indicates element not present
        }
        return true;
    }

    /**
     * Accept (click 'OK' on) an alert box
     */
    public void acceptAlert() {
        String action = "Clicking 'OK' on an alert";
        String expected = "Alert is present to be clicked";
        // wait for element to be present
        if (!is.alertPresent()) {
            waitFor.alertPresent();
        }
        if (!is.alertPresent()) {
            file.recordAction(action, expected, "Unable to click alert as it is not present", Result.FAILURE);
            return; // indicates element not present
        }
        accept(action, expected, "alert");
    }

    /**
     * Accept (click 'OK' on) a confirmation box
     */
    public void acceptConfirmation() {
        String action = "Clicking 'OK' on a confirmation";
        String expected = "Confirmation is present to be clicked";
        if (!isConfirmation(action, expected)) {
            return;
        }
        accept(action, expected, "confirmation");
    }

    /**
     * Dismiss (click 'Cancel' on) a confirmation box
     */
    public void dismissConfirmation() {
        String action = "Clicking 'Cancel' on a confirmation";
        String expected = "Confirmation is present to be clicked";
        if (!isConfirmation(action, expected)) {
            return;
        }
        dismiss(action, expected, "confirmation");
    }

    /**
     * Accept (click 'OK' on) a prompt box
     */
    public void acceptPrompt() {
        String action = "Clicking 'OK' on a prompt";
        String expected = "Prompt is present to be clicked";
        if (!isPrompt(action, expected, "click")) {
            return;
        }
        accept(action, expected, "prompt");
    }

    /**
     * Dismiss (click 'Cancel' on) a prompt box
     */
    public void dismissPrompt() {
        String action = "Clicking 'Cancel' on a prompt";
        String expected = "Prompt is present to be clicked";
        if (!isPrompt(action, expected, "click")) {
            return;
        }
        dismiss(action, expected, "prompt");
    }

    /**
     * Type text into a prompt box
     * 
     * @param text
     *            - the text to type into the prompt
     */
    public void typeIntoPrompt(String text) {
        String action = "Typing text '" + text + "' into prompt";
        String expected = "Prompt is present and enabled to have text " + text + " typed in";
        if (!isPrompt(action, expected, "type into")) {
            return;
        }
        try {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(text);
        } catch (Exception e) {
            log.error(e);
            file.recordAction(action, expected, "Unable to type into prompt. " + e.getMessage(), Result.FAILURE);
            file.addError();
            return;
        }
        file.recordAction(action, expected, "Typed text '" + text + "' into prompt", Result.SUCCESS);
    }
}