package tools;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import tools.logging.TestOutput.Result;

public class Listener extends TestListenerAdapter {
	
	//TODO - set folder to relative, instead of absolute

	@Override
	public void onTestFailure(ITestResult test) {
		String testName = getTestName(test);
		System.out.println("Test " + testName + " failed");
		String folder = test.getTestContext().getOutputDirectory();
		Reporter.log(Result.values()[test.getStatus()] + " | " + "<a href='" + folder + "/" + testName + ".html'>"
				+ testName + "</a>" + " | " + (test.getEndMillis() - test.getStartMillis()) / 1000 + " seconds");
	}

	@Override
	public void onTestSkipped(ITestResult test) {
		String testName = getTestName(test);
		System.out.println("Test " + testName + " skipped");
		String folder = test.getTestContext().getOutputDirectory();
		Reporter.log(Result.values()[test.getStatus()] + " | " + "<a href='" + folder + "/" + testName + ".html'>"
				+ testName + "</a>" + " | " + (test.getEndMillis() - test.getStartMillis()) / 1000 + " seconds");
	}

	@Override
	public void onTestSuccess(ITestResult test) {
		String testName = getTestName(test);
		System.out.println("Test " + testName + " passed");
		String folder = test.getTestContext().getOutputDirectory();
		Reporter.log(Result.values()[test.getStatus()] + " | " + "<a href='" + folder + "/" + testName + ".html'>"
				+ testName + "</a>" + " | " + (test.getEndMillis() - test.getStartMillis()) / 1000 + " seconds");
	}

	private static String getTestName(ITestResult result) {
		String testName = result.getName();
		Object[] parameters = result.getParameters();
		if (parameters != null && parameters.length > 0 && parameters[0] != null
				&& !parameters[0].toString().startsWith("public")) {
			testName += "WithOption";
			for (Object data : parameters) {
				if (data == null || data.toString().startsWith("public")) {
					break;
				}
				testName += General.capitalizeFirstLetters(General.removeNonWordCharacters(data.toString()));
			}
		}

		return testName;
	}
}
