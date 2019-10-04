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
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class ServicesAssertTest {

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
    public void confirmEqualsCodePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().equals(5);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsCodeFailTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().equals(6);
    }

    @Test
    public void confirmEqualsObjectPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().equals(json);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsObjectFailTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().equals(new JsonObject());
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsObjectNullTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.azzert().equals(new JsonObject());
    }

    @Test
    public void confirmEqualsArrayPassTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.azzert().equals(json);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsArrayFailTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.azzert().equals(new JsonArray());
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsArrayNullTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().equals(new JsonArray());
    }

    @Test
    public void confirmEqualsMessagePassTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.azzert().equals("Some message");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsMessageFailTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "SOME MESSAGE");
        response.azzert().equals("Some message");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmEqualsMessageNullTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.azzert().equals("");
    }

    @Test
    public void confirmContainsPairsStringPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john");
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsStringFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsStringFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john1");
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsIntegerPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsIntegerFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsIntegerFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsDoublePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsDoubleFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsDoubleFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsFloatPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5f);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsFloatFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5f);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsFloatFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5f);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsLongPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5L);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsLongFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5L);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsLongFails2Test() throws IOException {
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
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : true</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;true<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsBooleanFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", true);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsBooleanFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", false);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsBytePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 0);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsByteFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", (byte) 0);
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsByteFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 1);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsCharacterPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'a');
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsCharacterFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 'a');
        response.azzert().contains(pairs);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsCharacterFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'b');
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsJsonObjectPassTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Map<String, Object> map = new HashMap();
        map.put("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().contains(map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsJsonObjectFailTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        JsonObject badChild = new JsonObject();
        badChild.addProperty("first", "john");
        Map<String, Object> map = new HashMap();
        map.put("name", badChild);
        response.azzert().contains(map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsJsonObjectFail2Test() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap();
        map.put("name1", child);
        response.azzert().contains(map);
    }

    @Test
    public void confirmContainsPairsJsonArrayPassTest() throws IOException {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Map<String, Object> map = new HashMap();
        map.put("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().contains(map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsJsonArrayFailTest() throws IOException {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        JsonArray badChild = new JsonArray();
        badChild.add("john");
        Map<String, Object> map = new HashMap();
        map.put("name", badChild);
        response.azzert().contains(map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsJsonArrayFail2Test() throws IOException {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap();
        map.put("name1", child);
        response.azzert().contains(map);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsPairsNullTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsPairsMismatchTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.azzert().contains(pairs);
    }

    @Test
    public void confirmContainsArrayPassTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        response.azzert().contains(child);
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsArrayFailTest() throws IOException {
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
    public void confirmContainsArrayNullTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.azzert().contains(json);
    }

    @Test
    public void confirmContainsMessagePassTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.azzert().contains("message");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsMessageFailTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.azzert().contains("message ");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsMessageNullTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.azzert().contains("");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmContainsMessageNull2Test() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.azzert().contains("null");
    }
}