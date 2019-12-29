package unit;

import com.coveros.selenified.exceptions.InvalidBrowserException;
import com.coveros.selenified.exceptions.InvalidProxyException;
import com.coveros.selenified.utilities.CombinedPDFReport;
import org.testng.ISuite;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CombinedPDFReportTest extends CombinedPDFReport {

    File directory;

    @BeforeMethod(alwaysRun = true)
    public void createFile() throws InvalidBrowserException, InvalidProxyException {
        directory = new File("directory");
        directory.mkdirs();
    }

    @AfterMethod
    public void deleteFile() {
        for(File file: directory.listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    @Test
    public void listFilesMatchingTest() throws IOException {
        File file = new File("directory", "file.html");
        File anotherFile = new File("directory", "anotherFile.html");

        File[] fileArray = new File[2];
        fileArray[0] = anotherFile;
        anotherFile.createNewFile();
        fileArray[1] = file;
        file.createNewFile();

        assertEquals(fileArray,CombinedPDFReport.listFilesMatching(directory, ".*"));
    }

    @Test
    public void listFilesMatchingTest2() throws IOException {
        File file = new File("directory", "file.pdf");
        File anotherFile = new File("directory", "anotherFile.pdf");
        File nonPdfFile = new File("directory", "nonPDFFile.html");

        anotherFile.createNewFile();
        file.createNewFile();
        nonPdfFile.createNewFile();

        File[] fileArray = new File[2];
        fileArray[0] = anotherFile;
        fileArray[1] = file;

        assertEquals(fileArray,CombinedPDFReport.listFilesMatching(directory, ".*\\.pdf"));
    }

    @Test
    public void listFilesMatchingTest3() throws IOException {
        assertEquals(new File[0], CombinedPDFReport.listFilesMatching(directory, ".*"));
    }

    @Test
    public void generateCombinedReportTest() {
        generateReport(null, new ArrayList<ISuite>(), "myDir");
    }
}
