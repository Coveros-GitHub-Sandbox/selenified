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
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class CombinedPDFReport implements IReporter {

    static final float INCH = 72;

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        if (Property.generatePDF()) {
            try {
                File folder = new File(outputDirectory + "/Surefire suite");

                PDFMergerUtility pdfMerger = new PDFMergerUtility();
                pdfMerger.setDestinationFileName(outputDirectory + "/Surefire suite/merged.pdf");
                File[] testReports = listFilesMatching(folder, ".*\\.pdf");
                for (File file : testReports) {
                    pdfMerger.addSource(file);
                }
                pdfMerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
                PDDocument document = PDDocument.load(new File(outputDirectory + "/Surefire suite/merged.pdf"));

                PDPage tableOfContents = new PDPage(new PDRectangle(1000, testReports.length * 17));

                float ph = tableOfContents.getMediaBox().getUpperRightY();

                PDFont font = PDType1Font.HELVETICA_BOLD;
                int fontSize = 12;
                PDPageContentStream contents = new PDPageContentStream(document, tableOfContents);
                contents.beginText();
                contents.setFont(font, fontSize);
                contents.newLineAtOffset(INCH, ph - INCH - fontSize);
                for (File file : testReports) {
                    contents.showText(file.getName());
                    contents.newLineAtOffset(0, -15);
                }
                contents.endText();
                contents.close();

                // Now add the link annotation, so the click on "Jump to page three" works
                for (int reportIndex = 1; reportIndex <= testReports.length; reportIndex++) {
                    setLink(document, ph, tableOfContents, font, fontSize, reportIndex-1, "Go to " + testReports[reportIndex-1], reportIndex);
                }

                PDDocument newDoc = new PDDocument();
                PDPageTree allPages = document.getDocumentCatalog().getPages();

                allPages.insertBefore(tableOfContents, allPages.get(0));
                for (PDPage page : allPages) {
                    newDoc.addPage(page);
                }
                newDoc.save(outputDirectory + "/Surefire suite/merged.pdf");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setLink(PDDocument document, float ph, PDPage tableOfContents, PDFont font, int fontSize, int pageIndex, String linkText, int linkIndex) throws IOException {
        List<PDAnnotation> annotations = tableOfContents.getAnnotations();
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

    public static File[] listFilesMatching(File root, String regex) {
        if (!root.isDirectory()) {
            throw new IllegalArgumentException(root + " is no directory.");
        }
        final Pattern p = Pattern.compile(regex);
        return root.listFiles(file -> p.matcher(file.getName()).matches());
    }
}
