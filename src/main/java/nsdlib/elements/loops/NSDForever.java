package nsdlib.elements.loops;

import java.util.Collection;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;
import nsdlib.rendering.parts.BraceRenderPart;
import nsdlib.rendering.parts.RenderPart;


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
        super(null);
    }

    /**
     * @param children The element's initial child elements.
     */
    public NSDForever(Collection<? extends NSDElement> children)
    {
        super(null, children);
    }

    @Override
    public RenderPart toRenderPart()
    {
        return new BraceRenderPart(this, getChildRenderParts(), true, null,
                true, null);
    }
}
