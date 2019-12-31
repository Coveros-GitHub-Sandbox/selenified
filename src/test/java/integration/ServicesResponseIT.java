package integration;

import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.Request;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ServicesResponseIT extends ServicesBase {

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify json array response for data check")
    public void verifyJsonArrayDataCheckGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        assertTrue(call.get("posts/").isData());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
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

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify message response for data check")
    public void verifyMessageDataCheckGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        assertFalse(call.get("null/").isData());
        // verify no issues
        finish();
    }
}