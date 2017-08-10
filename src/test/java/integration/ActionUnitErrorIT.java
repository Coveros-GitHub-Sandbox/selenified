package integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import org.testng.Assert;

import com.coveros.selenified.selenium.Page;
import com.coveros.selenified.selenium.Selenium.Browser;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.Selenified;

public class ActionUnitErrorIT extends Selenified {

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		// set the base URL for the tests here
		setTestSite("http://172.31.2.65/");
		// set the author of the tests here
		setAuthor("Max Saperstone\n<br/>max.saperstone@coveros.com");
		// set the version of the tests or of the software, possibly with a
		// dynamic check
		setVersion("0.0.1");
	}

	@Test(groups = { "integration", "actions", "unit",
			"virtual" }, description = "An integration negative test to check the get css method")
	public void getCssErrorTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		if (page.getBrowser().equals(Browser.HTMLUNIT)) {
			String css = page.newElement(Locator.CSS, "input#alert_button").get().css("style");
			Assert.assertNull(css);
		}
		finish();
	}
	
	@Test(groups = { "integration", "actions", "unit",
	"virtual" }, description = "An integration negative test to check the get all attributes method")
	public void getAllAttributesErrorTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		if (page.getBrowser().equals(Browser.HTMLUNIT)) {
			Map<String, String> attributes = page.newElement(Locator.CSS, "input#alert_button").get().allAttributes();
			Assert.assertNull(attributes);
		}
		finish();
	}
	
	@Test(groups = { "integration", "actions", "unit",
	"virtual" }, description = "An integration negative test to check the get eval method")
	public void getEvalErrorTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		if (page.getBrowser().equals(Browser.HTMLUNIT)) {
			Object eval = page.get().eval("console.out('hello world')");
			Assert.assertNull(eval);
		}
		finish();
	}
	
	@Test(groups = { "integration", "actions", "unit",
	"virtual" }, description = "An integration negative test to check the get eval method")
	public void getEvalElementErrorTest() {
		// use this object to manipulate the page
		Page page = this.pages.get();
		// perform some actions
		if (page.getBrowser().equals(Browser.HTMLUNIT)) {
			Object eval = page.newElement(Locator.CSS, "input#alert_button").get().eval("console.out('hello world')");
			Assert.assertNull(eval);
		}
		finish();
	}
}