package integration;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Action;
import com.coveros.selenified.selenium.Selenium.DriverSetup;
import com.coveros.selenified.tools.TestBase;

public class NoBrowserIT extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws IOException {
        // set the base URL for the tests here
        setTestSite("http://172.31.2.65/");
        // set the author of the tests here
        setAuthor("Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion("0.0.1");
    }

    @BeforeMethod(alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result)
            throws IOException {
        super.startTest(dataProvider, method, test, result, DriverSetup.FALSE);
    }

    @Test(groups = { "integration",
            "virtual" }, description = "An integration test to verify we can start a test without a browser")
    public void verifyNoBrowser() throws IOException {
        // use this object to manipulate the page
        Action actions = this.actions.get();
        // verify no selenium actions class was setup
        Assert.assertNull(actions);
        // verify no issues
        finish();
    }
}