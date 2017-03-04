package nsdlib.rendering.parts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nsdlib.elements.NSDElement;
import nsdlib.rendering.Size;
import nsdlib.rendering.renderer.RenderAdapter;
import nsdlib.rendering.renderer.RenderContext;


/**
 * Render part that simply draws its children. The children can be laid out
 * vertically or horizontally.
 */
public class ContainerRenderPart extends RenderPart
{
    private final Orientation orientation;
    private final List<RenderPart> children;
    private Size size;

    /**
     * Constructs a new container part with the given orientation and children.
     * 
     * @param orientation Whether this is a horizontal or vertical layout.
     * @param children This container's child parts.
     */
    public ContainerRenderPart(Orientation orientation,
            Collection<? extends RenderPart> children)
    {
        this.orientation = orientation;
        this.children = Collections.unmodifiableList(new ArrayList<>(children));
    }

    @Override
    public RenderPart findForSource(NSDElement source)
    {
        if (source == getSource()) {
            return this;
        }
        return children.stream().map(c -> c.findForSource(source))
                .filter(p -> p != null).findAny().orElse(null);
    }

    @Override
    public void layout(RenderContext ctx)
    {
        size = new Size();

        Size childMaxSize = new Size();
        for (RenderPart e : children) {

            e.layout(ctx);
            Size eSize = e.getSize();

            childMaxSize.width = Math.max(childMaxSize.width, eSize.width);
            childMaxSize.height = Math.max(childMaxSize.height, eSize.height);

            // for vertical
            size.height += eSize.height;

        }

        size.width = childMaxSize.width;

        if (orientation == Orientation.HORIZONTAL) {
            size.width *= children.size();
            size.height = childMaxSize.height;
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
        adapter.drawRect(x, y, w, size.height);

        int childWidth = (orientation == Orientation.HORIZONTAL)
                ? (w / children.size()) : w;

        for (RenderPart e : children) {

            e.render(adapter, x, y, childWidth);

            if (orientation == Orientation.HORIZONTAL) {
                x += childWidth;
                adapter.drawLine(x, y, x, y + size.height);
            } else {
                y += e.getSize().height;
            }

        }
    }

    /**
     * Specifies a container's layout direction.
     */
    public static enum Orientation
    {
        /**
         * The container shall lay out its components next to each other,
         * dividing the available width equally among them.
         */
        HORIZONTAL,

        /**
         * The container shall lay out its components vertically, allocating the
         * full width to every one of them.
         */
        VERTICAL;
    }
}
