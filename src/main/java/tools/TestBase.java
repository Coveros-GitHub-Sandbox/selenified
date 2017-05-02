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

package tools;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.log4testng.Logger;
import selenified.exceptions.InvalidBrowserException;
import tools.logging.OutputFile;
import tools.logging.TestOutput;
import tools.selenium.SeleniumHelper;
import tools.selenium.SeleniumSetup;
import tools.selenium.SeleniumHelper.Browsers;

import static org.testng.AssertJUnit.assertEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Listeners({ tools.Listener.class, tools.Transformer.class })
public class TestBase {

	private static final Logger log = Logger.getLogger(General.class);

	protected static String testSite = "http://www.google.com/";
	protected static String version;
	protected static String author = "Max Saperstone";

	// some passed in system params
	protected static List<Browsers> browsers;
	protected static List<DesiredCapabilities> capabilities = new ArrayList<>();

	// for individual tests
	protected ThreadLocal<Browsers> browser = new ThreadLocal<>();
	protected ThreadLocal<DesiredCapabilities> capability = new ThreadLocal<>();
	protected ThreadLocal<TestOutput> output = new ThreadLocal<>();
	protected ThreadLocal<SeleniumHelper> selHelper = new ThreadLocal<>();
	protected ThreadLocal<Integer> errors = new ThreadLocal<>();

	// constants
	private static final String BROWSER_INPUT = "browser";
	private static final String INVOCATION_COUNT = "InvocationCount";

	protected static void initializeSystem() {
		// check our browser
		if (System.getProperty(BROWSER_INPUT) == null) {
			System.setProperty(BROWSER_INPUT, Browsers.HtmlUnit.toString());
		}
		// check to see if we are passing in a site address
		if (System.getProperty("appURL") != null) {
			testSite = System.getProperty("appURL");
		}
	}

	protected static void setupTestParameters() throws InvalidBrowserException {
		browsers = SeleniumSetup.setBrowser();

		// are we running remotely on a hub
		for (Browsers browser : browsers) {
			DesiredCapabilities capability;
			if (System.getProperty("hub") != null) {
				capability = SeleniumSetup.setupBrowserCapability(browser);
			} else {
				capability = new DesiredCapabilities();
			}
			capability = SeleniumSetup.setupProxy(capability);
			if (SeleniumSetup.areBrowserDetailsSet()) {
				Map<String, String> browserDetails = General.parseMap(System.getProperty(BROWSER_INPUT));
				capability = SeleniumSetup.setupBrowserDetails(capability, browserDetails);
			}
			capabilities.add(capability);
		}
	}

	@DataProvider(name = "no options", parallel = true)
	public Object[][] noOptions() {
		return new Object[][] { new Object[] { "" }, };
	}

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() throws InvalidBrowserException {
		MasterSuiteSetupConfigurator.getInstance().doSetup();
	}

	@BeforeMethod(alwaysRun = true)
	protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result)
			throws IOException {
		startTest(dataProvider, method, test, result, true);
	}

	protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result,
			boolean selenium) throws IOException {
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

		if (test.getAttribute(testName + INVOCATION_COUNT) == null) {
			test.setAttribute(testName + INVOCATION_COUNT, 0);
		}
		int invocationCount = (int) test.getAttribute(testName + INVOCATION_COUNT);

		Browsers myBrowser = browsers.get(invocationCount);
		this.browser.set(myBrowser);
		result.setAttribute(BROWSER_INPUT, myBrowser);

		DesiredCapabilities myCapability = capabilities.get(invocationCount);
		myCapability.setCapability("name", testName);
		this.capability.set(myCapability);

		TestOutput myOutput = new TestOutput(testName, myBrowser, outputDir);
		OutputFile myFile = myOutput.getOutputFile();
		myFile.setURL(testSite);
		myFile.setSuite(test.getName());
		myFile.setGroup(group);
		myFile.setLastModified(new Date(file.lastModified()));
		myFile.setVersion(version);
		myFile.setAuthor(author);
		myFile.setObjectives(description);
		long time = (new Date()).getTime();
		myFile.setStartTime(time);
		if (selenium) {
			try {
				SeleniumHelper mySelHelper = new SeleniumHelper(myBrowser, myCapability, myFile);
				this.selHelper.set(mySelHelper);
				myFile.setSeleniumHelper(mySelHelper);
				myOutput.setSeleniumHelper(mySelHelper);
			} catch (InvalidBrowserException | MalformedURLException e) {
				log.error(e);
			}
		}
		this.errors.set(myFile.startTestTemplateOutputFile());
		myOutput.setOutputFile(myFile);
		this.output.set(myOutput);
	}

	@AfterMethod(alwaysRun = true)
	protected void endTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
		String testName = General.getTestName(method, dataProvider);
		if (this.selHelper.get() != null) {
			this.selHelper.get().killDriver();
		}
		int invocationCount = (int) test.getAttribute(testName + INVOCATION_COUNT);
		test.setAttribute(testName + INVOCATION_COUNT, invocationCount + 1);
	}

	protected void finish() throws IOException {
		OutputFile myFile = this.output.get().getOutputFile();
		myFile.endTestTemplateOutputFile();
		assertEquals("Detailed results found at: " + myFile.getFileName(), "0 errors",
				Integer.toString(myFile.getErrors()) + " errors");
	}

	public static class MasterSuiteSetupConfigurator {
		private static MasterSuiteSetupConfigurator instance;
		private boolean wasInvoked = false;

		private MasterSuiteSetupConfigurator() {
		}

		public static MasterSuiteSetupConfigurator getInstance() {
			if (instance != null) {
				return instance;
			}
			instance = new MasterSuiteSetupConfigurator();
			return instance;
		}

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