package tools.output;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.log4testng.Logger;

import tools.General;
import tools.output.Action.Browsers;
import tools.output.Action.Locators;
import tools.output.Assert.Result;
import tools.output.Assert.Success;

public class OutputFile {

	private static final Logger log = Logger.getLogger(General.class);

	private Action selHelper = null;

	private String url;
	private String suite;
	private String group;
	private Date lastModified;
	private String version;
	private String author;
	private String objectives;

	private String test;
	private String directory;
	private File file;
	private String filename;
	private Browsers browser;

	// timing of the test
	private long startTime;
	private long lastTime = 0;
	// this will track our step numbers
	private int stepNum = 0;
	// this will keep track of the errors
	private int errors = 0;
	// the image width for reporting
	private int embeddedImageWidth = 300;

	OutputFile(String testDirectory, String testName, Browsers setBrowser) {

		test = testName;
		browser = setBrowser;
		directory = testDirectory;
		filename = testName + browser + ".html";
		file = new File(directory, filename);
		setupFile();
	}

	public String getFileName() {
		return filename;
	}

	public int getErrors() {
		return errors;
	}

	public void setSeleniumHelper(Action seleniumHelper) {
		selHelper = seleniumHelper;
	}

	public void setURL(String pageURL) {
		url = pageURL;
	}

	public void setSuite(String testSuite) {
		suite = testSuite;
	}

	public void setGroup(String testGroup) {
		group = testGroup;
	}

	public void setLastModified(Date date) {
		lastModified = date;
	}

	public void setVersion(String testVersion) {
		version = testVersion;
	}

	public void setAuthor(String testAuthor) {
		author = testAuthor;
	}

	public void setObjectives(String testObjectives) {
		objectives = testObjectives;
	}

	/**
	 * creates the directory and file to hold our test output file
	 * 
	 * @param directory
	 *            - a string location of the directory to hold the results
	 * @param fileName
	 *            - the desired filename of the test output file
	 */
	public void setupFile() {
		if (!new File(directory).exists()) {
			new File(directory).mkdirs();
		}
		if (!file.exists()) {
			try {
				if (!file.createNewFile()) {
					throw new IOException("Unable to create output file");
				}
			} catch (IOException e) {
				log.error(e);
			}
		}
	}

	/**
	 * A method to count the number of occurrence of a string within a file
	 *
	 * @param fileName
	 *            - the string of the complete filename
	 * @param textToFind
	 *            - the text to count
	 * @return Integer - the number of times the text was found in the file
	 *         provided
	 */
	public int countInstancesOf(String textToFind) {
		int count = 0;
		try (FileReader fr = new FileReader(filename); BufferedReader reader = new BufferedReader(fr);) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line.contains(textToFind)) {
					count++;
				}
			}
		} catch (IOException ioe) {
			log.error(ioe);
		}
		return count;
	}

	/**
	 * A method to replace an occurrence of a string within a file
	 *
	 * @param fileName
	 *            - the string of the complete filename
	 * @param oldText
	 *            - the text to be replaced
	 * @param newText
	 *            - the text to be replaced with
	 */
	public void replaceInFile(String oldText, String newText) {
		String line = "";
		String oldtext = "";

		try (FileReader fr = new FileReader(file); BufferedReader reader = new BufferedReader(fr);) {
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
		} catch (IOException ioe) {
			log.error(ioe);
		}

		// replace a word in a file
		String newtext = oldtext.replaceAll(oldText, newText);

		try (FileWriter writer = new FileWriter(file);) {
			writer.write(newtext);
		} catch (IOException ioe) {
			log.error(ioe);
		}
	}

	/**
	 * generates a unique image name based on the action the time, and a unique
	 * random string, creates an image using that name, and returns a processed
	 * link to be included in an HTML file
	 *
	 * @return a processed link to be included in an HTML file
	 */
	public String captureImage() {
		String imageName = generateImageName();
		String imageLink = generateImageLink(imageName);
		imageLink = captureEntirePageScreenshot(imageLink, imageName);
		return imageLink;
	}

	/**
	 * a helper function to capture the entire page screen shot
	 *
	 * @param imageLink
	 *            - the original link to the image to be captured
	 * @param imageName
	 *            - the name of the image to be captured
	 * @return new image link
	 */
	public String captureEntirePageScreenshot(String imageLink, String imageName) {
		try {
			selHelper.takeScreenshot(imageName);
		} catch (Exception e1) {
			log.error(e1);
			imageLink = "<br><b><font color=red>No Screenshot Available</font></b>";
		}
		return imageLink;
	}

	/**
	 * write an action that was performed out to the output file
	 *
	 * @param action
	 *            the step that was performed
	 * @param expectedResult
	 *            the result that was expected to occur
	 * @param actualResult
	 *            the result that actually occurred
	 * @param result
	 *            the result of the action
	 */
	public void recordAction(String action, String expectedResult, String actualResult, Result result) {
		stepNum++;
		String success = "Fail";
		String imageLink = "";
		if (result == Result.SUCCESS) {
			success = "Pass";
		}
		if (success.equals("Fail") && browser != Browsers.HtmlUnit) {
			// get a screen shot of our action
			imageLink = captureImage();
		}
		// determine time differences
		Date currentTime = new Date();
		long dTime = currentTime.getTime() - lastTime;
		long tTime = currentTime.getTime() - startTime;
		lastTime = currentTime.getTime();
		try (
				// Reopen file
				FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw);) {
			// record our action
			out.write("   <tr>\n");
			out.write("    <td align='center'>" + stepNum + ".</td>\n");
			out.write("    <td>" + action + "</td>\n");
			out.write("    <td>" + expectedResult + "</td>\n");
			out.write(
					"    <td class='" + result.toString().toLowerCase() + "'>" + actualResult + imageLink + "</td>\n");
			out.write("    <td>" + dTime + "ms / " + tTime + "ms</td>\n");
			out.write("    <td class='" + success.toString().toLowerCase() + "'>" + success + "</td>\n");
			out.write("   </tr>\n");
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * Will write to the output file the actual outcome, and also indicate
	 * whether a pass or fail has occurred
	 *
	 * @param actualOutcome
	 *            what the actual outcome was
	 * @param result
	 *            whether this result is a pass or a failure
	 */
	public void recordActual(String actualOutcome, Success result) {
		try (
				// reopen our log file
				FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw);) {
			// get a screen shot of our action
			String imageLink = "";
			if (browser != Browsers.HtmlUnit) {
				imageLink = captureImage();
			}
			// determine time differences
			Date currentTime = new Date();
			long dTime = currentTime.getTime() - lastTime;
			long tTime = (currentTime.getTime() - startTime);
			lastTime = currentTime.getTime();
			// write out our actual outcome
			out.write("    <td>" + actualOutcome + imageLink + "</td>\n");
			out.write("    <td>" + dTime + "ms / " + tTime + "ms</td>\n");
			// write out our pass or fail result
			if (result == Success.PASS) {
				out.write("    <td class='pass'>Pass</td>\n");
			} else {
				out.write("    <td class='fail'>Fail</td>\n");
			}
			// end our row
			out.write("   </tr>\n");
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * Will write to the output file the step number, skip the action taken, and
	 * then write out the expected outcome
	 *
	 * @param expectedOutcome
	 *            what the expected outcome is
	 */
	public void recordExpected(String expectedOutcome) {
		stepNum++;

		try (
				// reopen our log file
				FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw);) {
			// start our row
			out.write("   <tr>\n");
			// log our step number
			out.write("    <td align='center'>" + stepNum + ".</td>\n");
			// leave the step blank as this is simply a check
			out.write("    <td> </td>\n");
			// write out our expected outcome
			out.write("    <td>" + expectedOutcome + "</td>\n");
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * creates and starts the test template
	 *
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 *             - an IOException
	 */
	public int startTestTemplateOutputFile() throws IOException {
		createOutputHeader();
		if (selHelper != null) {
			selHelper.getDriver().get(url);
			Result result = (selHelper.isElementPresent(Locators.xpath, "//body", false)) ? Result.SUCCESS
					: Result.FAILURE;
			String actual = (selHelper.isElementPresent(Locators.xpath, "//body", false))
					? "The starting page <i>" + url + "</i> loaded successfully"
					: "The starting page <i>" + url + "</i> did not load successfully";
			recordAction("Opening new browser and loading up starting page",
					"The starting page <i>" + url + "</i> will successfully load", actual, result);
			if (result == Result.SUCCESS) {
				return 0;
			} else {
				addError();
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Creates the specially formatted output header
	 *
	 * @throws IOException
	 *             - an IOException
	 */
	public void createOutputHeader() {
		// Open file
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy");
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		String datePart = sdf.format(new Date());
		String sTime = stf.format(startTime);
		try (FileWriter fw = new FileWriter(file); BufferedWriter out = new BufferedWriter(fw);) {
			out.write("<html>\n");
			out.write(" <head>\n");
			out.write("  <title>" + test + "</title>\n");
			out.write("  <style type='text/css'>\n");
			out.write("   table {\n");
			out.write("    margin-left:auto;margin-right:auto;\n");
			out.write("    width:90%;\n");
			out.write("    border-collapse:collapse;\n");
			out.write("   }\n");
			out.write("   table, td, th {\n");
			out.write("    border:1px solid black;\n");
			out.write("    padding:0px 10px;\n");
			out.write("   }\n");
			out.write("   th {\n");
			out.write("    text-align:right;\n");
			out.write("   }\n");
			out.write("   td {\n");
			out.write("    word-wrap: break-word;\n");
			out.write("   }\n");
			out.write("   td.warning {\n");
			out.write("    color:yellow;\n");
			out.write("    font-weight:bold;\n");
			out.write("   }\n");
			out.write("   td.fail {\n");
			out.write("    color:red;\n");
			out.write("    font-weight:bold;\n");
			out.write("   }\n");
			out.write("   td.pass {\n");
			out.write("    color:green;\n");
			out.write("    font-weight:bold;\n");
			out.write("   }\n");
			out.write("  </style>\n");
			out.write("  <script type='text/javascript'>\n");
			out.write("   function toggleImage( imageName ) {\n");
			out.write("    var element = document.getElementById( imageName );\n");
			out.write("    element.src = location.href.match(/^.*\\//) + imageName;\n");
			out.write("    element.style.display = (element.style.display != 'none' ? 'none' : '' );\n");
			out.write("   }\n");
			out.write("   function displayImage( imageName ) {\n");
			out.write("    window.open( location.href.match(/^.*\\//) + imageName )\n");
			out.write("   }\n");
			out.write("   function toggleVis(col_no, do_show) {\n");
			out.write("    var stl;\n");
			out.write("    if (do_show) stl = ''\n");
			out.write("    else         stl = 'none';\n");
			out.write("    var tbl  = document.getElementById('all_results');\n");
			out.write("    var rows = tbl.getElementsByTagName('tr');\n");
			out.write("    var cels = rows[0].getElementsByTagName('th')\n");
			out.write("    cels[col_no].style.display=stl;\n");
			out.write("    for (var row=1; row<rows.length;row++) {\n");
			out.write("     var cels = rows[row].getElementsByTagName('td')\n");
			out.write("     cels[col_no].style.display=stl;\n");
			out.write("    }\n");
			out.write("   }\n");
			out.write("   function getElementsByClassName(oElm, strTagName, strClassName){\n");
			out.write(
					"    var arrElements = (strTagName == '*' && document.all)? document.all : oElm.getElementsByTagName(strTagName);\n");
			out.write("    var arrReturnElements = new Array();\n");
			out.write("    strClassName = strClassName.replace(/\\-/g, '\\\\-');\n");
			out.write("    var oRegExp = new RegExp('(^|\\s)' + strClassName + '(\\s|$)');\n");
			out.write("    var oElement;\n");
			out.write("    for(var i=0; i<arrElements.length; i++){\n");
			out.write("     oElement = arrElements[i];\n");
			out.write("     if(oRegExp.test(oElement.className)){\n");
			out.write("      arrReturnElements.push(oElement);\n");
			out.write("     }\n");
			out.write("    }\n");
			out.write("    return (arrReturnElements)\n");
			out.write("   }\n");
			out.write("   function fixImages( imageName ) {\n");
			out.write("    top.document.title = document.title;\n");
			out.write("    allImgIcons = getElementsByClassName( document, 'img', 'imgIcon' );\n");
			out.write("    for( var element in allImgIcons ) {\n");
			out.write("     element.src = location.href.match(/^.*\\//) + element.src;\n");
			out.write("    }\n");
			out.write("   }\n");
			out.write("  </script>\n");
			out.write(" </head>\n");
			out.write(" <body onLoad='fixImages()'>\n");
			out.write("  <table>\n");
			out.write("   <tr>\n");
			out.write("    <th bgcolor='lightblue'><font size='5'>Test</font></th>\n");
			out.write("    <td bgcolor='lightblue' colspan=3>" + "<font size='5'>" + test + " </font></td>\n");
			out.write("   </tr><tr>\n");
			out.write("    <th>Tester</th>\n");
			out.write("    <td>Automated</td>\n");
			out.write("    <th>Version</th>\n");
			out.write("    <td>" + this.version + "</td>\n");
			out.write("   </tr><tr>\n");
			out.write("    <th>Author</th>\n");
			out.write("    <td>" + this.author + "</td>\n");
			out.write("    <th>Date Test Last Modified</th>\n");
			out.write("    <td>" + sdf.format(this.lastModified) + "</td>\n");
			out.write("   </tr><tr>\n");
			out.write("    <th>Date Tested</th>\n");
			out.write("    <td>" + datePart + "</td>\n");
			if (this.selHelper != null) {
				out.write("    <th>Browser</th>\n");
				out.write("    <td>" + browser + "</td>\n");
			}
			out.write("   </tr><tr>\n");
			out.write("    <th>Test Run Time</th>\n");
			out.write("    <td colspan=3>\n");
			out.write("     Start:\t" + sTime + " <br/>\n");
			out.write("     End:\tTIMEFINISHED <br/>\n");
			out.write("     Run Time:\tRUNTIME \n");
			out.write("    </td>\n");
			out.write("   </tr><tr>\n");
			out.write("    <th>Testing Group</th>\n");
			out.write("    <td>" + group + "</td>\n");
			out.write("    <th>Testing Suite</th>\n");
			out.write("    <td>" + suite + "</td>\n");
			out.write("   </tr><tr>\n");
			out.write("    <th>Test Objectives</th>\n");
			out.write("    <td colspan=3>" + objectives + "</td>\n");
			out.write("   </tr><tr>\n");
			out.write("    <th>Overall Results</th>\n");
			out.write("    <td colspan=3 style='padding: 0px;'>\n");
			out.write("     <table style='width: 100%;'><tr>\n");
			out.write("      <td font-size='big' rowspan=2>PASSORFAIL</td>\n");
			out.write("      <td><b>Steps Performed</b></td><td><b>Steps Passed</b></td>"
					+ "<td><b>Steps Failed</b></td>\n");
			out.write("     </tr><tr>\n");
			out.write("      <td>STEPSPERFORMED</td><td>STEPSPASSED</td><td>STEPSFAILED</td>\n");
			out.write("     </tr></table>\n");
			out.write("    </td>\n");
			out.write("   </tr><tr>\n");
			out.write("    <th>View Results</th>\n");
			out.write("    <td colspan=3>\n");
			out.write("     <input type=checkbox name='step' onclick='toggleVis(0,this.checked)' checked>Step\n");
			out.write("     <input type=checkbox name='action' onclick='toggleVis(1,this.checked)' checked>Action \n");
			out.write(
					"     <input type=checkbox name='expected' onclick='toggleVis(2,this.checked)' checked>Expected Results \n");
			out.write(
					"     <input type=checkbox name='actual' onclick='toggleVis(3,this.checked)' checked>Actual Results \n");
			out.write(
					"     <input type=checkbox name='times' onclick='toggleVis(4,this.checked)' checked>Step Times \n");
			out.write("     <input type=checkbox name='result' onclick='toggleVis(5,this.checked)' checked>Results\n");
			out.write("    </td>\n");
			out.write("   </tr>\n");
			out.write("  </table>\n");
			out.write("  <table id='all_results'>\n");
			out.write("   <tr>\n");
			out.write("    <th align='center'>Step</th>" + "<th style='text-align:center'>Action</th>"
					+ "<th style='text-align:center'>Expected Result</th>"
					+ "<th style='text-align:center'>Actual Result</th>"
					+ "<th style='text-align:center'>Step Times</th>"
					+ "<th style='text-align:center'>Pass/Fail</th>\n");
			out.write("   </tr>\n");
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * ends and closes the test template
	 *
	 * @throws IOException
	 */
	public void endTestTemplateOutputFile() throws IOException {
		// reopen the file
		BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
		out.write("  </table>\n");
		out.write(" </body>\n");
		out.write("</html>\n");
		// Close the output stream
		out.close();
		// Record our metrics
		int passes = countInstancesOf("<td class='pass'>Pass</td>");
		int fails = countInstancesOf("<td class='fail'>Fail</td>");
		replaceInFile("STEPSPERFORMED", Integer.toString(fails + passes));
		replaceInFile("STEPSPASSED", Integer.toString(passes));
		replaceInFile("STEPSFAILED", Integer.toString(fails));
		if (fails == 0) {
			replaceInFile("PASSORFAIL", "<font size='+2' color='green'><b>PASS</b></font>");
		} else {
			replaceInFile("PASSORFAIL", "<font size='+2' color='red'><b>FAIL</b></font>");
		}
		// record our time
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		String timeNow = stf.format(new Date());
		long totalTime = (new Date()).getTime() - startTime;
		long time = totalTime / 1000;
		String seconds = Integer.toString((int) (time % 60));
		String minutes = Integer.toString((int) ((time % 3600) / 60));
		String hours = Integer.toString((int) (time / 3600));
		for (int i = 0; i < 2; i++) {
			if (seconds.length() < 2) {
				seconds = "0" + seconds;
			}
			if (minutes.length() < 2) {
				minutes = "0" + minutes;
			}
			if (hours.length() < 2) {
				hours = "0" + hours;
			}
		}
		replaceInFile("RUNTIME", hours + ":" + minutes + ":" + seconds);
		replaceInFile("TIMEFINISHED", timeNow);
	}

	/**
	 * generates the link for the image
	 *
	 * @param imageName
	 *            the name of the image being embedded
	 * @return String the link for the image which can be written out to the
	 *         html file
	 */
	public String generateImageLink(String imageName) {
		String imageLink = "<br/>";
		if (imageName.length() >= directory.length() + 1) {
			imageLink += "<a href='javascript:void(0)' onclick='toggleImage(\""
					+ imageName.substring(directory.length() + 1) + "\")'>Toggle Screenshot Thumbnail</a>";
			imageLink += " <a href='javascript:void(0)' onclick='displayImage(\""
					+ imageName.substring(directory.length() + 1) + "\")'>View Screenshot Fullscreen</a>";
			imageLink += "<br/><img id='" + imageName.substring(directory.length() + 1) + "' border='1px' src='file:///"
					+ imageName + "' width='" + embeddedImageWidth + "px' style='display:none;'>";
		} else {
			imageLink += "<b><font color='red'>No Image Preview</font></b>";
		}
		return imageLink;
	}

	/**
	 * generates a unique image name
	 *
	 * @return String the name of the image file as a PNG
	 */
	public String generateImageName() {
		long timeInSeconds = new Date().getTime();
		SecureRandom random = new SecureRandom();
		String randomChars = new BigInteger(130, random).toString(10);
		return directory + "/" + timeInSeconds + "_" + randomChars + ".png";
	}

	/**
	 * generates the link for the image
	 *
	 * @param imageName
	 *            the name of the image being embedded
	 * @return String the link for the image which can be written out to the
	 *         html file
	 */
	public String generateImageSource(String imageName) {
		return "<br/><div align='center' width='100%'><img id='" + imageName.substring(directory.length() + 1)
				+ "' class='imgIcon' src='" + imageName.substring(directory.length() + 1) + "'></div>";
	}

	/**
	 * Determines the current time and sets the 'last time' to the current time
	 */
	public void setStartTime() {
		startTime = (new Date()).getTime();
		lastTime = startTime;
	}

	/**
	 * Determines the time passed in and sets the 'last time' to that time
	 *
	 * @param time
	 *            - the time to set the start time to
	 */
	public void setStartTime(long time) {
		startTime = time;
		lastTime = time;
	}

	/**
	 * simply add an encountered error to our error counter
	 */
	public void addError() {
		errors++;
	}

	/**
	 * add a given set of errors to our error counter
	 *
	 * @param errors
	 *            - the number of errors to add
	 */
	public void addErrors(int errorsToAdd) {
		errors += errorsToAdd;
	}
}