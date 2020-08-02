package nsdlib.rendering;

import java.util.Objects;

/**
 * Class for measuring the 2-dimensional size of something (i.e., the width and
 * height).
 */
public class Size
{
    /**
     * The width / horizontal component.
     */
    public final int width;
    /**
     * The height / vertical component.
     */
    public final int height;

    /**
     * Constructs a new Size object with the width and height both set to 0.
     */
    public Size()
    {
        this(0, 0);
    }

    /**
     * Constructs a new Size object with the given width and height.
     *
     * @param width The width / horizontal component.
     * @param height The height / vertical component.
     */
    public Size(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(width, height);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Size other = (Size) obj;
        return height == other.height && width == other.width;
    }
}
