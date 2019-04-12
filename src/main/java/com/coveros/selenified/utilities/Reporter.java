/*
 * Copyright 2019 Coveros, Inc.
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

package com.coveros.selenified.utilities;

import com.coveros.selenified.Browser;
import com.coveros.selenified.Browser.BrowserName;
import com.coveros.selenified.Capabilities;
import com.coveros.selenified.application.App;
import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import com.coveros.selenified.services.HTTP;
import com.coveros.selenified.services.Request;
import com.coveros.selenified.services.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.testng.log4testng.Logger;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.coveros.selenified.utilities.Constants.DIV_I;
import static com.coveros.selenified.utilities.Constants.END_IDIV;

/**
 * A custom output file, recording all details of every step performed, both
 * actions and app. Actions, expected results, and actual results are captured.
 * All asserts have a screenshot taken for traceability, while all failing
 * actions also have a screenshot taken to assist with debugging purposes
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 4/4/2019
 */
public class Reporter {

    private static final String PASSORFAIL = "PASSORFAIL";
    private static final Logger log = Logger.getLogger(Reporter.class);
    // constants
    private static final String START_ROW = "   <tr>\n";
    private static final String START_CELL = "    <td>";
    private static final String END_CELL = "</td>\n";
    private static final String END_ROW = "   </tr>\n";
    private static final String ONCLICK_TOGGLE = "<a href='javascript:void(0)' onclick='toggle(\"";
    private static final String END_SPAN = "</span>";
    private static final String SPAN_ID = "<span id='";
    private static final String DISPLAY_NONE = "' style='display:none;'>";
    private static final String DIV = "<div>";
    private static final String END_DIV = "</div>";


    // the image width for reporting
    private static final int EMBEDDED_IMAGE_WIDTH = 300;
    private final String url;
    private final String suite;
    private final String group;
    private final String version;
    private final String author;
    private final String objectives;
    private final String test;
    private final String directory;
    private final File file;
    private final String filename;
    private final List<String> screenshots = new ArrayList<>();
    private final Capabilities capabilities;
    private App app = null;
    // timing of the test
    private long startTime;
    private long lastTime = 0;
    // this will track the step numbers
    private int stepNum = 0;
    // this will keep track of step successes
    private int passes = 0;
    private int fails = 0;
    private int checks = 0;

    /**
     * Creates a new instance of the Reporter, which will serve as the
     * detailed log
     *
     * @param directory    - a string of the directory holding the files
     * @param test         - a string value of the test name, typically the method name
     * @param capabilities - the capabilities of the tests that are running
     * @param url          - the url all of the tests are running against
     * @param suite        - the test suite associated with the particular test
     * @param group        - any testng groups associated with the particular test
     * @param author       - the author associated with the particular test
     * @param version      - the version of the test suite associated with the particular
     *                     test
     * @param objectives   - the test objectives, taken from the testng description
     */
    @SuppressWarnings("squid:S00107")
    public Reporter(String directory, String test, Capabilities capabilities, String url, String suite, String group,
                    String author, String version, String objectives) throws InvalidBrowserException, InvalidProxyException {
        if (directory == null) {
            this.directory = ".";
        } else {
            this.directory = directory;
        }
        this.test = test;
        if (capabilities == null) {
            this.capabilities = new Capabilities(new Browser("None"));
        } else {
            this.capabilities = capabilities;
        }
        this.url = url;
        this.suite = suite;
        this.group = group;
        this.author = author;
        this.version = version;
        this.objectives = objectives;
        filename = generateFilename();
        file = new File(directory, filename + ".html");
        setupFile();
        setStartTime();
        createOutputHeader();
    }

    /**
     * Retrieves the directory in string form of the output files
     *
     * @return String: filename
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Retrieves the filename in string form of the output file
     *
     * @return String: filename
     */
    public String getFileName() {
        return filename;
    }

    /**
     * Retrieves the current error count of the test
     *
     * @return Integer: the number of errors current encountered on the current
     * test
     */
    public int getFails() {
        return fails;
    }

    //////////////////////////////////////

    /**
     * Determines if a 'real' browser is being used. If the browser is NONE or
     * HTMLUNIT it is not considered a real browser
     *
     * @return Boolean: is the browser a 'real' browser
     */
    private boolean isRealBrowser() {
        Browser browser = capabilities.getBrowser();
        return browser.getName() != BrowserName.NONE && browser.getName() != BrowserName.HTMLUNIT;
    }

    /**
     * Retrieves the App class associated with the output file which controls all actions within
     * the browser
     *
     * @return App: the App class associated with the output file
     */
    public App getApp() {
        return this.app;
    }

    /**
     * Sets the App class which controls all actions within the browser
     *
     * @param app - the application to be tested, contains all control elements
     */
    public void setApp(App app) {
        this.app = app;
    }

    /**
     * Generates a unique filename, based on the package, class, method name, and invocation count
     *
     * @return String: the filename
     */
    private String generateFilename() {
        String counter = "";
        if (capabilities.getInstance() > 0) {
            counter = "_" + capabilities.getInstance();
        }
        return test + counter;
    }

    /**
     * Creates the directory and file to hold the test output file
     */
    private void setupFile() {
        if (!new File(directory).exists() && !new File(directory).mkdirs()) {
            try {
                throw new IOException("Unable to create output directory");
            } catch (IOException e) {
                log.info(e);
            }
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
     * Replaces an occurrence of a string within a file
     *
     * @param oldText - the text to be replaced
     * @param newText - the text to be replaced with
     */
    private void replaceInFile(String oldText, String newText) {
        StringBuilder oldContent = new StringBuilder();

        try (FileReader fr = new FileReader(file); BufferedReader reader = new BufferedReader(fr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                oldContent.append(line);
                oldContent.append("\r\n");
            }
        } catch (IOException e) {
            log.error(e);
        }

        // replace a word in a file
        String newContent = oldContent.toString().replaceAll(oldText, newText);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(newContent);
        } catch (IOException ioe) {
            log.error(ioe);
        }
    }

    /**
     * Removes all elements that cannot be converted to pdf, this method is to be used
     * before converting the html file to pdf with openhtmltopdf.pdfboxout.PdfRendererBuilder
     */
    private String getHtmlForPDFConversion() throws IOException {
        StringBuilder oldContent = new StringBuilder();

        FileReader fr = new FileReader(file);
        try (BufferedReader reader = new BufferedReader(fr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                oldContent.append(line);
                oldContent.append("\r\n");
            }
        }

        // replace all non convertible elements with empty text or modify for conversion
        String str = oldContent.toString()
                .replaceAll("<script type='text/javascript'>(?s).*</script>", "")
                .replaceAll("<tr>\\s*<th>View Results</th>(?s).*?</tr>", "")
                .replaceAll("&nbsp;", " ");

        String imagePattern = "(<img(?s).*? src='(.*?)'(?s).*?)</img>";
        Pattern r = Pattern.compile(imagePattern);
        Matcher m = r.matcher(str);
        int imageCount = 0;
        while (m.find()) {
            str = str.replaceFirst("<a href='javascript:void\\(0\\)'(?s).*?(<img(?s).*? src='(.*?)'(?s).*?)" +
                            " style(?s).*?</img>",
                    "<a href=\"#image-" + imageCount + "\">View Screenshot</a>");
            str = str.replaceFirst("</body>", "<p style='page-break-before: always' id='image-" + imageCount++ + "'></p" +
                    ">" +
                    m.group().replaceAll("width='300px' style(?s).*?'>", "height='600px' width='1000px'>") + "</body>");
        }
        return str;
    }

    /**
     * Captures the entire page screen shot, and created an HTML file friendly
     * link to place in the output file
     *
     * @return String: the image link string
     */
    public String captureEntirePageScreenshot() {
        String imageName = generateImageName();
        String imageLink = generateImageLink(imageName);
        try {
            app.takeScreenshot(imageName);
            screenshots.add(imageName);
        } catch (Exception e) {
            log.error(e);
            imageLink = "<br/><b><font class='fail'>No Screenshot Available</font></b>";
        }
        return imageLink;
    }

    /**
     * A simple method to allow posting a screenshot of the app in it's current state into the detailed report
     */
    public void recordScreenshot() {
        check("", "", "");
    }

    /**
     * Helper to recordStep, which takes in a check being performed, and determines if a wait is
     * occuring or not. If no wait, no action is recorded. If a wait was performed, that wait is
     * added to the check, and provided back as the action
     *
     * @param check   - the check being performed
     * @param waitFor - how long was something waited for. Provide 0 if no wait, and therefore no action
     * @return String: the wait being performed as the check, or nothing
     */
    private String getAction(String check, double waitFor) {
        String action = "";
        if (waitFor > 0) {
            action = "Waiting up to " + waitFor + " seconds " + check;
        }
        return action;
    }

    /**
     * Helper to recordStep, which takes in some result, and appends a time waited, if
     * appropriate. If timeTook is greater than zero, some time was waited along with
     * the action, and the returned result will reflect that
     *
     * @param actual   - the actual outcome from the check
     * @param timeTook - how long something took to run, provide 0 if it was an immediate check, and actual
     *                 will be returned unaltered
     * @return String: the actual response, prepended with a wait time if appropriate
     */
    private String getActual(String actual, double timeTook) {
        if (timeTook > 0) {
            String lowercase = actual.substring(0, 1).toLowerCase();
            actual = "After waiting for " + timeTook + " seconds, " + lowercase + actual.substring(1);
        }
        return actual;
    }

    /**
     * Records the performed check as a pass to the output file. A screenshot will be taken for traceability
     * This method takes in a check being performed, and determines if a wait is
     * occurring or not. If no wait, no action is recorded. If a wait was performed, that wait is
     * added to the check, and recorded as the action. The check is used as the expected outcome, and the actual
     * input is used for actual. If it took some time (timeTook greater than zero), than the actual result will
     * be updated to reflect the time took.
     * If a 'real' browser is not being used (not NONE or HTMLUNIT), then no screenshot will be taken
     *
     * @param check    - the check being performed
     * @param waitFor  - how long was something waited for. Provide 0 if no wait, and therefore no action
     * @param actual   - the actual outcome from the check
     * @param timeTook - how long something took to run, provide 0 if it was an immediate check, and actual
     *                 will be returned unaltered
     */
    public void pass(String check, double waitFor, String actual, double timeTook) {
        passes++;
        recordStep(getAction(check, waitFor), "Expected " + check, getActual(actual, timeTook), true, Success.PASS);
    }

    /**
     * Records the performed check as a fail to the output file. A screenshot will be taken for traceability
     * This method takes in a check being performed, and determines if a wait is
     * occurring or not. If no wait, no action is recorded. If a wait was performed, that wait is
     * added to the check, and recorded as the action. The check is used as the expected outcome, and the actual
     * input is used for actual. If it took some time (timeTook greater than zero), than the actual result will
     * be updated to reflect the time took.
     * If a 'real' browser is not being used (not NONE or HTMLUNIT), then no screenshot will be taken
     *
     * @param check    - the check being performed
     * @param waitFor  - how long was something waited for. Provide 0 if no wait, and therefore no action
     * @param actual   - the actual outcome from the check
     * @param timeTook - how long something took to run, provide 0 if it was an immediate check, and actual
     *                 will be returned unaltered
     */
    public void fail(String check, double waitFor, String actual, double timeTook) {
        fails++;
        recordStep(getAction(check, waitFor), "Expected " + check, getActual(actual, timeTook), true, Success.FAIL);
    }

    /**
     * Records the performed step as a pass to the output file. This includes the
     * action taken if any, the expected result, and the actual result.
     *
     * @param action         - the step that was performed
     * @param expectedResult - the result that was expected to occur
     * @param actualResult   - the result that actually occurred
     */
    public void pass(String action, String expectedResult, String actualResult) {
        passes++;
        recordStep(action, expectedResult, actualResult, false, Success.PASS);
    }

    /**
     * Records the performed step as a check to the output file. A screenshot will be taken for traceability and debugging purposes.
     * This includes the action taken if any, the expected result, and the actual result.
     * If a 'real' browser is not being used (not NONE or HTMLUNIT), then no screenshot will be taken
     *
     * @param action         - the step that was performed
     * @param expectedResult - the result that was expected to occur
     * @param actualResult   - the result that actually occurred
     */
    public void check(String action, String expectedResult, String actualResult) {
        checks++;
        recordStep(action, expectedResult, actualResult, true, Success.CHECK);
    }

    /**
     * Records the performed step as a fail to the output file. A screenshot will be taken for traceability and debugging purposes.
     * This includes the action taken if any, the expected result, and the actual result.
     * If a 'real' browser is not being used (not NONE or HTMLUNIT), then no screenshot will be taken
     *
     * @param action         - the step that was performed
     * @param expectedResult - the result that was expected to occur
     * @param actualResult   - the result that actually occurred
     */
    public void fail(String action, String expectedResult, String actualResult) {
        fails++;
        recordStep(action, expectedResult, actualResult, true, Success.FAIL);
    }

    /**
     * Records the performed step to the output file. This includes the action taken if any, the
     * expected result, and the actual result. If a screenshot is desired, indicate as such. If
     * a 'real' browser is not being used (not NONE or HTMLUNIT), then no screenshot will be taken
     *
     * @param action         - the step that was performed
     * @param expectedResult - the result that was expected to occur
     * @param actualResult   - the result that actually occurred
     * @param screenshot     - should a screenshot be taken
     * @param success        - the result of the action
     */
    private void recordStep(String action, String expectedResult, String actualResult, Boolean screenshot, Success success) {
        stepNum++;
        String imageLink = "";
        if (screenshot && isRealBrowser()) {
            // get a screen shot of the action
            imageLink = captureEntirePageScreenshot();
        }
        // determine time differences
        Date currentTime = new Date();
        long dTime = currentTime.getTime() - lastTime;
        long tTime = currentTime.getTime() - startTime;
        lastTime = currentTime.getTime();
        try (
                // Reopen file
                FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw)) {
            // record the action
            out.write(START_ROW);
            out.write("    <td align='center'>" + stepNum + ".</td>\n");
            out.write(START_CELL + action + END_CELL);
            out.write(START_CELL + expectedResult + END_CELL);
            out.write(START_CELL + actualResult + imageLink + END_CELL);
            out.write(START_CELL + dTime + "ms / " + tTime + "ms</td>\n");
            out.write("    <td class='" + success.toString().toLowerCase() + "'>" + success + END_CELL);
            out.write(END_ROW);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Creates the specially formatted output header for the particular test
     * case
     */
    private void createOutputHeader() {
        // setup some constants
        String endBracket3 = "   }\n";
        String endBracket4 = "    }\n";
        String boldFont = "    font-weight:bold;\n";
        String swapRow = "   </tr><tr>\n";

        // Open file
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        String datePart = sdf.format(new Date());
        String sTime = stf.format(startTime);
        try (FileWriter fw = new FileWriter(file); BufferedWriter out = new BufferedWriter(fw)) {
            out.write("<html>\n");
            out.write(" <head>\n");
            out.write("  <title>" + test + "</title>\n");
            out.write("  <style type='text/css'>\n");
            out.write("    @page {\n");
            out.write("     size: landscape;\n");
            out.write(endBracket3);
            out.write("   table {\n");
            out.write("    margin-left:auto;margin-right:auto;\n");
            out.write("    width:90%;\n");
            out.write("    border-collapse:collapse;\n");
            out.write(endBracket3);
            out.write("   table, td, th {\n");
            out.write("    border:1px solid black;\n");
            out.write("    padding:0px 10px;\n");
            out.write(endBracket3);
            out.write("   th {\n");
            out.write("    text-align:right;\n");
            out.write(endBracket3);
            out.write("   td {\n");
            out.write("    word-wrap: break-word;\n");
            out.write(endBracket3);
            out.write("   .check {\n");
            out.write("    color:orange;\n");
            out.write(boldFont);
            out.write(endBracket3);
            out.write("   .fail {\n");
            out.write("    color:red;\n");
            out.write(boldFont);
            out.write(endBracket3);
            out.write("   .pass {\n");
            out.write("    color:green;\n");
            out.write(boldFont);
            out.write(endBracket3);
            out.write("   .indent {\n");
            out.write("    position:relative;\n");
            out.write("    left:10px;\n");
            out.write(endBracket3);
            out.write("  </style>\n");
            out.write("  <script type='text/javascript'>\n");
            out.write("   function toggle( imageName ) {\n");
            out.write("    var element = document.getElementById( imageName );\n");
            out.write("    element.src = location.href.match(/^.*\\//) + imageName;\n");
            out.write("    element.style.display = (element.style.display != 'none' ? 'none' : '' );\n");
            out.write(endBracket3);
            out.write("   function display( imageName ) {\n");
            out.write("    window.open( location.href.match(/^.*\\//) + imageName )\n");
            out.write(endBracket3);
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
            out.write(endBracket4);
            out.write(endBracket3);
            out.write("   function getElementsByClassName(oElm, strTagName, strClassName){\n");
            out.write(
                    "    var arrElements = (strTagName == '*' && document.all)? document.all : oElm" +
                            ".getElementsByTagName(strTagName);\n");
            out.write("    var arrReturnElements = new Array();\n");
            out.write("    strClassName = strClassName.replace(/\\-/g, '\\\\-');\n");
            out.write("    var oRegExp = new RegExp('(^|\\s)' + strClassName + '(\\s|$)');\n");
            out.write("    var oElement;\n");
            out.write("    for(var i=0; i<arrElements.length; i++){\n");
            out.write("     oElement = arrElements[i];\n");
            out.write("     if(oRegExp.test(oElement.className)){\n");
            out.write("      arrReturnElements.push(oElement);\n");
            out.write("     }\n");
            out.write(endBracket4);
            out.write("    return (arrReturnElements)\n");
            out.write(endBracket3);
            out.write("   function fixImages( imageName ) {\n");
            out.write("    top.document.title = document.title;\n");
            out.write("    allImgIcons = getElementsByClassName( document, 'img', 'imgIcon' );\n");
            out.write("    for( var element in allImgIcons ) {\n");
            out.write("     element.src = location.href.match(/^.*\\//) + element.src;\n");
            out.write(endBracket4);
            out.write(endBracket3);
            out.write("  </script>\n");
            out.write(" </head>\n");
            out.write(" <body onLoad='fixImages()'>\n");
            out.write("  <table>\n");
            out.write(START_ROW);
            out.write("    <th bgcolor='lightblue'><font size='5'>Test</font></th>\n");
            out.write("    <td bgcolor='lightblue' colspan='3'><font size='5'>" + test + " </font></td>\n");
            out.write(swapRow);
            out.write("    <th>Tester</th>\n");
            out.write("    <td>Automated</td>\n");
            out.write("    <th>Version</th>\n");
            out.write(START_CELL + this.version + END_CELL);
            out.write(swapRow);
            out.write("    <th>Author</th>\n");
            out.write(START_CELL + this.author + END_CELL);
            out.write("    <th rowspan='2'>Test Run Time</th>\n");
            out.write("    <td rowspan='2'>\n");
            out.write("     Start:\t" + sTime + " <br/>\n");
            out.write("     End:\tTIMEFINISHED <br/>\n");
            out.write("     Run Time:\tRUNTIME \n");
            out.write("    </td>\n ");
            out.write(swapRow);
            out.write("    <th>Date Tested</th>\n");
            out.write(START_CELL + datePart + END_CELL);
            out.write(swapRow);
            out.write("    <th>URL Under Test</th>\n");
            out.write(START_CELL + "<a href='" + url + "'>" + url + "</a>" + END_CELL);
            out.write("    <th>Browser</th>\n");
            out.write(START_CELL + capabilities.getBrowser().getDetails() + END_CELL);
            out.write(swapRow);
            out.write("    <th>Testing Group</th>\n");
            out.write(START_CELL + group + END_CELL);
            out.write("    <th>Testing Suite</th>\n");
            out.write(START_CELL + suite + END_CELL);
            out.write(swapRow);
            out.write("    <th>Test Objectives</th>\n");
            out.write("    <td colspan='3'>" + objectives + END_CELL);
            out.write(swapRow);
            out.write("    <th>Overall Results</th>\n");
            out.write("    <td colspan='3' style='padding: 0px;'>\n");
            out.write("     <table style='width: 100%;'><tr>\n");
            out.write("      <td font-size='big' rowspan='2'>PASSORFAIL</td>\n");
            out.write("      <td><b>Steps Performed</b></td><td><b>Steps Passed</b></td>" +
                    "<td><b>Steps Failed</b></td>\n");
            out.write("     </tr><tr>\n");
            out.write("      <td>STEPSPERFORMED</td><td>STEPSPASSED</td><td>STEPSFAILED</td>\n");
            out.write("     </tr></table>\n");
            out.write("    </td>\n");
            out.write(swapRow);
            out.write("    <th>View Results</th>\n");
            out.write("    <td colspan='3'>\n");
            out.write("     <input type='checkbox' name='step' onclick='toggleVis(0,this.checked)' " +
                    "checked='checked'>Step</input>\n");
            out.write("     <input type='checkbox' name='action' onclick='toggleVis(1,this.checked)' " +
                    "checked='checked'>Action</input>\n");
            out.write(
                    "     <input type='checkbox' name='expected' onclick='toggleVis(2,this.checked)' " +
                            "checked='checked'>Expected Results</input>\n");
            out.write(
                    "     <input type='checkbox' name='actual' onclick='toggleVis(3,this.checked)' " +
                            "checked='checked'>Actual Results</input>\n");
            out.write(
                    "     <input type='checkbox' name='times' onclick='toggleVis(4,this.checked)' " +
                            "checked='checked'>Step Times</input>\n");
            out.write("     <input type='checkbox' name='result' onclick='toggleVis(5,this.checked)' " +
                    "checked='checked'>Results</input>\n");
            out.write("    </td>\n");
            out.write(END_ROW);
            out.write("  </table>\n");
            out.write("  <table id='all_results'>\n");
            out.write(START_ROW);
            out.write("    <th align='center'>Step</th><th style='text-align:center'>Action</th>" +
                    "<th style='text-align:center'>Expected Result</th>" +
                    "<th style='text-align:center'>Actual Result</th>" +
                    "<th style='text-align:center'>Step Times</th><th style='text-align:center'>Pass/Fail</th>\n");
            out.write(END_ROW);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Ends and closes the output test file. The HTML is properly ended, and the
     * file is analyzed to determine if the test passed or failed, and that
     * information is updated, along with the overall timing of the test
     */
    public void finalizeReporter(int testStatus) {
        // reopen the file
        try (FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw)) {
            out.write("  </table>\n");
            out.write(" </body>\n");
            out.write("</html>\n");
        } catch (IOException e) {
            log.error(e);
        }
        // Record the metrics
        if ((fails + passes + checks) != stepNum) {
            log.error("There was some error recording your test steps. Step results don't equal steps performed");
        }
        replaceInFile("STEPSPERFORMED", Integer.toString(fails + passes + checks));
        replaceInFile("STEPSPASSED", Integer.toString(passes));
        replaceInFile("STEPSFAILED", Integer.toString(fails));
        if (fails == 0 && checks == 0 && testStatus == 0) {
            replaceInFile(PASSORFAIL, "<font size='+2' class='pass'><b>PASS</b></font>");
        } else if (fails == 0) {
            replaceInFile(PASSORFAIL, "<font size='+2' class='check'><b>CHECK</b" +
                    "></font>");
        } else {
            replaceInFile(PASSORFAIL, "<font size='+2' class='fail'><b>FAIL</b></font>");
        }
        addTimeToReport();
        if (Property.packageResults()) {
            packageTestResults();
        }
        if (Property.generatePDF()) {
            generatePdf();
        }
    }

    /**
     * Updates the output file with timing information, including run time, and finish time
     */
    private void addTimeToReport() {
        // record the time
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        String timeNow = stf.format(new Date());
        long totalTime = (new Date()).getTime() - startTime;
        long time = totalTime / 1000;
        StringBuilder seconds = new StringBuilder(Integer.toString((int) (time % 60)));
        StringBuilder minutes = new StringBuilder(Integer.toString((int) ((time % 3600) / 60)));
        StringBuilder hours = new StringBuilder(Integer.toString((int) (time / 3600)));
        for (int i = 0; i < 2; i++) {
            if (seconds.length() < 2) {
                seconds.insert(0, "0");
            }
            if (minutes.length() < 2) {
                minutes.insert(0, "0");
            }
            if (hours.length() < 2) {
                hours.insert(0, "0");
            }
        }
        replaceInFile("RUNTIME", hours + ":" + minutes + ":" + seconds);
        replaceInFile("TIMEFINISHED", timeNow);
    }

    /**
     * Generates a pdf report in the same directory as the html report
     */
    private void generatePdf() {
        File pdfFile = new File(directory, filename + ".pdf");
        try (OutputStream os = new FileOutputStream(pdfFile)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(getHtmlForPDFConversion(), "file://" + pdfFile.getAbsolutePath()
                    .replaceAll(" ", "%20"));
            builder.toStream(os);
            builder.run();
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * Packages the test result file along with screenshots into a zip file
     */
    private void packageTestResults() {
        File f = new File(directory, filename + "_RESULTS.zip");
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f))) {
            // Add html results to zip file
            ZipEntry e = new ZipEntry(filename);
            out.putNextEntry(e);
            Path path = FileSystems.getDefault().getPath(directory, filename);
            byte[] data = Files.readAllBytes(path);
            out.write(data, 0, data.length);
            out.closeEntry();

            // Add screenshots to zip file
            for (String screenshot : screenshots) {
                ZipEntry s = new ZipEntry(screenshot.replaceAll(".*/", ""));
                out.putNextEntry(s);
                Path screenPath = FileSystems.getDefault().getPath(screenshot);
                byte[] screenData = Files.readAllBytes(screenPath);
                out.write(screenData, 0, screenData.length);
                out.closeEntry();
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Generates the HTML friendly link for the image
     *
     * @param imageName the name of the image being embedded
     * @return String: the link for the image which can be written out to the
     * html file
     */
    private String generateImageLink(String imageName) {
        StringBuilder imageLink = new StringBuilder("<br/>");
        if (imageName.length() >= directory.length() + 1) {
            imageLink.append(ONCLICK_TOGGLE).
                    append(imageName.substring(directory.length() + 1)).
                    append("\")'>Toggle Screenshot Thumbnail</a>");
            imageLink.append(" <a href='javascript:void(0)' onclick='display(\"").
                    append(imageName.substring(directory.length() + 1)).
                    append("\")'>View Screenshot Fullscreen</a>");
            imageLink.append("<br/><img id='").
                    append(imageName.substring(directory.length() + 1)).
                    append("' border='1px' src='").
                    append(imageName.substring(directory.length() + 1)).
                    append("' width='").
                    append(EMBEDDED_IMAGE_WIDTH).
                    append("px' style='display:none;'></img>");
        } else {
            imageLink.append("<b><font class='fail'>No Image Preview</font></b>");
        }
        return imageLink.toString();
    }

    /**
     * Generates a unique image name
     *
     * @return String: the name of the image file as a PNG
     */
    private String generateImageName() {
        return directory + "/" + getUUID() + ".png";
    }

    /**
     * Determines the current time and sets the 'last time' to the current time
     */
    private void setStartTime() {
        startTime = (new Date()).getTime();
        lastTime = startTime;
    }

    /**
     * Formats the response parameters to be 'prettily' printed out in HTML
     *
     * @param response - the http response to be formatted.
     * @return String: a 'prettily' formatted string that is HTML safe to output
     */
    public static String formatResponse(Response response) {
        if (response == null) {
            return "";
        }
        StringBuilder output = new StringBuilder();
        if (response.isData()) {
            output.append(DIV_I);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if (response.getArrayData() != null) {
                output.append(gson.toJson(response.getArrayData()));
            }
            if (response.getObjectData() != null) {
                output.append(gson.toJson(response.getObjectData()));
            }
            output.append(END_IDIV);
        }
        return formatHTML(output.toString());
    }

    /**
     * Takes a generic string and replaces spaces and new lines with HTML
     * friendly pieces for display purposes
     *
     * @param string - the regular string to be formatted into an HTML pretty
     *               rendering string
     * @return String : the replaced result
     */
    public static String formatHTML(String string) {
        if (string == null) {
            return "";
        }
        return string.replaceAll(" ", "&nbsp;").replaceAll("\n", "<br/>");
    }

    /**
     * From an object map passed in, building a key value set, properly html
     * formatted for used in reporting
     *
     * @param keyPairs - the key value set
     * @return String: an html formatting string
     */
    public static String formatKeyPair(Map<String, Object> keyPairs) {
        if (keyPairs == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : keyPairs.entrySet()) {
            stringBuilder.append(DIV);
            stringBuilder.append(entry.getKey());
            stringBuilder.append(" : ");
            stringBuilder.append(Reporter.formatHTML(String.valueOf(entry.getValue())));
            stringBuilder.append(END_DIV);
        }
        return stringBuilder.toString();
    }

    /**
     * Formats the request parameters to be 'prettily' printed out in HTML
     *
     * @param params - the parameters to be formatted. Either a JSON object, or a
     *               hashmap
     * @return String: a 'prettily' formatted string that is HTML safe to output
     */
    public static String getRequestPayloadOutput(Request params, File file) {
        StringBuilder payload = new StringBuilder();
        String uuid = getUUID();
        payload.append(ONCLICK_TOGGLE).append(uuid).append("\")'>Toggle Payload</a> ");
        payload.append(SPAN_ID).append(uuid).append(DISPLAY_NONE);
        if (params != null && params.getJsonPayload() != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            payload.append(DIV);
            payload.append(formatHTML(gson.toJson(params.getJsonPayload())));
            payload.append(END_DIV);
        }
        if (params != null && params.getMultipartData() != null) {
            for (Map.Entry<String, Object> entry : params.getMultipartData().entrySet()) {
                payload.append(DIV);
                payload.append(entry.getKey());
                payload.append(" : ");
                payload.append(entry.getValue());
                payload.append(END_DIV);
            }
        }
        if (file != null) {
            payload.append("<div> with file: <a href='file:///").append(file.getAbsoluteFile()).append("'>").append(file.getName()).append("</a></div>");
        }
        payload.append(END_SPAN);
        if (payload.toString().equals(ONCLICK_TOGGLE + uuid + "\")'>Toggle Payload</a> " + SPAN_ID + uuid + DISPLAY_NONE + END_SPAN)) {
            return "";
        } else {
            return payload.toString();
        }
    }

    /**
     * Looks for the simple login credentials, username and password, and if
     * they are both set, turns that into a string which will be formatted for
     * HTML to be printed into the output file
     *
     * @param http - the http object that is making the call
     * @return String: an HTML formatted string with the username and password -
     * if they are both set
     */
    public static String getCredentialStringOutput(HTTP http) {
        if (http == null || !http.useCredentials()) {
            return "";
        }
        StringBuilder credentials = new StringBuilder();
        String uuid = getUUID();
        credentials.append(ONCLICK_TOGGLE).append(uuid).append("\")'>Toggle Credentials</a> ");
        credentials.append(SPAN_ID).append(uuid).append(DISPLAY_NONE);
        credentials.append("<div><i>");
        credentials.append("Username: ");
        credentials.append(http.getUser());
        credentials.append("</div><div>");
        credentials.append("Password: ");
        credentials.append(http.getPass());
        credentials.append("</i></div>");
        credentials.append(END_SPAN);
        return credentials.toString();
    }

    /**
     * Takes the headers set in the HTTP request, and writes them to the output
     * file, in properly HTML formatted fashion
     *
     * @param http - the http object that is making the call
     * @return String: an HTML formatted string with headers
     */
    public static String getRequestHeadersOutput(HTTP http) {
        if (http == null) {
            return "";
        }
        StringBuilder requestHeaders = new StringBuilder();
        String uuid = getUUID();
        requestHeaders.append(ONCLICK_TOGGLE).append(uuid).append("\")'>Toggle Headers</a> ");
        requestHeaders.append(SPAN_ID).append(uuid).append(DISPLAY_NONE);
        requestHeaders.append(formatKeyPair(http.getHeaders()));
        requestHeaders.append(END_SPAN);
        return requestHeaders.toString();
    }

    /**
     * Takes the headers returned in the HTTP response, and writes them to the output
     * file, in properly HTML formatted fashion
     *
     * @param response - the response object obtained from the call
     * @return String: an HTML formatted string with headers
     */
    public static String getResponseHeadersOutput(Response response) {
        if (response == null) {
            return "";
        }
        StringBuilder responseHeaders = new StringBuilder();
        String uuid = getUUID();
        responseHeaders.append(ONCLICK_TOGGLE).append(uuid).append("\")'>Toggle Headers</a> ");
        responseHeaders.append(SPAN_ID).append(uuid).append(DISPLAY_NONE);
        responseHeaders.append(formatKeyPair(response.getHeaders()));
        responseHeaders.append(END_SPAN);
        return responseHeaders.toString();
    }

    /**
     * Takes the response code returned in the HTTP response, and writes them to the output
     * file, in properly HTML formatted fashion
     *
     * @param response - the response object obtained from the call
     * @return String: an HTML formatted string with headers
     */
    public static String getResponseCodeOutput(Response response) {
        if (response == null) {
            return "";
        }
        StringBuilder responseOutput = new StringBuilder();
        String uuid = getUUID();
        responseOutput.append(ONCLICK_TOGGLE).append(uuid).append("\")'>Toggle Response Status Code</a> ");
        responseOutput.append(SPAN_ID).append(uuid).append(DISPLAY_NONE);
        responseOutput.append(DIV).append(response.getCode()).append(END_DIV);
        responseOutput.append(END_SPAN);
        return responseOutput.toString();
    }

    /**
     * Takes the response returned from the HTTP call, and writes it to the output
     * file, in properly HTML formatted fashion
     *
     * @param response - the response object obtained from the call
     * @return String: an HTML formatted string with headers
     */
    public static String getResponseOutput(Response response) {
        if (response == null || response.getMessage() == null || "".equals(response.getMessage())) {
            return "";
        }
        StringBuilder responseOutput = new StringBuilder();
        String uuid = getUUID();
        responseOutput.append(ONCLICK_TOGGLE).append(uuid).append("\")'>Toggle Raw Response</a> ");
        responseOutput.append(SPAN_ID).append(uuid).append(DISPLAY_NONE);
        responseOutput.append(DIV).append(response.getMessage()).append(END_DIV);
        responseOutput.append(END_SPAN);
        return responseOutput.toString();
    }

    /**
     * Generates a unique id
     *
     * @return String: a random string with timestamp
     */
    public static String getUUID() {
        long timeInSeconds = new Date().getTime();
        String randomChars = TestCase.getRandomString(10);
        return timeInSeconds + "_" + randomChars;
    }

    /**
     * Converts an integer, and retrieves it's ordinal. 1 becomes 1st, 11 becomes 11th, etc
     *
     * @param i - the integer to convert
     * @return String: the ordinal of the integer
     */
    public static String ordinal(int i) {
        String[] suffixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];
        }
    }

    ///////////////////////////////////////////////////////////////////
    // this enum will be for a pass/fail
    ///////////////////////////////////////////////////////////////////

    /**
     * Determines if the tests pass or fail
     *
     * @author Max Saperstone
     */
    protected enum Success {
        PASS, FAIL, CHECK
    }
}