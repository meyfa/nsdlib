package nsdlib.rendering.parts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import nsdlib.elements.NSDElement;
import nsdlib.elements.NSDInstruction;
import nsdlib.rendering.RenderColor;
import nsdlib.rendering.Size;
import nsdlib.rendering.renderer.RenderContext;


public class ParallelRenderPartTest
{
    @Test
    public void findsForSource()
    {
        NSDElement source0 = new NSDInstruction("source0");
        MockRenderPart child0 = new MockRenderPart(source0);

        NSDElement source1 = new NSDInstruction("source1");
        MockRenderPart child1 = new MockRenderPart(source1);

        NSDElement sourceObj = new NSDInstruction("sourceObj");
        ParallelRenderPart obj = new ParallelRenderPart(sourceObj,
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

        ParallelRenderPart obj = new ParallelRenderPart(null,
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

        ParallelRenderPart obj = new ParallelRenderPart(null, Arrays.asList());

        obj.layout(ctx);

        Size size = obj.getSize();
        // minimumWidth = 6*padH
        assertEquals(60, size.width);
        // 2*pad + (no content) + 2*pad
        assertEquals(20 + 0 + 20, size.height);
    }

    @Test
    public void calculatesSizeWithChildren()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);

        MockRenderPart child0 = new MockRenderPart();
        child0.sizeToUse = new Size(200, 40);
        MockRenderPart child1 = new MockRenderPart();
        child1.sizeToUse = new Size(20, 20);

        ParallelRenderPart obj = new ParallelRenderPart(null,
                Arrays.asList(child0, child1));

        obj.layout(ctx);

        Size size = obj.getSize();
        // max(child0.width, child1.width) for both children
        assertEquals(200 + 200, size.width);
        // 2*pad + child0.width + 2*pad
        assertEquals(20 + 40 + 20, size.height);
    }

    @Test
    public void rendersBackground()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);
        MockRenderAdapter adapter = new MockRenderAdapter(ctx);

        ParallelRenderPart obj = new ParallelRenderPart(null, Arrays.asList());
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
        child0.sizeToUse = new Size(200, 40);
        MockRenderPart child1 = new MockRenderPart();
        child1.sizeToUse = new Size(20, 20);

        ParallelRenderPart obj = new ParallelRenderPart(null,
                Arrays.asList(child0, child1));
        obj.layout(ctx);

        obj.render(adapter, 0, 0, obj.getSize().width);

        assertTrue(child0.renderCalled);
        assertTrue(child1.renderCalled);

        assertEquals(child0.renderX + 200, child1.renderX);
        assertTrue(child0.renderY == child1.renderY);
        assertEquals(200, child0.renderW);
        assertEquals(200, child1.renderW);
    }
}
