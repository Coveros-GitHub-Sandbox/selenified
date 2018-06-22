package integration;

import com.coveros.selenified.Selenified;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SelenifiedIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://www.google.com/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "3.0.2");
    }

    @Test
    public void noAnnotationDetailsTest() {
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "selenified"})
    public void noDescriptionTest() {
        // verify 1 issues
        finish(1);
    }

    @Test(description = "A test to verify that logs work without any groups")
    public void noGroupsTest() {
        // verify 1 issues
        finish(1);
    }

    @Test(groups = "integration")
    public void oneGroupTest() {
        // verify 1 issues
        finish(1);
    }
}
