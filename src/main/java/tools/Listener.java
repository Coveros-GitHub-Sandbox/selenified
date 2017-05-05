package tools;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import tools.output.Action.Browsers;
import tools.output.Assert.Result;

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
		return General.getTestName(result.getName(), result.getParameters());
	}

	/**
	 * Runs the default TestNG onTestFailure, and adds additional information
	 * into the testng reporter
	 */
	@Override
	public void onTestFailure(ITestResult test) {
		super.onTestFailure(test);

		String testName = getTestName(test);
		Browsers browser = (Browsers) test.getAttribute(BROWSER_INPUT);
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
		Browsers browser = (Browsers) test.getAttribute(BROWSER_INPUT);
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
		Browsers browser = (Browsers) test.getAttribute(BROWSER_INPUT);
		Reporter.log(Result.values()[test.getStatus()] + OUTPUT_BREAK + browser + OUTPUT_BREAK + LINK_START
				+ getFolderName(test) + "/" + testName + browser + LINK_MIDDLE + testName + LINK_END + OUTPUT_BREAK
				+ (test.getEndMillis() - test.getStartMillis()) / 1000 + TIME_UNIT);
	}
}