package integration;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import tools.TestBase;
import tools.output.Action;
import tools.output.Assert;
import tools.output.Element;
import tools.output.Selenium.Locators;

public class ActionDoIT extends TestBase {

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

	@Test(groups = { "integration", "actions", "do", "url",
			"virtual" }, description = "An integration test to check the goToURL method")
	public void goToURLTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.goToURL("https://www.google.com/");
		asserts.compareURL("https://www.google.com/");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "url",
			"virtual" }, description = "An integration negative test to check the goToURL method")
	public void negativeGoToURLTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.goToURL("https://www.yahoo.com/");
		asserts.compareURL("https://www.google.com/");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "url",
			"virtual" }, description = "An integration negative test to check the goToURL method")
	public void negativeInvalidGoToURLTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.goToURL("https:///");
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "alert",
			"do" }, description = "An integration test to check the acceptAlert method")
	public void acceptAlertTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.acceptAlert();
		asserts.checkAlertNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "alert",
			"do" }, description = "An integration negative test to check the acceptAlert method")
	public void negativeAcceptAlertTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.acceptAlert();
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "alert",
			"do" }, description = "An integration test to check the acceptConfirmation method")
	public void acceptConfirmationTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.acceptConfirmation();
		asserts.checkConfirmationNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "alert",
			"do" }, description = "An integration negative test to check the acceptConfirmation method")
	public void negativeAcceptConfirmationTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.acceptConfirmation();
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "alert",
			"do" }, description = "An integration test to check the dismissConfirmation method")
	public void dismissConfirmationTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.dismissConfirmation();
		asserts.checkConfirmationNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "alert",
			"do" }, description = "An integration negative test to check the dismissConfirmation method")
	public void negativeDismissConfirmationTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.dismissConfirmation();
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "alert",
			"do" }, description = "An integration test to check the acceptPrompt method")
	public void acceptPromptTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.acceptPrompt();
		asserts.checkPromptNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "alert",
			"do" }, description = "An integration negative test to check the acceptPrompt method")
	public void negativeAcceptPromptTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.acceptPrompt();
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "alert",
			"do" }, description = "An integration test to check the dismissPrompt method")
	public void dismissPromptTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		actions.click(Locators.ID, "enable_button");
		actions.dismissPrompt();
		asserts.checkConfirmationNotPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "alert",
			"do" }, description = "An integration negative test to check the dismissPrompt method")
	public void negativeDismissPromptTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.dismissPrompt();
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "click",
			"virtual" }, description = "An integration test to check the click method")
	public void clickTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.ID, "disable_click");
		asserts.checkElementEditable(Locators.ID, "enable_button");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "click",
			"do" }, description = "An integration negative test to check the click method")
	public void clickDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "enable_button");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "click",
			"virtual" }, description = "An integration negative test to check the click method")
	public void clickNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "non-existent-element");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "click",
			"virtual" }, description = "An integration negative test to check the click method")
	public void clickHiddenTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.ID, "hidden_div");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "submit" }, description = "An integration test to check the submit method")
	public void submitTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.submit(Locators.ID, "submit_button");
		asserts.checkTextVisible("You're on the next page");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "submit",
			"do" }, description = "An integration negative test to check the submit method")
	public void submitDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.submit(Locators.ID, "enable_button");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "submit",
			"virtual" }, description = "An integration negative test to check the submit method")
	public void submitNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.submit(Locators.ID, "non-existent-element");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "submit",
			"virtual" }, description = "An integration negative test to check the submit method")
	public void submitHiddenTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.submit(Locators.ID, "hidden_div");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "hover" }, description = "An integration test to check the hover method")
	public void hoverTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.hover(Locators.ID, "hover_over_me");
		asserts.checkElementDisplayed(new Element(Locators.ID, "displayed_when_hovered"));
		// verify 2 issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "hover" }, description = "An integration test to check the hover method")
	public void hoverOffTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.hover(Locators.ID, "hover_over_me");
		actions.hover(Locators.ID, "scroll_button");
		asserts.checkElementNotDisplayed(new Element(Locators.ID, "displayed_when_hovered"));
		// verify 2 issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "hover",
			"virtual" }, description = "An integration negative test to check the hover method")
	public void hoverNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.hover(Locators.ID, "non-existent-element");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "hover",
			"virtual" }, description = "An integration negative test to check the hover method")
	public void hoverHiddenTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.hover(Locators.ID, "hidden_div");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "blur" }, description = "An integration test to check the blur method")
	public void blurTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.blur(Locators.ID, "input_box");
		asserts.checkAlertPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "blur",
			"virtual" }, description = "An integration negative test to check the blur method")
	public void blurNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.blur(Locators.ID, "non-existent-element");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "blur",
			"do" }, description = "An integration negative test to check the blur method")
	public void blurDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.blur(Locators.ID, "enable_button");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "blur",
			"virtual" }, description = "An integration negative test to check the blur method")
	public void blurNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.blur(Locators.ID, "hidden_div");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "blur",
			"virtual" }, description = "An integration negative test to check the blur method")
	public void blurNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.blur(new Element(Locators.ID, "disable_click"));
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration test to check the type method")
	public void typeTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.ID, "input_box", "This is a test");
		asserts.compareInputValue(Locators.ID, "input_box", "This is a test");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration negative test to check the type method")
	public void typeNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(Locators.ID, "non-existent-element", "This is a test");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "type",
			"do" }, description = "An integration negative test to check the type method")
	public void typeDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(Locators.ID, "enable_button", "This is a test");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration negative test to check the type method")
	public void typeNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(Locators.ID, "hidden_div", "This is a test");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration negative test to check the type method")
	public void typeNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(new Element(Locators.ID, "disable_click"), "This is a test");
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration test to check the type method")
	public void typeKeysTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.ID, "input_box", Keys.SPACE);
		asserts.compareInputValue(Locators.ID, "input_box", "");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration negative test to check the type method")
	public void typeKeysNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(Locators.ID, "non-existent-element", Keys.SPACE);
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "type",
			"do" }, description = "An integration negative test to check the type method")
	public void typeKeysDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(Locators.ID, "enable_button", Keys.SPACE);
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration negative test to check the type method")
	public void typeKeysNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(Locators.ID, "hidden_div", Keys.SPACE);
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration negative test to check the type method")
	public void typeKeysNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(new Element(Locators.ID, "disable_click"), Keys.SPACE);
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "clear", 
			"virtual" }, description = "An integration test to check the clear method")
	public void clearTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.ID, "input_box", "Text");
		asserts.compareInputValue(Locators.ID, "input_box", "Text");
		actions.clear(Locators.ID, "input_box");
		asserts.compareInputValue(Locators.ID, "input_box", "");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "clear",
			"virtual" }, description = "An integration negative test to check the clear method")
	public void clearNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.clear(Locators.ID, "non-existent-element");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "clear", 
			"do" }, description = "An integration negative test to check the clear method")
	public void clearDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.clear(Locators.ID, "enable_button");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "clear",
			"virtual" }, description = "An integration negative test to check the clear method")
	public void clearNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.clear(Locators.ID, "hidden_div");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "clear",
			"virtual" }, description = "An integration negative test to check the clear method")
	public void clearNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.clear(new Element(Locators.ID, "disable_click"));
		// verify 2 issues
		finish(1);
	}

	@Test(dataProvider = "car list items", groups = { "integration", "actions", "do", "select",
			"virtual" }, description = "An integration test using a data provider to perform searches")
	public void selectTest(String listItem) throws IOException {
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(Locators.ID, "car_list", listItem);
		asserts.compareSelectedValue(Locators.ID, "car_list", listItem);
		// close out the test
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "select",
			"virtual" }, description = "An integration negative test to check the select method")
	public void selectBadOptionTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(Locators.ID, "car_list", "option");
		// verify 2 issues
		finish(1);
	}
	
	@Test(groups = { "integration", "actions", "do", "select",
	"virtual" }, description = "An integration negative test to check the select method")
	public void selectNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(Locators.ID, "non-existent-element", "option");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "select", 
			"do" }, description = "An integration negative test to check the select method")
	public void selectDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(Locators.ID, "enable_button", "option");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "select",
			"virtual" }, description = "An integration negative test to check the select method")
	public void selectNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(Locators.ID, "hidden_div", "option");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "select",
			"virtual" }, description = "An integration negative test to check the select method")
	public void selectNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(new Element(Locators.ID, "disable_click"), "option");
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "move", 
			"virtual" }, description = "An integration test to check the move method")
	public void moveTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.move(Locators.ID, "input_box");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "move",
			"virtual" }, description = "An integration negative test to check the move method")
	public void moveNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.move(Locators.ID, "non-existent-element");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "move",
			"virtual" }, description = "An integration negative test to check the move method")
	public void moveNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.move(Locators.ID, "hidden_div");
		// verify 2 issues
		finish(1);
	}
	
	@Test(groups = { "integration", "actions", "do", "move", 
	"virtual" }, description = "An integration test to check the move method")
	public void moveAtTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.move(Locators.ID, "input_box", 10);
		// verify no issues
		finish();
	}
	
	@Test(groups = { "integration", "actions", "do", "move",
	"virtual" }, description = "An integration negative test to check the move method")
	public void moveAtNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.move(Locators.ID, "non-existent-element", 10);
		// verify 2 issues
		finish(2);
	}
	
	@Test(groups = { "integration", "actions", "do", "move",
	"virtual" }, description = "An integration negative test to check the move method")
	public void moveAtNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.move(Locators.ID, "hidden_div", 10);
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "scroll" }, description = "An integration test to check the scroll method")
	public void scrollTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.NAME, "scroll_button");
		actions.scroll(50);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "scroll",
			"do" }, description = "An integration negative test to check the scroll method")
	public void negativeScrollTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.scroll(50);
		// verify 1 issue
		finish(1);
	}
}