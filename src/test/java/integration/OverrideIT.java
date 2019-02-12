package integration;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public class OverrideIT extends WebBase {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        addAdditionalDesiredCapabilities(this, test, "javascriptEnabled", false);
        super.beforeClass(test);
    }

    @Test(groups = {"integration", "selenified", "override"},
            description = "An integration test to check that properties can be overridden")
    public void overrideTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element table = app.newElement(Locator.ID, "table");
        if (app.getBrowser().getName() == Browser.BrowserName.HTMLUNIT) {
            table.assertExcludes().attribute("id");
            // verify one issue
            finish(1);
        } else {
            table.assertContains().attribute("id");
            // verify no issues
            finish();
        }
    }

    @Test(groups = {"integration", "selenified", "override"},
            description = "An integration test to check that properties can be overridden")
    public void overrideVarCheckTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        DesiredCapabilities desiredCapabilities = app.getDesiredCapabilities();
        assertFalse(app.getDesiredCapabilities().isJavascriptEnabled());
    }
}
