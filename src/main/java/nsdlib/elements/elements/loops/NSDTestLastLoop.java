package nsdlib.elements.elements.loops;

import java.util.Collection;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;


/**
 * Represents a loop element that first runs its inner elements and then checks
 * the condition, so that the code is always run at least once (i.e. a
 * {@code do while} loop).
 */
public class NSDTestLastLoop extends NSDContainer<NSDElement>
{
    /**
     * @param label The element's label.
     * 
     * @throws IllegalArgumentException If label is null (use the empty string).
     */
    public NSDTestLastLoop(String label)
    {
        super(label);
    }

    /**
     * @param label The element's label.
     * @param children The element's initial child elements.
     * 
     * @throws IllegalArgumentException If label is null (use the empty string).
     */
    public NSDTestLastLoop(String label,
            Collection<? extends NSDElement> children)
    {
        super(label, children);
    }
}
