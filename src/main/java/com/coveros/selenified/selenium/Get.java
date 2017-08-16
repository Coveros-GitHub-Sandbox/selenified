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

import java.util.Date;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

import com.coveros.selenified.tools.General;
import com.coveros.selenified.tools.OutputFile;

/**
 * Get retrieves information about the app in general, not specific to any
 * particular page or element. If an object isn't present, null will be returned
 * 
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 8/13/2017
 */
public class Get {
    private static final Logger log = Logger.getLogger(General.class);

    // what locator actions are available in webdriver
    // this is the driver that will be used for all selenium actions
    private WebDriver driver;

    // the is class to determine if something exists
    private Is is;
    // the wait class to determine if we need to wait for something
    private WaitFor waitFor;

    public Get(WebDriver driver, OutputFile file) {
        this.driver = driver;
        this.is = new Is(driver, file);
        this.waitFor = new WaitFor(driver, file);
    }

    // ////////////////////////////////////
    // checking page information availability
    // ////////////////////////////////////

    /**
     * Retrieves the current url of the application.
     *
     * @return String - current application url
     */
    public String location() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Retrieves the title of the current page the application is on
     *
     * @return String - current application title
     */
    public String title() {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Retrieves the full html source of the current page the application is on
     *
     * @return String - current application page source
     */
    public String htmlSource() {
        try {
            return driver.getPageSource();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Executes a provided script, and returns the output of that script. If
     * there is an error executing this script, a null value will be returned.
     * 
     * @param javascriptFunction
     *            - the javascript function that is going to be executed
     * @return Object: any resultant output from the javascript command
     */
    public Object eval(String javascriptFunction) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return js.executeScript(javascriptFunction);
        } catch (NoSuchMethodError | Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Retrieves the content of an alert present on the page. If the alert isn't
     * present, it waits up to the default time (5 seconds) for the alert. If
     * the alert doesn't exist, a null value will be returned.
     *
     * @return String - the content of an alert
     */
    public String alert() {
        if (!is.alertPresent()) {
            waitFor.alertPresent();
        }
        if (!is.alertPresent()) {
            return null;
        }
        try {
            Alert alert = driver.switchTo().alert();
            return alert.getText();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Retrieves the content of a confirmation present on the page. If the
     * confirmation isn't present, it waits up to the default time (5 seconds)
     * for the confirmation. If the confirmation doesn't exist, a null value
     * will be returned.
     *
     * @return String - the content of the confirmation
     */
    public String confirmation() {
        if (!is.confirmationPresent()) {
            waitFor.confirmationPresent();
        }
        if (!is.confirmationPresent()) {
            return null;
        }
        try {
            Alert alert = driver.switchTo().alert();
            return alert.getText();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Retrieves the content of a prompt present on the page. If the prompt
     * isn't present, it waits up to the default time (5 seconds) for the
     * prompt. If the prompt doesn't exist, a null value will be returned.
     *
     * @return String - the content of the prompt
     */
    public String prompt() {
        if (!is.promptPresent()) {
            waitFor.promptPresent();
        }
        if (!is.promptPresent()) {
            return null;
        }
        try {
            Alert alert = driver.switchTo().alert();
            return alert.getText();
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Retrieves the full cookie in the application with the provided
     * cookieName. If the cookie doesn't exist, a null value will be returned.
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return Cookie - the cookie
     */
    public Cookie cookie(String expectedCookieName) {
        try {
            return driver.manage().getCookieNamed(expectedCookieName);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * Retrieves the cookie value in the application with the provided
     * cookieName. If the cookie doesn't exist, a null value will be returned.
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return String - the value of the cookie
     */
    public String cookieValue(String expectedCookieName) {
        Cookie cookie = cookie(expectedCookieName);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * Retrieves the cookie path in the application with the provided
     * cookieName. If the cookie doesn't exist, a null value will be returned.
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return String - the path of the cookie
     */
    public String cookiePath(String expectedCookieName) {
        Cookie cookie = cookie(expectedCookieName);
        if (cookie != null) {
            return cookie.getPath();
        }
        return null;
    }

    /**
     * Retrieves the cookie domain in the application with the provided
     * cookieName. If the cookie doesn't exist, a null value will be returned.
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return String - the domain of the cookie
     */
    public String cookieDomain(String expectedCookieName) {
        Cookie cookie = cookie(expectedCookieName);
        if (cookie != null) {
            return cookie.getDomain();
        }
        return null;
    }

    /**
     * Retrieves the cookie expiration in the application with the provided
     * cookieName. If the cookie doesn't exist, a null value will be returned.
     *
     * @param expectedCookieName
     *            - the name of the cookie
     * @return String - the expiration of the cookie
     */
    public Date cookieExpiration(String expectedCookieName) {
        Cookie cookie = cookie(expectedCookieName);
        if (cookie != null) {
            return cookie.getExpiry();
        }
        return null;
    }
}