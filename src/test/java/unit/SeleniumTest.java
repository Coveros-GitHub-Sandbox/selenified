package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Browser.BrowserName;
import com.coveros.selenified.DriverSetup;
import com.coveros.selenified.OutputFile.Success;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SeleniumTest {

    @Test
    public void driverSetupTest() {
        Assert.assertFalse(DriverSetup.FALSE.useBrowser());
        Assert.assertTrue(DriverSetup.OPEN.useBrowser());
        Assert.assertTrue(DriverSetup.LOAD.useBrowser());

        Assert.assertFalse(DriverSetup.FALSE.loadPage());
        Assert.assertFalse(DriverSetup.OPEN.loadPage());
        Assert.assertTrue(DriverSetup.LOAD.loadPage());
    }

    @Test
    public void errorsForPassTest() {
        Assert.assertEquals(Success.PASS.getErrors(), 0);
    }

    @Test
    public void errorsForFailTest() {
        Assert.assertEquals(Success.FAIL.getErrors(), 1);
    }
}