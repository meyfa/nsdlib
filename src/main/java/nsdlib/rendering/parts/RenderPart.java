package nsdlib.rendering.parts;

import nsdlib.rendering.Size;
import nsdlib.rendering.renderer.RenderAdapter;
import nsdlib.rendering.renderer.RenderContext;


/**
 * Base class for every NS part that can be rendered. Before
 * {@link #render(RenderAdapter, int, int, int)} is called, you MUST invoke
 * {@link #layout(RenderContext)}.
 */
public abstract class RenderPart
{
    /**
     * Recursively lays out this part and its children, using the given
     * {@link RenderContext} for measuring.
     * 
     * @param ctx The render context.
     */
    public abstract void layout(RenderContext ctx);

    /**
     * @return The amount of space this component requires. Note that it has to
     *         be laid out before this provides anything useful.
     * 
     * @see #layout(RenderContext)
     */
    public abstract Size getSize();

    /**
     * Renders this part and all its child parts to the given adapter. Rendering
     * starts at the point {@code (x, y)} and extends to
     * {@code (x + w, y + getSize().height)}.
     * 
     * <p>
     * Before this method is called, the part has to be laid out, preferrably
     * with the same context used by the adapter.
     * 
     * @param adapter The adapter to render to.
     * @param x The x coordinate to start rendering at.
     * @param y The y coordinate to start rendering at.
     * @param w The total width the component shall occupy.
     * 
     * @see #layout(RenderContext)
     */
    public abstract void render(RenderAdapter<?> adapter, int x, int y, int w);
}
