package nsdlib.elements.loops;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;
import nsdlib.rendering.parts.BraceRenderPart;
import nsdlib.rendering.parts.RenderPart;


/**
 * Represents a loop element that first runs its inner elements and then checks
 * the condition, so that the code is always run at least once (i.e. a
 * {@code do while} loop).
 */
public class NSDTestLastLoop extends NSDContainer<NSDElement>
{
    /**
     * @param label The element's label.
     */
    public NSDTestLastLoop(String label)
    {
        super(label);
    }

    /**
     * @param label The element's label.
     * @param children The element's initial child elements.
     */
    public NSDTestLastLoop(String label,
            Collection<? extends NSDElement> children)
    {
        super(label, children);
    }

    @Override
    public RenderPart toRenderPart()
    {
        List<RenderPart> children = stream().map(e -> e.toRenderPart())
                .collect(Collectors.toList());

        return new BraceRenderPart(this, children, false, null, true,
                getLabel());
    }
}
