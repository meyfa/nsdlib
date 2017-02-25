package nsdlib.elements;

import nsdlib.rendering.parts.BoxRenderPart;
import nsdlib.rendering.parts.RenderPart;


/**
 * Represents a standard "process" or instruction block in an NS diagram.
 */
public class NSDInstruction extends NSDElement
{
    /**
     * @param label The element's label.
     */
    public NSDInstruction(String label)
    {
        super(label);
    }

    @Override
    public RenderPart toRenderPart()
    {
        return new BoxRenderPart(getLabel());
    }
}
