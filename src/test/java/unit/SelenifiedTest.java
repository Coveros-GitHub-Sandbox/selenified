package unit;

import com.coveros.selenified.Selenified;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class SelenifiedTest extends Selenified {

    private String setAppURL = null;
    private String setBrowser = null;
    private String setHub = null;
    private String setProxy = null;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws InvalidBrowserException {
        // add some extra capabilities
        extraCapabilities = new DesiredCapabilities();
        extraCapabilities.setCapability("ignoreProtectedModeSettings", true);
        extraCapabilities.setCapability("unexpectedAlertBehaviour", "ignore");
        // save the passed in information
        if (System.getProperty("appURL") != null) {
            setAppURL = System.getProperty("appURL");
            System.clearProperty("appURL");
        }
        if (System.getProperty("browser") != null) {
            setBrowser = System.getProperty("browser");
            System.clearProperty("browser");
        }
        if (System.getProperty("hub") != null) {
            setHub = System.getProperty("hub");
            System.clearProperty("hub");
        }
        if (System.getProperty("proxy") != null) {
            setProxy = System.getProperty("proxy");
            System.clearProperty("proxy");
        }
        super.beforeSuite();
    }

    @AfterClass
    public void restoreBrowser() {
        if (setAppURL != null) {
            System.setProperty("appURL", setAppURL);
        }
        if (setBrowser != null) {
            System.setProperty("browser", setBrowser);
        }
        if (setHub != null) {
            System.setProperty("hub", setHub);
        }
        if (setProxy != null) {
            System.setProperty("proxy", setProxy);
        }
    }

    @BeforeMethod
    public void clearBrowser() {
        System.clearProperty("appURL");
        System.clearProperty("browser");
        System.clearProperty("hub");
        System.clearProperty("proxy");
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
    public void extraCapabilitiesTest() {
        DesiredCapabilities capability = capabilities.get(0);
        Assert.assertTrue((boolean) capability.getCapability("ignoreProtectedModeSettings"));
        Assert.assertEquals(capability.getCapability("unexpectedAlertBehaviour"), "ignore");
    }

    @Test
    public void siteTest(ITestContext context) {
        setTestSite(this, context, "yahoo");
        Assert.assertEquals(getTestSite(this.getClass().getName(), context), "yahoo");
        System.setProperty("appURL", "http://www.yahoo.com");
        setTestSite(this, context, "google");
        Assert.assertEquals(getTestSite(this.getClass().getName(), context), "http://www.yahoo.com");
    }

    @Test
    public void versionTest(ITestContext context) {
        setVersion(this, context, "1.0.0");
        Assert.assertEquals(getVersion(this.getClass().getName(), context), "1.0.0");
    }

    @Test
    public void authorTest(ITestContext context) {
        setAuthor(this, context, "Max");
        Assert.assertEquals(getAuthor(this.getClass().getName(), context), "Max");
    }
}