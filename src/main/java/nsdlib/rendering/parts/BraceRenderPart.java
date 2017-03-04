package nsdlib.rendering.parts;

import java.util.Collection;

import nsdlib.elements.NSDElement;
import nsdlib.rendering.Size;
import nsdlib.rendering.parts.ContainerRenderPart.Orientation;
import nsdlib.rendering.renderer.RenderAdapter;
import nsdlib.rendering.renderer.RenderContext;


/**
 * Render part for brace elements, as used by loops.
 */
public class BraceRenderPart extends RenderPart
{
    private final ContainerRenderPart content;
    private final boolean hasTop, hasBottom;
    private final String top, bottom;
    private Size size;
    private int braceLeftHeight;

    /**
     * Constructs a new braced container part with the given options and
     * children.
     * 
     * @param source This part's source element.
     * @param children This container's child parts.
     * @param hasTop Whether a top part shall be rendered for the brace.
     * @param top The brace's top part label.
     * @param hasBottom Whether a bottom part shall be rendered for the brace.
     * @param bottom The brace's bottom part label.
     */
    public BraceRenderPart(NSDElement source,
            Collection<? extends RenderPart> children, boolean hasTop,
            String top, boolean hasBottom, String bottom)
    {
        super(source);

        this.content = new ContainerRenderPart(Orientation.VERTICAL, children);

        this.hasTop = hasTop;
        this.top = top;
        this.hasBottom = hasBottom;
        this.bottom = bottom;
    }

    @Override
    public RenderPart findForSource(NSDElement source)
    {
        return source == getSource() ? this : content.findForSource(source);
    }

    @Override
    public void layout(RenderContext ctx)
    {
        size = new Size();

        size.width = ctx.getHorizontalPadding() * 2;

        content.layout(ctx);
        Size contentSize = content.getSize();
        size.width += contentSize.width;
        size.height += contentSize.height;

        size.height = Math.max(size.height, ctx.getVerticalPadding() * 2);

        braceLeftHeight = size.height;

        if (hasTop) {
            Size boxSize = ctx.box(top);
            size.width = Math.max(size.width, boxSize.width);
            size.height += boxSize.height;
        }

        if (hasBottom) {
            Size boxSize = ctx.box(bottom);
            size.width = Math.max(size.width, boxSize.width);
            size.height += boxSize.height;
        }
    }

    @Override
    public Size getSize()
    {
        return size;
    }

    @Override
    public void render(RenderAdapter<?> adapter, int x, int y, int w)
    {
        int braceLeftWidth = adapter.getContext().getHorizontalPadding() * 2;

        if (hasTop) {
            y += drawTop(adapter, x, y, w, braceLeftWidth);
        }

        drawLeft(adapter, x, y, braceLeftWidth);

        content.render(adapter, x + braceLeftWidth, y, w - braceLeftWidth);
        y += braceLeftHeight;

        if (hasBottom) {
            drawBottom(adapter, x, y, w, braceLeftWidth);
        }
    }

    private int drawTop(RenderAdapter<?> a, int x, int y, int w, int leftW)
    {
        int h = a.getContext().box(top).height;

        // top
        a.drawLine(x, y, x + w, y);
        // left + right
        a.drawLine(x, y, x, y + h);
        a.drawLine(x + w, y, x + w, y + h);
        // bottom
        a.drawLine(x + leftW, y + h, x + w, y + h);

        a.drawStringLeft(top, x, y);

        return h;
    }

    private void drawLeft(RenderAdapter<?> a, int x, int y, int w)
    {
        a.drawLine(x, y, x, y + braceLeftHeight);
        a.drawLine(x + w, y, x + w, y + braceLeftHeight);

        if (!hasTop) {
            a.drawLine(x, y, x + w, y);
        }
        if (!hasBottom) {
            a.drawLine(x, y + braceLeftHeight, x + w, y + braceLeftHeight);
        }
    }

    private int drawBottom(RenderAdapter<?> a, int x, int y, int w, int leftW)
    {
        int h = a.getContext().box(bottom).height;

        // top
        a.drawLine(x + leftW, y, x + w, y);
        // left + right
        a.drawLine(x, y, x, y + h);
        a.drawLine(x + w, y, x + w, y + h);
        // bottom
        a.drawLine(x, y + h, x + w, y + h);

        a.drawStringLeft(bottom, x, y);

        return h;
    }
}
