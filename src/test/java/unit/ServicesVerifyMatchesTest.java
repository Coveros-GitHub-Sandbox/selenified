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
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ServicesVerifyMatchesTest {

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
    public void confirmEqualsCodePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyMatches().code("(\\d)");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response code matching a pattern of: '<i>\\(\\\\d\\)</i>'</td>\n {4}<td>Found a response code of <b>5</b></td>\n {4}<td>[0-9]+ms / " +
                        "[0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCodeFailTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyMatches().code("([0-3])");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response code matching a pattern of: '<i>\\(\\[0-3]\\)</i>'</td>\n {4}<td>Found a response code of <b>5</b></td>\n {4}<td>[0-9]+ms / " +
                        "[0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsMessagePassTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "Some message");
        response.verifyMatches().message("([\\w\\s]*)");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response matching a pattern of: '<i>\\(\\[\\\\w\\\\s]\\*\\)</i>'</td>\n {4}<td>Found " +
                        "a response of: '<i>Some message</i>'</td>\n {4}<td>[0-9]+ms" +
                        " / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsMessageFailTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, "SOME MESSAGE");
        response.verifyMatches().message("([a-z]*)");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response matching a pattern of: '<i>\\(\\[a-z]\\*\\)</i>'</td>\n {4}<td>Found " +
                        "a response of: '<i>SOME MESSAGE</i>'</td>\n {4}<td>[0-9]+ms" +
                        " / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsMessageNullTest() throws IOException {
        Response response = new Response(reporter, null, 5, new JsonObject(), null, null);
        response.verifyMatches().message("");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response matching a pattern of: '<i></i>'</td>\n {4}<td>Found " +
                        "a response of: '<i>null</i>'</td>\n {4}<td>[0-9]+ms" +
                        " / [0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCrumbsNoJsonObject() throws IOException {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.verifyMatches().nestedValue(new ArrayList<>(), "name");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' matching a pattern of: <div><i>name</i></div></td>\n {4}<td>Found a response of: " +
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
        response.verifyMatches().nestedValue(list, "john");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' matching a pattern of: <div><i>john</i></div></td>\n {4}<td>Found a response of: " +
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
        response.verifyMatches().nestedValue(list, "john");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>first&nbsp;&#8594;&nbsp;last</i>' matching a pattern of: <div><i>john</i></div></td>\n {4}<td>Found a response of: " +
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
        response.verifyMatches().nestedValue(list, "john");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>first</i>' matching a pattern of: <div><i>john</i></div></td>\n {4}<td>Found a response of: " +
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
        response.verifyMatches().nestedValue(list, "janice");
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>first</i>' matching a pattern of: <div><i>janice</i></div></td>\n {4}<td>Found a response of: " +
                        "<div><i>\"john\"</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }
}