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

import java.net.MalformedURLException;
import java.net.URL;

import static com.coveros.selenified.utilities.Property.HUB;
import static com.coveros.selenified.utilities.Property.getProgramProperty;

/**
 * Utilities class to interact with some remote hub driver, retrieving information such as username, key, and test status
 *
 * @author Max Saperstone
 * @version 3.3.1
 * @lastupdate 8/23/2019
 */
public class Hub {

    private static final String HUB_ISN_T_SET = "Hub isn't set";
    private static final String HUB_USER = "HUB_USER";
    private static final String HUB_PASS = "HUB_PASS";
    private URL hubURL;
    private String username = null;
    private String password = null;

    public Hub() throws MalformedURLException {
        if (!isHubSet()) {
            throw new InvalidHubException(HUB_ISN_T_SET);
        }
        String hubProperty = getProgramProperty(HUB);
        try {
            this.hubURL = new URL(hubProperty);
        } catch (MalformedURLException e) {
            throw new InvalidHubException("Hub '" + hubProperty + "' isn't valid. Must contain protocol, optionally credentials, and endpoint");
        }
        setUserInfo();
        String credentials = username == null ? "" : username + ":" + password + "@";
        String port = hubURL.getPort() == -1 ? "" : ":" + hubURL.getPort();
        this.hubURL = new URL(hubURL.getProtocol() + "://" + credentials + hubURL.getHost() + port + hubURL.getFile() + "/wd/hub");
    }

    /**
     * Determines if a hub property is set. This could be to sauce, grid, or any other cloud tool.
     * This should be provided with the protocol and address, but leaving out the /wd/hub
     *
     * @return boolean: is a hub location set
     */
    public static boolean isHubSet() {
        String hub = getProgramProperty(HUB);
        return hub != null && !"".equals(hub);
    }

    private void setUserInfo() throws InvalidHubException {
        String userInfo = hubURL.getUserInfo();
        if (System.getenv(HUB_USER) != null && System.getenv(HUB_PASS) != null) {
            this.username = System.getenv(HUB_USER);
            this.password = System.getenv(HUB_PASS);
        } else if (userInfo != null) {
            int split = userInfo.indexOf(':');
            if (split >= 0 && split <= userInfo.length()) {
                this.username = userInfo.substring(0, split);
                this.password = userInfo.substring(split + 1);
            } else {
                throw new InvalidHubException("Hub isn't valid. Credentials '" + userInfo + "' must contain both username and password");
            }
        }
    }

    /**
     * Retrieves the hub property if it is set. This could be to sauce, grid, or any other cloud tool.
     * This should be provided with the protocol and address, but leaving out the /wd/hub
     *
     * @return String: the set hub address, null if none are set
     */
    public URL getHubURL() {
        return hubURL;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
