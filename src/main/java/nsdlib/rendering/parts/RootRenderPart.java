package nsdlib.rendering.parts;

import java.util.Collection;

import nsdlib.rendering.Size;
import nsdlib.rendering.parts.ContainerRenderPart.Orientation;
import nsdlib.rendering.renderer.RenderAdapter;
import nsdlib.rendering.renderer.RenderContext;


/**
 * Render part for the root element, drawing a labeled box around all of its
 * children.
 */
public class RootRenderPart extends RenderPart
{
    private final String label;
    private final ContainerRenderPart content;
    private Size size;
    private int padHorizontal, boxHeight;

    /**
     * Constructs a new root render part with the given label and children.
     * 
     * @param label The part's label.
     * @param children This container's child parts.
     */
    public RootRenderPart(String label,
            Collection<? extends RenderPart> children)
    {
        this.label = label;
        this.content = new ContainerRenderPart(Orientation.VERTICAL, children);
    }

    @Override
    public void layout(RenderContext ctx)
    {
        size = new Size();

        content.layout(ctx);

        Size contentSize = content.getSize();
        size.width = contentSize.width;
        size.height = contentSize.height;

        padHorizontal = ctx.getHorizontalPadding();
        size.width += padHorizontal * 2;

        Size boxSize = ctx.box(label);
        size.height += (boxHeight = boxSize.height);
        size.width = Math.max(size.width, boxSize.width);

        size.height += ctx.getVerticalPadding();
    }

    @Override
    public Size getSize()
    {
        return size;
    }

    @Override
    public void render(RenderAdapter<?> adapter, int x, int y, int w)
    {
        adapter.drawRect(x, y, w, size.height);
        adapter.drawStringLeft(label, x, y);
        y += boxHeight;

        content.render(adapter, x + padHorizontal, y, w - padHorizontal * 2);
    }
}
