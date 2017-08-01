package integration;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coveros.selenified.output.Assert;
import com.coveros.selenified.selenium.Selenium.DriverSetup;
import com.coveros.selenified.services.Request;
import com.coveros.selenified.tools.TestBase;
import com.google.gson.JsonObject;

public class ServicesIT extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        // set the base URL for the tests here
        setTestSite("https://jsonplaceholder.typicode.com/");
        // set the author of the tests here
        setAuthor("Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion("0.0.1");
    }

    @BeforeMethod(alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        super.startTest(dataProvider, method, test, result, DriverSetup.FALSE);
    }

    @Test(groups = { "integration", "services", "get",
            "virtual" }, description = "An integration test to verify the response code from a get call")
    public void compareGetResponseCode200Test() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareGetResponseCode("posts/", 200);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "services", "get",
            "virtual" }, description = "A negative integration test to verify the response code from a get call")
    public void compareGetResponseBadCode200Test() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareGetResponseCode("posts/", 201);
        // verify one issue
        finish(1);
    }

    @Test(groups = { "integration", "services", "get",
            "virtual" }, description = "An integration test to verify the response code from a get call")
    public void compareGetResponseCode404Test() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareGetResponseCode("post/", 404);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "services", "get",
            "virtual" }, description = "A negative integration test to verify the response code from a get call")
    public void compareGetResponseBadCode404Test() {
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareGetResponseCode("post/", 400);
        // verify one issue
        finish(1);
    }

    @Test(groups = { "integration", "services", "get",
            "virtual" }, description = "An integration test to verify the data from a get call")
    public void compareGetResponseDataTest() {
        JsonObject response = new JsonObject();
        response.addProperty("userId", 1);
        response.addProperty("id", 1);
        response.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        response.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareGetResponseData("posts/1", response);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "services", "get",
            "virtual" }, description = "A negative integration test to verify the data from a get call")
    public void compareGetResponseBadDataTest() {
        JsonObject response = new JsonObject();
        response.addProperty("userId", 1);
        response.addProperty("id", 1);
        response.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.compareGetResponseData("posts/1", response);
        // verify one issue
        finish(1);
    }

    @Test(groups = { "integration", "services", "post",
            "virtual" }, description = "An integration test to verify the response code from a post call")
    public void comparePostResponseCode200Test() {
        JsonObject request = new JsonObject();
        request.addProperty("title", "foo");
        request.addProperty("body", "bar");
        request.addProperty("userId", 1);
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.comparePostResponseCode("posts/", new Request(request), 201);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "services", "post",
            "virtual" }, description = "A negative integration test to verify the response code from a post call")
    public void comparePostResponseBadCode200Test() {
        JsonObject request = new JsonObject();
        request.addProperty("title", "foo");
        request.addProperty("body", "bar");
        request.addProperty("userId", 1);
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.comparePostResponseCode("posts/", new Request(request), 200);
        // verify one issue
        finish(1);
    }

    @Test(groups = { "integration", "services", "post",
            "virtual" }, description = "An integration test to verify the response code from a post call")
    public void comparePostResponseCode404Test() {
        JsonObject request = new JsonObject();
        request.addProperty("title", "foo");
        request.addProperty("body", "bar");
        request.addProperty("userId", 1);
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.comparePostResponseCode("post/", new Request(request), 404);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "services", "post",
            "virtual" }, description = "A negative integration test to verify the response code from a post call")
    public void comparePostResponseBadCode404Test() {
        JsonObject request = new JsonObject();
        request.addProperty("title", "foo");
        request.addProperty("body", "bar");
        request.addProperty("userId", 1);
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.comparePostResponseCode("post/", new Request(request), 400);
        // verify one issue
        finish(1);
    }

    @Test(groups = { "integration", "services", "post",
            "virtual" }, description = "An integration test to verify the data from a post call")
    public void comparePostResponseDataTest() {
        JsonObject request = new JsonObject();
        request.addProperty("title", "foo");
        request.addProperty("body", "bar");
        request.addProperty("userId", 1);
        JsonObject response = new JsonObject();
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.comparePostResponseData("posts/1", new Request(request), response);
        // verify no issues
        finish();
    }

    @Test(groups = { "integration", "services", "post",
            "virtual" }, description = "A negative integration test to verify the data from a post call")
    public void comparePostResponseBadDataTest() {
        JsonObject request = new JsonObject();
        request.addProperty("title", "foo");
        request.addProperty("body", "bar");
        request.addProperty("userId", 1);
        JsonObject response = new JsonObject();
        response.addProperty("userId", 1);
        // use this object to verify the page looks as expected
        Assert asserts = this.asserts.get();
        // perform some actions
        asserts.comparePostResponseData("posts/1", new Request(request), response);
        // verify one issue
        finish(1);
    }
}