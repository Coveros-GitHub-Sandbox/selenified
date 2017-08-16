package unit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coveros.selenified.utilities.General;

import java.lang.reflect.Method;
import java.util.Map;

public class GeneralTest {

    @Test
    public void getRandomStringLengthTest() {
        Assert.assertEquals(General.getRandomString(0).length(), 0);
        Assert.assertEquals(General.getRandomString(0), "");
        Assert.assertEquals(General.getRandomString(999).length(), 999);
        Assert.assertTrue(General.getRandomString(999).matches("^[A-Za-z0-9]{999}$"));
        Assert.assertEquals(General.getRandomString(-1).length(), 0);
        Assert.assertEquals(General.getRandomString(-1), "");
    }

    @Test
    public void removeNonWordCharactersTest() {
        Assert.assertEquals(General.removeNonWordCharacters(null), null);
        Assert.assertEquals(General.removeNonWordCharacters(""), "");
        Assert.assertEquals(General.removeNonWordCharacters("hello world"), "helloworld");
        Assert.assertEquals(General.removeNonWordCharacters("hello-world"), "helloworld");
        Assert.assertEquals(General.removeNonWordCharacters("hello_world"), "helloworld");
        Assert.assertEquals(General.removeNonWordCharacters("hello`~!@#$%^&*()world"), "helloworld");
        Assert.assertEquals(General.removeNonWordCharacters("hello[]\\{}|;':\",./<>?world"), "helloworld");
    }

    @Test
    public void capitalizeFirstLettersTest() {
        Assert.assertEquals(General.capitalizeFirstLetters(null), null);
        Assert.assertEquals(General.capitalizeFirstLetters("hello world"), "Hello World");
        Assert.assertEquals(General.capitalizeFirstLetters("helloWorld"), "HelloWorld");
        Assert.assertEquals(General.capitalizeFirstLetters("hello_world"), "Hello_World");
        Assert.assertEquals(General.capitalizeFirstLetters("123helloWorld"), "123HelloWorld");
        Assert.assertEquals(General.capitalizeFirstLetters("hello123world"), "Hello123World");
        Assert.assertEquals(General.capitalizeFirstLetters("helloWorld123"), "HelloWorld123");
    }

    @Test
    public void getTestNameTest(Method method) {
        Assert.assertEquals(General.getTestName(method), "unit_GeneralTest_getTestNameTest");
        Object[] options = new Object[] { "Python", "public" };
        Assert.assertEquals(General.getTestName(method, options), "unit_GeneralTest_getTestNameTestWithOptionPython");
        options = new Object[] { "Python", null };
        Assert.assertEquals(General.getTestName(method, options), "unit_GeneralTest_getTestNameTestWithOptionPython");
        Assert.assertEquals(General.getTestName("", "UnitTests", "helloWorld"), "UnitTests_helloWorld");
        Assert.assertEquals(General.getTestName("", "UnitTests", "helloWorld", "python"),
                "UnitTests_helloWorldWithOptionPython");
        Assert.assertEquals(General.getTestName("", "UnitTests", "helloWorld", "visual basic"),
                "UnitTests_helloWorldWithOptionVisualbasic");
        Assert.assertEquals(General.getTestName("", "UnitTests", "helloWorld", "Python"),
                "UnitTests_helloWorldWithOptionPython");
        Assert.assertEquals(General.getTestName("", "UnitTests", "helloWorld", "Python", "Perl"),
                "UnitTests_helloWorldWithOptionPythonPerl");
        Assert.assertEquals(
                General.getTestName("", "UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby", "Groovy",
                        "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell", "Swift",
                        "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                        "Gosu", "Powershell", "Squeak", "Gambas"),
                "UnitTests_helloWorldWithOptionPythonPerlBashJavaRubyGroovyJavascriptPHPScalaFortanLispCOBOLErlangPacalHaskellSwiftElixirBASICTclRustVisualBasicCeylonCobraForthCurryCOMOLGosuPowershellSqueakGambas");
        String testName = General.getTestName("", "UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby",
                "Groovy", "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell",
                "Swift", "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                "Gosu", "Powershell", "Squeak", "Gambas", "Euphoria", "Fantom", "Assembly");
        Assert.assertTrue(testName.matches("^UnitTests_helloWorld@[0-9a-f]+$"));
        testName = General.getTestName("unit", "UnitTests", "helloWorld", "Python", "Perl", "Bash", "Java", "Ruby",
                "Groovy", "Javascript", "PHP", "Scala", "Fortan", "Lisp", "COBOL", "Erlang", "Pacal", "Haskell",
                "Swift", "Elixir", "BASIC", "Tcl", "Rust", "Visual Basic", "Ceylon", "Cobra", "Forth", "Curry", "COMOL",
                "Gosu", "Powershell", "Squeak", "Gambas", "Euphoria", "Fantom", "Assembly");
        Assert.assertTrue(testName.matches("^unit_UnitTests_helloWorld@[0-9a-f]+$"));
    }

    @Test
    public void parseMapTest() {
        Map<String, String> map = General.parseMap("A=B&C=D&E=F");
        Assert.assertTrue(map.containsKey("A"));
        Assert.assertEquals("B", map.get("A"));

        Assert.assertTrue(map.containsKey("C"));
        Assert.assertEquals("D", map.get("C"));

        Assert.assertTrue(map.containsKey("E"));
        Assert.assertEquals("F", map.get("E"));
    }
}