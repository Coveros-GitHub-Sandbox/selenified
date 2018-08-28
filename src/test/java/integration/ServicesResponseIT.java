package integration;

import com.coveros.selenified.DriverSetup;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Request;
import com.coveros.selenified.services.Response;
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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ServicesResponseIT extends Selenified {

    private ClientAndServer mockServer;
    JsonObject json1 = new JsonObject();
    JsonObject json2 = new JsonObject();
    JsonObject json3 = new JsonObject();
    JsonObject json4 = new JsonObject();

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
    }

    @BeforeMethod(alwaysRun = true)
    protected void startTest(Object[] dataProvider, Method method, ITestContext test, ITestResult result) {
        super.startTest(dataProvider, method, test, result, DriverSetup.FALSE);
    }

    @BeforeMethod
    public void startMockServer() {
        mockServer = startClientAndServer(1080);
        mockServer.when(request().withMethod("GET").withPath("/null/"))
                .respond(response().withStatusCode(404).withBody("We encountered an error, no page was found"));
        mockServer.when(request().withMethod("GET").withPath("/posts/")
                .withQueryStringParameter(new Parameter("id", "4"))).respond(response().withBody(
                "{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"}"));
        mockServer.when(request().withMethod("GET").withPath("/posts/4")).respond(response().withBody(
                "{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"}"));
        mockServer.when(request().withMethod("GET").withPath("/posts/")).respond(response().withBody(
                "[{\"userId\":1,\"id\":1,\"title\":\"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\"body\":\"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"},{\"userId\":1,\"id\":2,\"title\":\"qui est esse\",\"body\":\"est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla\"},{\"userId\":1,\"id\":3,\"title\":\"ea molestias quasi exercitationem repellat qui ipsa sit aut\",\"body\":\"et iusto sed quo iure\\nvoluptatem occaecati omnis eligendi aut ad\\nvoluptatem doloribus vel accusantium quis pariatur\\nmolestiae porro eius odio et labore et velit aut\"},{\"userId\":1,\"id\":4,\"title\":\"eum et est occaecati\",\"body\":\"ullam et saepe reiciendis voluptatem adipisci\\nsit amet autem assumenda provident rerum culpa\\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\\nquis sunt voluptatem rerum illo velit\"}]"));
    }

    @AfterMethod
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json array response for data check")
    public void verifyJsonArrayDataCheckGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        assertTrue(call.get("posts/").isData());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json object response for data check")
    public void verifyJsonObjectDataCheckGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map params = new HashMap();
        params.put("id", 4);
        assertTrue(call.get("posts/", new Request(params)).isData());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify message response for data check")
    public void verifyMessageDataCheckGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        assertFalse(call.get("null/").isData());
        // verify no issues
        finish();
    }

    //negative checks for assert equals

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify response code negative responses")
    public void negativeSuccessfulGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals(201);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayIsntObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals(json1);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals(json1);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageIsntObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertEquals(json1);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayMismatch() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals(json);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectIsntArray() {
        JsonArray json = new JsonArray();
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals(json);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageIsntArray() {
        JsonArray json = new JsonArray();
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertEquals(json);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayMessageMismatch() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals(json.toString());
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectMessageMismatch() {
        JsonArray json = new JsonArray();
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals(json.toString());
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageMessageMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("posts/?id=4");
        response.setMessage(null);
        response.assertEquals("Something");
        // verify no issues
        finish(1);
    }

    // checks for assert contains

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayContains() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertContains(json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectContainsInteger() {
        Map values = new HashMap();
        values.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectContainsString() {
        Map values = new HashMap();
        values.put("title", "eum et est occaecati");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectContainsMultiple() {
        Map values = new HashMap();
        values.put("id", 4);
        values.put("title", "eum et est occaecati");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageContains() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertContains("We encountered an error");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayDoesntContain() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 1);
        json.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        json.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertContains(json);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectDoesntContain() {
        Map values = new HashMap();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains(values);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageDoesntContain() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertContains("We found an error");
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayDoesntContainMismatch() {
        Map values = new HashMap();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertContains(values);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectDoesntContainMismatch() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 1);
        json.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        json.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains(json);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageDoesntContainMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("null/");
        response.setMessage(null);
        response.assertContains("Something");
        // verify no issues
        finish(1);
    }
}