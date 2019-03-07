package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.annotations.Test;

public class VerifyExcludesIT extends WebBase {

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration test to check the checkSelectValueNotPresent method")
    public void checkSelectValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyExcludes().selectValue("ford");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the checkSelectValueNotPresent method")
    public void negativeCheckSelectValueNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).verifyExcludes().selectValue("volvo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the checkSelectValueNotPresent method")
    public void negativeCheckSelectValueNotPresentNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").verifyExcludes().selectValue("volvo");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration test to check the checkIfOptionNotInSelect method")
    public void checkIfOptionNotInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyExcludes().selectOption("ford");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the checkIfOptionNotInSelect method")
    public void negativeCheckIfOptionNotInSelectTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).verifyExcludes().selectOption("Audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the checkIfOptionNotInSelect method")
    public void negativeCheckIfOptionNotInSelectNotEnabledTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "alert_button").verifyExcludes().selectOption("audi");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration test to check the checkElementDoesntContainClass method")
    public void checkElementDoesntContainClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div").verifyExcludes().clazz("wrong_class");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the checkElementDoesntContainClass method")
    public void negativeCheckElementDoesntContainClassTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "hidden_div", 0).verifyExcludes().clazz("hidden_div");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the checkElementDoesntContainClass method")
    public void negativeCheckElementDoesntContainClassNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyExcludes().clazz("hidden_div");
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration test to check the checkElementDoesntHaveAttribute method")
    public void checkElementDoesntHaveAttributeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").verifyExcludes().attribute("class");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
    public void negativeCheckElementDoesntHaveAttributeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list", 0).verifyExcludes().attribute("name");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the checkElementDoesntHaveAttribute method")
    public void negativeCheckElementDoesntHaveAttributeNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyExcludes().attribute("name");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration test to check the compareTextValueExcludes method")
    public void compareTextValueExcludesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]").verifyExcludes().text("Anders");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareTextValueExcludesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.XPATH, "//*[@id=\"table\"]/tbody/tr[2]/td[1]", 0).verifyExcludes().text("Centro comer");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareTextValueExcludesNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyExcludes().text("Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration test to check the compareTextValueExcludes method")
    public void compareValueExcludesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input").verifyExcludes().value("Anders");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareValueExcludesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "textarea_input", 0).verifyExcludes().value("Pretty");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareValueNotInputExcludesTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "overlay_span").verifyExcludes().value("Anders");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "verify", "excludes"},
            description = "An integration negative test to check the compareTextValueExcludes method")
    public void negativeCompareValueExcludesNotPresentTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "non-existent-element").verifyExcludes().value("Anders");
        // verify 1 issue
        finish(1);
    }
}