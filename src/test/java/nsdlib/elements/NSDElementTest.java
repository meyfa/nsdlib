package nsdlib.elements;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import nsdlib.rendering.parts.RenderPart;


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
