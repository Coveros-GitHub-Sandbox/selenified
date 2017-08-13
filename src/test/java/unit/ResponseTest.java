package unit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coveros.selenified.services.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ResponseTest {

    @Test
    public void checkNewResponseCodeCodeTest() {
        Response response = new Response(5);
        Assert.assertEquals(response.getCode(), 5);
    }

    @Test
    public void checkNewResponseCodeObjectTest() {
        Response response = new Response(5);
        Assert.assertNull(response.getObjectData());
    }

    @Test
    public void checkNewResponseCodeArrayTest() {
        Response response = new Response(5);
        Assert.assertNull(response.getArrayData());
    }

    @Test
    public void checkNewResponseCodeMessageTest() {
        Response response = new Response(5);
        Assert.assertNull(response.getMessage());
    }

    @Test
    public void checkNewResponseObjectCodeTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getCode(), 5);
    }

    @Test
    public void checkNewResponseObjectObjectTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getObjectData(), json);
    }

    @Test
    public void checkNewResponseObjectArrayTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(5, json, "");
        Assert.assertNull(response.getArrayData());
    }

    @Test
    public void checkNewResponseObjectMessageTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getMessage(), "");
    }

    @Test
    public void checkNewResponseArrayCodeTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getCode(), 5);
    }

    @Test
    public void checkNewResponseArrayObjectTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(5, json, "");
        Assert.assertNull(response.getObjectData());
    }

    @Test
    public void checkNewResponseArrayArrayTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getArrayData(), json);
    }

    @Test
    public void checkNewResponseArrayMessageTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getMessage(), "");
    }

    @Test
    public void checkSetObjectTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getObjectData(), json);
        json.addProperty("name", "john");
        response.setObjectData(json);
        Assert.assertEquals(response.getObjectData(), json);
    }

    @Test
    public void checkSetArrayTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getArrayData(), json);
        json.add("name");
        response.setArrayData(json);
        Assert.assertEquals(response.getArrayData(), json);
    }

    @Test
    public void checkSetMessageTest() {
        JsonArray json = new JsonArray();
        String message = "";
        Response response = new Response(5, json, message);
        Assert.assertEquals(response.getMessage(), message);
        String newMessage = "hello world";
        response.setMessage(newMessage);
        Assert.assertEquals(response.getMessage(), newMessage);
    }

    /////
    // TODO - assertion
}
