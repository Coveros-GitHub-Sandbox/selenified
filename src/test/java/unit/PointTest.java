package unit;

import com.coveros.selenified.utilities.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest {

    @Test
    public void getXTest() {
        Assert.assertEquals(new Point(5, 10.2).getX(), 5);
        Assert.assertEquals(new Point("Hello", "World").getX(), "Hello");
        Assert.assertTrue((Boolean) new Point(true, false).getX());
    }

    @Test
    public void getYTest() {
        Assert.assertEquals(new Point(5, 10.2).getY(), 10.2);
        Assert.assertEquals(new Point("Hello", "World").getY(), "World");
        Assert.assertFalse((Boolean) new Point(true, false).getY());
    }

    @Test
    public void setXTest() {
        Point point = new Point(5, 10.2);
        point.setX(6);
        Assert.assertEquals(point.getX(), 6);
        point.setX("Hello");
        Assert.assertEquals(point.getX(), "Hello");
        point.setX(true);
        Assert.assertTrue((Boolean) point.getX());
    }

    @Test
    public void setYTest() {
        Point point = new Point(5, 10.2);
        point.setY(66.1);
        Assert.assertEquals(point.getY(), 66.1);
        point.setY("World");
        Assert.assertEquals(point.getY(), "World");
        point.setY(false);
        Assert.assertFalse((Boolean) point.getY());
    }
}
