package nsdlib.elements.loops;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;
import nsdlib.rendering.parts.BraceRenderPart;
import nsdlib.rendering.parts.RenderPart;


public class NSDTestFirstLoopTest
{
    private static NSDContainer<NSDElement> child0 = new NSDContainer<>("c0");
    private static NSDContainer<NSDElement> child1 = new NSDContainer<>("c1");

    @Test
    public void addsChildrenGivenToConstructor()
    {
        NSDTestFirstLoop obj = new NSDTestFirstLoop("foo");
        assertEquals(0, obj.countChildren());

        obj = new NSDTestFirstLoop("foo", Arrays.asList(child0, child1));
        assertEquals(2, obj.countChildren());
    }

    @Test
    public void convertsToBraceRenderPart()
    {
        NSDTestFirstLoop obj = new NSDTestFirstLoop("foo",
                Arrays.asList(child0, child1));
        RenderPart part = obj.toRenderPart();

        assertThat(part, instanceOf(BraceRenderPart.class));
    }
}
