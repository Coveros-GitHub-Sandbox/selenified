package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.testng.annotations.Test;

public class VerifyEqualsIT extends WebBase {

    @Test(groups = {"integration", "verify", "equals"}, description = "An integration test to check the matchCount method")
    public void matchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").verifyEquals().matches(1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"}, description = "An integration test to check the matchCount method")
    public void matchesMultipleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.TAGNAME, "table").verifyEquals().matches(3);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"}, description = "An integration negative test to check the matchCount method")
    public void matchesFalseTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.TAGNAME, "table").verifyEquals().matches(1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"}, description = "An integration negativetest to check the matchCount method")
    public void matchesNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.TAGNAME, "someBadTag").verifyEquals().matches(1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareSelectValues method")
    public void compareSelectValuesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").verifyEquals().selectValues("volvo", "saab", "mercedes", "audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-element", 0).verifyEquals()
                .selectValues("volvo", "ford", "mercedes", "audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").verifyEquals().selectValues("volvo", "ford", "mercedes", "audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareSelectValues method")
    public void negativeCompareSelectValuesExtraTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").verifyEquals()
                .selectValues("volvo", "saab", "mercedes", "audi", "chevrolet");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesMissingTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").verifyEquals().selectValues("volvo", "saab", "mercedes");
        // verify no issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareSelectOptions method")
    public void compareSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").verifyEquals().selectOptions("Volvo", "Saab", "Mercedes", "Audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-element", 0).verifyEquals()
                .selectOptions("Volvo", "Ford", "Mercedes", "Audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").verifyEquals().selectOptions("Volvo", "Ford", "Mercedes", "Audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsExtraTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").verifyEquals()
                .selectOptions("Volvo", "Saab", "Mercedes", "Audi", "Chevrolet");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsMissingTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").verifyEquals().selectOptions("Volvo", "Saab", "Mercedes");
        // verify no issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareTableCellText method")
    public void compareTableCellTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").verifyEquals().text(2, 2, "Francisco Chang");
        // verify no issues
        finish();
    }


    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareTableCellText method", expectedExceptions = AssertionError.class)
    public void negativeCompareTableCellTextOutOfBoundsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table", 0).assertEquals().text(99, 99, "Bad-Value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table", 0).verifyEquals().text(1, 1, "Bad-Value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextNotPresetTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyEquals().text(0, 0, "");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the checkElementHasClass method")
    public void checkElementHasClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").verifyEquals().clazz("hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).verifyEquals().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyEquals().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassNoClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "prompt_button").verifyEquals().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").verifyEquals().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareCssValue method")
    public void compareCssValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").verifyEquals().cssValue("display", "none");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).verifyEquals().cssValue("display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssNullValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).verifyEquals().cssValue(null, "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyEquals().cssValue("display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareAttributeValue method")
    public void compareAttributeValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").verifyEquals().attribute("id", "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareAttributeValue method")
    public void negativeCompareAttributeValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").verifyEquals().attribute("id", "another_id");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareAttributeValue method")
    public void negativeCompareAttributeNoAttributeValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).verifyEquals().attribute("display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareAttributeValue method")
    public void negativeCompareAttributeValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyEquals().attribute("display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareCssValue method")
    public void compareInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").verifyEquals().value("That");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that", 0).verifyEquals().value("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareSelectedValue method")
    public void compareSelectedValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyEquals().selectedValue("volvo");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).verifyEquals().selectedValue("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "enabled_button").verifyEquals().selectedValue("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").verifyEquals().selectedValue("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareTextValue method")
    public void compareTextValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").verifyEquals()
                .text("Centro comercial Moctezuma");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareTextValue method")
    public void negativeCompareTextValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", 0).verifyEquals().text("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareTextValue method")
    public void negativeCompareTextValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyEquals().text("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareValue method")
    public void compareValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").verifyEquals().value("A Pretty Text Area");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input", 0).verifyEquals().value("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hover_over_me").verifyEquals().value("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyEquals().value("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the compareSelectedText method")
    public void compareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyEquals().selectedOption("Volvo");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).verifyEquals().selectedOption("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").verifyEquals().selectedOption("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the assert tag name method")
    public void compareTagNameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").verifyEquals().tagName("td");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "A negative integration test to check the tag name method")
    public void negativeCompareTagNameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "blur_box").verifyEquals().tagName("td");
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the tag name method")
    public void negativeCompareTagNameNonExistantTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existant-id").verifyEquals().tagName("input");
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the verify location method")
    public void compareLocationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "fixed_element").verifyEquals().location(new Point(5, 5));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "A negative integration test to check the location method")
    public void negativeCompareLocationTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "blur_box").verifyEquals().location(new Point(10, 10));
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the location method")
    public void negativeCompareLocationNonExistantTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existant-id").verifyEquals().location(new Point(5, 5));
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the verify size method")
    public void compareSizeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "fixed_element").verifyEquals().size(new Dimension(5, 5));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "A negative integration test to check the size method")
    public void negativeCompareSizeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "blur_box").verifyEquals().size(new Dimension(5, 5));
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the size method")
    public void negativeCompareSizeNonExistantTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existant-id").verifyEquals().size(new Dimension(5, 5));
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the verify tag name method")
    public void compareRectangleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "fixed_element").verifyEquals().rectangle(new Rectangle(5, 5, 5, 5));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "A negative integration test to check the rectangle method")
    public void negativeCompareRectangleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "blur_box").verifyEquals().rectangle(new Rectangle(5, 5, 5, 5));
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "equals"},
            description = "An integration test to check the rectangle method")
    public void negativeCompareRectangleNonExistantTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existant-id").verifyEquals().rectangle(new Rectangle(5, 5, 5, 5));
        // verify one issue
        finish(1);
    }
}