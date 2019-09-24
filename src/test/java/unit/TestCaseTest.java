package unit;

import com.coveros.selenified.utilities.TestCase;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestCaseTest {

    @Test
    public void getRandomStringLengthTest() {
        assertEquals(TestCase.getRandomString(0).length(), 0);
        assertEquals(TestCase.getRandomString(0), "");
        assertEquals(TestCase.getRandomString(999).length(), 999);
        assertTrue(TestCase.getRandomString(999).matches("^[A-Za-z0-9]{999}$"));
        assertEquals(TestCase.getRandomString(-1).length(), 0);
        assertEquals(TestCase.getRandomString(-1), "");
    }

    @Test
    public void removeNonWordCharactersTest() {
        assertEquals(TestCase.removeNonWordCharacters(null), null);
        assertEquals(TestCase.removeNonWordCharacters(""), "");
        assertEquals(TestCase.removeNonWordCharacters("hello world"), "helloworld");
        assertEquals(TestCase.removeNonWordCharacters("hello-world"), "helloworld");
        assertEquals(TestCase.removeNonWordCharacters("hello_world"), "helloworld");
        assertEquals(TestCase.removeNonWordCharacters("hello`~!@#$%^&*()world"), "helloworld");
        assertEquals(TestCase.removeNonWordCharacters("hello[]\\{}|;':\",./<>?world"), "helloworld");
    }

    @Test
    public void getTestNameNullTest(Method method) {
        assertEquals(TestCase.getTestName("UnitTests", "helloWorld"), "UnitTests.helloWorld");
        assertEquals(TestCase.getTestName("UnitTests", "helloWorld", null), "UnitTests.helloWorld");
        assertTrue(TestCase.getTestName("UnitTests", "helloWorld", new Object[]{}).startsWith("UnitTests.helloWorld"));
        assertTrue(TestCase.getTestName( "UnitTests", "helloWorld", new Object[]{null}).startsWith("UnitTests.helloWorld"));

    }

    @Test
    public void getTestNameTest(Method method) {
        assertEquals(TestCase.getTestName(method), "unit.TestCaseTest.getTestNameTest");
        Object[] options = new Object[]{"Python", "public"};
        assertEquals(TestCase.getTestName(method, options),
                "unit.TestCaseTest.getTestNameTestWithOptionPythonPublic");
        options = new Object[]{"Python", null};
        assertEquals(TestCase.getTestName(method, options),
                "unit.TestCaseTest.getTestNameTestWithOptionPython");
        assertEquals(TestCase.getTestName("UnitTests", "helloWorld", "python"),
                "UnitTests.helloWorldWithOptionPython");
        assertEquals(TestCase.getTestName("UnitTests", "helloWorld", "visual basic"),
                "UnitTests.helloWorldWithOptionVisualbasic");
        assertEquals(TestCase.getTestName("UnitTests", "helloWorld", "Python"),
                "UnitTests.helloWorldWithOptionPython");
        assertEquals(TestCase.getTestName("UnitTests", "helloWorld", "Python", "Perl"),
                "UnitTests.helloWorldWithOptionPythonPerl");
        assertEquals(TestCase
                        .getTestName("UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby", "Groovy",
                                "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell", "Swift",
                                "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                                "Gosu", "Powershell", "Squeak", "Gambas"),
                "UnitTests.helloWorldWithOptionPythonPerlBashJavaRubyGroovyJavascriptPHPScalaFortanLispCOBOLErlangPacalHaskellSwiftElixirBASICTclRustVisualBasicCeylonCobraForthCurryCOMOLGosuPowershellSqueakGambas");
        String testName = TestCase
                .getTestName("UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby", "Groovy",
                        "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell", "Swift",
                        "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                        "Gosu", "Powershell", "Squeak", "Gambas", "Euphoria", "Fantom", "Assembly");
        assertTrue(testName.matches("^UnitTests\\.helloWorld@[0-9a-f]+$"));
    }

}
