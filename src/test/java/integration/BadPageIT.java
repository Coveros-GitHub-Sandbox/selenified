package integration;

import java.io.IOException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import tools.TestBase;

public class BadPageIT extends TestBase {

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		// set the base URL for the tests here
		testSite = "http://172.31.2.64/";
		// set the author of the tests here
		author = "Max Saperstone\n<br/>max.saperstone@coveros.com";
		// set the version of the tests or of the software, possibly with a
		// dynamic check
		version = "0.0.1";
	}

	@Test(groups = { "integration",
			"virtual" }, description = "An integration test to verify we fail when the url doesn't exist")
	public void verifyBadPage() throws IOException {
		// verify one issue
		finish(1);
	}
}