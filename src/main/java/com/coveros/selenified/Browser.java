/*
 * Copyright 2018 Coveros, Inc.
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

package com.coveros.selenified;

import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.utilities.TestCase;
import org.openqa.selenium.Platform;

import java.util.HashMap;
import java.util.Map;

/**
 * Select a browser to run Available options are: HtmlUnit, Firefox, Chrome, InternetExplorer, Edge, Opera,
 * Safari, PhantomJS
 *
 * @author Max Saperstone
 * @version 3.0.5
 * @lastupdate 1/12/2019
 */
public class Browser {

    public enum BrowserName {
        NONE, HTMLUNIT, FIREFOX, CHROME, INTERNETEXPLORER, EDGE, OPERA, SAFARI, PHANTOMJS
    }

    public static final String SCREENSIZE_INPUT = "screensize";
    public static final String BROWSER_INPUT = "browser";
    public static final String NAME_INPUT = "name";
    public static final String VERSION_INPUT = "version";
    public static final String PLATFORM_INPUT = "platform";

    private String browserInput;
    private BrowserName name;
    private String version = null;
    private Platform platform = null;
    private String screensize = null;

    /**
     * Parses the passed in browser information (obtained from command line inputs), and saves off the information
     *
     * @param browserInput
     * @throws InvalidBrowserException
     */
    public Browser(String browserInput) throws InvalidBrowserException {
        this.browserInput = browserInput;
        if (!areBrowserDetailsSet()) {
            this.name = lookup(browserInput);
        } else {
            Map<String, String> browserDetails = parseMap();
            if (!browserDetails.containsKey(NAME_INPUT)) {
                throw new InvalidBrowserException("name must be included in browser details");
            }
            this.name = lookup(browserDetails.get(NAME_INPUT));
            if (browserDetails.containsKey(VERSION_INPUT)) {
                this.version = browserDetails.get(VERSION_INPUT);
            }
            if (browserDetails.containsKey(PLATFORM_INPUT)) {
                this.platform = Platform.fromString(browserDetails.get(PLATFORM_INPUT));
            }
            if (browserDetails.containsKey(SCREENSIZE_INPUT) && (browserDetails.get(SCREENSIZE_INPUT).matches("(\\d+)x(\\d+)")
                    || browserDetails.get(SCREENSIZE_INPUT).equalsIgnoreCase("maximum"))) {
                this.screensize = browserDetails.get(SCREENSIZE_INPUT);
            }
        }
    }

    public BrowserName getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public Platform getPlatform() {
        return platform;
    }

    public String getScreensize() {
        return screensize;
    }

    public String getDetails() {
        StringBuilder stringBuilder = new StringBuilder(TestCase.capitalizeFirstLetters(getName().toString().toLowerCase()));
        if (getVersion() != null) {
            stringBuilder.append(" ").append(getVersion());
        }
        if (getPlatform() != null) {
            stringBuilder.append(" ").append(getPlatform());
        }
        if (getScreensize() != null) {
            stringBuilder.append(" ").append(getScreensize());
        }
        return stringBuilder.toString();
    }

    /**
     * allows the browser selected to be passed in with a case insensitive name
     *
     * @param b - the string name of the browser
     * @return Browser: the enum version of the browser
     * @throws InvalidBrowserException If a browser that is not one specified in the
     *                                 Selenium.Browser class is used, this exception will be thrown
     */
    public static BrowserName lookup(String b) throws InvalidBrowserException {
        for (BrowserName browser : BrowserName.values()) {
            if (browser.name().equalsIgnoreCase(b)) {
                return browser;
            }
        }
        throw new InvalidBrowserException("The selected browser " + b + " is not an applicable choice");
    }

    /**
     * determines if the browser information provided has details, or just the
     * browser name
     *
     * @return Boolean: are there details associated with the browser, such as
     * version, os, etc
     */
    private boolean areBrowserDetailsSet() {
        return browserInput != null && !browserInput.matches("^[a-zA-Z,]+$");
    }

    /**
     * Breaks up a string, and places it into a map. ampersands (&) are used to
     * split into key value pairs, while equals (=) are used to assign key vs
     * values
     *
     * @return Map: a map with values
     */
    private Map<String, String> parseMap() {
        final Map<String, String> map = new HashMap<>();
        for (String pair : browserInput.split("&")) {
            String[] kv = pair.split("=");
            map.put(kv[0], kv[1]);
        }
        return map;
    }
}