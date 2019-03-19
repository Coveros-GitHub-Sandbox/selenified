package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.services.Request;
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

public class ReporterTest {

    private Reporter reporter;
    private File directory;
    private File file;

    @BeforeMethod
    public void createFile() throws InvalidBrowserException {
        reporter = new Reporter("directory", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
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
        new Reporter("somenewdir", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null, null);
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
        new Reporter("directory", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null, null);
        assertNotEquals(file.length(), 0);
        assertTrue(directory.exists());
        assertTrue(file.exists());
    }

    @Test
    public void fileNameTest() {
        assertEquals(reporter.getFileName(), "file");
    }

    @Test
    public void captureEntirePageScreenshotTest() {
        assertEquals(reporter.captureEntirePageScreenshot(),
                "<br/><b><font class='fail'>No Screenshot Available</font></b>");
    }

    @Test
    public void createOutputHeaderSuiteTest() throws IOException {
        new Reporter("newdirectory", "file", new Capabilities(new Browser("Chrome")), null, "My Suite", null, null, null,
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
        new Reporter("newdirectory", "file", new Capabilities(new Browser("Chrome")), null, null, "My Group", null, null,
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
        new Reporter("newdirectory", "file", new Capabilities(new Browser("Chrome")), null, null, null, "My Author", null,
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
        new Reporter("newdirectory", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, "My Version",
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
        new Reporter("newdirectory", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
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
        reporter.pass("my action", "expected", "actual");
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td>actual</td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionFailure() throws IOException {
        reporter.fail("my action", "expected", "actual");
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionFailureCheck() throws IOException {
        reporter.check("my action", "expected", "actual");
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='check'>CHECK</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionBadFile() throws InvalidBrowserException {
        Reporter file =
                new Reporter("/somenewdir", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
                        null);
        file.check("my action", "expected", "actual");
        // we are just verifying that no errors were thrown
    }

    @Test
    public void endTestTemplateReporterTest() throws IOException {
        reporter.finalizeReporter(0);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("  </table>\r\n </body>\r\n</html>\r\n"));
    }

    @Test
    public void endTestTemplateReporterNoErrorsPassTest() throws IOException {
        reporter.finalizeReporter(0);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='pass'><b>PASS</b></font>"));
    }

    @Test
    public void endTestTemplateReporterNoErrorsWarningTest() throws IOException {
        reporter.finalizeReporter(1);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='check'><b>CHECK</b></font>"));
    }

    @Test
    public void endTestTemplateReporterNoErrorsFailureTest() throws IOException {
        reporter.finalizeReporter(2);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='check'><b>CHECK</b></font>"));
    }

    @Test
    public void endTestTemplateReporterActionFailTest() throws IOException {
        reporter.fail("Something", "Something", "Something");
        reporter.finalizeReporter(0);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='fail'><b>FAIL</b></font>"));
    }

    @Test
    public void endTestTemplateReporterActionNotLoggedTest() throws IOException {
        reporter.pass("Something", "Something", "Something");
        reporter.finalizeReporter(0);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='pass'><b>PASS</b></font>"));
    }

    @Test
    public void endTestTemplateReporterActionNotLoggedWarningTest() throws IOException {
        reporter.check("Something", "Something", "Something");
        reporter.finalizeReporter(0);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.contains("<font size='+2' class='check'><b>CHECK</b></font>"));
    }

    @Test
    public void packageResultsTest() throws IOException {
        reporter.finalizeReporter(1);
        assertFalse(new File(directory, file.getName() + "_RESULTS.zip").exists());
    }

    @Test
    public void packageResultsPositiveTest() throws IOException {
        String packageResults = null;
        if (System.getProperty("packageResults") != null) {
            packageResults = System.getProperty("packageResults");
        }
        Reporter reporter =
                new Reporter("results", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null, null);
        File directory = new File("results");
        File file = new File("results", "file");

        System.setProperty("packageResults", "true");
        reporter.finalizeReporter(1);
        File results = new File("results", file.getName() + "_RESULTS.zip");
        assertTrue(results.exists());

        file.delete();
        results.delete();
        directory.delete();
        System.clearProperty("packageResults");
        if (packageResults != null) {
            System.setProperty("packageResults", packageResults);
        }
    }

    @Test
    public void packageResultsNegativeTest() {
        String packageResults = null;
        if (System.getProperty("packageResults") != null) {
            packageResults = System.getProperty("packageResults");
        }
        System.setProperty("packageResults", "false");
        reporter.finalizeReporter(1);
        assertFalse(new File(directory, file.getName() + "_RESULTS.zip").exists());
        System.clearProperty("packageResults");
        if (packageResults != null) {
            System.setProperty("packageResults", packageResults);
        }
    }

    @Test
    public void outputRequestPropertiesNullTest() {
        assertEquals(reporter.outputRequestProperties(null, null), "");
    }

    @Test
    public void outputRequestPropertiesNullNullTest() {
        Request request = new Request();
        assertEquals(reporter.outputRequestProperties(request, null), "");
    }

    @Test
    public void outputRequestPropertiesNullNullFileTest() {
        assertEquals(reporter.outputRequestProperties(null, new File("Jenkinsfile")),
                "<div>&nbsp;with&nbsp;file:&nbsp;<i>" + System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesNullNullBadFileTest() {
        assertEquals(reporter.outputRequestProperties(null, new File("Jenkinsfi")),
                "<div>&nbsp;with&nbsp;file:&nbsp;<i>" + System.getProperty("user.dir") + "/Jenkinsfi</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyJsonObjectTest() {
        Request request = new Request().setJsonPayload(new JsonObject());
        assertEquals(reporter.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{}</i></div>");
    }

    @Test
    public void outputRequestPropertiesJsonObjectTest() {
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        Request request = new Request().setJsonPayload(json);
        assertEquals(reporter.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyJsonArrayTest() {
        Request request = new Request().setJsonPayload(new JsonArray());
        assertEquals(reporter.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>[]</i></div>");
    }

    @Test
    public void outputRequestPropertiesJsonArrayTest() {
        JsonArray json = new JsonArray();
        json.add("hello");
        json.add("world");
        Request request = new Request().setJsonPayload(json);
        assertEquals(reporter.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>[<br/>&nbsp;&nbsp;\"hello\",<br/>&nbsp;&nbsp;" +
                        "\"world\"<br/>]</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyMultipartTest() {
        Request request = new Request().setMultipartData(new HashMap<>());
        assertEquals(reporter.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i></i></div>");
    }

    @Test
    public void outputRequestPropertiesMultipartTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setMultipartData(map);
        assertEquals(reporter.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i><div>hello&nbsp;:&nbsp;world</div></i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyParamsTest() {
        Request request = new Request().setUrlParams(new HashMap<>());
        assertEquals(reporter.outputRequestProperties(request, null), "");
    }

    @Test
    public void outputRequestPropertiesParamsTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        assertEquals(reporter.outputRequestProperties(request, null), "");
    }

    @Test
    public void outputRequestPropertiesBothObjectTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        request.setJsonPayload(json);
        assertEquals(reporter.outputRequestProperties(request, null),
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
        assertEquals(reporter.outputRequestProperties(request, null),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>[<br/>&nbsp;&nbsp;\"hello\",<br/>&nbsp;&nbsp;" +
                        "\"world\"<br/>]</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyJsonObjectAndFileTest() {
        Request request = new Request().setJsonPayload(new JsonObject());
        assertEquals(reporter.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{}</i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesJsonObjectAndFileTest() {
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        Request request = new Request().setJsonPayload(json);
        assertEquals(reporter.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i>" +
                        "</div><div>&nbsp;with&nbsp;file:&nbsp;<i>" + System.getProperty("user.dir") +
                        "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyJsonArrayAndFileTest() {
        Request request = new Request().setJsonPayload(new JsonArray());
        assertEquals(reporter.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>[]</i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesJsonArrayAndFileTest() {
        JsonArray json = new JsonArray();
        json.add("hello");
        json.add("world");
        Request request = new Request().setJsonPayload(json);
        assertEquals(reporter.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>[<br/>&nbsp;&nbsp;\"hello\",<br/>&nbsp;&nbsp;" +
                        "\"world\"<br/>]</i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyMultipartAndFileTest() {
        Request request = new Request().setMultipartData(new HashMap<>());
        assertEquals(reporter.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i></i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesMultipartAndFileTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setMultipartData(map);
        assertEquals(reporter.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i><div>hello&nbsp;:&nbsp;world</div></i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyParamsAndFileTest() {
        Request request = new Request().setUrlParams(new HashMap<>());
        assertEquals(reporter.outputRequestProperties(request, new File("Jenkinsfile")),
                "<div>&nbsp;with&nbsp;file:&nbsp;<i>" + System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void outputRequestPropertiesParamsAndFileTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        assertEquals(reporter.outputRequestProperties(request, new File("Jenkinsfile")),
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
        assertEquals(reporter.outputRequestProperties(request, new File("Jenkinsfile")),
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
        assertEquals(reporter.outputRequestProperties(request, new File("Jenkinsfile")),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>[<br/>&nbsp;&nbsp;\"hello\",<br/>&nbsp;&nbsp;" +
                        "\"world\"<br/>]</i></div><div>&nbsp;with&nbsp;file:&nbsp;<i>" +
                        System.getProperty("user.dir") + "/Jenkinsfile</i></div>");
    }

    @Test
    public void formatResponseNullTest() {
        assertEquals(reporter.formatResponse(null), "");
    }

    @Test
    public void formatResponseNullNullTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(0, json, null);
        response.setObjectData(null);
        assertEquals(reporter.formatResponse(response), "");
    }

    @Test
    public void formatResponseEmptyObjectTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(0, json, null);
        assertEquals(reporter.formatResponse(response), "<div><i>{}</i></div>");
    }

    @Test
    public void formatRequestObjectTest() {
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        Response response = new Response(0, json, null);
        assertEquals(reporter.formatResponse(response),
                "<div><i>{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div>");
    }

    @Test
    public void formatResponseEmptyArrayTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(0, json, null);
        assertEquals(reporter.formatResponse(response), "<div><i>[]</i></div>");
    }

    @Test
    public void formatResponseArrayTest() {
        JsonArray json = new JsonArray();
        json.add("world");
        Response response = new Response(0, json, null);
        assertEquals(reporter.formatResponse(response), "<div><i>[<br/>&nbsp;&nbsp;\"world\"<br/>]</i></div>");
    }

    @Test
    public void formatResponseBothTest() {
        JsonArray array = new JsonArray();
        array.add("world");
        Response response = new Response(0, array, null);
        JsonObject object = new JsonObject();
        object.addProperty("hello", "world");
        response.setObjectData(object);
        assertEquals(reporter.formatResponse(response),
                "<div><i>[<br/>&nbsp;&nbsp;\"world\"<br/>]{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div>");
    }

    @Test
    public void formatHTMLNullTest() {
        assertEquals(reporter.formatHTML(null), "");
    }

    @Test
    public void formatHTMLEmptyTest() {
        assertEquals(reporter.formatHTML(""), "");
    }

    @Test
    public void formatHTMLNewlineTest() {
        assertEquals(reporter.formatHTML("\n"), "<br/>");
    }

    @Test
    public void formatHTMLSpaceTest() {
        assertEquals(reporter.formatHTML(" "), "&nbsp;");
    }

    @Test
    public void formatHTMLFullTest() {
        assertEquals(reporter.formatHTML("hello world\nhello world"), "hello&nbsp;world<br/>hello&nbsp;world");
    }

    @Test
    public void generatePDFTest() throws InvalidBrowserException {
        String generatePDF = null;
        if (System.getProperty("generatePDF") != null) {
            generatePDF = System.getProperty("generatePDF");
        }
        Reporter reporter =
                new Reporter("results", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null, null);
        File directory = new File("results");
        File file = new File("results", "file");

        System.setProperty("generatePDF", "true");
        reporter.finalizeReporter(1);
        File results = new File("results", file.getName() + ".pdf");
        assertTrue(results.exists());

        file.delete();
        results.delete();
        directory.delete();
        System.clearProperty("generatePDF");
        if (generatePDF != null) {
            System.setProperty("generatePDF", generatePDF);
        }
    }
}