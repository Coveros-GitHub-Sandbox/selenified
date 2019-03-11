package sample;

import com.coveros.selenified.Locator;
import com.coveros.selenified.application.App;
import com.coveros.selenified.element.Element;
import integration.WebBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SimpleSampleIT extends WebBase {

    @DataProvider(name = "car list items", parallel = true)
    public Object[][] DataSetOptions() {
        return new Object[][]{new Object[]{"Volvo"}, new Object[]{"Saab"}, new Object[]{"Mercedes"}};
    }

    @Test(groups = {"sample"}, description = "A sample test to check a title")
    public void sampleTest() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform the verification
        app.azzert().titleEquals("Selenified Test Page");
    }

    @Test(dataProvider = "car list items", groups = {"sample"},
            description = "A sample test using a data provider to perform searches")
    public void sampleTestWDataProvider(String listItem) {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        app.newElement(Locator.ID, "car_list").selectOption(listItem);
        // close out the test
        finish();
    }

    @Test(groups = {"sample"}, description = "A sample test to show how to loop through elements with multiple matches")
    public void sampleTestLoopThroughElements() {
        // use this object to manipulate the app
        App app = this.apps.get();
        // perform some actions
        Element element = app.newElement(Locator.XPATH, "//form/input[@type='checkbox']");
        for (int match = 0; match < element.get().matchCount(); match++) {
            element.setMatch(match);
            element.click();
            element.assertState().checked();
        }
        // close out the test
        finish();
    }
}
