package nsdlib.elements.loops;

import java.util.Collection;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;


/**
 * Represents a loop element that first checks its condition before the inner
 * elements are run (i.e. a {@code while} loop).
 */
public class NSDTestFirstLoop extends NSDContainer<NSDElement>
{
    /**
     * @param label The element's label.
     * 
     * @throws IllegalArgumentException If label is null (use the empty string).
     */
    public NSDTestFirstLoop(String label)
    {
        super(label);
    }

    /**
     * @param label The element's label.
     * @param children The element's initial child elements.
     * 
     * @throws IllegalArgumentException If label is null (use the empty string).
     */
    public NSDTestFirstLoop(String label,
            Collection<? extends NSDElement> children)
    {
        super(label, children);
    }
}
