package sample;


import com.coveros.selenified.Locator;
import com.coveros.selenified.Selenified;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class SampleIT extends Selenified {


    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "https://www.google.com");
        // set the author of the tests here
        setAuthor(this, test, "Hammid Funsho\n<br/>hammid.funsho@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "0.0.1");
    }

    @DataProvider(name = "google search terms", parallel = true)
    public Object[][] DataSetOptions() {
        return new Object[][] { new Object[] { "python" },
                new Object[] { "perl" }, new Object[] { "bash" }, };
    }

    @Test(groups = { "sampleS" }, description = "A sample selenium test to check a title")
    public void sampleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // verify the correct page title
        app.azzert().titleEquals("Google");
        // verify no issues
        finish();
    }

    @Test(dataProvider = "google search terms", groups = { "sampleS", "search" },
            description = "A sample selenium test using a data provider to perform a google search")
    public void sampleTestWDataProvider(String searchTerm) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // find the search box element and create the object
        Element searchBox = app.newElement(Locator.NAME, "q");
        //perform the search and submit
        searchBox.type(searchTerm);
        searchBox.submit();
        //wait for the page to return the results
        app.newElement(Locator.ID, "resultStats").waitFor().present();
        // verify the correct page title
        app.azzert().titleEquals(searchTerm + " - Google Search");
        // verify no issues
        finish();
    }

}

