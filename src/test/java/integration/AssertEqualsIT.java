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
            description = "An integration test to check the compareSelectedText method")
    public void compareSelectedTextTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertEquals().selectedOption("Volvo");
        // verify no issues
        finish();
    }
}