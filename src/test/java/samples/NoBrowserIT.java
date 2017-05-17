package samples;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Selenium.DriverSetup;

public class NoBrowserIT extends TestBase {

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		// set the base URL for the tests here
		testSite = "http://172.31.2.65/";
		// set the author of the tests here
		author = "Max Saperstone\n<br/>max.saperstone@coveros.com";
		// set the version of the tests or of the software, possibly with a
		// dynamic check
		version = "0.0.1";
	}

	@BeforeMethod(alwaysRun = true)
	protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result)
			throws IOException {
		super.startTest(dataProvider, method, test, result, DriverSetup.FALSE);
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample test to verify we can start a test without a browser")
	public void verifyNoBrowser() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// verify no selenium actions class was setup
		Assert.assertNull(actions);
		// verify no issues
		finish();
	}
}