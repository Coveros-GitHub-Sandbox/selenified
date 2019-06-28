package integration;

import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.HTTP.ContentType;
import com.coveros.selenified.services.Request;
import com.google.gson.JsonObject;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class ServicesOverrideIT extends ServicesBase {

    @BeforeClass(alwaysRun = true)
    public void setupHeaders(ITestContext test) {
        // for this test, we want to change the default headers for each call
        Map<String, Object> headers = new HashMap<>();
        headers.put("X-Atlassian-Token", "check");
        addHeaders(this, test, headers);
        setContentType(this, test, ContentType.FORMDATA);
        // for this particular test, we want to set some bogus credentials
        setCredentials(this, test, "servicesUsername", "servicesPassword");
    }

    @Test(groups = {"integration", "service", "headers"},
            description = "An integration test to verify we can successfully set header values")
    public void addHeaderTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom headers
        Map<String, Object> headers = new HashMap<>();
        headers.put("X-Atlassian-Token", "no-check");
        call.addHeaders(headers);
        // perform some actions
        call.get("posts/", new Request()).verify().equals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "headers"},
            description = "An integration test to verify we can successfully set header values")
    public void addHeaderDataTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom headers
        Map<String, Object> headers = new HashMap<>();
        headers.put("X-Atlassian-Token", "no-check");
        call.addHeaders(headers);
        // perform some actions - this will fail as application/xml isn't supported
        call.post("posts/", new Request().setJsonPayload(new JsonObject())).verify().equals(201);
        // verify one issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "headers"},
            description = "An integration test to verify we can successfully override standard header values")
    public void overrideAcceptTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom headers
        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "no-check");
        call.resetHeaders();
        call.addHeaders(headers);
        // perform some actions
        call.get("posts/").verify().equals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "headers"},
            description = "An integration test to verify we can successfully override standard header values")
    public void overrideCredentialsTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom credentials
        call.addCredentials("hello", "world");
        // perform some actions
        call.get("posts/").verify().equals(200);
        // verify no issues
        finish();
    }
}