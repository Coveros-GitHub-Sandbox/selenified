package unit;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.exceptions.*;
import com.coveros.selenified.services.Call;
import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.services.Request;
import com.coveros.selenified.services.Response;
import com.coveros.selenified.utilities.Reporter;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.coveros.selenified.utilities.Property.GENERATE_PDF;
import static com.coveros.selenified.utilities.Property.PACKAGE_RESULTS;
import static org.testng.Assert.*;

public class ReporterTest {

    private Reporter reporter;
    private File directory;
    private File file;
    private HTTP http;

    @BeforeMethod(alwaysRun = true)
    public void createFile() throws InvalidBrowserException, InvalidProxyException {
        reporter = new Reporter("directory", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
                null);
        directory = new File("directory");
        file = new File("directory", "file.html");
        http = new HTTP(reporter, "SomeURL");
    }

    @AfterMethod
    public void deleteFile() {
        file.delete();
        directory.delete();
    }

    @Test
    public void constructorNullTest() throws InvalidBrowserException, InvalidProxyException {
        // just verify no errors are thrown
        new Reporter(null, null, null, null, null, null, null, null, null);
    }

    @Test
    public void setupFileFreshTest() throws InvalidBrowserException, InvalidProxyException {
        new Reporter("somenewdir", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null, null);
        File file = new File("somenewdir", "file.html");
        assertTrue(file.exists());
        file.delete();
        new File("somenewdir").delete();
    }

    @Test
    public void setupFileTest() throws InvalidBrowserException, InvalidProxyException {
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
        new Reporter("newdirectory", "file", new Capabilities(new Browser("Chrome")), null, null, Arrays.asList("My Group"), null, null,
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
    public void recordScreenshot() throws IOException {
        reporter.recordScreenshot();
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td></td>\n    <td><br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='check'>CHECK</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionPass() throws IOException {
        reporter.pass("my check", 0, "actual", 0);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected my check</td>\n    <td>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionPassWait() throws IOException {
        reporter.pass("my check", 5, "actual", 2);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>Waiting up to 5.0 seconds my check</td>\n    <td>Expected my check</td>\n    <td>After waiting for 2.0 seconds, actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>PASS</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionFail() throws IOException {
        reporter.fail("my check", 0, "actual", 0);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td></td>\n    <td>Expected my check</td>\n    <td>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionFailWait() throws IOException {
        reporter.fail("my check", 5, "actual", 2);
        assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>Waiting up to 5.0 seconds my check</td>\n    <td>Expected my check</td>\n    <td>After waiting for 2.0 seconds, actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>FAIL</td>\n   </tr>\n"));
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
    public void recordActionBadFile() throws InvalidBrowserException, InvalidProxyException {
        Reporter reporter =
                new Reporter("/somenewdir", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null,
                        null);
        reporter.check("my action", "expected", "actual");
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
    public void packageResultsTest() {
        reporter.finalizeReporter(1);
        assertFalse(new File(directory, file.getName() + "_RESULTS.zip").exists());
    }

    @Test
    public void packageResultsPositiveTest() throws IOException {
        Reporter reporter =
                new Reporter("results", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null, null);
        File directory = new File("results");
        File file = new File("results", "file");

        System.setProperty(PACKAGE_RESULTS, "true");
        reporter.finalizeReporter(1);
        File results = new File("results", file.getName() + "_RESULTS.zip");
        assertTrue(results.exists());

        file.delete();
        results.delete();
        directory.delete();
    }

    @Test
    public void packageResultsNegativeTest() {
        System.setProperty(PACKAGE_RESULTS, "false");
        reporter.finalizeReporter(1);
        assertFalse(new File(directory, file.getName() + "_RESULTS.zip").exists());
    }

    @Test
    public void outputRequestPropertiesNullTest() {
        assertEquals(Reporter.getRequestPayloadOutput(null, null), "");
    }

    @Test
    public void outputRequestPropertiesNullNullTest() {
        Request request = new Request();
        assertEquals(Reporter.getRequestPayloadOutput(request, null), "");
    }

    @Test
    public void outputRequestPropertiesNullNullFileTest() {
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(null, new File("Jenkinsfile"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfile'>Jenkinsfile</a></div></span>"));
    }

    @Test
    public void outputRequestPropertiesNullNullBadFileTest() {
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(null, new File("Jenkinsfi"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfi'>Jenkinsfi</a></div></span>"));
    }

    @Test
    public void outputRequestPropertiesEmptyJsonObjectTest() {
        Request request = new Request().setJsonPayload(new JsonObject());
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, null);
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>\\{\\}</div></span>"));

    }

    @Test
    public void outputRequestPropertiesJsonObjectTest() {
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        Request request = new Request().setJsonPayload(json);
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, null);
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>\\{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>\\}</div></span>"));
    }

    @Test
    public void outputRequestPropertiesEmptyJsonArrayTest() {
        Request request = new Request().setJsonPayload(new JsonArray());
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, null);
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>\\[\\]</div></span>"));
    }

    @Test
    public void outputRequestPropertiesJsonArrayTest() {
        JsonArray json = new JsonArray();
        json.add("hello");
        json.add("world");
        Request request = new Request().setJsonPayload(json);
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, null);
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>\\[<br/>&nbsp;&nbsp;\"hello\",<br/>&nbsp;&nbsp;\"world\"<br/>\\]</div></span>"));
    }

    @Test
    public void outputRequestPropertiesEmptyMultipartTest() {
        Request request = new Request().setMultipartData(new HashMap<>());
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, null);
        assertEquals(requestPayloadOutput, "");
    }

    @Test
    public void outputRequestPropertiesMultipartTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setMultipartData(map);
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, new File("Jenkinsfile"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>hello : world</div><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfile'>Jenkinsfile</a></div></span>"));
    }

    @Test
    public void outputRequestPropertiesEmptyParamsTest() {
        Request request = new Request().setUrlParams(new HashMap<>());
        assertEquals(Reporter.getRequestPayloadOutput(request, null), "");
    }

    @Test
    public void outputRequestPropertiesParamsTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        assertEquals(Reporter.getRequestPayloadOutput(request, null), "");
    }

    @Test
    public void outputRequestPropertiesBothObjectTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        request.setJsonPayload(json);
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, null);
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>\\{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>\\}</div></span>"));
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
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, null);
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>\\[<br/>&nbsp;&nbsp;\"hello\",<br/>&nbsp;&nbsp;\"world\"<br/>\\]</div></span>"));
    }

    @Test
    public void outputRequestPropertiesEmptyJsonObjectAndFileTest() {
        Request request = new Request().setJsonPayload(new JsonObject());
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, new File("Jenkinsfile"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>\\{\\}</div><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfile'>Jenkinsfile</a></div></span>"));
    }

    @Test
    public void outputRequestPropertiesJsonObjectAndFileTest() {
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        Request request = new Request().setJsonPayload(json);
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, new File("Jenkinsfile"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>\\{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>\\}</div><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfile'>Jenkinsfile</a></div></span>"));
    }

    @Test
    public void outputRequestPropertiesEmptyJsonArrayAndFileTest() {
        Request request = new Request().setJsonPayload(new JsonArray());
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, new File("Jenkinsfile"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>\\[\\]</div><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfile'>Jenkinsfile</a></div></span>"));
    }

    @Test
    public void outputRequestPropertiesJsonArrayAndFileTest() {
        JsonArray json = new JsonArray();
        json.add("hello");
        json.add("world");
        Request request = new Request().setJsonPayload(json);
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, new File("Jenkinsfile"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>\\[<br/>&nbsp;&nbsp;\"hello\",<br/>&nbsp;&nbsp;\"world\"<br/>\\]</div><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfile'>Jenkinsfile</a></div></span>"));
    }

    @Test
    public void outputRequestPropertiesEmptyMultipartAndFileTest() {
        Request request = new Request().setMultipartData(new HashMap<>());
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, new File("Jenkinsfile"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfile'>Jenkinsfile</a></div></span>"));
    }

    @Test
    public void outputRequestPropertiesMultipartAndFileTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setMultipartData(map);
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, new File("Jenkinsfile"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>hello : world</div><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfile'>Jenkinsfile</a></div></span>"));
    }

    @Test
    public void outputRequestPropertiesEmptyParamsAndFileTest() {
        Request request = new Request().setUrlParams(new HashMap<>());
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, new File("Jenkinsfile"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfile'>Jenkinsfile</a></div></span>"));
    }

    @Test
    public void outputRequestPropertiesParamsAndFileTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, new File("Jenkinsfile"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfile'>Jenkinsfile</a></div></span>"));
    }

    @Test
    public void outputRequestPropertiesBothObjectAndFileTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        Request request = new Request().setUrlParams(map);
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        request.setJsonPayload(json);
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, new File("Jenkinsfile"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>\\{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>\\}</div><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfile'>Jenkinsfile</a></div></span>"));
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
        String requestPayloadOutput = Reporter.getRequestPayloadOutput(request, new File("Jenkinsfile"));
        assertTrue(requestPayloadOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Payload</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>\\[<br/>&nbsp;&nbsp;\"hello\",<br/>&nbsp;&nbsp;\"world\"<br/>\\]</div><div> with file: <a href='file:///" + System.getProperty("user.dir") + "/Jenkinsfile'>Jenkinsfile</a></div></span>"));
    }

    @Test
    public void formatResponseNullTest() {
        assertEquals(Reporter.formatResponse(null), "");
    }

    @Test
    public void formatResponseNullNullTest() {
        Response response = new Response(null, null, 0, null, null, null);
        assertEquals(Reporter.formatResponse(response), "");
    }

    @Test
    public void formatResponseEmptyObjectTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(null, null, 0, json, null, null);
        assertEquals(Reporter.formatResponse(response), "<div><i>{}</i></div>");
    }

    @Test
    public void formatRequestObjectTest() {
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        Response response = new Response(null, null, 0, json, null, null);
        assertEquals(Reporter.formatResponse(response),
                "<div><i>{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div>");
    }

    @Test
    public void formatResponseEmptyArrayTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(null, null, 0, null, json, null);
        assertEquals(Reporter.formatResponse(response), "<div><i>[]</i></div>");
    }

    @Test
    public void formatResponseArrayTest() {
        JsonArray json = new JsonArray();
        json.add("world");
        Response response = new Response(null, null, 0, null, json, null);
        assertEquals(Reporter.formatResponse(response), "<div><i>[<br/>&nbsp;&nbsp;\"world\"<br/>]</i></div>");
    }

    @Test
    public void formatResponseBothTest() {
        JsonArray array = new JsonArray();
        array.add("world");
        JsonObject object = new JsonObject();
        object.addProperty("hello", "world");
        Response response = new Response(null, null, 0, object, array, null);
        assertEquals(Reporter.formatResponse(response),
                "<div><i>[<br/>&nbsp;&nbsp;\"world\"<br/>]{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div>");
    }

    @Test
    public void formatHTMLNullTest() {
        assertEquals(Reporter.formatHTML(null), "");
    }

    @Test
    public void formatHTMLEmptyTest() {
        assertEquals(Reporter.formatHTML(""), "");
    }

    @Test
    public void formatHTMLNewlineTest() {
        assertEquals(Reporter.formatHTML("\n"), "<br/>");
    }

    @Test
    public void formatHTMLSpaceTest() {
        assertEquals(Reporter.formatHTML(" "), "&nbsp;");
    }

    @Test
    public void formatHTMLFullTest() {
        assertEquals(Reporter.formatHTML("hello world\nhello world"), "hello&nbsp;world<br/>hello&nbsp;world");
    }

    @Test
    public void generatePDFTest() throws InvalidBrowserException, InvalidProxyException {
        Reporter reporter =
                new Reporter("results", "file", new Capabilities(new Browser("Chrome")), null, null, null, null, null, null);
        File directory = new File("results");
        File file = new File("results", "file");

        System.setProperty(GENERATE_PDF, "true");
        reporter.finalizeReporter(1);
        File results = new File("results", file.getName() + ".pdf");
        assertTrue(results.exists());

        file.delete();
        results.delete();
        directory.delete();
    }

    @DataProvider(name = "ordinals", parallel = true)
    public Object[][] DataSetOptions() {
        return new Object[][]{
                new Object[]{0, "0th"},
                new Object[]{1, "1st"},
                new Object[]{2, "2nd"},
                new Object[]{3, "3rd"},
                new Object[]{4, "4th"},
                new Object[]{5, "5th"},
                new Object[]{10, "10th"},
                new Object[]{11, "11th"},
                new Object[]{12, "12th"},
                new Object[]{13, "13th"},
                new Object[]{14, "14th"},
                new Object[]{20, "20th"},
                new Object[]{21, "21st"},
                new Object[]{22, "22nd"},
                new Object[]{23, "23rd"},
                new Object[]{24, "24th"},
                new Object[]{100, "100th"},
                new Object[]{101, "101st"},
                new Object[]{102, "102nd"},
                new Object[]{103, "103rd"},
                new Object[]{104, "104th"},
                new Object[]{111, "111th"},
                new Object[]{112, "112th"},
                new Object[]{113, "113th"},
                new Object[]{114, "114th"}
        };
    }

    @Test(dataProvider = "ordinals")
    public void ordinalTest(int number, String output) {
        assertEquals(Reporter.ordinal(number), output);
    }

    @Test
    public void formatKeyPairNullTest() {
        assertEquals(Reporter.formatKeyPair(null), "");
    }

    @Test
    public void formatKeyPairEmptyTest() {
        assertEquals(Reporter.formatKeyPair(new HashMap<String, Object>()), "");
    }

    @Test
    public void formatKeyPairSingleNullTest() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hello", null);
        assertEquals(Reporter.formatKeyPair(map), "<div>hello : null</div>");
    }

    @Test
    public void formatKeyPairSingleJsonTest() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hello", new JsonObject());
        assertEquals(Reporter.formatKeyPair(map), "<div>hello : {}</div>");
    }

    @Test
    public void formatKeyPairSingleTest() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hello", "world");
        assertEquals(Reporter.formatKeyPair(map), "<div>hello : world</div>");
    }

    @Test
    public void formatKeyPairDoubleTest() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hello", "world");
        map.put("john", "smith");
        assertEquals(Reporter.formatKeyPair(map), "<div>john : smith</div><div>hello : world</div>");
    }

    @Test
    public void appendCredentialsNull() {
        assertEquals(Reporter.getCredentialStringOutput(null), "");
    }

    @Test
    public void appendCredentialsEmpty() {
        assertEquals(Reporter.getCredentialStringOutput(http), "");
    }

    @Test
    public void appendCredentials() {
        HTTP http = new HTTP(reporter, "SomeURL", "User", "Pass");
        String credentialStringOutput = Reporter.getCredentialStringOutput(http);
    }

    @Test
    public void addCredentials() throws InvalidHTTPException, InvalidReporterException {
        HTTP http = new HTTP(reporter, "SomeURL");
        Call call = new Call(http, new HashMap<>());
        call.addCredentials("User", "Pass");
        String credentialStringOutput = Reporter.getCredentialStringOutput(http);
        assertTrue(credentialStringOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Credentials</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div><i>Username: User</div><div>Password: Pass</i></div></span>"));
    }

    @Test
    public void getRequestHeadersOutputNullTest() {
        assertEquals(Reporter.getRequestHeadersOutput(null), "");
    }

    @Test
    public void getRequestHeadersOutputDefaultTest() {
        String requestHeadersOutput = Reporter.getRequestHeadersOutput(http);
        assertTrue(requestHeadersOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Headers</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>Accept : application/json</div><div>Content-length : 0</div><div>Content-Type : application/json;&nbsp;charset=UTF-8</div></span>"));
    }

    @Test
    public void getUUIDTest() {
        assertTrue(Reporter.getUUID().matches("[0-9]{13}_[a-zA-Z0-9]{10}"));
    }

    @Test
    public void getResponseHeadersOutputNullTest() {
        assertEquals(Reporter.getResponseHeadersOutput(null), "");
    }

    @Test
    public void getResponseHeadersOutputEmptyTest() {
        Response response = new Response(reporter, null, 200, null, null, null);
        assertTrue(Reporter.getResponseHeadersOutput(response).matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Headers</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'></span>"));
    }

    @Test
    public void getResponseHeadersOutputTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("", "Response");
        map.put("hello", "world");
        Response response = new Response(reporter, map, 200, null, null, null);
        String responseHeadersOutput = Reporter.getResponseHeadersOutput(response);
        assertTrue(responseHeadersOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Headers</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div> : Response</div><div>hello : world</div></span>"));
    }

    @Test
    public void getResponseCodeOutputNullTest() {
        assertEquals(Reporter.getResponseCodeOutput(null), "");
    }

    @Test
    public void getResponseCodeOutputTest() {
        Response response = new Response(reporter, null, 200, null, null, null);
        String responseCodeOutput = Reporter.getResponseCodeOutput(response);
        assertTrue(responseCodeOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Response Status Code</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>200</div></span>"));
    }

    @Test
    public void getResponseOutputNullTest() {
        assertEquals(Reporter.getResponseOutput(null), "");
    }

    @Test
    public void getResponseOutputNullMessageTest() {
        Response response = new Response(reporter, null, 200, null, null, null);
        assertEquals(Reporter.getResponseOutput(response), "");
    }

    @Test
    public void getResponseOutputEmptyMessageTest() {
        Response response = new Response(reporter, null, 200, null, null, "");
        assertEquals(Reporter.getResponseOutput(response), "");
    }

    @Test
    public void getResponseOutputTest() {
        Response response = new Response(reporter, null, 200, null, null, "hello world");
        String responseOutput = Reporter.getResponseOutput(response);
        assertTrue(responseOutput.matches("<a href='javascript:void\\(0\\)' onclick='toggle\\(\"[0-9]{13}_[a-zA-Z0-9]{10}\"\\)'>Toggle Raw Response</a> <span id='[0-9]{13}_[a-zA-Z0-9]{10}' style='display:none;'><div>hello world</div></span>"));
    }

    @Test
    public void capitalizeFirstLettersTest() {
        assertEquals(Reporter.capitalizeFirstLetters(null), null);
        assertEquals(Reporter.capitalizeFirstLetters("hello world"), "Hello World");
        assertEquals(Reporter.capitalizeFirstLetters("helloWorld"), "HelloWorld");
        assertEquals(Reporter.capitalizeFirstLetters("hello_world"), "Hello_World");
        assertEquals(Reporter.capitalizeFirstLetters("123helloWorld"), "123HelloWorld");
        assertEquals(Reporter.capitalizeFirstLetters("hello123world"), "Hello123World");
        assertEquals(Reporter.capitalizeFirstLetters("helloWorld123"), "HelloWorld123");
    }
}