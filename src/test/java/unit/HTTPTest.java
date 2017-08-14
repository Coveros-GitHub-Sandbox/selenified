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
    public void useCredentialsBothCTest() {
        HTTP http = new HTTP("Service", "User", "Pass");
        Assert.assertTrue(http.useCredentials());
    }

}