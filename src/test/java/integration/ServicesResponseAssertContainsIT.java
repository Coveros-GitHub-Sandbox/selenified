package integration;

import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Response;
import com.google.gson.JsonObject;
import org.testng.annotations.Test;

import java.util.*;

public class ServicesResponseAssertContainsIT extends ServicesBase {

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonArrayContains() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertContains().value(json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonObjectContainsInteger() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains().keyValues(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonObjectContainsString() {
        Map<String, Object> values = new HashMap<>();
        values.put("title", "eum et est occaecati");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains().keyValues(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonObjectContainsMultiple() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        values.put("title", "eum et est occaecati");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains().keyValues(values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonMessageContains() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertContains().message("We encountered an error");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonArrayDoesntContain() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 1);
        json.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        json.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertContains().value(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonObjectDoesntContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains().keyValues(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsEmptyContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains().nestedKeyValues(new ArrayList<>(), values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsSingleContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("userId", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("users");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users/").assertContains().nestedKeyValues(crumbs, values);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsDoubleContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("users");
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users/").assertContains().nestedKeyValues(crumbs, values);
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsNoObjectContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertContains().nestedKeyValues(crumbs, values);
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsBadCrumbContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("user");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains().nestedKeyValues(crumbs, values);
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsMisMatchContain() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 4);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains().nestedKeyValues(crumbs, values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsEmptyContains() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertContains().nestedValue(new ArrayList<>(), json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertJsonCrumbsSingleContains() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/").assertContains().nestedValue(crumbs, json1);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsDoubleContains() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        crumbs.add("id");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/").assertContains().nestedValue(crumbs, json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsNoArrayContains() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("users");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users").assertContains().nestedValue(crumbs, json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsBadCrumbContains() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("userId");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains().nestedValue(crumbs, json1);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonCrumbsMisMatchContains() {
        List<String> crumbs = new ArrayList<>();
        crumbs.add("ids");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids").assertContains().nestedValue(crumbs, simJson4);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonMessageDoesntContain() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").assertContains().message("We found an error");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonArrayDoesntContainMismatch() {
        Map<String, Object> values = new HashMap<>();
        values.put("id", 3);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").assertContains().keyValues(values);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonObjectDoesntContainMismatch() {
        JsonObject json = new JsonObject();
        json.addProperty("id", 1);
        json.addProperty("title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        json.addProperty("body",
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto");
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains().value(json);
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonMessageDoesntContainMismatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("null/");
        response.assertContains().message("Something");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertJsonMessageDoesntContainNull() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Response response = call.get("/null/");
        response.assertContains().message("Something");
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertKeysContainsEmpty() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("/null/").assertContains().keys(Arrays.asList("id", "title"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertKeysContainsPass() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains().keys(Arrays.asList("id", "title"));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertKeysContainsFailOne() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains().keys(Arrays.asList("id", "space"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertKeysContainsFail() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").assertContains().keys(Collections.singletonList("space"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertNestedKeysNotObject() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("/null/").assertContains().nestedKeys(Collections.singletonList("something"), Arrays.asList("id", "title"));
        // verify 1 issue
        finish(1);
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertNestedKeysNoCrumb() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/1").assertContains().nestedKeys(new ArrayList<>(), Collections.singletonList("ids"));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response")
    public void assertNestedKeysCrumbMatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("users/").assertContains().nestedKeys(Collections.singletonList("users"), Collections.singletonList("userId"));
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json data response", expectedExceptions = AssertionError.class)
    public void assertNestedKeysCrumbNoMatch() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("ids/1").assertContains().nestedKeys(Collections.singletonList("ids"), Arrays.asList("userId", "name"));
        // verify 1 issue
        finish(1);
    }
}