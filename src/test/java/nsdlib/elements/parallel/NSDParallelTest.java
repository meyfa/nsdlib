package nsdlib.elements.parallel;

import java.util.Arrays;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;
import nsdlib.rendering.parts.ParallelRenderPart;
import nsdlib.rendering.parts.RenderPart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class NSDParallelTest
{
    private static final NSDContainer<NSDElement> child0 = new NSDContainer<>("c0");
    private static final NSDContainer<NSDElement> child1 = new NSDContainer<>("c1");

    @Test
    public void addsChildrenGivenToConstructor()
    {
        NSDParallel obj = new NSDParallel();
        assertEquals(0, obj.countChildren());

        obj = new NSDParallel(Arrays.asList(child0, child1));
        assertEquals(2, obj.countChildren());
    }

    @Test
    public void convertsToParallelRenderPart()
    {
        NSDParallel obj = new NSDParallel(Arrays.asList(child0, child1));
        RenderPart part = obj.toRenderPart();

        assertTrue(part instanceof ParallelRenderPart);
    }
}
