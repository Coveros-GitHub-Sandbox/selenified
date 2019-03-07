package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.annotations.Test;

public class VerifyContainsIT extends WebBase {

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration test to check the compareNumOfSelectOptions method")
    public void compareNumOfSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").verifyContains().selectOptions(4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the compareNumOfSelectOptions method")
    public void negativeCompareNumOfSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list", 0).verifyContains().selectOptions(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the compareNumOfSelectOptions method")
    public void negativeCompareNumOfSelectOptionsNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "alert_button").verifyContains().selectOptions(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration test to check the compareNumOfTableRows method")
    public void compareNumOfTableRowsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").verifyContains().rows(7);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the compareNumOfTableRows method")
    public void negativeCompareNumOfTableRowsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table", 0).verifyContains().rows(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the compareNumOfTableRows method")
    public void negativeCompareNumOfTableRowsNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyContains().rows(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration test to check the compareNumOfTableColumns method")
    public void compareNumOfTableColumnsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").verifyContains().columns(4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the compareNumOfTableColumns method")
    public void negativeCompareNumOfTableColumnsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table", 0).verifyContains().columns(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the compareNumOfTableColumns method")
    public void negativeCompareNumOfTableColumnsNotTableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").verifyContains().columns(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the compareNumOfTableColumns method")
    public void negativeCompareNumOfTableColumnsNotPresetTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyContains().columns(0);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration test to check the checkIfOptionInSelect method")
    public void checkIfOptionInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyContains().selectOption("Audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfOptionInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).verifyContains().selectOption("Ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfOptionInSelectNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").verifyContains().selectOption("Ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfOptionInSelectDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_input").verifyContains().selectOption("Ford");
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method")
    public void checkIfValueInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyContains().selectValue("audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfValueInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyContains().selectValue("ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfValueInSelectNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button", 0).verifyContains().selectValue("ford");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfValueInSelectDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_enable_button").click();
        app.newElement(Locator.ID, "delayed_input").verifyContains().selectValue("ford");
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method")
    public void negativeCheckIfValueInSelectCheckDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "delayed_enable_button").click();
        app.newElement(Locator.ID, "check").verifyContains().selectValue("ford");
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration test to check the checkElementContainsClass method")
    public void checkElementContainsClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").verifyContains().clazz("hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkElementContainsClass method")
    public void negativeCheckElementContainsClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).verifyContains().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkElementContainsClass method")
    public void negativeCheckElementContainsClassNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyContains().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkElementContainsClass method")
    public void negativeCheckElementContainsClassDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").verifyContains().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration test to check the checkElementHasAttribute method")
    public void checkElementHasAttributeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyContains().attribute("name");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkElementHasAttribute method")
    public void negativeCheckElementHasAttributeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).verifyContains().attribute("class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkElementHasAttribute method")
    public void negativeCheckElementHasAttributeNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyContains().attribute("class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the checkElementHasAttribute method")
    public void negativeCheckElementHasAttributeDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").verifyContains().attribute("class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration test to check the compareTextValueContains method")
    public void compareTextValueContainsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").verifyContains().text("Centro comer");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration test to check the compareTextValueContains method")
    public void compareTextValueContainsDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").verifyContains().text("");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareTextValueContainsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", 0).verifyContains().text("Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareTextValueContainsNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyContains().text("Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration test to check the compareTextValueContains method")
    public void compareValueContainsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").verifyContains().value("Pretty");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration test to check the compareTextValueContains method")
    public void compareValueContainsDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").verifyContains().value("");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareValueContainsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input", 0).verifyContains().value("Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareValueNotInputContainsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "overlay_span").verifyContains().value("Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "contains"},
            description = "An integration negative test to check the compareTextValueContains method")
    public void negativeCompareValueContainsNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyContains().value("Anders");
        // verify 1 issue
        finish(1);
    }
}