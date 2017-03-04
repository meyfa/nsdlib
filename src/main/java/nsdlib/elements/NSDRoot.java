package nsdlib.elements;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import nsdlib.rendering.parts.RenderPart;
import nsdlib.rendering.parts.RootRenderPart;


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

    @Override
    public RenderPart toRenderPart()
    {
        List<RenderPart> children = stream().map(e -> e.toRenderPart())
                .collect(Collectors.toList());

        return new RootRenderPart(this, getLabel(), children);
    }
}
