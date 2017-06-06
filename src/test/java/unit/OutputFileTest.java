package unit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coveros.selenified.output.OutputFile;
import com.coveros.selenified.output.Assert.Result;
import com.coveros.selenified.output.Assert.Success;
import com.coveros.selenified.selenium.Selenium.Browsers;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class OutputFileTest {

	private OutputFile outputFile;
	private File directory;
	private File file;

	@BeforeMethod
	public void createFile() {
		outputFile = new OutputFile("directory", "file", Browsers.ANDROID);
		directory = new File("directory");
		file = new File("directory", "fileANDROID.html");
	}

	@AfterMethod
	public void deleteFile() {
		file.delete();
		directory.delete();
	}

	@Test
	public void setupFileTest() {
		Assert.assertEquals(file.length(), 0);
		Assert.assertTrue(directory.exists());
		Assert.assertTrue(file.exists());

		// do it again, ensure nothing breaks when it already exists
		outputFile = new OutputFile("directory", "file", Browsers.ANDROID);
		Assert.assertEquals(file.length(), 0);
		Assert.assertTrue(directory.exists());
		Assert.assertTrue(file.exists());
	}

	@Test
	public void fileNameTest() {
		Assert.assertEquals(outputFile.getFileName(), "fileANDROID.html");
	}

	@Test
	public void countInstancesOfTest() {
		try (
				// Reopen file
				FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw);) {
			// record our action
			out.write("1 2 3 4 5 1\n");
		} catch (IOException e) {
		}
		Assert.assertEquals(outputFile.countInstancesOf("1"), 1);
		Assert.assertEquals(outputFile.countInstancesOf("2"), 1);
		try (
				// Reopen file
				FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw);) {
			// record our action
			out.write("1\n");
		} catch (IOException e) {
		}
		Assert.assertEquals(outputFile.countInstancesOf("1"), 2);
		Assert.assertEquals(outputFile.countInstancesOf("2"), 1);
	}

	@Test
	public void replaceInFileTest() {
		try (
				// Reopen file
				FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw);) {
			// record our action
			out.write("1 2 3 4 5 1\n");
			out.write("Hello World\n");
		} catch (IOException e) {
		}
		outputFile.replaceInFile("1", "2");
		Assert.assertEquals(outputFile.countInstancesOf("1"), 0);
		outputFile.replaceInFile("2 2 3 4 5 2", "Hello World");
		Assert.assertEquals(outputFile.countInstancesOf("Hello World"), 2);
	}

	@Test
	public void generateImageNameTest() {
		Assert.assertTrue(outputFile.generateImageName().startsWith(directory.toString()));
		Assert.assertTrue(outputFile.generateImageName().endsWith(".png"));
		Assert.assertEquals(outputFile.generateImageName().length(), 38);
		Assert.assertTrue(
				outputFile.generateImageName().matches("^" + directory.toString() + "/[0-9]{13}_[A-Za-z0-9]{10}.png$"));
	}

	@Test
	public void generateImageSourceTest() {
		Assert.assertEquals(outputFile.generateImageSource(""),
				"<br/><div align='center' width='100%'><img id='' class='imgIcon' src=''></div>");
		Assert.assertEquals(outputFile.generateImageSource(directory.toString()),
				"<br/><div align='center' width='100%'><img id='' class='imgIcon' src=''></div>");
		Assert.assertEquals(outputFile.generateImageSource("directory/1234"),
				"<br/><div align='center' width='100%'><img id='1234' class='imgIcon' src='1234'></div>");
	}

	@Test
	public void generateImageLinkTest() {
		Assert.assertEquals(outputFile.generateImageLink(""), "<br/><b><font class='fail'>No Image Preview</font></b>");
		Assert.assertEquals(outputFile.generateImageLink(directory.toString()),
				"<br/><b><font class='fail'>No Image Preview</font></b>");
		Assert.assertEquals(outputFile.generateImageLink("directory/1234"),
				"<br/><a href='javascript:void(0)' onclick='toggleImage(\"1234\")'>Toggle Screenshot Thumbnail</a> <a href='javascript:void(0)' onclick='displayImage(\"1234\")'>View Screenshot Fullscreen</a><br/><img id='1234' border='1px' src='file:///directory/1234' width='300px' style='display:none;'>");
	}

	@Test
	public void captureEntirePageScreenshotTest() {
		Assert.assertEquals(outputFile.captureEntirePageScreenshot(),
				"<br/><b><font class='fail'>No Screenshot Available</font></b>");
	}

	@Test
	public void setStartTimeTest() {
		outputFile.setStartTime();
		Assert.assertEquals(outputFile.getStartTime(), outputFile.getLastTime());
		outputFile.setStartTime(123456);
		Assert.assertEquals(outputFile.getStartTime(), 123456);
		Assert.assertEquals(outputFile.getLastTime(), 123456);
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

	@Test
	public void createOutputHeaderSuiteTest() {
		outputFile.setSuite("My Suite");
		outputFile.createOutputHeader();
		Assert.assertNotEquals(file.length(), 0);
		Assert.assertEquals(outputFile.countInstancesOf("My Suite"), 1);
	}

	@Test
	public void createOutputHeaderGroupTest() {
		outputFile.setGroup("My Group");
		outputFile.createOutputHeader();
		Assert.assertNotEquals(file.length(), 0);
		Assert.assertEquals(outputFile.countInstancesOf("My Group"), 1);
	}

	@Test
	public void createOutputHeaderLastModifiedTest() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		String dateInString = "31-08-1982";
		Date date = sdf.parse(dateInString);
		outputFile.setLastModified(date);
		outputFile.createOutputHeader();
		Assert.assertNotEquals(file.length(), 0);
		Assert.assertEquals(outputFile.countInstancesOf("Tuesday, August 31, 1982"), 1);
	}

	@Test
	public void createOutputHeaderNoLastModifiedTest() throws ParseException {
		outputFile.createOutputHeader();
		Assert.assertNotEquals(file.length(), 0);
		Assert.assertEquals(outputFile.countInstancesOf("--"), 1);
	}

	@Test
	public void createOutputHeaderVersionTest() {
		outputFile.setVersion("My Version");
		outputFile.createOutputHeader();
		Assert.assertNotEquals(file.length(), 0);
		Assert.assertEquals(outputFile.countInstancesOf("My Version"), 1);
	}

	@Test
	public void createOutputHeaderAuthorTest() {
		outputFile.setAuthor("My Author");
		outputFile.createOutputHeader();
		Assert.assertNotEquals(file.length(), 0);
		Assert.assertEquals(outputFile.countInstancesOf("My Author"), 1);
	}

	@Test
	public void createOutputHeaderObjectivesTest() {
		outputFile.setObjectives("My Objectives");
		outputFile.createOutputHeader();
		Assert.assertNotEquals(file.length(), 0);
		Assert.assertEquals(outputFile.countInstancesOf("My Objectives"), 1);
	}

	@Test
	public void loadInitialPageTest() throws IOException {
		outputFile.loadInitialPage();
		Assert.assertEquals(file.length(), 0);
		Assert.assertEquals(outputFile.countInstancesOf("The starting page"), 0);
	}

	@Test
	public void recordActionSuccess() throws IOException {
		outputFile.setStartTime(new Date().getTime());
		outputFile.recordAction("my action", "expected", "actual", Result.SUCCESS);
		Assert.assertNotEquals(file.length(), 0);
		String content = Files.toString(file, Charsets.UTF_8);
		Assert.assertTrue(content.matches(
				"   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td class='success'>actual</td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
	}

	@Test
	public void recordActionFailure() throws IOException {
		outputFile.setStartTime(new Date().getTime());
		outputFile.recordAction("my action", "expected", "actual", Result.FAILURE);
		Assert.assertNotEquals(file.length(), 0);
		String content = Files.toString(file, Charsets.UTF_8);
		Assert.assertTrue(content.matches(
				"   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td class='failure'>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
	}

	@Test
	public void recordActionFailureSkipped() throws IOException {
		outputFile.setStartTime(new Date().getTime());
		outputFile.recordAction("my action", "expected", "actual", Result.SKIPPED);
		Assert.assertNotEquals(file.length(), 0);
		String content = Files.toString(file, Charsets.UTF_8);
		Assert.assertTrue(content.matches(
				"   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td class='skipped'>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
	}

	@Test
	public void recordActionFailureWarning() throws IOException {
		outputFile.setStartTime(new Date().getTime());
		outputFile.recordAction("my action", "expected", "actual", Result.WARNING);
		Assert.assertNotEquals(file.length(), 0);
		String content = Files.toString(file, Charsets.UTF_8);
		Assert.assertTrue(content.matches(
				"   <tr>\n    <td align='center'>1.</td>\n    <td>my action</td>\n    <td>expected</td>\n    <td class='warning'>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
	}

	@Test
	public void recordExpected() throws IOException {
		outputFile.setStartTime(new Date().getTime());
		outputFile.recordExpected("expected");
		Assert.assertNotEquals(file.length(), 0);
		String content = Files.toString(file, Charsets.UTF_8);
		Assert.assertEquals(content,
				"   <tr>\n    <td align='center'>1.</td>\n    <td> </td>\n    <td>expected</td>\n");
	}

	@Test
	public void recordActualPass() throws IOException {
		outputFile.setStartTime(new Date().getTime());
		outputFile.recordActual("actual", Success.PASS);
		Assert.assertNotEquals(file.length(), 0);
		String content = Files.toString(file, Charsets.UTF_8);
		Assert.assertTrue(content.matches(
				"    <td>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='pass'>Pass</td>\n   </tr>\n"));
	}

	@Test
	public void recordActualFail() throws IOException {
		outputFile.setStartTime(new Date().getTime());
		outputFile.recordActual("actual", Success.FAIL);
		Assert.assertNotEquals(file.length(), 0);
		String content = Files.toString(file, Charsets.UTF_8);
		Assert.assertTrue(content.matches(
				"    <td>actual<br/><b><font class='fail'>No Screenshot Available</font></b></td>\n    <td>[0-9]+ms / [0-9]+ms</td>\n    <td class='fail'>Fail</td>\n   </tr>\n"));
	}

	@Test
	public void endTestTemplateOutputFileTest() throws IOException {
		outputFile.endTestTemplateOutputFile();
		Assert.assertNotEquals(file.length(), 0);
		String content = Files.toString(file, Charsets.UTF_8);
		Assert.assertEquals(content, "  </table>\r\n </body>\r\n</html>\r\n");
	}
}