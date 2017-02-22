package nsdlib.elements;

/**
 * Represents a standard "process" or instruction block in an NS diagram.
 */
public class NSDInstruction extends NSDElement
{
    /**
     * @param label The element's label.
     * 
     * @throws IllegalArgumentException If label is null (use the empty string).
     */
    public NSDInstruction(String label)
    {
        super(label);
    }
}
