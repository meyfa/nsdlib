package nsdlib.rendering.parts;

import java.util.Arrays;

import nsdlib.elements.NSDElement;
import nsdlib.elements.NSDInstruction;
import nsdlib.rendering.RenderColor;
import nsdlib.rendering.Size;
import nsdlib.rendering.parts.ContainerRenderPart.Orientation;
import nsdlib.rendering.renderer.RenderContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ContainerRenderPartTest
{
    @Test
    public void findsForSource()
    {
        NSDElement source0 = new NSDInstruction("source0");
        MockRenderPart child0 = new MockRenderPart(source0);

        NSDElement source1 = new NSDInstruction("source1");
        MockRenderPart child1 = new MockRenderPart(source1);

        ContainerRenderPart obj = new ContainerRenderPart(Orientation.VERTICAL,
                Arrays.asList(child0, child1));

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

        ContainerRenderPart obj = new ContainerRenderPart(Orientation.VERTICAL,
                Arrays.asList(child0, child1));

        obj.layout(ctx);

        assertTrue(child0.layoutCalled);
        assertTrue(child1.layoutCalled);
    }

    @Test
    public void calculatesSizeWithoutChildrenVertical()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);

        ContainerRenderPart obj = new ContainerRenderPart(Orientation.VERTICAL,
                Arrays.asList());

        obj.layout(ctx);

        Size size = obj.getSize();
        assertEquals(0, size.width);
        assertEquals(0, size.height);
    }

    @Test
    public void calculatesSizeWithoutChildrenHorizontal()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);

        ContainerRenderPart obj = new ContainerRenderPart(
                Orientation.HORIZONTAL, Arrays.asList());

        obj.layout(ctx);

        Size size = obj.getSize();
        assertEquals(0, size.width);
        assertEquals(0, size.height);
    }

    @Test
    public void calculatesSizeWithChildrenVertical()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);

        MockRenderPart child0 = new MockRenderPart();
        child0.sizeToUse = new Size(200, 40);
        MockRenderPart child1 = new MockRenderPart();
        child1.sizeToUse = new Size(20, 20);

        ContainerRenderPart obj = new ContainerRenderPart(Orientation.VERTICAL,
                Arrays.asList(child0, child1));

        obj.layout(ctx);

        Size size = obj.getSize();
        assertEquals(200, size.width);
        assertEquals(40 + 20, size.height);
    }

    @Test
    public void calculatesSizeWithChildrenHorizontal()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);

        MockRenderPart child0 = new MockRenderPart();
        child0.sizeToUse = new Size(200, 40);
        MockRenderPart child1 = new MockRenderPart();
        child1.sizeToUse = new Size(20, 20);

        ContainerRenderPart obj = new ContainerRenderPart(
                Orientation.HORIZONTAL, Arrays.asList(child0, child1));

        obj.layout(ctx);

        Size size = obj.getSize();
        // max(child0.width, child1.width) for both children
        assertEquals(200 + 200, size.width);
        // max(child0.height, child1.height)
        assertEquals(40, size.height);
    }

    @Test
    public void rendersBackground()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);
        MockRenderAdapter adapter = new MockRenderAdapter(ctx);

        MockRenderPart child0 = new MockRenderPart();
        MockRenderPart child1 = new MockRenderPart();

        ContainerRenderPart obj = new ContainerRenderPart(Orientation.VERTICAL,
                Arrays.asList(child0, child1));
        obj.setBackground(new RenderColor(0xFF, 0, 0));
        obj.layout(ctx);

        obj.render(adapter, 0, 0, obj.getSize().width);

        assertTrue(adapter.fillRectCalled);
    }

    @Test
    public void rendersChildrenVertical()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);
        MockRenderAdapter adapter = new MockRenderAdapter(ctx);

        MockRenderPart child0 = new MockRenderPart();
        child0.sizeToUse = new Size(200, 40);
        MockRenderPart child1 = new MockRenderPart();
        child1.sizeToUse = new Size(20, 20);

        ContainerRenderPart obj = new ContainerRenderPart(Orientation.VERTICAL,
                Arrays.asList(child0, child1));
        obj.layout(ctx);

        obj.render(adapter, 0, 0, obj.getSize().width);

        assertTrue(child0.renderCalled);
        assertTrue(child1.renderCalled);

        assertTrue(child0.renderX == child1.renderX);
        assertEquals(child0.renderY + 40, child1.renderY);
        assertEquals(200, child0.renderW);
        assertEquals(200, child1.renderW);
    }

    @Test
    public void rendersChildrenHorizontal()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5,
                (s) -> 8);
        MockRenderAdapter adapter = new MockRenderAdapter(ctx);

        MockRenderPart child0 = new MockRenderPart();
        child0.sizeToUse = new Size(200, 40);
        MockRenderPart child1 = new MockRenderPart();
        child1.sizeToUse = new Size(20, 20);

        ContainerRenderPart obj = new ContainerRenderPart(
                Orientation.HORIZONTAL, Arrays.asList(child0, child1));
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
