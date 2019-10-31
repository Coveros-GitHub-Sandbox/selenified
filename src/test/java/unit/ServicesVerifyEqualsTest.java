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

public class ServicesVerifyEqualsTest {

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
        response.verifyEquals().code(5);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response code of <b>5</b></td>\n {4}<td>Found a response code of <b>5</b></td>\n {4}<td>[0-9]+ms / " +
                        "[0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCodeFailTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyEquals().code(6);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response code of <b>6</b></td>\n {4}<td>Found a response code of <b>5</b></td>\n {4}<td>[0-9]+ms / " +
                        "[0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
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
                        "\"john\"<br/>}</i></div></td>\n {4}<td>Found a response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\"" +
                        ":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td class='pass'>PASS</td>\n {3}</tr>\n"));
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
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;\"john\"<br/>}</i></div></td>\n {4}<td>[0-9]+ms / " +
                        "[0-9]+ms</td>\n {4}<td class='fail'>FAIL</td>\n {3}</tr>\n"));
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
    public void confirmEqualsArraySizeNotArray() throws IOException {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.verifyEquals().arraySize(-1);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response to be an array with size of '<i>-1</i>'</td>\n {4}<td>Found a response of: " +
                        " which isn't an array</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsArraySizeEmptyMatch() throws IOException {
        Response response = new Response(reporter, null, 5, null, new JsonArray(), null);
        response.verifyEquals().arraySize(0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response to be an array with size of '<i>0</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\[]</i></div> which has a size of '<i>0</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsArraySizeMatch() throws IOException {
        JsonArray array = new JsonArray();
        array.add("5");
        Response response = new Response(reporter, null, 5, null, array, null);
        response.verifyEquals().arraySize(1);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response to be an array with size of '<i>1</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\[<br/>&nbsp;&nbsp;\"5\"<br/>]</i></div> which has a size of '<i>1</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsArraySizeMisMatch() throws IOException {
        JsonArray array = new JsonArray();
        array.add("5");
        Response response = new Response(reporter, null, 5, null, array, null);
        response.verifyEquals().arraySize(2);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response to be an array with size of '<i>2</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\[<br/>&nbsp;&nbsp;\"5\"<br/>]</i></div> which has a size of '<i>1</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCrumbsNotObject() throws IOException {
        Response response = new Response(reporter, null, 5, null, null, null);
        response.verifyEquals().nestedArraySize(new ArrayList<>(), -1);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' to be an array with size of '<i>-1</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div> which isn't an array</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCrumbsEmptyCrumbs() throws IOException {
        JsonArray array = new JsonArray();
        array.add("5");
        JsonObject json = new JsonObject();
        json.add("numbers", array);
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyEquals().nestedArraySize(new ArrayList<>(), -1);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i></i>' to be an array with size of '<i>-1</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\{<br/>&nbsp;&nbsp;\"numbers\":&nbsp;\\[<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"5\"<br/>&nbsp;&nbsp;]" +
                        "<br/>}</i></div> which isn't an array</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCrumbsOneCrumb() throws IOException {
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
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyEquals().nestedArraySize(crumbs, 2);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' to be an array with size of '<i>2</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\[<br/>&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "\"last\":&nbsp;\"doe\"<br/>&nbsp;&nbsp;},<br/>&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"jon\"," +
                        "<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"doe\"<br/>&nbsp;&nbsp;}<br/>]" +
                        "</i></div> which has a size of '<i>2</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='pass'>PASS</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCrumbsTwoCrumbs() throws IOException {
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
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        crumbs.add("first");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyEquals().nestedArraySize(crumbs, 2);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name&nbsp;\uD83E\uDC1A&nbsp;first</i>' to be an array with size of '<i>2</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>null</i></div> which isn't an array</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }

    @Test
    public void confirmEqualsCrumbsArraySizeMisMatch() throws IOException {
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
        List<String> crumbs = new ArrayList<>();
        crumbs.add("name");
        Response response = new Response(reporter, null, 5, json, null, null);
        response.verifyEquals().nestedArraySize(crumbs, 3);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+ {3}<tr>\n {4}<td align='center'>1.</td>\n {4}<td></td>\n {4}<td>Expected to find a " +
                        "response of: '<i>name</i>' to be an array with size of '<i>3</i>'</td>\n {4}<td>Found a response of: " +
                        "<div><i>\\[<br/>&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"john\",<br/>&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "\"last\":&nbsp;\"doe\"<br/>&nbsp;&nbsp;},<br/>&nbsp;&nbsp;\\{<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"first\":&nbsp;\"jon\"," +
                        "<br/>&nbsp;&nbsp;&nbsp;&nbsp;\"last\":&nbsp;\"doe\"<br/>&nbsp;&nbsp;}<br/>]" +
                        "</i></div> which has a size of '<i>2</i>'</td>\n {4}<td>[0-9]+ms / [0-9]+ms</td>\n {4}<td " +
                        "class='fail'>FAIL</td>\n {3}</tr>\n"));
    }
}