package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import com.coveros.selenified.services.Response;
import com.coveros.selenified.utilities.Reporter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.*;

public class ServicesAssertExcludesTest {

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

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesPairsStringPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john");
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsStringFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsStringFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john1");
        response.assertExcludes().keyValues(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesPairsIntegerPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsIntegerFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5);
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsIntegerFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6);
        response.assertExcludes().keyValues(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesPairsDoublePassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5);
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsDoubleFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5);
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsDoubleFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5);
        response.assertExcludes().keyValues(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesPairsFloatPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5f);
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsFloatFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5f);
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsFloatFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5f);
        response.assertExcludes().keyValues(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesPairsLongPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5L);
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsLongFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5L);
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsLongFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6L);
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsBooleanFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", true);
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsBooleanFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", false);
        response.assertExcludes().keyValues(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesPairsBytePassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 0);
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsByteFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", (byte) 0);
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsByteFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 1);
        response.assertExcludes().keyValues(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesPairsCharacterPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'a');
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsCharacterFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 'a');
        response.assertExcludes().keyValues(pairs);
    }

    @Test
    public void confirmExcludesPairsCharacterFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'b');
        response.assertExcludes().keyValues(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesPairsJsonObjectPassTest() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Map<String, Object> map = new HashMap<>();
        map.put("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertExcludes().keyValues(map);
    }

    @Test
    public void confirmExcludesPairsJsonObjectFailTest() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        JsonObject badChild = new JsonObject();
        badChild.addProperty("first", "john");
        Map<String, Object> map = new HashMap<>();
        map.put("name", badChild);
        response.assertExcludes().keyValues(map);
    }

    @Test
    public void confirmExcludesPairsJsonObjectFail2Test() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("name1", child);
        response.assertExcludes().keyValues(map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesPairsJsonArrayPassTest() {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Map<String, Object> map = new HashMap<>();
        map.put("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertExcludes().keyValues(map);
    }

    @Test
    public void confirmExcludesPairsJsonArrayFailTest() {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        JsonArray badChild = new JsonArray();
        badChild.add("john");
        Map<String, Object> map = new HashMap<>();
        map.put("name", badChild);
        response.assertExcludes().keyValues(map);
    }

    @Test
    public void confirmExcludesPairsJsonArrayFail2Test() {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("name1", child);
        response.assertExcludes().keyValues(map);
    }

    @Test
    public void confirmExcludesPairsNullTest() {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.assertExcludes().keyValues(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesPairsMismatchTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.assertExcludes().keyValues(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesArrayPassTest() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        response.assertExcludes().value(child);
    }

    @Test
    public void confirmExcludesArrayFailTest() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        JsonObject badChild = new JsonObject();
        badChild.addProperty("first", "john");
        response.assertExcludes().value(badChild);
    }

    @Test
    public void confirmExcludesArrayNullTest() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertExcludes().value(json);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesMessagePassTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.assertExcludes().message("message");
    }

    @Test
    public void confirmExcludesMessageFailTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.assertExcludes().message("message ");
    }

    @Test
    public void confirmExcludesMessageNullTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.assertExcludes().message("");
    }

    @Test
    public void confirmExcludesMessageNull2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.assertExcludes().message("null");
    }

    @Test
    public void confirmExcludesCrumbsPairNullObject() {
        Response response = new Response(reporter, null, 5, null, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.assertExcludes().nestedKeyValues(crumbs, new HashMap<>());
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesCrumbsPairEmptyCrumbs() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.assertExcludes().nestedKeyValues(new ArrayList<>(), map);
    }

    @Test
    public void confirmExcludesCrumbsPairCrumbsNotExist() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("last");
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.assertExcludes().nestedKeyValues(crumbs, map);
    }

    @Test
    public void confirmExcludesCrumbsPairNotJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("first");
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.assertExcludes().nestedKeyValues(crumbs, map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesCrumbsPairMatch() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.assertExcludes().nestedKeyValues(crumbs, map);
    }

    @Test
    public void confirmExcludesCrumbsPairMismatch() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        Map<String, Object> map = new HashMap<>();
        map.put("first", "janice");
        response.assertExcludes().nestedKeyValues(crumbs, map);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void confirmExcludesCrumbsJsonElementNothing() {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.assertExcludes().nestedValue(null, new JsonObject());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void confirmExcludesCrumbsJsonElementNullCrumbs() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.assertExcludes().nestedValue(null, new JsonObject());
    }

    @Test
    public void confirmExcludesCrumbsJsonElementNullObject() {
        Response response = new Response(reporter, null, 5, null, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.assertExcludes().nestedValue(crumbs, new JsonObject());
    }

    @Test
    public void confirmExcludesCrumbsJsonElementEmptyCrumbs() {
        JsonArray array = new JsonArray();
        JsonObject john = new JsonObject();
        john.addProperty("first", "john");
        john.addProperty("last", "doe");
        array.add(john);
        JsonObject jon = new JsonObject();
        jon.addProperty("first", "jon");
        jon.addProperty("last", "doe");
        array.add(jon);
        JsonObject json = new JsonObject();
        json.add("name", array);
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertExcludes().nestedValue(new ArrayList<>(), john);
    }

    @Test
    public void confirmExcludesCrumbsJsonElementCrumbsNotExist() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        JsonObject expected = new JsonObject();
        expected.addProperty("name", "john");
        response.assertExcludes().nestedValue(new ArrayList<>(), expected);
    }

    @Test
    public void confirmExcludesCrumbsJsonElementNotJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("first");
        crumbs.add("name");
        JsonObject expected = new JsonObject();
        expected.addProperty("name", "john");
        response.assertExcludes().nestedValue(crumbs, expected);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesCrumbsJsonElementMatch() {
        JsonArray array = new JsonArray();
        JsonObject john = new JsonObject();
        john.addProperty("first", "john");
        john.addProperty("last", "doe");
        array.add(john);
        JsonObject jon = new JsonObject();
        jon.addProperty("first", "jon");
        jon.addProperty("last", "doe");
        array.add(jon);
        JsonObject json = new JsonObject();
        json.add("name", array);
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.assertExcludes().nestedValue(crumbs, john);
    }

    @Test
    public void confirmExcludesCrumbsJsonElementMismatch() {
        JsonArray array = new JsonArray();
        JsonObject john = new JsonObject();
        john.addProperty("first", "john");
        john.addProperty("last", "doe");
        array.add(john);
        JsonObject jon = new JsonObject();
        jon.addProperty("first", "jon");
        jon.addProperty("last", "doe");
        array.add(jon);
        JsonObject json = new JsonObject();
        json.add("name", array);
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        JsonObject expected = new JsonObject();
        john.addProperty("first", "john");
        response.assertExcludes().nestedValue(crumbs, expected);
    }

    @Test
    public void confirmExcludesKeysStringFailTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertExcludes().keys(Collections.singletonList("first"));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesKeysStringPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertExcludes().keys(Collections.singletonList("name"));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesKeysMultipleStringPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertExcludes().keys(Arrays.asList("first", "last"));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesKeysMultipleStringFailTest() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertExcludes().keys(Arrays.asList("first", "name"));
    }

    @Test
    public void confirmExcludesKeysStringFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.assertExcludes().keyValues(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesCrumbsKeyNullObject() {
        Response response = new Response(reporter, null, 5, null, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.assertExcludes().nestedKeys(crumbs, new ArrayList<>());
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesCrumbsKeyEmptyCrumbs() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.assertExcludes().nestedKeys(new ArrayList<>(), Collections.emptyList());
    }

    @Test
    public void confirmExcludesCrumbsKeyCrumbsNotExist() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("last");
        response.assertExcludes().nestedKeys(crumbs, Collections.singletonList("first"));
    }

    @Test
    public void confirmExcludesCrumbsKeyNotJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("first");
        response.assertExcludes().nestedKeys(crumbs, Collections.singletonList("first"));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesCrumbsKeyMatch() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.assertExcludes().nestedKeys(crumbs, Collections.singletonList("first"));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesCrumbsKeyMismatch() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.assertExcludes().nestedKeys(crumbs, Collections.singletonList("first"));
    }
}