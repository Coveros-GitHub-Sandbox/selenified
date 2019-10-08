package integration;

import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicesResponseAssertIT extends ServicesBase {

    //negative checks for assert equals

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify response code negative responses", expectedExceptions = AssertionError.class)
    public void negativeSuccessfulGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").azzert().equals(201);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonArrayIsntObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").azzert().equals(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonObjectMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().equals(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonMessageIsntObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").azzert().equals(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonArrayMismatch() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").azzert().equals(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonObjectIsntArray() {
        JsonArray json = new JsonArray();
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().equals(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonMessageIsntArray() {
        JsonArray json = new JsonArray();
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").azzert().equals(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsEmpty() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().equals(new ArrayList<>(), json4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsSingle() {
        List crumbs = new ArrayList();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().equals(crumbs, 1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonCrumbsDouble() {
        List crumbs = new ArrayList();
        crumbs.add("userId");
        crumbs.add("user");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().equals(crumbs, 1);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonCrumbsNoObject() {
        List crumbs = new ArrayList();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").azzert().equals(crumbs, 1);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonCrumbsBadCrumb() {
        List crumbs = new ArrayList();
        crumbs.add("user");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().equals(crumbs, 1);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonCrumbsMisMatch() {
        List crumbs = new ArrayList();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().equals(crumbs, "hi");
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonArrayMessageMismatch() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").azzert().equals(json.toString());
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonObjectMessageMismatch() {
        JsonArray json = new JsonArray();
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().equals(json.toString());
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonMessageMessageMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("posts/?id=4");
        response.azzert().equals("Something");
        // verify 1 issue
        finish(1);
    }

    // checks for assert contains

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayContains() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").azzert().contains(json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectContainsInteger() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().contains(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectContainsString() {
        Map<String, Object> values = new HashMap<>();
        values.put("title", "eum et est occaecati");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().contains(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectContainsMultiple() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        values.put("title", "eum et est occaecati");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().contains(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageContains() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").azzert().contains("We encountered an error");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonArrayDoesntContain() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 1);
        json.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        json.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").azzert().contains(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonObjectDoesntContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().contains(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonMessageDoesntContain() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").azzert().contains("We found an error");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonArrayDoesntContainMismatch() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").azzert().contains(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonObjectDoesntContainMismatch() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 1);
        json.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        json.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").azzert().contains(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void verifyJsonMessageDoesntContainMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("null/");
        response.azzert().contains("Something");
        // verify 1 issue
        finish(1);
    }
}