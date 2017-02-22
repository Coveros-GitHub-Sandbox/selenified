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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tools.logging.TestOutput;
import tools.selenium.SeleniumHelper;
import tools.selenium.SeleniumHelper.Browsers;
import tools.logging.TestOutput.Result;

public class TestBase {

	public static General gen = new General();
	public static GeneralFunctions genFun = new GeneralFunctions();

	public static String testSite = "http://www.google.com/";
	public String author = "Max Saperstone";
	public static String version;

	@DataProvider(name = "no options", parallel = true)
	public Object[][] NoOptions() {
		return new Object[][] { new Object[] { "" }, };
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

		public void doSetup() throws Exception {
			if (wasInvoked) {
				return;
			}
			initializeSystem();
			// System.out.println("beforeSuite executed");
			wasInvoked = true;
		}
	}

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() throws Exception {
		MasterSuiteSetupConfigurator.getInstance().doSetup();
	}

	@BeforeMethod(alwaysRun = true)
	protected void startTest(Object[] dataProvider, Method method, ITestContext test) throws Exception {
		startTest(dataProvider, method, test, true);
	}

	protected static void initializeSystem() throws Exception {
		// check our browser
		if (System.getProperty("browser") == null || System.getProperty("browser").equals("${browser}")) {
			System.setProperty("browser", Browsers.HtmlUnit.toString());
			// throw new InvalidBrowserException(
			// "Please indicate which browser to test with by passing in the " +
			// "browser as a '-Dbrowser' argument" );
		}
		// see if we are using a selenium hub
		if (System.getProperty("hubAddress") == null || System.getProperty("hubAddress").equals("${hubAddress}")) {
			System.setProperty("hubAddress", "LOCAL");
		}
		// check to see if we are passing in a site address
		if (System.getProperty("appURL") != null && !System.getProperty("appURL").equals("${appURL}")) {
			testSite = System.getProperty("appURL");
		}
	}

	protected void startTest(Object[] dataProvider, Method method, ITestContext test, boolean selenium)
			throws Exception {
		String testName = getTestName(method, dataProvider);
		System.out.println("Starting test " + testName);
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

		TestOutput output = new TestOutput(testName, outputDir, testSite, suite, gen.wordToSentence(group),
				lastModified, version, author, description);
		long time = (new Date()).getTime();
		output.setStartTime(time);
		if (selenium) {
			SeleniumHelper selHelper = new SeleniumHelper(output);
			test.setAttribute(testName + "SelHelper", selHelper);
		}
		test.setAttribute(testName + "Output", output);
		test.setAttribute(testName + "Errors", output.startTestTemplateOutputFile(selenium));
	}

	@AfterMethod(alwaysRun = true)
	protected void endTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result)
			throws Exception {
		String testLink = getTestName(method, dataProvider);
		String testName = method.getName();
		if (dataProvider != null) {
			testName += " : ";
			for (Object data : dataProvider) {
				if (data == null || data.toString().startsWith("public")) {
					break;
				}
				testName += data.toString() + ", ";
			}
			testName.substring(0, testName.length() - 2);
		}
		if (test.getAttribute(testLink + "SelHelper") != null) {
			SeleniumHelper selHelper = (SeleniumHelper) test.getAttribute(testLink + "SelHelper");
			selHelper.killDriver();
		}
		// TestOutput output = (TestOutput) test
		// .getAttribute(testLink + "Output");
		// genFun.stopTest(output);

		String colClass = "";
		if (result.getStatus() == 1) {
			colClass = "invocation-passed";
		}
		if (result.getStatus() == 2) {
			colClass = "invocation-failed";
		}
		if (result.getStatus() == 3) {
			colClass = "invocation-skipped";
		}
		Reporter.log("<span class='" + colClass + "'>" + Result.values()[result.getStatus()] + "</span></td><td>"
				+ "<a href='" + testLink + ".html'>" + testName + "</a>" + "</td><td>"
				+ (result.getEndMillis() - result.getStartMillis()) / 1000 + " seconds");
		System.out.println("Finished test " + testLink + " with status " + result.getStatus());
	}

	protected void finalize(TestOutput output) throws IOException {
		genFun.stopTest(output);
	}

	@AfterClass(alwaysRun = true)
	protected void endClass() throws Exception {
		System.out.println("Finished class");
	}

	@AfterTest(alwaysRun = true)
	protected void endTest() throws Exception {
		System.out.println("Finished testing group");
	}

	@AfterSuite(alwaysRun = true)
	protected void archiveTestResults() throws Exception {
		System.out.println("\nREMEMBER TO ARCHIVE YOUR TESTS!\n\n");
	}

	protected static String getTestName(Method method, Object... dataProvider) {
		String testName = method.getName();
		if (dataProvider != null) {
			if (dataProvider.length > 0) {
				if (dataProvider[0] != null && !dataProvider[0].toString().startsWith("public")) {
					testName += "WithOption";
					for (Object data : dataProvider) {
						if (data == null || data.toString().startsWith("public")) {
							break;
						}
						testName += gen.capitalizeFirstLetters(gen.removeNonWordCharacters(data.toString()));
					}
				}
			}
		}
		return testName;
	}

	protected static SeleniumHelper getSelHelper(Method method, ITestContext test, Object... dataProvider) {
		String testName = getTestName(method, dataProvider);
		return (SeleniumHelper) test.getAttribute(testName + "SelHelper");
	}

	protected static TestOutput getTestOutput(Method method, ITestContext test, Object... dataProvider) {
		String testName = getTestName(method, dataProvider);
		return (TestOutput) test.getAttribute(testName + "Output");
	}

	protected static int getErrors(Method method, ITestContext test, Object... dataProvider) {
		String testName = getTestName(method, dataProvider);
		return (Integer) test.getAttribute(testName + "Errors");
	}
}