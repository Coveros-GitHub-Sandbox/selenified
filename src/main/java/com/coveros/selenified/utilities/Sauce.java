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

package com.coveros.selenified.utilities;

import com.coveros.selenified.exceptions.InvalidSauceException;
import com.saucelabs.saucerest.SauceException;
import com.saucelabs.saucerest.SauceREST;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;

import java.net.MalformedURLException;

import static com.coveros.selenified.Selenified.SESSION_ID;
import static com.coveros.selenified.utilities.Property.HUB;
import static com.coveros.selenified.utilities.Property.getProgramProperty;

/**
 * Utilities class to interact with sauce labs, retrieving information such as username, key, and test status
 *
 * @author Max Saperstone
 * @version 3.2.1
 * @lastupdate 8/18/2019
 */
public class Sauce extends Hub {
    private static final Logger log = Logger.getLogger(Sauce.class);
    private static final String SAUCE_HUB_ISN_T_SET = "Sauce hub isn't set";

    public Sauce() throws MalformedURLException {
        super();
    }

    /**
     * Determine whether the hub parameter is set, and if it is, is it set to sauce labs? Iff, then will return true,
     * otherwise, returns false
     *
     * @return Boolean: whether sauce labs is specified as the hub
     */
    public static Boolean isSauce() {
        String hub = getProgramProperty(HUB);
        return hub != null && hub.contains("ondemand.saucelabs.com");
    }

    /**
     * TODO
     *
     * @param result
     */
    static void updateStatus(ITestResult result) {
        if (isSauce() && result.getAttributeNames().contains(SESSION_ID)) {
            String sessionId = result.getAttribute(SESSION_ID).toString();
            try {
                SauceREST sauce = new Sauce().getSauceConnection();
                if (result.getStatus() == 1) {
                    sauce.jobPassed(sessionId);
                } else {
                    sauce.jobFailed(sessionId);
                }
            } catch (SauceException | MalformedURLException e) {
                log.error("Unable to connect to sauce, due to credential problems");
            }
        }
    }

    /**
     * Sauce labs has specific capabilities to manage the selenium version used. The version is obtained from the
     * POM (or could be passed in via CMD to override) and then set so that Sauce sets the specific selenium version,
     * instead of their default: https://wiki.saucelabs.com/display/DOCS/Test+Configuration+Options#TestConfigurationOptions-SeleniumVersion
     * Additionally, the iedriverVersion is set to match the selenium version as suggested, if ie is the chosen browser
     * Finally, the default platform for edge is set to windows 10
     */
    public static void setupSauceCapabilities(DesiredCapabilities desiredCapabilities) {
        if (Sauce.isSauce()) {
            // set the selenium version
            desiredCapabilities.setCapability("seleniumVersion", System.getProperty("selenium.version"));
            // set the ie driver if needed
            if (desiredCapabilities.getBrowserName().equals("internet explorer")) {
                desiredCapabilities.setCapability("iedriverVersion", System.getProperty("selenium.version"));
            }
        }
    }

    /**
     * Creates a new connection to sauce labs
     *
     * @return SauceREST: an object with information to connect to/update sauce labs
     * @throws MalformedURLException if no sauce connection is set, invalid hub will be thrown
     */
    public SauceREST getSauceConnection() throws MalformedURLException {
        if (!isSauce()) {
            throw new InvalidSauceException(SAUCE_HUB_ISN_T_SET);
        }
        return new SauceREST(getUsername(), getPassword());
    }
}