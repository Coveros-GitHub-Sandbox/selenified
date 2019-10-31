package integration;

import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Response;
import com.google.gson.JsonObject;
import org.testng.annotations.Test;

import java.util.*;

public class ServicesResponseVerifyExcludesIT extends ServicesBase {

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayExcludes() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyExcludes().value(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectExcludesInteger() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyExcludes().keyValues(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectExcludesString() {
        Map<String, Object> values = new HashMap<>();
        values.put("title", "eum et est occaecati");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyExcludes().keyValues(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectExcludesMultiple() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        values.put("title", "eum et est occaecati");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyExcludes().keyValues(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageExcludes() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").verifyExcludes().message("We encountered an error");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayDoesntExclude() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 1);
        json.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        json.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyExcludes().value(json);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectDoesntExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyExcludes().keyValues(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsEmptyExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyExcludes().nestedKeyValues(new ArrayList<>(), values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsSingleExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("userId", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("users");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users/").verifyExcludes().nestedKeyValues(crumbs, values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsDoubleExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("users");
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users/").verifyExcludes().nestedKeyValues(crumbs, values);
        // verify no issuess
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsNoObjectExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyExcludes().nestedKeyValues(crumbs, values);
        // verify no issuess
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsBadCrumbExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("user");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyExcludes().nestedKeyValues(crumbs, values);
        // verify no issuess
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsMisMatchExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyExcludes().nestedKeyValues(crumbs, values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsEmptyExcludes() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyExcludes().nestedValue(new ArrayList<>(), json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsSingleExcludes() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/").verifyExcludes().nestedValue(crumbs, json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsSingleExcludesTrue() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/").verifyExcludes().nestedValue(crumbs, simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsDoubleExcludes() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        crumbs.add("id");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/").verifyExcludes().nestedValue(crumbs, json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsNoArrayExcludes() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("users");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users").verifyExcludes().nestedValue(crumbs, json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsBadCrumbExcludes() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyExcludes().nestedValue(crumbs, json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonCrumbsMisMatchExcludes() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids").verifyExcludes().nestedValue(crumbs, simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageDoesntExclude() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").verifyExcludes().message("We found an error");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonArrayDoesntExcludeMismatch() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verifyExcludes().keyValues(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonObjectDoesntExcludeMismatch() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 1);
        json.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        json.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyExcludes().value(json);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageDoesntExcludeMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("null/");
        response.verifyExcludes().message("Something");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyJsonMessageDoesntExcludeNull() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("/null/");
        response.verifyExcludes().message("Something");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyKeysExcludesEmpty() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("/null/").verifyExcludes().keys(Arrays.asList("id", "title"));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyKeysExcludesPass() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyExcludes().keys(Arrays.asList("id", "title"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyKeysExcludesFailOne() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyExcludes().keys(Arrays.asList("id", "space"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyKeysExcludesFail() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verifyExcludes().keys(Collections.singletonList("space"));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyNestedKeysNotObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("/null/").verifyExcludes().nestedKeys(Collections.singletonList("something"), Arrays.asList("id", "title"));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyNestedKeysNoCrumb() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/1").verifyExcludes().nestedKeys(new ArrayList<>(), Collections.singletonList("ids"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyNestedKeysCrumbMatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users/").verifyExcludes().nestedKeys(Collections.singletonList("users"), Collections.singletonList("userId"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void verifyNestedKeysCrumbNoMatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/1").verifyExcludes().nestedKeys(Collections.singletonList("ids"), Arrays.asList("userId", "name"));
        // verify no issues
        finish();
    }
}