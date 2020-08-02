package nsdlib.rendering.parts;

import java.util.Arrays;

import nsdlib.elements.NSDElement;
import nsdlib.elements.NSDInstruction;
import nsdlib.rendering.RenderColor;
import nsdlib.rendering.Size;
import nsdlib.rendering.renderer.RenderContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class RootRenderPartTest
{
    @Test
    public void findsForSource()
    {
        NSDElement source0 = new NSDInstruction("source0");
        MockRenderPart child0 = new MockRenderPart(source0);

        NSDElement source1 = new NSDInstruction("source1");
        MockRenderPart child1 = new MockRenderPart(source1);

        NSDElement sourceObj = new NSDInstruction("sourceObj");
        RootRenderPart obj = new RootRenderPart(sourceObj, "root",
                Arrays.asList(child0, child1));

        assertSame(obj, obj.findForSource(sourceObj));
        assertSame(child0, obj.findForSource(source0));
        assertSame(child1, obj.findForSource(source1));
        assertNull(obj.findForSource(new NSDInstruction("other")));
    }

    @Test
    public void callsLayoutForChildren()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);

        MockRenderPart child0 = new MockRenderPart();
        MockRenderPart child1 = new MockRenderPart();

        RootRenderPart obj = new RootRenderPart(null, "root",
                Arrays.asList(child0, child1));

        obj.layout(ctx);

        assertTrue(child0.layoutCalled);
        assertTrue(child1.layoutCalled);
    }

    @Test
    public void calculatesSizeWithoutChildren()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);

        RootRenderPart obj = new RootRenderPart(null, "root", Arrays.asList());

        obj.layout(ctx);

        Size size = obj.getSize();
        // pad + label + pad
        assertEquals(8 + (4 * 5) + 8, size.width);
        // pad + label + pad + bottom
        assertEquals(10 + 8 + 10 + 10, size.height);
    }

    @Test
    public void calculatesSizeWithChildren()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);

        MockRenderPart child0 = new MockRenderPart();
        child0.sizeToUse = new Size(200, 10);
        MockRenderPart child1 = new MockRenderPart();
        child1.sizeToUse = new Size(20, 10);

        RootRenderPart obj = new RootRenderPart(null, "root",
                Arrays.asList(child0, child1));

        obj.layout(ctx);

        Size size = obj.getSize();
        // pad + child0.width + pad
        assertEquals(8 + 200 + 8, size.width);
        // pad + label + pad + content + bottom
        assertEquals(10 + 8 + 10 + 20 + 10, size.height);
    }

    @Test
    public void rendersBackground()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);
        MockRenderAdapter adapter = new MockRenderAdapter(ctx);

        RootRenderPart obj = new RootRenderPart(null, "root", Arrays.asList());
        obj.setBackground(new RenderColor(0xFF, 0, 0));
        obj.layout(ctx);

        obj.render(adapter, 0, 0, obj.getSize().width);

        assertTrue(adapter.fillRectCalled);
    }

    @Test
    public void rendersChildren()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);
        MockRenderAdapter adapter = new MockRenderAdapter(ctx);

        MockRenderPart child0 = new MockRenderPart();
        MockRenderPart child1 = new MockRenderPart();

        RootRenderPart obj = new RootRenderPart(null, "root",
                Arrays.asList(child0, child1));
        obj.layout(ctx);

        obj.render(adapter, 0, 0, obj.getSize().width);

        assertTrue(child0.renderCalled);
        assertTrue(child1.renderCalled);

        assertTrue(child0.renderX == child1.renderX);
        assertEquals(child0.renderY + child0.getSize().height, child1.renderY);
        assertEquals(child0.getSize().width, child0.renderW);
        assertEquals(child1.getSize().width, child1.renderW);
    }
}
