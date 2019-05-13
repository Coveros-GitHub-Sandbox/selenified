package integration;

import com.coveros.selenified.Browser.BrowserName;
import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;
import org.testng.annotations.Test;

public class WaitForEqualsIT extends WebBase {

    @Test(groups = {"integration", "equals", "wait"},
            description = "An integration test to check changing the default wait method")
    public void setDefaultWaitElementTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element element = app.newElement(Locator.ID, "changing_text");
        element.waitForEquals().changeDefaultWait(0.5);
        element.waitForEquals().text("Some Changing Text");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "equals", "wait"},
            description = "An integration test to check changing the default wait method")
    public void setDefaultWait2ElementTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element element = app.newElement(Locator.ID, "changing_text");
        element.waitForEquals().text("Some Changing Text", 0.5);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check changing the default poll method")
    public void setDefaultPollElementTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element element = app.newElement(Locator.ID, "changing_text");
        element.waitForEquals().changeDefaultPoll(5);
        element.waitForEquals().text("Some Changing Text");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "state", "wait"},
            description = "An integration test to check changing the default poll method")
    public void setDefaultPollNegativeElementTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element element = app.newElement(Locator.ID, "changing_text");
        element.waitForEquals().changeDefaultPoll(10000);
        element.waitForEquals().text("Some Changing Text");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"}, description = "An integration test to check the matchCount method")
    public void matchesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForEquals().matches(1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"}, description = "An integration test to check the matchCount method")
    public void matchesTestNone() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForEquals().matches(0, 2);
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"}, description = "An integration test to check the matchCount method")
    public void matchesMultipleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.TAGNAME, "table").waitForEquals().matches(3);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"}, description = "An integration negative test to check the matchCount method")
    public void matchesFalseTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.TAGNAME, "table").waitForEquals().matches(1, 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"}, description = "An integration negativetest to check the matchCount method")
    public void matchesNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.TAGNAME, "someBadTag").waitForEquals().matches(1, 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectValues method")
    public void compareSelectValuesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForEquals().selectValues("volvo", "saab", "mercedes", "audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-element", 0).waitForEquals()
                .selectValues(new String[]{"volvo", "ford", "mercedes", "audi"}, 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForEquals().selectValues(new String[]{"volvo", "ford", "mercedes", "audi"}, 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareSelectValues method")
    public void negativeCompareSelectValuesExtraTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForEquals()
                .selectValues(new String[]{"volvo", "saab", "mercedes", "audi", "chevrolet"}, 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesMissingTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForEquals().selectValues(new String[]{"volvo", "saab", "mercedes"}, 1);
        // verify no issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesNoElementTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existing-element").waitForEquals().selectValues(new String[]{"volvo", "saab", "mercedes"}, 1);
        // verify no issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectValues method")
    public void negativeCompareSelectValuesNotSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "blur_box").waitForEquals().selectValues(new String[]{"volvo", "saab", "mercedes"}, 1);
        // verify no issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectOptions method")
    public void compareSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForEquals().selectOptions("Volvo", "Saab", "Mercedes", "Audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existent-element", 0).waitForEquals()
                .selectOptions(new String[]{"Volvo", "Ford", "Mercedes", "Audi"}, 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForEquals().selectOptions(new String[]{"Volvo", "Ford", "Mercedes", "Audi"}, 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsExtraTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForEquals()
                .selectOptions(new String[]{"Volvo", "Saab", "Mercedes", "Audi", "Chevrolet"}, 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsMissingTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").waitForEquals().selectOptions(new String[]{"Volvo", "Saab", "Mercedes"}, 1);
        // verify no issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsNoElementTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "non-existant-element").waitForEquals().selectOptions(new String[]{"Volvo", "Saab", "Mercedes"}, 1);
        // verify no issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectOptions method")
    public void negativeCompareSelectOptionsNotSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "blur_box").waitForEquals().selectOptions(new String[]{"Volvo", "Saab", "Mercedes"}, 1);
        // verify no issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareTableCellText method")
    public void compareTableCellTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").waitForEquals().text(2, 2, "Francisco Chang");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table", 0).waitForEquals().text(1, 1, "Bad-Value", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextNotPresetTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").waitForEquals().text(0, 0, "", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextNotTableTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").waitForEquals().text(2, 1, "", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the checkElementHasClass method")
    public void checkElementHasClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").waitForEquals().clazz("hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).waitForEquals().clazz("wrong_class", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassNullTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).waitForEquals().clazz(null, 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").waitForEquals().clazz("wrong_class", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassNoClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "prompt_button").waitForEquals().clazz("wrong_class", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassNoClassNullTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        if (app.getBrowser().getName() == BrowserName.HTMLUNIT) {
            app.newElement(Locator.ID, "prompt_button").waitForEquals().clazz(null);
        } else {
            app.newElement(Locator.ID, "prompt_button").waitForEquals().clazz("");
        }
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassDelayedTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").waitForEquals().clazz("wrong_class", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals", "browser"},
            description = "An integration negative test to check the checkElementHasClass method")
    public void negativeCheckElementHasClassDelayedNullTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "check").waitForEquals().clazz(null);
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareCssValue method")
    public void compareCssValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").waitForEquals().cssValue("display", "none");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).waitForEquals().cssValue("display", "inline", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssNullValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).waitForEquals().cssValue(null, "inline", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareCssValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").waitForEquals().cssValue("display", "inline", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareAttributeValue method")
    public void compareAttributeValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").waitForEquals().attribute("id", "hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareAttributeValue method")
    public void negativeCompareAttributeValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").waitForEquals().attribute("id", "another_id", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareAttributeValue method")
    public void negativeCompareAttributeNoAttributeValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).waitForEquals().attribute("display", "inline", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareAttributeValue method")
    public void negativeCompareAttributeValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").waitForEquals().attribute("display", "inline", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareCssValue method")
    public void compareInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").waitForEquals().value("That");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that", 0).waitForEquals().value("wrong value", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectedValue method")
    public void compareSelectedValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").waitForEquals().selectedValue("volvo");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).waitForEquals().selectedValue("wrong value", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "enabled_button").waitForEquals().selectedValue("wrong value", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueNotInputTest() throws Exception {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").waitForEquals().selectedValue("wrong value", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareTextValue method")
    public void compareTextValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").waitForEquals()
                .text("Centro comercial Moctezuma");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareTextValue method")
    public void negativeCompareTextValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", 0).waitForEquals().text("Maria Anders", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareTextValue method")
    public void negativeCompareTextValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").waitForEquals().text("Maria Anders", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareValue method")
    public void compareValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").waitForEquals().value("A Pretty Text Area");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input", 0).waitForEquals().value("Maria Anders", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hover_over_me").waitForEquals().value("Maria Anders", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").waitForEquals().value("Maria Anders", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration test to check the compareSelectedText method")
    public void compareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").waitForEquals().selectedOption("Volvo");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).waitForEquals().selectedOption("wrong value", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").waitForEquals().selectedOption("wrong value", 1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "wait", "equals"},
            description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "no_such_element").waitForEquals().selectedOption("wrong value", 1);
        // verify 1 issue
        finish(1);
    }
}