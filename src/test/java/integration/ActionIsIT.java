package integration;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Page;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.tools.Selenified;

public class ActionIsIT extends Selenified {

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

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is present")
    public void isElementPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "submit_button").is().present());
        // verify no issues
        finish();
    }
    
    @Test(groups = { "integration", "actions", "is", "virtual" },
            description = "An integration test to check if an element is present")
    public void isElementPresentPrintTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "submit_button").is().present(true));
        // verify no issues
        finish();
    }
    
    @Test(groups = { "integration", "actions", "is", "virtual" },
            description = "An integration test to check if an element is present")
    public void isElementPresentMatchTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "submit_button", 0).is().present());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is present")
    public void isElementPresentNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "non-existent-name", 0).is().present(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementInputTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "input_box").is().input(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementSelectTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "car_list", 0).is().input());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementTextAreaTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "textarea_input").is().input());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementNotInputTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "disable_click", 0).is().input(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementInputNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "non-existent-name").is().input());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementSelectInputTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "input_box").is().select());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementSelectSelectTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "car_list", 0).is().select());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementSelectTextAreaTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "textarea_input").is().select(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementSelectNotSelectTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "disable_click", 0).is().select(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is input")
    public void isElementSelectNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "non-existent-name").is().select());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is a table")
    public void isElementTableTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "that").click();
        Assert.assertTrue(page.newElement(Locator.ID, "table").is().table());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is a table")
    public void isElementTableNotTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "that", 0).is().table());
        // verify no issues
        finish();
    }
    
    @Test(groups = { "integration", "actions", "is", "virtual" },
            description = "An integration test to check if an element is a table")
    public void isElementTableNotPrintMatchTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "that", 0).is().table(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is a table")
    public void isElementTableNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "non-existent-name").is().table(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is enabled")
    public void isElementEnabledTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "scroll_button").is().enabled());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is", "virtual" },
            description = "An integration test to check if an element is enabled")
    public void isElementEnabledPrintTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "scroll_button").is().enabled(true));
        // verify no issues
        finish();
    }
    
    @Test(groups = { "integration", "actions", "is", "virtual" },
            description = "An integration test to check if an element is enabled")
    public void isElementEnabledMatchTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "scroll_button", 0).is().enabled());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is enabled")
    public void isElementEnabledNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "non-existent-name", 0).is().enabled(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration test to check if an element is checked")
    public void isElementCheckedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "that").click();
        Assert.assertTrue(page.newElement(Locator.ID, "that").is().checked());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration test to check if an element is checked")
    public void isElementCheckedNotTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "that", 0).is().checked());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is checked")
    public void isElementCheckedNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "non-existent-name").is().checked(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is", "virtual" },
            description = "An integration test to check if an element is checked")
    public void isElementCheckedNotExistMatchPrintTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "non-existent-name", 0).is().checked(true));
        // verify no issues
        finish();
    }
    
    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is displayed")
    public void isElementDisplayedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "that").is().displayed());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is", "virtual" },
            description = "An integration test to check if an element is displayed")
    public void isElementDisplayedPrintTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "that").is().displayed(true));
        // verify no issues
        finish();
    }
    
    @Test(groups = { "integration", "actions", "is", "virtual" },
            description = "An integration test to check if an element is displayed")
    public void isElementDisplayedMatchTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "that", 0).is().displayed());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if an element is displayed")
    public void isElementDisplayedNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "non-existent-name", 0).is().displayed(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from a dropdown")
    public void isSomethingSelectedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertTrue(page.newElement(Locator.ID, "car_list").is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration test to check if something is selected from a dropdown")
    public void isSomethingSelectedMultipleTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "car_list_multiple").select(1);
        Assert.assertTrue(page.newElement(Locator.ID, "car_list_multiple", 0).is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from a dropdown")
    public void isSomethingNotSelectedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "car_list_multiple").is().somethingSelected(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from a checkbox")
    public void isSomethingCheckedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "that").click();
        Assert.assertTrue(page.newElement(Locator.ID, "that", 0).is().somethingSelected(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from a checkbox")
    public void isSomethingNotCheckedTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "that").is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from a non-existant element")
    public void isSomethingSelectedNotExistTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "non-existent-name").is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from an div")
    public void isSomethingSelectedTextAreaTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "textarea_input").is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check if something is selected from an div")
    public void isSomethingSelectedNotSelectOrInputTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.newElement(Locator.ID, "disable_click").is().somethingSelected());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration test to check the isAlertPresent method")
    public void isAlertPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "disable_click").click();
        page.newElement(Locator.ID, "alert_button").click();
        Assert.assertTrue(page.is().alertPresent());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration negative test to check the isAlertPresent method")
    public void negativeIsAlertPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.is().alertPresent(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration test to check the isConfirmationPresent method")
    public void isConfirmationPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "confirm_button").click();
        Assert.assertTrue(page.is().confirmationPresent());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration negative test to check the isConfirmationPresent method")
    public void negativeIsConfirmationPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.is().confirmationPresent(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "An integration test to check the isPromptPresent method")
    public void isPromptPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "prompt_button").click();
        Assert.assertTrue(page.is().promptPresent());
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions",
            "is" }, description = "A integration negative test to check the isPromptPresent method")
    public void negativeIsPromptPresentTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        Assert.assertFalse(page.is().promptPresent(true));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration test to check the isTextPresentInSource method")
    public void isTextPresentInSourceTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "submit_button").click();
        Assert.assertTrue(page.is().textPresentInSource("You're on the next page"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration negative test to check the isTextPresentInSource method")
    public void negativeIsTextPresentInSourceTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.newElement(Locator.ID, "submit_button").click();
        Assert.assertFalse(page.is().textPresentInSource("Hello World"));
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "actions", "is",
            "virtual" }, description = "An integration negative test to check the isTextPresentInSource method")
    public void negativeIsTextPresentInSourceErrorTest() {
        // use this object to manipulate the page
        Page page = this.pages.get();
        // perform some actions
        page.killDriver();
        page.is().textPresentInSource("Hello World");
        // verify no issues
        finish();
    }
}