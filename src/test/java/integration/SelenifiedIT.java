package integration;

import org.testng.annotations.Test;

public class SelenifiedIT extends WebBase {

    private String setScreensize = null;

    @Test
    public void noAnnotationDetailsTest() {
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "selenified"})
    public void noDescriptionTest() {
        // verify no issues
        finish();
    }

    @Test(description = "A test to verify that logs work without any groups")
    public void noGroupsTest() {
        // verify no issues
        finish();
    }

    @Test(groups = "integration")
    public void oneGroupTest() {
        // verify no issues
        finish();
    }

    @Test(groups = {"integration", "no-htmlunit", "no-chrome", "no-edge", "no-firefox", "no-safari", "no-internetexplorer"},
            description = "A test to verify a skip is thrown")
    public void skipThisTest() {
        this.apps.get().getReporter().fail("", "", "");
        finish();
    }
}
