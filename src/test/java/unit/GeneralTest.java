package unit;

import org.testng.Assert;
import org.testng.annotations.*;
import tools.General;

public class GeneralTest {
	
	@Test
	public void padRightSpaceTest() {
		Assert.assertEquals( General.padRightSpace("", 0), "");
		Assert.assertEquals( General.padRightSpace("", 5), "     ");
		Assert.assertEquals( General.padRightSpace("A", 0), "A");
		Assert.assertEquals( General.padRightSpace("A", 5), "A    ");
	}
	
	@Test
	public void padRightZerosTest() {
		Assert.assertEquals( General.padRightZeros(1, 0), "1");
		Assert.assertEquals( General.padRightZeros(1, 5), "10000");
	}
	
	@Test
	public void padRightIntegerTest() {
		Assert.assertEquals( General.padRight(1, 0, "Z"), "1");
		Assert.assertEquals( General.padRight(1, 5, "Z"), "1ZZZZ");
	}
	
	@Test
	public void padRightStringTest() {
		Assert.assertEquals( General.padRight("", 0, "Z"), "");
		Assert.assertEquals( General.padRight("", 5, "Z"), "ZZZZZ");
		Assert.assertEquals( General.padRight("A", 0, "Z"), "A");
		Assert.assertEquals( General.padRight("A", 5, "Z"), "AZZZZ");
	}
}