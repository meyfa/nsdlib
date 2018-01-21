package nsdlib.rendering;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


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

        assertTrue(obj0.hashCode() == obj1.hashCode());
    }

    @Test
    public void performsValidEqualityTest()
    {
        Size obj0 = new Size(37, 42);
        Size obj1 = new Size(37, 42);

        assertTrue("obj0 not considered equal to itself", obj0.equals(obj0));

        assertTrue("obj1 not considered equal to obj0", obj0.equals(obj1));
        assertTrue("obj0 not considered equal to obj1", obj1.equals(obj0));

        assertFalse("another type considered equal to obj0",
                obj0.equals(new Object()));
        assertFalse("null considered equal to obj0", obj0.equals(null));

        Size obj2 = new Size(100, 200);
        assertFalse("obj2 considered equal to obj0", obj0.equals(obj2));
    }
}
