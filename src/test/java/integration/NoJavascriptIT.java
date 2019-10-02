package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NoJavascriptIT extends WebBase {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        addAdditionalDesiredCapabilities(this, test, "javascriptEnabled", false);
        super.beforeClass(test);
    }

    @Test(groups = {"integration", "assert", "excludes"},
            description = "An integration test to check the checkElementDoesntHaveAttribute method")
    public void checkElementDoesntHaveAnyAttributeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.TAGNAME, "body").assertExcludes().attribute("class");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "assert", "contains"},
            description = "An integration test to check the checkElementDoesntHaveAttribute method", expectedExceptions = AssertionError.class)
    public void checkElementDoesHaveAnAttributeTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.TAGNAME, "body").assertContains().attribute("class");
        // verify one issue
        finish(1);
    }
}
