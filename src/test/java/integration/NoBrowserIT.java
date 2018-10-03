package integration;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Browser.BrowserName;
import com.coveros.selenified.DriverSetup;
import com.coveros.selenified.application.App;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class NoBrowserIT extends WebBase {

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
        Assert.assertEquals(browser.getName(), BrowserName.NONE);
        // verify no issues
        finish();
    }
}