package integration;

import org.testng.annotations.Test;

public class SelenifiedIT extends WebBase {

    @Test
    public void noAnnotationDetailsTest() {
        // verify 1 issues
        finish(1);
    }

    @Test(groups = {"integration", "selenified"})
    public void noDescriptionTest() {
        // verify 1 issues
        finish(1);
    }

    @Test(description = "A test to verify that logs work without any groups")
    public void noGroupsTest() {
        // verify 1 issues
        finish(1);
    }

    @Test(groups = "integration")
    public void oneGroupTest() {
        // verify 1 issues
        finish(1);
    }
}
