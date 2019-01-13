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

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.log4testng.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Select a browser to run Available options are: HtmlUnit (only locally - not
 * on grid), Firefox, Marionette, Chrome, InternetExplorer, Edge, Android, Ipad
 * (only locally - not on grid), Iphone (only locally, not on grid), Opera,
 * Safari, PhantomJS
 *
 * @author Max Saperstone
 * @version 3.0.4
 * @lastupdate 5/16/2018
 */
public class Browser {

    private static final Logger log = Logger.getLogger(Browser.class);

    public enum BrowserName {
        NONE, HTMLUNIT, FIREFOX, CHROME, INTERNETEXPLORER, EDGE, ANDROID, IPAD, IPHONE, OPERA, SAFARI, PHANTOMJS
    }

    public static final String SCREENSIZE = "screensize";
    public static final String BROWSER = "browser";
    public static final String NAME = "name";
    public static final String VERSION = "version";
    public static final String PLATFORM = "platform";

    private String browserInput;
    private BrowserName name;
    private String version = null;
    private String platform = null;
    private String screensize = null;

    /**
     * TODO
     *
     * @param browserInput
     * @throws InvalidBrowserException
     */
    public Browser(String browserInput) throws InvalidBrowserException {
        this.browserInput = browserInput;
        if (!areBrowserDetailsSet()) {
            try {
                this.name = lookup(browserInput);
            } catch (InvalidBrowserException e) {
                log.error(e);
                throw new InvalidBrowserException(e.getMessage());
            }
        } else {
            Map<String, String> browserDetails = parseMap();
            if (!browserDetails.containsKey(NAME)) {
                throw new InvalidBrowserException("name must be included in browser details");
            }
            this.name = lookup(browserDetails.get(NAME));
            if (browserDetails.containsKey(VERSION)) {
                this.version = browserDetails.get(VERSION);
            }
            if (browserDetails.containsKey(PLATFORM)) {
                this.platform = browserDetails.get(PLATFORM);
            }
            if (browserDetails.containsKey(SCREENSIZE) && (browserDetails.get(SCREENSIZE).matches("(\\d+)x(\\d+)")
                    || browserDetails.get(SCREENSIZE).equalsIgnoreCase("maximum"))) {
                this.screensize = browserDetails.get(SCREENSIZE);
            }
        }
    }

    public BrowserName getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getPlatform() {
        return platform;
    }

    public String getScreensize() {
        return screensize;
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
     * sets the browser details (name, version, device, orientation, os) into
     * the device capabilities
     *
     * @param capabilities - the DesiredCapabilities object, which will get set with all browser capabilities
     */
    public DesiredCapabilities setBrowserCapabilities(DesiredCapabilities capabilities) {
        // determine the browser information
        if (getName() != null &&
                (capabilities.getBrowserName() == null || "".equals(capabilities.getBrowserName()))) {
            capabilities.setCapability(CapabilityType.BROWSER_NAME, getName().toString().toLowerCase());
        }
        if (getVersion() != null) {
            capabilities.setCapability(CapabilityType.VERSION, getVersion());
        }
        if (getPlatform() != null) {
            capabilities.setCapability(CapabilityType.PLATFORM, getPlatform());
        }
        if (getScreensize() != null && getScreensize().matches("(\\d+)x(\\d+)")) {
            capabilities.setCapability("screenResolution", getScreensize());
        }
        return capabilities;
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