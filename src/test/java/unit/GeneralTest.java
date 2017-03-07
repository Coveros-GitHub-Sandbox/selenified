package unit;

import org.testng.Assert;
import org.testng.annotations.*;
import tools.General;

public class GeneralTest {

	@Test
	public void padRightSpaceTest() {
		Assert.assertEquals(General.padRightSpace("", 0), "");
		Assert.assertEquals(General.padRightSpace("", 5), "     ");
		Assert.assertEquals(General.padRightSpace("A", 0), "A");
		Assert.assertEquals(General.padRightSpace("A", 5), "A    ");
		Assert.assertEquals(General.padRightSpace(null, 0), "");
		Assert.assertEquals(General.padRightSpace(null, 5), "     ");
	}

	@Test
	public void padRightZerosTest() {
		Assert.assertEquals(General.padRightZeros(1, 0), "1");
		Assert.assertEquals(General.padRightZeros(1, 5), "10000");
	}

	@Test
	public void padRightIntegerTest() {
		Assert.assertEquals(General.padRight(1, 0, "Z"), "1");
		Assert.assertEquals(General.padRight(1, 5, "Z"), "1ZZZZ");
	}

	@Test
	public void padRightStringTest() {
		Assert.assertEquals(General.padRight("", 0, "Z"), "");
		Assert.assertEquals(General.padRight("", 5, "Z"), "ZZZZZ");
		Assert.assertEquals(General.padRight("A", 0, "Z"), "A");
		Assert.assertEquals(General.padRight("A", 5, "Z"), "AZZZZ");
		Assert.assertEquals(General.padRight(null, 0, "Z"), "");
		Assert.assertEquals(General.padRight(null, 5, "Z"), "ZZZZZ");
	}

	@Test
	public void padLeftSpaceTest() {
		Assert.assertEquals(General.padLeftSpace("", 0), "");
		Assert.assertEquals(General.padLeftSpace("", 5), "     ");
		Assert.assertEquals(General.padLeftSpace("A", 0), "A");
		Assert.assertEquals(General.padLeftSpace("A", 5), "    A");
		Assert.assertEquals(General.padLeftSpace(null, 0), "");
		Assert.assertEquals(General.padLeftSpace(null, 5), "     ");
	}

	@Test
	public void padLeftZerosTest() {
		Assert.assertEquals(General.padLeftZeros(1, 0), "1");
		Assert.assertEquals(General.padLeftZeros(1, 5), "00001");
	}

	@Test
	public void padLeftIntegerTest() {
		Assert.assertEquals(General.padLeft(1, 0, "Z"), "1");
		Assert.assertEquals(General.padLeft(1, 5, "Z"), "ZZZZ1");
	}

	@Test
	public void padLeftStringTest() {
		Assert.assertEquals(General.padLeft("", 0, "Z"), "");
		Assert.assertEquals(General.padLeft("", 5, "Z"), "ZZZZZ");
		Assert.assertEquals(General.padLeft("A", 0, "Z"), "A");
		Assert.assertEquals(General.padLeft("A", 5, "Z"), "ZZZZA");
		Assert.assertEquals(General.padLeft(null, 0, "Z"), "");
		Assert.assertEquals(General.padLeft(null, 5, "Z"), "ZZZZZ");
	}

	@Test
	public void reverseItTest() {
		Assert.assertEquals(General.reverseIt(""), "");
		Assert.assertEquals(General.reverseIt("ABC"), "CBA");
		Assert.assertEquals(General.reverseIt(null), null);
	}

	@Test
	public void getRandomCharTest() {
		Assert.assertEquals(General.getRandomChar().length(), 1);
		Assert.assertTrue(General.getRandomChar().matches("^[A-Za-z0-9]$"));
	}
	
	@Test
	public void getRandomStringTest() {
		Assert.assertEquals(General.getRandomString().length(), 32);
		Assert.assertTrue(General.getRandomString().matches("^[A-Za-z0-9]{32}$"));
	}
	
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
	public void getRandomIntTest() {
		Assert.assertEquals(General.getRandomInt().length(), 9);
		Assert.assertTrue(General.getRandomInt().matches("^[0-9]{9}$"));
	}
	
	@Test
	public void getRandomIntLengthTest() {
		Assert.assertEquals(General.getRandomInt(0).length(), 0);
		Assert.assertEquals(General.getRandomInt(0), "");
		Assert.assertEquals(General.getRandomInt(999).length(), 999);
		Assert.assertTrue(General.getRandomInt(999).matches("^[0-9]{999}$"));
		Assert.assertEquals(General.getRandomInt(-1).length(), 0);
		Assert.assertEquals(General.getRandomInt(-1), "");
	}
}