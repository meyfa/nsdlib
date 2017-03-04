package nsdlib.elements.loops;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        List<RenderPart> children = stream().map(e -> e.toRenderPart())
                .collect(Collectors.toList());

        return new BraceRenderPart(this, children, true, null, true, null);
    }
}
