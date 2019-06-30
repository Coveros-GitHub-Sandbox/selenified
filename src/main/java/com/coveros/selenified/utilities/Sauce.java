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

import com.coveros.selenified.exceptions.InvalidHubException;
import com.coveros.selenified.exceptions.InvalidSauceException;
import org.testng.log4testng.Logger;

/**
 * Utilities class to interact with sauce labs, retrieving information such as username, key, and test status
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 3/29/2019
 */
public class Sauce {
    private static final Logger log = Logger.getLogger(Sauce.class);

    private Sauce() {
    }

    public static Boolean isSauce() {
        String hub;
        try {
            hub = Property.getHub();
        } catch (InvalidHubException e) {
            log.info(e);
            return false;
        }
        return hub.contains("ondemand.saucelabs.com");
    }

    private static String getSauceCreds(String hub) throws InvalidSauceException {
        String[] parts = hub.split("@");
        if (parts.length != 2) {
            throw new InvalidSauceException("Sauce labs hub '" + hub + "' isn't valid. Must contain protocol, credentials, and location");
        }
        String[] startParts = parts[0].split("/");
        if (startParts.length != 3) {
            throw new InvalidSauceException("Sauce labs hub '" + hub + "' isn't valid. Must contain protocol, credentials and location");
        }
        return startParts[2];
    }

    public static String getSauceUser() throws InvalidHubException {
        if (!isSauce()) {
            throw new InvalidSauceException("Sauce hub isn't set");
        }
        String credentials = getSauceCreds(Property.getHub());
        String[] parts = credentials.split(":");
        if (parts.length != 2) {
            throw new InvalidSauceException("Sauce labs hub isn't valid. Credentials '" + credentials + "' must contain both username and password");
        }
        return parts[0];
    }

    public static String getSauceKey() throws InvalidHubException {
        if (!isSauce()) {
            throw new InvalidSauceException("Sauce hub isn't set");
        }
        String credentials = getSauceCreds(Property.getHub());
        String[] parts = credentials.split(":");
        if (parts.length != 2) {
            throw new InvalidSauceException("Sauce labs hub isn't valid. Credentials '" + credentials + "' must contain both username and password");
        }
        return parts[1];
    }
}