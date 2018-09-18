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
import org.testng.annotations.AfterClass;
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
    private final JsonObject json1 = new JsonObject();
    private final JsonObject json2 = new JsonObject();
    private final JsonObject json3 = new JsonObject();
    private final JsonObject json4 = new JsonObject();
    private final JsonObject simJson4 = new JsonObject();

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

    @BeforeClass(alwaysRun = true)
    public void startMockServer() {
        mockServer = startClientAndServer(1080);
        System.out.println("starting server");
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
        mockServer.when(request().withMethod("PUT").withPath("/post/4")).respond(response().withStatusCode(200));
        mockServer.when(request().withPath("/post/4")).respond(response().withBody("{\"id\":4}"));
    }

    @AfterClass(alwaysRun = true)
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
        // set some custom headers
        // as application/xml is not currently supported, the initial post call will also fail
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/xml");
        call.addHeaders(headers);
        // perform some actions
        call.post("posts/", new Request().setJsonPayload(request)).assertEquals(201);
        // verify 2 issues
        finish(2);
    }

    @Test(groups = {"integration", "services", "headers"},
            description = "An integration negative test to verify we can successfully change header values")
    public void setPostMissingFileTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request(), new File("Jenkins")).assertEquals(200);
        // verify no issues
        finish();
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
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        call.get("posts/", new Request().setUrlParams(params)).assertEquals(200);
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
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        call.get("posts/", new Request().setUrlParams(params)).assertEquals(json4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters")
    public void verifySuccessfulGetCallParamsMessageData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        call.get("posts/", new Request().setUrlParams(params)).assertEquals(json4.toString());
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
        call.post("post/4", new Request()).assertEquals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostParamsCallNoData() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setUrlParams(params)).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallNoData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request()).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallNullData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(null)).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonObjectData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(new JsonObject())).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonArrayData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(new JsonArray())).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallEmptyMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setMultipartData(new HashMap<>())).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        call.post("post/4", new Request().setMultipartData(map)).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonObjectMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(new JsonObject()).setMultipartData(new HashMap<>()))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonArrayMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(new JsonArray()).setMultipartData(new HashMap<>()))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.post("post/4", new Request().setMultipartData(new HashMap<>())).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonObjectMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.post("post/4", new Request().setJsonPayload(new JsonObject()).setMultipartData(new HashMap<>()))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonArrayMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.post("post/4", new Request().setJsonPayload(new JsonArray()).setMultipartData(new HashMap<>()))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallUrlParamsData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setUrlParams(new HashMap<>())).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallNoDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request(), new File("Jenkinsfile")).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonObjectDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(new JsonObject()), new File("Jenkinsfile"))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonArrayDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(new JsonArray()), new File("Jenkinsfile"))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallMultipartDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setMultipartData(new HashMap<>()), new File("Jenkinsfile"))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallUrlParamsDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setUrlParams(new HashMap<>()), new File("Jenkinsfile"))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters in url")
    public void verifySuccessfulBadPostCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("null/", new Request()).assertEquals("We encountered an error, no page was found");
        // verify no issues
        finish();
    }

    // for put calls

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request()).assertEquals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutParamCallNoData() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setUrlParams(params)).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallNoData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request()).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonObjectData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setJsonPayload(new JsonObject())).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonArrayData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setJsonPayload(new JsonArray())).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallEmptyMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setMultipartData(new HashMap<>())).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        call.put("post/4", new Request().setMultipartData(map)).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonObjectMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setJsonPayload(new JsonObject()).setMultipartData(new HashMap<>()))
                .assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonArrayMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setJsonPayload(new JsonArray()).setMultipartData(new HashMap<>()))
                .assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.put("post/4", new Request().setMultipartData(new HashMap<>())).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonObjectMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.put("post/4", new Request().setJsonPayload(new JsonObject()).setMultipartData(new HashMap<>()))
                .assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonArrayMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.put("post/4", new Request().setJsonPayload(new JsonArray()).setMultipartData(new HashMap<>()))
                .assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallUrlParamsData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setUrlParams(new HashMap<>())).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallNoDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request(), new File("Jenkinsfile")).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonObjectDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setJsonPayload(new JsonObject()), new File("Jenkinsfile")).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonArrayDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setJsonPayload(new JsonArray()), new File("Jenkinsfile")).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallMultipartDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setMultipartData(new HashMap<>()), new File("Jenkinsfile")).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallUrlParamsDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setUrlParams(new HashMap<>()), new File("Jenkinsfile")).assertEquals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters in url")
    public void verifySuccessfulBadPutCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("null/", new Request()).assertEquals("We encountered an error, no page was found");
        // verify no issues
        finish();
    }

    // for delete calls

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request()).assertEquals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallNoData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4").assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteParamsCallNoData() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setUrlParams(params)).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallEmptyData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request()).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonObjectData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setJsonPayload(new JsonObject())).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonArrayData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setJsonPayload(new JsonArray())).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallEmptyMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setMultipartData(new HashMap<>())).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        call.delete("post/4", new Request().setMultipartData(map)).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonObjectMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setJsonPayload(new JsonObject()).setMultipartData(new HashMap<>()))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonArrayMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setJsonPayload(new JsonArray()).setMultipartData(new HashMap<>()))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.delete("post/4", new Request().setMultipartData(new HashMap<>())).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonObjectMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.delete("post/4", new Request().setJsonPayload(new JsonObject()).setMultipartData(new HashMap<>()))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonArrayMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.delete("post/4", new Request().setJsonPayload(new JsonArray()).setMultipartData(new HashMap<>()))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallUrlParamsData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setUrlParams(new HashMap<>())).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallNoDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request(), new File("Jenkinsfile")).assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonObjectDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setJsonPayload(new JsonObject()), new File("Jenkinsfile"))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonArrayDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setJsonPayload(new JsonArray()), new File("Jenkinsfile"))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallMultipartDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setMultipartData(new HashMap<>()), new File("Jenkinsfile"))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallUrlParamsDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setUrlParams(new HashMap<>()), new File("Jenkinsfile"))
                .assertEquals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters in url")
    public void verifySuccessfulBadDeleteCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("null/", new Request()).assertEquals("We encountered an error, no page was found");
        // verify no issues
        finish();
    }
}