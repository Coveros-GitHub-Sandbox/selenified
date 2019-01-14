package unit;

import com.coveros.selenified.Capabilities;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SelenifiedTest extends Selenified {

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

    @Test
    public void versionTest(ITestContext context) {
        setVersion(this, context, "1.0.0");
        assertEquals(getVersion(this.getClass().getName(), context), "1.0.0");
    }

    @Test
    public void authorTest(ITestContext context) {
        setAuthor(this, context, "Max");
        assertEquals(getAuthor(this.getClass().getName(), context), "Max");
    }
}