package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import com.coveros.selenified.services.Response;
import com.coveros.selenified.utilities.Reporter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class ServicesCheckTest {

    private Reporter reporter;
    private File directory;
    private File file;

<<<<<<< HEAD
    @BeforeMethod(alwaysRun = true)
=======
    @BeforeMethod
>>>>>>> master
    public void createFile() throws InvalidBrowserException, InvalidProxyException {
        reporter =
                new Reporter("directory", "file", new Capabilities(new Browser("None")), null, null, null, null, null, null);
        directory = new File("directory");
        file = new File("directory", "file.html");
    }

    @AfterMethod
    public void deleteFile() {
        file.delete();
        directory.delete();
    }

    @Test
    public void castObjectNullNullTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertNull(response.azzert().castObject(null, null));
    }

    @Test
    public void castObjectNullTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertEquals(response.azzert().castObject(null, new JsonObject()), new JsonObject());
    }

    @Test
    public void castObjectMismatchTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertEquals(response.azzert().castObject("Hello", new JsonObject()), new JsonObject());
    }

    @Test
    public void castObjectStringTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", "World");
        assertEquals(response.azzert().castObject("Hello", json.get("name")), "World");
    }

    @Test
    public void castObjectIntegerTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        assertEquals(response.azzert().castObject(6, json.get("name")), 5);
    }

    @Test
    public void castObjectDoubleTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5d);
        assertEquals(response.azzert().castObject(6d, json.get("name")), 5d);
    }

    @Test
    public void castObjectFloatTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5f);
        assertEquals(response.azzert().castObject(6f, json.get("name")), 5f);
    }

    @Test
    public void castObjectLongTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        assertEquals(response.azzert().castObject(6L, json.get("name")), 5L);
    }

    @Test
    public void castObjectMixedNumberTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.0);
        assertEquals(response.azzert().castObject(6L, json.get("name")), 5L);
    }

    @Test
    public void castObjectMixedNumber2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        assertEquals(response.azzert().castObject(6, json.get("name")), 5);
    }

    @Test
    public void castObjectMixedNumber3Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        assertEquals(response.azzert().castObject(6.0, json.get("name")), 5.0);
    }

    @Test
    public void castObjectMixedNumber4Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        assertEquals(response.azzert().castObject("Hello", json.get("name")), "5");
    }

    @Test
    public void castObjectBooleanTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        assertEquals(response.azzert().castObject(false, json.get("name")), true);
    }

    @Test
    public void castObjectBooleanMixedTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        assertEquals(response.azzert().castObject("Hello", json.get("name")), "true");
    }

    @Test
    public void castObjectBooleanMixed2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        assertEquals(response.azzert().castObject(5, json.get("name")), new JsonPrimitive(true));
    }

    @Test
    public void castObjectByteTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 9);
        assertEquals(response.azzert().castObject((byte) 0, json.get("name")), (byte) 9);
    }

    @Test
    public void castObjectByteMixedTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 9);
        assertEquals(response.azzert().castObject("Hello", json.get("name")), "9");
    }

    @Test
    public void castObjectByteMixed2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 9);
        assertEquals(response.azzert().castObject(3, json.get("name")), 9);
    }

    @Test
    public void castObjectCharacterTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        assertEquals(response.azzert().castObject('b', json.get("name")), 'a');
    }

    @Test
    public void castObjectCharacterMixedTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        assertEquals(response.azzert().castObject("b", json.get("name")), "a");
    }

    @Test
    public void castObjectCharacterMixed2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", '5');
        assertEquals(response.azzert().castObject(2, json.get("name")), 5);
    }

    @Test
    public void castObjectCharacterMixed3Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", '5');
        assertEquals(response.azzert().castObject(2.0, json.get("name")), 5.0);
    }

    @Test
    public void castObjectJsonObjectTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonObject());
        assertEquals(response.azzert().castObject(new JsonObject(), json.get("name")), new JsonObject());
    }

    @Test
    public void castObjectJsonObjectMixedTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonObject());
        assertEquals(response.azzert().castObject(new JsonArray(), json.get("name")), new JsonObject());
    }

    @Test
    public void castObjectJsonObjectMixed2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonObject());
        assertEquals(response.azzert().castObject("Hello", json.get("name")), new JsonObject());
    }

    @Test
    public void castObjectJsonArrayTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonArray());
        assertEquals(response.azzert().castObject(new JsonArray(), json.get("name")), new JsonArray());
    }

    @Test
    public void castObjectJsonArrayMixedTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonArray());
        assertEquals(response.azzert().castObject(new JsonObject(), json.get("name")), new JsonArray());
    }

    @Test
    public void castObjectJsonArrayMixed2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonArray());
        assertEquals(response.azzert().castObject("Hello", json.get("name")), new JsonArray());
    }
}