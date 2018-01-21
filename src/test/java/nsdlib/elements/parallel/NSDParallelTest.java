package nsdlib.elements.parallel;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;
import nsdlib.rendering.parts.ParallelRenderPart;
import nsdlib.rendering.parts.RenderPart;


public class NSDParallelTest
{
    private static NSDContainer<NSDElement> child0 = new NSDContainer<>("c0");
    private static NSDContainer<NSDElement> child1 = new NSDContainer<>("c1");

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

        assertThat(part, instanceOf(ParallelRenderPart.class));
    }
}
