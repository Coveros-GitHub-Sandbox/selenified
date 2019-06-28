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
import org.testng.ISuite;
import org.testng.ITestResult;
import org.testng.internal.Utils;
import org.testng.log4testng.Logger;
import org.testng.reporters.EmailableReporter2;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.List;

import static com.coveros.selenified.Selenified.REPORTER;
import static com.coveros.selenified.utilities.Constants.*;
import static com.coveros.selenified.utilities.Property.BROWSER;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.newBufferedWriter;

/**
 * Creates a custom report at the conclusion of the test run, which nicely lays
 * out the high level results, and links to all of the detailed reports
 *
 * @author Max Saperstone
 * @version 3.2.0
 * @lastupdate 6/28/2019
 */
public class ReportOverview extends EmailableReporter2 {

    private static final Logger log = Logger.getLogger(ReportOverview.class);
    public static final String WARNING = "warning";
    public static final String SUCCESS = "success";
    public static final String DANGER = "danger";
    public static final String TR = "</tr>";
    private String outputDirectory;
    private String fileName = "report.html";
    private NumberFormat integerFormat = NumberFormat.getIntegerInstance();

    /**
     * Creates a summary of the test run, with all xml suites and test information. extends emailable reporter,
     * and builds upon it's common methods
     *
     * @param xmlSuites       - the list of the xml suites
     * @param suites          - the list of testng test suites
     * @param outputDirectory - the output directory of tests being run
     */
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        this.outputDirectory = outputDirectory;
        try {
            writer = createWriter(outputDirectory);
        } catch (IOException e) {
            log.error("Unable to create output file", e);
            return;
        }
        for (ISuite suite : suites) {
            suiteResults.add(new SuiteResult(suite));
        }

        writeDocumentStart();
        writeHead();
        writeBody();
        writeDocumentEnd();

        writer.close();
    }

    /**
     * Generates the output file
     *
     * @param outdir - the output directory to save the output file to
     * @return PrintWriter - the writer for our custom output file
     * @throws IOException - if unable to create the file, IOException will be thrown
     */
    @Override
    protected PrintWriter createWriter(String outdir) throws IOException {
        new File(outdir).mkdirs();
        return new PrintWriter(newBufferedWriter(new File(outdir, fileName).toPath(), UTF_8));
    }

    /**
     * Creates the header information for the test report
     */
    @Override
    protected void writeHead() {
        writer.println("    <head>");
        writer.println("        <meta name='viewport' content='width=device-width, initial-scale=1'>");
        writer.println("        <title>Selenified Test Report</title>");
        writeStylesheet();
        writer.println("    </head>");
    }

    /**
     * Sets the css and js for the test report
     */
    @Override
    protected void writeStylesheet() {
        writer.println("        <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>\n");
        writer.println("        <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js'></script>\n");
        writer.println("        <script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>\n");
        writer.println("        <link rel='stylesheet' href='https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css'>\n");
        writer.println("        <script src='https:////cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>\n");
        writer.println("        <script>\n");
        writer.println("            $(document).ready( function () {\n");
        writer.println("                $('.sort').DataTable();\n");
        writer.println("            } );\n");
        writer.println("        </script>\n");
    }

    /**
     * Create the title and content of the test report
     */
    @Override
    protected void writeBody() {
        writer.println("    <body>");
        writer.println("        <h1 style='text-align:center;'>Selenified Test Results</h1>");
        writeSuiteSummary();
        writeScenarioDetails();
        writer.println("    </body>");
    }

    /**
     * Generates the high level summary of the test report
     */
    @Override
    protected void writeSuiteSummary() {
        int totalPassedTests = 0;
        int totalSkippedTests = 0;
        int totalFailedTests = 0;
        long totalDuration = 0;

        for (SuiteResult suiteResult : suiteResults) {
            for (TestResult testResult : suiteResult.getTestResults()) {
                totalPassedTests += testResult.getPassedTestCount();
                totalSkippedTests += testResult.getSkippedTestCount();
                totalFailedTests += testResult.getFailedTestCount();
                totalDuration += testResult.getDuration();
            }
        }
        String overallCssClass = SUCCESS;
        if (totalSkippedTests > 0) {
            overallCssClass = WARNING;
        }
        if (totalFailedTests > 0) {
            overallCssClass = DANGER;
        }

        writer.println("        <div class='container' style='font-size:large;'>");
        writer.println("            <table class='table table-bordered' style='width:100%;text-align:center;'>");
        writer.println("                <thead>");
        writer.print("                      <tr class='" + overallCssClass + "'>");
        headerCell("Tests");
        headerCell("Passed");
        headerCell("Skipped");
        headerCell("Failed");
        headerCell("Time (ms)");
        writer.println(TR);
        writer.println("                </thead>");
        writer.println("                <tbody>");
        writer.print("                      <tr class='" + overallCssClass + "'>");
        cell(integerFormat.format((long) totalPassedTests + totalSkippedTests + totalFailedTests));
        cell(integerFormat.format(totalPassedTests));
        cell(integerFormat.format(totalSkippedTests));
        cell(integerFormat.format(totalFailedTests));
        cell(integerFormat.format(totalDuration));
        writer.println(TR);
        writer.println("                </tbody>");
        writer.println("            </table>");
        writer.println("        </div>");
    }

    /**
     * Creates a table with each test case run and it's details
     */
    @Override
    protected void writeScenarioDetails() {
        writer.println("        <div class='container'>");
        writer.println("            <table class='table sort'>");
        writer.println("                <thead>");
        writer.print("                    <tr>");
        headerCell("Browser");
        headerCell("Test Suite");
        headerCell("Test Case");
        headerCell("Report");
        headerCell("Status");
        headerCell("Time (ms)");
        writer.println(TR);
        writer.println("                </thead>");
        writer.println("                <tbody>");
        for (SuiteResult suiteResult : suiteResults) {
            for (TestResult testResult : suiteResult.getTestResults()) {
                writeResults(testResult.getFailedConfigurationResults(), "Fail", DANGER);
                writeResults(testResult.getFailedTestResults(), "Fail", DANGER);
                writeResults(testResult.getSkippedConfigurationResults(), "Skip", WARNING);
                writeResults(testResult.getSkippedTestResults(), "Skip", WARNING);
                writeResults(testResult.getPassedTestResults(), "Pass", SUCCESS);
            }
        }
        writer.println("                </tbody>");
        writer.println("            </table>");
        writer.println("        </div>");
        writer.println("    </body>");
    }

    /**
     * Helper class to writing out each test case data. extracts the classname, browser, and report information
     *
     * @param classResults - the class results containing all of the test and method information
     * @param status       - did this test pass, fail, or was skipped
     * @param cssClass     - what color do we want this row to be?
     */
    private void writeResults(List<ClassResult> classResults, String status, String cssClass) {
        for (ClassResult classResult : classResults) {
            String className = classResult.getClassName();
            for (MethodResult methodResult : classResult.getMethodResults()) {
                for (ITestResult iTestResult : methodResult.getResults()) {
                    writeTestResult(status, cssClass, className, iTestResult);
                }
            }
        }
    }

    /**
     * Takes a test result, and writes out the information to the outputfile
     *
     * @param status      - did this test pass, fail, or was skipped
     * @param cssClass    - what color do we want this row to be?
     * @param className   - the package and classname of the test case
     * @param iTestResult - the testng itestresult object
     */
    private void writeTestResult(String status, String cssClass, String className, ITestResult iTestResult) {
        Browser browser = (Browser) iTestResult.getAttribute(BROWSER);
        Reporter reporter = (Reporter) iTestResult.getAttribute(REPORTER);
        String timeTook = "--";
        String link = "--";
        if (reporter != null && !"Skip".equals(status)) {
            timeTook = integerFormat.format(iTestResult.getEndMillis() - iTestResult.getStartMillis());
            String htmlFilename = reporter.getFileName() + ".html";
            link = LINK_START + getReportDir(iTestResult) + "/" + htmlFilename + LINK_MIDDLE + "HTML Report" + LINK_END;
            if (Property.generatePDF()) {
                String pdfFilename = reporter.getFileName() + ".pdf";
                link += " " + LINK_START + getReportDir(iTestResult) + "/" + pdfFilename + LINK_MIDDLE + "PDF Report" + LINK_END;
            }
        }
        writer.print("<tr class='" + cssClass + "'>");
        cell(browser.getDetails());
        cell(Utils.escapeHtml(className));
        cell(Utils.escapeHtml(Reporter.capitalizeFirstLetters(iTestResult.getName())));
        cell(link);
        cell(status);
        cell(timeTook);
        writer.println(TR);
    }

    /**
     * Create a header table cell
     *
     * @param content - the content to write into the cell
     */
    private void headerCell(String content) {
        writer.print("<th>" + content + "</th>");
    }

    /**
     * Create a regular table cell
     *
     * @param content - the content to write into the cell
     */
    private void cell(String content) {
        writer.print("<td>" + content + "</td>");
    }

    /**
     * Retrieves the report directory for links in the html reports
     *
     * @param result - the testng itestresult object
     * @return String - the relative directory of the detailed reports
     */
    private String getReportDir(ITestResult result) {
        String workspace = System.getProperty("user.dir");
        String outputDir = new File(result.getTestContext().getOutputDirectory()).getAbsolutePath();
        if (outputDir.startsWith(outputDirectory)) {
            outputDir = outputDir.substring(outputDirectory.length() + 1);
        }
        if (outputDir.startsWith(workspace + File.separator + outputDirectory)) {
            outputDir = outputDir.substring(workspace.length() + outputDirectory.length() + 2);
        }
        return outputDir;
    }
}
