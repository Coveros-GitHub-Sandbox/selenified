package integration;

import com.coveros.selenified.Selenified;
import com.coveros.selenified.application.App;
import com.coveros.selenified.utilities.TestSetup;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SelenifiedIT extends Selenified {

    private String setScreensize = null;

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "https://www.google.com/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "3.0.2");
    }

    @BeforeClass
    public void saveScreensize() {
        if (System.getProperty("screensize") != null) {
            setScreensize = System.getProperty("screensize");
        }
    }

    @AfterClass
    public void restoreScreensize() {
        if (setScreensize != null) {
            System.setProperty("screensize", setScreensize);
        }
    }

    @AfterMethod
    public void clearBrowser() {
        System.clearProperty("screensize");
    }

    @Test
    public void noAnnotationDetailsTest() {
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "selenified"})
    public void noDescriptionTest() {
        // verify no issues
        finish();
    }

    @Test(description = "A test to verify that logs work without any groups")
    public void noGroupsTest() {
        // verify no issues
        finish();
    }

    @Test(groups = "integration")
    public void oneGroupTest() {
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "browser"},
            description = "A test to verify that resizing the application works from passed in paramters")
    public void resizeValidSizeTest() {
        System.setProperty("screensize", "600x800");
        // use this object to manipulate the app
        App app = this.apps.get();
        TestSetup.setupScreenSize(app);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "browser"},
            description = "A test to verify that resizing the application works from passed in paramters")
    public void resizeMaximizeTest() {
        System.setProperty("screensize", "maximum");
        // use this object to manipulate the app
        App app = this.apps.get();
        TestSetup.setupScreenSize(app);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "browser"},
            description = "A test to verify that resizing the application works from passed in paramters")
    public void resizeInvalidTest() {
        System.setProperty("screensize", "large");
        // use this object to manipulate the app
        App app = this.apps.get();
        TestSetup.setupScreenSize(app);
        // verify no issues
        finish();
    }
}
