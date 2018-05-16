package sample;

import com.coveros.selenified.Selenified;
import com.coveros.selenified.application.App;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class POMSampleIT extends Selenified {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext test) {
        // set the base URL for the tests here
        setTestSite(this, test, "http://34.233.135.10/");
        // set the author of the tests here
        setAuthor(this, test, "Max Saperstone\n<br/>max.saperstone@coveros.com");
        // set the version of the tests or of the software, possibly with a
        // dynamic check
        setVersion(this, test, "0.0.1");
    }

    @DataProvider(name = "car list items", parallel = true)
    public Object[][] DataSetOptions() {
        return new Object[][]{new Object[]{"Volvo"}, new Object[]{"Saab"}, new Object[]{"Mercedes"}};
    }

    private final ThreadLocal<MainPage> main = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void setupApp() {
        main.set(new MainPage(this.apps.get()));
    }

    @Test(dataProvider = "car list items", groups = {"sample", "pom"},
            description = "A sample test using a data provider to perform searches")
    public void sampleTestWDataProvider(String listItem) {
        // our test actions - use our threadsafe main object
        main.get().selectCar(listItem);
        main.get().assertCar(listItem);
        // close out the test
        finish();
    }

    @Test(groups = {"sample", "pom"}, description = "A sample test using a data provider to perform searches")
    public void sampleTest() {
        // grab our main app object
        App app = this.apps.get();
        // grab our main threadsafe object for future use
        MainPage main = this.main.get();
        // our test actions
        main.generateAlert();
        app.azzert().alertPresent();
        app.acceptAlert();
        app.azzert().alertNotPresent();
        // close out the test
        finish();
    }

    @Test(groups = {"sample", "pom"}, description = "A sample test using a data provider to perform searches")
    public void sampleTestWMatches() {
        // define a new main object
        MainPage main = new MainPage(this.apps.get());
        // perform some actions
        for (int match = 0; match < main.checkbox.get().matchCount(); match++) {
            main.checkbox.setMatch(match);
            main.checkbox.click();
            main.checkbox.assertState().checked();
        }
        // close out the test
        finish();
    }
}