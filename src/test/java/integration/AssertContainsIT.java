package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.annotations.Test;

public class AssertContainsIT extends WebBase {

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the compareNumOfSelectOptions method")
    public void compareNumOfSelectOptionsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.NAME, "car_list").assertContains().selectOptions(4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the compareNumOfTableRows method")
    public void compareNumOfTableRowsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertContains().rows(7);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the compareNumOfTableColumns method")
    public void compareNumOfTableColumnsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "table").assertContains().columns(4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the checkIfOptionInSelect method")
    public void checkIfOptionInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertContains().selectOption("Audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration negative test to check the checkIfOptionInSelect method")
    public void checkIfValueInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertContains().selectValue("audi");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the checkElementContainsClass method")
    public void checkElementContainsClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").assertContains().clazz("hidden_div");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the checkElementHasAttribute method")
    public void checkElementHasAttributeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").assertContains().attribute("name");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the compareTextValueContains method")
    public void compareTextValueContainsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").assertContains().text("Centro comer");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the compareTextValueContains method")
    public void compareValueContainsTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").assertContains().value("Pretty");
        // verify no issues
        finish();
    }
}