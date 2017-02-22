package nsdlib.elements.elements.loops;

import java.util.Collection;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;


/**
 * Represents a loop element that loops forever.
 */
public class NSDForever extends NSDContainer<NSDElement>
{
    /**
     * Constructs an empty forever loop block.
     */
    public NSDForever()
    {
        super("");
    }

    /**
     * @param children The element's initial child elements.
     */
    public NSDForever(Collection<? extends NSDElement> children)
    {
        super("", children);
    }
}
