package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConflictBIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://34.233.135.10/");
        // set the author of the tests here
        setAuthor(this, test, "Matt Grasberger\n<br/>matthew.grasberger@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "3.0.2");
    }

    @Test(groups = {"integration", "conflict"},
            description = "A sample test to show how to loop through elements with multiple matches")
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
