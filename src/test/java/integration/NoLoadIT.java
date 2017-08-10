package integration;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.App;
import com.coveros.selenified.selenium.Selenium.DriverSetup;
import com.coveros.selenified.tools.Selenified;

public class NoLoadIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        // set the base URL for the tests here
        setTestSite("http://172.31.2.65/");
        // set the author of the tests here
        setAuthor("Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion("0.0.1");
    }

    @BeforeMethod(alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        super.startTest(dataProvider, method, test, result, DriverSetup.OPEN);
    }

    @Test(groups = { "integration",
            "virtual" }, description = "An integration test to verify we can start a test with a browser, but won't load any app")
    public void verifyNoLoad() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // verify a selenium actions class was setup
        org.testng.Assert.assertNotNull(app);
        org.testng.Assert.assertEquals(
                app.getOutputFile().countInstancesOf("Opening new browser and loading up starting app"), 0);
        // verify the app wasn't attempted to load
        app.urlEquals(getTestSite());
        // verify one issue from the above check
        finish(1);
    }
}