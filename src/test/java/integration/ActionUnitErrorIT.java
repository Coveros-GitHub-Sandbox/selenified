package integration;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import org.testng.Assert;

import com.coveros.selenified.Selenified;
import com.coveros.selenified.Browser;
import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;

public class ActionUnitErrorIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://172.31.2.65/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "0.0.1");
    }

    @Test(groups = { "integration", "actions", "unit",
            "virtual" }, description = "An integration negative test to check the get css method")
    public void getCssErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        if (app.getBrowser() == Browser.HTMLUNIT) {
            String css = app.newElement(Locator.CSS, "input#alert_button").get().css("style");
            Assert.assertNull(css);
        }
        finish();
    }

    @Test(groups = { "integration", "actions", "unit",
            "virtual" }, description = "An integration negative test to check the get css method")
    public void assessCssEqualsErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        if (app.getBrowser() == Browser.HTMLUNIT) {
            app.newElement(Locator.CSS, "input#alert_button").assertEquals().cssValue("font", "bold");
            finish(1);
        } else {
            finish();
        }
    }

    @Test(groups = { "integration", "actions", "unit",
            "virtual" }, description = "An integration negative test to check the get all attributes method")
    public void getAllAttributesErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        if (app.getBrowser() == Browser.HTMLUNIT) {
            Map<String, String> attributes = app.newElement(Locator.CSS, "input#alert_button").get().allAttributes();
            Assert.assertNull(attributes);
        }
        finish();
    }

    @Test(groups = { "integration", "actions", "unit",
            "virtual" }, description = "An integration negative test to check the get all attributes method")
    public void assessAttributesContainErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        if (app.getBrowser() == Browser.HTMLUNIT) {
            app.newElement(Locator.CSS, "input#alert_button").assertContains().attribute("display");
            finish(1);
        } else {
            finish();
        }
    }

    @Test(groups = { "integration", "actions", "unit",
            "virtual" }, description = "An integration negative test to check the get all attributes method")
    public void assessAttributesExcludeErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        if (app.getBrowser() == Browser.HTMLUNIT) {
            app.newElement(Locator.CSS, "input#alert_button").assertExcludes().attribute("display");
            finish(1);
        } else {
            finish();
        }
    }

    @Test(groups = { "integration", "actions", "unit",
            "virtual" }, description = "An integration negative test to check the get eval method")
    public void getEvalErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        if (app.getBrowser() == Browser.HTMLUNIT) {
            Object eval = app.get().eval("console.out('hello world')");
            Assert.assertNull(eval);
        }
        finish();
    }

    @Test(groups = { "integration", "actions", "unit",
            "virtual" }, description = "An integration negative test to check the get eval method")
    public void getEvalElementErrorTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        if (app.getBrowser() == Browser.HTMLUNIT) {
            Object eval = app.newElement(Locator.CSS, "input#alert_button").get().eval("console.out('hello world')");
            Assert.assertNull(eval);
        }
        finish();
    }
}