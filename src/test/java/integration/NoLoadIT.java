package integration;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Assert;
import tools.output.Selenium.DriverSetup;

public class NoLoadIT extends TestBase {

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		// set the base URL for the tests here
		setTestSite("http://172.31.2.65/");
		// set the author of the tests here
		setAuthor("Max Saperstone\n<br/>max.saperstone@coveros.com");
		// set the version of the tests or of the software, possibly with a
		// dynamic check
		setVersion("0.0.1");
	}

	@BeforeMethod(alwaysRun = true)
	protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result)
			throws IOException {
		super.startTest(dataProvider, method, test, result, DriverSetup.OPEN);
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to verify we can start a test with a browser, but won't load any page")
	public void verifyNoLoad() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// verify a selenium actions class was setup
		org.testng.Assert.assertNotNull(actions);
		org.testng.Assert.assertEquals(
				asserts.getOutputFile().countInstancesOf("Opening new browser and loading up starting page"), 0);
		// verify the page wasn't attempted to load
		asserts.compareURL("http://172.31.2.65/");
		// verify one issue from the above check
		finish(1);
	}
}