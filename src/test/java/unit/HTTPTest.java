package unit;

import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.services.Request;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void buildStringEmptyParamTest() {
        HTTP http = new HTTP("Service");
        Assert.assertEquals(http.getRequestParams(new Request()), "");
    }

    @Test
    public void buildStringNoParamTest() {
        HTTP http = new HTTP("Service");
        Assert.assertEquals(http.getRequestParams(new Request().setUrlParams(new HashMap<>())), "");
    }

    @Test
    public void buildStringSingleParamTest() {
        HTTP http = new HTTP("Service");
        Map<String, Object> params = new HashMap<>();
        params.put("hello", "world");
        Assert.assertEquals(http.getRequestParams(new Request().setUrlParams(params)), "?hello=world");
    }

    @Test
    public void buildStringMultipleParamsTest() {
        HTTP http = new HTTP("Service");
        Map<String, Object> params = new HashMap<>();
        params.put("hello", "world");
        params.put("john", 5);
        Assert.assertEquals(http.getRequestParams(new Request().setUrlParams(params)), "?john=5&hello=world");
    }
}