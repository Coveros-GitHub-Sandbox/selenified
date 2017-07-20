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

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.log4testng.Logger;

import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.output.Assert;
import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Selenium.Browser;
import com.coveros.selenified.selenium.Selenium.DriverSetup;

import static org.testng.AssertJUnit.assertEquals;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TestBase contains all of the elements to setup the test suite, and to start
 * and finish your tests. The site under test is set here should be set,
 * otherwise a default value of google will be used. Before each suite is run,
 * the system variables are gathered, to set the browser, test site, proxy, hub,
 * etc. This class should be extended by each test class to allow for simple
 * execution of tests.
 * 
 * By default each test run will launch a selenium browser, and open the defined
 * test site. If no browser is needed for the test, override the startTest
 * method. Similarly, if you don't want a URL to initially load, override the
 * startTest method.
 *
 * @author Max Saperstone
 * @version 2.0.1
 * @lastupdate 7/20/2017
 */
@Listeners({ com.coveros.selenified.tools.Listener.class, com.coveros.selenified.tools.Transformer.class })
public class TestBase {

	private static final Logger log = Logger.getLogger(General.class);

	private static String testSite = "https://www.google.com/";
	private static String version = "";
	private static String author = "";

	protected static DesiredCapabilities extraCapabilities = null;

	// some passed in system params
	protected static List<Browser> browsers;
	protected static List<DesiredCapabilities> capabilities = new ArrayList<>();

	// for individual tests
	protected ThreadLocal<Browser> browser = new ThreadLocal<>();
	protected ThreadLocal<DesiredCapabilities> capability = new ThreadLocal<>();
	protected ThreadLocal<Assert> asserts = new ThreadLocal<>();
	protected ThreadLocal<Action> actions = new ThreadLocal<>();
	protected ThreadLocal<Integer> errors = new ThreadLocal<>();

	// constants
	private static final String APP_INPUT = "appURL";
	private static final String BROWSER_INPUT = "browser";
	private static final String INVOCATION_COUNT = "InvocationCount";
	private static final String ERRORS_CHECK = " errors";

	// default getters and setters for test information
	public String getTestSite() {
		return testSite;
	}

	public static void setTestSite(String siteURL) {
		if (System.getProperty(APP_INPUT) == null) {
			testSite = siteURL;
		}
	}

	private static void passedInTestSite(String siteURL) {
		testSite = siteURL;
	}

	public String getVersion() {
		return version;
	}

	public static void setVersion(String ver) {
		version = ver;
	}

	public String getAuthor() {
		return author;
	}

	public static void setAuthor(String auth) {
		author = auth;
	}

	/**
	 * Initializes the test settings by setting default values for the browser
	 * and URL if they are not specifically set
	 */
	public static void initializeSystem() {
		// check the browser
		if (System.getProperty(BROWSER_INPUT) == null) {
			System.setProperty(BROWSER_INPUT, Browser.HTMLUNIT.toString());
		}
		if (System.getProperty(APP_INPUT) != null) {
			passedInTestSite(System.getProperty(APP_INPUT));
		}
	}

	/**
	 * Obtains passed in browser information, and sets up the required
	 * capabilities
	 * 
	 * @throws InvalidBrowserException
	 *             If a browser that is not one specified in the
	 *             Selenium.Browser class is used, this exception will be thrown
	 */
	public static void setupTestParameters() throws InvalidBrowserException {
		browsers = TestSetup.setBrowser();

		for (Browser browser : browsers) {
			TestSetup setup = new TestSetup();
			// are we running remotely on a hub
			if (System.getProperty("hub") != null) {
				setup.setupBrowserCapability(browser);
			}
			setup.setupProxy();
			if (TestSetup.areBrowserDetailsSet()) {
				Map<String, String> browserDetails = General.parseMap(System.getProperty(BROWSER_INPUT));
				setup.setupBrowserDetails(browserDetails);
			}
			DesiredCapabilities caps = setup.getDesiredCapabilities();
			if (extraCapabilities != null) {
				caps = caps.merge(extraCapabilities);
			}
			capabilities.add(caps);
		}
	}

	@DataProvider(name = "no options", parallel = true)
	public Object[][] noOptions() {
		return new Object[][] { new Object[] { "" }, };
	}

	/**
	 * Runs once before any of the tests run, to parse and setup the static
	 * passed information such as browsers, proxy, hub, etc
	 * 
	 * @throws InvalidBrowserException
	 *             If a browser that is not one specified in the
	 *             Selenium.Browser class is used, this exception will be thrown
	 */
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() throws InvalidBrowserException {
		MasterSuiteSetupConfigurator.getInstance().doSetup();
	}

	/**
	 * Before any tests run, setup the logging and test details. If a selenium
	 * test is being run, it sets up the driver as well
	 * 
	 * @param dataProvider
	 *            - any objects that are being passed to the tests to loop
	 *            through as variables
	 * @param method
	 *            - what is the method that is being run. the test name will be
	 *            extracted from this
	 * @param test
	 *            - was the is context associated with this test suite. suite
	 *            information will be extracted from this
	 * @param result
	 *            - where are the test results stored. browser information will
	 *            be kept here
	 */
	@BeforeMethod(alwaysRun = true)
	protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
		startTest(dataProvider, method, test, result, DriverSetup.LOAD);
	}

	/**
	 * gathers all of the testing information, and setup up the logging. If a
	 * selenium test is running, also sets up the webdriver object
	 * 
	 * @param dataProvider
	 *            - any objects that are being passed to the tests to loop
	 *            through as variables
	 * @param method
	 *            - what is the method that is being run. the test name will be
	 *            extracted from this
	 * @param test
	 *            - was the is context associated with this test suite. suite
	 *            information will be extracted from this
	 * @param result
	 *            - where are the test results stored. browser information will
	 *            be kept here
	 * @param selenium
	 *            - is this a selenium test. if so, the webdriver content will
	 *            be setup
	 */
	protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result,
			DriverSetup selenium) {
		String testName = General.getTestName(method, dataProvider);
		String outputDir = test.getOutputDirectory();
		String extClass = test.getCurrentXmlTest().getXmlClasses().get(0).getName();
		String fileLocation = "src." + extClass;
		File file = new File("./" + fileLocation.replaceAll("\\.", "/") + ".java");
		String description = "";
		String group = "";
		Test annotation = method.getAnnotation(Test.class);
		// set description from annotation
		if (annotation.description() != null) {
			description = annotation.description();
		}
		// adding in the group if it exists
		if (annotation.groups() != null) {
			group = Arrays.toString(annotation.groups());
			group = group.substring(1, group.length() - 1);
		}

		while (test.getAttribute(testName + INVOCATION_COUNT) == null) {
			test.setAttribute(testName + INVOCATION_COUNT, 0);
		}
		int invocationCount = (int) test.getAttribute(testName + INVOCATION_COUNT);

		Browser myBrowser = browsers.get(invocationCount);
		this.browser.set(myBrowser);
		result.setAttribute(BROWSER_INPUT, myBrowser);

		DesiredCapabilities myCapability = capabilities.get(invocationCount);
		myCapability.setCapability("name", testName);
		this.capability.set(myCapability);

		Assert myOutput = new Assert(testName, myBrowser, outputDir);
		OutputFile myFile = myOutput.getOutputFile();
		myFile.setURL(testSite);
		myFile.setSuite(test.getName());
		myFile.setGroup(group);
		if (file.exists()) {
			myFile.setLastModified(new Date(file.lastModified()));
		}
		myFile.setVersion(version);
		myFile.setAuthor(author);
		myFile.setObjectives(description);
		myFile.setStartTime();
		if (selenium.useBrowser()) {
			try {
				Action action = new Action(myBrowser, myCapability, myFile);
				this.actions.set(action);
				myFile.setAction(action);
				myOutput.setSeleniumHelper(action);
			} catch (InvalidBrowserException | MalformedURLException e) {
				log.error(e);
			}
		}
		myFile.createOutputHeader();
		if (selenium.loadPage()) {
			this.errors.set(myFile.loadInitialPage());
		} else {
			this.errors.set(0);
		}
		myOutput.setOutputFile(myFile);
		this.asserts.set(myOutput);
	}

	/**
	 * after each test is completed, the test is closed out, and the test
	 * counter is incremented
	 * 
	 * @param dataProvider
	 *            - any objects that are being passed to the tests to loop
	 *            through as variables
	 * @param method
	 *            - what is the method that is being run. the test name will be
	 *            extracted from this
	 * @param test
	 *            - was the is context associated with this test suite. suite
	 *            information will be extracted from this
	 * @param result
	 *            - where are the test results stored. browser information will
	 *            be kept here
	 */
	@AfterMethod(alwaysRun = true)
	public void endTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
		String testName = General.getTestName(method, dataProvider);
		if (this.actions.get() != null) {
			this.actions.get().killDriver();
		}
		int invocationCount = (int) test.getAttribute(testName + INVOCATION_COUNT);
		test.setAttribute(testName + INVOCATION_COUNT, invocationCount + 1);
	}

	/**
	 * to conclude each test, run this finish command. it will close out the
	 * output logging file, and count any errors that were encountered during
	 * the test, and fail the test if any errors were encountered
	 * 
	 */
	public void finish() {
		OutputFile myFile = this.asserts.get().getOutputFile();
		myFile.endTestTemplateOutputFile();
		assertEquals("Detailed results found at: " + myFile.getFileName(), "0 errors",
				Integer.toString(myFile.getErrors()) + ERRORS_CHECK);
	}

	/**
	 * to conclude each test, run this finish command. it will close out the
	 * output logging file, and count any errors that were encountered during
	 * the test, and verify that the number of errors indicated occurred
	 * 
	 * @param errors
	 *            - number of expected errors from the test
	 */
	protected void finish(int errors) {
		OutputFile myFile = this.asserts.get().getOutputFile();
		myFile.endTestTemplateOutputFile();
		assertEquals("Detailed results found at: " + myFile.getFileName(), errors + ERRORS_CHECK,
				Integer.toString(myFile.getErrors()) + ERRORS_CHECK);
	}

	public static class MasterSuiteSetupConfigurator {
		private static MasterSuiteSetupConfigurator instance;
		private boolean wasInvoked = false;

		private MasterSuiteSetupConfigurator() {
		}

		/**
		 * Runs once before any of the tests run, to parse and setup the static
		 * passed information such as browsers, proxy, hub, etc
		 * 
		 * @return null
		 */
		public static MasterSuiteSetupConfigurator getInstance() {
			if (instance != null) {
				return instance;
			}
			instance = new MasterSuiteSetupConfigurator();
			return instance;
		}

		/**
		 * Runs once before any of the tests run, to parse and setup the static
		 * passed information such as browsers, proxy, hub, etc
		 * 
		 * @throws InvalidBrowserException
		 *             If a browser that is not one specified in the
		 *             Selenium.Browser class is used, this exception will be
		 *             thrown
		 */
		public void doSetup() throws InvalidBrowserException {
			if (wasInvoked) {
				return;
			}
			initializeSystem();
			setupTestParameters();
			wasInvoked = true;
		}
	}
}