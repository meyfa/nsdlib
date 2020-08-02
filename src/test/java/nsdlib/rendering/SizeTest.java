package nsdlib.rendering;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SizeTest
{
    @Test
    public void usesZeroByDefault()
    {
        Size obj = new Size();

        assertEquals(0, obj.width);
        assertEquals(0, obj.height);
    }

    @Test
    public void usesGivenDimensions()
    {
        Size obj = new Size(37, 42);

        assertEquals(37, obj.width);
        assertEquals(42, obj.height);
    }

    @Test
    public void calculatesSymmetricHashCode()
    {
        Size obj0 = new Size(37, 42);
        Size obj1 = new Size(37, 42);

        assertEquals(obj1.hashCode(), obj0.hashCode());
    }

    @Test
    public void performsValidEqualityTest()
    {
        Size obj0 = new Size(37, 42);
        Size obj1 = new Size(37, 42);

        assertEquals(obj0, obj0, "obj0 not considered equal to itself");

        assertEquals(obj1, obj0, "obj1 not considered equal to obj0");
        assertEquals(obj0, obj1, "obj0 not considered equal to obj1");

        assertNotEquals(new Object(), obj0, "another type considered equal to obj0");
        assertNotEquals(obj0, null, "null considered equal to obj0");

        Size obj2 = new Size(100, 200);
        assertNotEquals(obj2, obj0, "obj2 considered equal to obj0");
    }
}
