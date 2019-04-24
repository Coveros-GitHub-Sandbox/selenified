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

import com.coveros.selenified.Browser;
import com.coveros.selenified.exceptions.InvalidHubException;
import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.services.Request;
import com.coveros.selenified.utilities.Reporter.Success;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;
import org.testng.log4testng.Logger;

import java.io.File;
import java.io.IOException;

import static com.coveros.selenified.Selenified.REPORTER;
import static com.coveros.selenified.Selenified.SESSION_ID;
import static com.coveros.selenified.utilities.Constants.*;
import static com.coveros.selenified.utilities.Property.BROWSER;

/**
 * Appends additional test links and information into the TestNG report file,
 * for easier tracking and viewing of detailed custom test reports. This class
 * should be specified as a listener for the main Selenified class, and/or in
 * the TestNG xml file.
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 4/4/2019
 */
public class Listener extends TestListenerAdapter {
    private static final Logger log = Logger.getLogger(Listener.class);

    private static final String OUTPUT_BREAK = " | ";
    private static final String TIME_UNIT = " seconds";

    /**
     * determines the folder name associated with the given tests
     *
     * @param result - the testng itestresult object
     * @return String: a string version of the folder name
     */
    private static String getFolderName(ITestResult result) {
        return new File(result.getTestContext().getOutputDirectory()).getName();
    }

    /**
     * determines the test name associated with the given tests
     *
     * @param result - the testng itestresult object
     * @return String: a string version of the test name
     */
    protected static String getTestName(ITestResult result) {
        String className;
        String packageName = "";
        String testClass = result.getTestClass().toString();
        // cleanup the class name
        if (testClass.startsWith("[")) {
            testClass = testClass.substring(1);
        }
        if (testClass.endsWith("]")) {
            testClass = testClass.substring(0, testClass.length() - 1);
        }
        if (testClass.contains(" ")) {
            String[] parts = testClass.split(" ");
            testClass = parts[parts.length - 1];
        }
        // finally split into package and class
        if (testClass.contains(".")) {
            packageName = testClass.split("\\.")[0];
            className = testClass.split("\\.")[1];
        } else {
            className = testClass;
        }
        return TestCase.getTestName(packageName, className, result.getName(), result.getParameters());
    }

    /**
     * Provides ability to skip a test, based on the browser selected
     *
     * @param result - the testng itestresult object
     */
    @Override
    public void onTestStart(ITestResult result) {
        super.onTestStart(result);
        // if a group indicates an invalid browser, skip the test
        Browser browser = (Browser) result.getAttribute(BROWSER);
        if (browser != null) {
            String[] groups = result.getMethod().getGroups();
            for (String group : groups) {
                if (group.equalsIgnoreCase("no-" + browser.getName().toString())) {
                    log.warn("Skipping test case " + getTestName(result) + ", as it is not intended for browser " + browser.getName());
                    result.setStatus(ITestResult.SKIP);
                    throw new SkipException("Skipping test case");
                }
            }
        }
    }

    /**
     * Runs the default TestNG onTestFailure, and adds additional information
     * into the testng reporter
     *
     * @param result - the testng itestresult object
     */
    @Override
    public void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
        recordResult(result);
    }

    /**
     * Runs the default TestNG onTestSkipped, and adds additional information
     * into the testng reporter
     *
     * @param result - the testng itestresult object
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        super.onTestSkipped(result);
        //cleanup unneeded files
        Reporter reporter = (Reporter) result.getAttribute(REPORTER);
        if (reporter != null) {
            new File(reporter.getDirectory(), reporter.getFileName() + ".html").delete();
            if (Property.generatePDF()) {
                new File(reporter.getDirectory(), reporter.getFileName() + ".pdf").delete();
            }
        }
    }

    /**
     * Runs the default TestNG onTestSuccess, and adds additional information
     * into the testng reporter
     *
     * @param result - the testng itestresult object
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        super.onTestSuccess(result);
        recordResult(result);
    }

    /**
     * Checks to see if the test execution was performed on SauceLabs or not. If it was, this reaches out to Sauce,
     * in order to update the execution results
     *
     * @param result - the testng itestresult object
     */
    private void recordResult(ITestResult result) {
        // finalize our output file
        Reporter reporter = (Reporter) result.getAttribute(REPORTER);
        String htmlFilename = "";
        String pdfFilename = "";
        if (reporter != null) {
            // subtracting one from the status ordinal to map ITestResult to Success
            reporter.finalizeReporter(result.getStatus() - 1);
            htmlFilename = reporter.getFileName() + ".html";
            if (Property.generatePDF()) {
                pdfFilename = reporter.getFileName() + ".pdf";
            }
        }
        // update our reporter logger
        String testName = getTestName(result);
        Browser browser = (Browser) result.getAttribute(BROWSER);
        if (browser != null) {
            // subtracting one from the status ordinal to map ITestResult to Success
            org.testng.Reporter.log(
                    Success.values()[result.getStatus() - 1] + OUTPUT_BREAK + browser.getDetails() + OUTPUT_BREAK + LINK_START +
                            getFolderName(result) + "/" + htmlFilename + LINK_MIDDLE + testName + " HTML Report" +
                            LINK_END);
            if (!pdfFilename.isEmpty()) {
                org.testng.Reporter.log(OUTPUT_BREAK + LINK_START +
                        getFolderName(result) + "/" + pdfFilename + LINK_MIDDLE + testName + " PDF" +
                        LINK_END);
            }
            org.testng.Reporter.log(OUTPUT_BREAK + (result.getEndMillis() - result.getStartMillis()) / 1000 + TIME_UNIT);
        }
        // update sauce labs
        if (Sauce.isSauce() && result.getAttributeNames().contains(SESSION_ID)) {
            String sessionId = result.getAttribute(SESSION_ID).toString();
            JsonObject json = new JsonObject();
            json.addProperty("passed", result.getStatus() == 1);
            JsonArray tags = new JsonArray();
            for (String tag : result.getMethod().getGroups()) {
                tags.add(tag);
            }
            json.add("tags", tags);
            try {
                HTTP http = new HTTP(null, "https://saucelabs.com/rest/v1/" + Sauce.getSauceUser() + "/jobs/", Sauce.getSauceUser(),
                        Sauce.getSauceKey());
                http.put(sessionId, new Request().setJsonPayload(json), null);
            } catch (InvalidHubException e) {
                log.error("Unable to connect to sauce, due to credential problems");
            } catch (IOException e) {
                log.error(e);
            }
        }
    }
}