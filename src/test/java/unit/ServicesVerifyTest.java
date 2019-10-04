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
        response.verify().equals(5);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a response code of <b>5</b></td>\n    <td>Found a response code of <b>5</b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsCodeFailTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verify().equals(6);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a response code of <b>6</b></td>\n    <td>Found a response code of <b>5</b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsObjectPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verify().equals(json);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;" +
                        "\"john\"<br/>\\}</i></div></td>\n    <td>Found a response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsObjectFailTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verify().equals(new JsonObject());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response of: <div><i>\\{\\}</i></div></td>\n    <td>Found a response of: " +
                        "<div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsObjectNullTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.verify().equals(new JsonObject());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response of: <div><i>\\{\\}</i></div></td>\n    <td>Found a response of: " +
                        "<div><i>\\[<br/>\\&nbsp;\\&nbsp;\"name\"<br/>\\]</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsArrayPassTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.verify().equals(json);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response of: <div><i>\\[<br/>\\&nbsp;\\&nbsp;\"name\"<br/>\\]</i></div></td>\n    <td>Found " +
                        "a response of: <div><i>\\[<br/>\\&nbsp;\\&nbsp;\"name\"<br/>\\]</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsArrayFailTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        response.verify().equals(new JsonArray());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response of: <div><i>\\[\\]</i></div></td>\n    <td>Found a response of: " +
                        "<div><i>\\[<br/>\\&nbsp;\\&nbsp;\"name\"<br/>\\]</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsArrayNullTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verify().equals(new JsonArray());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response of: <div><i>\\[\\]</i></div></td>\n    <td>Found a response of: " +
                        "<div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsMessagePassTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.verify().equals("Some message");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response of: '<i>Some message</i>'</td>\n    <td>Found " +
                        "a response of: '<i>Some message</i>'</td>\n    <td>[0-9]+ms" +
                        " / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsMessageFailTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "SOME MESSAGE");
        response.verify().equals("Some message");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response of: '<i>Some message</i>'</td>\n    <td>Found " +
                        "a response of: '<i>SOME MESSAGE</i>'</td>\n    <td>[0-9]+ms" +
                        " / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsMessageNullTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.verify().equals("");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response of: '<i></i>'</td>\n    <td>Found " +
                        "a response of: '<i>null</i>'</td>\n    <td>[0-9]+ms" +
                        " / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsStringPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john");
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : john</div></i></div></td>\n    <td>Found a response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsStringFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : john</div></i></div></td>\n    <td>Found a response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsStringFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john1");
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : john1</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsIntegerPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsIntegerFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsIntegerFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 6</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsDoublePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 5.5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5.5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsDoubleFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 5.5</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5.5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsDoubleFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 6.5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5.5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsFloatPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5f);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 5.5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5.5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsFloatFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5f);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 5.5</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5.5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsFloatFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5f);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 6.5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5.5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsLongPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5L);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsLongFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5L);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 5</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsLongFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6L);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 6</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsBooleanPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", true);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : true</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;true<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsBooleanFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", true);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : true</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;true<br/>\\}</i></div></td>\n " +
                        "   <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsBooleanFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", false);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : false</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;true<br/>\\}</i></div></td>\n " +
                        "   <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsBytePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 0);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 0</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;0<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsByteFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", (byte) 0);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 0</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;0<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsByteFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 1);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 1</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;0<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsCharacterPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'a');
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : a</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"a\"<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsCharacterFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 'a');
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : a</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"a\"<br/>\\}</i></div></td>\n" +
                        "    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsCharacterFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'b');
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : b</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"a\"<br/>\\}</i></div></td>\n" +
                        "    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
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
        response.verify().contains(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : \\{\"first\":\"john\"," +
                        "\"last\":\"smith\"\\}</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\\{<br/>\\&nbsp;\\&nbsp;\\&nbsp;" +
                        "\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;\\}<br/>}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
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
        Map<String, Object> map = new HashMap();
        map.put("name", badChild);
        response.verify().contains(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : \\{\"first\":\"john\"\\}</div></i></div></td>\n    " +
                        "<td>Found a response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\\{<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;\\}<br/>}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsJsonObjectFail2Test() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap();
        map.put("name1", child);
        response.verify().contains(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : \\{\"first\":\"john\"," +
                        "\"last\":\"smith\"\\}</div></i></div></td>\n    <td>Found a response of: " +
                        "<div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\\{<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;\\}<br/>}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
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
        response.verify().contains(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : \\[\"john\",\"smith\"\\]</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\\[<br/>\\&nbsp;\\&nbsp;\\&nbsp;" +
                        "\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;" +
                        "\\]<br/>}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
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
        Map<String, Object> map = new HashMap();
        map.put("name", badChild);
        response.verify().contains(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : \\[\"john\"\\]</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\\[<br/>\\&nbsp;\\&nbsp;\\&nbsp;" +
                        "\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;" +
                        "\\]<br/>}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n" +
                        "   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsJsonArrayFail2Test() throws IOException {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> map = new HashMap();
        map.put("name1", child);
        response.verify().contains(map);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : \\[\"john\",\"smith\"\\]</div></i></div></td>\n   " +
                        " <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\\[<br/>\\&nbsp;\\&nbsp;\\&nbsp;" +
                        "\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;" +
                        "\\]<br/>}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n" +
                        "   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsNullTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(reporter, null, 5, null, json, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : john</div></i></div></td>\n    <td>Found a response of: <div><i>\\[<br/>\\&nbsp;\\&nbsp;\"name\"<br/>\\]</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsMismatchTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5L);
        Response response = new Response(reporter, null, 5, json, null, null);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.verify().contains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches("[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n" +
                "    <td>Expected to find a response containing: <div><i><div>name : 5</div></i></div></td>\n" +
                "    <td>Found a response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;" +
                "5<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n" +
                "    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsArrayPassTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(reporter, null, 5, null, json, null);
        response.verify().contains(child);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\}</i></div></td>\n    <td>Found a response of: <div><i>\\[<br/>&nbsp;&nbsp;\\{<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;\\}<br/>\\]</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
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
        response.verify().contains(badChild);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>Found a response of: <div><i>\\[<br/>&nbsp;&nbsp;\\{<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;\\}<br/>\\]</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsArrayNullTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verify().contains(json);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\}</i></div></td>\n    <td>Found a response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsMessagePassTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.verify().contains("message");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: '<i>message</i>'</td>\n    <td>Found a response of: " +
                        "'<i>Some message</i>'</td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td " +
                        "class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsMessageFailTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.verify().contains("message ");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: '<i>message </i>'</td>\n    <td>Found a response of: " +
                        "'<i>Some message</i>'</td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td " +
                        "class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsMessageNullTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.verify().contains("");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: '<i></i>'</td>\n    <td>Found a response of: " +
                        "'<i>null</i>'</td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td " +
                        "class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsMessageNull2Test() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.verify().contains("null");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected to find a " +
                        "response containing: '<i>null</i>'</td>\n    <td>Found a response of: " +
                        "'<i>null</i>'</td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td " +
                        "class='fail'>FAIL</td>\n   </tr>\n"));
    }
}