package integration;

import com.coveros.selenified.DriverSetup;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.services.Call;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ServicesOverrideIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "https://jsonplaceholder.typicode.com/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "3.0.2");
        // for this test, we want to change the default headers for each call
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/xml");
        addHeaders(this, test, headers);
        // for this particular test, we want to set some bogus credentials
        setCredentials(this, test, "servicesUsername", "servicesPassword");
    }

    @BeforeMethod(alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        super.startTest(dataProvider, method, test, result, DriverSetup.FALSE);
    }

    @Test(groups = {"integration", "services", "headers"},
            description = "An integration test to verify we can successfully set header values")
    public void addHeaderTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom headers
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Atlassian-Token", "no-check");
        call.addHeaders(headers);
        // perform some actions
        call.get("posts/").assertEquals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "headers"},
            description = "An integration test to verify we can successfully override standard header values")
    public void overrideAcceptTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "no-check");
        call.resetHeaders();
        call.addHeaders(headers);
        // perform some actions
        call.get("posts/").assertEquals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "headers"},
            description = "An integration test to verify we can successfully override standard header values")
    public void overrideCredentialsTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom credentials
        call.addCredentials("hello", "world");
        // perform some actions
        call.get("posts/").assertEquals(200);
        // verify no issues
        finish();
    }
}