package tools;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import tools.logging.TestOutput.Result;

import java.io.File;

public class Listener extends TestListenerAdapter {

    private static String getFolderName(ITestResult test) {
        return new File(test.getTestContext().getOutputDirectory()).getName();
    }

    private static String getTestName(ITestResult result) {
        return TestBase.getTestName( result.getName(), result.getParameters() );
    }

    @Override
    public void onTestFailure(ITestResult test) {
        String testName = getTestName(test);
        System.out.println("Test " + testName + " failed");
        Reporter.log(Result.values()[test.getStatus()] + " | " + "<a target='_blank' href='" + getFolderName(test) + "/" + testName + ".html'>"
                + testName + "</a>" + " | " + (test.getEndMillis() - test.getStartMillis()) / 1000 + " seconds");
    }

    @Override
    public void onTestSkipped(ITestResult test) {
        String testName = getTestName(test);
        System.out.println("Test " + testName + " skipped");
        Reporter.log(Result.values()[test.getStatus()] + " | " + "<a target='_blank' href='" + getFolderName(test) + "/" + testName + ".html'>"
                + testName + "</a>" + " | " + (test.getEndMillis() - test.getStartMillis()) / 1000 + " seconds");
    }

    @Override
    public void onTestSuccess(ITestResult test) {
        String testName = getTestName(test);
        System.out.println("Test " + testName + " passed");
        Reporter.log(Result.values()[test.getStatus()] + " | " + "<a target='_blank' href='" + getFolderName(test) + "/" + testName + ".html'>"
                + testName + "</a>" + " | " + (test.getEndMillis() - test.getStartMillis()) / 1000 + " seconds");
    }
}