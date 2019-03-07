package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.annotations.Test;

public class AssertContainsIT extends WebBase {

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the compareNumOfSelectOptions method")
    public void compareNumOfSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertContains().selectOptions(4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the compareNumOfSelectOptions method", expectedExceptions = AssertionError.class)
    public void negativeCompareNumOfSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list", 0).assertContains().selectOptions(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the compareNumOfSelectOptions method", expectedExceptions = AssertionError.class)
    public void negativeCompareNumOfSelectOptionsNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button").assertContains().selectOptions(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the compareNumOfTableRows method")
    public void compareNumOfTableRowsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertContains().rows(7);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the compareNumOfTableRows method", expectedExceptions = AssertionError.class)
    public void negativeCompareNumOfTableRowsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table", 0).assertContains().rows(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the compareNumOfTableRows method", expectedExceptions = AssertionError.class)
    public void negativeCompareNumOfTableRowsNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertContains().rows(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the compareNumOfTableColumns method")
    public void compareNumOfTableColumnsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertContains().columns(4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the compareNumOfTableColumns method", expectedExceptions = AssertionError.class)
    public void negativeCompareNumOfTableColumnsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table", 0).assertContains().columns(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the compareNumOfTableColumns method", expectedExceptions = AssertionError.class)
    public void negativeCompareNumOfTableColumnsNotTableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertContains().columns(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the compareNumOfTableColumns method", expectedExceptions = AssertionError.class)
    public void negativeCompareNumOfTableColumnsNotPresetTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertContains().columns(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the checkIfOptionInSelect method")
    public void checkIfOptionInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertContains().selectOption("Audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method", expectedExceptions = AssertionError.class)
    public void negativeCheckIfOptionInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).assertContains().selectOption("Ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method", expectedExceptions = AssertionError.class)
    public void negativeCheckIfOptionInSelectNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").assertContains().selectOption("Ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method", expectedExceptions = AssertionError.class)
    public void negativeCheckIfOptionInSelectDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_input").assertContains().selectOption("Ford");
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method")
    public void checkIfValueInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertContains().selectValue("audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method", expectedExceptions = AssertionError.class)
    public void negativeCheckIfValueInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertContains().selectValue("ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method", expectedExceptions = AssertionError.class)
    public void negativeCheckIfValueInSelectNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button", 0).assertContains().selectValue("ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method", expectedExceptions = AssertionError.class)
    public void negativeCheckIfValueInSelectDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_enable_button").click();
        app.newElement(Locator.ID, "delayed_input").assertContains().selectValue("ford");
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method", expectedExceptions = AssertionError.class)
    public void negativeCheckIfValueInSelectCheckDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_enable_button").click();
        app.newElement(Locator.ID, "check").assertContains().selectValue("ford");
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the checkElementContainsClass method")
    public void checkElementContainsClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertContains().clazz("hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkElementContainsClass method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementContainsClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).assertContains().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkElementContainsClass method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementContainsClassNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertContains().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkElementContainsClass method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementContainsClassDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertContains().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the checkElementHasAttribute method")
    public void checkElementHasAttributeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertContains().attribute("name");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkElementHasAttribute method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementHasAttributeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).assertContains().attribute("class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkElementHasAttribute method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementHasAttributeNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertContains().attribute("class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkElementHasAttribute method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementHasAttributeDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertContains().attribute("class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the compareTextValueContains method")
    public void compareTextValueContainsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").assertContains().text("Centro comer");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the compareTextValueContains method", expectedExceptions = AssertionError.class)
    public void compareTextValueContainsDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertContains().text("");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the compareTextValueContains method", expectedExceptions = AssertionError.class)
    public void negativeCompareTextValueContainsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", 0).assertContains().text("Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the compareTextValueContains method", expectedExceptions = AssertionError.class)
    public void negativeCompareTextValueContainsNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertContains().text("Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the compareTextValueContains method")
    public void compareValueContainsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").assertContains().value("Pretty");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the compareTextValueContains method", expectedExceptions = AssertionError.class)
    public void compareValueContainsDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertContains().value("");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the compareTextValueContains method", expectedExceptions = AssertionError.class)
    public void negativeCompareValueContainsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input", 0).assertContains().value("Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the compareTextValueContains method", expectedExceptions = AssertionError.class)
    public void negativeCompareValueNotInputContainsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "overlay_span").assertContains().value("Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the compareTextValueContains method", expectedExceptions = AssertionError.class)
    public void negativeCompareValueContainsNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertContains().value("Anders");
        // verify 1 issue
        finish(1);
    }
}