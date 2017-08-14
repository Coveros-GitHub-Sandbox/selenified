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

import com.coveros.selenified.exceptions.InvalidBrowserException;

/**
 * Selenium has some basic extensions of the Selenium class to assist with
 * testing, such as supported browsers, locators, and how to initially start the
 * tests
 * 
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 7/20/2017
 *
 */
public class Selenium {

    /**
     * determining how to launch/start the browser. Do we even want a browser,
     * and if so do we wait for the initial page to load, or do we need to
     * perform other activities first
     */
    public enum DriverSetup {
        FALSE, OPEN, LOAD;

        protected Boolean browser;
        protected Boolean load;

        static {
            FALSE.browser = false;
            OPEN.browser = true;
            LOAD.browser = true;
        }

        static {
            FALSE.load = false;
            OPEN.load = false;
            LOAD.load = true;
        }

        public Boolean useBrowser() {
            return this.browser;
        }

        public Boolean loadPage() {
            return this.load;
        }
    }

    /**
     * Select a Locator for the element we are interacting with Available
     * options are: xpath, id, name, classname, css, paritallinktext, linktext,
     * tagname
     */
    public enum Locator {
        XPATH, ID, NAME, CLASSNAME, CSS, PARTIALLINKTEXT, LINKTEXT, TAGNAME
    }

    /**
     * Select a browser to run Available options are: HtmlUnit (only locally -
     * not on grid), Firefox, Marionette, Chrome, InternetExplorer, Edge,
     * Android, Ipad (only locally - not on grid), Iphone (only locally, not on
     * grid), Opera, Safari, PhantomJS
     */
    public enum Browser {
        NONE, HTMLUNIT, FIREFOX, MARIONETTE, CHROME, INTERNETEXPLORER, EDGE, ANDROID, IPAD, IPHONE, OPERA, SAFARI, PHANTOMJS;

        /**
         * allows the browser selected to be passed in with a case insensitive
         * name
         * 
         * @param b
         *            - the string name of the browser
         * @return Browser: the enum version of the browser
         * @throws InvalidBrowserException
         *             If a browser that is not one specified in the
         *             Selenium.Browser class is used, this exception will be
         *             thrown
         */
        public static Browser lookup(String b) throws InvalidBrowserException {
            for (Browser browser : Browser.values()) {
                if (browser.name().equalsIgnoreCase(b)) {
                    return browser;
                }
            }
            throw new InvalidBrowserException("The selected browser " + b + " is not an applicable choice");
        }
    }
}