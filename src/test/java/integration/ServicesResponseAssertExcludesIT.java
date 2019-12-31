package integration;

import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Response;
import com.google.gson.JsonObject;
import org.testng.annotations.Test;

import java.util.*;

public class ServicesResponseAssertExcludesIT extends ServicesBase {

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonArrayExcludes() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertExcludes().value(json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonObjectExcludesInteger() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertExcludes().keyValues(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonObjectExcludesString() {
        Map<String, Object> values = new HashMap<>();
        values.put("title", "eum et est occaecati");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertExcludes().keyValues(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonObjectExcludesMultiple() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        values.put("title", "eum et est occaecati");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertExcludes().keyValues(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonMessageExcludes() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertExcludes().message("We encountered an error");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonArrayDoesntExclude() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 1);
        json.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        json.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertExcludes().value(json);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonObjectDoesntExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertExcludes().keyValues(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsEmptyExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertExcludes().nestedKeyValues(new ArrayList<>(), values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsSingleExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("userId", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("users");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users/").assertExcludes().nestedKeyValues(crumbs, values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsDoubleExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("users");
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users/").assertExcludes().nestedKeyValues(crumbs, values);
        // verify no issuess
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsNoObjectExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertExcludes().nestedKeyValues(crumbs, values);
        // verify no issuess
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsBadCrumbExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("user");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertExcludes().nestedKeyValues(crumbs, values);
        // verify no issuess
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsMisMatchExclude() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertExcludes().nestedKeyValues(crumbs, values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsEmptyExcludes() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertExcludes().nestedValue(new ArrayList<>(), json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsSingleExcludes() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/").assertExcludes().nestedValue(crumbs, json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsSingleExcludesTrue() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/").assertExcludes().nestedValue(crumbs, simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsDoubleExcludes() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        crumbs.add("id");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/").assertExcludes().nestedValue(crumbs, json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsNoArrayExcludes() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("users");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users").assertExcludes().nestedValue(crumbs, json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsBadCrumbExcludes() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertExcludes().nestedValue(crumbs, json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsMisMatchExcludes() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids").assertExcludes().nestedValue(crumbs, simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonMessageDoesntExclude() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertExcludes().message("We found an error");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonArrayDoesntExcludeMismatch() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertExcludes().keyValues(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonObjectDoesntExcludeMismatch() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 1);
        json.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        json.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertExcludes().value(json);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonMessageDoesntExcludeMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("null/");
        response.assertExcludes().message("Something");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonMessageDoesntExcludeNull() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("/null/");
        response.assertExcludes().message("Something");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertKeysExcludesEmpty() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("/null/").assertExcludes().keys(Arrays.asList("id", "title"));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertKeysExcludesPass() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertExcludes().keys(Arrays.asList("id", "title"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertKeysExcludesFailOne() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertExcludes().keys(Arrays.asList("id", "space"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertKeysExcludesFail() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertExcludes().keys(Collections.singletonList("space"));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertNestedKeysNotObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("/null/").assertExcludes().nestedKeys(Collections.singletonList("something"), Arrays.asList("id", "title"));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertNestedKeysNoCrumb() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/1").assertExcludes().nestedKeys(new ArrayList<>(), Collections.singletonList("ids"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertNestedKeysCrumbMatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users/").assertExcludes().nestedKeys(Collections.singletonList("users"), Collections.singletonList("userId"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertNestedKeysCrumbNoMatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/1").assertExcludes().nestedKeys(Collections.singletonList("ids"), Arrays.asList("userId", "name"));
        // verify no issues
        finish();
    }
}