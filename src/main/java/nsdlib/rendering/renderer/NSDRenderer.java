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
     * Renders the given NS diagram element using this renderer and returns the result.
     * This is the same as calling {@link NSDElement#toRenderPart()} followed by
     * {@link #render(RenderPart, double)}.
     *
     * @param nsd The element to render.
     * @param scale The scaling factor to apply.
     * @return The render result.
     */
    public T render(NSDElement nsd, double scale)
    {
        return render(nsd.toRenderPart(), scale);
    }

    /**
     * Renders the given {@link RenderPart} using this renderer and returns the result.
     *
     * <p>
     * The diagram is rendered in its intrinsic size scaled by the given factor.
     * For example, if the diagram in its layout-ed form is 200x300 and scale factor is 2,
     * the resulting image will be 400x600.
     *
     * <p>
     * This can be used if an element's render part has been precomputed.
     *
     * @param part The part to render.
     * @param scale The scaling factor to apply.
     * @return The render result.
     */
    public T render(RenderPart part, double scale)
    {
        RenderContext ctx = createContext();

        part.layout(ctx);
        Size size = part.getSize();

        int width = (int) Math.ceil((size.width + 1) * scale);
        int height = (int) Math.ceil((size.height + 1) * scale);

        RenderAdapter<T> adapter = createAdapter(ctx, width, height, scale);
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
     * <p>
     * The width and height of the result will be exactly as provided, while
     * everything that is drawn will be scaled by the given factor. It is the
     * caller's responsibility to ensure width/height and scale are matched to
     * part dimensions.
     *
     * @param context The render context used during the layout phase.
     * @param width The width of the result.
     * @param height The height of the result.
     * @param scale The scaling to apply to any drawing operation.
     * @return A render context appropriate for this type of renderer.
     */
    public abstract RenderAdapter<T> createAdapter(RenderContext context, int width, int height, double scale);
}
