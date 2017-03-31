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
import tools.logging.TestOutput;
import tools.selenium.SeleniumHelper;
import tools.selenium.SeleniumSetup;
import tools.selenium.SeleniumHelper.Browsers;

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
	protected static GeneralFunctions genFun = new GeneralFunctions();

	protected static String testSite = "http://www.google.com/";
	protected static String version;
	protected String author = "Max Saperstone";

	protected static List<Browsers> browsers;
	protected static List<DesiredCapabilities> capabilities = new ArrayList<>();
	
	// for our individual tests
	protected ThreadLocal<Browsers> browser = new ThreadLocal<>();
	protected ThreadLocal<DesiredCapabilities> capability = new ThreadLocal<>();
	protected ThreadLocal<TestOutput> output = new ThreadLocal<>();
	protected ThreadLocal<SeleniumHelper> selHelper = new ThreadLocal<>();
	protected ThreadLocal<Integer> errors = new ThreadLocal<>();
	
	protected static void initializeSystem() {
		// check our browser
		if (System.getProperty("browser") == null) {
			System.setProperty("browser", Browsers.HtmlUnit.toString());
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
				Map<String, String> browserDetails = General.parseMap(System.getProperty("browser"));
				capability = SeleniumSetup.setupBrowserDetails(capability, browserDetails);
			}
			capabilities.add(capability);
		}
	}
	
	protected static SeleniumHelper getSelHelper(Method method, ITestContext test, Object... dataProvider) {
		String testName = General.getTestName(method, dataProvider);
		return (SeleniumHelper) test.getAttribute(testName + "SelHelper");
	}

	protected static TestOutput getTestOutput(Method method, ITestContext test, Object... dataProvider) {
		String testName = General.getTestName(method, dataProvider);
		return (TestOutput) test.getAttribute(testName + "Output");
	}

	protected static int getErrors(Method method, ITestContext test, Object... dataProvider) {
		String testName = General.getTestName(method, dataProvider);
		return (Integer) test.getAttribute(testName + "Errors");
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
	protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) throws IOException {
		startTest(dataProvider, method, test, result, true);
	}

	protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result, boolean selenium)
			throws IOException {
		String testName = General.getTestName(method, dataProvider);
		String suite = test.getName();
		String outputDir = test.getOutputDirectory();
		String extClass = test.getCurrentXmlTest().getXmlClasses().get(0).getName();
		String fileLocation = "src." + extClass;
		File file = new File(fileLocation.replaceAll("\\.", "/") + ".java");
		Date lastModified = new Date(file.lastModified());
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

		if (test.getAttribute(testName + "InvocationCount") == null) {
			test.setAttribute(testName + "InvocationCount", 0);
		}
		int invocationCount = (int) test.getAttribute(testName + "InvocationCount");
		
		Browsers browser = browsers.get(invocationCount);
		this.browser.set(browser);
		result.setAttribute("browser", browser);
		
		DesiredCapabilities capability = capabilities.get(invocationCount);
		capability.setCapability("name", testName);
		this.capability.set(capability);

		TestOutput output = new TestOutput(testName, browser, outputDir, testSite, suite, General.wordToSentence(group),
				lastModified, version, author, description);
		long time = (new Date()).getTime();
		output.setStartTime(time);
		if (selenium) {
			SeleniumHelper selHelper;

			try {
				selHelper = new SeleniumHelper(browser, capability, output);
				this.selHelper.set(selHelper);
			} catch (InvalidBrowserException | MalformedURLException e) {
				log.error(e);
			}
		}
		this.output.set(output);
		int errors = output.startTestTemplateOutputFile(selenium);
		this.errors.set(errors);
	}

	@AfterMethod(alwaysRun = true)
	protected void endTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
		String testName = General.getTestName(method, dataProvider);
		if (this.selHelper.get() != null) {
			this.selHelper.get().killDriver();
		}
		int invocationCount = (int) test.getAttribute(testName + "InvocationCount");
		test.setAttribute(testName + "InvocationCount", invocationCount + 1);
	}

	protected void finalize(TestOutput output) throws IOException {
		genFun.stopTest(output);
	}

	@AfterSuite(alwaysRun = true)
	protected void archiveTestResults() {
		System.out.println("\nREMEMBER TO ARCHIVE YOUR TESTS!\n\n");
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