package samples;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Assert;
import tools.output.Action.Locators;

public class SampleIT extends TestBase {

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws Exception {
		// set the base URL for the tests here
		testSite = "http://www.google.com/";
		// set the author of the tests here
		author = "Max Saperstone\n<br/>max.saperstone@coveros.com";
		// set the version of the tests or of the software, possibly with a
		// dynamic check
		version = "0.0.1";
	}

	@DataProvider(name = "google search terms", parallel = true)
	public Object[][] DataSetOptions() {
		return new Object[][] { new Object[] { "python" }, new Object[] { "perl" }, new Object[] { "bash" } };
	}

	@Test(groups = { "sample" }, description = "A sample test to check a title")
	public void sampleTest() throws Exception {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform our verification
		asserts.compareTitle("Google");
		// close out the test
		finish();
	}

	@Test(dataProvider = "google search terms", groups = {
			"sample" }, description = "A sample test using a data provider to perform searches")
	public void sampleTestWDataProvider(String searchTerm) throws Exception {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.name, "q", searchTerm);
		actions.click(Locators.name, "btnG");
		actions.waitForElementDisplayed(Locators.id, "resultStats");
		// perform our verification
		asserts.compareTitle(searchTerm + " - Google Search");
		// close out the test
		finish();
	}
}