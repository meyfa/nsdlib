package nsdlib.rendering.parts;

import java.util.Arrays;
import java.util.Collections;

import nsdlib.elements.NSDElement;
import nsdlib.elements.NSDInstruction;
import nsdlib.rendering.RenderColor;
import nsdlib.rendering.Size;
import nsdlib.rendering.renderer.RenderContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BraceRenderPartTest
{
    @Test
    public void findsForSource()
    {
        NSDElement source0 = new NSDInstruction("source0");
        MockRenderPart child0 = new MockRenderPart(source0);

        NSDElement source1 = new NSDInstruction("source1");
        MockRenderPart child1 = new MockRenderPart(source1);

        NSDElement sourceObj = new NSDInstruction("sourceObj");
        BraceRenderPart obj = new BraceRenderPart(sourceObj, Arrays.asList(child0, child1), true, "", true, "");

        assertSame(obj, obj.findForSource(sourceObj));
        assertSame(child0, obj.findForSource(source0));
        assertSame(child1, obj.findForSource(source1));
        assertNull(obj.findForSource(new NSDInstruction("other")));
    }

    @Test
    public void callsLayoutForChildren()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5, (s) -> 8);

        MockRenderPart child0 = new MockRenderPart();
        MockRenderPart child1 = new MockRenderPart();

        BraceRenderPart obj = new BraceRenderPart(null, Arrays.asList(child0, child1), true, "", true, "");

        obj.layout(ctx);

        assertTrue(child0.layoutCalled);
        assertTrue(child1.layoutCalled);
    }

    @Test
    public void calculatesSizeWithoutChildren()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5, (s) -> 8);

        BraceRenderPart obj = new BraceRenderPart(null, Collections.emptyList(), true, "top", true, "bottom");

        obj.layout(ctx);

        Size size = obj.getSize();
        // pad + "bottom" + pad
        assertEquals(8 + (6 * 5) + 8, size.width);
        // "top" + minHeight + "bottom"
        assertEquals((10 + 8 + 10) + 20 + (10 + 8 + 10), size.height);
    }

    @Test
    public void calculatesSizeWithoutChildrenOrTop()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5, (s) -> 8);

        BraceRenderPart obj = new BraceRenderPart(null, Collections.emptyList(), false, "top", true, "bottom");

        obj.layout(ctx);

        Size size = obj.getSize();
        // pad + "bottom" + pad
        assertEquals(8 + (6 * 5) + 8, size.width);
        // minHeight + "bottom"
        assertEquals(20 + (10 + 8 + 10), size.height);
    }

    @Test
    public void calculatesSizeWithoutChildrenOrBottom()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5, (s) -> 8);

        BraceRenderPart obj = new BraceRenderPart(null, Collections.emptyList(), true, "top", false, "bottom");

        obj.layout(ctx);

        Size size = obj.getSize();
        // pad + "top" + pad
        assertEquals(8 + (3 * 5) + 8, size.width);
        // "top" + minHeight
        assertEquals((10 + 8 + 10) + 20, size.height);
    }

    @Test
    public void calculatesSizeWithChildren()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5, (s) -> 8);

        MockRenderPart child0 = new MockRenderPart();
        child0.sizeToUse = new Size(200, 40);
        MockRenderPart child1 = new MockRenderPart();
        child1.sizeToUse = new Size(20, 20);

        BraceRenderPart obj = new BraceRenderPart(null, Arrays.asList(child0, child1), true, "top", true, "bottom");

        obj.layout(ctx);

        Size size = obj.getSize();
        // 2*pad + content
        assertEquals(16 + 200, size.width);
        // "top" + child0.height + child1.height + "bottom"
        assertEquals((10 + 8 + 10) + 40 + 20 + (10 + 8 + 10), size.height);
    }

    @Test
    public void rendersBackground()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5, (s) -> 8);
        MockRenderAdapter adapter = new MockRenderAdapter(ctx);

        BraceRenderPart obj = new BraceRenderPart(null, Collections.emptyList(), true, "top", true, "bottom");
        obj.setBackground(new RenderColor(0xFF, 0, 0));
        obj.layout(ctx);

        obj.render(adapter, 0, 0, obj.getSize().width);

        assertTrue(adapter.fillRectCalled);
    }

    @Test
    public void rendersChildren()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5, (s) -> 8);
        MockRenderAdapter adapter = new MockRenderAdapter(ctx);

        MockRenderPart child0 = new MockRenderPart();
        child0.sizeToUse = new Size(200, 40);
        MockRenderPart child1 = new MockRenderPart();
        child1.sizeToUse = new Size(20, 20);

        BraceRenderPart obj = new BraceRenderPart(null, Arrays.asList(child0, child1), true, "top", true, "bottom");
        obj.layout(ctx);

        obj.render(adapter, 0, 0, obj.getSize().width);

        assertTrue(child0.renderCalled);
        assertTrue(child1.renderCalled);

        assertEquals(child1.renderX, child0.renderX);
        assertEquals(child0.renderY + 40, child1.renderY);
        assertEquals(200, child0.renderW);
        assertEquals(200, child1.renderW);
    }
}
