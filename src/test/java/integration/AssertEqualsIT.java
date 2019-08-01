package integration;

import com.coveros.selenified.Browser.BrowserName;
import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.annotations.Test;

public class AssertEqualsIT extends WebBase {

    @Test(groups = {"integration", "assert", "equals"}, description = "An integration test to check the matchCount method")
    public void matchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals().matches(1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"}, description = "An integration test to check the matchCount method")
    public void matchesMultipleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.TAGNAME, "table").assertEquals().matches(3);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"}, description = "An integration negative test to check the matchCount method", expectedExceptions = AssertionError.class)
    public void matchesFalseTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.TAGNAME, "table").assertEquals().matches(1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"}, description = "An integration negativetest to check the matchCount method", expectedExceptions = AssertionError.class)
    public void matchesNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.TAGNAME, "someBadTag").assertEquals().matches(1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareSelectValues method")
    public void compareSelectValuesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals().selectValues("volvo", "saab", "mercedes", "audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareSelectValues method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectValuesNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-element", 0).assertEquals()
                .selectValues("volvo", "ford", "mercedes", "audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareSelectValues method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectValuesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals().selectValues("volvo", "ford", "mercedes", "audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareSelectValues method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectValuesExtraTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals()
                .selectValues("volvo", "saab", "mercedes", "audi", "chevrolet");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareSelectValues method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectValuesMissingTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals().selectValues("volvo", "saab", "mercedes");
        // verify no issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareSelectValues method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectValuesNotSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "blur_box").assertEquals().selectValues("volvo", "saab", "mercedes");
        // verify no issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareSelectOptions method")
    public void compareSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals().selectOptions("Volvo", "Saab", "Mercedes", "Audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareSelectOptions method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectOptionsNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-element", 0).assertEquals()
                .selectOptions("Volvo", "Ford", "Mercedes", "Audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareSelectOptions method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals().selectOptions("Volvo", "Ford", "Mercedes", "Audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareSelectOptions method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectOptionsExtraTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals()
                .selectOptions("Volvo", "Saab", "Mercedes", "Audi", "Chevrolet");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareSelectOptions method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectOptionsMissingTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertEquals().selectOptions("Volvo", "Saab", "Mercedes");
        // verify no issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareSelectOptions method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectOptionsNotSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "blur_box").assertEquals().selectOptions("Volvo", "Saab", "Mercedes");
        // verify no issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareTableCellText method")
    public void compareTableCellTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertEquals().text(2, 2, "Francisco Chang");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareTableCellText method", expectedExceptions = AssertionError.class)
    public void negativeCompareTableCellTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table", 0).assertEquals().text(1, 1, "Bad-Value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareTableCellText method", expectedExceptions = AssertionError.class)
    public void negativeCompareTableCellNotTableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "blur_box", 0).assertEquals().text(1, 1, "Bad-Value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareTableCellText method", expectedExceptions = AssertionError.class)
    public void negativeCompareTableCellTextNotPresetTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().text(0, 0, "");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the checkElementHasClass method")
    public void checkElementHasClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertEquals().clazz("hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the checkElementHasClass method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementHasClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).assertEquals().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the checkElementHasClass method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementHasClassNullTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).assertEquals().clazz(null);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the checkElementHasClass method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementHasClassNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the checkElementHasClass method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementHasClassNoClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "prompt_button").assertEquals().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassNoClassNullTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        if (app.getBrowser().getName() == BrowserName.HTMLUNIT) {
            app.newElement(Locator.ID, "prompt_button").assertEquals().clazz(null);
        } else {
            app.newElement(Locator.ID, "prompt_button").assertEquals().clazz("");
        }
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the checkElementHasClass method", expectedExceptions = AssertionError.class)
    public void negativeCheckElementHasClassDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertEquals().clazz("wrong_class");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals", "browser", "local"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassDelayedNullTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").assertEquals().clazz(null);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareCssValue method")
    public void compareCssValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertEquals().cssValue("display", "none");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareCssValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareCssValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).assertEquals().cssValue("display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareCssValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareCssNullValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).assertEquals().cssValue(null, "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareCssValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareCssValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().cssValue("display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssValueNotPresentNullTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().cssValue("display", null);
        // verify one issue
        finish(1);
        //TODO unsure what to do about this one
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareAttributeValue method")
    public void compareAttributeValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertEquals().attribute("id", "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareAttributeValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareAttributeValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertEquals().attribute("id", "another_id");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareAttributeValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareAttributeNoAttributeValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).assertEquals().attribute("display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareAttributeValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareAttributeValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().attribute("display", "inline");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareCssValue method")
    public void compareInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").assertEquals().value("That");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareCssValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that", 0).assertEquals().value("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareSelectedValue method")
    public void compareSelectedValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertEquals().selectedValue("volvo");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareSelectedValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectedValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).assertEquals().selectedValue("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareSelectedValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectedValueNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "enabled_button").assertEquals().selectedValue("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareSelectedValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectedValueNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertEquals().selectedValue("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the assert tag name method")
    public void compareTagNameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").assertEquals().tagName("td");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "A negative integration test to check the tag name method", expectedExceptions = AssertionError.class)
    public void negativeCompareTagNameTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "blur_box").assertEquals().tagName("td");
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the tag name method", expectedExceptions = AssertionError.class)
    public void negativeCompareTagNameNonExistantTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existant-id").assertEquals().tagName("input");
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareTextValue method")
    public void compareTextValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").assertEquals()
                .text("Centro comercial Moctezuma");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareTextValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareTextValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", 0).assertEquals().text("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareTextValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareTextValueNotTableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "blur_box").assertEquals().text("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareTextValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareTextValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().text("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareValue method")
    public void compareValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").assertEquals().value("A Pretty Text Area");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input", 0).assertEquals().value("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareValueNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hover_over_me").assertEquals().value("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertEquals().value("Maria Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration test to check the compareSelectedText method")
    public void compareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertEquals().selectedOption("Volvo");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareSelectedText method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).assertEquals().selectedOption("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareSelectedText method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectedTextNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").assertEquals().selectedOption("wrong value");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "equals"},
            description = "An integration negative test to check the compareSelectedText method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectedTextNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existant-element").assertEquals().selectedOption("wrong value");
        // verify 1 issue
        finish(1);
    }
}