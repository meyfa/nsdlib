package nsdlib.rendering.renderer;

import java.util.function.ToIntFunction;

import nsdlib.rendering.Size;


/**
 * This class defines general layout parameters for rendering NS diagrams.
 */
public class RenderContext
{
    private final int padH, padV;
    private final ToIntFunction<String> stringWidth, stringHeight;

    /**
     * @param padH The horizontal padding to either side of a rendered box.
     * @param padV The vertical padding to either side of a rendered box.
     * @param stringWidth A function taking a String that returns its width.
     * @param stringHeight A function taking a String that returns its height.
     */
    public RenderContext(int padH, int padV, ToIntFunction<String> stringWidth,
            ToIntFunction<String> stringHeight)
    {
        this.padH = padH;
        this.padV = padV;

        this.stringWidth = stringWidth;
        this.stringHeight = stringHeight;
    }

    /**
     * @return The amount of horizontal padding there should be on either side
     *         of a rendered box.
     */
    public int getHorizontalPadding()
    {
        return padH;
    }

    /**
     * @return The amount of vertical padding there should be on either side of
     *         a rendered box.
     */
    public int getVerticalPadding()
    {
        return padV;
    }

    /**
     * Measures the given string's width when drawn, in pixels. If the string is
     * {@code null}, a value of 0 is returned.
     *
     * @param s The string to measure.
     * @return The string's width when drawn.
     */
    public int stringWidth(String s)
    {
        return s == null ? 0 : stringWidth.applyAsInt(s);
    }

    /**
     * Measures the given string's height when drawn, in pixels. If the string
     * is {@code null}, a value of 0 is returned.
     *
     * @param s The string to measure.
     * @return The string's height when drawn.
     */
    public int stringHeight(String s)
    {
        return s == null ? 0 : stringHeight.applyAsInt(s);
    }

    /**
     * Measures the given string's width and height when drawn, in pixels. If
     * the string is {@code null}, both dimensions are 0.
     *
     * @param s The string to measure.
     * @return A size object describing the string's dimensions.
     */
    public Size measureString(String s)
    {
        return new Size(stringWidth(s), stringHeight(s));
    }

    /**
     * Utility function for calculating the size of a box around the given
     * string. This is done by measuring the string's size and combining it with
     * the defined padding on all four sides.
     *
     * @param s The string to calculate the box size for.
     * @return The box size for the given string.
     */
    public Size box(String s)
    {
        int width = stringWidth(s) + padH * 2;
        int height = stringHeight(s) + padV * 2;

        return new Size(width, height);
    }
}
