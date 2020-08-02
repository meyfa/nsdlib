package nsdlib.rendering.renderer.awt;

import java.awt.image.BufferedImage;

import nsdlib.rendering.renderer.RenderAdapter;
import nsdlib.rendering.renderer.RenderContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AwtRendererTest
{
    @Test
    public void createsContextWithPadding()
    {
        AwtRenderer obj = new AwtRenderer();
        RenderContext ctx = obj.createContext();

        assertTrue(ctx.getHorizontalPadding() > 0);
        assertTrue(ctx.getVerticalPadding() > 0);
    }

    @Test
    public void createsContextThatMeasuresStringWidth()
    {
        AwtRenderer obj = new AwtRenderer();
        RenderContext ctx = obj.createContext();

        assertTrue(ctx.stringWidth("foobar") > ctx.stringWidth("foo"));
    }

    @Test
    public void createsContextThatMeasuresStringHeight()
    {
        AwtRenderer obj = new AwtRenderer();
        RenderContext ctx = obj.createContext();

        assertTrue(ctx.stringHeight("foobar") > 0);
    }

    @Test
    public void createsAdapterWithImageWithGivenDimensions()
    {
        AwtRenderer obj = new AwtRenderer();
        RenderAdapter<BufferedImage> adapter = obj
                .createAdapter(obj.createContext(), 20, 40);

        BufferedImage img = adapter.finish();

        assertEquals(20, img.getWidth());
        assertEquals(40, img.getHeight());
    }
}
