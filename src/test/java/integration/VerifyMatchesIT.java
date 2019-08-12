package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.annotations.Test;

public class VerifyMatchesIT extends WebBase {

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration test to check the compareTableCellText method")
    public void compareTableCellTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").verifyMatches().text(2, 2, "([\\w\\s]*)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextOutOfBoundsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table", 0).verifyMatches().text(99, 99, "([\\d\\s]*)");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table", 0).verifyMatches().text(1, 1, "([\\d\\s]*)");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareTableCellText method")
    public void negativeCompareTableCellTextNotPresetTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyMatches().text(0, 0, "([\\w\\s]*)");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration test to check the compareCssValue method")
    public void compareInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that").verifyMatches().value("([a-zA-Z]*)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareCssValue method")
    public void negativeCompareInputValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "that", 0).verifyMatches().value("[0-9]*");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration test to check the compareSelectedValue method")
    public void compareSelectedValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyMatches().selectedValue("[a-z]*");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).verifyMatches().selectedValue("[\\w]{4}");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "enabled_button").verifyMatches().selectedValue("[\\w]");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareSelectedValue method")
    public void negativeCompareSelectedValueNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").verifyMatches().selectedValue("([\\w&&[^b]])*");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration test to check the compareTextValue method")
    public void compareTextValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").verifyMatches()
                .text("([a-zA-Z ]{26})");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareTextValue method")
    public void negativeCompareTextValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", 0).verifyMatches().text("[\\d]{3}");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareTextValue method")
    public void negativeCompareTextValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyMatches().text("[^0-9]*[12]?[0-9]{1,2}[^0-9]*");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration test to check the compareValue method")
    public void compareValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").verifyMatches().value("([a-zA-Z ]*)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input", 0).verifyMatches().value("");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueNotInputTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hover_over_me").verifyMatches().value("[tT]rue|[yY]es");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareValue method")
    public void negativeCompareValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyMatches().value(".*true.*");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration test to check the compareSelectedText method")
    public void compareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyMatches().selectedOption("^[^\\d].*");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).verifyMatches().selectedOption("^[^A-Z].*");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "matches"},
            description = "An integration negative test to check the compareSelectedText method")
    public void negativeCompareSelectedTextNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").verifyMatches().selectedOption("\\s+");
        // verify 1 issue
        finish(1);
    }
}