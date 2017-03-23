/*
 * Copyright 2017 Coveros, Inc.
 * 
 * This file is part of Selenified.
 * 
 * Selenified is licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy 
 * of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on 
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY 
 * KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations 
 * under the License.
 */

package tools.logging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.log4testng.Logger;

import selenified.exceptions.InvalidActionException;
import selenified.exceptions.InvalidLocatorTypeException;
import tools.General;
import tools.selenium.SeleniumHelper;
import tools.selenium.SeleniumHelper.Browsers;
import tools.selenium.SeleniumHelper.Locators;

/**
 * Test Output A custom generated output file recording all actions taken
 * 
 * @author Max Saperstone
 * @version 1.0.4
 * @lastupdate 8/29/2016
 */
public class TestOutput {

	private static final Logger log = Logger.getLogger(General.class);

	private SeleniumHelper selHelper;
	private String pageURL;
	private String outputDir;
	private String browser;
	// this will be the name of the file we write all commands out to
	private String fileName;
	private File outputFile;
	private String testName;
	private String testSuite;
	private String testGroup;
	// these are the variables for the header information
	private String version;
	private Date dateLastModified;
	private String author;
	private long startTime;
	private long lastTime = 0;
	private String testObjectives;
	// this will track our step numbers
	private int stepNum;

	//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
	// this will keep track of the errors
	private int errors = 0;

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
	public void addErrors(int errors) {
		this.errors += errors;
	}

	/**
	 * retrieve the number of errors from our counter
	 * 
	 * @return errors - the current tallied number of errors for the test
	 */
	public int getErrors() {
		return errors;
	}

	///////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	// this enum will be for a pass/fail
	public enum Success {
		PASS, FAIL
	};

	public enum Result {
		WARNING, SUCCESS, FAILURE, SKIPPED
	};

	// the image width for reporting
	private int embeddedImageWidth = 300;

	public void setSelHelper(SeleniumHelper selHelper) {
		this.selHelper = selHelper;
	}

	/**
	 * the basic test output constructor, providing access to the output file
	 * and all page elements
	 * 
	 * @param testsName
	 *            - the name of the file we will write out all commands to
	 * @param outputDir
	 *            - the output directory to store the test results
	 * @param pageURL
	 *            - the initial URL to load up/log
	 */
	public TestOutput(String testsName, String outputDir, String pageURL) {
		this.pageURL = pageURL;
		this.outputDir = outputDir;
		fileName = testsName + ".html";
		outputFile = new File(outputDir, fileName);
		browser = System.getProperty("browser").substring(0, 1).toUpperCase()
				+ System.getProperty("browser").substring(1);
		testSuite = null;
		testGroup = null;
		testName = General.wordToSentence(testsName);
		version = null;
		dateLastModified = new Date();
		author = null;
		testObjectives = null;
		stepNum = 0;
	}

	/**
	 * the complex test out constructor taking in all test document information
	 * 
	 * @param testsName
	 *            the name of the file we will write out to
	 * @param outputDir
	 *            the output directory to store the results
	 * @param pageURL
	 *            the initial URL to load up/log
	 * @param testsSuite
	 *            the suite of tests the test belongs to
	 * @param testsGroup
	 *            the group of tests the test belongs to
	 * @param iDateModified
	 *            the date the test was created
	 * @param iVersion
	 *            the version number of the test
	 * @param iAuthor
	 *            the author of the tests
	 * @param iTestObjectives
	 *            the test objectives
	 */
	public TestOutput(String testsName, String outputDir, String pageURL, String testsSuite, String testsGroup,
			Date iDateModified, String iVersion, String iAuthor, String iTestObjectives) {
		this.pageURL = pageURL;
		this.outputDir = outputDir;
		fileName = testsName + ".html";
		outputFile = new File(outputDir, fileName);
		if (!new File(outputDir).exists()) {
			new File(outputDir).mkdirs();
		}
		if (!outputFile.exists()) {
			try {
				while (!outputFile.createNewFile()) {
				}
			} catch (IOException e) {
				log.error(e);
			}
		}
		browser = System.getProperty("browser").substring(0, 1).toUpperCase()
				+ System.getProperty("browser").substring(1);
		testName = General.wordToSentence(testsName);
		testSuite = testsSuite;
		testGroup = testsGroup;
		version = iVersion;
		dateLastModified = iDateModified;
		author = iAuthor;
		testObjectives = iTestObjectives;
		stepNum = 0;
	}

	/**
	 * provides access to the file name
	 * 
	 * @return String with the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * provides access to the output file name
	 * 
	 * @return String with the actual output file name
	 */
	public File getOutputFileName() {
		return outputFile;
	}

	/**
	 * creates and starts the test template
	 * 
	 * @return Integer - the number of errors encountered while executing these
	 *         steps
	 * @throws IOException
	 *             - an IOException
	 */
	public int startTestTemplateOutputFile(boolean selenium) throws IOException {
		createOutputHeader(selenium);
		if (selenium) {
			selHelper.getDriver().get(pageURL);
			Result result = (selHelper.isElementPresent(Locators.xpath, "//body", false)) ? Result.SUCCESS
					: Result.FAILURE;
			String actual = (selHelper.isElementPresent(Locators.xpath, "//body", false))
					? "The starting page <i>" + pageURL + "</i> loaded successfully"
					: "The starting page <i>" + pageURL + "</i> did not load successfully";
			recordAction("Opening new browser and loading up starting page",
					"The starting page <i>" + pageURL + "</i> will successfully load", actual, result);
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
	 * ends and closes the test template
	 * 
	 * @throws IOException
	 */
	public void endTestTemplateOutputFile() throws IOException {
		// reopen the file
		BufferedWriter out = new BufferedWriter(new FileWriter(outputFile, true));
		out.write("  </table>\n");
		out.write(" </body>\n");
		out.write("</html>\n");
		// Close the output stream
		out.close();
		// Record our metrics
		int passes = countInstancesOf(outputFile, "<td class='pass'>Pass</td>");
		int fails = countInstancesOf(outputFile, "<td class='fail'>Fail</td>");
		replaceInFile(outputFile, "STEPSPERFORMED", Integer.toString(fails + passes));
		replaceInFile(outputFile, "STEPSPASSED", Integer.toString(passes));
		replaceInFile(outputFile, "STEPSFAILED", Integer.toString(fails));
		if (fails == 0) {
			replaceInFile(outputFile, "PASSORFAIL", "<font size='+2' color='green'><b>PASS</b></font>");
		} else {
			replaceInFile(outputFile, "PASSORFAIL", "<font size='+2' color='red'><b>FAIL</b></font>");
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
		replaceInFile(outputFile, "RUNTIME", hours + ":" + minutes + ":" + seconds);
		replaceInFile(outputFile, "TIMEFINISHED", timeNow);
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

		BufferedWriter out = null;
		FileWriter fw = null;
		try {
			// reopen our log file
			fw = new FileWriter(outputFile, true);
			out = new BufferedWriter(fw);
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
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
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
		// reopen our log file
		BufferedWriter out = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(outputFile, true);
			out = new BufferedWriter(fw);
			// get a screen shot of our action
			String imageLink = "";
			if (selHelper.getBrowser() != Browsers.HtmlUnit) {
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
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
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
	 * Creates the specially formatted output header
	 * 
	 * @throws IOException
	 *             - an IOException
	 * 
	 */
	public void createOutputHeader(boolean selenium) {
		// Open file
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy");
		SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
		String DatePart = sdf.format(new Date());
		String sTime = stf.format(startTime);
		FileWriter fw = null;
		BufferedWriter out = null;
		try {
			fw = new FileWriter(outputFile);
			out = new BufferedWriter(fw);
			out.write("<html>\n");
			out.write(" <head>\n");
			out.write("  <title>" + testName + "</title>\n");
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
			out.write("    <td bgcolor='lightblue' colspan=3>" + "<font size='5'>" + testName + " </font></td>\n");
			out.write("   </tr><tr>\n");
			out.write("    <th>Tester</th>\n");
			out.write("    <td>Automated</td>\n");
			out.write("    <th>Version</th>\n");
			out.write("    <td>" + version + "</td>\n");
			out.write("   </tr><tr>\n");
			out.write("    <th>Author</th>\n");
			out.write("    <td>" + author + "</td>\n");
			out.write("    <th>Date Test Last Modified</th>\n");
			out.write("    <td>" + sdf.format(dateLastModified) + "</td>\n");
			out.write("   </tr><tr>\n");
			out.write("    <th>Date Tested</th>\n");
			out.write("    <td>" + DatePart + "</td>\n");
			if (selenium) {
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
			out.write("    <td>" + testGroup + "</td>\n");
			out.write("    <th>Testing Suite</th>\n");
			out.write("    <td>" + testSuite + "</td>\n");
			out.write("   </tr><tr>\n");
			out.write("    <th>Test Objectives</th>\n");
			out.write("    <td colspan=3>" + testObjectives + "</td>\n");
			out.write("   </tr><tr>\n");
			out.write("    <th>Overall Results</th>\n");
			out.write("    <td colspan=3 style='padding: 0px;'>\n");
			out.write("     <table style='width: 100%;'><tr>\n");
			out.write("      <td font-size='big' rowspan=2>PASSORFAIL</td>\n");
			out.write(
					"      <td><b>Steps Performed</b></td><td><b>Steps Passed</b></td>" + "<td><b>Steps Failed</b></td>\n");
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
			out.write("     <input type=checkbox name='times' onclick='toggleVis(4,this.checked)' checked>Step Times \n");
			out.write("     <input type=checkbox name='result' onclick='toggleVis(5,this.checked)' checked>Results\n");
			out.write("    </td>\n");
			out.write("   </tr>\n");
			out.write("  </table>\n");
			out.write("  <table id='all_results'>\n");
			out.write("   <tr>\n");
			out.write("    <th align='center'>Step</th>" + "<th style='text-align:center'>Action</th>"
					+ "<th style='text-align:center'>Expected Result</th>"
					+ "<th style='text-align:center'>Actual Result</th>" + "<th style='text-align:center'>Step Times</th>"
					+ "<th style='text-align:center'>Pass/Fail</th>\n");
			out.write("   </tr>\n");
		} catch (IOException e) {
			log.error(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
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
		if (selHelper != null && success.equals("Fail") && selHelper.getBrowser() != Browsers.HtmlUnit) {
			// get a screen shot of our action
			imageLink = captureImage();
		}
		// determine time differences
		Date currentTime = new Date();
		long dTime = currentTime.getTime() - lastTime;
		long tTime = (currentTime.getTime() - startTime);
		lastTime = currentTime.getTime();
		// Reopen file
		FileWriter fw = null;
		BufferedWriter out = null;
		try {
			fw = new FileWriter(outputFile, true);
			out = new BufferedWriter(fw);
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
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
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
		return outputDir + "/" + timeInSeconds + "_" + randomChars + ".png";
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
		// FOR DEBUGGING PURPOSES
		// System.out.print( "Output Location: " + outputFolder + "\n" );
		// System.out.print( "File name: " + imageName + "\n" );
		if (imageName.length() >= outputDir.length() + 1) {
			imageLink += "<a href='javascript:void(0)' onclick='toggleImage(\""
					+ imageName.substring(outputDir.length() + 1) + "\")'>Toggle Screenshot Thumbnail</a>";
			imageLink += " <a href='javascript:void(0)' onclick='displayImage(\""
					+ imageName.substring(outputDir.length() + 1) + "\")'>View Screenshot Fullscreen</a>";
			imageLink += "<br/><img id='" + imageName.substring(outputDir.length() + 1) + "' border='1px' src='file:///"
					+ imageName + "' width='" + embeddedImageWidth + "px' style='display:none;'>";
		} else {
			imageLink += "<b><font color='red'>No Image Preview</font></b>";
		}
		return imageLink;
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
		String imageLink = "<br/><div align='center' width='100%'><img id='"
				+ imageName.substring(outputDir.length() + 1) + "' class='imgIcon' src='"
				+ imageName.substring(outputDir.length() + 1) + "'></div>";
		// centered image without border
		// String imageLink = "<br/><img id='" + imageName.substring(
		// outputFolder.length()+1 ) +
		// "' border='1px' src='file:///" + imageName + "'>"; //image with
		// border
		return imageLink;
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
			imageLink = "<br><b><font color=red>No Screenshot Available</font></b>";
		}
		return imageLink;
	}

	// /////////////////////////////////////////////////////////////////////////
	// a bunch of methods to positively check for objects using selenium calls
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * checks to see if an alert is correct on the page
	 * 
	 * @param expectedAlert
	 *            the expected text of the alert
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkAlert(String expectedAlert) {
		// record our action
		recordExpected("Expected to find alert with the text <b>" + expectedAlert + "</b> on the page");
		// check for our object to the visible
		String alert = "";
		boolean isAlertPresent = selHelper.isAlertPresent();
		if (isAlertPresent) {
			alert = selHelper.getAlert();
		}
		if (!isAlertPresent) {
			recordActual("No alert is present on the page", Success.FAIL);
			addError();
			return 1;
		}
		Pattern patt = Pattern.compile(expectedAlert);
		Matcher m = patt.matcher(alert);
		boolean isCorrect = false;
		if (expectedAlert.contains("\\")) {
			isCorrect = m.matches();
		} else {
			isCorrect = alert.equals(expectedAlert);
		}
		if (!isCorrect) {
			recordActual("An alert with text <b>" + alert + "</b> is present on the page", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("An alert with text <b>" + alert + "</b> is present on the page", Success.PASS);
		return 0;
	}

	// /////////////////////////////////////////////////////////////////////////
	// a bunch of methods to negatively check for objects using selenium calls
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * checks to see if an alert is present on the page
	 * 
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkAlertPresent() {
		// record our action
		recordExpected("Expected to find an alert on the page");
		// check for our object to the visible
		String alert = "";
		boolean isAlertPresent = selHelper.isAlertPresent();
		if (isAlertPresent) {
			alert = selHelper.getAlert();
		}
		if (!isAlertPresent) {
			recordActual("No alert is present on the page", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("An alert with text <b>" + alert + "</b> is present on the page", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an alert is not present on the page
	 * 
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkAlertNotPresent() {
		// record our action
		recordExpected("Expected not to find an alert on the page");
		// check for our object to the visible
		boolean isAlertPresent = selHelper.isAlertPresent();
		if (isAlertPresent) {
			recordActual("An alert is present on the page", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("No alert is present on the page", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a confirmation is correct on the page
	 * 
	 * @param expectedConfirmation
	 *            the expected text of the confirmation
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkConfirmation(String expectedConfirmation) {
		// record our action
		recordExpected("Expected to find confirmation with the text <b>" + expectedConfirmation + "</b> on the page");
		// check for our object to the visible
		String confirmation = "";
		boolean isConfirmationPresent = selHelper.isConfirmationPresent();
		if (isConfirmationPresent) {
			confirmation = selHelper.getConfirmation();
		}
		if (!isConfirmationPresent) {
			recordActual("No confirmation is present on the page", Success.FAIL);
			addError();
			addError();
			return 1;
		}
		if (!expectedConfirmation.equals(confirmation)) {
			recordActual("A confirmation with text <b>" + confirmation + "</b> is present on the page", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("A confirmation with text <b>" + confirmation + "</b> is present on the page", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a confirmation is present on the page
	 * 
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkConfirmationPresent() {
		// record our action
		recordExpected("Expected to find a confirmation on the page");
		// check for our object to the visible
		String confirmation = "";
		boolean isConfirmationPresent = selHelper.isConfirmationPresent();
		if (isConfirmationPresent) {
			confirmation = selHelper.getConfirmation();
		}
		if (!isConfirmationPresent) {
			recordActual("No confirmation is present on the page", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("A confirmation with text <b>" + confirmation + "</b> is present on the page", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a confirmation is not present on the page
	 * 
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkConfirmationNotPresent() {
		// record our action
		recordExpected("Expected to find a confirmation on the page");
		// check for our object to the visible
		boolean isConfirmationPresent = selHelper.isConfirmationPresent();
		if (isConfirmationPresent) {
			recordActual("A confirmation is present on the page", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("No confirmation is present on the page", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a prompt is correct on the page
	 * 
	 * @param expectedPrompt
	 *            the expected text of the confirmation
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkPrompt(String expectedPrompt) {
		// record our action
		recordExpected("Expected to find prompt with the text <b>" + expectedPrompt + "</b> on the page");
		// check for our object to the visible
		String prompt = "";
		boolean isPromptPresent = selHelper.isPromptPresent();
		if (isPromptPresent) {
			prompt = selHelper.getPrompt();
		}
		if (!isPromptPresent) {
			recordActual("No prompt is present on the page", Success.FAIL);
			addError();
			return 1;
		}
		if (!expectedPrompt.equals(prompt)) {
			recordActual("A prompt with text <b>" + prompt + "</b> is present on the page", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("A prompt with text <b>" + prompt + "</b> is present on the page", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a prompt is present on the page
	 * 
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkPromptPresent() {
		// record our action
		recordExpected("Expected to find prompt on the page");
		// check for our object to the visible
		String prompt = "";
		boolean isPromptPresent = selHelper.isPromptPresent();
		if (isPromptPresent) {
			prompt = selHelper.getPrompt();
		}
		if (!isPromptPresent) {
			recordActual("No prompt is present on the page", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("A prompt with text <b>" + prompt + "</b> is present on the page", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a prompt is not present on the page
	 * 
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkPromptNotPresent() {
		// record our action
		recordExpected("Expected not to find prompt on the page");
		// check for our object to the visible
		boolean isPromptPresent = selHelper.isPromptPresent();
		if (isPromptPresent) {
			recordActual("A prompt is present on the page", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("No prompt is present on the page", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a cookie is correct for the page
	 * 
	 * @param cookieName
	 *            the name of the cookie
	 * @param expectedCookieValue
	 *            the expected value of the cookie
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkCookie(String cookieName, String expectedCookieValue) {
		// record our action
		recordExpected("Expected to find cookie with the name <b>" + cookieName + "</b> and a value of <b>"
				+ expectedCookieValue + "</b> stored for the page");
		// check for our object to the visible
		String cookieValue = "";
		boolean isCookiePresent = selHelper.isCookiePresent(cookieName);
		if (isCookiePresent) {
			cookieValue = selHelper.getCookieValue(cookieName);
		}
		if (!isCookiePresent) {
			recordActual("No cookie with the name <b>" + cookieName + "</b> is stored for the page", Success.FAIL);
			addError();
			return 1;
		}
		if (!cookieValue.equals(expectedCookieValue)) {
			recordActual("A cookie with the name <b>" + cookieName + "</b> is stored for the page, but the value "
					+ "of the cookie is " + cookieValue, Success.FAIL);
			addError();
			return 1;
		}
		recordActual("A cookie with the name <b>" + cookieName + "</b> and a value of <b>" + cookieValue
				+ "</b> is stored for the page", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a cookie is present on the page
	 * 
	 * @param expectedCookieName
	 *            the name of the cookie
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkCookiePresent(String expectedCookieName) {
		// record our action
		recordExpected("Expected to find cookie with the name <b>" + expectedCookieName + "</b> stored for the page");
		// check for our object to the visible
		String cookieValue = "";
		boolean isCookiePresent = selHelper.isCookiePresent(expectedCookieName);
		if (isCookiePresent) {
			cookieValue = selHelper.getCookieValue(expectedCookieName);
		}
		if (!isCookiePresent) {
			recordActual("No cookie with the name <b>" + expectedCookieName + "</b> is stored for the page",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual("A cookie with the name <b>" + expectedCookieName + "</b> and a value of <b>" + cookieValue
				+ "</b> is stored for the page", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if a cookie is not present on the page
	 * 
	 * @param unexpectedCookieName
	 *            the name of the cookie
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkCookieNotPresent(String unexpectedCookieName) {
		// record our action
		recordExpected(
				"Expected to find no cookie with the name <b>" + unexpectedCookieName + "</b> stored for the page");
		// check for our object to the visible
		boolean isCookiePresent = selHelper.isCookiePresent(unexpectedCookieName);
		if (isCookiePresent) {
			recordActual("A cookie with the name <b>" + unexpectedCookieName + "</b> is stored for the page",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual("No cookie with the name <b>" + unexpectedCookieName + "</b> is stored for the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element is visible on the page
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkElementDisplayed(Locators type, String locator)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (!selHelper.isElementDisplayed(type, locator)) {
			if (selHelper.waitForElementDisplayed(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> visible on the page");
		recordActual("The element with " + type + " <i>" + locator + "</i> is present and visible on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element is not visible on the page
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkElementNotDisplayed(Locators type, String locator)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (selHelper.isElementDisplayed(type, locator)) {
			if (selHelper.waitForElementNotDisplayed(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator
				+ "</i> present, but not visible on the page");
		recordActual("The element with " + type + " <i>" + locator + "</i> is present and not visible on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an object is checked on the page
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int checkElementChecked(Locators type, String locator)
			throws InvalidLocatorTypeException, InvalidActionException {
		// wait for the element
		if (!selHelper.isElementPresent(type, locator)) {
			if (selHelper.waitForElementPresent(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> checked on the page");
		// check for our object to the visible
		if (!selHelper.isElementChecked(type, locator)) {
			recordActual("The element with " + type + " <i>" + locator + "</i> is not checked on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element with " + type + " <i>" + locator + "</i> is checked on the page", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an object is not checked on the page
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int checkElementNotChecked(Locators type, String locator)
			throws InvalidLocatorTypeException, InvalidActionException {
		// wait for the element
		if (!selHelper.isElementPresent(type, locator)) {
			if (selHelper.waitForElementPresent(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> unchecked on the page");
		// check for our object to the visible
		if (selHelper.isElementChecked(type, locator)) {
			recordActual("The element with " + type + " <i>" + locator + "</i> is checked on the page", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element with " + type + " <i>" + locator + "</i> is unchecked on the page", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an object is visible and checked on the page
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkElementDisplayedAndChecked(Locators type, String locator)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (!selHelper.isElementDisplayed(type, locator)) {
			if (selHelper.waitForElementDisplayed(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> checked on the page");
		// check for our object to the visible
		if (!selHelper.isElementChecked(type, locator)) {
			recordActual("The element with " + type + " <i>" + locator + "</i> is not checked on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element with " + type + " <i>" + locator + "</i> is checked and visible on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an object is visible and not checked on the page
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkElementDisplayedAndUnchecked(Locators type, String locator)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (!selHelper.isElementDisplayed(type, locator)) {
			if (selHelper.waitForElementDisplayed(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> unchecked on the page");
		// check for our object to the visible
		if (selHelper.isElementChecked(type, locator)) {
			recordActual("The element with " + type + " <i>" + locator + "</i> is checked on the page", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element with " + type + " <i>" + locator + "</i> is unchecked and visible on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element is editable on the page
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int checkElementEditable(Locators type, String locator)
			throws InvalidLocatorTypeException, InvalidActionException {
		// wait for the element
		if (!selHelper.isElementPresent(type, locator)) {
			if (selHelper.waitForElementPresent(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> editable on the page");
		// check for our object to the editable
		if (!selHelper.isElementInput(type, locator)) {
			recordActual("The element with " + type + " <i>" + locator + "</i> is present but not an input on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (!selHelper.isElementEnabled(type, locator)) {
			recordActual("The element with " + type + " <i>" + locator + "</i> is present but not editable on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element with " + type + " <i>" + locator + "</i> is present and editable on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element is not editable on the page
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int checkElementNotEditable(Locators type, String locator)
			throws InvalidLocatorTypeException, InvalidActionException {
		// wait for the element
		if (!selHelper.isElementPresent(type, locator)) {
			if (selHelper.waitForElementPresent(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> not editable on the page");
		// check for our object to the editable
		boolean isElementEnabled = false;
		boolean isInput = selHelper.isElementInput(type, locator);
		if (isInput) {
			isElementEnabled = selHelper.isElementEnabled(type, locator);
		}
		if (isElementEnabled) {
			recordActual("The element with " + type + " <i>" + locator + "</i> is present but editable on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element with " + type + " <i>" + locator + "</i> is present and not editable on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element is visible and editable on the page
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkElementDisplayedAndEditable(Locators type, String locator)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (!selHelper.isElementDisplayed(type, locator)) {
			if (selHelper.waitForElementDisplayed(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		// record our action
		recordExpected(
				"Expected to find element with " + type + " <i>" + locator + "</i> visible and editable on the page");
		// check for our object to the editable
		if (!selHelper.isElementInput(type, locator)) {
			recordActual("The element with " + type + " <i>" + locator + "</i> is visible but not an input on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (!selHelper.isElementEnabled(type, locator)) {
			recordActual("The element with " + type + " <i>" + locator + "</i> is visible but not editable on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element with " + type + " <i>" + locator + "</i> is visible and editable on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element is visible and not editable on the page
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkElementDisplayedAndNotEditable(Locators type, String locator)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (!selHelper.isElementDisplayed(type, locator)) {
			if (selHelper.waitForElementDisplayed(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator
				+ "</i> visible and not editable on the page");
		// check for our object to the editable
		boolean isElementEnabled = false;
		boolean isInput = selHelper.isElementInput(type, locator);
		if (isInput) {
			isElementEnabled = selHelper.isElementEnabled(type, locator);
		}
		if (isElementEnabled) {
			recordActual("The element with " + type + " <i>" + locator + "</i> is visible but editable on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element with " + type + " <i>" + locator + "</i> is visible and not editable on the page",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element has an attribute associated with it
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param attribute
	 *            - the attribute to check for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkElementHasAttribute(Locators type, String locator, String attribute)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (!selHelper.isElementPresent(type, locator)) {
			if (selHelper.waitForElementPresent(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> with attribute <b>"
				+ attribute + "</b>");
		Map<String, String> attributes = selHelper.getAllAttributes(type, locator);
		Set<String> keys = attributes.keySet();
		String[] array = keys.toArray(new String[keys.size()]);
		// record our action
		if (General.doesArrayContain(array, attribute)) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> contains the attribute of <b>"
					+ attribute + "</b>", Success.PASS);
			return 0;
		}
		recordActual("The element  with " + type + " <i>" + locator + "</i> does not contain the attribute of <b>"
				+ attribute + "</b>" + ", only the values <b>" + Arrays.toString(array) + "</b>", Success.FAIL);
		addError();
		return 1;
	}

	/**
	 * checks to see if an element has an attribute associated with it
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param attribute
	 *            - the attribute to check for
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkElementDoesntHaveAttribute(Locators type, String locator, String attribute)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (!selHelper.isElementPresent(type, locator)) {
			if (selHelper.waitForElementPresent(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> without attribute <b>"
				+ attribute + "</b>");
		Map<String, String> attributes = selHelper.getAllAttributes(type, locator);
		Set<String> keys = attributes.keySet();
		String[] array = keys.toArray(new String[keys.size()]);
		// record our action
		if (General.doesArrayContain(array, attribute)) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> contains the attribute of <b>"
					+ attribute + "</b>", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element  with " + type + " <i>" + locator + "</i> does not contain the attribute of <b>"
				+ attribute + "</b>" + ", only the values <b>" + Arrays.toString(array) + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element has a particular class
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedClass
	 *            - the full expected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkElementHasClass(Locators type, String locator, String expectedClass)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (!selHelper.isElementPresent(type, locator)) {
			if (selHelper.waitForElementPresent(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> with class <b>"
				+ expectedClass + "</b>");
		String actualClass = selHelper.getAttribute(type, locator, "class");
		// record our action
		if (actualClass.equals(expectedClass)) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> has a class value of <b>"
					+ expectedClass + "</b>", Success.PASS);
			return 0;
		}
		recordActual(
				"The element  with " + type + " <i>" + locator + "</i> has a class value of <b>" + actualClass + "</b>",
				Success.FAIL);
		addError();
		return 1;
	}

	/**
	 * checks to see if an element contains a particular class
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedClass
	 *            - the expected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkElementContainsClass(Locators type, String locator, String expectedClass)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (!selHelper.isElementPresent(type, locator)) {
			if (selHelper.waitForElementPresent(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> containing class <b>"
				+ expectedClass + "</b>");
		String actualClass = selHelper.getAttribute(type, locator, "class");
		// record our action
		if (actualClass.contains(expectedClass)) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> has a class value of <b>" + actualClass
					+ "</b>, which contains <b>" + expectedClass + "</b>", Success.PASS);
			return 0;
		}
		recordActual(
				"The element  with " + type + " <i>" + locator + "</i> has a class value of <b>" + actualClass + "</b>",
				Success.FAIL);
		addError();
		return 1;
	}

	/**
	 * checks to see if an element does not contain a particular class
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param unexpectedClass
	 *            - the unexpected class value
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkElementDoesntContainClass(Locators type, String locator, String unexpectedClass)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (!selHelper.isElementPresent(type, locator)) {
			if (selHelper.waitForElementPresent(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> without class <b>"
				+ unexpectedClass + "</b>");
		String actualClass = selHelper.getAttribute(type, locator, "class");
		// record our action
		if (actualClass.contains(unexpectedClass)) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> has a class value of <b>" + actualClass
					+ "</b>, which contains <b>" + unexpectedClass + "</b>", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element  with " + type + " <i>" + locator + "</i> does not contain a class value of <b>"
				+ unexpectedClass + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an option is available to be selected on the page
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param option
	 *            the option expected in the list
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkIfOptionInSelect(Locators type, String locator, String option)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (!selHelper.isElementEnabled(type, locator)) {
			if (selHelper.waitForElementEnabled(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> with the option <b>" + option
				+ "</b> available to be" + " selected on the page");
		// check for our object to the editable
		String[] allOptions = selHelper.getSelectOptions(type, locator);
		if (!Arrays.asList(allOptions).contains(option)) {
			recordActual(
					"The element with " + type + " <i>" + locator
							+ "</i> is editable and present but does not contain the option " + "<b>" + option + "</b>",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element with " + type + " <i>" + locator
				+ "</i> is editable and present and contains the option " + "<b>" + option + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an option is not available to be selected on the page
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param option
	 *            the option not expected in the list
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidLocatorTypeException
	 * @throws InvalidActionException
	 */
	public int checkIfOptionNotInSelect(Locators type, String locator, String option)
			throws InvalidActionException, InvalidLocatorTypeException {
		// wait for the element
		if (!selHelper.isElementEnabled(type, locator)) {
			if (selHelper.waitForElementEnabled(type, locator) == 1) {
				addError();
				return 1;
			}
		}
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> without the option <b>"
				+ option + "</b> available to be" + " selected on the page");
		// check for our object to the editable
		String[] allOptions = selHelper.getSelectedValues(type, locator);
		if (Arrays.asList(allOptions).contains(option)) {
			recordActual(
					"The element with " + type + " <i>" + locator
							+ "</i> is editable and present and contains the option " + "<b>" + option + "</b>",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual(
				"The element with " + type + " <i>" + locator
						+ "</i> is editable and present but does not contain the option " + "<b>" + option + "</b>",
				Success.PASS);
		return 0;
	}

	/**
	 * checks to see if text is visible on the page
	 * 
	 * @param expectedTexts
	 *            the expected text to be visible
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkTextVisible(String... expectedTexts) {
		// record our action
		int errors = 0;
		for (String expectedText : expectedTexts) {
			recordExpected("Expected to find text <b>" + expectedText + "</b> visible on the page");
			// check for our object to the visible
			boolean isPresent = selHelper.isTextPresent(expectedText);
			if (!isPresent) {
				recordActual("The text <b>" + expectedText + "</b> is not visible on the page", Success.FAIL);
				addError();
				errors++;
			} else {
				recordActual("The text <b>" + expectedText + "</b> is visible on the page", Success.PASS);
			}
		}
		return errors;
	}

	/**
	 * checks to see if text is not visible on the page
	 * 
	 * @param expectedTexts
	 *            the expected text to be invisible
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkTextNotVisible(String... expectedTexts) {
		// record our action
		int errors = 0;
		for (String expectedText : expectedTexts) {
			recordExpected("Expected not to find text <b>" + expectedText + "</b> visible on the page");
			// check for our object to the visible
			boolean isPresent = selHelper.isTextPresent(expectedText);
			if (isPresent) {
				recordActual("The text <b>" + expectedText + "</b> is visible on the page", Success.FAIL);
				addError();
				errors++;
			} else {
				recordActual("The text <b>" + expectedText + "</b> is not visible on the page", Success.PASS);
			}
		}
		return errors;
	}

	/**
	 * checks to see if text is visible on the page
	 * 
	 * @param expectedTexts
	 *            the expected text to be visible
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int checkTextVisibleOR(String... expectedTexts) {
		// record our action
		int errors = 0;
		boolean isPresent = false;
		String foundText = "";
		String allTexts = "";
		for (String expectedText : expectedTexts) {
			allTexts += "<b>" + expectedText + "</b> or ";
		}
		allTexts = allTexts.substring(0, allTexts.length() - 4);
		recordExpected("Expected to find text " + allTexts + " visible on the page");
		// check for our object to the visible
		for (String expectedText : expectedTexts) {
			isPresent = selHelper.isTextPresent(expectedText);
			if (isPresent) {
				foundText = expectedText;
				break;
			}
		}
		if (!isPresent) {
			recordActual("None of the texts " + allTexts.replace(" or ", ", ") + " are visible on the page",
					Success.FAIL);
			addError();
			errors++;
			return errors;
		}
		recordActual("The text <b>" + foundText + "</b> is visible on the page", Success.PASS);
		return errors;
	}

	/**
	 * compares the expected element value with the actual value from an element
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedValue
	 *            the expected value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int compareTextValue(Locators type, String locator, String expectedValue)
			throws InvalidLocatorTypeException, InvalidActionException {
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> having a value of <b>"
				+ expectedValue + "</b>");
		// check for our object to the present on the page
		String elementValue = "";
		boolean isPresent = selHelper.isElementPresent(type, locator);
		if (isPresent) {
			elementValue = selHelper.getText(type, locator);
		}
		if (!isPresent) {
			recordActual("The element with " + type + " <i>" + locator + "</i> is not present on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (!elementValue.equals(expectedValue)) {
			recordActual(
					"The element with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual(
				"The element with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
				Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element value with the actual value from an element
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedValue
	 *            the expected value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int compareTextValueContains(Locators type, String locator, String expectedValue)
			throws InvalidLocatorTypeException, InvalidActionException {
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> contains the value of <b>"
				+ expectedValue + "</b>");
		// check for our object to the present on the page
		String elementValue = "";
		boolean isPresent = selHelper.isElementPresent(type, locator);
		if (isPresent) {
			elementValue = selHelper.getText(type, locator);
		}
		if (!isPresent) {
			recordActual("The element with " + type + " <i>" + locator + "</i> is not present on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (!elementValue.contains(expectedValue)) {
			recordActual(
					"The element with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual(
				"The element with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
				Success.PASS);
		return 0;
	}

	/**
	 * compares the actual URL a page is on to the expected URL
	 * 
	 * @param expectedTitle
	 *            the friendly name of the page
	 * @return Integer: 1 if a failure and 0 if a pass
	 */
	public int compareTitle(String expectedTitle) {
		// record our action
		recordExpected("Expected to be on page with the title of <i>" + expectedTitle + "</i>");
		String actualTitle = selHelper.getTitle();
		if (!actualTitle.equalsIgnoreCase(expectedTitle)) {
			recordActual("The page title reads <b>" + actualTitle + "</b>", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The page title reads <b>" + actualTitle + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element input value with the actual value from an
	 * element
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int compareInputValue(Locators type, String locator, String expectedValue)
			throws InvalidLocatorTypeException, InvalidActionException {
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> having a value of <b>"
				+ expectedValue + "</b>");
		// check for our object to the present on the page
		String elementValue = "";
		boolean isPresent = selHelper.isElementPresent(type, locator);
		if (isPresent) {
			elementValue = selHelper.getValue(type, locator);
		}
		if (!isPresent) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (!elementValue.equals(expectedValue)) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue
					+ "</b>", Success.FAIL);
			addError();
			return 1;
		}
		recordActual(
				"The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
				Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element css attribute value with the actual css
	 * attribute value from an element
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param attribute
	 *            - the css attribute to be checked
	 * @param expectedValue
	 *            the expected css value of the passed attribute of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int compareCssValue(Locators type, String locator, String attribute, String expectedValue)
			throws InvalidLocatorTypeException, InvalidActionException {
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> having a css attribute of <i>"
				+ attribute + "</i> with a value of <b>" + expectedValue + "</b>");
		// check for our object to the present on the page
		String elementCssValue = "";
		boolean isPresent = selHelper.isElementPresent(type, locator);
		if (isPresent) {
			elementCssValue = selHelper.getCss(type, locator, attribute);
		}
		if (!isPresent) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (!elementCssValue.equals(expectedValue)) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> has a css attribute of <i>" + attribute
					+ "</i> with the value of <b>" + elementCssValue + "</b>", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element  with " + type + " <i>" + locator + "</i> has a css attribute of <i>" + attribute
				+ "</i> with the value of <b>" + elementCssValue + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * checks to see if an element select value exists
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param selectValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int checkSelectValuePresent(Locators type, String locator, String selectValue)
			throws InvalidLocatorTypeException, InvalidActionException {
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> having a select value of <b>"
				+ selectValue + "</b>");
		// check for our object to the present on the page
		String[] elementValues = null;
		boolean isPresent = selHelper.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = selHelper.isElementInput(type, locator);
		}
		if (!isPresent) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (isInput) {
			isElementEnabled = selHelper.isElementEnabled(type, locator);
		}
		if (!isInput) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not an input on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (isElementEnabled) {
			elementValues = selHelper.getSelectedValues(type, locator);
		}
		if (!isElementEnabled) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not editable on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (General.doesArrayContain(elementValues, selectValue)) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> contains the value of <b>" + selectValue
					+ "</b>", Success.PASS);
			return 0;
		}
		recordActual("The element  with " + type + " <i>" + locator + "</i> does not contain the value of <b>"
				+ selectValue + "</b>" + ", only the values <b>" + Arrays.toString(elementValues) + "</b>",
				Success.FAIL);
		addError();
		return 1;
	}

	/**
	 * checks to see if an element select value does not exist
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param selectValue
	 *            the unexpected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int checkSelectValueNotPresent(Locators type, String locator, String selectValue)
			throws InvalidLocatorTypeException, InvalidActionException {
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> without a select value of <b>"
				+ selectValue + "</b>");
		// check for our object to the present on the page
		String[] elementValues = null;
		boolean isPresent = selHelper.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = selHelper.isElementInput(type, locator);
		}
		if (!isPresent) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (isInput) {
			isElementEnabled = selHelper.isElementEnabled(type, locator);
		}
		if (!isInput) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not an input on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (isElementEnabled) {
			elementValues = selHelper.getSelectedValues(type, locator);
		}
		if (!isElementEnabled) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not editable on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (General.doesArrayContain(elementValues, selectValue)) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> contains the value of <b>" + selectValue
					+ "</b>", Success.FAIL);
			addError();
			return 1;
		}
		recordActual("The element  with " + type + " <i>" + locator + "</i> does not contain the value of <b>"
				+ selectValue + "</b>, only the values <b>" + Arrays.toString(elementValues) + "</b>", Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element select value with the actual value from an
	 * element
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int compareSelectedValue(Locators type, String locator, String expectedValue)
			throws InvalidLocatorTypeException, InvalidActionException {
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator
				+ "</i> having a selected value of <b>" + expectedValue + "</b>");
		// check for our object to the present on the page
		String elementValue = "";
		boolean isPresent = selHelper.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = selHelper.isElementInput(type, locator);
		}
		if (!isPresent) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (isInput) {
			isElementEnabled = selHelper.isElementEnabled(type, locator);
		}
		if (!isInput) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not an input on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (isElementEnabled) {
			elementValue = selHelper.getSelectedValue(type, locator);
		}
		if (!isElementEnabled) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not editable on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (!elementValue.equals(expectedValue)) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue
					+ "</b>", Success.FAIL);
			addError();
			return 1;
		}
		recordActual(
				"The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
				Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element select test with the actual value from an
	 * element
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedText
	 *            the expected input text of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int compareSelectedText(Locators type, String locator, String expectedText)
			throws InvalidLocatorTypeException, InvalidActionException {
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> having a selected text of <b>"
				+ expectedText + "</b>");
		// check for our object to the present on the page
		String elementText = "";
		boolean isPresent = selHelper.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = selHelper.isElementInput(type, locator);
		}
		if (!isPresent) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (isInput) {
			isElementEnabled = selHelper.isElementEnabled(type, locator);
		}
		if (!isInput) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not an input on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (isElementEnabled) {
			elementText = selHelper.getSelectedText(type, locator);
		}
		if (!isElementEnabled) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not editable on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (!elementText.equals(expectedText)) {
			recordActual(
					"The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementText + "</b>",
					Success.FAIL);
			addError();
			return 1;
		}
		recordActual(
				"The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementText + "</b>",
				Success.PASS);
		return 0;
	}

	/**
	 * compares the expected element select value with the actual value from an
	 * element
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedValue
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int compareSelectedValueNotEqual(Locators type, String locator, String expectedValue)
			throws InvalidLocatorTypeException, InvalidActionException {
		// record our action
		recordExpected("Expected to find element with " + type + " <i>" + locator
				+ "</i> not having a selected value of <b>" + expectedValue + "</b>");
		// check for our object to the present on the page
		String elementValue = "";
		boolean isPresent = selHelper.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = selHelper.isElementInput(type, locator);
		}
		if (!isPresent) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (isInput) {
			isElementEnabled = selHelper.isElementEnabled(type, locator);
		}
		if (!isInput) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not an input on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (isElementEnabled) {
			elementValue = selHelper.getSelectedValue(type, locator);
		}
		if (!isElementEnabled) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not editable on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (elementValue.equals(expectedValue)) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue
					+ "</b>", Success.FAIL);
			addError();
			return 1;
		}
		recordActual(
				"The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValue + "</b>",
				Success.PASS);
		return 0;
	}

	/**
	 * compares the expected attributes from a select value with the actual
	 * attributes from the element
	 * 
	 * @param type
	 *            - the locator type e.g. Locators.id, Locators.xpath
	 * @param locator
	 *            - the locator string e.g. login, //input[@id='login']
	 * @param expectedValues
	 *            the expected input value of the element
	 * @return Integer: 1 if a failure and 0 if a pass
	 * @throws InvalidActionException
	 * @throws InvalidLocatorTypeException
	 */
	public int compareSelectValues(Locators type, String locator, String... expectedValues)
			throws InvalidLocatorTypeException, InvalidActionException {
		// record our action
		int errors = 0;
		recordExpected("Expected to find element with " + type + " <i>" + locator + "</i> with select values of <b>"
				+ expectedValues + "</b>");
		// check for our object to the present on the page
		String[] elementValues = null;
		boolean isPresent = selHelper.isElementPresent(type, locator);
		boolean isInput = false;
		boolean isElementEnabled = false;
		if (isPresent) {
			isInput = selHelper.isElementInput(type, locator);
		}
		if (!isPresent) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not present on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (isInput) {
			isElementEnabled = selHelper.isElementEnabled(type, locator);
		}
		if (!isInput) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not an input on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		if (isElementEnabled) {
			elementValues = selHelper.getSelectOptions(type, locator);
		}
		if (!isElementEnabled) {
			recordActual("The element  with " + type + " <i>" + locator + "</i> is not editable on the page",
					Success.FAIL);
			addError();
			return 1;
		}
		for (String entry : expectedValues) {
			if (!Arrays.asList(elementValues).contains(entry)) {
				recordActual("The element  with " + type + " <i>" + locator
						+ "</i> does not have the select value of <b>" + entry + "</b>", Success.FAIL);
				addError();
				errors++;
			}
		}
		for (String entry : elementValues) {
			if (!Arrays.asList(expectedValues).contains(entry)) {
				recordActual("The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + entry
						+ "</b> which was not expected", Success.FAIL);
				addError();
				errors++;
			}
		}
		if (errors > 0) {
			return errors;
		}
		recordActual(
				"The element  with " + type + " <i>" + locator + "</i> has the value of <b>" + elementValues + "</b>",
				Success.PASS);
		return 0;
	}

	// //////////////////////////////////////////
	// some custom made selenium helper methods
	// ////////////////////////////////////////

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
	public int countInstancesOf(String fileName, String textToFind) {
		return countInstancesOf(new File(fileName), textToFind);
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
	public int countInstancesOf(File fileName, String textToFind) {
		int count = 0;
		FileReader fr = null;
		BufferedReader reader = null;
		try {
			fr = new FileReader(fileName);
			reader = new BufferedReader(fr);
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line.contains(textToFind)) {
					count++;
				}
			}
		} catch (IOException ioe) {
			log.error(ioe);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
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
	public void replaceInFile(String fileName, String oldText, String newText) {
		replaceInFile(new File(fileName), oldText, newText);
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
	public void replaceInFile(File fileName, String oldText, String newText) {
		String line = "";
		String oldtext = "";

		FileReader fr = null;
		BufferedReader reader = null;
		FileWriter writer = null;
		try {
			fr = new FileReader(fileName);
			reader = new BufferedReader(fr);
			while ((line = reader.readLine()) != null) {
				oldtext += line + "\r\n";
			}
		} catch (IOException ioe) {
			log.error(ioe);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}

		// replace a word in a file
		String newtext = oldtext.replaceAll(oldText, newText);

		try {
			writer = new FileWriter(fileName);
			writer.write(newtext);
		} catch (IOException ioe) {
			log.error(ioe);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
	}
}