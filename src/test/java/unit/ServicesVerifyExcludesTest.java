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
import java.util.*;

import static org.testng.Assert.assertTrue;

public class ServicesVerifyExcludesTest {

    private Reporter reporter;
    private File directory;
    private File file;

    @BeforeMethod(alwaysRun = true)
    public void createFile() throws InvalidBrowserException, InvalidProxyException {
        reporter =
                new Reporter("directory", "file", new Capabilities(new Browser("None")),
                        null, null, null, null, null, null);
        directory = new File("directory");
        file = new File("directory", "file.html");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteFile() {
        file.delete();
        directory.delete();
    }

    @Test
    public void confirmExcludesPairsStringPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john");
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsStringFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name1 : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsStringFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john1");
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : john1</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i></div>" +
                        "</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsIntegerPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : 5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsIntegerFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name1 : 5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsIntegerFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : 6</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsDoublePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : 5.5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5.5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsDoubleFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name1 : 5.5</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5.5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsDoubleFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : 6.5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5.5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsFloatPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5f);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : 5.5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5.5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsFloatFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5f);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name1 : 5.5</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5.5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsFloatFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5f);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : 6.5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5.5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsLongPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5L);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : 5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsLongFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5L);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name1 : 5</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsLongFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6L);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : 6</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsBooleanPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", true);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : true</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;true<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsBooleanFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", true);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name1 : true</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;true<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsBooleanFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", false);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : false</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;true<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsBytePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 0);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : 0</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;0<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsByteFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", (byte) 0);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name1 : 0</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;0<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsByteFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 1);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : 1</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;0<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsCharacterPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'a');
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : a</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"a\"<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsCharacterFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 'a');
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name1 : a</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"a\"<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsCharacterFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'b');
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : b</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"a\"<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsJsonObjectPassTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Map<String, Object> map = new HashMap<>();
        map.put("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyExcludes().keyValues(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : \\{\"first\":\"john\"," +
                        "\"last\":\"smith\"}</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;" +
                        "&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>&nbsp;&nbsp;}<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsJsonObjectFailTest() throws IOException {
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
        response.verifyExcludes().keyValues(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : \\{\"first\":\"john\"}</div></i></div></td>\n {4}" +
                        "<td>Found a response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>&nbsp;&nbsp;}<br/>}" +
                        "</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsJsonObjectFail2Test() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("name1", child);
        response.verifyExcludes().keyValues(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name1 : \\{\"first\":\"john\"," +
                        "\"last\":\"smith\"}</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"john\"," +
                        "<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>&nbsp;&nbsp;}<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsJsonArrayPassTest() throws IOException {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Map<String, Object> map = new HashMap<>();
        map.put("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyExcludes().keyValues(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : \\[\"john\",\"smith\"]</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\[<br/>&nbsp;&nbsp;&nbsp;" +
                        "&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"smith\"<br/>&nbsp;&nbsp;" +
                        "]<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsJsonArrayFailTest() throws IOException {
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
        response.verifyExcludes().keyValues(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name : \\[\"john\"]</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\[<br/>&nbsp;&nbsp;&nbsp;" +
                        "&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"smith\"<br/>&nbsp;&nbsp;" +
                        "]<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}" +
                        "</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsJsonArrayFail2Test() throws IOException {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("name1", child);
        response.verifyExcludes().keyValues(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name1 : \\[\"john\",\"smith\"]</div></i></div></td>\n {4}" +
                        "<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\[<br/>&nbsp;&nbsp;&nbsp;" +
                        "&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"smith\"<br/>&nbsp;&nbsp;" +
                        "]<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}" +
                        "</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsNullTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i><div>name1 : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\[<br/>&nbsp;&nbsp;\"name\"<br/>]</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}" +
                        "<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesPairsMismatchTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.verifyExcludes().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches("[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}" +
                "<td>Expected to find a response excluding: <div><i><div>name : 5</div></i></div></td>\n {4}" +
                "<td>Found a response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;" +
                "5<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}" +
                "<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesArrayPassTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        response.verifyExcludes().value(child);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":" +
                        "&nbsp;\"smith\"<br/>}</i></div></td>\n {4}<td>Found a response of: <div><i>\\[<br/>&nbsp;&nbsp;\\{" +
                        "<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"smith\"" +
                        "<br/>&nbsp;&nbsp;}<br/>]</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesArrayFailTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        JsonObject badChild = new JsonObject();
        badChild.addProperty("first", "john");
        response.verifyExcludes().value(badChild);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}" +
                        "<td>Found a response of: <div><i>\\[<br/>&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;" +
                        "\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>&nbsp;&nbsp;}<br/>]</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesArrayNullTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyExcludes().value(json);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: <div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\"" +
                        ":&nbsp;\"smith\"<br/>}</i></div></td>\n {4}<td>Found a response of: <div><i>\\{<br/>&nbsp;&nbsp;" +
                        "\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}</i></div></td>\n {4}<td>" +
                        "[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesMessagePassTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.verifyExcludes().message("message");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: '<i>message</i>'</td>\n {4}<td>Found a response of: " +
                        "'<i>Some message</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesMessageFailTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.verifyExcludes().message("message ");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: '<i>message </i>'</td>\n {4}<td>Found a response of: " +
                        "'<i>Some message</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesMessageNullTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.verifyExcludes().message("");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: '<i></i>'</td>\n {4}<td>Found a response of: " +
                        "'<i>null</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesMessageNull2Test() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.verifyExcludes().message("null");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding: '<i>null</i>'</td>\n {4}<td>Found a response of: " +
                        "'<i>null</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsPairNullObject() throws IOException {
        Response response = new Response(reporter, null, 5, null, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.verifyExcludes().nestedKeyValues(crumbs, new HashMap<>());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' excluding: <div><i></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsPairEmptyCrumbs() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.verifyExcludes().nestedKeyValues(new ArrayList<>(), map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' excluding: <div><i><div>first : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}</i></div>" +
                        "</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsPairCrumbsNotExist() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("last");
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.verifyExcludes().nestedKeyValues(crumbs, map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>last</i>' excluding: <div><i><div>first : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsPairNotJsonObject() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("first");
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.verifyExcludes().nestedKeyValues(crumbs, map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>first</i>' excluding: <div><i><div>first : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsPairMatch() throws IOException {
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
        response.verifyExcludes().nestedKeyValues(crumbs, map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' excluding: <div><i><div>first : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}" +
                        "</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsPairMismatch() throws IOException {
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
        response.verifyExcludes().nestedKeyValues(crumbs, map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' excluding: <div><i><div>first : janice</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}</i>" +
                        "</div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsJsonElementNullObject() throws IOException {
        Response response = new Response(reporter, null, 5, null, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.verifyExcludes().nestedValue(crumbs, new JsonObject());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' excluding: <div><i>\\{}</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsJsonElementEmptyCrumbs() throws IOException {
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
        response.verifyExcludes().nestedValue(new ArrayList<>(), john);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' excluding: <div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;" +
                        "\"last\":&nbsp;\"doe\"<br/>}</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\[<br/>&nbsp;&nbsp;&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"doe\"<br/>&nbsp;&nbsp;&nbsp;&nbsp;}," +
                        "<br/>&nbsp;&nbsp;&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"jon\"," +
                        "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"doe\"<br/>&nbsp;&nbsp;&nbsp;&nbsp;}<br/>&nbsp;&nbsp;]<br/>}" +
                        "</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsJsonElementCrumbsNotExist() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        JsonObject expected = new JsonObject();
        expected.addProperty("name", "john");
        response.verifyExcludes().nestedValue(new ArrayList<>(), expected);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' excluding: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsJsonElementNotJsonObject() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("first");
        crumbs.add("name");
        JsonObject expected = new JsonObject();
        expected.addProperty("name", "john");
        response.verifyExcludes().nestedValue(crumbs, expected);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>first&nbsp;\uD83E\uDC1A&nbsp;name</i>' excluding: <div><i>\\{<br/>&nbsp;&nbsp;" +
                        "\"name\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test()
    public void confirmExcludesCrumbsJsonElementMatch() throws IOException {
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
        response.verifyExcludes().nestedValue(crumbs, john);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a response of: '<i>name</i>' " +
                        "excluding: <div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":" +
                        "&nbsp;\"doe\"<br/>}</i></div></td>\n {4}<td>Found a response of: <div><i>\\[<br/>&nbsp;&nbsp;\\{" +
                        "<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"doe\"<br/>&nbsp;&nbsp;}," +
                        "<br/>&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"jon\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":" +
                        "&nbsp;\"doe\"<br/>&nbsp;&nbsp;}<br/>]</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsJsonElementMismatch() throws IOException {
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
        response.verifyExcludes().nestedValue(crumbs, expected);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' excluding: <div><i>\\{}</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\[<br/>&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"doe\"" +
                        "<br/>&nbsp;&nbsp;},<br/>&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"jon\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;" +
                        "\"doe\"<br/>&nbsp;&nbsp;}<br/>]</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesKeysStringPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyExcludes().keys(Collections.singletonList("name"));
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding keys: '<i>name</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i>" +
                        "</div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesKeysMultipleStringPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyExcludes().keys(Arrays.asList("first", "last"));
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding keys: '<i>first</i>', '<i>last</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"john\"<br/>}</i>" +
                        "</div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesKeysMultipleStringFailTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyExcludes().keys(Arrays.asList("first", "name"));
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response excluding keys: '<i>first</i>', '<i>name</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"john\"<br/>}</i>" +
                        "</div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsKeyNullObject() throws IOException {
        Response response = new Response(reporter, null, 5, null, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.verifyExcludes().nestedKeys(crumbs, new ArrayList<>());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' excluding keys: '<i></i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsKeyEmptyCrumbs() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyExcludes().nestedKeys(new ArrayList<>(), Collections.emptyList());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' excluding keys: '<i></i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}</i>" +
                        "</div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesCrumbsKeyCrumbsNotExist() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("last");
        response.verifyExcludes().nestedKeys(crumbs, Collections.singletonList("first"));
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' excluding keys: '<i></i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}</i>" +
                        "</div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void confirmExcludesCrumbsKeyNotJsonObject() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("first");
        response.verifyExcludes().nestedKeys(crumbs, Collections.singletonList("first"));
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' excluding keys: '<i></i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}</i>" +
                        "</div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsKeyMatch() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.verifyExcludes().nestedKeys(crumbs, Collections.singletonList("first"));
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' excluding keys: '<i>first</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}</i>" +
                        "</div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmExcludesCrumbsKeyMismatch() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.verifyExcludes().nestedKeys(crumbs, Collections.singletonList("first"));
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' excluding keys: '<i>first</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}" +
                        "</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }
}