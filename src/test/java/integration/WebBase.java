package integration;

import com.coveros.selenified.Selenified;
import org.mockserver.integration.ClientAndServer;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class WebBase extends Selenified {
    private ClientAndServer mockServer;

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://localhost:1070/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a dynamic check
        setVersion(this, test, "3.1.1");
    }

    private static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }

    @BeforeSuite(alwaysRun = true)
    public void startMockServer() throws IOException {
        mockServer = startClientAndServer(1070);
        mockServer.when(request().withMethod("GET").withPath("/"))
                .respond(response().withBody(readFile("public/index.html")));
        mockServer.when(request().withMethod("GET").withPath("/next_page.html"))
                .respond(response().withBody(readFile("public/next_page.html")));
    }

    @AfterSuite(alwaysRun = true)
    public void stopMockServer() {
        mockServer.stop();
    }
}
