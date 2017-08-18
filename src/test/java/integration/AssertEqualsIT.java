package integration;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.Selenified;
import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;

public class AssertEqualsIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://172.31.2.65/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "0.0.1");
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectValues method")
    public void compareSelectValuesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals()
                .selectValues(new String[] { "volvo", "saab", "mercedes", "audi" });
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-element", 0).assertEquals()
                .selectValues(new String[] { "volvo", "ford", "mercedes", "audi" });
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals()
                .selectValues(new String[] { "volvo", "ford", "mercedes", "audi" });
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareSelectValues method")
    public void negativeCompareSelectValuesExtraTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals()
                .selectValues(new String[] { "volvo", "saab", "mercedes", "audi", "chevrolet" });
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesMissingTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals()
                .selectValues(new String[] { "volvo", "saab", "mercedes" });
        // verify no issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectOptions method")
    public void compareSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals()
                .selectOptions(new String[] { "Volvo", "Saab", "Mercedes", "Audi" });
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-element", 0).assertEquals()
                .selectOptions(new String[] { "Volvo", "Ford", "Mercedes", "Audi" });
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals()
                .selectOptions(new String[] { "Volvo", "Ford", "Mercedes", "Audi" });
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsExtraTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals()
                .selectOptions(new String[] { "Volvo", "Saab", "Mercedes", "Audi", "Chevrolet" });
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsMissingTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals()
                .selectOptions(new String[] { "Volvo", "Saab", "Mercedes" });
        // verify no issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareTableCellText method")
    public void compareTableCellTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertEquals().text(2, 2, "Francisco Chang");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table", 0).assertEquals().text(1, 1, "Bad-Value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextNotPresetTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().text(0, 0, "");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the checkElementHasClass method")
    public void checkElementHasClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertEquals().clazz("hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).assertEquals().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertEquals().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "equals" }, description = "An integration test to check the compareCssValue method")
    public void compareCssValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertEquals().cssValue("display", "none");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).assertEquals().cssValue("display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().cssValue("display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareAttributeValue method")
    public void compareAttributeValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertEquals().attribute("id", "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareAttributeValue method")
    public void negativeCompareAttributeValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertEquals().attribute("id", "another_id");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareAttributeValue method")
    public void negativeCompareAttributeNoAttributeValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).assertEquals().attribute("display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareAttributeValue method")
    public void negativeCompareAttributeValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().attribute("display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareCssValue method")
    public void compareInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").assertEquals().value("That");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that", 0).assertEquals().value("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectedValue method")
    public void compareSelectedValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertEquals().selectedValue("volvo");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).assertEquals().selectedValue("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "equals" }, description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "enabled_button").assertEquals().selectedValue("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "equals" }, description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueNotInputTest() throws Exception {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertEquals().selectedValue("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareTextValue method")
    public void compareTextValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").assertEquals()
                .text("Centro comercial Moctezuma");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareTextValue method")
    public void negativeCompareTextValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", 0).assertEquals().text("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareTextValue method")
    public void negativeCompareTextValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().text("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareValue method")
    public void compareValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").assertEquals().value("A Pretty Text Area");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input", 0).assertEquals().value("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hover_over_me").assertEquals().value("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().value("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration test to check the compareSelectedText method")
    public void compareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertEquals().selectedOption("Volvo");
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "asserts", "equals",
            "virtual" }, description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).assertEquals().selectedOption("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = { "integration", "asserts",
            "equals" }, description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").assertEquals().selectedOption("wrong value");
        // verify 1 issue
        finish(1);
    }
}