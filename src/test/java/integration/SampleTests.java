package integration;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Assert;
import tools.output.Element;
import tools.output.Selenium.Locators;

public class SampleTests extends TestBase {

	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws IOException {
		// set the base URL for the tests here
		setTestSite("http://172.31.2.65/");
		// set the author of the tests here
		setAuthor("Max Saperstone\n<br/>max.saperstone@coveros.com");
		// set the version of the tests or of the software, possibly with a
		// dynamic check
		setVersion("0.0.1");
	}

	@DataProvider(name = "car list items", parallel = true)
	public Object[][] DataSetOptions() {
		return new Object[][] { new Object[] { "volvo" }, new Object[] { "saab" }, new Object[] { "mercedes" } };
	}

	@Test(groups = { "sample", "virtual" }, description = "A sample test to check a title")
	public void sampleTest() throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform our verification
		asserts.compareTitle("Selenified Test Page");
		// perform our verification
		finish();
	}

	@Test(dataProvider = "car list items", groups = { "sample",
			"virtual" }, description = "A sample test using a data provider to perform searches")
	public void sampleTestWDataProvider(String listItem) throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(Locators.ID, "car_list", listItem);
		// close out the test
		finish();
	}

	@Test(groups = { "sample",
			"virtual" }, description = "A sample test to show how to loop through elements with multiple matches")
	public void sampleTestLoopThroughElements() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		Element element = new Element(Locators.XPATH, "//form/input[@type='checkbox']");
		for (int option = 0; option < actions.getElementMatchCount(element); option++) {
			actions.click(element, option);
			asserts.checkElementChecked(element, option);
		}
		// close out the test
		finish();
	}
}
