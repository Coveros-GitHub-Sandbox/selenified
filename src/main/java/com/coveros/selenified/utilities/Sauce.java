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

/**
 * Utilities class to interact with sauce labs, retrieving information such as username, key, and test status
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 5/13/2018
 */
public class Sauce {
    private Sauce() {
    }

    public static Boolean isSauce() {
        String hub = System.getProperty("hub");
        return hub != null && hub.contains("ondemand.saucelabs.com");
    }

    private static String getSauceCreds(String hub) {
        String[] parts = hub.split("@");
        if (parts.length != 2) {
            return null;
        }
        String[] startParts = parts[0].split("/");
        if (startParts.length != 3) {
            return null;
        }
        return startParts[2];
    }

    public static String getSauceUser() {
        if (!isSauce()) {
            return null;
        }
        String creds = getSauceCreds(System.getProperty("hub"));
        if (creds == null) {
            return null;
        }
        String[] parts = creds.split(":");
        if (parts.length != 2) {
            return null;
        }
        return parts[0];
    }

    public static String getSauceKey() {
        if (!isSauce()) {
            return null;
        }
        String creds = getSauceCreds(System.getProperty("hub"));
        if (creds == null) {
            return null;
        }
        String[] parts = creds.split(":");
        if (parts.length != 2) {
            return null;
        }
        return parts[1];
    }
}