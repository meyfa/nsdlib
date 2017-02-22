package nsdlib.elements;

/**
 * Base class for all NS diagram elements.
 */
public abstract class NSDElement
{
    private String label;

    /**
     * @param label The element's label.
     * 
     * @throws IllegalArgumentException If label is null (use the empty string).
     */
    public NSDElement(String label)
    {
        if (label == null) {
            throw new IllegalArgumentException("label may not be null");
        }
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
     * 
     * @throws IllegalArgumentException If label is null (use the empty string).
     */
    public void setLabel(String label)
    {
        if (label == null) {
            throw new IllegalArgumentException("label may not be null");
        }
        this.label = label;
    }
}
