package unit;

import com.coveros.selenified.Selenified;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;

public class SelenifiedTest extends Selenified {

    private String appURL;

    @BeforeClass(alwaysRun = true)
    public void saveAppURL() {
        if (System.getProperty("appURL") != null) {
            appURL = System.getProperty("appURL");
        }
    }

    @AfterClass(alwaysRun = true)
    public void restoreAppURL() {
        if (appURL != null) {
            System.setProperty("appURL", appURL);
        }
    }

    @BeforeMethod(alwaysRun = true)
    @AfterMethod(alwaysRun = true)
    public void clearAppURL() {
        System.clearProperty("appURL");
    }

    @Override
    public void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        // do nothing
    }

    @Override
    public void endTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        // do nothing
    }

    @Test
    public void siteTest(ITestContext context) {
        setTestSite(this, context, "yahoo");
        assertEquals(getTestSite(this.getClass().getName(), context), "yahoo");
        System.setProperty("appURL", "http://www.yahoo.com");
        setTestSite(this, context, "google");
        assertEquals(getTestSite(this.getClass().getName(), context), "http://www.yahoo.com");
    }
}