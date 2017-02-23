package nsdlib.elements.alternatives;

import java.util.Collection;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;


/**
 * Represents a decision element, i.e. an if-then-else construct. The else
 * branch is optional.
 */
public class NSDDecision extends NSDElement
{
    private final NSDContainer<NSDElement> then, otherwise;

    /**
     * @param label The element's label.
     */
    public NSDDecision(String label)
    {
        this(label, null, null);
    }

    /**
     * @param label The element's label.
     * @param then The child elements for the "then" branch.
     */
    public NSDDecision(String label, Collection<? extends NSDElement> then)
    {
        this(label, then, null);
    }

    /**
     * @param label The element's label.
     * @param then The child elements for the "then" branch.
     * @param otherwise The child elements for the "else" branch.
     */
    public NSDDecision(String label, Collection<? extends NSDElement> then,
            Collection<? extends NSDElement> otherwise)
    {
        super(label);

        this.then = new NSDContainer<>("T", then);
        this.otherwise = new NSDContainer<>("F", otherwise);
    }

    /**
     * @return The child elements for the "then" branch of this decision.
     */
    public NSDContainer<NSDElement> getThen()
    {
        return then;
    }

    /**
     * @return The child elements for the "else" branch of this decision.
     */
    public NSDContainer<NSDElement> getElse()
    {
        return otherwise;
    }
}
