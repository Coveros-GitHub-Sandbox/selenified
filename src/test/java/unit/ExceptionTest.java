package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class ExceptionTest {

    @Test(expectedExceptions = InvalidBrowserException.class)
    public void invalidBrowserExceptionTest() throws InvalidBrowserException {
        new Capabilities(new Browser("Android"));
        fail("Expected an InvalidBrowserException");
    }
}
