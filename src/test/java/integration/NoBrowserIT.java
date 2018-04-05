package integration;

import com.coveros.selenified.Browser;
import com.coveros.selenified.DriverSetup;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.application.App;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class NoBrowserIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "file://" + System.getProperty("user.dir") + "/public/index.html");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "0.0.1");
    }

    @BeforeMethod(alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        super.startTest(dataProvider, method, test, result, DriverSetup.FALSE);
    }

    @Test(groups = {"integration"}, description = "An integration test to verify we can start a test without a browser")
    public void verifyNoBrowser() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // verify no selenium actions class was setup
        Assert.assertNull(app);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration"}, description = "An integration test to verify we can start a test without a browser")
    public void verifyNoBrowserActions() {
        // use this object to manipulate the app
        Browser browser = this.browser.get();
        // verify no selenium actions class was setup
        Assert.assertEquals(browser, Browser.NONE);
        // verify no issues
        finish();
    }
}