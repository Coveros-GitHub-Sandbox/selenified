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
            description = "An integration test to check the compareSelectedText method")
    public void compareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertMatches().selectedOption("^[^\\d].*");
        // verify no issues
        finish();
    }
}