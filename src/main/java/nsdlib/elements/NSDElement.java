package nsdlib.elements;

import nsdlib.rendering.parts.RenderPart;


/**
 * Base class for all NS diagram elements.
 */
public abstract class NSDElement
{
    private String label;

    /**
     * @param label The element's label.
     */
    public NSDElement(String label)
    {
        this.label = label;
    }

    /**
     * @return The element's label.
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * Sets the element's label to the given string.
     * 
     * @param label The new label.
     */
    public void setLabel(String label)
    {
        this.label = label;
    }

    /**
     * Converts this element into an instance of {@link RenderPart}, i.e. into
     * something that can be laid out and rendered.
     * 
     * @return A new render part for this element.
     */
    public abstract RenderPart toRenderPart();
}
