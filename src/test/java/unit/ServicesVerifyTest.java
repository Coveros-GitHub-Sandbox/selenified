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

public class ServicesVerifyTest {

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

    @AfterMethod(alwaysRun = true)
    public void deleteFile() {
        file.delete();
        directory.delete();
    }

    @Test
    public void confirmEqualsCodePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyEquals().code(5);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a response code of <b>5</b></td>\n {4}<td>Found a response code of <b>5</b></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCodeFailTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyEquals().code(6);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a response code of <b>6</b></td>\n {4}<td>Found a response code of <b>5</b></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsObjectPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyEquals().objectData(json);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;" +
                        "\"john\"<br/>}</i></div></td>\n {4}<td>Found a response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsObjectFailTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyEquals().objectData(new JsonObject());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: <div><i>\\{}</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsObjectNullTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.verifyEquals().objectData(new JsonObject());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: <div><i>\\{}</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\[<br/>&nbsp;&nbsp;\"name\"<br/>]</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsArrayPassTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.verifyEquals().arrayData(json);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: <div><i>\\[<br/>&nbsp;&nbsp;\"name\"<br/>]</i></div></td>\n {4}<td>Found " +
                        "a response of: <div><i>\\[<br/>&nbsp;&nbsp;\"name\"<br/>]</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsArrayFailTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.verifyEquals().arrayData(new JsonArray());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: <div><i>\\[]</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\[<br/>&nbsp;&nbsp;\"name\"<br/>]</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsArrayNullTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyEquals().arrayData(new JsonArray());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: <div><i>\\[]</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsMessagePassTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.verifyEquals().message("Some message");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>Some message</i>'</td>\n {4}<td>Found " +
                        "a response of: '<i>Some message</i>'</td>\n {4}<td>[0-9]+ms" +
                        " / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsMessageFailTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "SOME MESSAGE");
        response.verifyEquals().message("Some message");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>Some message</i>'</td>\n {4}<td>Found " +
                        "a response of: '<i>SOME MESSAGE</i>'</td>\n {4}<td>[0-9]+ms" +
                        " / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsMessageNullTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.verifyEquals().message("");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>'</td>\n {4}<td>Found " +
                        "a response of: '<i>null</i>'</td>\n {4}<td>[0-9]+ms" +
                        " / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsStringPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john");
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsStringFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name1 : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsStringFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john1");
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : john1</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i></div>" +
                        "</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsIntegerPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : 5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsIntegerFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsIntegerFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : 6</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsDoublePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : 5.5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5.5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsDoubleFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 5.5</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5.5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsDoubleFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : 6.5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5.5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsFloatPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5f);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : 5.5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5.5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsFloatFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5f);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 5.5</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5.5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsFloatFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5f);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : 6.5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5.5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsLongPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5L);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : 5</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsLongFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5L);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 5</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsLongFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6L);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : 6</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;5<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsBooleanPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", true);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : true</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;true<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsBooleanFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", true);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name1 : true</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;true<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsBooleanFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", false);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : false</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;true<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsBytePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 0);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : 0</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;0<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsByteFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", (byte) 0);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 0</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;0<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsByteFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 1);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : 1</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;0<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsCharacterPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'a');
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : a</div></i></div></td>\n {4}<td>Found a response" +
                        " of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"a\"<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsCharacterFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 'a');
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name1 : a</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"a\"<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsCharacterFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'b');
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : b</div></i></div></td>\n {4}<td>Found a " +
                        "response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"a\"<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsJsonObjectPassTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Map<String, Object> map = new HashMap<>();
        map.put("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyContains().keyValues(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : \\{\"first\":\"john\"," +
                        "\"last\":\"smith\"}</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;" +
                        "&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>&nbsp;&nbsp;}<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsJsonObjectFailTest() throws IOException {
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
        response.verifyContains().keyValues(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : \\{\"first\":\"john\"}</div></i></div></td>\n {4}" +
                        "<td>Found a response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>&nbsp;&nbsp;}<br/>}" +
                        "</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsJsonObjectFail2Test() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("name1", child);
        response.verifyContains().keyValues(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name1 : \\{\"first\":\"john\"," +
                        "\"last\":\"smith\"}</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"john\"," +
                        "<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>&nbsp;&nbsp;}<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsJsonArrayPassTest() throws IOException {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Map<String, Object> map = new HashMap<>();
        map.put("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyContains().keyValues(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : \\[\"john\",\"smith\"]</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\[<br/>&nbsp;&nbsp;&nbsp;" +
                        "&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"smith\"<br/>&nbsp;&nbsp;" +
                        "]<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsJsonArrayFailTest() throws IOException {
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
        response.verifyContains().keyValues(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name : \\[\"john\"]</div></i></div></td>\n {4}<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\[<br/>&nbsp;&nbsp;&nbsp;" +
                        "&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"smith\"<br/>&nbsp;&nbsp;" +
                        "]<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}" +
                        "</tr>\n"));
    }

    @Test
    public void confirmContainsPairsJsonArrayFail2Test() throws IOException {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("name1", child);
        response.verifyContains().keyValues(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name1 : \\[\"john\",\"smith\"]</div></i></div></td>\n {4}" +
                        "<td>Found a response " +
                        "of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\[<br/>&nbsp;&nbsp;&nbsp;" +
                        "&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"smith\"<br/>&nbsp;&nbsp;" +
                        "]<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}" +
                        "</tr>\n"));
    }

    @Test
    public void confirmContainsPairsNullTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i><div>name1 : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\[<br/>&nbsp;&nbsp;\"name\"<br/>]</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}" +
                        "<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsPairsMismatchTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.verifyContains().keyValues(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches("[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}" +
                "<td>Expected to find a response containing: <div><i><div>name : 5</div></i></div></td>\n {4}" +
                "<td>Found a response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;" +
                "5<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}" +
                "<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsArrayPassTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        response.verifyContains().value(child);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":" +
                        "&nbsp;\"smith\"<br/>}</i></div></td>\n {4}<td>Found a response of: <div><i>\\[<br/>&nbsp;&nbsp;\\{" +
                        "<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"smith\"" +
                        "<br/>&nbsp;&nbsp;}<br/>]</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsArrayFailTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        JsonObject badChild = new JsonObject();
        badChild.addProperty("first", "john");
        response.verifyContains().value(badChild);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}" +
                        "<td>Found a response of: <div><i>\\[<br/>&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;" +
                        "\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>&nbsp;&nbsp;}<br/>]</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsArrayNullTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyContains().value(json);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: <div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\"" +
                        ":&nbsp;\"smith\"<br/>}</i></div></td>\n {4}<td>Found a response of: <div><i>\\{<br/>&nbsp;&nbsp;" +
                        "\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}</i></div></td>\n {4}<td>" +
                        "[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsMessagePassTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.verifyContains().message("message");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: '<i>message</i>'</td>\n {4}<td>Found a response of: " +
                        "'<i>Some message</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsMessageFailTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.verifyContains().message("message ");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: '<i>message </i>'</td>\n {4}<td>Found a response of: " +
                        "'<i>Some message</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsMessageNullTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.verifyContains().message("");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: '<i></i>'</td>\n {4}<td>Found a response of: " +
                        "'<i>null</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsMessageNull2Test() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.verifyContains().message("null");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response containing: '<i>null</i>'</td>\n {4}<td>Found a response of: " +
                        "'<i>null</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCrumbsNoJsonObject() throws IOException {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.verifyEquals().nestedValue(new ArrayList<>(), "name");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' with value of: <div><i>\"name\"</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCrumbsCrumbsNotExist() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        List<String> list = new ArrayList<>();
        list.add("name");
        response.verifyEquals().nestedValue(list, "john");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' with value of: <div><i>\"john\"</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCrumbsCrumbsNotJsonObject() throws IOException {
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
        response.verifyEquals().nestedValue(list, "john");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>first&nbsp;\uD83E\uDC1A&nbsp;last</i>' with value of: <div><i>\"john\"</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCrumbsMatch() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> list = new ArrayList<>();
        list.add("first");
        response.verifyEquals().nestedValue(list, "john");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>first</i>' with value of: <div><i>\"john\"</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\"john\"</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCrumbsNoMatch() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> list = new ArrayList<>();
        list.add("first");
        response.verifyEquals().nestedValue(list, "janice");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>first</i>' with value of: <div><i>\"janice\"</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\"john\"</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsCrumbsPairNullObject() throws IOException {
        Response response = new Response(reporter, null, 5, null, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.verifyContains().nestedKeyValues(crumbs, new HashMap<>());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' containing: <div><i></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsCrumbsPairEmptyCrumbs() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.verifyContains().nestedKeyValues(new ArrayList<>(), map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' containing: <div><i><div>first : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}</i></div>" +
                        "</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsCrumbsPairCrumbsNotExist() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("last");
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.verifyContains().nestedKeyValues(crumbs, map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>last</i>' containing: <div><i><div>first : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsCrumbsPairNotJsonObject() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("first");
        Map<String, Object> map = new HashMap<>();
        map.put("first", "john");
        response.verifyContains().nestedKeyValues(crumbs, map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>first</i>' containing: <div><i><div>first : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsCrumbsPairMatch() throws IOException {
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
        response.verifyContains().nestedKeyValues(crumbs, map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' containing: <div><i><div>first : john</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}" +
                        "</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsCrumbsPairMismatch() throws IOException {
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
        response.verifyContains().nestedKeyValues(crumbs, map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' containing: <div><i><div>first : janice</div></i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}</i>" +
                        "</div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsCrumbsJsonElementNullObject() throws IOException {
        Response response = new Response(reporter, null, 5, null, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        response.verifyContains().nestedValue(crumbs, new JsonObject());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' containing: <div><i>\\{}</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsCrumbsJsonElementEmptyCrumbs() throws IOException {
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
        response.verifyContains().nestedValue(new ArrayList<>(), john);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' containing: <div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;" +
                        "\"last\":&nbsp;\"doe\"<br/>}</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\\[<br/>&nbsp;&nbsp;&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"doe\"<br/>&nbsp;&nbsp;&nbsp;&nbsp;}," +
                        "<br/>&nbsp;&nbsp;&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"jon\"," +
                        "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"doe\"<br/>&nbsp;&nbsp;&nbsp;&nbsp;}<br/>&nbsp;&nbsp;]<br/>}" +
                        "</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsCrumbsJsonElementCrumbsNotExist() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        JsonObject expected = new JsonObject();
        expected.addProperty("name", "john");
        response.verifyContains().nestedValue(new ArrayList<>(), expected);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' containing: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":&nbsp;\"smith\"<br/>}</i></div></td>\n {4}" +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsCrumbsJsonElementNotJsonObject() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        List<String> crumbs = new ArrayList<>();
        crumbs.add("first");
        crumbs.add("name");
        JsonObject expected = new JsonObject();
        expected.addProperty("name", "john");
        response.verifyContains().nestedValue(crumbs, expected);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>first&nbsp;\uD83E\uDC1A&nbsp;name</i>' containing: <div><i>\\{<br/>&nbsp;&nbsp;" +
                        "\"name\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test()
    public void confirmContainsCrumbsJsonElementMatch() throws IOException {
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
        response.verifyContains().nestedValue(crumbs, john);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a response of: '<i>name</i>' " +
                        "containing: <div><i>\\{<br/>&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;\"last\":" +
                        "&nbsp;\"doe\"<br/>}</i></div></td>\n {4}<td>Found a response of: <div><i>\\[<br/>&nbsp;&nbsp;\\{" +
                        "<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"doe\"<br/>&nbsp;&nbsp;}," +
                        "<br/>&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"jon\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":" +
                        "&nbsp;\"doe\"<br/>&nbsp;&nbsp;}<br/>]</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmContainsCrumbsJsonElementMismatch() throws IOException {
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
        response.verifyContains().nestedValue(crumbs, expected);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' containing: <div><i>\\{}</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\\[<br/>&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"doe\"" +
                        "<br/>&nbsp;&nbsp;},<br/>&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"jon\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;" +
                        "\"doe\"<br/>&nbsp;&nbsp;}<br/>]</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }
}