package nsdlib.elements.alternatives;

import java.util.Arrays;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;
import nsdlib.rendering.parts.AlternativesRenderPart;
import nsdlib.rendering.parts.RenderPart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class NSDCaseTest
{
    private static final NSDContainer<NSDElement> child0 = new NSDContainer<>("c0");
    private static final NSDContainer<NSDElement> child1 = new NSDContainer<>("c1");

    @Test
    public void addsChildrenGivenToConstructor()
    {
        NSDCase obj = new NSDCase("foo");
        assertEquals(0, obj.countChildren());

        obj = new NSDCase("foo", Arrays.asList(child0, child1));
        assertEquals(2, obj.countChildren());
    }

    @Test
    public void convertsToAlternativesRenderPart()
    {
        NSDCase obj = new NSDCase("foo", Arrays.asList(child0, child1));
        RenderPart part = obj.toRenderPart();

        assertTrue(part instanceof AlternativesRenderPart);
    }
}
