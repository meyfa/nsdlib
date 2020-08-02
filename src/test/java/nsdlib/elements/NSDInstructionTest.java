package nsdlib.elements;

import nsdlib.rendering.parts.BoxRenderPart;
import nsdlib.rendering.parts.RenderPart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class NSDInstructionTest
{
    @Test
    public void convertsToBoxRenderPart()
    {
        NSDInstruction obj = new NSDInstruction("foo");
        RenderPart part = obj.toRenderPart();

        assertTrue(part instanceof BoxRenderPart);
    }
}
