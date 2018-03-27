package unit;

import com.coveros.selenified.services.Request;
import com.google.gson.JsonObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class RequestTest {

    @Test
    public void checkJsonRequestTest() {
        JsonObject json = new JsonObject();
        Request request = new Request(json);
        Assert.assertEquals(request.getData(), json);
    }

    @Test
    public void checkJsonParamsRequestTest() {
        JsonObject json = new JsonObject();
        Request request = new Request(json);
        Assert.assertEquals(request.getParams(), null);
    }

    @Test
    public void checkJsonDataRequestTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Request request = new Request(json);
        Assert.assertEquals(request.getData(), json);
    }

    @Test
    public void checkJsonDataChangeRequestTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Request request = new Request(json);
        json.addProperty("address", "1234 no where");
        request.setData(json);
        Assert.assertEquals(request.getData(), json);
    }

    @Test
    public void checkParamsRequestTest() {
        Map<String, String> params = new HashMap<>();
        Request request = new Request(params);
        Assert.assertEquals(request.getParams(), params);
    }

    @Test
    public void checkParamsJsonRequestTest() {
        Map<String, String> params = new HashMap<>();
        Request request = new Request(params);
        Assert.assertEquals(request.getData(), null);
    }

    @Test
    public void checkParamsDataRequestTest() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "john");
        Request request = new Request(params);
        Assert.assertEquals(request.getParams(), params);
    }

    @Test
    public void checkParamsDataChangeRequestTest() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "john");
        Request request = new Request(params);
        params.put("address", "1234 no where");
        request.setParams(params);
        Assert.assertEquals(request.getParams(), params);
    }
}
