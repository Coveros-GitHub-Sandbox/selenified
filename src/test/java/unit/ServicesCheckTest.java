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
import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.AssertJUnit.assertFalse;

public class ServicesCheckTest {

    private Reporter reporter;
    private File directory;
    private File file;

    @BeforeMethod(alwaysRun = true)
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
        assertNull(response.assertContains().castObject(null, null));
    }

    @Test
    public void castObjectNotNullTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertNull(response.assertContains().castObject("Hi", null));
    }

    @Test
    public void castObjectNullTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertEquals(response.assertContains().castObject(null, new JsonObject()), new JsonObject());
    }

    @Test
    public void castObjectMismatchTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertEquals(response.assertContains().castObject("Hello", new JsonObject()), new JsonObject());
    }

    @Test
    public void castObjectStringTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", "World");
        assertEquals(response.assertContains().castObject("Hello", json.get("name")), "World");
    }

    @Test
    public void castObjectIntegerTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        assertEquals(response.assertContains().castObject(6, json.get("name")), 5);
    }

    @Test
    public void castObjectDoubleTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5d);
        assertEquals(response.assertContains().castObject(6d, json.get("name")), 5d);
    }

    @Test
    public void castObjectFloatTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5f);
        assertEquals(response.assertContains().castObject(6f, json.get("name")), 5f);
    }

    @Test
    public void castObjectLongTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        assertEquals(response.assertContains().castObject(6L, json.get("name")), 5L);
    }

    @Test
    public void castObjectMixedNumberTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.0);
        assertEquals(response.assertContains().castObject(6L, json.get("name")), 5L);
    }

    @Test
    public void castObjectMixedNumber2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        assertEquals(response.assertContains().castObject(6, json.get("name")), 5);
    }

    @Test
    public void castObjectMixedNumber3Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        assertEquals(response.assertContains().castObject(6.0, json.get("name")), 5.0);
    }

    @Test
    public void castObjectMixedNumber4Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        assertEquals(response.assertContains().castObject("Hello", json.get("name")), "5");
    }

    @Test
    public void castObjectBooleanTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        assertEquals(response.assertContains().castObject(false, json.get("name")), true);
    }

    @Test
    public void castObjectBooleanMixedTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        assertEquals(response.assertContains().castObject("Hello", json.get("name")), "true");
    }

    @Test
    public void castObjectBooleanMixed2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        assertEquals(response.assertContains().castObject(5, json.get("name")), new JsonPrimitive(true));
    }

    @Test
    public void castObjectByteTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 9);
        assertEquals(response.assertContains().castObject((byte) 0, json.get("name")), (byte) 9);
    }

    @Test
    public void castObjectByteMixedTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 9);
        assertEquals(response.assertContains().castObject("Hello", json.get("name")), "9");
    }

    @Test
    public void castObjectByteMixed2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 9);
        assertEquals(response.assertContains().castObject(3, json.get("name")), 9);
    }

    @Test
    public void castObjectCharacterTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        assertEquals(response.assertContains().castObject('b', json.get("name")), 'a');
    }

    @Test
    public void castObjectCharacterMixedTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        assertEquals(response.assertContains().castObject("b", json.get("name")), "a");
    }

    @Test
    public void castObjectCharacterMixed2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", '5');
        assertEquals(response.assertContains().castObject(2, json.get("name")), 5);
    }

    @Test
    public void castObjectCharacterMixed3Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", '5');
        assertEquals(response.assertContains().castObject(2.0, json.get("name")), 5.0);
    }

    @Test
    public void castObjectJsonObjectTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonObject());
        assertEquals(response.assertContains().castObject(new JsonObject(), json.get("name")), new JsonObject());
    }

    @Test
    public void castObjectJsonObjectMixedTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonObject());
        assertEquals(response.assertContains().castObject(new JsonArray(), json.get("name")), new JsonObject());
    }

    @Test
    public void castObjectJsonObjectMixed2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonObject());
        assertEquals(response.assertContains().castObject("Hello", json.get("name")), new JsonObject());
    }

    @Test
    public void castObjectJsonArrayTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonArray());
        assertEquals(response.assertContains().castObject(new JsonArray(), json.get("name")), new JsonArray());
    }

    @Test
    public void castObjectJsonArrayMixedTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonArray());
        assertEquals(response.assertContains().castObject(new JsonObject(), json.get("name")), new JsonArray());
    }

    @Test
    public void castObjectJsonArrayMixed2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonArray());
        assertEquals(response.assertContains().castObject("Hello", json.get("name")), new JsonArray());
    }

    @Test
    public void doesJsonObjectContainPairNullActualTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertFalse(response.assertContains().doesJsonObjectContainPairs(new HashMap<>(), null));
    }

    @Test
    public void doesJsonObjectContainPairEmptyMapTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonArray());
        assertTrue(response.assertContains().doesJsonObjectContainPairs(new HashMap<>(), json));
    }

    @Test
    public void doesJsonObjectContainPairSinglePairContains() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 1);
        Map<String, Object> map = new HashMap<>();
        map.put("name", 1);
        assertTrue(response.assertContains().doesJsonObjectContainPairs(map, json));
    }

    @Test
    public void doesJsonObjectContainPairDoublePairContains() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 1);
        json.addProperty("email", "someemail@123.com");
        json.addProperty("phone", "(123) 456-7890");
        Map<String, Object> map = new HashMap<>();
        map.put("name", 1);
        map.put("email", "someemail@123.com");
        assertTrue(response.assertContains().doesJsonObjectContainPairs(map, json));
    }

    @Test
    public void doesJsonObjectContainPairSinglePairNotContain() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 1);
        json.addProperty("email", "someemail@123.com");
        json.addProperty("phone", "(123) 456-7890");
        Map<String, Object> map = new HashMap<>();
        map.put("name", 2);
        assertFalse(response.assertContains().doesJsonObjectContainPairs(map, json));
    }

    @Test
    public void doesJsonObjectContainPairDoublePairConainsOne() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 1);
        json.addProperty("email", "someemail@123.com");
        json.addProperty("phone", "(123) 456-7890");
        Map<String, Object> map = new HashMap<>();
        map.put("name", 2);
        map.put("email", "someemail@123.com");
        assertFalse(response.assertContains().doesJsonObjectContainPairs(map, json));
    }

    @Test
    public void doesJsonObjectExcludePairNullActualTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertTrue(response.assertExcludes().doesJsonObjectExcludePairs(new HashMap<>(), null));
    }

    @Test
    public void doesJsonObjectExcludePairNullNoneActualTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "john");
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertTrue(response.assertExcludes().doesJsonObjectExcludePairs(map, null));
    }

    @Test
    public void doesJsonObjectExcludePairEmptyMapTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.add("name", new JsonArray());
        assertTrue(response.assertExcludes().doesJsonObjectExcludePairs(new HashMap<>(), json));
    }

    @Test
    public void doesJsonObjectExcludePairSinglePairExcludes() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 1);
        Map<String, Object> map = new HashMap<>();
        map.put("name", 1);
        assertFalse(response.assertExcludes().doesJsonObjectExcludePairs(map, json));
    }

    @Test
    public void doesJsonObjectExcludePairDoublePairExcludes() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 1);
        json.addProperty("email", "someemail@123.com");
        json.addProperty("phone", "(123) 456-7890");
        Map<String, Object> map = new HashMap<>();
        map.put("name", 1);
        map.put("email", "someemail@123.com");
        assertFalse(response.assertExcludes().doesJsonObjectExcludePairs(map, json));
    }

    @Test
    public void doesJsonObjectExcludePairSinglePairNotExclude() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 1);
        json.addProperty("email", "someemail@123.com");
        json.addProperty("phone", "(123) 456-7890");
        Map<String, Object> map = new HashMap<>();
        map.put("name", 2);
        assertTrue(response.assertExcludes().doesJsonObjectExcludePairs(map, json));
    }

    @Test
    public void doesJsonObjectExcludePairSinglePairExclude() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 1);
        json.addProperty("email", "someemail@123.com");
        json.addProperty("phone", "(123) 456-7890");
        Map<String, Object> map = new HashMap<>();
        map.put("number", 2);
        assertTrue(response.assertExcludes().doesJsonObjectExcludePairs(map, json));
    }

    @Test
    public void doesJsonObjectExcludePairDoublePairConainsOne() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        JsonObject json = new JsonObject();
        json.addProperty("name", 1);
        json.addProperty("email", "someemail@123.com");
        json.addProperty("phone", "(123) 456-7890");
        Map<String, Object> map = new HashMap<>();
        map.put("name", 2);
        map.put("email", "someemail@123.com");
        assertFalse(response.assertExcludes().doesJsonObjectExcludePairs(map, json));
    }

    @Test
    public void doesJsonObjectContainKeysEmptyKeys() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertTrue(response.assertContains().doesJsonObjectContainKeys(new ArrayList<>(), null));
    }

    @Test
    public void doesJsonObjectContainKeyKeys() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertFalse(response.assertContains().doesJsonObjectContainKeys(Collections.singletonList("name"), null));
    }

    @Test
    public void doesJsonObjectContainKeysEmptyActualEmptyObjectKeys() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertTrue(response.assertContains().doesJsonObjectContainKeys(new ArrayList<>(), new JsonObject()));
    }

    @Test
    public void doesJsonObjectContainKeysEmptyActualKeys() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertTrue(response.assertContains().doesJsonObjectContainKeys(new ArrayList<>(), json));
    }

    @Test
    public void doesJsonObjectContainKeysActualHasOneKeys() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertTrue(response.assertContains().doesJsonObjectContainKeys(Collections.singletonList("name"), json));
    }

    @Test
    public void doesJsonObjectContainKeysActualHasTwoKeys() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertTrue(response.assertContains().doesJsonObjectContainKeys(Arrays.asList("first", "last"), json));
    }

    @Test
    public void doesJsonObjectContainKeysActualHasOnlyOneKeys() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertFalse(response.assertContains().doesJsonObjectContainKeys(Arrays.asList("first", "john"), json));
    }

    @Test
    public void doesJsonObjectContainKeysActualHasNeitherKeys() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertFalse(response.assertContains().doesJsonObjectContainKeys(Arrays.asList("smith", "john"), json));
    }

    @Test
    public void doesJsonObjectExcludeKeysEmptyKeys() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertTrue(response.assertExcludes().doesJsonObjectExcludeKeys(new ArrayList<>(), null));
    }

    @Test
    public void doesJsonObjectExcludeKeyEmptyKeys() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertTrue(response.assertExcludes().doesJsonObjectExcludeKeys(Collections.singletonList("name"), null));
    }

    @Test
    public void doesJsonObjectExcludeKeysEmptyActualEmptyObjectKeys() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertTrue(response.assertExcludes().doesJsonObjectExcludeKeys(new ArrayList<>(), new JsonObject()));
    }

    @Test
    public void doesJsonObjectExcludeKeysEmptyActualKeys() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertTrue(response.assertExcludes().doesJsonObjectExcludeKeys(new ArrayList<>(), json));
    }

    @Test
    public void doesJsonObjectExcludeKeysActualHasOneKeys() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertFalse(response.assertExcludes().doesJsonObjectExcludeKeys(Collections.singletonList("name"), json));
    }

    @Test
    public void doesJsonObjectExcludeKeysActualHasTwoKeys() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertFalse(response.assertExcludes().doesJsonObjectExcludeKeys(Arrays.asList("first", "last"), json));
    }

    @Test
    public void doesJsonObjectExcludeKeysActualHasOnlyOneKeys() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertFalse(response.assertExcludes().doesJsonObjectExcludeKeys(Arrays.asList("first", "john"), json));
    }

    @Test
    public void doesJsonObjectExcludeKeysActualHasNeitherKeys() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        assertTrue(response.assertExcludes().doesJsonObjectExcludeKeys(Arrays.asList("smith", "john"), json));
    }
}