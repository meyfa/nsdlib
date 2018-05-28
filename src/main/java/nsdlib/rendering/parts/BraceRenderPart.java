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
    private int leftWidth, contentHeight;
    private int topHeight, bottomHeight;

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
        content.layout(ctx);

        Size contentSize = content.getSize();
        int minimumContentHeight = ctx.getVerticalPadding() * 2;

        leftWidth = ctx.getHorizontalPadding() * 2;
        contentHeight = Math.max(contentSize.height, minimumContentHeight);

        int width = leftWidth + contentSize.width;
        int height = contentHeight;

        if (hasTop) {
            Size boxSize = ctx.box(top);
            width = Math.max(width, boxSize.width);
            topHeight = boxSize.height;
            height += topHeight;
        }

        if (hasBottom) {
            Size boxSize = ctx.box(bottom);
            width = Math.max(width, boxSize.width);
            bottomHeight = boxSize.height;
            height += bottomHeight;
        }

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
        if (hasTop) {
            y += drawTop(adapter, x, y, w);
        }

        drawLeft(adapter, x, y);

        content.render(adapter, x + leftWidth, y, w - leftWidth);
        y += contentHeight;

        if (hasBottom) {
            drawBottom(adapter, x, y, w);
        }
    }

    private int drawTop(RenderAdapter<?> a, int x, int y, int w)
    {
        a.fillRect(x, y, w, topHeight, getBackground());

        // top
        a.drawLine(x, y, x + w, y);
        // left + right
        a.drawLine(x, y, x, y + topHeight);
        a.drawLine(x + w, y, x + w, y + topHeight);
        // bottom
        a.drawLine(x + leftWidth, y + topHeight, x + w, y + topHeight);

        a.drawStringLeft(top, x, y);

        return topHeight;
    }

    private void drawLeft(RenderAdapter<?> a, int x, int y)
    {
        a.fillRect(x, y, leftWidth, contentHeight, getBackground());

        a.drawLine(x, y, x, y + contentHeight);
        a.drawLine(x + leftWidth, y, x + leftWidth, y + contentHeight);

        if (!hasTop) {
            a.drawLine(x, y, x + leftWidth, y);
        }
        if (!hasBottom) {
            a.drawLine(x, y + contentHeight, x + leftWidth, y + contentHeight);
        }
    }

    private int drawBottom(RenderAdapter<?> a, int x, int y, int w)
    {
        a.fillRect(x, y, w, bottomHeight, getBackground());

        // top
        a.drawLine(x + leftWidth, y, x + w, y);
        // left + right
        a.drawLine(x, y, x, y + bottomHeight);
        a.drawLine(x + w, y, x + w, y + bottomHeight);
        // bottom
        a.drawLine(x, y + bottomHeight, x + w, y + bottomHeight);

        a.drawStringLeft(bottom, x, y);

        return bottomHeight;
    }
}
