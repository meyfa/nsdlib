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
    private int decoHeight;

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
        content.layout(ctx);

        decoHeight = ctx.getVerticalPadding() * 2;

        Size contentSize = content.getSize();
        int minimumWidth = decoHeight * 3;

        int width = Math.max(minimumWidth, contentSize.width);
        int height = decoHeight * 2 + contentSize.height;

        size = new Size(width, height);
    }

    @Override
    public Size getSize()
    {
        return size;
    }

    @Override
    public void render(RenderAdapter<?> adapter, int x, int y, int w)
    {
        adapter.fillRect(x, y, w, decoHeight, getBackground());

        // draw top
        adapter.drawRect(x, y, w, decoHeight);
        adapter.drawLine(x, y + decoHeight, x + decoHeight, y);
        adapter.drawLine(x + w - decoHeight, y, x + w, y + decoHeight);
        y += decoHeight;

        // draw content
        content.render(adapter, x, y, w);
        y += content.getSize().height;

        adapter.fillRect(x, y, w, decoHeight, getBackground());

        // draw bottom
        adapter.drawRect(x, y, w, decoHeight);
        adapter.drawLine(x, y, x + decoHeight, y + decoHeight);
        adapter.drawLine(x + w - decoHeight, y + decoHeight, x + w, y);
    }
}
