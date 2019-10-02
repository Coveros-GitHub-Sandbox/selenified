package sample;

import com.coveros.selenified.application.App;
import integration.WebBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class POMSampleIT extends WebBase {

    @DataProvider(name = "car list items", parallel = true)
    public Object[][] DataSetOptions() {
        return new Object[][]{new Object[]{"Volvo"}, new Object[]{"Saab"}, new Object[]{"Mercedes"}};
    }

    private final ThreadLocal<MainPage> main = new ThreadLocal<>();

    @BeforeMethod
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

    // skipping safari as it doesn't support modal dialogs: https://github.com/SeleniumHQ/selenium-google-code-issue-archive/issues/3862
    @Test(groups = {"sample", "pom", "alert", "no-safari"}, description = "A sample test using a data provider to perform searches")
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