package unit;

import com.coveros.selenified.services.Request;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class RequestTest {

    @Test
    public void checkJsonDataRequestTest() {
        JsonObject json = new JsonObject();
        Assert.assertEquals(new Request().setJsonPayload(json).getJsonPayload(), json);
    }

    @Test
    public void checkJsonDataMultipartRequestTest() {
        JsonObject json = new JsonObject();
        Assert.assertEquals(new Request().setJsonPayload(json).getMultipartData(), null);
    }

    @Test
    public void checkJsonDataParamsRequestTest() {
        JsonObject json = new JsonObject();
        Assert.assertEquals(new Request().setJsonPayload(json).getUrlParams(), null);
    }

    @Test
    public void checkJsonDataDataRequestTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Assert.assertEquals(new Request().setJsonPayload(json).getJsonPayload(), json);
    }

    @Test
    public void checkJsonArrayRequestTest() {
        JsonArray json = new JsonArray();
        Assert.assertEquals(new Request().setJsonPayload(json).getJsonPayload(), json);
    }

    @Test
    public void checkJsonArrayMultipartRequestTest() {
        JsonArray json = new JsonArray();
        Assert.assertEquals(new Request().setJsonPayload(json).getMultipartData(), null);
    }

    @Test
    public void checkJsonArrayParamsRequestTest() {
        JsonArray json = new JsonArray();
        Assert.assertEquals(new Request().setJsonPayload(json).getUrlParams(), null);
    }

    @Test
    public void checkJsonArrayDataRequestTest() {
        JsonArray json = new JsonArray();
        json.add("john");
        json.add("smith");
        Assert.assertEquals(new Request().setJsonPayload(json).getJsonPayload(), json);
    }

    @Test
    public void checkMultipartRequestTest() {
        Map<String, Object> params = new HashMap<>();
        Assert.assertEquals(new Request().setMultipartData(params).getMultipartData(), params);
    }

    @Test
    public void checkMultipartParamsRequestTest() {
        Map<String, Object> params = new HashMap<>();
        Assert.assertEquals(new Request().setMultipartData(params).getUrlParams(), null);
    }

    @Test
    public void checkMultipartJsonRequestTest() {
        Map<String, Object> params = new HashMap<>();
        Assert.assertEquals(new Request().setMultipartData(params).getJsonPayload(), null);
    }

    @Test
    public void checkMultipartDataRequestTest() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "john");
        Assert.assertEquals(new Request().setMultipartData(params).getMultipartData(), params);
    }

    @Test
    public void checkParamsRequestTest() {
        Map<String, Object> params = new HashMap<>();
        Assert.assertEquals(new Request().setUrlParams(params).getUrlParams(), params);
    }

    @Test
    public void checkParamsJsonRequestTest() {
        Map<String, Object> params = new HashMap<>();
        Assert.assertEquals(new Request().setUrlParams(params).getJsonPayload(), null);
    }

    @Test
    public void checkParamsMultipartRequestTest() {
        Map<String, Object> params = new HashMap<>();
        Assert.assertEquals(new Request().setUrlParams(params).getMultipartData(), null);
    }

    @Test
    public void checkParamsDataRequestTest() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "john");
        Assert.assertEquals(new Request().setUrlParams(params).getUrlParams(), params);
    }

    @Test
    public void checkJsonObjectIsDataTest() {
        JsonObject json = new JsonObject();
        Assert.assertTrue(new Request().setJsonPayload(json).isPayload());
    }

    @Test
    public void checkJsonArrayIsDataTest() {
        JsonArray json = new JsonArray();
        Assert.assertTrue(new Request().setJsonPayload(json).isPayload());
    }

    @Test
    public void checkMultipartIsDataTest() {
        Map<String, Object> params = new HashMap<>();
        Assert.assertTrue(new Request().setMultipartData(params).isPayload());
    }

    @Test
    public void checkParamsIsDataTest() {
        Map<String, Object> params = new HashMap<>();
        Assert.assertFalse(new Request().setUrlParams(params).isPayload());
    }
}
