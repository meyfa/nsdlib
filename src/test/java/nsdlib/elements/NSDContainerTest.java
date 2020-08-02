package nsdlib.elements;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import nsdlib.rendering.parts.ContainerRenderPart;
import nsdlib.rendering.parts.RenderPart;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class NSDContainerTest
{
    private static final NSDContainer<NSDElement> child0 = new NSDContainer<>("c0");
    private static final NSDContainer<NSDElement> child1 = new NSDContainer<>("c1");

    @Test
    public void addsChildrenGivenToConstructor()
    {
        NSDContainer<NSDElement> obj = new NSDContainer<>("foo");
        assertEquals(0, obj.countChildren());

        obj = new NSDContainer<>("foo", Arrays.asList(child0, child1));
        assertEquals(2, obj.countChildren());
    }

    @Test
    public void returnsCorrectChild()
    {
        NSDContainer<NSDElement> obj = new NSDContainer<>("foo", Arrays.asList(child0, child1));

        assertSame(child0, obj.getChild(0));
        assertSame(child1, obj.getChild(1));
    }

    @Test
    public void replacesChildren()
    {
        NSDContainer<NSDElement> obj = new NSDContainer<>("foo", Collections.singletonList(child0));
        obj.setChild(0, child1);

        assertEquals(1, obj.countChildren());
        assertSame(child1, obj.getChild(0));
    }

    @Test
    public void addsChildrenAtEnd()
    {
        NSDContainer<NSDElement> obj = new NSDContainer<>("foo", Collections.singletonList(child0));

        assertEquals(1, obj.countChildren());

        obj.addChild(child1);
        assertEquals(2, obj.countChildren());
        assertSame(child1, obj.getChild(1));
    }

    @Test
    public void addsChildrenAtIndex()
    {
        NSDContainer<NSDElement> obj = new NSDContainer<>("foo", Collections.singletonList(child0));

        assertEquals(1, obj.countChildren());

        obj.addChild(0, child1);
        assertEquals(2, obj.countChildren());
        assertSame(child1, obj.getChild(0));
    }

    @Test
    public void throwsWhenAddingItselfAsChild()
    {
        NSDContainer<NSDElement> obj = new NSDContainer<>("foo", Collections.singletonList(child0));

        assertThrows(IllegalArgumentException.class, () -> obj.setChild(0, obj));
        assertThrows(IllegalArgumentException.class, () -> obj.addChild(obj));
        assertThrows(IllegalArgumentException.class, () -> obj.addChild(0, obj));
    }

    @Test
    public void throwsWhenAddingNullAsChild()
    {
        NSDContainer<NSDElement> obj = new NSDContainer<>("foo", Collections.singletonList(child0));

        assertThrows(IllegalArgumentException.class, () -> obj.setChild(0, null));
        assertThrows(IllegalArgumentException.class, () -> obj.addChild(null));
        assertThrows(IllegalArgumentException.class, () -> obj.addChild(0, null));
    }

    @Test
    public void removesCorrectChildByIndex()
    {
        NSDContainer<NSDElement> obj = new NSDContainer<>("foo", Arrays.asList(child0, child1));
        obj.removeChild(1);

        assertEquals(1, obj.countChildren());
        assertSame(child0, obj.getChild(0));
    }

    @Test
    public void removesCorrectChildByInstance()
    {
        NSDContainer<NSDElement> obj = new NSDContainer<>("foo", Arrays.asList(child0, child1));
        obj.removeChild(child1);

        assertEquals(1, obj.countChildren());
        assertSame(child0, obj.getChild(0));
    }

    @Test
    public void isIterable()
    {
        NSDContainer<NSDElement> obj = new NSDContainer<>("foo", Arrays.asList(child0, child1));
        Iterator<NSDElement> it = obj.iterator();

        assertSame(child0, it.next());
        assertSame(child1, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void isStreamable()
    {
        NSDContainer<NSDElement> obj = new NSDContainer<>("foo", Arrays.asList(child0, child1));

        assertArrayEquals(new NSDElement[] { child0, child1 }, obj.stream().toArray());
    }

    @Test
    public void convertsToContainerRenderPart()
    {
        NSDContainer<NSDElement> obj = new NSDContainer<>("foo", Arrays.asList(child0, child1));
        RenderPart part = obj.toRenderPart();

        assertTrue(part instanceof ContainerRenderPart);
    }
}
