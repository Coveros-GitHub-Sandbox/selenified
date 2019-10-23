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

public class ServicesResponseVerifyIT extends ServicesBase {

    //negative checks for assert equals

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify response code negative responses")
    public void negativeSuccessfulGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyEquals().code(201);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayIsntObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyEquals().objectData(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyEquals().objectData(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageIsntObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").verifyEquals().objectData(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayMismatch() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyEquals().arrayData(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectIsntArray() {
        JsonArray json = new JsonArray();
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyEquals().arrayData(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageIsntArray() {
        JsonArray json = new JsonArray();
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").verifyEquals().arrayData(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsEmpty() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyEquals().nestedValue(new ArrayList<>(), json4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsSingle() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyEquals().nestedValue(crumbs, 1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsDouble() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        crumbs.add("user");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyEquals().nestedValue(crumbs, 1);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsNoObject() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyEquals().nestedValue(crumbs, 1);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsBadCrumb() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("user");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyEquals().nestedValue(crumbs, 1);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsMisMatch() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyEquals().nestedValue(crumbs, "hi");
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayMessageMismatch() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyEquals().message(json.toString());
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectMessageMismatch() {
        JsonArray json = new JsonArray();
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyEquals().message(json.toString());
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageMessageMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("posts/?id=4");
        response.verifyEquals().message("Something");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageMessageNull() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("/null/");
        response.verifyEquals().message("Something");
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
        call.get("posts/").verifyContains().value(json1);
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
        call.get("posts/?id=4").verifyContains().keyValues(values);
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
        call.get("posts/?id=4").verifyContains().keyValues(values);
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
        call.get("posts/?id=4").verifyContains().keyValues(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageContains() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").verifyContains().message("We encountered an error");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
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
        call.get("posts/").verifyContains().value(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectDoesntContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyContains().keyValues(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsEmptyContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyContains().nestedKeyValues(new ArrayList<>(), values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsSingleContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("userId", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("users");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users/").verifyContains().nestedKeyValues(crumbs, values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsDoubleContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("users");
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users/").verifyContains().nestedKeyValues(crumbs, values);
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsNoObjectContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyContains().nestedKeyValues(crumbs, values);
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsBadCrumbContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("user");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyContains().nestedKeyValues(crumbs, values);
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsMisMatchContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyContains().nestedKeyValues(crumbs, values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsEmptyContains() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyContains().nestedValue(new ArrayList<>(), json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsSingleContains() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/").verifyContains().nestedValue(crumbs, json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsDoubleContains() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        crumbs.add("id");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/").verifyContains().nestedValue(crumbs, json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsNoArrayContains() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("users");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users").verifyContains().nestedValue(crumbs, json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsBadCrumbContains() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyContains().nestedValue(crumbs, json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsMisMatchContains() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids").verifyContains().nestedValue(crumbs, simJson4);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageDoesntContain() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").verifyContains().message("We found an error");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayDoesntContainMismatch() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyContains().keyValues(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
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
        call.get("posts/?id=4").verifyContains().value(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageDoesntContainMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("null/");
        response.verifyContains().message("Something");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageDoesntContainNull() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("/null/");
        response.verifyContains().message("Something");
        // verify 1 issue
        finish(1);
    }
}