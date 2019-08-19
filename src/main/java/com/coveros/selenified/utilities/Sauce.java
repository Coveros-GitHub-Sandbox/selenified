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
import com.saucelabs.saucerest.SauceREST;
import org.testng.log4testng.Logger;

/**
 * Utilities class to interact with sauce labs, retrieving information such as username, key, and test status
 *
 * @author Max Saperstone
 * @version 3.2.1
 * @lastupdate 8/18/2019
 */
public class Sauce {
    private static final Logger log = Logger.getLogger(Sauce.class);
    private static final String SAUCE_HUB_ISN_T_SET = "Sauce hub isn't set";

    private Sauce() {
    }

    /**
     * Determione whether the hub parameter is set, and if it is, is it set to sauce labs? Iff, then will return true,
     * otherwise, returns false
     *
     * @return Boolean: whether sauce labs is specified as the hub
     */
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

    /**
     * Creates a new connection to sauce labs
     *
     * @return SauceREST: an object with information to connect to/update sauce labs
     * @throws InvalidHubException if no sauce connection is set, invalid hub will be thrown
     */
    public static SauceREST getSauceConnection() throws InvalidHubException {
        if (!isSauce()) {
            throw new InvalidSauceException(SAUCE_HUB_ISN_T_SET);
        }
        return new SauceREST(Sauce.getSauceUser(), Sauce.getSauceKey());
    }

    /**
     * Retrieves sauce labs credentials, both username, and secret key
     *
     * @param hub - the hub parameter
     * @return String: the string version of the credentials `[username]:[secret key]`
     * @throws InvalidSauceException if invalid sauce connection is set (i.e. needs protocol, credentials, and
     *                               endpoint), invalid sauce will be thrown
     */
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

    /**
     * Retrieves the sauce labs username. If it is set via environment variables, it will be returned. Otherwise, the
     * Hub address will be analyzed, and the information will be extracted from it
     *
     * @return String: the sauce labs username
     * @throws InvalidHubException if hub isn't set, or invalid sauce connection is set (i.e. needs protocol,
     *                             credentials, and endpoint), invalid sauce will be thrown
     */
    public static String getSauceUser() throws InvalidHubException {
        if (!isSauce()) {
            throw new InvalidSauceException(SAUCE_HUB_ISN_T_SET);
        }
        if (System.getenv("SAUCE_USER") != null) {
            return System.getenv("SAUCE_USER");
        }
        String credentials = getSauceCreds(Property.getHub());
        String[] parts = credentials.split(":");
        if (parts.length != 2) {
            throw new InvalidSauceException("Sauce labs hub isn't valid. Credentials '" + credentials + "' must contain both username and password");
        }
        return parts[0];
    }

    /**
     * Retrieves the sauce labs secret key. If it is set via environment variables, it will be returned. Otherwise, the
     * Hub address will be analyzed, and the information will be extracted from it
     *
     * @return String: the sauce labs secret key
     * @throws InvalidHubException if hub isn't set, or invalid sauce connection is set (i.e. needs protocol,
     *                             credentials, and endpoint), invalid sauce will be thrown
     */
    public static String getSauceKey() throws InvalidHubException {
        if (!isSauce()) {
            throw new InvalidSauceException(SAUCE_HUB_ISN_T_SET);
        }
        if (System.getenv("SAUCE_KEY") != null) {
            return System.getenv("SAUCE_KEY");
        }
        String credentials = getSauceCreds(Property.getHub());
        String[] parts = credentials.split(":");
        if (parts.length != 2) {
            throw new InvalidSauceException("Sauce labs hub isn't valid. Credentials '" + credentials + "' must contain both username and password");
        }
        return parts[1];
    }
}