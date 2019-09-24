import com.coveros.selenified.utilities.TestCase;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;

public class TestCaseTest {
    @Test
    public void getTestNameTest(Method method) {
        assertEquals(TestCase.getTestName(method), "TestCaseTest.getTestNameTest");
    }

}
