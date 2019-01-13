package unit;

import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.HTTP;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.testng.Assert.assertEquals;

public class CallTest {

    @Test
    public void constructorNullTest() {
        // just verify no errors are thrown
        new Call(null, null, null);
    }

    @Test
    public void constructorHttpAndHeadersTest() {
        HTTP http = new HTTP("SomeURL");
        new Call(http, null, new HashMap<>());
    }

    @Test
    public void appendCredentialsNull() {
        HTTP http = new HTTP("SomeURL");
        Call call = new Call(http, null, new HashMap<>());
        assertEquals(call.getCredentialString(), "");
    }

    @Test
    public void appendCredentials() {
        HTTP http = new HTTP("SomeURL", "User", "Pass");
        Call call = new Call(http, null, new HashMap<>());
        assertEquals(call.getCredentialString(),
                "<br/> with credentials: <div><i>Username: User</div><div>Password: Pass</i></div>");
    }

    @Test
    public void addCredentials() {
        HTTP http = new HTTP("SomeURL");
        Call call = new Call(http, null, new HashMap<>());
        call.addCredentials("User", "Pass");
        assertEquals(call.getCredentialString(),
                "<br/> with credentials: <div><i>Username: User</div><div>Password: Pass</i></div>");
    }
}
