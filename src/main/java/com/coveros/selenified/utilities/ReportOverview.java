package com.coveros.selenified.utilities;

import com.coveros.selenified.Browser;
import org.testng.*;
import org.testng.log4testng.Logger;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.coveros.selenified.Selenified.REPORTER;
import static com.coveros.selenified.utilities.Constants.*;
import static com.coveros.selenified.utilities.Property.BROWSER;
import static java.util.stream.Collectors.toList;

public class ReportOverview implements IReporter {

    private static final Logger log = Logger.getLogger(ReportOverview.class);

    private static final String REPORT_TEMPLATE = "<html>\n" +
            "<head><title>My Custom Report</title>\n" +
            "    <meta name='viewport' content='width=device-width, initial-scale=1'>\n" +
            "    <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>\n" +
            "    <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js'></script>\n" +
            "    <script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>\n" +
            "    <link rel='stylesheet' href='https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css'>\n" +
            "    <script src='https:////cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js'></script>\n" +
            "    <script>\n" +
            "        $(document).ready( function () {\n" +
            "            $('table').DataTable();\n" +
            "        } );\n" +
            "    </script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div class='container'>\n" +
            NEED HIGH LEVEL RESULTS!!!
            "</div>\n" +
            "<div class='container'>\n" +
            "    <table class='table'>\n" +
            "        <thead>\n" +
            "        <tr>\n" +
            "            <th>Browser</th>\n" +
            "            <th>Test Case</th>\n" +
            "            <th>Report</th>\n" +
            "            <th>Status</th>\n" +
            "            <th>Execution Time</th>\n" +
            "        </tr>\n" +
            "        </thead>\n" +
            "        <tbody>\n" +
            "        </tbody>\n" +
            "    </table>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";
    private static final String ROW_TEMPLATE = "<tr class='%s'><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";
    private String outputDirectory;

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        this.outputDirectory = outputDirectory;
        final String body = suites.stream().flatMap(suiteToResults()).collect(Collectors.joining());
        saveReportTemplate(REPORT_TEMPLATE.replaceFirst("</tbody>", String.format("%s</tbody>", body)));
    }

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
//
//    private Function<ISuite, Stream<? extends String>> suiteToResults2() {
//        return suite -> suite.getResults().entrySet().stream().flatMap(resultsToRows());
//    }
//
//    private Function<Map.Entry<String, ISuiteResult>, Stream<? extends String>> resultsToRows2() {
//        return e -> {
//            ITestContext testContext = e.getValue().getTestContext();
//            Set<ITestResult> failedTests = testContext.getFailedTests().getAllResults();
//            Set<ITestResult> passedTests = testContext.getPassedTests().getAllResults();
//            Set<ITestResult> skippedTests = testContext.getSkippedTests().getAllResults();
//            return Stream.of(failedTests, passedTests, skippedTests).flatMap(results -> generateReportRows2(results).stream());
//        };
//    }

    private Function<ISuite, Stream<? extends String>> suiteToResults() {
        return suite -> suite.getResults().entrySet().stream().flatMap(resultsToRows());
    }

    private Function<Map.Entry<String, ISuiteResult>, Stream<? extends String>> resultsToRows() {
        return e -> {
            ITestContext testContext = e.getValue().getTestContext();
            Set<ITestResult> failedTests = testContext.getFailedTests().getAllResults();
            Set<ITestResult> passedTests = testContext.getPassedTests().getAllResults();
            Set<ITestResult> skippedTests = testContext.getSkippedTests().getAllResults();
            return Stream.of(failedTests, passedTests, skippedTests).flatMap(results -> generateReportRows(results).stream());
        };
    }

    private List<String> generateReportRows(Set<ITestResult> allTestResults) {
        return allTestResults.stream().map(testResultToResultRow()).collect(toList());
    }

    private Function<ITestResult, String> testResultToResultRow() {
        return testResult -> {
            Browser browser = (Browser) testResult.getAttribute(BROWSER);
            Reporter reporter = (Reporter) testResult.getAttribute(REPORTER);
            String link = "";
            if (reporter != null) {
                String htmlFilename = reporter.getFileName() + ".html";
                link = LINK_START + getReportDir(testResult) + "/" + htmlFilename + LINK_MIDDLE + "HTML Report" + LINK_END;
                if (Property.generatePDF()) {
                    String pdfFilename = reporter.getFileName() + ".pdf";
                    link += " " + LINK_START + getReportDir(testResult) + "/" + pdfFilename + LINK_MIDDLE + "PDF Report" + LINK_END;
                }
            }
            Reporter.Success status = Reporter.Success.values()[testResult.getStatus() - 1];
            String clazz = null;
            switch (status) {
                case PASS:
                    clazz = "success";
                    break;
                case CHECK:
                    clazz = "warning";
                    break;
                case FAIL:
                    clazz = "danger";
                    break;
            }
            return String.format(ROW_TEMPLATE, clazz, browser.getDetails(), Reporter.capitalizeFirstLetters(testResult.getName()), link,
                    status, String.valueOf(testResult.getEndMillis() - testResult.getStartMillis()));
        };
    }

    private void saveReportTemplate(String reportTemplate) {
        if (!new File(outputDirectory).exists() && !new File(outputDirectory).mkdirs()) {
            try {
                throw new IOException("Unable to create output directory");
            } catch (IOException e) {
                log.info(e);
            }
        }
        try {
            PrintWriter reportWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDirectory, "report.html"))));
            reportWriter.println(reportTemplate);
            reportWriter.flush();
            reportWriter.close();
        } catch (IOException e) {
            log.error(e);
        }
    }
}
