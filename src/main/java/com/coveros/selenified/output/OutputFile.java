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

package com.coveros.selenified.output;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.testng.log4testng.Logger;

import com.coveros.selenified.selenium.App;
import com.coveros.selenified.selenium.Selenium.Browser;
import com.coveros.selenified.services.Request;
import com.coveros.selenified.services.Response;
import com.coveros.selenified.tools.General;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A custom output file, recording all details of every step performed, both
 * actions and app. Actions, expected results, and actual results are captured.
 * All asserts have a screenshot taken for traceability, while all failing
 * actions also have a screenshot taken to assist with debugging purposes
 *
 * @author Max Saperstone
 * @version 3.0.0
 * @lastupdate 7/20/2017
 */
public class OutputFile {

    private static final Logger log = Logger.getLogger(General.class);

    private App app = null;

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
    private Browser browser = Browser.NONE;
    private List<String> screenshots = new ArrayList<>();

    // timing of the test
    private long startTime;
    private long lastTime = 0;
    // this will track the step numbers
    private int stepNum = 0;
    // this will keep track of the errors
    private int errors = 0;
    // the image width for reporting
    private int embeddedImageWidth = 300;

    // constants
    private static final String START_ROW = "   <tr>\n";
    private static final String START_CELL = "    <td>";
    private static final String END_CELL = "</td>\n";
    private static final String END_ROW = "   </tr>\n";

    /**
     * Creates a new instance of the OutputFile, which will serve as the
     * detailed log
     * 
     * @param testDirectory
     *            - a string of the directory holding the files
     * @param testName
     *            - a string value of the test name, typically the method name
     * @param setServiceURL
     *            - the service URL to execute all services tests on
     */
    public OutputFile(String testDirectory, String testName, String setServiceURL) {
        url = setServiceURL;
        init(testDirectory, testName);
    }

    /**
     * Creates a new instance of the OutputFile, which will serve as the
     * detailed log
     * 
     * @param testDirectory
     *            - a string of the directory holding the files
     * @param testName
     *            - a string value of the test name, typically the method name
     * @param setBrowser
     *            - the browser we are performing this test on
     */
    public OutputFile(String testDirectory, String testName, Browser setBrowser) {
        browser = setBrowser;
        init(testDirectory, testName);
    }
    
    /**
     * Setups up our logging file
     * 
     * @param testDirectory
     *            - a string of the directory holding the files
     * @param testName
     *            - a string value of the test name, typically the method name
     */
    private void init(String testDirectory, String testName) {
    	test = testName;
        directory = testDirectory;
        filename = testName + browser + ".html";
        file = new File(directory, filename);
        setupFile();
    }

    /**
     * returns the filename in string form of the output file
     * 
     * @return String: filename
     */
    public String getFileName() {
        return filename;
    }

    /**
     * returns the start time of the test
     * 
     * @return Long
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * returns the last time a step was completed
     * 
     * @return Long
     */
    public long getLastTime() {
        return lastTime;
    }

    /**
     * returns the current error count of the test
     * 
     * @return Integer
     */
    public int getErrors() {
        return errors;
    }

    /**
     * adds the app class which controls actions within the browser
     * 
     * @param app
     *            - the application to be tested, contains all control elements
     */
    public void setApp(App app) {
        this.app = app;
    }

    /**
     * sets the url of the app under test
     * 
     * @param appURL
     *            - the initial app to open the browser to
     */
    public void setURL(String appURL) {
        url = appURL;
    }

    /**
     * sets the name of the suite of the test
     * 
     * @param testSuite
     *            - the name of test suite
     */
    public void setSuite(String testSuite) {
        suite = testSuite;
    }

    /**
     * sets the name(s) of the group(s) of the tests
     * 
     * @param testGroup
     *            - the name of the test group
     */
    public void setGroup(String testGroup) {
        group = testGroup;
    }

    /**
     * sets the date of the last time the test was modified
     * 
     * @param date
     *            - the date the test was last modified
     */
    public void setLastModified(Date date) {
        lastModified = date;
    }

    /**
     * sets the version of the test being run
     * 
     * @param testVersion
     *            - the version associated with the test
     */
    public void setVersion(String testVersion) {
        version = testVersion;
    }

    /**
     * sets the author of the test
     * 
     * @param testAuthor
     *            - the author of the test
     */
    public void setAuthor(String testAuthor) {
        author = testAuthor;
    }

    /**
     * sets the objectives of the test being run
     * 
     * @param testObjectives
     *            - the testing objectives
     */
    public void setObjectives(String testObjectives) {
        objectives = testObjectives;
    }

    /**
     * creates the directory and file to hold the test output file
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
     * @param textToFind
     *            - the text to count
     * @return Integer - the number of times the text was found in the file
     *         provided
     */
    public int countInstancesOf(String textToFind) {
        int count = 0;
        try (FileReader fr = new FileReader(file); BufferedReader reader = new BufferedReader(fr);) {
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
     * @param oldText
     *            - the text to be replaced
     * @param newText
     *            - the text to be replaced with
     */
    public void replaceInFile(String oldText, String newText) {
        StringBuilder oldContent = new StringBuilder();

        try (FileReader fr = new FileReader(file); BufferedReader reader = new BufferedReader(fr);) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                oldContent.append(line + "\r\n");
            }
        } catch (IOException ioe) {
            log.error(ioe);
        }

        // replace a word in a file
        String newContent = oldContent.toString().replaceAll(oldText, newText);

        try (FileWriter writer = new FileWriter(file);) {
            writer.write(newContent);
        } catch (IOException ioe) {
            log.error(ioe);
        }
    }

    /**
     * a helper function to capture the entire app screen shot
     *
     * @return new image link
     */
    public String captureEntirePageScreenshot() {
        String imageName = generateImageName();
        String imageLink = generateImageLink(imageName);
        try {
            app.takeScreenshot(imageName);
            screenshots.add(imageName);
        } catch (Exception e1) {
            log.error(e1);
            imageLink = "<br/><b><font class='fail'>No Screenshot Available</font></b>";
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
        String success = "Check";
        String imageLink = "";
        if (result == Result.SUCCESS) {
            success = "Pass";
        }
        if (result == Result.FAILURE) {
            success = "Fail";
        }
        if (!"Pass".equals(success) && browser != null && browser != Browser.NONE && browser != Browser.HTMLUNIT) {
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
                FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw);) {
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
                // reopen the log file
                FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw);) {
            // get a screen shot of the action
            String imageLink = "";
            if (browser != null && browser != Browser.HTMLUNIT && browser != Browser.NONE) {
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
     * Will write to the output file the step number, skip the action taken, and
     * then write out the expected outcome
     *
     * @param expectedOutcome
     *            what the expected outcome is
     */
    public void recordExpected(String expectedOutcome) {
        stepNum++;

        try (
                // reopen the log file
                FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw);) {
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
     * Creates the specially formatted output header
     *
     * - an IOException
     */
    public void createOutputHeader() {
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
        try (FileWriter fw = new FileWriter(file); BufferedWriter out = new BufferedWriter(fw);) {
            out.write("<html>\n");
            out.write(" <head>\n");
            out.write("  <title>" + test + "</title>\n");
            out.write("  <style type='text/css'>\n");
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
            out.write("    <td bgcolor='lightblue' colspan=3>" + "<font size='5'>" + test + " </font></td>\n");
            out.write(swapRow);
            out.write("    <th>Tester</th>\n");
            out.write("    <td>Automated</td>\n");
            out.write("    <th>Version</th>\n");
            out.write(START_CELL + this.version + END_CELL);
            out.write(swapRow);
            out.write("    <th>Author</th>\n");
            out.write(START_CELL + this.author + END_CELL);
            out.write("    <th>Date Test Last Modified</th>\n");
            String modified = "--";
            if (this.lastModified != null) {
                modified = sdf.format(this.lastModified);
            }
            out.write(START_CELL + modified + END_CELL);
            out.write(swapRow);
            out.write("    <th>Date Tested</th>\n");
            out.write(START_CELL + datePart + END_CELL);
            out.write("    <th>Test Run Time</th>\n");
            out.write("    <td colspan=3>\n");
            out.write("     Start:\t" + sTime + " <br/>\n");
            out.write("     End:\tTIMEFINISHED <br/>\n");
            out.write("     Run Time:\tRUNTIME \n");
            out.write("    </td>\n ");
            out.write(swapRow);
            out.write("    <th>URL Under Test</th>\n");
            out.write(START_CELL + "<a href='" + url + "'>" + url + "</a>" + END_CELL);
            out.write("    <th>Browser</th>\n");
            out.write(START_CELL + browser + END_CELL);
            out.write(swapRow);
            out.write("    <th>Testing Group</th>\n");
            out.write(START_CELL + group + END_CELL);
            out.write("    <th>Testing Suite</th>\n");
            out.write(START_CELL + suite + END_CELL);
            out.write(swapRow);
            out.write("    <th>Test Objectives</th>\n");
            out.write("    <td colspan=3>" + objectives + END_CELL);
            out.write(swapRow);
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
            out.write(swapRow);
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
            out.write(END_ROW);
            out.write("  </table>\n");
            out.write("  <table id='all_results'>\n");
            out.write(START_ROW);
            out.write("    <th align='center'>Step</th>" + "<th style='text-align:center'>Action</th>"
                    + "<th style='text-align:center'>Expected Result</th>"
                    + "<th style='text-align:center'>Actual Result</th>"
                    + "<th style='text-align:center'>Step Times</th>"
                    + "<th style='text-align:center'>Pass/Fail</th>\n");
            out.write(END_ROW);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * loads the initial app specified by the url, and ensures the app loads
     * successfully
     * 
     * @return Integer: how many errors encountered
     */
    public int loadInitialPage() {
        String startingPage = "The starting app <i>";
        String act = "Opening new browser and loading up starting app";
        String expected = startingPage + url + "</i> will successfully load";

        if (app != null) {
            try {
                app.getDriver().get(url);
                if (!app.get().location().contains(url)) {
                    recordAction(act, expected,
                            startingPage + app.get().location() + "</i> loaded instead of <i>" + url + "</i>",
                            Result.FAILURE);
                    addError();
                    return 1;
                }
                recordAction(act, expected, startingPage + url + "</i> loaded successfully", Result.SUCCESS);
            } catch (Exception e) {
                log.error(e);
                recordAction(act, expected, startingPage + url + "</i> did not load successfully", Result.FAILURE);
                addError();
                return 1;
            }
        }
        return 0;
    }

    /**
     * ends and closes the test template
     *
     */
    public void finalizeOutputFile() {
        // reopen the file
        try (FileWriter fw = new FileWriter(file, true); BufferedWriter out = new BufferedWriter(fw);) {
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
        if (fails == 0) {
            replaceInFile("PASSORFAIL", "<font size='+2' class='pass'><b>PASS</b></font>");
        } else {
            replaceInFile("PASSORFAIL", "<font size='+2' class='fail'><b>FAIL</b></font>");
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
    }

    /**
     * packages the test result file along with screenshots into a zip file
     *
     */
    public void packageTestResults() {
        File f = new File(directory, filename + "_RESULTS.zip");
        try (// Create new zip file
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f))) {

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
            out.close();
        } catch (IOException e) {
            log.error(e);
        }
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
            imageLink += "<br/><img id='" + imageName.substring(directory.length() + 1) + "' border='1px' src='"
                    + imageName.substring(directory.length() + 1) + "' width='" + embeddedImageWidth
                    + "px' style='display:none;'>";
        } else {
            imageLink += "<b><font class='fail'>No Image Preview</font></b>";
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
        String randomChars = General.getRandomString(10);
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
        String image = "";
        if (imageName.length() > directory.length()) {
            image = imageName.substring(directory.length() + 1);
        }
        return "<br/><div align='center' width='100%'><img id='" + image + "' class='imgIcon' src='" + image
                + "'></div>";
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
     * simply add an encountered error to the error counter
     */
    public void addError() {
        errors++;
    }

    /**
     * add a given set of errors to the error counter
     *
     * @param errorsToAdd
     *            - the number of errors to add
     */
    public void addErrors(int errorsToAdd) {
        errors += errorsToAdd;
    }

    ///////////////////////////////////////////////////////////////////
    // some comparisons for our services
    ///////////////////////////////////////////////////////////////////

    /**
     * Formats the request parameters to be 'prettily' printed out in HTML
     * 
     * @param params
     *            - the parameters to be formatted. Either a JSON object, or a
     *            hashmap
     * @return String: a 'prettily' formatted string that is HTML safe to output
     */
    public String outputRequestProperties(Request params) {
        if (params == null) {
            return "";
        }
        StringBuilder output = new StringBuilder();
        output.append("<br/> with parameters: ");
        output.append("<div><i>");
        if (params.getData() != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            output.append(gson.toJson(params.getData()));
        }
        if (params.getParams() != null) {
            for (Map.Entry<String, String> entry : params.getParams().entrySet()) {
                output.append("<div>");
                output.append(entry.getKey());
                output.append(" : ");
                output.append(entry.getValue());
                output.append("</div>");
            }
        }
        output.append("</i></div>");
        return formatHTML(output.toString());
    }

    /**
     * Formats the response parameters to be 'prettily' printed out in HTML
     * 
     * @param response
     *            - the http response to be formatted.
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
            output.append("</i></div>");
        }
        return formatHTML(output.toString());
    }

    /**
     * This takes a generic string and replaces spaces and new lines with HTML
     * friendly pieces for display purposes
     * 
     * @param string
     *            : the regular string to be formatted into an HTML pretty
     *            rendering string
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
     * An enumeration used to determine if the tests pass or fail
     * 
     * @author Max Saperstone
     *
     */
    public enum Success {
        PASS, FAIL;

        protected int errors;

        /**
         * Are errors associated with the enumeration
         */
        static {
            PASS.errors = 0;
            FAIL.errors = 1;
        }

        /**
         * Retrieve the errors associated with the enumeration
         * 
         * @return Integer: the errors associated with the enumeration
         */
        public int getErrors() {
            return this.errors;
        }
    }

    /**
     * An enumeration used to give status for each test step
     * 
     * @author Max Saperstone
     *
     */
    public enum Result {
        WARNING, SUCCESS, FAILURE, SKIPPED
    }

}