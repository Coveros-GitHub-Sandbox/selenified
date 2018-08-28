package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Browser.BrowserName;
import com.coveros.selenified.OutputFile;
import com.coveros.selenified.services.Response;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseTest {

    private OutputFile outputFile;
    private File directory;
    private File file;

    @BeforeMethod
    public void createFile() {
        outputFile =
                new OutputFile("directory", "file", new Browser(BrowserName.NONE), null, null, null, null, null, null);
        directory = new File("directory");
        file = new File("directory", "fileNONE.html");
    }

    @AfterMethod
    public void deleteFile() {
        file.delete();
        directory.delete();
    }

    @Test
    public void checkNewResponseFileCodeTest() {
        Response response = new Response(outputFile);
        Assert.assertEquals(response.getCode(), 0);
    }

    @Test
    public void checkNewResponseFileObjectTest() {
        Response response = new Response(outputFile);
        Assert.assertNull(response.getObjectData());
    }

    @Test
    public void checkNewResponseFileArrayTest() {
        Response response = new Response(outputFile);
        Assert.assertNull(response.getArrayData());
    }

    @Test
    public void checkNewResponseFileMessageTest() {
        Response response = new Response(
                new OutputFile("directory", "file", new Browser(BrowserName.ANDROID), null, null, null, null, null,
                        null));
        Assert.assertNull(response.getMessage());
    }

    @Test
    public void checkNewResponseCodeCodeTest() {
        Response response = new Response(5);
        Assert.assertEquals(response.getCode(), 5);
    }

    @Test
    public void checkNewResponseCodeObjectTest() {
        Response response = new Response(5);
        Assert.assertNull(response.getObjectData());
    }

    @Test
    public void checkNewResponseCodeArrayTest() {
        Response response = new Response(5);
        Assert.assertNull(response.getArrayData());
    }

    @Test
    public void checkNewResponseCodeMessageTest() {
        Response response = new Response(5);
        Assert.assertNull(response.getMessage());
    }

    @Test
    public void checkNewResponseObjectCodeTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getCode(), 5);
    }

    @Test
    public void checkNewResponseObjectObjectTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getObjectData(), json);
    }

    @Test
    public void checkNewResponseObjectArrayTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(5, json, "");
        Assert.assertNull(response.getArrayData());
    }

    @Test
    public void checkNewResponseObjectMessageTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getMessage(), "");
    }

    @Test
    public void checkNewResponseArrayCodeTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getCode(), 5);
    }

    @Test
    public void checkNewResponseArrayObjectTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(5, json, "");
        Assert.assertNull(response.getObjectData());
    }

    @Test
    public void checkNewResponseArrayArrayTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getArrayData(), json);
    }

    @Test
    public void checkNewResponseArrayMessageTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getMessage(), "");
    }

    @Test
    public void checkSetObjectTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getObjectData(), json);
        json.addProperty("name", "john");
        response.setObjectData(json);
        Assert.assertEquals(response.getObjectData(), json);
    }

    @Test
    public void checkSetArrayTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(5, json, "");
        Assert.assertEquals(response.getArrayData(), json);
        json.add("name");
        response.setArrayData(json);
        Assert.assertEquals(response.getArrayData(), json);
    }

    @Test
    public void checkSetMessageTest() {
        JsonArray json = new JsonArray();
        String message = "";
        Response response = new Response(5, json, message);
        Assert.assertEquals(response.getMessage(), message);
        String newMessage = "hello world";
        response.setMessage(newMessage);
        Assert.assertEquals(response.getMessage(), newMessage);
    }

    @Test
    public void checkSetFileTest() {
        Response response = new Response(outputFile);
        response.setOutputFile(outputFile);
        // just ensure no errors are thrown
    }

    @Test
    public void confirmEqualsCodePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        response.assertEquals(5);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a response code of <b>5</b></td>\n    <td>Found a response code of <b>5</b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsCodeFailTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        response.assertEquals(6);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a response code of <b>6</b></td>\n    <td>Found a response code of <b>5</b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsObjectPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        response.assertEquals(json);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;" +
                        "\"john\"<br/>\\}</i></div></td>\n    <td>Found a response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsObjectFailTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        response.assertEquals(new JsonObject());
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response of: <div><i>\\{\\}</i></div></td>\n    <td>Found a response of: " +
                        "<div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsObjectNullTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        response.assertEquals(new JsonObject());
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response of: <div><i>\\{\\}</i></div></td>\n    <td>Found a response of: " +
                        "<div><i>\\[<br/>\\&nbsp;\\&nbsp;\"name\"<br/>\\]</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsArrayPassTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        response.assertEquals(json);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response of: <div><i>\\[<br/>\\&nbsp;\\&nbsp;\"name\"<br/>\\]</i></div></td>\n    <td>Found " +
                        "a response of: <div><i>\\[<br/>\\&nbsp;\\&nbsp;\"name\"<br/>\\]</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsArrayFailTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        response.assertEquals(new JsonArray());
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response of: <div><i>\\[\\]</i></div></td>\n    <td>Found a response of: " +
                        "<div><i>\\[<br/>\\&nbsp;\\&nbsp;\"name\"<br/>\\]</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmEqualsArrayNullTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        response.assertEquals(new JsonArray());
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response of: <div><i>\\[\\]</i></div></td>\n    <td>Found a response of: " +
                        "<div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsStringPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john");
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : john</div></i></div></td>\n    <td>Found a response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsStringFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : john</div></i></div></td>\n    <td>Found a response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsStringFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", "john");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", "john1");
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : john1</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsIntegerPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsIntegerFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsIntegerFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 6</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsDoublePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 5.5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5.5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsDoubleFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 5.5</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5.5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsDoubleFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 6.5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5.5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsFloatPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5.5f);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 5.5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5.5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsFloatFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5.5f);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 5.5</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5.5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsFloatFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5.5f);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6.5f);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 6.5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5.5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsLongPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5l);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5l);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 5</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsLongFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5l);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 5l);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 5</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsLongFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5l);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 6l);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 6</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;5<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsBooleanPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", true);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : true</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;true<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsBooleanFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", true);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : true</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;true<br/>\\}</i></div></td>\n " +
                        "   " + "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsBooleanFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", true);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", false);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : false</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;true<br/>\\}</i></div></td>\n " +
                        "   <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsBytePassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 0);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 0</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;0<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsByteFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", (byte) 0);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : 0</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;0<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsByteFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", (byte) 0);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", (byte) 1);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : 1</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;0<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsCharacterPassTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'a');
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : a</div></i></div></td>\n    <td>Found a response" +
                        " of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"a\"<br/>\\}</i></div></td>\n    " +
                        "<td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsCharacterFailsTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", 'a');
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : a</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"a\"<br/>\\}</i></div></td>\n" +
                        "    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsCharacterFails2Test() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 'a');
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 'b');
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : b</div></i></div></td>\n    <td>Found a " +
                        "response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\"a\"<br/>\\}</i></div></td>\n" +
                        "    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
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
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        response.assertContains(map);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : \\{\"first\":\"john\"," +
                        "\"last\":\"smith\"\\}</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\\{<br/>\\&nbsp;\\&nbsp;\\&nbsp;" +
                        "\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;\\}<br/>}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsJsonObjectFailTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        JsonObject badChild = new JsonObject();
        badChild.addProperty("first", "john");
        Map<String, Object> map = new HashMap();
        map.put("name", badChild);
        response.assertContains(map);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : \\{\"first\":\"john\"\\}</div></i></div></td>\n    " +
                        "<td>Found a response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\\{<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;\\}<br/>}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsJsonObjectFail2Test() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> map = new HashMap();
        map.put("name1", child);
        response.assertContains(map);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : \\{\"first\":\"john\"," +
                        "\"last\":\"smith\"\\}</div></i></div></td>\n    <td>Found a response of: " +
                        "<div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\\{<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;\\}<br/>}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
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
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        response.assertContains(map);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : \\[\"john\",\"smith\"\\]</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\\[<br/>\\&nbsp;\\&nbsp;\\&nbsp;" +
                        "\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;" +
                        "\\]<br/>}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsJsonArrayFailTest() throws IOException {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        JsonArray badChild = new JsonArray();
        badChild.add("john");
        Map<String, Object> map = new HashMap();
        map.put("name", badChild);
        response.assertContains(map);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name : \\[\"john\"\\]</div></i></div></td>\n    <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\\[<br/>\\&nbsp;\\&nbsp;\\&nbsp;" +
                        "\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;" +
                        "\\]<br/>}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n" +
                        "   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsJsonArrayFail2Test() throws IOException {
        JsonArray child = new JsonArray();
        child.add("john");
        child.add("smith");
        JsonObject json = new JsonObject();
        json.add("name", child);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> map = new HashMap();
        map.put("name1", child);
        response.assertContains(map);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : \\[\"john\",\"smith\"\\]</div></i></div></td>\n   " +
                        " <td>Found a response " +
                        "of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"name\":\\&nbsp;\\[<br/>\\&nbsp;\\&nbsp;\\&nbsp;" +
                        "\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;" +
                        "\\]<br/>}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n" +
                        "   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsNullTest() throws IOException {
        JsonArray json = new JsonArray();
        json.add("name");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name1", "john");
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing: <div><i><div>name1 : john</div></i></div></td>\n    <td>Found a response of: <div><i>\\[<br/>\\&nbsp;\\&nbsp;\"name\"<br/>\\]</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsPairsMismatchTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("name", 5l);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("name", 5);
        response.assertContains(pairs);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n" + "    <td align='center'>1.</td>\n" + "    <td> </td>\n" +
                        "    <td>Expected to find a response containing: <div><i><div>name : 5</div></i></div></td>\n" +
                        "    <td>Found a response of: <div><i>\\{<br/>&nbsp;&nbsp;\"name\":&nbsp;" +
                        "5<br/>\\}</i></div></td>\n" + "    <td>[0-9]+ms / [0-9]+ms</td>\n" +
                        "    <td class='pass'>Pass</td>\n" + "   </tr>\n"));
    }

    @Test
    public void confirmContainsArrayPassTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        response.assertContains(child);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing:<div><i>\\{<br/>\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\}</i></div></td>\n    <td>Found a response of: <div><i>\\[<br/>&nbsp;&nbsp;\\{<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;\\}<br/>\\]</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsArrayFailTest() throws IOException {
        JsonObject child = new JsonObject();
        child.addProperty("first", "john");
        child.addProperty("last", "smith");
        JsonArray json = new JsonArray();
        json.add(child);
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        JsonObject badChild = new JsonObject();
        badChild.addProperty("first", "john");
        response.assertContains(badChild);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing:<div><i>\\{<br/>\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\"<br/>\\}</i></div></td>\n    <td>Found a response of: <div><i>\\[<br/>&nbsp;&nbsp;\\{<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\&nbsp;\\&nbsp;\\}<br/>\\]</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void confirmContainsArrayNullTest() throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("first", "john");
        json.addProperty("last", "smith");
        Response response = new Response(5, json, "");
        response.setOutputFile(outputFile);
        response.assertContains(json);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>Expected to find a " +
                        "response containing:<div><i>\\{<br/>\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\}</i></div></td>\n    <td>Found a response of: <div><i>\\{<br/>\\&nbsp;\\&nbsp;\"first\":\\&nbsp;\"john\",<br/>\\&nbsp;\\&nbsp;\"last\":\\&nbsp;\"smith\"<br/>\\}</i></div></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }
}