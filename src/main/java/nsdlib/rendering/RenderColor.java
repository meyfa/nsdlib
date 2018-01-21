package nsdlib.rendering;

/**
 * Represents an RGBA color with integer values from 0 - 255 for each component.
 */
public class RenderColor
{
    /** The color black (0, 0, 0). */
    public static final RenderColor BLACK = new RenderColor(0, 0, 0);
    /** The color white (255, 255, 255). */
    public static final RenderColor WHITE = new RenderColor(255, 255, 255);

    /** A fully transparent black (0, 0, 0, 0). */
    public static final RenderColor TRANSPARENT = new RenderColor(0, 0, 0, 0);

    private final int r, g, b, a;

    /**
     * Constructs a fully opaque (alpha = 255) color.
     *
     * @param r The red component.
     * @param g The green component.
     * @param b The blue component.
     */
    public RenderColor(int r, int g, int b)
    {
        this(r, g, b, 255);
    }

    /**
     * Constructs a color with optional transparency.
     *
     * @param r The red component.
     * @param g The green component.
     * @param b The blue component.
     * @param a The alpha component (0 = fully transparent, 255 = fully opaque).
     */
    public RenderColor(int r, int g, int b, int a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * @return This color's red component.
     */
    public int getRed()
    {
        return r;
    }

    /**
     * @return This color's green component.
     */
    public int getGreen()
    {
        return g;
    }

    /**
     * @return This color's blue component.
     */
    public int getBlue()
    {
        return b;
    }

    /**
     * @return This color's alpha component.
     */
    public int getAlpha()
    {
        return a;
    }
}
