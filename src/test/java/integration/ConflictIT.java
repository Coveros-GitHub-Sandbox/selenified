package integration;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;
import org.testng.annotations.Test;

@Test(groups = {"conflict full"})
public class ConflictIT extends WebBase {

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
