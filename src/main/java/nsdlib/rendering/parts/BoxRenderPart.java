package nsdlib.rendering.parts;

import nsdlib.rendering.Size;
import nsdlib.rendering.renderer.RenderAdapter;
import nsdlib.rendering.renderer.RenderContext;


/**
 * Render part for a simple instruction box.
 */
public class BoxRenderPart extends RenderPart
{
    private final String label;
    private Size size;

    /**
     * Constructs a new box part with the given label.
     * 
     * @param s The box label.
     */
    public BoxRenderPart(String s)
    {
        this.label = s;
    }

    @Override
    public void layout(RenderContext ctx)
    {
        size = ctx.box(label);
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
    }
}
