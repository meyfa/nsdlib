package nsdlib.elements;

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
}
