package integration;

import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Response;
import com.google.gson.JsonArray;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServicesResponseAssertEqualsIT extends ServicesBase {

    //negative checks for assert equals

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify response code negative responses", expectedExceptions = AssertionError.class)
    public void negativeSuccessfulGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals().code(201);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify response code negative responses")
    public void successfulGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals().code(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonObjectCorrect() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals().objectData(json4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonArrayIsntObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals().objectData(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonObjectMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals().objectData(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonMessageIsntObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertEquals().objectData(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonArray() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals().arrayData(json);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonArrayMismatch() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals().arrayData(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonObjectIsntArray() {
        JsonArray json = new JsonArray();
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals().arrayData(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonMessageIsntArray() {
        JsonArray json = new JsonArray();
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertEquals().arrayData(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsEmpty() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals().nestedValue(new ArrayList<>(), json4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsSingle() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals().nestedValue(crumbs, 1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsDouble() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        crumbs.add("user");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals().nestedValue(crumbs, 1);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsNoObject() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals().nestedValue(crumbs, 1);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsBadCrumb() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("user");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals().nestedValue(crumbs, 1);
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsMisMatch() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals().nestedValue(crumbs, "hi");
        // verify no issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonArrayMessage() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals().message(json.toString());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonArrayMessageMismatch() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertEquals().message(json.toString());
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonObjectMessageMismatch() {
        JsonArray json = new JsonArray();
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertEquals().message(json.toString());
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonMessageMessageMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("posts/?id=4");
        response.assertEquals().message("Something");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonMessageMessageNull() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("/null/");
        response.assertEquals().message("Something");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertArraySizeNull() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("/null/");
        response.assertEquals().arraySize(-1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertArraySizeEmpty() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("empty/");
        response.assertEquals().arraySize(0);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertArraySizeGood() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("posts/");
        response.assertEquals().arraySize(4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertArraySizeBad() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("posts/");
        response.assertEquals().arraySize(3);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertNestArraySizeNotArray() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("/null/");
        response.assertEquals().nestedArraySize(Collections.singletonList("name"), -1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertNestArraySizeNotExist() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("posts/4");
        response.assertEquals().nestedArraySize(Collections.singletonList("name"), -1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertNestArraySizeEmtpy() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("ids/5");
        response.assertEquals().nestedArraySize(Collections.singletonList("ids"), 0);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertNestArraySizeGood() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("ids/");
        response.assertEquals().nestedArraySize(Collections.singletonList("ids"), 4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertNestArraySizeBad() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("ids/");
        response.assertEquals().nestedArraySize(Collections.singletonList("ids"), 3);
        // verify 1 issue
        finish(1);
    }
}