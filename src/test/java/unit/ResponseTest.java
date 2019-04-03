package unit;

import com.coveros.selenified.services.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ResponseTest {

    @Test
    public void defaultIsDataTest() {
        Response response = new Response(null, 0, null, null, null);
        assertFalse(response.isData());
    }

    @Test
    public void IsDataObjectTest() {
        Response response = new Response(null, 0, new JsonObject(), null, null);
        assertTrue(response.isData());
    }

    @Test
    public void IsDataArrayTest() {
        Response response = new Response(null, 0, null, new JsonArray(), null);
        assertTrue(response.isData());
    }

    @Test
    public void IsDataBothTest() {
        Response response = new Response(null, 0, new JsonObject(), new JsonArray(), null);
        assertTrue(response.isData());
    }
}