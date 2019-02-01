package unit;

import com.coveros.selenified.DriverSetup;
import com.coveros.selenified.OutputFile.Success;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SeleniumTest {

    @Test
    public void driverSetupTest() {
        assertFalse(DriverSetup.FALSE.useBrowser());
        assertTrue(DriverSetup.OPEN.useBrowser());
        assertTrue(DriverSetup.LOAD.useBrowser());

        assertFalse(DriverSetup.FALSE.loadPage());
        assertFalse(DriverSetup.OPEN.loadPage());
        assertTrue(DriverSetup.LOAD.loadPage());
    }

    @Test
    public void errorsForPassTest() {
        assertEquals(Success.PASS.getErrors(), 0);
    }

    @Test
    public void errorsForFailTest() {
        assertEquals(Success.FAIL.getErrors(), 1);
    }
}