package nsdlib.rendering.renderer.awt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.awt.image.BufferedImage;

import org.junit.Test;

import nsdlib.rendering.RenderColor;
import nsdlib.rendering.renderer.RenderContext;


public class AwtRenderAdapterTest
{
    @Test
    public void drawsLines()
    {
        RenderContext ctx = new RenderContext(10, 10, (s) -> s.length() * 8,
                (s) -> 5);
        BufferedImage img = new BufferedImage(10, 10,
                BufferedImage.TYPE_INT_ARGB);

        AwtRenderAdapter obj = new AwtRenderAdapter(ctx, img);

        obj.drawLine(3, 5, 6, 5);

        assertEquals(0x00000000, img.getRGB(2, 5));

        assertEquals(0xFF000000, img.getRGB(3, 5));
        assertEquals(0xFF000000, img.getRGB(4, 5));
        assertEquals(0xFF000000, img.getRGB(5, 5));
        assertEquals(0xFF000000, img.getRGB(6, 5));

        assertEquals(0x00000000, img.getRGB(7, 5));
    }

    @Test
    public void drawsRects()
    {
        RenderContext ctx = new RenderContext(10, 10, (s) -> s.length() * 8,
                (s) -> 5);
        BufferedImage img = new BufferedImage(10, 10,
                BufferedImage.TYPE_INT_ARGB);

        AwtRenderAdapter obj = new AwtRenderAdapter(ctx, img);

        obj.drawRect(3, 5, 2, 2);

        // outside top left corner
        assertEquals(0x00000000, img.getRGB(2, 5));
        assertEquals(0x00000000, img.getRGB(3, 4));

        // top left corner
        assertEquals(0xFF000000, img.getRGB(3, 5));
        assertEquals(0xFF000000, img.getRGB(4, 5));
        assertEquals(0xFF000000, img.getRGB(3, 6));

        // bottom right corner
        assertEquals(0xFF000000, img.getRGB(4, 7));
        assertEquals(0xFF000000, img.getRGB(5, 7));
        assertEquals(0xFF000000, img.getRGB(5, 6));

        // outside bottom right corner
        assertEquals(0x00000000, img.getRGB(6, 7));
        assertEquals(0x00000000, img.getRGB(5, 8));
    }

    @Test
    public void fillsRects()
    {
        RenderContext ctx = new RenderContext(10, 10, (s) -> s.length() * 8,
                (s) -> 5);
        BufferedImage img = new BufferedImage(10, 10,
                BufferedImage.TYPE_INT_ARGB);

        AwtRenderAdapter obj = new AwtRenderAdapter(ctx, img);

        obj.fillRect(3, 5, 2, 2, new RenderColor(0xFF, 0x00, 0xFF));

        // outside top left corner
        assertEquals(0x00000000, img.getRGB(2, 5));
        assertEquals(0x00000000, img.getRGB(3, 4));

        // top left corner
        assertEquals(0xFFFF00FF, img.getRGB(3, 5));
        assertEquals(0xFFFF00FF, img.getRGB(4, 5));
        assertEquals(0xFFFF00FF, img.getRGB(3, 6));

        // bottom right corner
        assertEquals(0xFFFF00FF, img.getRGB(4, 7));
        assertEquals(0xFFFF00FF, img.getRGB(5, 7));
        assertEquals(0xFFFF00FF, img.getRGB(5, 6));

        // outside bottom right corner
        assertEquals(0x00000000, img.getRGB(6, 7));
        assertEquals(0x00000000, img.getRGB(5, 8));
    }

    @Test
    public void ignoresNullFillColor()
    {
        RenderContext ctx = new RenderContext(10, 10, (s) -> s.length() * 8,
                (s) -> 5);
        BufferedImage img = new BufferedImage(10, 10,
                BufferedImage.TYPE_INT_ARGB);

        AwtRenderAdapter obj = new AwtRenderAdapter(ctx, img);

        obj.fillRect(3, 5, 2, 2, null);

        // sample a few points
        assertEquals(0x00000000, img.getRGB(3, 5));
        assertEquals(0x00000000, img.getRGB(4, 5));
        assertEquals(0x00000000, img.getRGB(3, 6));
        assertEquals(0x00000000, img.getRGB(4, 7));
        assertEquals(0x00000000, img.getRGB(5, 7));
        assertEquals(0x00000000, img.getRGB(5, 6));
    }

    @Test
    public void returnsImage()
    {
        RenderContext ctx = new RenderContext(10, 10, (s) -> s.length() * 8,
                (s) -> 5);
        BufferedImage img = new BufferedImage(10, 10,
                BufferedImage.TYPE_INT_ARGB);

        AwtRenderAdapter obj = new AwtRenderAdapter(ctx, img);

        assertSame(img, obj.finish());
    }
}
