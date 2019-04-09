package unit;

import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.exceptions.InvalidHTTPException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import com.coveros.selenified.exceptions.InvalidReporterException;
import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.utilities.Reporter;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CallTest {

    private Reporter reporter = new Reporter(null, null, null, null, null, null, null, null, null);
    private HTTP http = new HTTP(reporter, "SomeURL");

    public CallTest() throws InvalidBrowserException, InvalidProxyException {
    }

    @Test(expectedExceptions = InvalidHTTPException.class)
    public void constructorNullTest() throws InvalidHTTPException, InvalidReporterException {
        // just verify no errors are thrown
        new Call(null, null);
    }

    @Test
    public void constructorHttpTest() throws InvalidHTTPException, InvalidReporterException {
        // just verify no errors are thrown
        new Call(http, null);
    }

    @Test(expectedExceptions = InvalidReporterException.class)
    public void constructorNullReporterTest() throws InvalidHTTPException, InvalidReporterException {
        // just verify no errors are thrown
        new Call(new HTTP(null, "SomeURL"), null);
    }

    @Test
    public void constructorTest() throws InvalidHTTPException, InvalidReporterException {
        new Call(http, new HashMap<>());
    }
}
