package unit;

import com.coveros.selenified.DriverSetup;
import com.coveros.selenified.OutputFile;
import com.coveros.selenified.Selenified;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.*;

public class SelenifiedTest extends Selenified {

    private String setAppURL = null;

    @BeforeClass(alwaysRun = true)
    public void saveBrowser() {
        if (System.getProperty("appURL") != null) {
            setAppURL = System.getProperty("appURL");
        }
    }

    @AfterClass
    public void restoreBrowser() {
        System.clearProperty("appURL");
        if (setAppURL != null) {
            System.setProperty("appURL", setAppURL);
        }
    }

    @BeforeMethod(alwaysRun = true)
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

    @Test
    public void driverSetupTest() {
        assertFalse(DriverSetup.FALSE.useBrowser());
        assertTrue(DriverSetup.OPEN.useBrowser());
        assertTrue(DriverSetup.LOAD.useBrowser());

        assertFalse(DriverSetup.FALSE.loadPage());
        assertFalse(DriverSetup.OPEN.loadPage());
        assertTrue(DriverSetup.LOAD.loadPage());
    }

    @Test
    public void errorsForPassTest() {
        assertEquals(OutputFile.Success.PASS.getErrors(), 0);
    }

    @Test
    public void errorsForFailTest() {
        assertEquals(OutputFile.Success.FAIL.getErrors(), 1);
    }
}