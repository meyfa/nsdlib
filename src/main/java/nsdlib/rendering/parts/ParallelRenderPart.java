package nsdlib.rendering.parts;

import java.util.Collection;

import nsdlib.elements.NSDElement;
import nsdlib.rendering.Size;
import nsdlib.rendering.parts.ContainerRenderPart.Orientation;
import nsdlib.rendering.renderer.RenderAdapter;
import nsdlib.rendering.renderer.RenderContext;


/**
 * Render part for parallel processing elements, drawing the top and bottom
 * boxes with the children in between.
 */
public class ParallelRenderPart extends RenderPart
{
    private final ContainerRenderPart content;
    private Size size;

    /**
     * Constructs a new parallel processing part with the given children.
     * 
     * @param source This part's source element.
     * @param content This container's child parts.
     */
    public ParallelRenderPart(NSDElement source, Collection<RenderPart> content)
    {
        super(source);

        this.content = new ContainerRenderPart(Orientation.HORIZONTAL, content);
    }

    @Override
    public RenderPart findForSource(NSDElement source)
    {
        return source == getSource() ? this : content.findForSource(source);
    }

    @Override
    public void layout(RenderContext ctx)
    {
        size = ctx.box(null);
        size.width = Math.max(size.width, ctx.getVerticalPadding() * 6);
        size.height *= 2;

        content.layout(ctx);
        Size contentSize = content.getSize();

        size.width = Math.max(size.width, contentSize.width);
        size.height += contentSize.height;
    }

    @Override
    public Size getSize()
    {
        return size;
    }

    @Override
    public void render(RenderAdapter<?> adapter, int x, int y, int w)
    {
        int boxHeight = adapter.getContext().getVerticalPadding() * 2;

        // draw top
        adapter.drawRect(x, y, w, boxHeight);
        adapter.drawLine(x, y + boxHeight, x + boxHeight, y);
        adapter.drawLine(x + w - boxHeight, y, x + w, y + boxHeight);
        y += boxHeight;

        // draw content
        content.render(adapter, x, y, w);
        y += content.getSize().height;

        // draw bottom
        adapter.drawRect(x, y, w, boxHeight);
        adapter.drawLine(x, y, x + boxHeight, y + boxHeight);
        adapter.drawLine(x + w - boxHeight, y + boxHeight, x + w, y);
    }
}
