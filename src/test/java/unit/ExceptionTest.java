package unit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.coveros.selenified.Browser;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.utilities.TestSetup;

public class ExceptionTest {

    @Test
    public void invalidBrowserExceptionTest() {
        try {
            TestSetup.setupDriver(Browser.ANDROID, new DesiredCapabilities());
            Assert.fail("Expected an InvalidBrowserException");
        } catch (InvalidBrowserException e) {
            Assert.assertEquals(e.getMessage(), "The selected browser ANDROID is not an applicable choice");
        }
    }
}
