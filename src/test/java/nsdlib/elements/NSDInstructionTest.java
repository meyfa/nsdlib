package nsdlib.elements;

import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Test;

import static org.junit.Assert.assertThat;

import nsdlib.rendering.parts.BoxRenderPart;
import nsdlib.rendering.parts.RenderPart;


public class NSDInstructionTest
{
    @Test
    public void convertsToBoxRenderPart()
    {
        NSDInstruction obj = new NSDInstruction("foo");
        RenderPart part = obj.toRenderPart();

        assertThat(part, instanceOf(BoxRenderPart.class));
    }
}
