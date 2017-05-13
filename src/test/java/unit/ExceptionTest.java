package unit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import selenified.exceptions.InvalidBrowserException;
import tools.TestSetup;
import tools.output.Selenium.Browsers;

public class ExceptionTest {
	
	@Test
	public void invalidBrowserExceptionTest() {
		try {
			TestSetup.setupDriver(Browsers.Android, new DesiredCapabilities() );
			Assert.fail("Expected an InvalidBrowserException");
		} catch (InvalidBrowserException e) {
			Assert.assertEquals(e.getMessage(), "The selected browser Android is not an applicable choice");
		}
	}
}
