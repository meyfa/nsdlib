package nsdlib.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nsdlib.rendering.parts.ContainerRenderPart;
import nsdlib.rendering.parts.ContainerRenderPart.Orientation;
import nsdlib.rendering.parts.RenderPart;


/**
 * Base class for any element type that can contain child elements (e.g. loops).
 * Can also be instantiated directly if an anonymous container is needed.
 *
 * @param <T> The type of element that may be added to this container. Use {@link NSDElement} to accept any.
 */
public class NSDContainer<T extends NSDElement> extends NSDElement implements Iterable<T>
{
    private final List<T> children;

    /**
     * @param label The element's label.
     */
    public NSDContainer(String label)
    {
        this(label, null);
    }

    /**
     * @param label The element's label.
     * @param children The element's initial child elements.
     */
    public NSDContainer(String label, Collection<? extends T> children)
    {
        super(label);

        this.children = children == null ? new ArrayList<>() : new ArrayList<>(children);
    }

    /**
     * @return The number of elements in this container.
     */
    public int countChildren()
    {
        return children.size();
    }

    /**
     * Retrieves a child element from this container.
     *
     * @param index The index of the child element.
     * @return The child element.
     */
    public T getChild(int index)
    {
        return children.get(index);
    }

    /**
     * Replaces the child element at the given index with the given element.
     *
     * @param index The element position.
     * @param e The new element to insert instead of the current one.
     */
    public void setChild(int index, T e)
    {
        validateChild(e);
        children.set(index, e);
    }

    /**
     * Inserts a child element at the end of this container.
     *
     * @param e The element to insert.
     */
    public void addChild(T e)
    {
        validateChild(e);
        children.add(e);
    }

    /**
     * Inserts a child element at a given index, shifting the current element
     * and any subsequent elements 1 to the right (adding 1 to their indices).
     *
     * @param index The index to insert the element at.
     * @param e The element to insert.
     */
    public void addChild(int index, T e)
    {
        validateChild(e);
        children.add(index, e);
    }

    /**
     * Removes the child element at the given index from this container.
     *
     * @param index The element's index.
     */
    public void removeChild(int index)
    {
        children.remove(index);
    }

    /**
     * Removes the element from this container's list of child elements.
     *
     * @param e The element to remove.
     */
    public void removeChild(T e)
    {
        children.remove(e);
    }

    private void validateChild(NSDElement e)
    {
        if (e == null) {
            throw new IllegalArgumentException("element may not be null");
        }
        if (e == this) {
            throw new IllegalArgumentException("element may not be its own child");
        }
    }

    @Override
    public Iterator<T> iterator()
    {
        return children.iterator();
    }

    /**
     * @return A stream of all child elements in this container.
     */
    public Stream<T> stream()
    {
        return children.stream();
    }

    @Override
    public RenderPart toRenderPart()
    {
        return new ContainerRenderPart(Orientation.VERTICAL, getChildRenderParts());
    }

    /**
     * @return A list of child elements converted to render parts.
     */
    protected List<RenderPart> getChildRenderParts()
    {
        return stream().map(NSDElement::toRenderPart).collect(Collectors.toList());
    }
}
