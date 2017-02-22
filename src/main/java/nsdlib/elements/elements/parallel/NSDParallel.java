package nsdlib.elements.elements.parallel;

import java.util.Collection;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;


/**
 * Represents a "parallel processing" element, i.e. one that contains multiple
 * threads of blocks running in parallel.
 */
public class NSDParallel extends NSDContainer<NSDContainer<NSDElement>>
{
    /**
     * Constructs an empty "parallel processing" block.
     */
    public NSDParallel()
    {
        super("");
    }

    /**
     * @param children The element's initial child elements.
     */
    public NSDParallel(Collection<? extends NSDContainer<NSDElement>> children)
    {
        super("", children);
    }
}
