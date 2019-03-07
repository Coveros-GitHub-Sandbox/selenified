package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.annotations.Test;

public class AssertExcludesIT extends WebBase {

    @Test(groups = {"integration", "assert", "excludes"},
            description = "An integration test to check the checkSelectValueNotPresent method")
    public void checkSelectValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertExcludes().selectValue("ford");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "excludes"},
            description = "An integration test to check the checkIfOptionNotInSelect method")
    public void checkIfOptionNotInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertExcludes().selectOption("ford");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "excludes"},
            description = "An integration test to check the checkElementDoesntContainClass method")
    public void checkElementDoesntContainClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertExcludes().clazz("wrong_class");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "excludes"},
            description = "An integration test to check the checkElementDoesntHaveAttribute method")
    public void checkElementDoesntHaveAttributeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertExcludes().attribute("class");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "excludes"},
            description = "An integration test to check the compareTextValueExcludes method")
    public void compareTextValueExcludesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").assertExcludes().text("Anders");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "excludes"},
            description = "An integration test to check the compareTextValueExcludes method")
    public void compareValueExcludesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").assertExcludes().value("Anders");
        // verify no issues
        finish();
    }
}