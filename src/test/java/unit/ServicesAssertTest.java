package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import com.coveros.selenified.services.Response;
import com.coveros.selenified.utilities.Reporter;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;

public class ServicesAssertTest {

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
    public void confirmEqualsCodePassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().equals(5);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCodeFailTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().equals(6);
    }

    @Test
    public void confirmEqualsObjectPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().equals(json);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsObjectFailTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().equals(new JsonObject());
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsObjectNullTest() {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.azzert().equals(new JsonObject());
    }

    @Test
    public void confirmEqualsArrayPassTest() {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.azzert().equals(json);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsArrayFailTest() {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.azzert().equals(new JsonArray());
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsArrayNullTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().equals(new JsonArray());
    }

    @Test
    public void confirmEqualsMessagePassTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.azzert().equals("Some message");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsMessageFailTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "SOME MESSAGE");
        response.azzert().equals("Some message");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsMessageNullTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.azzert().equals("");
    }

    @Test
    public void confirmContainsPairsStringPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john");
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsStringFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsStringFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john1");
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsIntegerPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsIntegerFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsIntegerFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsDoublePassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsDoubleFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsDoubleFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsFloatPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5f);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsFloatFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5f);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsFloatFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5f);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsLongPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5L);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsLongFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5L);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsLongFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6L);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsBooleanPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", true);
        response.azzert().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : true</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;true<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsBooleanFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", true);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsBooleanFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", false);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsBytePassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 0);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsByteFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", (byte) 0);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsByteFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 1);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsCharacterPassTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'a');
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsCharacterFailsTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 'a');
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsCharacterFails2Test() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'b');
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsJsonObjectPassTest() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Map<String, Object> map = new HashMap<>();
        map.put("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().contains(map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsJsonObjectFailTest() {
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
        response.azzert().contains(map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsJsonObjectFail2Test() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("name1", child);
        response.azzert().contains(map);
    }

    @Test
    public void confirmContainsPairsJsonArrayPassTest() {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Map<String, Object> map = new HashMap<>();
        map.put("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().contains(map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsJsonArrayFailTest() {
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
        response.azzert().contains(map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsJsonArrayFail2Test() {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("name1", child);
        response.azzert().contains(map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsNullTest() {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsMismatchTest() {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsArrayPassTest() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        response.azzert().contains(child);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsArrayFailTest() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        JsonObject badChild = new JsonObject();
        badChild.addProperty("first", "john");
        response.azzert().contains(badChild);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsArrayNullTest() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().contains(json);
    }

    @Test
    public void confirmContainsMessagePassTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.azzert().contains("message");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsMessageFailTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.azzert().contains("message ");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsMessageNullTest() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.azzert().contains("");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsMessageNull2Test() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.azzert().contains("null");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void confirmEqualsCrumbsNothing() {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.azzert().equals(null, "name");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCrumbsNoJsonObject() {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.azzert().equals(new ArrayList<>(), "name");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void confirmEqualsCrumbsNoCrumbs() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.azzert().equals(null, "name");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCrumbsCrumbsNotExist() {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        List<String> list = new ArrayList<>();
        list.add("name");
        response.azzert().equals(list, "john");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCrumbsCrumbsNotJsonObject() {
        JsonArray array = new JsonArray();
        array.add("john");
        array.add("jon");
        JsonObject json = new JsonObject();
        json.add("first", array);
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> list = new ArrayList<>();
        list.add("first");
        list.add("last");
        response.azzert().equals(list, "john");
    }

    @Test
    public void confirmEqualsCrumbsMatch() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> list = new ArrayList<>();
        list.add("first");
        response.azzert().equals(list, "john");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCrumbsNoMatch() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> list = new ArrayList<>();
        list.add("first");
        response.azzert().equals(list, "janice");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void confirmContainsCrumbsPairNothing() {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.azzert().contains(null, new HashMap<>());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void confirmContainsCrumbsPairNullCrumbs() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.azzert().contains(null, new HashMap<>());
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsCrumbsPairNullObject() {
        Response response = new Response(reporter, null, 5, null, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.azzert().contains(crumbs, new HashMap<>());
    }

    @Test
    public void confirmContainsCrumbsPairEmptyCrumbs() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.azzert().contains(new ArrayList<>(), map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsCrumbsPairCrumbsNotExist() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("last");
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.azzert().contains(crumbs, map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsCrumbsPairNotJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("first");
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.azzert().contains(crumbs, map);
    }

    @Test
    public void confirmContainsCrumbsPairMatch() {
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
        response.azzert().contains(crumbs, map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsCrumbsPairMismatch() {
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
        response.azzert().contains(crumbs, map);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void confirmContainsCrumbsJsonElementNothing() {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.azzert().contains(null, new JsonObject());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void confirmContainsCrumbsJsonElementNullCrumbs() {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.azzert().contains(null, new JsonObject());
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsCrumbsJsonElementNullObject() {
        Response response = new Response(reporter, null, 5, null, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.azzert().contains(crumbs, new JsonObject());
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsCrumbsJsonElementEmptyCrumbs() {
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
        response.azzert().contains(new ArrayList<>(), john);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsCrumbsJsonElementCrumbsNotExist() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        JsonObject expected = new JsonObject();
        expected.addProperty("name", "john");
        response.azzert().contains(new ArrayList<>(), expected);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsCrumbsJsonElementNotJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("first");
        crumbs.add("name");
        JsonObject expected = new JsonObject();
        expected.addProperty("name", "john");
        response.azzert().contains(crumbs, expected);
    }

    @Test()
    public void confirmContainsCrumbsJsonElementMatch() {
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
        response.azzert().contains(crumbs, john);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsCrumbsJsonElementMismatch() {
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
        response.azzert().contains(crumbs, expected);
    }
}