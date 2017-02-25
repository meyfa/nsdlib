package nsdlib.rendering.renderer;

import nsdlib.elements.NSDElement;
import nsdlib.rendering.Size;
import nsdlib.rendering.parts.RenderPart;


/**
 * Base class for NS diagram renderers. Subclasses are responsible for
 * implementing the methods for drawing to their respective medium or output.
 * 
 * <p>
 * An implementation for rendering to AWT's {@code BufferedImage} class is
 * available in {@code nsdlib.rendering.renderer.awt.AwtRenderer}.
 * 
 * <p>
 * Note that the actual drawing functions are not inside this class. Instead,
 * this class exists to instantiate proper {@link RenderContext} and
 * {@link RenderAdapter} instances that can then do the drawing.
 *
 * @param <T> The type of result returned by the renderer implementation.
 */
public abstract class NSDRenderer<T>
{
    /**
     * Renders the given NS diagram element using this renderer and returns the
     * result. The diagram is rendered in its intrinsic size.
     * 
     * @param nsd The element to render.
     * @return The render result.
     */
    public T render(NSDElement nsd)
    {
        return render(nsd.toRenderPart());
    }

    /**
     * Renders the given {@link RenderPart} using this renderer and returns the
     * result. The part is rendered in its intrinsic size.
     * 
     * <p>
     * This can be used if an element's render part has been precomputed.
     * 
     * @param part The part to render.
     * @return The render result.
     */
    public T render(RenderPart part)
    {
        RenderContext ctx = createContext();

        part.layout(ctx);
        Size size = part.getSize();

        RenderAdapter<T> adapter = createAdapter(ctx, size.width + 1,
                size.height + 1);

        part.render(adapter, 0, 0, size.width);

        return adapter.finish();
    }

    /**
     * Creates a {@link RenderContext} that can be used for laying out parts
     * before and during the render.
     * 
     * <p>
     * Subclasses are not required to return a new one every time, as render
     * contexts must be stateless.
     * 
     * @return A render context appropriate for this type of renderer.
     */
    public abstract RenderContext createContext();

    /**
     * Creates a {@link RenderAdapter} that acts as the connecting piece between
     * rather abstract drawing instructions and generating the final result.
     * 
     * @param context The render context used during the layout phase.
     * @param width The width of the result.
     * @param height The height of the result.
     * @return A render context appropriate for this type of renderer.
     */
    public abstract RenderAdapter<T> createAdapter(RenderContext context,
            int width, int height);
}
