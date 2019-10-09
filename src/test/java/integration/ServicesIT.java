package integration;

import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.services.Request;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ServicesIT extends ServicesBase {

    @Test(groups = {"integration", "service", "headers"},
            description = "An integration test to verify we can successfully set header values")
    public void setHeaderTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom headers
        Map<String, Object> headers = new HashMap<>();
        headers.put("X-Atlassian-Token", "no-check");
        call.addHeaders(headers);
        // perform some actions
        call.get("sample/").verify().equals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "headers"},
            description = "An integration test to verify we can successfully override standard header values")
    public void overrideAcceptTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom headers
        Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "no-check");
        call.addHeaders(headers);
        // perform some actions
        call.get("sample/").verify().equals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "headers"},
            description = "An integration test to verify we can successfully override standard header values")
    public void setCredentialsTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        //set some custom credentials
        call.addCredentials("hello", "world");
        // perform some actions
        call.get("sample/").verify().equals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "headers"},
            description = "An integration negative test to verify we can successfully change header values")
    public void overrideContentTypeTest() {
        JsonObject request = new JsonObject();
        request.addProperty("title", "foo");
        request.addProperty("body", "bar");
        request.addProperty("userId", 2);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        call.setContentType(HTTP.ContentType.FORMDATA);
        // perform some actions
        call.post("posts/", new Request().setJsonPayload(request)).verify().equals(404);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "headers"},
            description = "An integration negative test to verify we can successfully change header values")
    public void setPostMissingFileTest() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request(), new File("Jenkins")).verify().equals(200);
        // verify no issues
        finish();
    }

    // for get calls

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify a successful get call with no parameters")
    public void verifySuccessfulGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verify().equals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters")
    public void verifySuccessfulGetCallParams() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        call.get("posts/", new Request().setUrlParams(params)).verify().equals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters in url")
    public void verifySuccessfulGetCallUrlParams() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verify().equals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify a successful get call with no parameters")
    public void verifySuccessfulGetCallData() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verify().equals(json);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify a successful get call with no parameters")
    public void verifySuccessfulGetCallMessageData() {
        JsonArray json = new JsonArray();
        json.add(json1);
        json.add(json2);
        json.add(json3);
        json.add(json4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/").verify().equals(json.toString());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters")
    public void verifySuccessfulGetCallParamsData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        call.get("posts/", new Request().setUrlParams(params)).verify().equals(json4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters")
    public void verifySuccessfulGetCallParamsMessageData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        call.get("posts/", new Request().setUrlParams(params)).verify().equals(json4.toString());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters in url")
    public void verifySuccessfulGetCallUrlParamsData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verify().equals(json4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters in url")
    public void verifySuccessfulGetCallUrlParamsMessageData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("posts/?id=4").verify().equals(json4.toString());
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpget", "response"},
            description = "An integration test to verify a successful get call with parameters in url")
    public void verifySuccessfulBadGetCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.get("null/").verify().equals("We encountered an error, no page was found");
        // verify no issues
        finish();
    }

    // for post calls

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request()).verify().equals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostParamsCallNoData() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setUrlParams(params)).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallNoData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request()).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallNullData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(null)).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonObjectData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(new JsonObject())).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonArrayData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(new JsonArray())).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallEmptyMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setMultipartData(new HashMap<>())).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        call.post("post/4", new Request().setMultipartData(map)).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonObjectMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(new JsonObject()).setMultipartData(new HashMap<>()))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonArrayMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(new JsonArray()).setMultipartData(new HashMap<>()))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.post("post/4", new Request().setMultipartData(new HashMap<>())).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonObjectMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.post("post/4", new Request().setJsonPayload(new JsonObject()).setMultipartData(new HashMap<>()))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonArrayMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.post("post/4", new Request().setJsonPayload(new JsonArray()).setMultipartData(new HashMap<>()))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallUrlParamsData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setUrlParams(new HashMap<>())).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallNoDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request(), new File("Jenkinsfile")).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonObjectDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(new JsonObject()), new File("Jenkinsfile"))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallJsonArrayDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setJsonPayload(new JsonArray()), new File("Jenkinsfile"))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallMultipartDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setMultipartData(new HashMap<>()), new File("Jenkinsfile"))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters")
    public void verifySuccessfulPostCallUrlParamsDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("post/4", new Request().setUrlParams(new HashMap<>()), new File("Jenkinsfile"))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httppost", "response"},
            description = "An integration test to verify a successful post call with parameters in url")
    public void verifySuccessfulBadPostCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.post("null/", new Request()).verify().equals("We encountered an error, no page was found");
        // verify no issues
        finish();
    }

    // for put calls

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request()).verify().equals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutParamCallNoData() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setUrlParams(params)).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallNoData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request()).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonObjectData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setJsonPayload(new JsonObject())).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonArrayData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setJsonPayload(new JsonArray())).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallEmptyMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setMultipartData(new HashMap<>())).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        call.put("post/4", new Request().setMultipartData(map)).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonObjectMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setJsonPayload(new JsonObject()).setMultipartData(new HashMap<>()))
                .verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonArrayMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setJsonPayload(new JsonArray()).setMultipartData(new HashMap<>()))
                .verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.put("post/4", new Request().setMultipartData(new HashMap<>())).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonObjectMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.put("post/4", new Request().setJsonPayload(new JsonObject()).setMultipartData(new HashMap<>()))
                .verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonArrayMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.put("post/4", new Request().setJsonPayload(new JsonArray()).setMultipartData(new HashMap<>()))
                .verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallUrlParamsData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setUrlParams(new HashMap<>())).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallNoDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request(), new File("Jenkinsfile")).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonObjectDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setJsonPayload(new JsonObject()), new File("Jenkinsfile")).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallJsonArrayDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setJsonPayload(new JsonArray()), new File("Jenkinsfile")).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallMultipartDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setMultipartData(new HashMap<>()), new File("Jenkinsfile")).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters")
    public void verifySuccessfulPutCallUrlParamsDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("post/4", new Request().setUrlParams(new HashMap<>()), new File("Jenkinsfile")).verify().equals("");
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpput", "response"},
            description = "An integration test to verify a successful put call with parameters in url")
    public void verifySuccessfulBadPutCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.put("null/", new Request()).verify().equals("We encountered an error, no page was found");
        // verify no issues
        finish();
    }

    // for delete calls

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request()).verify().equals(200);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallNoData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4").verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteParamsCallNoData() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 4);
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setUrlParams(params)).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallEmptyData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request()).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonObjectData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setJsonPayload(new JsonObject())).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonArrayData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setJsonPayload(new JsonArray())).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallEmptyMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setMultipartData(new HashMap<>())).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        call.delete("post/4", new Request().setMultipartData(map)).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonObjectMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setJsonPayload(new JsonObject()).setMultipartData(new HashMap<>()))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonArrayMultipartData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setJsonPayload(new JsonArray()).setMultipartData(new HashMap<>()))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.delete("post/4", new Request().setMultipartData(new HashMap<>())).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonObjectMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.delete("post/4", new Request().setJsonPayload(new JsonObject()).setMultipartData(new HashMap<>()))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonArrayMultipartTrueData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        Map<String, Object> headers = new HashMap<>();
        headers.put("Content-Type", "multipart/form-data;");
        call.addHeaders(headers);
        call.delete("post/4", new Request().setJsonPayload(new JsonArray()).setMultipartData(new HashMap<>()))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallUrlParamsData() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setUrlParams(new HashMap<>())).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallNoDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request(), new File("Jenkinsfile")).verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonObjectDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setJsonPayload(new JsonObject()), new File("Jenkinsfile"))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallJsonArrayDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setJsonPayload(new JsonArray()), new File("Jenkinsfile"))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallMultipartDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setMultipartData(new HashMap<>()), new File("Jenkinsfile"))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters")
    public void verifySuccessfulDeleteCallUrlParamsDataWithFile() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("post/4", new Request().setUrlParams(new HashMap<>()), new File("Jenkinsfile"))
                .verify().equals(simJson4);
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "service", "httpdelete", "response"},
            description = "An integration test to verify a successful delete call with parameters in url")
    public void verifySuccessfulBadDeleteCall() {
        // use this object to verify the app looks as expected
        Call call = this.calls.get();
        // perform some actions
        call.delete("null/", new Request()).verify().equals("We encountered an error, no page was found");
        // verify no issues
        finish();
    }

    //TODO - need tests around PATCH
}
