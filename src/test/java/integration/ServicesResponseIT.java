package integration;

import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Request;
import com.coveros.selenified.services.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ServicesResponseIT extends ServicesBase {

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
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        assertTrue(call.get("posts/", new Request().setUrlParams(params)).isData());
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
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayIsntObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageIsntObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertEquals(json1);
        // verify 1 issue
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
        // verify 1 issue
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
        // verify 1 issue
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
        // verify 1 issue
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
        // verify 1 issue
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
        // verify 1 issue
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
        // verify 1 issue
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
        Map<String, Object> values = new HashMap<>();
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
        Map<String, Object> values = new HashMap<>();
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
        Map<String, Object> values = new HashMap<>();
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
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectDoesntContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageDoesntContain() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertContains("We found an error");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "services", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayDoesntContainMismatch() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertContains(values);
        // verify 1 issue
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
        // verify 1 issue
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
        // verify 1 issue
        finish(1);
    }
}