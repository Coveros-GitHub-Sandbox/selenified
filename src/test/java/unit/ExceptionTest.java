package unit;

import java.net.MalformedURLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import selenified.exceptions.InvalidBrowserException;
import tools.logging.TestOutput;
import tools.selenium.SeleniumHelper;

public class ExceptionTest {
	
//	@Test(expectedExceptions=InvalidBrowserException.class)
//	public void invalidBrowserExceptionTest() {
//		TestOutput output = null;
//		System.setProperty("browser","IE");
//		try {
//			new SeleniumHelper( output );
//			Assert.fail("Expected an InvalidBrowserException");
//		} catch (InvalidBrowserException e) {
//			Assert.assertEquals(e.getMessage(), "");
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//	}
}
