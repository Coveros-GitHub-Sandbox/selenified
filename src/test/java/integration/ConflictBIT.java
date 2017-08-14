package integration;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.App;
import com.coveros.selenified.selenium.Selenium.Locator;
import com.coveros.selenified.selenium.element.Element;
import com.coveros.selenified.tools.Selenified;

public class ConflictBIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://172.31.2.65/");
        // set the author of the tests here
        setAuthor(this, test, "Matt Grasberger\n<br/>matthew.grasberger@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "0.0.1");
    }

    @Test(groups = { "integration", "conflict",
            "virtual" }, description = "A sample test to show how to loop through elements with multiple matches")
    public void conflictingTestName() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element element = app.newElement(Locator.XPATH, "//form/input[@type='checkbox']");
        for (int match = 0; match < element.get().matchCount(); match++) {
            element.setMatch(match);
            element.click();
            element.assertState().checked();
        }
        // close out the test
        finish();
    }

}
