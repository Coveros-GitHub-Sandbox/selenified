package integration.hub;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Locator;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;
import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Response;
import com.coveros.selenified.utilities.Hub;
import com.google.gson.JsonArray;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SauceIT extends Selenified {

    private ITestContext test;

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setAppURL(this, test, "https://saucelabs.com/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a dynamic check
        setVersion(this, test, "3.2.2");

        this.test = test;
    }

    @Test(groups = {"integration", "hub", "sauce"}, description = "An integration test to check that sauce gets all expected information")
    public void hubTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // verify the correct page title
        app.azzert().urlEquals("https://saucelabs.com/");
        // capture the session_id
        test.setAttribute("HUB_SESSION_ID", ((RemoteWebDriver) app.getDriver()).getSessionId());
        test.setAttribute("HUB_BUILD", app.getDesiredCapabilities().getCapability("build"));
        // verify no issues
        finish();
    }

    @DataProvider(name = "search terms", parallel = true)
    public Object[][] DataSetOptions2() {
        return new Object[][]{new Object[]{"selenified@coveros.com"}};
    }

    @Test(dataProvider = "search terms", groups = {"integration", "hub", "sauce"},
            description = "An integration test to check that sauce gets all expected information")
    public void hubSearchTest(String searchTerm) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // find the search box element and create the object
        Element searchBox = app.newElement(Locator.ID, "Email");
        //perform the search and submit
        searchBox.type(searchTerm);
        // capture the session_id
        test.setAttribute("PARAM_SESSION_ID", ((RemoteWebDriver) app.getDriver()).getSessionId());
        // verify no issues
        finish();
    }

    @BeforeMethod(alwaysRun = true)
    protected void noBrowser(Object[] dataProvider, Method method, ITestContext test, ITestResult result) throws IOException {
        if (Arrays.asList(result.getMethod().getGroups()).contains("sauceAPI")) {
            super.startTest(dataProvider, method, test, result, Browser.BrowserUse.FALSE);
        }
    }

    @Test(groups = {"integration", "hub", "sauce", "sauceAPI"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that sauce get all expected information")
    public void sauceSeleniumVersionTest(ITestContext iTestContext) throws IOException {
        SessionId sessionId = (SessionId) iTestContext.getAttribute("HUB_SESSION_ID");
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("rest/v1/" + hub.getUsername() + "/jobs/" + sessionId.toString());
        Map expectedResults = new HashMap<>();
        expectedResults.put("selenium_version", System.getProperty("selenium.version"));
        response.azzert().contains(expectedResults);
        finish();
    }

    @Test(groups = {"integration", "hub", "sauce", "sauceAPI"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that sauce get all expected information")
    public void saucePassedTest(ITestContext iTestContext) throws IOException {
        SessionId sessionId = (SessionId) iTestContext.getAttribute("HUB_SESSION_ID");
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("rest/v1/" + hub.getUsername() + "/jobs/" + sessionId.toString());
        Map expectedResults = new HashMap<>();
        expectedResults.put("passed", true);
        response.azzert().contains(expectedResults);
        finish();
    }

    @Test(groups = {"integration", "hub", "sauce", "sauceAPI"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that sauce get all expected information")
    public void sauceTagsTest(ITestContext iTestContext) throws IOException {
        SessionId sessionId = (SessionId) iTestContext.getAttribute("HUB_SESSION_ID");
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("rest/v1/" + hub.getUsername() + "/jobs/" + sessionId.toString());
        Map expectedResults = new HashMap<>();
        JsonArray tags = new JsonArray();
        tags.add("hub");
        tags.add("integration");
        tags.add("sauce");
        expectedResults.put("tags", tags);
        response.azzert().contains(expectedResults);
        finish();
    }

    @Test(groups = {"integration", "hub", "sauce", "sauceAPI"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that sauce get all expected information")
    public void sauceBuildTest(ITestContext iTestContext) throws IOException {
        SessionId sessionId = (SessionId) iTestContext.getAttribute("HUB_SESSION_ID");
        String buildName = iTestContext.getAttribute("HUB_BUILD").toString();
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("rest/v1/" + hub.getUsername() + "/jobs/" + sessionId.toString());
        Map expectedResults = new HashMap<>();
        expectedResults.put("build", buildName);
        response.azzert().contains(expectedResults);
        finish();
    }

    @Test(groups = {"integration", "hub", "sauce", "sauceAPI"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that sauce get all expected information")
    public void sauceNameTest(ITestContext iTestContext) throws IOException {
        SessionId sessionId = (SessionId) iTestContext.getAttribute("HUB_SESSION_ID");
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("rest/v1/" + hub.getUsername() + "/jobs/" + sessionId.toString());
        Map expectedResults = new HashMap<>();
        expectedResults.put("name", "integration.hub.SauceIT.hubTitleTest");
        response.azzert().contains(expectedResults);
        finish();
    }

    @Test(groups = {"integration", "hub", "sauce", "sauceAPI"}, dependsOnMethods = {"hubSearchTest"},
            description = "An integration test to check that sauce get all expected information")
    public void sauceComplexNameTest(ITestContext iTestContext) throws IOException {
        SessionId paramSessionId = (SessionId) iTestContext.getAttribute("PARAM_SESSION_ID");
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("rest/v1/" + hub.getUsername() + "/jobs/" + paramSessionId.toString());
        Map expectedResults = new HashMap<>();
        expectedResults.put("name", "integration.hub.SauceIT.hubSearchTestWithOptionSelenifiedcoveroscom");
        response.azzert().contains(expectedResults);
        finish();
    }
}
