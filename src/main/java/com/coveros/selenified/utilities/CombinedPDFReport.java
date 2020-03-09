package com.coveros.selenified.utilities;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestNGMethod;
import org.testng.log4testng.Logger;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombinedPDFReport implements IReporter {

    static final float INCH = 72;
    static final String FILE_NAME = "/AllPDFReports.pdf";
    private static final Logger log = Logger.getLogger(CombinedPDFReport.class);

    /**
     * Combines the PDFs created from test reports into one PDF
     *
     * @param xmlSuites       - the list of the xml suites
     * @param suites          - the list of testng test suites
     * @param outputDirectory - the output directory of tests being run
     */
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        if (Property.generatePDF()) {
            Map<String, List<ITestNGMethod>> testReportsMap = getTestSuiteMap(suites);
            List<File> testReportsList = getAllTestReportFiles(testReportsMap);
            try {
                PDFMergerUtility pdfMerger = new PDFMergerUtility();
                pdfMerger.setDestinationFileName(outputDirectory + FILE_NAME);
                for (File testReport : testReportsList) {
                    pdfMerger.addSource(testReport);
                }

                pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
                PDDocument document = PDDocument.load(new File(outputDirectory + FILE_NAME));
                PDPage tableOfContents;
                // Create larger page size if there are more than 48 tests
                if (testReportsList.size() > 48) {
                    tableOfContents = new PDPage(new PDRectangle(1000, testReportsList.size() * 17f));
                } else {
                    tableOfContents = new PDPage();
                }

                float ph = tableOfContents.getMediaBox().getUpperRightY();

                PDFont font = PDType1Font.HELVETICA_BOLD;
                int fontSize = 12;
                PDPageContentStream contents = new PDPageContentStream(document, tableOfContents);
                contents.beginText();
                contents.setFont(font, fontSize);
                contents.newLineAtOffset(INCH, ph - INCH - fontSize);
                for (File file : testReportsList) {
                    contents.showText(file.getName());
                    contents.newLineAtOffset(0, -15);
                }
                contents.endText();
                contents.close();

                // Now add the link annotation, so the click on "Jump to page three" works
                for (int reportIndex = 1; reportIndex <= testReportsList.size(); reportIndex++) {
                    int pageIndex = reportIndex - 1;
                    PDAnnotationLink pageLink = createLink(ph, font, fontSize,
                            "Go to " + testReportsList.get(pageIndex), reportIndex);
                    addLink(document, tableOfContents, pageLink, pageIndex);
                }

                try (PDDocument newDoc = new PDDocument()) {
                    PDPageTree allPages = document.getDocumentCatalog().getPages();

                    allPages.insertBefore(tableOfContents, allPages.get(0));
                    for (PDPage page : allPages) {
                        newDoc.addPage(page);
                    }
                    newDoc.save(outputDirectory + FILE_NAME);
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    /**
     * Creates a map of the output directory of each suite paired with a list of all the ITestNGMethod objects
     * in that suite
     *
     * @param suites the suites object from the TestNG tests executed
     * @return Map<String, List<ITestNGMethod>>: a map of all output directories and their tests
     */
    protected Map<String, List<ITestNGMethod>> getTestSuiteMap(List<ISuite> suites) {
        Map<String, List<ITestNGMethod>> testReportsMap = new HashMap<>();
        for (ISuite iSuite : suites) {
            String testReportDirectory = iSuite.getOutputDirectory();
            List<ITestNGMethod> iTestNGMethods = iSuite.getAllMethods();
            testReportsMap.put(testReportDirectory, iTestNGMethods);
        }
        return testReportsMap;
    }

    /**
     * Creates a list of all test files, using the map to get the directory and test name
     *
     * @param testReportsMap the test report map object from getTestSuiteMap
     * @return List<File>: a list of all test files in all suites
     */
    protected List<File> getAllTestReportFiles(Map<String, List<ITestNGMethod>> testReportsMap) {
        List<File> testReportsList = new ArrayList<>();
        for (Map.Entry<String, List<ITestNGMethod>> testReportsMapEntry : testReportsMap.entrySet()) {
            String testReportDirectory = testReportsMapEntry.getKey();
            for (ITestNGMethod iTestNGMethod : testReportsMapEntry.getValue()) {
                File testReport = new File(testReportDirectory,
                        iTestNGMethod.getTestClass().getName() + "." + iTestNGMethod.getMethodName() + ".pdf");
                addToReport(testReportsList, testReport);
            }
        }
        return testReportsList;
    }

    private void addToReport(List<File> testReportsList, File testReport) {
        System.out.println(testReport.getAbsolutePath());
        if (testReport.exists()) {
            testReportsList.add(testReport);
        }
    }

    /**
     * @param document PDDocument object of the final combined PDF
     * @param tableOfContents PDPage object of the table of contents page
     * @param pageLink PDAnnotations object of the link to each page from the table of contents
     * @param pageIndex int of the index of the page in the final combined PDF to link to
     * @throws IOException - if unable to get annotations, IOException will be thrown
     */
    private void addLink(PDDocument document, PDPage tableOfContents, PDAnnotationLink pageLink, int pageIndex) throws IOException {
        List<PDAnnotation> annotations = tableOfContents.getAnnotations();
        // add the GoTo action
        PDActionGoTo actionGoto = new PDActionGoTo();
        // see javadoc for other types of PDPageDestination
        PDPageDestination dest = new PDPageFitWidthDestination();
        // do not use setPageNumber(), this is for external destinations only
        dest.setPage(document.getPage(pageIndex));
        actionGoto.setDestination(dest);
        pageLink.setAction(actionGoto);
        annotations.add(pageLink);
    }

    /**
     * @param ph float of the page height
     * @param font PDFont object of the link to be created in the final combined PDF
     * @param fontSize int of the size of the font of the link to be created in the final combined PDF
     * @param linkText String object of the text for the link to be created in the final combined PDF
     * @param linkIndex int of the index of the link in the list of all links in the final combined PDF
     * @return PDAnnotationLink: the link object to add to the final combined pdf on the table of contents page
     * @throws IOException - if unable to get string width, IOException will be thrown
     */
    private PDAnnotationLink createLink(float ph, PDFont font, int fontSize, String linkText, int linkIndex) throws IOException {
        PDBorderStyleDictionary borderULine = new PDBorderStyleDictionary();
        borderULine.setStyle(PDBorderStyleDictionary.STYLE_UNDERLINE);
        borderULine.setWidth(0); // 1 point
        // Set the rectangle containing the link
        PDAnnotationLink pageLink = new PDAnnotationLink();
        pageLink.setBorderStyle(borderULine);
        float textWidth = font.getStringWidth(linkText) / 1000 * fontSize;
        PDRectangle position = new PDRectangle();
        float linkY = ph - (INCH + (15 * linkIndex));
        position.setLowerLeftX(INCH);
        position.setLowerLeftY(linkY);  // down a couple of points
        position.setUpperRightX(INCH + textWidth + 1);
        position.setUpperRightY(linkY + 10);
        pageLink.setRectangle(position);

        return pageLink;
    }
}
