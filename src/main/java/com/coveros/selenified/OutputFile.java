/*
 * Copyright 2018 Coveros, Inc.
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

package com.coveros.selenified;

import com.coveros.selenified.Browser.BrowserName;
import com.coveros.selenified.application.App;
import com.coveros.selenified.services.Request;
import com.coveros.selenified.services.Response;
import com.coveros.selenified.utilities.TestCase;
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

/**
 * A custom output file, recording all details of every step performed, both
 * actions and app. Actions, expected results, and actual results are captured.
 * All asserts have a screenshot taken for traceability, while all failing
 * actions also have a screenshot taken to assist with debugging purposes
 *
 * @author Max Saperstone
 * @version 3.0.4
 * @lastupdate 1/12/2019
 */
public class OutputFile {

    private static final Logger log = Logger.getLogger(OutputFile.class);
    public static final String PASSORFAIL = "PASSORFAIL";

    private App app = null;

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
    private Capabilities capabilities;
    private final List<String> screenshots = new ArrayList<>();

    // timing of the test
    private long startTime;
    private long lastTime = 0;
    // this will track the step numbers
    private int stepNum = 0;
    // this will keep track of the errors
    private int errors = 0;

    // constants
    private static final String START_ROW = "   <tr>\n";
    private static final String START_CELL = "    <td>";
    private static final String END_CELL = "</td>\n";
    private static final String END_ROW = "   </tr>\n";
    private static final String END_IDIV = "</i></div>";
    // the image width for reporting
    private static final int EMBEDDED_IMAGE_WIDTH = 300;

    /**
     * Creates a new instance of the OutputFile, which will serve as the
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
    public OutputFile(String directory, String test, Capabilities capabilities, String url, String suite, String group,
                      String author, String version, String objectives) {
        this.directory = directory;
        this.test = test;
        this.capabilities = capabilities;
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
    public int getErrors() {
        return errors;
    }

    /**
     * Increments the current error count of the test by one
     */
    public void addError() {
        errors++;
    }

    /**
     * Increments the current error count of the test by the provided amount
     *
     * @param errorsToAdd - the number of errors to add
     */
    public void addErrors(int errorsToAdd) {
        errors += errorsToAdd;
    }

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
                log.error(e);
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
     * Counts the number of occurrence of a string within a file
     *
     * @param textToFind - the text to count
     * @return Integer: the number of times the text was found in the file
     * provided
     */
    private int countInstancesOf(String textToFind) {
        int count = 0;
        try (FileReader fr = new FileReader(file); BufferedReader reader = new BufferedReader(fr)) {
            String line;
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
    private String getHtmlForPDFConversion() {
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

        // replace all non convertible elements with empty text or modify for conversion
        String str = oldContent.toString()
                .replaceAll("<script type='text/javascript'>(?s).*</script>", "")
                .replaceAll("<tr>\\s*<th>View Results</th>(?s).*?</tr>", "");

        String imagePattern = "(<img(?s).*? src='(.*?)'(?s).*?)</img>";
        Pattern r = Pattern.compile(imagePattern);
        Matcher m = r.matcher(str);
        int imageCount = 0;
        while (m.find()) {
            str = str.replaceAll("<a href='javascript:void\\(0\\)'(?s).*?(<img(?s).*? src='(.*?)'(?s).*?)" +
                            " style(?s).*?</img>",
                    "$1" + "></img><a href=\"#image-" + imageCount + "\">Link to full size image</a>");
            str = str.replaceAll("</body>", "<p style='page-break-before: always' id='image-" + imageCount++ + "'></p>" +
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
     * Writes an action that was performed out to the output file. If the action
     * is considered a failure, and a 'real' browser is being used (not NONE or
     * HTMLUNIT), then a screenshot will automatically be taken
     *
     * @param action         - the step that was performed
     * @param expectedResult - the result that was expected to occur
     * @param actualResult   - the result that actually occurred
     * @param result         - the result of the action
     */
    public void recordAction(String action, String expectedResult, String actualResult, Result result) {
        stepNum++;
        String success = "Check";
        String imageLink = "";
        if (result == Result.SUCCESS) {
            success = "Pass";
        }
        if (result == Result.FAILURE) {
            success = "Fail";
        }
        if (!"Pass".equals(success) && isRealBrowser()) {
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
            out.write("    <td class='" + result.toString().toLowerCase() + "'>" + actualResult + imageLink + END_CELL);
            out.write(START_CELL + dTime + "ms / " + tTime + "ms</td>\n");
            out.write("    <td class='" + success.toLowerCase() + "'>" + success + END_CELL);
            out.write(END_ROW);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Writes to the output file the actual outcome of an event. A screenshot is
     * automatically taken to provide tracability for and proof of success or
     * failure. This method should only be used after first writing the expected
     * result, using the recordExpected method.
     *
     * @param actualOutcome - what the actual outcome was
     * @param result        - whether this result is a pass or a failure
     */
    public void recordActual(String actualOutcome, Success result) {
        try (
                // reopen the log file
                FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw)) {
            // get a screen shot of the action
            String imageLink = "";
            if (isRealBrowser()) {
                imageLink = captureEntirePageScreenshot();
            }
            // determine time differences
            Date currentTime = new Date();
            long dTime = currentTime.getTime() - lastTime;
            long tTime = currentTime.getTime() - startTime;
            lastTime = currentTime.getTime();
            // write out the actual outcome
            out.write(START_CELL + actualOutcome + imageLink + END_CELL);
            out.write(START_CELL + dTime + "ms / " + tTime + "ms</td>\n");
            // write out the pass or fail result
            if (result == Success.PASS) {
                out.write("    <td class='pass'>Pass</td>\n");
            } else {
                out.write("    <td class='fail'>Fail</td>\n");
            }
            // end the row
            out.write(END_ROW);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Writes to the output file the expected outcome of an event. This method
     * should always be followed the recordActual method to record what actually
     * happened.
     *
     * @param expectedOutcome - what the expected outcome is
     */
    public void recordExpected(String expectedOutcome) {
        stepNum++;

        try (
                // reopen the log file
                FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw)) {
            // start the row
            out.write(START_ROW);
            // log the step number
            out.write("    <td align='center'>" + stepNum + ".</td>\n");
            // leave the step blank as this is simply a check
            out.write("    <td> </td>\n");
            // write out the expected outcome
            out.write(START_CELL + expectedOutcome + END_CELL);
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
            out.write("   .warning {\n");
            out.write("    color:orange;\n");
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
            out.write("  </style>\n");
            out.write("  <script type='text/javascript'>\n");
            out.write("   function toggleImage( imageName ) {\n");
            out.write("    var element = document.getElementById( imageName );\n");
            out.write("    element.src = location.href.match(/^.*\\//) + imageName;\n");
            out.write("    element.style.display = (element.style.display != 'none' ? 'none' : '' );\n");
            out.write(endBracket3);
            out.write("   function displayImage( imageName ) {\n");
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
    public void finalizeOutputFile(int testStatus) {
        // reopen the file
        try (FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw)) {
            out.write("  </table>\n");
            out.write(" </body>\n");
            out.write("</html>\n");
        } catch (IOException e) {
            log.error(e);
        }
        // Record the metrics
        int passes = countInstancesOf("<td class='pass'>Pass</td>");
        int fails = countInstancesOf("<td class='fail'>Fail</td>");
        replaceInFile("STEPSPERFORMED", Integer.toString(fails + passes));
        replaceInFile("STEPSPASSED", Integer.toString(passes));
        replaceInFile("STEPSFAILED", Integer.toString(fails));
        if (fails == 0 && errors == 0 && testStatus == 1) {
            replaceInFile(PASSORFAIL, "<font size='+2' class='pass'><b>SUCCESS</b></font>");
        } else if (fails == 0 && errors == 0) {
            replaceInFile(PASSORFAIL, "<font size='+2' class='warning'><b>" + Result.values()[testStatus] + "</b" +
                    "></font>");
        } else {
            replaceInFile(PASSORFAIL, "<font size='+2' class='fail'><b>FAILURE</b></font>");
        }
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
        if (System.getProperty("packageResults") != null && "true".equals(System.getProperty("packageResults"))) {
            packageTestResults();
        }
        if (System.getProperty("generatePDF") != null && "true".equals(System.getProperty("generatePDF"))) {
            generatePdf();
        }
    }

    /**
     * Generates a pdf report in the same directory as the html report
     */
    private void generatePdf() {
        String pdfFilename = directory + "/" + filename + ".pdf";
        try (OutputStream os = new FileOutputStream(pdfFilename)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(getHtmlForPDFConversion(), "file://" + pdfFilename.replaceAll(" ", "%20"));
            builder.toStream(os);
            builder.run();
        } catch (Exception e) {
            e.printStackTrace();
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
                ZipEntry s = new ZipEntry(screenshot.replaceAll(".*\\/", ""));
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
        String imageLink = "<br/>";
        if (imageName.length() >= directory.length() + 1) {
            imageLink += "<a href='javascript:void(0)' onclick='toggleImage(\"" +
                    imageName.substring(directory.length() + 1) + "\")'>Toggle Screenshot Thumbnail</a>";
            imageLink += " <a href='javascript:void(0)' onclick='displayImage(\"" +
                    imageName.substring(directory.length() + 1) + "\")'>View Screenshot Fullscreen</a>";
            imageLink += "<br/><img id='" + imageName.substring(directory.length() + 1) + "' border='1px' src='" +
                    imageName.substring(directory.length() + 1) + "' width='" + EMBEDDED_IMAGE_WIDTH +
                    "px' style='display:none;'></img>";
        } else {
            imageLink += "<b><font class='fail'>No Image Preview</font></b>";
        }
        return imageLink;
    }

    /**
     * Generates a unique image name
     *
     * @return String: the name of the image file as a PNG
     */
    private String generateImageName() {
        long timeInSeconds = new Date().getTime();
        String randomChars = TestCase.getRandomString(10);
        return directory + "/" + timeInSeconds + "_" + randomChars + ".png";
    }

    /**
     * Determines the current time and sets the 'last time' to the current time
     */
    private void setStartTime() {
        startTime = (new Date()).getTime();
        lastTime = startTime;
    }

    ///////////////////////////////////////////////////////////////////
    // some comparisons for our services
    ///////////////////////////////////////////////////////////////////

    /**
     * Formats the request parameters to be 'prettily' printed out in HTML
     *
     * @param params - the parameters to be formatted. Either a JSON object, or a
     *               hashmap
     * @return String: a 'prettily' formatted string that is HTML safe to output
     */
    public String outputRequestProperties(Request params, File file) {
        StringBuilder output = new StringBuilder();
        if (params != null && params.isPayload()) {
            output.append("<br/> with parameters: ");
            output.append("<div><i>");
            if (params.getJsonPayload() != null) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                output.append(gson.toJson(params.getJsonPayload()));
            }
            if (params.getMultipartData() != null) {
                for (Map.Entry<String, Object> entry : params.getMultipartData().entrySet()) {
                    output.append("<div>");
                    output.append(String.valueOf(entry.getKey()));
                    output.append(" : ");
                    output.append(String.valueOf(entry.getValue()));
                    output.append("</div>");
                }
            }
            output.append(END_IDIV);
        }
        if (file != null) {
            output.append("<div> with file: <i>").append(file.getAbsoluteFile()).append(END_IDIV);
        }
        return formatHTML(output.toString());
    }

    /**
     * Formats the response parameters to be 'prettily' printed out in HTML
     *
     * @param response - the http response to be formatted.
     * @return String: a 'prettily' formatted string that is HTML safe to output
     */
    public String formatResponse(Response response) {
        if (response == null) {
            return "";
        }
        StringBuilder output = new StringBuilder();
        if (response.isData()) {
            output.append("<div><i>");
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
     * @param string : the regular string to be formatted into an HTML pretty
     *               rendering string
     * @return String : the replaced result
     */
    public String formatHTML(String string) {
        if (string == null) {
            return "";
        }
        return string.replaceAll(" ", "&nbsp;").replaceAll("\n", "<br/>");
    }

    ///////////////////////////////////////////////////////////////////
    // this enum will be for a pass/fail
    ///////////////////////////////////////////////////////////////////

    /**
     * Determines if the tests pass or fail
     *
     * @author Max Saperstone
     */
    public enum Success {
        PASS, FAIL;

        int errors;

        static {
            PASS.errors = 0;
            FAIL.errors = 1;
        }

        /**
         * Retrieves the errors associated with the enumeration
         *
         * @return Integer: the errors associated with the enumeration
         */
        public int getErrors() {
            return this.errors;
        }
    }

    /**
     * Gives status for each test step
     *
     * @author Max Saperstone
     */
    public enum Result {
        WARNING, SUCCESS, FAILURE, SKIPPED
    }
}