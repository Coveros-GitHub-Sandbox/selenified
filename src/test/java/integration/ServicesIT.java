package integration;

import com.coveros.selenified.DriverSetup;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Request;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Parameter;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class ServicesIT extends Selenified {

    private ClientAndServer mockServer;
    JsonObject json1 = new JsonObject();
    JsonObject json2 = new JsonObject();
    JsonObject json3 = new JsonObject();
    JsonObject json4 = new JsonObject();
    JsonObject simJson4 = new JsonObject();

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://localhost:1080/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "3.0.2");

        //test data
        json1.addProperty("userId", 1);
        json1.addProperty("id", 1);
        json1.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        json1.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        json2.addProperty("userId", 1);
        json2.addProperty("id", 2);
        json2.addProperty("title", "qui est esse");
        json2.addProperty("body",
                "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla");
        json3.addProperty("userId", 1);
        json3.addProperty("id", 3);
        json3.addProperty("title", "ea molestias quasi exercitationem repellat qui ipsa sit aut");
        json3.addProperty("body",
                "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut");
        json4.addProperty("userId", 1);
        json4.addProperty("id", 4);
        json4.addProperty("title", "eum et est occaecati");
        json4.addProperty("body",
                "ullam et saepe reiciendis voluptatem adipisci\nsit amet autem assumenda provident rerum culpa\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\nquis sunt voluptatem rerum illo velit");
        simJson4.addProperty("id", 4);
    }

    @BeforeMethod(alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        super.startTest(dataProvider, method, test, result, DriverSetup.FALSE);
    }

    @BeforeMethod
    public void startMockServer() {
        mockServer = startClientAndServer(1080);
        mockServer.when(request().withMethod("GET").withPath("/sample/")).respond(response().withBody("{}"));
        mockServer.when(request().withPath("/null/"))
                .respond(response().withStatusCode(404).withBody("We encountered an error, no page was found"));
        mockServer.when(request().withMethod("GET").withPath("/posts/")
                .withQueryStringParameter(new Parameter("id", "4"))).respond(response().withBody(
                "{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"}"));
        mockServer.when(request().withMethod("GET").withPath("/posts/4")).respond(response().withBody(
                "{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"}"));
        mockServer.when(request().withMethod("GET").withPath("/posts/")).respond(response().withBody(
                "[{\"userId\":1,\"id\":1,\"title\":\"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\"body\":\"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"},{\"userId\":1,\"id\":2,\"title\":\"qui est esse\",\"body\":\"est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla\"},{\"userId\":1,\"id\":3,\"title\":\"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\"body\":\"et iusto sed quo iure\\nvoluptatem occaecati omnis eligendi aut ad\\nvoluptatem doloribus vel accusantium quis pariatur\\nmolestiae porro eius odio et labore et velit aut\"},{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"}]"));
        mockServer.when(request().withMethod("POST").withPath("/posts/")).respond(response().withBody("{\"id\":4}"));
    }

    @AfterMethod
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test(groups = {"integration", "services", "headers"},
            description = "An integration test to verify we can successfully set header values")
    public void setHeaderTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom headers
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Atlassian-Token", "no-check");
        call.addHeaders(headers);
        // perform some actions
        call.get("sample/").assertEquals(200);
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
        call.addHeaders(headers);
        // perform some actions
        call.get("sample/").assertEquals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "headers"},
            description = "An integration test to verify we can successfully override standard header values")
    public void setCredentialsTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom credentials
        call.addCredentials("hello", "world");
        // perform some actions
        call.get("sample/").assertEquals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "headers"},
            description = "An integration negative test to verify we can successfully change header values")
    public void setUnsupportedHeaderTest() {
        JsonObject request = new JsonObject();
        request.addProperty("title", "foo");
        request.addProperty("body", "bar");
        request.addProperty("userId", 2);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/xml");
        call.addHeaders(headers);
        // perform some actions
        call.post("sample/", new Request(request)).assertEquals(201);
        // verify no issues
        finish(2);
    }

    // for get calls

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify a successful get call with no parameters")
    public void verifySuccessfulGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters")
    public void verifySuccessfulGetCallParams() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map params = new HashMap();
        params.put("id", 4);
        call.get("posts/", new Request(params)).assertEquals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters in url")
    public void verifySuccessfulGetCallUrlParams() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify a successful get call with no parameters")
    public void verifySuccessfulGetCallData() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals(json);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify a successful get call with no parameters")
    public void verifySuccessfulGetCallMessageData() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals(json.toString());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters")
    public void verifySuccessfulGetCallParamsData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map params = new HashMap();
        params.put("id", 4);
        call.get("posts/", new Request(params)).assertEquals(json4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters")
    public void verifySuccessfulGetCallParamsMessageData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map params = new HashMap();
        params.put("id", 4);
        call.get("posts/", new Request(params)).assertEquals(json4.toString());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters in url")
    public void verifySuccessfulGetCallUrlParamsData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals(json4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters in url")
    public void verifySuccessfulGetCallUrlParamsMessageData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals(json4.toString());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters in url")
    public void verifySuccessfulBadGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertEquals("We encountered an error, no page was found");
        // verify no issues
        finish();
    }

    // for post calls

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map params = new HashMap();
        params.put("id", 4);
        call.post("posts/", new Request(params)).assertEquals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map params = new HashMap();
        params.put("id", 4);
        call.post("posts/", new Request(params)).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map params = new HashMap();
        params.put("id", 4);
        call.post("posts/", new Request(params), new File("Jenkinsfile")).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters in url")
    public void verifySuccessfulBadPostCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("null/", new Request(new JsonObject())).assertEquals("We encountered an error, no page was found");
        // verify no issues
        finish();
    }

    // for patch calls
    // TODO - copy post (don't need file - research?)

    // for put calls
    // TODO - copy post (don't need file - research?)

    // for delete calls
    // TODO - copy delete
}