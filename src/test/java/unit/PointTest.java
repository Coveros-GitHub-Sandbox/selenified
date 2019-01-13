package unit;

import com.coveros.selenified.utilities.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class PointTest {

    @Test
    public void getXTest() {
        assertEquals(new Point(5, 10.2).getX(), 5);
        assertEquals(new Point("Hello", "World").getX(), "Hello");
        assertTrue((Boolean) new Point(true, false).getX());
    }

    @Test
    public void getYTest() {
        assertEquals(new Point(5, 10.2).getY(), 10.2);
        assertEquals(new Point("Hello", "World").getY(), "World");
        assertFalse((Boolean) new Point(true, false).getY());
    }

    @Test
    public void setXTest() {
        Point point = new Point(5, 10.2);
        point.setX(6);
        assertEquals(point.getX(), 6);
        point.setX("Hello");
        assertEquals(point.getX(), "Hello");
        point.setX(true);
        assertTrue((Boolean) point.getX());
    }

    @Test
    public void setYTest() {
        Point point = new Point(5, 10.2);
        point.setY(66.1);
        assertEquals(point.getY(), 66.1);
        point.setY("World");
        assertEquals(point.getY(), "World");
        point.setY(false);
        assertFalse((Boolean) point.getY());
    }
}
