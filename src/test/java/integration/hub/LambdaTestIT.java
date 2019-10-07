package integration.hub;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.application.App;
import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Response;
import com.coveros.selenified.utilities.Hub;
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

public class LambdaTestIT extends Selenified {

    private ITestContext test;

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setAppURL(this, test, "https://api.lambdatest.com/automation/api/v1/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a dynamic check
        setVersion(this, test, "3.2.2");

        this.test = test;
    }

    @Test(groups = {"integration", "hub", "lambda"}, description = "An integration test to check that lambda gets all expected information")
    public void hubTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // verify the correct page title
        app.azzert().urlEquals("https://api.lambdatest.com/automation/api/v1/");
        // capture the session_id
        test.setAttribute("HUB_SESSION_ID", ((RemoteWebDriver) app.getDriver()).getSessionId());
        test.setAttribute("HUB_BUILD", app.getDesiredCapabilities().getCapability("build"));
        // verify no issues
        finish();
    }

    @DataProvider(name = "search terms", parallel = true)
    public Object[][] DataSetOptions2() {
        return new Object[][]{new Object[]{"hi"}};
    }

    @Test(dataProvider = "search terms", groups = {"integration", "hub", "lambda"},
            description = "An integration test to check that lambda gets all expected information")
    public void hubSearchTest(String message) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // find the search box element and create the object
        app.azzert().textPresent(message);
        // capture the session_id
        test.setAttribute("PARAM_SESSION_ID", ((RemoteWebDriver) app.getDriver()).getSessionId());
        // verify no issues
        finish();
    }

    @BeforeMethod(alwaysRun = true)
    protected void noBrowser(Object[] dataProvider, Method method, ITestContext test, ITestResult result) throws IOException {
        if (Arrays.asList(result.getMethod().getGroups()).contains("lambdaAPI")) {
            super.startTest(dataProvider, method, test, result, Browser.BrowserUse.FALSE);
        }
    }

    @Test(groups = {"integration", "hub", "lambda", "lambdaAPI"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that lambda get all expected information")
    public void lambdaPassedTest(ITestContext iTestContext) throws IOException {
        SessionId sessionId = (SessionId) iTestContext.getAttribute("HUB_SESSION_ID");
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("sessions/" + sessionId.toString());
        Map expectedResults = new HashMap<>();
        expectedResults.put("status_ind", "passed");
//        response.azzert().contains(expectedResults);
        //TODO - commenting out assertion as feature doesn't yet exist. Issue 102 created to address it: https://github.com/Coveros/selenified/issues/102
        finish();
    }

    @Test(groups = {"integration", "hub", "lambda", "lambdaAPI"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that lambda get all expected information")
    public void lambdaBuildTest(ITestContext iTestContext) throws IOException {
        SessionId sessionId = (SessionId) iTestContext.getAttribute("HUB_SESSION_ID");
        String buildName = iTestContext.getAttribute("HUB_BUILD").toString();
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("sessions/" + sessionId.toString());
        Map expectedResults = new HashMap<>();
        expectedResults.put("build_name", buildName);
//        response.azzert().contains(expectedResults);
        //TODO - commenting out assertion as feature doesn't yet exist. Issue 102 created to address it: https://github.com/Coveros/selenified/issues/102
        finish();
    }

    @Test(groups = {"integration", "hub", "lambda", "lambdaAPI"}, dependsOnMethods = {"hubTitleTest"},
            description = "An integration test to check that lambda get all expected information")
    public void lambdaNameTest(ITestContext iTestContext) throws IOException {
        SessionId sessionId = (SessionId) iTestContext.getAttribute("HUB_SESSION_ID");
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("sessions/" + sessionId.toString());
        Map expectedResults = new HashMap<>();
        expectedResults.put("name", "integration.hub.lambdaIT.hubTitleTest");
//        response.azzert().contains(expectedResults);
        //TODO - commenting out assertion as feature doesn't yet exist. Issue 102 created to address it: https://github.com/Coveros/selenified/issues/102
        finish();
    }

    @Test(groups = {"integration", "hub", "lambda", "lambdaAPI"}, dependsOnMethods = {"hubSearchTest"},
            description = "An integration test to check that lambda get all expected information")
    public void lambdaComplexNameTest(ITestContext iTestContext) throws IOException {
        SessionId paramSessionId = (SessionId) iTestContext.getAttribute("PARAM_SESSION_ID");
        Hub hub = new Hub();
        Call call = this.calls.get();
        call.addCredentials(hub.getUsername(), hub.getPassword());
        Response response = call.get("sessions/" + paramSessionId.toString());
        Map expectedResults = new HashMap<>();
        expectedResults.put("name", "integration.hub.lambdaIT.hubSearchTestWithOptionSelenifiedcoveroscom");
//        response.azzert().contains(expectedResults);
        //TODO - commenting out assertion as feature doesn't yet exist. Issue 102 created to address it: https://github.com/Coveros/selenified/issues/102
        finish();
    }
}
