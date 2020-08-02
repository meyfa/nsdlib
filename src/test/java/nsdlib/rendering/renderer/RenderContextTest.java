package nsdlib.rendering.renderer;

import nsdlib.rendering.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class RenderContextTest
{
    @Test
    public void returnsPadding()
    {
        RenderContext obj = new RenderContext(37, 42, s -> 0, s -> 0);

        assertEquals(37, obj.getHorizontalPadding());
        assertEquals(42, obj.getVerticalPadding());
    }

    @Test
    public void calculatesStringWidth()
    {
        RenderContext obj = new RenderContext(37, 42, s -> 100, s -> 20);

        assertEquals(0, obj.stringWidth(null));
        assertEquals(100, obj.stringWidth("foobar"));
    }

    @Test
    public void calculatesStringHeight()
    {
        RenderContext obj = new RenderContext(37, 42, s -> 100, s -> 20);

        assertEquals(0, obj.stringHeight(null));
        assertEquals(20, obj.stringHeight("foobar"));
    }

    @Test
    public void calculatesStringSize()
    {
        RenderContext obj = new RenderContext(37, 42, s -> 100, s -> 20);

        assertEquals(new Size(0, 0), obj.measureString(null));
        assertEquals(new Size(100, 20), obj.measureString("foobar"));
    }

    @Test
    public void calculatesBox()
    {
        RenderContext obj = new RenderContext(37, 42, s -> 100, s -> 20);

        assertEquals(new Size(37 + 37, 42 + 42), obj.box(null));
        assertEquals(new Size(37 + 100 + 37, 42 + 20 + 42), obj.box("foobar"));
    }
}
