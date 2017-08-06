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

package com.coveros.selenified.tools;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.coveros.selenified.selenium.Assert.Result;
import com.coveros.selenified.selenium.Selenium.Browser;

import java.io.File;

public class Listener extends TestListenerAdapter {

    private static final String BROWSER_INPUT = "browser";
    private static final String OUTPUT_BREAK = " | ";
    private static final String FILE_EXTENTION = "html";
    private static final String LINK_START = "<a target='_blank' href='";
    private static final String LINK_MIDDLE = "." + FILE_EXTENTION + "'>";
    private static final String LINK_END = "</a>";
    private static final String TIME_UNIT = " seconds";

    /**
     * determines the folder name associated with the given tests
     * 
     * @param test
     *            - the testng itestresult object
     * @return String: a string version of the folder name
     */
    private static String getFolderName(ITestResult test) {
        return new File(test.getTestContext().getOutputDirectory()).getName();
    }

    /**
     * determines the test name associated with the given tests
     * 
     * @param test
     *            - the testng itestresult object
     * @return String: a string version of the test name
     */
    private static String getTestName(ITestResult result) {
        String className = "";
        String packageName = "";
        if (result.getTestClass().toString().contains(".")) {
            packageName = result.getTestClass().toString().substring(22, result.getTestClass().toString().length() - 1)
                    .split("\\.")[0];
            className = result.getTestClass().toString().substring(22, result.getTestClass().toString().length() - 1)
                    .split("\\.")[1];
        } else {
            className = result.getTestClass().toString().substring(22, result.getTestClass().toString().length() - 1);
        }
        return General.getTestName(packageName, className, result.getName(), result.getParameters());
    }

    /**
     * Runs the default TestNG onTestFailure, and adds additional information
     * into the testng reporter
     */
    @Override
    public void onTestFailure(ITestResult test) {
        super.onTestFailure(test);

        String testName = getTestName(test);
        Browser browser = (Browser) test.getAttribute(BROWSER_INPUT);
        Reporter.log(Result.values()[test.getStatus()] + OUTPUT_BREAK + browser + OUTPUT_BREAK + LINK_START
                + getFolderName(test) + "/" + testName + browser + LINK_MIDDLE + testName + LINK_END + OUTPUT_BREAK
                + (test.getEndMillis() - test.getStartMillis()) / 1000 + TIME_UNIT);
    }

    /**
     * Runs the default TestNG onTestSkipped, and adds additional information
     * into the testng reporter
     */
    @Override
    public void onTestSkipped(ITestResult test) {
        super.onTestSkipped(test);
        String testName = getTestName(test);
        Browser browser = (Browser) test.getAttribute(BROWSER_INPUT);
        Reporter.log(Result.values()[test.getStatus()] + OUTPUT_BREAK + browser + OUTPUT_BREAK + LINK_START
                + getFolderName(test) + "/" + testName + browser + LINK_MIDDLE + testName + LINK_END + OUTPUT_BREAK
                + (test.getEndMillis() - test.getStartMillis()) / 1000 + TIME_UNIT);
    }

    /**
     * Runs the default TestNG onTestSuccess, and adds additional information
     * into the testng reporter
     */
    @Override
    public void onTestSuccess(ITestResult test) {
        super.onTestSuccess(test);
        String testName = getTestName(test);
        Browser browser = (Browser) test.getAttribute(BROWSER_INPUT);
        Reporter.log(Result.values()[test.getStatus()] + OUTPUT_BREAK + browser + OUTPUT_BREAK + LINK_START
                + getFolderName(test) + "/" + testName + browser + LINK_MIDDLE + testName + LINK_END + OUTPUT_BREAK
                + (test.getEndMillis() - test.getStartMillis()) / 1000 + TIME_UNIT);
    }
}