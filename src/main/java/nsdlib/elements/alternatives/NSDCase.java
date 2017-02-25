package nsdlib.elements.alternatives;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;
import nsdlib.rendering.parts.AlternativesRenderPart;
import nsdlib.rendering.parts.RenderPart;


/**
 * Represents a case / select / switch element, i.e. multiple possible execution
 * lists of which one is chosen depending on some input or question.
 */
public class NSDCase extends NSDContainer<NSDContainer<NSDElement>>
{
    /**
     * @param label The element's label.
     */
    public NSDCase(String label)
    {
        super(label);
    }

    /**
     * @param label The element's label.
     * @param children The element's initial child elements.
     */
    public NSDCase(String label,
            Collection<? extends NSDContainer<NSDElement>> children)
    {
        super(label, children);
    }

    @Override
    public RenderPart toRenderPart()
    {
        List<String> pathLabels = new ArrayList<>();
        List<RenderPart> pathContents = new ArrayList<>();

        for (NSDContainer<NSDElement> e : this) {
            pathLabels.add(e.getLabel());
            pathContents.add(e.toRenderPart());
        }

        return new AlternativesRenderPart(getLabel(), pathLabels,
                pathContents);
    }
}
