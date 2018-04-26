package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.application.App;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AssertExcludesIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "file:" + System.getProperty("user.dir") + "/public/index.html");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "0.0.1");
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration test to check the checkSelectValueNotPresent method")
    public void checkSelectValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertExcludes().selectValue("ford");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the checkSelectValueNotPresent method")
    public void negativeCheckSelectValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).assertExcludes().selectValue("volvo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the checkSelectValueNotPresent method")
    public void negativeCheckSelectValueNotPresentNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").assertExcludes().selectValue("volvo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration test to check the checkIfOptionNotInSelect method")
    public void checkIfOptionNotInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertExcludes().selectOption("ford");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the checkIfOptionNotInSelect method")
    public void negativeCheckIfOptionNotInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).assertExcludes().selectOption("Audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the checkIfOptionNotInSelect method")
    public void negativeCheckIfOptionNotInSelectNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").assertExcludes().selectOption("audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration test to check the checkElementDoesntContainClass method")
    public void checkElementDoesntContainClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertExcludes().clazz("wrong_class");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the checkElementDoesntContainClass method")
    public void negativeCheckElementDoesntContainClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).assertExcludes().clazz("hidden_div");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the checkElementDoesntContainClass method")
    public void negativeCheckElementDoesntContainClassNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertExcludes().clazz("hidden_div");
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the checkElementDoesntContainClass method")
    public void checkElementDoesntContainClassDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertExcludes().clazz("hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration test to check the checkElementDoesntHaveAttribute method")
    public void checkElementDoesntHaveAttributeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertExcludes().attribute("class");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
    public void negativeCheckElementDoesntHaveAttributeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).assertExcludes().attribute("name");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
    public void negativeCheckElementDoesntHaveAttributeNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertExcludes().attribute("name");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
    public void checkElementDoesntHaveAttributeDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertExcludes().attribute("name");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration test to check the compareTextValueExcludes method")
    public void compareTextValueExcludesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").assertExcludes().text("Anders");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration test to check the compareTextValueExcludes method")
    public void compareTextValueExcludesDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertExcludes().text("Anders");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareTextValueExcludesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", 0).assertExcludes().text("Centro comer");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareTextValueExcludesNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertExcludes().text("Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration test to check the compareTextValueExcludes method")
    public void compareValueExcludesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").assertExcludes().value("Anders");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration test to check the compareTextValueExcludes method")
    public void compareValueExcludesDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertExcludes().value("Anders");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareValueExcludesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input", 0).assertExcludes().value("Pretty");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareValueNotInputExcludesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "overlay_span").assertExcludes().value("Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "asserts", "excludes"},
            description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareValueExcludesNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertExcludes().value("Anders");
        // verify 1 issue
        finish(1);
    }
}