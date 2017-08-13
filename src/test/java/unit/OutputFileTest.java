package unit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coveros.selenified.selenium.Selenium.Browser;
import com.coveros.selenified.services.Request;
import com.coveros.selenified.services.Response;
import com.coveros.selenified.tools.OutputFile;
import com.coveros.selenified.tools.OutputFile.Result;
import com.coveros.selenified.tools.OutputFile.Success;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class OutputFileTest {

    private OutputFile outputFile;
    private File directory;
    private File file;

    @BeforeMethod
    public void createFile() {
        outputFile = new OutputFile("directory", "file", Browser.ANDROID, null, null, null, null, null, null);
        directory = new File("directory");
        file = new File("directory", "fileANDROID.html");
    }

    @AfterMethod
    public void deleteFile() {
        file.delete();
        directory.delete();
    }

    @Test
    public void setupFileFreshTest() {
        new OutputFile("/somenewdir", "file", Browser.ANDROID, null, null, null, null, null, null);
        Assert.assertFalse(new File("/somenewdir/fileAndroid.html").exists());
    }

    @Test
    public void setupFileTest() {
        Assert.assertNotEquals(file.length(), 0);
        Assert.assertTrue(directory.exists());
        Assert.assertTrue(file.exists());

        // do it again, ensure nothing breaks when it already exists
        new OutputFile("directory", "file", Browser.ANDROID, null, null, null, null, null, null);
        Assert.assertNotEquals(file.length(), 0);
        Assert.assertTrue(directory.exists());
        Assert.assertTrue(file.exists());
    }

    @Test
    public void fileNameTest() {
        Assert.assertEquals(outputFile.getFileName(), "fileANDROID.html");
    }

    @Test
    public void captureEntirePageScreenshotTest() {
        Assert.assertEquals(outputFile.captureEntirePageScreenshot(),
                "<br/><b><font class='fail'>No Screenshot Available</font></b>");
    }

    @Test
    public void addErrorTest() {
        Assert.assertEquals(outputFile.getErrors(), 0);
        outputFile.addError();
        Assert.assertEquals(outputFile.getErrors(), 1);
        outputFile.addError();
        Assert.assertEquals(outputFile.getErrors(), 2);
    }

    @Test
    public void addErrorsTest() {
        Assert.assertEquals(outputFile.getErrors(), 0);
        outputFile.addErrors(2);
        Assert.assertEquals(outputFile.getErrors(), 2);
        outputFile.addErrors(99999);
        Assert.assertEquals(outputFile.getErrors(), 100001);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void createOutputHeaderSuiteTest() throws IOException {
        new OutputFile("newdirectory", "file", Browser.ANDROID, null, "My Suite", null, null, null, null);
        File file = new File("newdirectory", "fileANDROID.html");
        Assert.assertTrue(file.exists());
        org.testng.Assert.assertTrue(FileUtils.readFileToString(file).contains("My Suite"));
        file.delete();
        new File("newdirectory").delete();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void createOutputHeaderGroupTest() throws IOException {
        new OutputFile("newdirectory", "file", Browser.ANDROID, null, null, "My Group", null, null, null);
        File file = new File("newdirectory", "fileANDROID.html");
        Assert.assertTrue(file.exists());
        org.testng.Assert.assertTrue(FileUtils.readFileToString(file).contains("My Group"));
        file.delete();
        new File("newdirectory").delete();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void createOutputHeaderAuthorTest() throws IOException {
        new OutputFile("newdirectory", "file", Browser.ANDROID, null, null, null, "My Author", null, null);
        File file = new File("newdirectory", "fileANDROID.html");
        Assert.assertTrue(file.exists());
        org.testng.Assert.assertTrue(FileUtils.readFileToString(file).contains("My Author"));
        file.delete();
        new File("newdirectory").delete();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void createOutputHeaderVersionTest() throws IOException {
        new OutputFile("newdirectory", "file", Browser.ANDROID, null, null, null, null, "My Version", null);
        File file = new File("newdirectory", "fileANDROID.html");
        Assert.assertTrue(file.exists());
        org.testng.Assert.assertTrue(FileUtils.readFileToString(file).contains("My Version"));
        file.delete();
        new File("newdirectory").delete();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void createOutputHeaderObjectivesTest() throws IOException {
        new OutputFile("newdirectory", "file", Browser.ANDROID, null, null, null, null, null, "My Objectives");
        File file = new File("newdirectory", "fileANDROID.html");
        Assert.assertTrue(file.exists());
        org.testng.Assert.assertTrue(FileUtils.readFileToString(file).contains("My Objectives"));
        file.delete();
        new File("newdirectory").delete();
    }

    @SuppressWarnings("deprecation")
    @Test
    public void loadInitialPageTest() throws IOException {
        Assert.assertNotEquals(file.length(), 0);
        org.testng.Assert.assertFalse(FileUtils.readFileToString(file).contains("The starting page"));
    }

    @Test
    public void recordActionSuccess() throws IOException {
        outputFile.recordAction("my action", "expected", "actual", Result.SUCCESS);
        Assert.assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td class='success'>actual</td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionFailure() throws IOException {
        outputFile.recordAction("my action", "expected", "actual", Result.FAILURE);
        Assert.assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td class='failure'>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionFailureSkipped() throws IOException {
        outputFile.recordAction("my action", "expected", "actual", Result.SKIPPED);
        Assert.assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td class='skipped'>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='check'>Check</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionFailureWarning() throws IOException {
        outputFile.recordAction("my action", "expected", "actual", Result.WARNING);
        Assert.assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td class='warning'>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='check'>Check</td>\n   </tr>\n"));
    }

    @Test
    public void recordActionBadFile() {
        OutputFile file = new OutputFile("/somenewdir", "file", Browser.ANDROID, null, null, null, null, null, null);
        file.recordAction("my action", "expected", "actual", Result.WARNING);
        // we are just verifying that no errors were thrown
    }

    @Test
    public void recordExpected() throws IOException {
        outputFile.recordExpected("expected");
        Assert.assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(
                content.contains("   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>expected</td>\n"));
    }

    @Test
    public void recordExpectedBadFile() {
        OutputFile file = new OutputFile("/somenewdir", "file", Browser.ANDROID, null, null, null, null, null, null);
        file.recordExpected("expected");
        // we are just verifying that no errors were thrown
    }

    @Test
    public void recordActualPass() throws IOException {
        outputFile.recordActual("actual", Success.PASS);
        Assert.assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+    <td>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
    }

    @Test
    public void recordActualFail() throws IOException {
        outputFile.recordActual("actual", Success.FAIL);
        Assert.assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.matches(
                "[.\\s\\S]+    <td>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
    }

    @Test
    public void recordActualBadFile() {
        OutputFile file = new OutputFile("/somenewdir", "file", Browser.ANDROID, null, null, null, null, null, null);
        file.recordActual("actual", Success.FAIL);
        // we are just verifying that no errors were thrown
    }

    @Test
    public void endTestTemplateOutputFileTest() throws IOException {
        outputFile.finalizeOutputFile();
        Assert.assertNotEquals(file.length(), 0);
        String content = Files.toString(file, Charsets.UTF_8);
        Assert.assertTrue(content.contains("  </table>\r\n </body>\r\n</html>\r\n"));
    }

    @Test
    public void packageResultsTest() throws IOException {
        outputFile.finalizeOutputFile();
        Assert.assertFalse(new File(directory, file.getName() + "_RESULTS.zip").exists());
    }

    @Test
    public void packageResultsPositiveTest() throws IOException {
        OutputFile outputFile = new OutputFile("results", "file", Browser.ANDROID, null, null, null, null, null, null);
        File directory = new File("results");
        File file = new File("results", "fileANDROID.html");

        System.setProperty("packageResults", "true");
        outputFile.finalizeOutputFile();
        System.clearProperty("packageResults");
        File results = new File("results", file.getName() + "_RESULTS.zip");
        Assert.assertTrue(results.exists());

        file.delete();
        results.delete();
        directory.delete();
    }

    @Test
    public void packageResultsNegativeTest() throws IOException {
        System.setProperty("packageResults", "false");
        outputFile.finalizeOutputFile();
        System.clearProperty("packageResults");
        Assert.assertFalse(new File(directory, file.getName() + "_RESULTS.zip").exists());
    }

    @Test
    public void outputRequestPropertiesNullTest() {
        Assert.assertEquals(outputFile.outputRequestProperties(null), "");
    }

    @Test
    public void outputRequestPropertiesNullNullTest() {
        Request request = new Request(new JsonObject());
        request.setData(null);
        Assert.assertEquals(outputFile.outputRequestProperties(request),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i></i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyDataTest() {
        Request request = new Request(new JsonObject());
        Assert.assertEquals(outputFile.outputRequestProperties(request),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{}</i></div>");
    }

    @Test
    public void outputRequestPropertiesDataTest() {
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        Request request = new Request(json);
        Assert.assertEquals(outputFile.outputRequestProperties(request),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div>");
    }

    @Test
    public void outputRequestPropertiesEmptyParamsTest() {
        Request request = new Request(new HashMap<String, String>());
        Assert.assertEquals(outputFile.outputRequestProperties(request),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i></i></div>");
    }

    @Test
    public void outputRequestPropertiesParamsTest() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("hello", "world");
        Request request = new Request(map);
        Assert.assertEquals(outputFile.outputRequestProperties(request),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i><div>hello&nbsp;:&nbsp;world</div></i></div>");
    }

    @Test
    public void outputRequestPropertiesBothTest() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("hello", "world");
        Request request = new Request(map);
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        request.setData(json);
        Assert.assertEquals(outputFile.outputRequestProperties(request),
                "<br/>&nbsp;with&nbsp;parameters:&nbsp;<div><i>{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}<div>hello&nbsp;:&nbsp;world</div></i></div>");
    }

    @Test
    public void formatResponseNullTest() {
        Assert.assertEquals(outputFile.formatResponse(null), "");
    }

    @Test
    public void formatResponseNullNullTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(0, json, null);
        response.setObjectData(null);
        Assert.assertEquals(outputFile.formatResponse(response), "");
    }

    @Test
    public void formatResponseEmptyObjectTest() {
        JsonObject json = new JsonObject();
        Response response = new Response(0, json, null);
        Assert.assertEquals(outputFile.formatResponse(response), "<div><i>{}</i></div>");
    }

    @Test
    public void formatRequestObjectTest() {
        JsonObject json = new JsonObject();
        json.addProperty("hello", "world");
        Response response = new Response(0, json, null);
        Assert.assertEquals(outputFile.formatResponse(response),
                "<div><i>{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div>");
    }

    @Test
    public void formatResponseEmptyArrayTest() {
        JsonArray json = new JsonArray();
        Response response = new Response(0, json, null);
        Assert.assertEquals(outputFile.formatResponse(response), "<div><i>[]</i></div>");
    }

    @Test
    public void formatResponseArrayTest() {
        JsonArray json = new JsonArray();
        json.add("world");
        Response response = new Response(0, json, null);
        Assert.assertEquals(outputFile.formatResponse(response), "<div><i>[<br/>&nbsp;&nbsp;\"world\"<br/>]</i></div>");
    }

    @Test
    public void formatResponseBothTest() {
        JsonArray array = new JsonArray();
        array.add("world");
        Response response = new Response(0, array, null);
        JsonObject object = new JsonObject();
        object.addProperty("hello", "world");
        response.setObjectData(object);
        Assert.assertEquals(outputFile.formatResponse(response),
                "<div><i>[<br/>&nbsp;&nbsp;\"world\"<br/>]{<br/>&nbsp;&nbsp;\"hello\":&nbsp;\"world\"<br/>}</i></div>");
    }

    @Test
    public void formatHTMLNullTest() {
        Assert.assertEquals(outputFile.formatHTML(null), "");
    }

    @Test
    public void formatHTMLEmptyTest() {
        Assert.assertEquals(outputFile.formatHTML(""), "");
    }

    @Test
    public void formatHTMLNewlineTest() {
        Assert.assertEquals(outputFile.formatHTML("\n"), "<br/>");
    }

    @Test
    public void formatHTMLSpaceTest() {
        Assert.assertEquals(outputFile.formatHTML(" "), "&nbsp;");
    }

    @Test
    public void formatHTMLFullTest() {
        Assert.assertEquals(outputFile.formatHTML("hello world\nhello world"), "hello&nbsp;world<br/>hello&nbsp;world");
    }
}