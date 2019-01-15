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
    public void capitalizeFirstLettersTest() {
        assertEquals(TestCase.capitalizeFirstLetters(null), null);
        assertEquals(TestCase.capitalizeFirstLetters("hello world"), "Hello World");
        assertEquals(TestCase.capitalizeFirstLetters("helloWorld"), "HelloWorld");
        assertEquals(TestCase.capitalizeFirstLetters("hello_world"), "Hello_World");
        assertEquals(TestCase.capitalizeFirstLetters("123helloWorld"), "123HelloWorld");
        assertEquals(TestCase.capitalizeFirstLetters("hello123world"), "Hello123World");
        assertEquals(TestCase.capitalizeFirstLetters("helloWorld123"), "HelloWorld123");
    }

    @Test
    public void getTestNameTest(Method method) {
        assertEquals(TestCase.getTestName(method), "unit_TestCaseTest_getTestNameTest");
        Object[] options = new Object[]{"Python", "public"};
        assertEquals(TestCase.getTestName(method, options),
                "unit_TestCaseTest_getTestNameTestWithOptionPython");
        options = new Object[]{"Python", null};
        assertEquals(TestCase.getTestName(method, options),
                "unit_TestCaseTest_getTestNameTestWithOptionPython");
        assertEquals(TestCase.getTestName("", "UnitTests", "helloWorld"), "UnitTests_helloWorld");
        assertEquals(TestCase.getTestName("", "UnitTests", "helloWorld", "python"),
                "UnitTests_helloWorldWithOptionPython");
        assertEquals(TestCase.getTestName("", "UnitTests", "helloWorld", "visual basic"),
                "UnitTests_helloWorldWithOptionVisualbasic");
        assertEquals(TestCase.getTestName("", "UnitTests", "helloWorld", "Python"),
                "UnitTests_helloWorldWithOptionPython");
        assertEquals(TestCase.getTestName("", "UnitTests", "helloWorld", "Python", "Perl"),
                "UnitTests_helloWorldWithOptionPythonPerl");
        assertEquals(TestCase
                        .getTestName("", "UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby", "Groovy",
                                "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell", "Swift",
                                "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                                "Gosu", "Powershell", "Squeak", "Gambas"),
                "UnitTests_helloWorldWithOptionPythonPerlBashJavaRubyGroovyJavascriptPHPScalaFortanLispCOBOLErlangPacalHaskellSwiftElixirBASICTclRustVisualBasicCeylonCobraForthCurryCOMOLGosuPowershellSqueakGambas");
        String testName = TestCase
                .getTestName("", "UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby", "Groovy",
                        "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell", "Swift",
                        "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                        "Gosu", "Powershell", "Squeak", "Gambas", "Euphoria", "Fantom", "Assembly");
        assertTrue(testName.matches("^UnitTests_helloWorld@[0-9a-f]+$"));
        testName = TestCase
                .getTestName("unit", "UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby", "Groovy",
                        "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell", "Swift",
                        "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                        "Gosu", "Powershell", "Squeak", "Gambas", "Euphoria", "Fantom", "Assembly");
        assertTrue(testName.matches("^unit_UnitTests_helloWorld@[0-9a-f]+$"));
    }

}
