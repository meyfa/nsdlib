package nsdlib.rendering;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RenderColorTest
{
    @Test
    public void setsRGB()
    {
        RenderColor obj = new RenderColor(10, 20, 30);

        assertEquals(10, obj.getRed());
        assertEquals(20, obj.getGreen());
        assertEquals(30, obj.getBlue());

        assertEquals(255, obj.getAlpha());
    }

    @Test
    public void setsRGBA()
    {
        RenderColor obj = new RenderColor(10, 20, 30, 40);

        assertEquals(10, obj.getRed());
        assertEquals(20, obj.getGreen());
        assertEquals(30, obj.getBlue());

        assertEquals(40, obj.getAlpha());
    }
}
