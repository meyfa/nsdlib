package nsdlib.elements;

import nsdlib.rendering.parts.RenderPart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class NSDElementTest
{
    @Test
    public void updatesLabel()
    {
        NSDElement obj = new NSDElement("foo") {
            @Override
            public RenderPart toRenderPart()
            {
                return null;
            }
        };

        assertEquals("foo", obj.getLabel());

        obj.setLabel("bar");
        assertEquals("bar", obj.getLabel());
    }
}
