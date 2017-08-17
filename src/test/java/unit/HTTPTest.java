package unit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coveros.selenified.services.HTTP;

public class HTTPTest {

    @Test
    public void useCredentialsEmptyTest() {
        HTTP http = new HTTP("Service");
        Assert.assertFalse(http.useCredentials());
    }

    @Test
    public void useCredentialsBothTest() {
        HTTP http = new HTTP("Service", "User", "Pass");
        Assert.assertTrue(http.useCredentials());
    }

    @Test
    public void useCredentialsNeitherTest() {
        HTTP http = new HTTP("Service", "", "");
        Assert.assertFalse(http.useCredentials());
    }

    @Test
    public void useCredentialsUserTest() {
        HTTP http = new HTTP("Service", "User", "");
        Assert.assertFalse(http.useCredentials());
    }

    @Test
    public void useCredentialsPassTest() {
        HTTP http = new HTTP("Service", "", "Pass");
        Assert.assertFalse(http.useCredentials());
    }
}