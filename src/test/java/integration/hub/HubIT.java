package integration.hub;

import com.coveros.selenified.Locator;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;
import com.coveros.selenified.utilities.Sauce;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.saucelabs.saucerest.SauceREST;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class HubIT extends Selenified {

    ITestContext test;

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setAppURL(this, test, "https://www.coveros.com/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a dynamic check
        setVersion(this, test, "3.2.1");

        this.test = test;
    }

    @DataProvider(name = "coveros search terms", parallel = true)
    public Object[][] DataSetOptions() {
        return new Object[][]{new Object[]{"selenified"}};
    }

    @Test(groups = {"integration", "hub", "sauce"}, description = "An integration test to check that sauce gets all expected information")
    public void hubTitleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // verify the correct page title
        app.azzert().titleEquals("Coveros | Bringing together agile and security to deliver superior software");
        // capture the session_id
        SessionId sessionId = ((RemoteWebDriver) app.getDriver()).getSessionId();
        test.setAttribute("HUB_SESSION_ID", sessionId);
        test.setAttribute("HUB_BUILD", app.getDesiredCapabilities().getCapability("build"));
        // verify no issues
        finish();
    }

    @Test(dataProvider = "coveros search terms", groups = {"integration", "hub", "sauce"},
            description = "An integration test to check that sauce gets all expected information")
    public void hubSearchTest(String searchTerm) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // find the search box element and create the object
        Element searchBox = app.newElement(Locator.NAME, "s");
        //perform the search and submit
        searchBox.type(searchTerm);
        // capture the session_id
        SessionId sessionId = ((RemoteWebDriver) app.getDriver()).getSessionId();
        test.setAttribute("PARAM_SESSION_ID", sessionId);
        // verify no issues
        finish();
    }
}
