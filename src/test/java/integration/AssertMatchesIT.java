package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.annotations.Test;

public class AssertMatchesIT extends WebBase {

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration test to check the compareTableCellText method")
    public void compareTableCellTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertMatches().text(2, 2, "([\\w\\s]*)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration test to check the compareTableCellText method", expectedExceptions = AssertionError.class)
    public void negativeCompareTableCellTextOutOfBoundsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertMatches().text(99, 99, "([\\w\\s]*)");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareTableCellText method", expectedExceptions = AssertionError.class)
    public void negativeCompareTableCellTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table", 0).assertMatches().text(1, 1, "([\\d\\s]*)");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareTableCellText method", expectedExceptions = AssertionError.class)
    public void negativeCompareTableCellTextNotPresetTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertMatches().text(0, 0, "([\\w\\s]*)");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration test to check the compareCssValue method")
    public void compareInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").assertMatches().value("([a-zA-Z]*)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareCssValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that", 0).assertMatches().value("[0-9]*");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration test to check the compareSelectedValue method")
    public void compareSelectedValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertMatches().selectedValue("[a-z]*");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareSelectedValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectedValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).assertMatches().selectedValue("[\\w]{4}");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareSelectedValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectedValueNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "enabled_button").assertMatches().selectedValue("[\\w]");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareSelectedValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectedValueNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertMatches().selectedValue("([\\w&&[^b]])*");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration test to check the compareTextValue method")
    public void compareTextValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").assertMatches()
                .text("([a-zA-Z ]{26})");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareTextValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareTextValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", 0).assertMatches().text("[\\d]{3}");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareTextValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareTextValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertMatches().text("[^0-9]*[12]?[0-9]{1,2}[^0-9]*");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration test to check the compareValue method")
    public void compareValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").assertMatches().value("([a-zA-Z ]*)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input", 0).assertMatches().value("");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareValueNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hover_over_me").assertMatches().value("[tT]rue|[yY]es");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareValue method", expectedExceptions = AssertionError.class)
    public void negativeCompareValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").assertMatches().value(".*true.*");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration test to check the compareSelectedText method")
    public void compareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertMatches().selectedOption("^[^\\d].*");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareSelectedText method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).assertMatches().selectedOption("^[^A-Z].*");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareSelectedText method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectedTextNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").assertMatches().selectedOption("\\s+");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "assert", "matches"},
            description = "An integration negative test to check the compareSelectedText method", expectedExceptions = AssertionError.class)
    public void negativeCompareSelectedTextNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existant-element").assertMatches().selectedOption("\\s+");
        // verify 1 issue
        finish(1);
    }
}