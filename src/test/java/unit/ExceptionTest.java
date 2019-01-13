package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class ExceptionTest {

    @Test
    public void invalidBrowserExceptionTest() {
        try {
            Capabilities capabilities = new Capabilities(new Browser("Android"));
            capabilities.setupDriver();
            Assert.fail("Expected an InvalidBrowserException");
        } catch (InvalidBrowserException e) {
            assertEquals(e.getMessage(), "The selected browser ANDROID is not an applicable choice");
        }
    }
}
