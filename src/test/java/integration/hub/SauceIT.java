package integration.hub;

import com.coveros.selenified.Browser;
import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Response;
import com.coveros.selenified.utilities.Hub;
import com.google.gson.JsonArray;
import org.openqa.selenium.remote.SessionId;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SauceIT extends HubIT {

    SessionId sessionId;
    SessionId paramSessionId;
    String buildName;

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setAppURL(this, test, "https://saucelabs.com/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a dynamic check
        setVersion(this, test, "3.2.1");

        sessionId = (SessionId) test.getAttribute("HUB_SESSION_ID");
        paramSessionId = (SessionId) test.getAttribute("PARAM_SESSION_ID");
        buildName = test.getAttribute("HUB_BUILD").toString();
    }

    @BeforeMethod(alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) throws IOException {
        super.startTest(dataProvider, method, test, result, Browser.BrowserUse.FALSE);
    }

    @Test(groups = {"integration", "hub", "sauce"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that sauce get all expected information")
    public void sauceSeleniumVersionTest() throws IOException {
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("rest/v1/" + hub.getUsername() + "/jobs/" + sessionId);
        Map expectedResults = new HashMap<>();
        expectedResults.put("selenium_version", System.getProperty("selenium.version"));
        response.azzert().contains(expectedResults);
        finish();
    }

    @Test(groups = {"integration", "hub", "sauce"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that sauce get all expected information")
    public void saucePassedTest() throws IOException {
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("rest/v1/" + hub.getUsername() + "/jobs/" + sessionId);
        Map expectedResults = new HashMap<>();
        expectedResults.put("passed", true);
        response.azzert().contains(expectedResults);
        finish();
    }

    @Test(groups = {"integration", "hub", "sauce"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that sauce get all expected information")
    public void sauceTagsTest() throws IOException {
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("rest/v1/" + hub.getUsername() + "/jobs/" + sessionId);
        Map expectedResults = new HashMap<>();
        JsonArray tags = new JsonArray();
        tags.add("hub");
        tags.add("integration");
        expectedResults.put("tags", tags);
        response.azzert().contains(expectedResults);
        finish();
    }

    @Test(groups = {"integration", "hub", "sauce"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that sauce get all expected information")
    public void sauceBuildTest() throws IOException {
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("rest/v1/" + hub.getUsername() + "/jobs/" + sessionId);
        Map expectedResults = new HashMap<>();
        expectedResults.put("build", buildName);
        response.azzert().contains(expectedResults);
        finish();
    }

    @Test(groups = {"integration", "hub", "sauce"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that sauce get all expected information")
    public void sauceNameTest() throws IOException {
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("rest/v1/" + hub.getUsername() + "/jobs/" + sessionId);
        Map expectedResults = new HashMap<>();
        expectedResults.put("name", "integration.hub.HubIT.hubTitleTest");
        response.azzert().contains(expectedResults);
        finish();
    }

    @Test(groups = {"integration", "hub", "sauce"}, dependsOnMethods = {"hubSearchTest"},
            description = "An integration test to check that sauce get all expected information")
    public void sauceComplexNameTest() throws IOException {
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("rest/v1/" + hub.getUsername() + "/jobs/" + paramSessionId);
        Map expectedResults = new HashMap<>();
        expectedResults.put("name", "integration.hub.HubIT.hubSearchTestWithOptionSelenified");
        response.azzert().contains(expectedResults);
        finish();
    }
}
