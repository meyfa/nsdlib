package nsdlib.elements;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import nsdlib.rendering.parts.RenderPart;
import nsdlib.rendering.parts.RootRenderPart;


public class NSDRootTest
{
    private static NSDContainer<NSDElement> child0 = new NSDContainer<>("c0");
    private static NSDContainer<NSDElement> child1 = new NSDContainer<>("c1");

    @Test
    public void addsChildrenGivenToConstructor()
    {
        NSDRoot obj = new NSDRoot("foo");
        assertEquals(0, obj.countChildren());

        obj = new NSDRoot("foo", Arrays.asList(child0, child1));
        assertEquals(2, obj.countChildren());
    }

    @Test
    public void convertsToRootRenderPart()
    {
        NSDRoot obj = new NSDRoot("foo", Arrays.asList(child0, child1));
        RenderPart part = obj.toRenderPart();

        assertThat(part, instanceOf(RootRenderPart.class));
    }
}
