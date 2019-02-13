package unit;

import com.coveros.selenified.Selenified;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SelenifiedTest extends Selenified {

    private String appURL;

    @BeforeClass
    public void setupArrays() {
        if (System.getProperty("appURL") != null) {
            appURL = System.getProperty("appURL");
        }
    }

    @AfterClass
    public void restoreBrowser() {
        if (appURL != null) {
            System.setProperty("appURL", appURL);
        }
    }

    @BeforeMethod
    @AfterMethod
    public void clearBrowser() {
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