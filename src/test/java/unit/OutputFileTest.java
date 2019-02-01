package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.OutputFile;
import com.coveros.selenified.OutputFile.Result;
import com.coveros.selenified.OutputFile.Success;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.services.Request;
import com.coveros.selenified.services.Response;
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

public class OutputFileTest {

    private OutputFile outputFile;
    private File directory;
    private File file;

    @BeforeMethod
    public void createFile() throws InvalidBrowserException {
        outputFile = new OutputFile("directory", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
                null);
        directory = new File("directory");
        file = new File("directory", "file.html");
    }

    @AfterMethod
    public void deleteFile() {
        file.delete();
        directory.delete();
    }

    @Test
    public void setupFileFreshTest() throws InvalidBrowserException {
        new OutputFile("somenewdir", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null, null);
        File file = new File("somenewdir", "file.html");
        assertTrue(file.exists());
        file.delete();
        new File("somenewdir").delete();
    }

    @Test
    public void setupFileTest() throws InvalidBrowserException {
        assertNotEquals(file.length(), 0);
        assertTrue(directory.exists());
        assertTrue(file.exists());

        // do it again, ensure nothing breaks when it already exists
        new OutputFile("directory", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null, null);
        assertNotEquals(file.length(), 0);
        assertTrue(directory.exists());
        assertTrue(file.exists());
    }

    @Test
    public void fileNameTest() {
        assertEquals(outputFile.getFileName(), "file");
    }

    @Test
    public void captureEntirePageScreenshotTest() {
        assertEquals(outputFile.captureEntirePageScreenshot(),
                "<br/><b><font class='fail'>No Screenshot Available</font></b>");
    }

    @Test
    public void addErrorTest() {
        assertEquals(outputFile.getErrors(), 0);
        outputFile.addError();
        assertEquals(outputFile.getErrors(), 1);
        outputFile.addError();
        assertEquals(outputFile.getErrors(), 2);
    }

    @Test
    public void addErrorsTest() {
        assertEquals(outputFile.getErrors(), 0);
        outputFile.addErrors(2);
        assertEquals(outputFile.getErrors(), 2);
        outputFile.addErrors(99999);
        assertEquals(outputFile.getErrors(), 100001);
    }

    @Test
    public void createOutputHeaderSuiteTest() throws IOException {
        new OutputFile("newdirectory", "file", new Capabilities(new Browser("Chrome")), null, "My Suite", null, null, null,
                null);
        File file = new File("newdirectory", "file.html");
        assertTrue(file.exists());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("My Suite"));
        file.delete();
        new File("newdirectory").delete();
    }

    @Test
    public void createOutputHeaderGroupTest() throws IOException {
        new OutputFile("newdirectory", "file", new Capabilities(new Browser("Chrome")), null, null, "My Group", null, null,
                null);
        File file = new File("newdirectory", "file.html");
        assertTrue(file.exists());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("My Group"));
        file.delete();
        new File("newdirectory").delete();
    }

    @Test
    public void createOutputHeaderAuthorTest() throws IOException {
        new OutputFile("newdirectory", "file", new Capabilities(new Browser("Chrome")), null, null, null, "My Author", null,
                null);
        File file = new File("newdirectory", "file.html");
        assertTrue(file.exists());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("My Author"));
        file.delete();
        new File("newdirectory").delete();
    }

    @Test
    public void createOutputHeaderVersionTest() throws IOException {
        new OutputFile("newdirectory", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, "My Version",
                null);
        File file = new File("newdirectory", "file.html");
        assertTrue(file.exists());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("My Version"));
        file.delete();
        new File("newdirectory").delete();
    }

    @Test
    public void createOutputHeaderObjectivesTest() throws IOException {
        new OutputFile("newdirectory", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
                "My Objectives");
        File file = new File("newdirectory", "file.html");
        assertTrue(file.exists());
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("My Objectives"));
        file.delete();
        new File("newdirectory").delete();
    }

    @Test
    public void loadInitialPageTest() throws IOException {
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertFalse(content.contains("The starting page"));
    }

    @Test
    public void recordActionSuccess() throws IOException {
        outputFile.recordAction("my action", "expected", "actual", Result.SUCCESS);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td class='success'>actual</td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionFailure() throws IOException {
        outputFile.recordAction("my action", "expected", "actual", Result.FAILURE);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td class='failure'>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionFailureSkipped() throws IOException {
        outputFile.recordAction("my action", "expected", "actual", Result.SKIPPED);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td class='skipped'>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='check'>Check</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionFailureWarning() throws IOException {
        outputFile.recordAction("my action", "expected", "actual", Result.WARNING);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td class='warning'>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='check'>Check</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionBadFile() throws InvalidBrowserException {
        OutputFile file =
                new OutputFile("/somenewdir", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
                        null);
        file.recordAction("my action", "expected", "actual", Result.WARNING);
        // we are just verifying that no errors were thrown
    }

    @Test
    public void recordExpected() throws IOException {
        outputFile.recordExpected("expected");
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(
                content.contains("   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>expected</td>\n"));
    }

    @Test
    public void recordExpectedBadFile() throws InvalidBrowserException {
        OutputFile file =
                new OutputFile("/somenewdir", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
                        null);
        file.recordExpected("expected");
        // we are just verifying that no errors were thrown
    }

    @Test
    public void recordActualPass() throws IOException {
        outputFile.recordActual("actual", Success.PASS);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+    <td>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void recordActualFail() throws IOException {
        outputFile.recordActual("actual", Success.FAIL);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+    <td>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void recordActualBadFile() throws InvalidBrowserException {
        OutputFile file =
                new OutputFile("/somenewdir", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
                        null);
        file.recordActual("actual", Success.FAIL);
        // we are just verifying that no errors were thrown
    }

    @Test
    public void endTestTemplateOutputFileTest() throws IOException {
        outputFile.finalizeOutputFile(1);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("  </table>\r\n </body>\r\n</html>\r\n"));
    }

    @Test
    public void endTestTemplateOutputFileNoErrorsPassTest() throws IOException {
        outputFile.finalizeOutputFile(1);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='pass'><b>SUCCESS</b></font>"));
    }

    @Test
    public void endTestTemplateOutputFileNoErrorsWarningTest() throws IOException {
        outputFile.finalizeOutputFile(0);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='warning'><b>WARNING</b></font>"));
    }

    @Test
    public void endTestTemplateOutputFileNoErrorsFailureTest() throws IOException {
        outputFile.finalizeOutputFile(2);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='warning'><b>FAILURE</b></font>"));
    }

    @Test
    public void endTestTemplateOutputFileNoErrorsSkipTest() throws IOException {
        outputFile.finalizeOutputFile(3);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='warning'><b>SKIPPED</b></font>"));
    }

    @Test
    public void endTestTemplateOutputFileErrorsTest() throws IOException {
        outputFile.addError();
        outputFile.finalizeOutputFile(0);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='fail'><b>FAILURE</b></font>"));
    }

    @Test
    public void endTestTemplateOutputFileLoggedTest() throws IOException {
        outputFile.recordActual("Something", Success.FAIL);
        outputFile.finalizeOutputFile(1);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='fail'><b>FAILURE</b></font>"));
    }

    @Test
    public void endTestTemplateOutputFileActualNotLoggedTest() throws IOException {
        outputFile.recordActual("Something", Success.PASS);
        outputFile.finalizeOutputFile(1);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='pass'><b>SUCCESS</b></font>"));
    }

    @Test
    public void endTestTemplateOutputFileActionNotLoggedTest() throws IOException {
        outputFile.recordAction("Something", "Something", "Something", Result.SUCCESS);
        outputFile.finalizeOutputFile(1);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='pass'><b>SUCCESS</b></font>"));
    }

    @Test
    public void endTestTemplateOutputFileActionNotLoggedWarningTest() throws IOException {
        outputFile.recordAction("Something", "Something", "Something", Result.WARNING);
        outputFile.finalizeOutputFile(1);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='pass'><b>SUCCESS</b></font>"));
    }

    @Test
    public void packageResultsTest() throws IOException {
        outputFile.finalizeOutputFile(1);
        assertFalse(new File(directory, file.getName() + "_RESULTS.zip").exists());
    }

    @Test
    public void packageResultsPositiveTest() throws IOException {
        OutputFile outputFile =
                new OutputFile("results", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null, null);
        File directory = new File("results");
        File file = new File("results", "file");

        System.setProperty("packageResults", "true");
        outputFile.finalizeOutputFile(1);
        System.clearProperty("packageResults");
        File results = new File("results", file.getName() + "_RESULTS.zip");
        assertTrue(results.exists());

        file.delete();
        results.delete();
        directory.delete();
    }

    @Test
    public void packageResultsNegativeTest() throws IOException {
        System.setProperty("packageResults", "false");
        outputFile.finalizeOutputFile(1);
        System.clearProperty("packageResults");
        assertFalse(new File(directory, file.getName() + "_RESULTS.zip").exists());
    }

    @Test
    public void outputRequestPropertiesNullTest() {
        assertEquals(outputFile.outputRequestProperties(null, null), "");
    }

    @Test
    public void outputRequestPropertiesNullNullTest() {
        Request request = new Request();
        assertEquals(outputFile.outputRequestProperties(request, null), "");
    }

    @Test
    public void outputRequestPropertiesNullNullFileTest() {
        assertEquals(outputFile.outputRequestProperties(null, new File("Jenkinsfile")),
                "<div>&nbsp;with&nbsp;file:&nbsp;<i>" + System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesNullNullBadFileTest() {
        assertEquals(outputFile.outputRequestProperties(null, new File("Jenkinsfi")),
                "<div>&nbsp;with&nbsp;file:&nbsp;<i>" + System.getProperty("user.dir") + "/Jenkinsfi</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyJsonObjectTest() {
        Request request = new Request().setJsonPayload(new JsonObject());
        assertEquals(outputFile.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{}</i></div>");
    }

    @Test
    public void outputRequestPropertiesJsonObjectTest() {
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        Request request = new Request().setJsonPayload(json);
        assertEquals(outputFile.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyJsonArrayTest() {
        Request request = new Request().setJsonPayload(new JsonArray());
        assertEquals(outputFile.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>[]</i></div>");
    }

    @Test
    public void outputRequestPropertiesJsonArrayTest() {
        JsonArray json = new JsonArray();
        json.add("hello");
        json.add("world");
        Request request = new Request().setJsonPayload(json);
        assertEquals(outputFile.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>[<br/>&nbsp;&nbsp;\"hello\",<br/>&nbsp;&nbsp;" +
                        "\"world\"<br/>]</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyMultipartTest() {
        Request request = new Request().setMultipartData(new HashMap<>());
        assertEquals(outputFile.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i></i></div>");
    }

    @Test
    public void outputRequestPropertiesMultipartTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setMultipartData(map);
        assertEquals(outputFile.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i><div>hello&nbsp;:&nbsp;world</div></i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyParamsTest() {
        Request request = new Request().setUrlParams(new HashMap<>());
        assertEquals(outputFile.outputRequestProperties(request, null), "");
    }

    @Test
    public void outputRequestPropertiesParamsTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        assertEquals(outputFile.outputRequestProperties(request, null), "");
    }

    @Test
    public void outputRequestPropertiesBothObjectTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        request.setJsonPayload(json);
        assertEquals(outputFile.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div>");
    }

    @Test
    public void outputRequestPropertiesBothArrayTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        JsonArray json = new JsonArray();
        json.add("hello");
        json.add("world");
        request.setJsonPayload(json);
        assertEquals(outputFile.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>[<br/>&nbsp;&nbsp;\"hello\",<br/>&nbsp;&nbsp;" +
                        "\"world\"<br/>]</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyJsonObjectAndFileTest() {
        Request request = new Request().setJsonPayload(new JsonObject());
        assertEquals(outputFile.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{}</i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesJsonObjectAndFileTest() {
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        Request request = new Request().setJsonPayload(json);
        assertEquals(outputFile.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i>" +
                        "</div><div>&nbsp;with&nbsp;file:&nbsp;<i>" + System.getProperty("user.dir") +
                        "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyJsonArrayAndFileTest() {
        Request request = new Request().setJsonPayload(new JsonArray());
        assertEquals(outputFile.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>[]</i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesJsonArrayAndFileTest() {
        JsonArray json = new JsonArray();
        json.add("hello");
        json.add("world");
        Request request = new Request().setJsonPayload(json);
        assertEquals(outputFile.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>[<br/>&nbsp;&nbsp;\"hello\",<br/>&nbsp;&nbsp;" +
                        "\"world\"<br/>]</i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyMultipartAndFileTest() {
        Request request = new Request().setMultipartData(new HashMap<>());
        assertEquals(outputFile.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i></i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesMultipartAndFileTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setMultipartData(map);
        assertEquals(outputFile.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i><div>hello&nbsp;:&nbsp;world</div></i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyParamsAndFileTest() {
        Request request = new Request().setUrlParams(new HashMap<>());
        assertEquals(outputFile.outputRequestProperties(request, new File("Jenkinsfile")),
                "<div>&nbsp;with&nbsp;file:&nbsp;<i>" + System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesParamsAndFileTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        assertEquals(outputFile.outputRequestProperties(request, new File("Jenkinsfile")),
                "<div>&nbsp;with&nbsp;file:&nbsp;<i>" + System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesBothObjectAndFileTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        request.setJsonPayload(json);
        assertEquals(outputFile.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesBothArrayAndFileTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        JsonArray json = new JsonArray();
        json.add("hello");
        json.add("world");
        request.setJsonPayload(json);
        assertEquals(outputFile.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>[<br/>&nbsp;&nbsp;\"hello\",<br/>&nbsp;&nbsp;" +
                        "\"world\"<br/>]</i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void formatResponseNullTest() {
        assertEquals(outputFile.formatResponse(null), "");
    }

    @Test
    public void formatResponseNullNullTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(0, json, null);
        response.setObjectData(null);
        assertEquals(outputFile.formatResponse(response), "");
    }

    @Test
    public void formatResponseEmptyObjectTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(0, json, null);
        assertEquals(outputFile.formatResponse(response), "<div><i>{}</i></div>");
    }

    @Test
    public void formatRequestObjectTest() {
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        Response response = new Response(0, json, null);
        assertEquals(outputFile.formatResponse(response),
                "<div><i>{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div>");
    }

    @Test
    public void formatResponseEmptyArrayTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(0, json, null);
        assertEquals(outputFile.formatResponse(response), "<div><i>[]</i></div>");
    }

    @Test
    public void formatResponseArrayTest() {
        JsonArray json = new JsonArray();
        json.add("world");
        Response response = new Response(0, json, null);
        assertEquals(outputFile.formatResponse(response), "<div><i>[<br/>&nbsp;&nbsp;\"world\"<br/>]</i></div>");
    }

    @Test
    public void formatResponseBothTest() {
        JsonArray array = new JsonArray();
        array.add("world");
        Response response = new Response(0, array, null);
        JsonObject object = new JsonObject();
        object.addProperty("hello", "world");
        response.setObjectData(object);
        assertEquals(outputFile.formatResponse(response),
                "<div><i>[<br/>&nbsp;&nbsp;\"world\"<br/>]{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div>");
    }

    @Test
    public void formatHTMLNullTest() {
        assertEquals(outputFile.formatHTML(null), "");
    }

    @Test
    public void formatHTMLEmptyTest() {
        assertEquals(outputFile.formatHTML(""), "");
    }

    @Test
    public void formatHTMLNewlineTest() {
        assertEquals(outputFile.formatHTML("\n"), "<br/>");
    }

    @Test
    public void formatHTMLSpaceTest() {
        assertEquals(outputFile.formatHTML(" "), "&nbsp;");
    }

    @Test
    public void formatHTMLFullTest() {
        assertEquals(outputFile.formatHTML("hello world\nhello world"), "hello&nbsp;world<br/>hello&nbsp;world");
    }
}