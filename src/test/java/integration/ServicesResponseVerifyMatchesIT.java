package integration;

import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Response;
import com.google.gson.JsonArray;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ServicesResponseVerifyMatchesIT extends ServicesBase {

    //negative checks for verify equals

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify response code negative responses")
    public void negativeSuccessfulGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyMatches().code("301");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify response code negative responses")
    public void successfulGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyMatches().code("([02]+)");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsEmpty() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyMatches().nestedValue(new ArrayList<>(), "(.*)");
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
        call.get("posts/?id=4").verifyMatches().nestedValue(crumbs, "[\\d]");
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
        call.get("posts/?id=4").verifyMatches().nestedValue(crumbs, "[\\d]");
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
        call.get("posts/").verifyMatches().nestedValue(crumbs, "[\\d]");
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
        call.get("posts/?id=4").verifyMatches().nestedValue(crumbs, "[\\d]");
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
        call.get("posts/?id=4").verifyMatches().nestedValue(crumbs, "hi");
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
        call.get("posts/").verifyMatches().message(json.toString());
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
        call.get("posts/?id=4").verifyMatches().message(json.toString());
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
        response.verifyMatches().message("Something");
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
        response.verifyMatches().message("Something");
        // verify 1 issue
        finish(1);
    }
}