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

import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.services.Request;
import com.google.gson.JsonObject;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;

import java.io.IOException;
import java.net.MalformedURLException;

import static com.coveros.selenified.Selenified.SESSION_ID;
import static com.coveros.selenified.utilities.Property.HUB;
import static com.coveros.selenified.utilities.Property.getProgramProperty;

/**
 * Utilities class to interact with lambda test, retrieving information such as username, key, and test status
 *
 * @author Max Saperstone
 * @version 3.3.1
 * @lastupdate 8/18/2019
 */
public class LambdaTest extends Hub {
    private static final Logger log = Logger.getLogger(LambdaTest.class);

    public LambdaTest() throws MalformedURLException {
        super();
    }

    /**
     * Determine whether the hub parameter is set, and if it is, is it set to sauce labs? Iff, then will return true,
     * otherwise, returns false
     *
     * @return Boolean: whether sauce labs is specified as the hub
     */
    public static Boolean isLambdaTest() {
        String hub = getProgramProperty(HUB);
        return hub != null && hub.contains("hub.lambdatest.com");
    }

    /**
     * Connects with Lambda Test and updates the status in their system to the test result
     * status
     *
     * @param result - the testng itestresult object
     */
    static void updateStatus(ITestResult result) {
        if (isLambdaTest() && result.getAttributeNames().contains(SESSION_ID)) {
            String sessionId = result.getAttribute(SESSION_ID).toString();
            JsonObject json = new JsonObject();
            String status = (result.getStatus() == 1) ? "passed" : "failed";
            json.addProperty("status_ind", status);
            try {
                LambdaTest lambdaTest = new LambdaTest();
                HTTP http = new HTTP(null, "https://api.lambdatest.com/automation/api/v1/", lambdaTest.getUsername(), lambdaTest.getPassword());
                http.patch("sessions/" + sessionId, new Request().setJsonPayload(json), null);
            } catch (MalformedURLException e) {
                log.error("Unable to connect to lambda test, due to credential problems");
            } catch (IOException e) {
                log.error(e);
            }
        }
    }
}