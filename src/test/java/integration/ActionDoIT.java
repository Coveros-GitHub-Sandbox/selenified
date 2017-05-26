package integration;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import selenified.exceptions.InvalidLocatorTypeException;
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

	@Test(expectedExceptions = InvalidLocatorTypeException.class, groups = { "integration", "actions", "do",
			"virtual" }, description = "An integration test to verify we can't define an element with a bad locator")
	public void badLocatorTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(Locators.NONE, "element");
		// verify no issues
		finish();
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
		actions.click(Locators.CLASSNAME, "click");
		actions.click(Locators.CSS, "input#enable_button");
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
		actions.click(Locators.CLASSNAME, "click");
		actions.click(Locators.CSS, "input#enable_button");
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
		actions.click(Locators.CLASSNAME, "click");
		actions.click(Locators.CSS, "input#enable_button");
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
		actions.click(Locators.CLASSNAME, "click");
		actions.click(Locators.CSS, "input#enable_button");
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
		actions.click(Locators.CLASSNAME, "click");
		actions.click(Locators.CSS, "input#enable_button");
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

	@Test(groups = { "integration", "actions", "do",
			"click" }, description = "An integration test to check the click method")
	public void clickTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.click(Locators.LINKTEXT, "I'M A LINK");
		asserts.checkConfirmationPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "click",
			"do" }, description = "An integration negative test to check the click method")
	public void clickDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(new Element(Locators.CSS, "input#enable_button"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "click",
			"virtual" }, description = "An integration negative test to check the click method")
	public void clickNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(new Element(Locators.ID, "non-existent-element"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "click",
			"virtual" }, description = "An integration negative test to check the click method")
	public void clickHiddenTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.click(new Element(Locators.ID, "hidden_div"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do",
			"submit" }, description = "An integration test to check the submit method")
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
		actions.submit(new Element(Locators.CSS, "input#enable_button"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "submit",
			"virtual" }, description = "An integration negative test to check the submit method")
	public void submitNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.submit(new Element(Locators.ID, "non-existent-element"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "submit",
			"virtual" }, description = "An integration negative test to check the submit method")
	public void submitHiddenTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.submit(new Element(Locators.ID, "hidden_div"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "submit",
			"virtual" }, description = "An integration negative test to check the submit method")
	public void submitNonFormTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.submit(new Element(Locators.ID, "scroll_button"));
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do",
			"hover" }, description = "An integration test to check the hover method")
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

	@Test(groups = { "integration", "actions", "do",
			"hover" }, description = "An integration test to check the hover method")
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
		actions.hover(new Element(Locators.ID, "non-existent-element"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "hover",
			"virtual" }, description = "An integration negative test to check the hover method")
	public void hoverHiddenTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.hover(new Element(Locators.ID, "hidden_div"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do",
			"blur" }, description = "An integration test to check the blur method")
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
		actions.blur(new Element(Locators.ID, "non-existent-element"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "blur",
			"do" }, description = "An integration negative test to check the blur method")
	public void blurDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.blur(new Element(Locators.CSS, "input#enable_button"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "blur",
			"virtual" }, description = "An integration negative test to check the blur method")
	public void blurNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.blur(new Element(Locators.ID, "hidden_div"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "blur",
			"virtual" }, description = "An integration negative test to check the blur method")
	public void blurNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.blur(new Element(Locators.CLASSNAME, "click"));
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration test to check the type method")
	public void typeInputTest() throws IOException {
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
			"virtual" }, description = "An integration test to check the type method")
	public void typeTextAreaTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.ID, "textarea_input", " With More Text");
		asserts.compareInputValue(Locators.ID, "textarea_input", "A Pretty Text Area With More Text");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do",
			"type" }, description = "An integration test to check the type method")
	public void typeCheckboxTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.ID, "this", " ");
		asserts.checkElementChecked(Locators.ID, "this");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do",
			"type" }, description = "An integration test to check the type method")
	public void typeSelectTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.ID, "car_list", "A");
		asserts.compareSelectedValue(Locators.ID, "car_list", "audi");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration negative test to check the type method")
	public void typeNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(new Element(Locators.TAGNAME, "non-existent-element"), "This is a test");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "type",
			"do" }, description = "An integration negative test to check the type method")
	public void typeDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(new Element(Locators.CSS, "input#enable_button"), "This is a test");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration negative test to check the type method")
	public void typeNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(new Element(Locators.ID, "hidden_div"), "This is a test");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration negative test to check the type method")
	public void typeNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(new Element(Locators.CLASSNAME, "click"), "This is a test");
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do",
			"type" }, description = "An integration test to check the type method")
	public void typeKeysInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.ID, "input_box", Keys.TAB);
		asserts.checkAlertPresent();
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do",
			"type" }, description = "An integration test to check the type method")
	public void typeKeysTextAreaTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.ID, "textarea_input", Keys.BACK_SPACE);
		asserts.compareInputValue(Locators.ID, "textarea_input", "A Pretty Text Are");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do",
			"type" }, description = "An integration test to check the type method")
	public void typeKeysCheckboxTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.ID, "this", Keys.SPACE);
		asserts.checkElementChecked(Locators.ID, "this");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do",
			"type" }, description = "An integration test to check the type method")
	public void typeKeysSelectTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		actions.type(Locators.ID, "car_list", Keys.DOWN);
		asserts.compareSelectedValue(Locators.ID, "car_list", "saab");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration negative test to check the type method")
	public void typeKeysNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(new Element(Locators.ID, "non-existent-element"), Keys.SPACE);
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "type",
			"do" }, description = "An integration negative test to check the type method")
	public void typeKeysDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(new Element(Locators.CSS, "input#enable_button"), Keys.SPACE);
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration negative test to check the type method")
	public void typeKeysNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(new Element(Locators.ID, "hidden_div"), Keys.SPACE);
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "type",
			"virtual" }, description = "An integration negative test to check the type method")
	public void typeKeysNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.type(new Element(Locators.CLASSNAME, "click"), Keys.SPACE);
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "clear",
			"virtual" }, description = "An integration test to check the clear method")
	public void clearInputTest() throws IOException {
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
			"virtual" }, description = "An integration test to check the clear method")
	public void clearTextAreaTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.compareInputValue(Locators.ID, "textarea_input", "A Pretty Text Area");
		actions.clear(Locators.ID, "textarea_input");
		asserts.compareInputValue(Locators.ID, "textarea_input", "");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do",
			"clear" }, description = "An integration test to check the clear method")
	public void clearCheckboxTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.clear(new Element(Locators.ID, "this"));
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do",
			"clear" }, description = "An integration test to check the clear method")
	public void clearSelectTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.clear(new Element(Locators.ID, "car_list"));
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "clear",
			"virtual" }, description = "An integration negative test to check the clear method")
	public void clearNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.clear(new Element(Locators.ID, "non-existent-element"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "clear",
			"do" }, description = "An integration negative test to check the clear method")
	public void clearDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.clear(new Element(Locators.CSS, "input#enable_button"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "clear",
			"virtual" }, description = "An integration negative test to check the clear method")
	public void clearNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.clear(new Element(Locators.ID, "hidden_div"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "clear",
			"virtual" }, description = "An integration negative test to check the clear method")
	public void clearNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.clear(new Element(Locators.CLASSNAME, "click"));
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
		actions.select(new Element(Locators.ID, "car_list"), "option");
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "select",
			"virtual" }, description = "An integration negative test to check the select method")
	public void selectNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(new Element(Locators.PARTIALLINKTEXT, "non-existent-element"), "option");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "select",
			"do" }, description = "An integration negative test to check the select method")
	public void selectDisabledTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(new Element(Locators.CSS, "input#enable_button"), "option");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "select",
			"virtual" }, description = "An integration negative test to check the select method")
	public void selectNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(new Element(Locators.ID, "hidden_div"), "option");
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "select",
			"virtual" }, description = "An integration negative test to check the select method")
	public void selectNotInputTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(new Element(Locators.CLASSNAME, "click"), "option");
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "select",
			"virtual" }, description = "An integration negative test to check the select method")
	public void selectNotSelectTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.select(new Element(Locators.ID, "scroll_button"), "option");
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "move",
			"virtual" }, description = "An integration test to check the move method")
	public void moveTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.move(Locators.ID, "table_no_header");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "move",
			"virtual" }, description = "An integration negative test to check the move method")
	public void moveNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.move(new Element(Locators.ID, "non-existent-element"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "move",
			"virtual" }, description = "An integration negative test to check the move method")
	public void moveNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.move(new Element(Locators.ID, "hidden_div"));
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do",
			"move" }, description = "An integration test to check the move method")
	public void moveAtTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.move(Locators.ID, "table_no_header", (long) 10);
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do",
			"move" }, description = "An integration negative test to check the move method")
	public void moveAtNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.move(new Element(Locators.ID, "non-existent-element"), (long) 10);
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do",
			"move" }, description = "An integration negative test to check the move method")
	public void moveAtNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.move(new Element(Locators.ID, "hidden_div"), (long) 10);
		// verify 2 issues
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do",
			"scroll" }, description = "An integration test to check the scroll method")
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
		actions.scroll(500);
		// verify 1 issue
		finish(1);
	}

	@Test(groups = { "integration", "actions", "do", "frame",
			"virtual" }, description = "An integration test to check the frame method")
	public void selectFrameTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// use this object to verify our page looks as expected
		Assert asserts = this.asserts.get();
		// perform some actions
		asserts.checkElementNotDisplayed(Locators.ID, "message");
		actions.selectFrame(Locators.ID, "some_frame");
		asserts.checkElementDisplayed(Locators.ID, "message");
		// verify no issues
		finish();
	}

	@Test(groups = { "integration", "actions", "do", "frame",
			"virtual" }, description = "An integration negative test to check the frame method")
	public void selectFrameNotExistTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.selectFrame(new Element(Locators.ID, "non-existent-element"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "frame",
			"virtual" }, description = "An integration negative test to check the frame method")
	public void selectFrameNotVisibleTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.selectFrame(new Element(Locators.ID, "some_other_frame"));
		// verify 2 issues
		finish(2);
	}

	@Test(groups = { "integration", "actions", "do", "frame",
			"virtual" }, description = "An integration negative test to check the frame method")
	public void selectFrameNotFrameTest() throws IOException {
		// use this object to manipulate our page
		Action actions = this.actions.get();
		// perform some actions
		actions.selectFrame(new Element(Locators.ID, "scroll_button"));
		// verify no issues
		finish(1);
	}
}