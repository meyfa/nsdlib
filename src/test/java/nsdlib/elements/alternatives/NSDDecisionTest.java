package nsdlib.elements.alternatives;

import java.util.Arrays;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;
import nsdlib.rendering.parts.AlternativesRenderPart;
import nsdlib.rendering.parts.RenderPart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class NSDDecisionTest
{
    private static final NSDContainer<NSDElement> child0 = new NSDContainer<>("c0");
    private static final NSDContainer<NSDElement> child1 = new NSDContainer<>("c1");

    @Test
    public void addsChildrenGivenToConstructor()
    {
        NSDDecision obj = new NSDDecision("foo");
        assertEquals(0, obj.getThen().countChildren());
        assertEquals(0, obj.getElse().countChildren());

        obj = new NSDDecision("foo", Arrays.asList(child0));
        assertEquals(1, obj.getThen().countChildren());
        assertEquals(0, obj.getElse().countChildren());

        obj = new NSDDecision("foo", Arrays.asList(child0),
                Arrays.asList(child1));
        assertEquals(1, obj.getThen().countChildren());
        assertEquals(1, obj.getElse().countChildren());
    }

    @Test
    public void hasTandFLabelsByDefault()
    {
        NSDDecision obj = new NSDDecision("foo");
        assertEquals("T", obj.getThen().getLabel());
        assertEquals("F", obj.getElse().getLabel());
    }

    @Test
    public void convertsToAlternativesRenderPart()
    {
        NSDDecision obj = new NSDDecision("foo");
        RenderPart part = obj.toRenderPart();

        assertTrue(part instanceof AlternativesRenderPart);
    }
}
