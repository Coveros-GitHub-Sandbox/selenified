package unit;

import com.coveros.selenified.exceptions.*;
import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.utilities.Reporter;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class CallTest {

    HTTP http = new HTTP("SomeURL");
    Reporter reporter = new Reporter(null, null, null, null, null, null, null, null, null);

    public CallTest() throws InvalidBrowserException, InvalidProxyException, InvalidHubException {
    }

    @Test(expectedExceptions = InvalidHTTPException.class)
    public void constructorNullTest() throws InvalidHTTPException, InvalidReporterException {
        // just verify no errors are thrown
        new Call(null, null, null);
    }

    @Test(expectedExceptions = InvalidReporterException.class)
    public void constructorHttpTest() throws InvalidHTTPException, InvalidReporterException {
        // just verify no errors are thrown
        new Call(http, null, null);
    }

    @Test
    public void constructorHttpAndReporterTest() throws InvalidHTTPException, InvalidReporterException {
        new Call(http, reporter, null);
    }

    @Test
    public void constructorTest() throws InvalidHTTPException, InvalidReporterException {
        new Call(http, reporter, new HashMap<>());
    }

    @Test
    public void appendCredentialsNull() throws InvalidHTTPException, InvalidReporterException {
        Call call = new Call(http, reporter, new HashMap<>());
        assertEquals(call.getCredentialString(), "");
    }

    @Test
    public void appendCredentials() throws InvalidHTTPException, InvalidReporterException {
        HTTP http = new HTTP("SomeURL", "User", "Pass");
        Call call = new Call(http, reporter, new HashMap<>());
        assertEquals(call.getCredentialString(),
                "<br/> with credentials: <div><i>Username: User</div><div>Password: Pass</i></div>");
    }

    @Test
    public void addCredentials() throws InvalidHTTPException, InvalidReporterException {
        HTTP http = new HTTP("SomeURL");
        Call call = new Call(http, reporter, new HashMap<>());
        call.addCredentials("User", "Pass");
        assertEquals(call.getCredentialString(),
                "<br/> with credentials: <div><i>Username: User</div><div>Password: Pass</i></div>");
    }
}
