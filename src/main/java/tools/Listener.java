package tools;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import tools.logging.TestOutput.Result;
import tools.selenium.SeleniumHelper.Browsers;

import java.io.File;

public class Listener extends TestListenerAdapter {
	
	private static final String BROWSER_INPUT = "browser";
	private static final String OUTPUT_BREAK = " | ";
	private static final String FILE_EXTENTION = "html";
	private static final String LINK_START = "<a target='_blank' href='";
	private static final String LINK_MIDDLE = "." + FILE_EXTENTION + "'>";
	private static final String LINK_END = "</a>";
	private static final String TIME_UNIT = " seconds";
	

	private static String getFolderName(ITestResult test) {
		return new File(test.getTestContext().getOutputDirectory()).getName();
	}

	private static String getTestName(ITestResult result) {
		return General.getTestName(result.getName(), result.getParameters());
	}

	@Override
	public void onTestFailure(ITestResult test) {
		super.onTestFailure(test);

		String testName = getTestName(test);
		Browsers browser = (Browsers) test.getAttribute(BROWSER_INPUT);
		Reporter.log(Result.values()[test.getStatus()] + OUTPUT_BREAK + browser + OUTPUT_BREAK + LINK_START
				+ getFolderName(test) + "/" + testName + browser + LINK_MIDDLE + testName + LINK_END + OUTPUT_BREAK
				+ (test.getEndMillis() - test.getStartMillis()) / 1000 + TIME_UNIT);
	}

	@Override
	public void onTestSkipped(ITestResult test) {
		super.onTestSkipped(test);
		String testName = getTestName(test);
		Browsers browser = (Browsers) test.getAttribute(BROWSER_INPUT);
		Reporter.log(Result.values()[test.getStatus()] + OUTPUT_BREAK + browser + OUTPUT_BREAK + LINK_START
				+ getFolderName(test) + "/" + testName + browser + LINK_MIDDLE + testName + LINK_END + OUTPUT_BREAK
				+ (test.getEndMillis() - test.getStartMillis()) / 1000 + TIME_UNIT);
	}

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