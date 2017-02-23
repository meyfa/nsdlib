package nsdlib.elements.alternatives;

import java.util.Collection;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;


/**
 * Represents a case / select / switch element, i.e. multiple possible execution
 * lists of which one is chosen depending on some input or question.
 */
public class NSDCase extends NSDContainer<NSDContainer<NSDElement>>
{
    /**
     * @param label The element's label.
     * 
     * @throws IllegalArgumentException If label is null (use the empty string).
     */
    public NSDCase(String label)
    {
        super(label);
    }

    /**
     * @param label The element's label.
     * @param children The element's initial child elements.
     * 
     * @throws IllegalArgumentException If label is null (use the empty string).
     */
    public NSDCase(String label,
            Collection<? extends NSDContainer<NSDElement>> children)
    {
        super(label, children);
    }
}
