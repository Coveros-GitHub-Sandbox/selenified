package integration;

import com.coveros.selenified.DriverSetup;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Request;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class ServicesErrorIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "https://bad.url.com/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "3.0.2");
    }

    @BeforeMethod(alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        super.startTest(dataProvider, method, test, result, DriverSetup.FALSE);
    }

    @Test(groups = {"integration", "services", "httpget"},
            description = "An integration test to verify the response code from a get call")
    public void compareGetResponseCode200Test() {
        // use this object to make calls
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/4").assertEquals(200);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "services", "httppost"},
            description = "An integration test to verify the response code from a post call")
    public void comparePostResponseCode201Test() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("posts/", new Request()).assertEquals(201);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpput"},
            description = "An integration test to verify the response code from a put call")
    public void comparePutResponseCode200Test() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("posts/3", new Request()).assertEquals(200);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "services", "httppatch"},
            description = "An integration test to verify the response code from a patch call")
    public void comparePatchResponseCode200Test() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.patch("posts/4", new Request()).assertEquals(200);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpdelete"},
            description = "An integration test to verify the response code from a delete call")
    public void compareDeleteResponseCode200Test() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("posts/5", new Request()).assertEquals(200);
        // verify 1 issue
        finish(1);
    }
}