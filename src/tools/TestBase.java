package tools;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
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

	protected static void initializeSystem() throws Exception {
		// check our browser
		if (System.getProperty("browser") == null
				|| System.getProperty("browser").equals("${browser}")) {
			System.setProperty("browser", Browsers.HtmlUnit.toString());
			// throw new InvalidBrowserException(
			// "Please indicate which browser to test with by passing in the " +
			// "browser as a '-Dbrowser' argument" );
		}
		// see if we are using a selenium hub
		if (System.getProperty("hubAddress") == null
				|| System.getProperty("hubAddress").equals("${hubAddress}")) {
			System.setProperty("hubAddress", "LOCAL");
		}
		// check to see if we are passing in a site address
		if (System.getProperty("appURL") != null
				&& !System.getProperty("appURL").equals("${appURL}")) {
			testSite = System.getProperty("appURL");
		}
	}

	// @BeforeClass(alwaysRun = true, description = "")
	// protected void setupSystemForTests() throws Exception {
	// }
	//
	// @AfterClass(alwaysRun = true, description = "")
	// protected void cleanUpSystemFromTests() throws Exception {
	// }

	// @BeforeMethod (alwaysRun = false)
	protected void startTest(Object[] dataProvider, Method method,
			ITestContext test, boolean selenium) throws Exception {
		String testName = getTestName(method, dataProvider);
		String suite = test.getName();
		String outputDir = test.getOutputDirectory();
		String extClass = test.getCurrentXmlTest().getXmlClasses().get(0)
				.getName();
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

		TestOutput output = new TestOutput(testName, outputDir, testSite,
				suite, gen.wordToSentence(group), lastModified, version,
				author, description);
		long time = (new Date()).getTime();
		output.setStartTime(time);
		if (selenium) {
			SeleniumHelper selHelper = new SeleniumHelper(output);
			test.setAttribute(testName + "SelHelper", selHelper);
		}
		test.setAttribute(testName + "Output", output);
		test.setAttribute(testName + "Errors",
				output.startTestTemplateOutputFile(selenium));
	}

	@AfterMethod(alwaysRun = true)
	protected void endTest(Object[] dataProvider, Method method,
			ITestContext test, ITestResult result) throws Exception {
		// System.out.println("GOT TO LINE 1");
		String testLink = getTestName(method, dataProvider);
		// System.out.println("GOT TO LINE 2");
		String testName = method.getName();
		// System.out.println("GOT TO LINE 3");
		if (dataProvider != null) {
			// System.out.println("GOT TO LINE 4");
			testName += " : ";
			// System.out.println("GOT TO LINE 5");
			for (Object data : dataProvider) {
				// System.out.println("GOT TO LINE 6");
				if (data == null || data.toString().startsWith("public")) {
					// System.out.println("GOT TO LINE 7");
					break;
				}
				// System.out.println("GOT TO LINE 8");
				testName += data.toString() + ", ";
				// System.out.println("GOT TO LINE 9");
			}
			// System.out.println("GOT TO LINE 10");
			testName.substring(0, testName.length() - 2);
			// System.out.println("GOT TO LINE 11");
		}
		// System.out.println("GOT TO LINE 12");
		if (test.getAttribute(testLink + "SelHelper") != null) {
			// System.out.println("GOT TO LINE 13");
			SeleniumHelper selHelper = (SeleniumHelper) test
					.getAttribute(testLink + "SelHelper");
			// System.out.println("GOT TO LINE 14");
			selHelper.killDriver();
			// System.out.println("GOT TO LINE 15");
		}
		// System.out.println("GOT TO LINE 16");

		String colClass = "";
		// System.out.println("GOT TO LINE 17");
		if (result.getStatus() == 1) {
			colClass = "invocation-passed";
			// System.out.println("GOT TO LINE 18");
		}
		// System.out.println("GOT TO LINE 19");
		if (result.getStatus() == 2) {
			// System.out.println("GOT TO LINE 20");
			colClass = "invocation-failed";
		}
		// System.out.println("GOT TO LINE 21");
		if (result.getStatus() == 3) {
			// System.out.println("GOT TO LINE 22");
			colClass = "invocation-skipped";
		}
		// System.out.println("GOT TO LINE 23");
		Reporter.log("<span class='" + colClass + "'>"
				+ Result.values()[result.getStatus()] + "</span></td><td>"
				+ "<a href='" + testLink + ".html'>" + testName + "</a>"
				+ "</td><td>"
				+ (result.getEndMillis() - result.getStartMillis()) / 1000
				+ " seconds");
		// System.out.println("GOT TO LINE 24");
	}

	protected static String getTestName(Method method, Object... dataProvider) {
		String testName = method.getName();
		if (dataProvider != null) {
			if (dataProvider.length > 0) {
				if (dataProvider[0] != null
						&& !dataProvider[0].toString().startsWith("public")) {
					testName += "WithOption";
					for (Object data : dataProvider) {
						if (data == null
								|| data.toString().startsWith("public")) {
							break;
						}
						testName += gen.capitalizeFirstLetters(gen
								.removeNonWordCharacters(data.toString()));
					}
				}
			}
		}
		return testName;
	}

	@AfterSuite(alwaysRun = true)
	protected void archiveTestResults() throws Exception {
		System.out.println("\nREMEMBER TO ARCHIVE YOUR TESTS!\n\n");
	}
}