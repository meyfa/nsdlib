package nsdlib.elements;

import java.util.Collection;


/**
 * The root element for any NS diagram (structogram).
 */
public class NSDRoot extends NSDContainer<NSDElement>
{
    /**
     * @param label The element's label.
     */
    public NSDRoot(String label)
    {
        super(label);
    }

    /**
     * @param label The element's label.
     * @param children The element's initial child elements.
     */
    public NSDRoot(String label, Collection<? extends NSDElement> children)
    {
        super(label, children);
    }
}
