package unit;

import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.services.Request;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class HTTPTest {

    @Test
    public void useCredentialsEmptyTest() {
        HTTP http = new HTTP("Service");
        assertFalse(http.useCredentials());
    }

    @Test
    public void useCredentialsBothTest() {
        HTTP http = new HTTP("Service", "User", "Pass");
        assertTrue(http.useCredentials());
    }

    @Test
    public void useCredentialsNeitherTest() {
        HTTP http = new HTTP("Service", "", "");
        assertFalse(http.useCredentials());
    }

    @Test
    public void useCredentialsUserTest() {
        HTTP http = new HTTP("Service", "User", "");
        assertFalse(http.useCredentials());
    }

    @Test
    public void useCredentialsPassTest() {
        HTTP http = new HTTP("Service", "", "Pass");
        assertFalse(http.useCredentials());
    }

    @Test
    public void buildStringNullParamTest() {
        HTTP http = new HTTP("Service");
        assertEquals(http.getRequestParams(null), "");
    }

    @Test
    public void buildStringEmptyParamTest() {
        HTTP http = new HTTP("Service");
        assertEquals(http.getRequestParams(new Request()), "");
    }

    @Test
    public void buildStringNoParamTest() {
        HTTP http = new HTTP("Service");
        assertEquals(http.getRequestParams(new Request().setUrlParams(new HashMap<>())), "");
    }

    @Test
    public void buildStringSingleParamTest() {
        HTTP http = new HTTP("Service");
        Map<String, Object> params = new HashMap<>();
        params.put("hello", "world");
        assertEquals(http.getRequestParams(new Request().setUrlParams(params)), "?hello=world");
    }

    @Test
    public void buildStringMultipleParamsTest() {
        HTTP http = new HTTP("Service");
        Map<String, Object> params = new HashMap<>();
        params.put("hello", "world");
        params.put("john", 5);
        assertEquals(http.getRequestParams(new Request().setUrlParams(params)), "?john=5&hello=world");
    }

    @Test
    public void checkAddingCredentialsTest() {
        HTTP http = new HTTP("Service");
        http.addCredentials("User", "Pass");
        assertTrue(http.useCredentials());
    }

    @Test
    public void checkAddingUserCredentialsTest() {
        HTTP http = new HTTP("Service");
        http.addCredentials("User", "");
        assertFalse(http.useCredentials());
    }

    @Test
    public void checkAddingPassCredentialsTest() {
        HTTP http = new HTTP("Service");
        http.addCredentials("", "Pass");
        assertFalse(http.useCredentials());
    }

    @Test
    public void checkAddingNoCredentialsTest() {
        HTTP http = new HTTP("Service");
        http.addCredentials("", "");
        assertFalse(http.useCredentials());
    }

    @Test
    public void checkAddingBaseUrl() {
        HTTP http = new HTTP("Service");
        assertEquals(http.getServiceBaseUrl(), "Service");
    }

    @Test
    public void checkAddingNullBaseUrl() {
        HTTP http = new HTTP(null);
        assertNull(http.getServiceBaseUrl());
    }

    @Test
    public void checkAddingEmptyBaseUrl() {
        HTTP http = new HTTP("");
        assertEquals(http.getServiceBaseUrl(), "");
    }
}