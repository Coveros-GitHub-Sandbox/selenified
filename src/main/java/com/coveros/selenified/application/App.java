/*
 * Copyright 2019 Coveros, Inc.
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

import com.coveros.selenified.Browser;
import com.coveros.selenified.Browser.BrowserName;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.Locator;
import com.coveros.selenified.utilities.Reporter;
import com.coveros.selenified.element.Element;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.log4testng.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * App is an instance of the browser based application that is under test.
 * <p>
 * Pages should be build out of this object (if using the page object model
 * (POM)), so that several pages make up an app. Within each page, multiple
 * elements should be created. In this way, we can act on our app, page, or
 * element directly. If you choose not to use the POM, then simply build your
 * elements directly out of your app.
 *
 * @author Max Saperstone
 * @version 3.1.1
 * @lastupdate 3/7/2019
 */
public class App {

    private static final Logger log = Logger.getLogger(Capabilities.class);

    // this will be the name of the file we write all commands out to
    private final Reporter file;

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private WebDriver driver;

    // what browser are we interested in implementing
    // this is the browser that we are using
    private final Browser browser;
    private final DesiredCapabilities desiredCapabilities;

    // keeps track of the initial window open
    private String parentWindow;

    // the get class to retrieve information about the app
    private Get get;
    // the is class to determine if something exists
    private Is is;
    // the wait class to determine if we need to wait for something
    private WaitFor waitFor;
    // the assert class to verify information about the app
    private Assert azzert;
    // the verify class to verify information about the app
    private Verify verify;

    // constants
    private static final String WAITED = "Waited ";
    private static final String SECONDS = " seconds";
    private static final String AVAILABLE = "</b> is available and selected";
    private static final String NOT_SELECTED = "</b> was unable to be selected";
    private static final String FRAME = "Frame <b>";

    /**
     * Sets up the app object. Browser, and Output are defined here, which will
     * control actions and all logging and records
     *
     * @param capabilities - what browser capabilities are desired
     * @param file         - the TestOutput file. This is provided by the
     *                     SeleniumTestBase functionality
     * @throws InvalidBrowserException If a browser that is not one specified in the
     *                                 Selenium.Browser class is used, this exception will be thrown
     * @throws MalformedURLException   If the provided hub address isn't a URL, this exception will
     *                                 be thrown
     */
    public App(Capabilities capabilities,
               Reporter file) throws InvalidBrowserException, MalformedURLException {
        if (capabilities == null) {
            capabilities = new Capabilities(new Browser("None"));
        }
        this.browser = capabilities.getBrowser();
        this.desiredCapabilities = capabilities.getDesiredCapabilities();
        this.file = file;
        // if we want to test remotely
        if (System.getProperty("hub") != null) {
            driver = new RemoteWebDriver(new URL(System.getProperty("hub") + "/wd/hub"), this.desiredCapabilities);
        } else {
            driver = capabilities.setupDriver();
        }
        is = new Is(driver);
        get = new Get(driver);
        waitFor = new WaitFor(this, file);
        azzert = new Assert(this, file);
        verify = new Verify(this, file);
    }

    /**
     * setups a new element which is located on the page
     *
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @return Element: a page element to interact with
     */
    public Element newElement(Locator type, String locator) {
        return new Element(driver, file, type, locator);
    }

    /**
     * setups a new element which is located on the page
     *
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param match   - if there are multiple matches of the selector, this is which
     *                match (starting at 0) to interact with
     * @return Element: a page element to interact with
     */
    public Element newElement(Locator type, String locator, int match) {
        return new Element(driver, file, type, locator, match);
    }

    /**
     * setups a new element which is located on the page
     *
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param parent  - the parent of the element to be defined
     * @return Element: a page element to interact with
     */
    public Element newElement(Locator type, String locator, Element parent) {
        return new Element(driver, file, type, locator, parent);
    }

    /**
     * setups a new element which is located on the page
     *
     * @param type    - the locator type e.g. Locator.id, Locator.xpath
     * @param locator - the locator string e.g. login, //input[@id='login']
     * @param match   - if there are multiple matches of the selector, this is which
     *                match (starting at 0) to interact with
     * @param parent  - the parent of the element to be defined
     * @return Element: a page element to interact with
     */
    public Element newElement(Locator type, String locator, int match, Element parent) {
        return new Element(driver, file, type, locator, match, parent);
    }

    ///////////////////////////////////////////////////////
    // instantiating our additional action classes for further use
    ///////////////////////////////////////////////////////

    /**
     * Retrieves information about the app in general, not specific to any
     * particular page or element. If an object isn't present, null will be
     * returned
     */
    public Get get() {
        return get;
    }

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
     * Will handle all assertions performed on the actual application itself.
     * These asserts are custom to the framework, and in addition to providing
     * easy object oriented desiredCapabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests. A failed assert will cause
     * the test to immediately stop on the error.
     */
    public Assert azzert() {
        return azzert;
    }

    /**
     * Will handle all verifications performed on the actual application itself.
     * These verifications are custom to the framework, and in addition to providing
     * easy object oriented desiredCapabilities, they take screenshots with each
     * verification to provide additional traceability, and assist in
     * troubleshooting and debugging failing tests. A failed verify will log the error,
     * but continue on anyways, failing at the end due to the issues
     */
    public Verify verify() {
        return verify;
    }

    ////////////////////////////////////////////
    // Ability to interact with important elements
    ////////////////////////////////////////////

    /**
     * Retrieves the Selenium driver instance
     *
     * @return WebDriver: access to the driver controlling the browser via
     * webdriver
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
     * @return Reporter: the file recording all actions
     */
    public Reporter getReporter() {
        return file;
    }

    /**
     * Retrieves the browser (and potentially device) desiredCapabilities setup for
     * this particular test
     *
     * @return desiredCapabilities: the listing of set desiredCapabilities
     */
    public DesiredCapabilities getDesiredCapabilities() {
        return desiredCapabilities;
    }

    /**
     * Ends the current Selenium driver instance, which will end the test. No
     * additional actions or asserts can be performed after this, as the browser
     * will be terminated as well
     */
    public void killDriver() {
        try {
            driver.quit();
        } catch (Exception e) {
            log.warn(e);
        }
    }

    ////////////////////////////////////////////
    // perform actions on the page itself, not the elements
    ////////////////////////////////////////////

    /**
     * Pauses the test for a set amount of time
     *
     * @param seconds - the number of seconds to wait
     */
    public void wait(double seconds) {
        String action = "Wait " + seconds + SECONDS;
        String expected = WAITED + seconds + SECONDS;
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException e) {
            log.warn(e);
            file.fail(action, expected, "Failed to wait " + seconds + SECONDS + ". " + e.getMessage());
            Thread.currentThread().interrupt();
            return;
        }
        file.pass(action, expected, WAITED + seconds + SECONDS);
    }

    /**
     * Navigates to a new url
     *
     * @param url - the URL to navigate to
     */
    public void goToURL(String url) {
        String action = "Loading " + url;
        String expected = "Loaded " + url;
        double start = System.currentTimeMillis();
        try {
            driver.get(url);
        } catch (Exception e) {
            log.warn(e);
            file.fail(action, expected, "Fail to Load " + url + ". " + e.getMessage());
            return;
        }
        double timeTook = System.currentTimeMillis() - start;
        timeTook = timeTook / 1000;
        file.pass(action, expected, "Loaded " + url + " in " + timeTook + SECONDS);
        acceptCertificate();
    }

    /**
     * Takes a full screenshot of the entire page
     *
     * @param imageName - the name of the image typically generated via functions from
     *                  TestOutput.generateImageName
     */
    public void takeScreenshot(String imageName) {
        if (browser.getName() == BrowserName.HTMLUNIT) {
            return;
        }
        try {
            // take a screenshot
            File srcFile;
            if (System.getProperty("hub") != null) {
                WebDriver augemented = new Augmenter().augment(driver);
                srcFile = ((TakesScreenshot) augemented).getScreenshotAs(OutputType.FILE);
            } else {
                srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            }
            // now we need to save the file
            FileUtils.copyFile(srcFile, new File(imageName));
        } catch (Exception e) {
            log.error("IO Error taking screenshot: " + e);
        }
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
            file.fail(action, expected, "Browser was unable to go back one page. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
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
            file.fail(action, expected, "Browser was unable to go forward one page. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
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
            file.fail(action, expected, "Browser was unable to be refreshed. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
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
            driver.findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.F5));
            driver.findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.COMMAND, Keys.F5));
        } catch (Exception e) {
            file.fail(action, expected,
                    "There was a problem clearing the cache and reloading the page. " + e.getMessage());
            log.warn(e);
        }
        file.pass(action, expected, expected);
    }

    /**
     * Adds a cookie to the application for this particular test
     *
     * @param cookie - the details of the cookie to set
     */
    public void setCookie(Cookie cookie) {
        String domain = cookie.getDomain();
        Date expiry = cookie.getExpiry();
        String name = cookie.getName();
        String path = cookie.getPath();
        String value = cookie.getValue();

        String action = "Setting up cookie with attributes:<div><table><tbody><tr><td>Domain</td><td>" + domain +
                "</tr><tr><td>Expiration</td><td>" + expiry.toString() + "</tr><tr><td>Name</td><td>" + name +
                "</tr><tr><td>Path</td><td>" + path + "</tr><tr><td>Value</td><td>" + value +
                "</tr></tbody></table></div><br/>";
        String expected = "Cookie is added";
        try {
            driver.manage().addCookie(cookie);
        } catch (Exception e) {
            file.fail(action, expected, "Unable to add cookie. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
    }

    /**
     * Deletes a stored cookie, indicated by the cookieName for this particular
     * test. If the cookie by the provided name isn't present, than an error
     * will be logged and recorded
     *
     * @param cookieName - the name of the cookie to delete
     */
    public void deleteCookie(String cookieName) {
        String action = "Deleting cookie <i>" + cookieName + "</i>";
        String expected = "Cookie <i>" + cookieName + "</i> is removed";
        try {
            Cookie cookie = driver.manage().getCookieNamed(cookieName);
            if (cookie == null) {
                file.fail(action, expected,
                        "Unable to remove cookie <i>" + cookieName + "</i> as it doesn't exist.");
                return;
            }
            driver.manage().deleteCookieNamed(cookieName);
        } catch (Exception e) {
            file.fail(action, expected, "Unable to remove cookie <i>" + cookieName + "</i>. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
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
            file.fail(action, expected, "Unable to remove all cookies. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
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
            file.fail(action, expected, "Browser was unable to be maximized. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
    }

    /**
     * Resizes the current window to the specified size
     *
     * @param width  - the desired width of the browser
     * @param height - the desired height of the browser
     */
    public void resize(int width, int height) {
        String action = "Resizing browser to " + width + " x " + height;
        String expected = "Browser is resized to " + width + " x " + height;
        try {
            Dimension dimension = new Dimension(width, height);
            driver.manage().window().setSize(dimension);
        } catch (Exception e) {
            file.fail(action, expected, "Browser was unable to be resized. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
    }

    /**
     * An custom script to scroll to a given position on the page, using
     * javascript. If the browser being used doesn't support javascript, or the
     * page isn't long enough to support scrolling to the desired position, than
     * an error will be logged and recorded
     *
     * @param desiredPosition - the position on the page to scroll to
     */
    public void scroll(int desiredPosition) {
        String action = "Scrolling page by " + desiredPosition + " pixels";
        String expected = "Page is scrolled down " + desiredPosition + " pixels";
        Long newPosition;
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            Long initialPosition = (Long) jse.executeScript("return window.scrollY;");

            action = "Scrolling page from " + initialPosition + " to " + desiredPosition;
            expected = "Page is now set at position " + desiredPosition;

            jse.executeScript("window.scrollBy(0, " + desiredPosition + ")");

            newPosition = (Long) jse.executeScript("return window.scrollY;");
        } catch (Exception e) {
            file.fail(action, expected, "Unable to scroll on the page. " + e.getMessage());
            log.warn(e);
            return;
        }
        if (newPosition != desiredPosition) {
            file.fail(action, expected, "Page is set at position " + newPosition);
            return; // indicates page didn't scroll properly
        }
        file.pass(action, expected, "Page is now set at position " + newPosition);
    }

    /**
     * Opens a new tab, and have it selected. The page provided will be loaded
     *
     * @param url - the url to load once the new tab is opened and selected
     */
    public void openNewWindow(String url) {
        String action = "Opening new window to url " + url;
        String expected = "New window is opened to url " + url;
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            jse.executeScript("window.open('" + url + "','_blank');");
        } catch (Exception e) {
            file.fail(action, expected, "Unable to open window tab. " + e.getMessage());
            log.warn(e);
            return;
        }
        switchToNewWindow();
        waitFor().urlEquals(url);
        if (!get().url().equals(url)) {
            file.fail(action, expected, "Unable to open new window to " + url);
            return;
        }
        file.pass(action, expected, expected);
        acceptCertificate();
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
            file.fail(action, expected, "New window was unable to be selected. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
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
            file.fail(action, expected, "Parent window was unable to be selected. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
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
            file.fail(action, expected, "Current window was unable to be closed. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
    }

    /**
     * Select the main window. Used for returning to the main content after
     * selecting a frame. If there are nested frames, the main content will be
     * selected, not the next frame in the parent child relationship
     */
    public void selectMainWindow() {
        String action = "Switching to main window";
        String expected = "Main window is selected";
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            file.fail(action, expected, "Main window was not selected. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
    }

    /**
     * Select the parent frame. Used for returning to the next frame up after
     * selecting a frame. If there are nested frames, the main content won't be
     * selected, however the next frame in the parent child relationship will be
     */
    public void selectParentFrame() {
        String action = "Switching to parent frame";
        String expected = "Parent frame is selected";
        try {
            driver.switchTo().parentFrame();
        } catch (Exception e) {
            file.fail(action, expected, "Parent frame was not selected. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
    }

    /**
     * Select a frame by its (zero-based) index. That is, if a page has three
     * frames, the first frame would be at index 0, the second at index 1 and
     * the third at index 2. Once the frame has been selected, all subsequent
     * calls on the WebDriver interface are made to that frame.
     *
     * @param frameNumber - the frame number, starts at 0
     */
    public void selectFrame(int frameNumber) {
        String action = "Switching to frame <b>" + frameNumber + "</b>";
        String expected = FRAME + frameNumber + AVAILABLE;
        try {
            driver.switchTo().frame(frameNumber);
        } catch (Exception e) {
            file.fail(action, expected, FRAME + frameNumber + NOT_SELECTED + ". " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
    }

    /**
     * Select a frame by its name or ID. Frames located by matching name
     * attributes are always given precedence over those matched by ID.
     *
     * @param frameIdentifier - the frame name or ID
     */
    public void selectFrame(String frameIdentifier) {
        String action = "Switching to frame <b>" + frameIdentifier + "</b>";
        String expected = FRAME + frameIdentifier + AVAILABLE;
        try {
            driver.switchTo().frame(frameIdentifier);
        } catch (Exception e) {
            file.fail(action, expected, FRAME + frameIdentifier + NOT_SELECTED + ". " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, expected);
    }

    /**
     * Safari, Edge and IE don't recognize the allowInsecureCert capability. As a result, an alternative method is
     * provided in order to 'manually' accept the problematic certificate
     */
    public void acceptCertificate() {
        String action = "Clicking override link to accept ssl certificate";
        String result = "Override link clicked";
        //for IE and Edge
        if (browser.getName() == BrowserName.INTERNETEXPLORER || browser.getName() == BrowserName.EDGE) {
            Element overrideLink = newElement(Locator.ID, "overridelink");
            if (overrideLink.is().present()) {
                try {
                    if (browser.getName() == BrowserName.EDGE) {
                        newElement(Locator.ID, "moreInformationDropdownSpan").getWebElement().click();
                    }
                    overrideLink.getWebElement().click();
                    file.pass(action, result, result);
                } catch (Exception e) {
                    file.fail(action, result, "Unable to click override link. "
                            + e.getMessage());
                    log.warn(e);
                }
            }
        }
    }

    //////////////////////////
    // pop-up interactions
    /////////////////////////

    /**
     * Accept (click 'OK' on) whatever popup is present on the page
     *
     * @param action   - the action occurring
     * @param expected - the expected result
     * @param popup    - the element we are interacting with
     */
    private void accept(String action, String expected, String popup) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            file.fail(action, expected, "Unable to click 'OK' on the " + popup + ". " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, "Clicked 'OK' on the " + popup);
    }

    /**
     * Dismiss (click 'Cancel' on) whatever popup is present on the page
     *
     * @param action   - the action occurring
     * @param expected - the expected result
     * @param popup    - the element we are interacting with
     */
    private void dismiss(String action, String expected, String popup) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        } catch (Exception e) {
            log.warn(e);
            file.fail(action, expected, "Unable to click 'Cancel' on the " + popup + ". " + e.getMessage());
            return;
        }
        file.pass(action, expected, "Clicked 'Cancel' on the " + popup);
    }

    /**
     * Determines if a confirmation is present or not, and can be interacted
     * with. If it's not present, an indication that the confirmation can't be
     * clicked on is written to the log file
     *
     * @param action   - the action occurring
     * @param expected - the expected result
     * @return Boolean: is a confirmation actually present or not.
     */
    private boolean isNotConfirmation(String action, String expected) {
        // wait for element to be present
        if (!is.confirmationPresent()) {
            waitFor.confirmationPresent();
        }
        if (!is.confirmationPresent()) {
            file.fail(action, expected, "Unable to click confirmation as it is not present");
            return true; // indicates element not present
        }
        return false;
    }

    /**
     * Determines if a prompt is present or not, and can be interacted with. If
     * it's not present, an indication that the confirmation can't be clicked on
     * is written to the log file
     *
     * @param action   - the action occurring
     * @param expected - the expected result
     * @param perform  - the action occurring to the prompt
     * @return Boolean: is a prompt actually present or not.
     */
    private boolean isNotPrompt(String action, String expected, String perform) {
        // wait for element to be present
        if (!is.promptPresent()) {
            waitFor.promptPresent();
        }
        if (!is.promptPresent()) {
            file.fail(action, expected, "Unable to " + perform + " prompt as it is not present");
            return true; // indicates element not present
        }
        return false;
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
            file.fail(action, expected, "Unable to click alert as it is not present");
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
        if (isNotConfirmation(action, expected)) {
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
        if (isNotConfirmation(action, expected)) {
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
        if (isNotPrompt(action, expected, "click")) {
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
        if (isNotPrompt(action, expected, "click")) {
            return;
        }
        dismiss(action, expected, "prompt");
    }

    /**
     * Type text into a prompt box
     *
     * @param text - the text to type into the prompt
     */
    public void typeIntoPrompt(String text) {
        String action = "Typing text '" + text + "' into prompt";
        String expected = "Prompt is present and enabled to have text " + text + " typed in";
        if (isNotPrompt(action, expected, "type into")) {
            return;
        }
        try {
            Alert alert = driver.switchTo().alert();
            alert.sendKeys(text);
        } catch (Exception e) {
            file.fail(action, expected, "Unable to type into prompt. " + e.getMessage());
            log.warn(e);
            return;
        }
        file.pass(action, expected, "Typed text '" + text + "' into prompt");
    }
}
