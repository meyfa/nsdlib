package nsdlib.rendering.parts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import nsdlib.elements.NSDElement;
import nsdlib.rendering.Size;
import nsdlib.rendering.parts.ContainerRenderPart.Orientation;
import nsdlib.rendering.renderer.RenderAdapter;
import nsdlib.rendering.renderer.RenderContext;


/**
 * Render part for Case and Decision elements, drawing the header and the child
 * containers.
 */
public class AlternativesRenderPart extends RenderPart
{
    private final String label;
    private final List<String> pathLabels;
    private final ContainerRenderPart content;
    private Size size;

    /**
     * Constructs a new alternatives part.
     * 
     * @param source This part's source element.
     * @param label The alternative's condition label.
     * @param pathLabels The labels of all possible cases.
     * @param pathContents The child parts.
     */
    public AlternativesRenderPart(NSDElement source, String label,
            Collection<String> pathLabels, Collection<RenderPart> pathContents)
    {
        super(source);

        this.label = label;
        this.pathLabels = Collections
                .unmodifiableList(new ArrayList<>(pathLabels));

        this.content = new ContainerRenderPart(Orientation.HORIZONTAL,
                pathContents);
    }

    @Override
    public RenderPart findForSource(NSDElement source)
    {
        return source == getSource() ? this : content.findForSource(source);
    }

    @Override
    public void layout(RenderContext ctx)
    {
        size = ctx.box(label);
        size.width *= pathLabels.size();
        size.height *= 2;

        content.layout(ctx);

        Size contentSize = content.getSize();
        size.width = Math.max(size.width, contentSize.width);
        size.height += contentSize.height;
    }

    @Override
    public Size getSize()
    {
        return size;
    }

    @Override
    public void render(RenderAdapter<?> adapter, int x, int y, int w)
    {
        int headingHeight = adapter.getContext().box(label).height * 2;
        adapter.fillRect(x, y, w, headingHeight, getBackground());

        y += drawHeading(adapter, x, y, w);
        content.render(adapter, x, y, w);
    }

    private int drawHeading(RenderAdapter<?> a, int x, int y, int w)
    {
        int hHalf = a.getContext().box(label).height;
        int h = hHalf * 2;

        a.drawRect(x, y, w, h);

        int caseWidth = w / pathLabels.size();
        int linkX = x + w - caseWidth;

        a.drawLine(x, y, linkX, y + hHalf);
        a.drawLine(linkX, y + hHalf, x + w, y);

        a.drawStringCentered(label, linkX, y);

        y += hHalf;

        // a^2 + b^2 = c^2
        double hypotLength = Math.sqrt((linkX - x) * (linkX - x) + h * h);
        // tan of angle between x-axis and hypotenuse
        double linkAngleTan = Math.tan(Math.asin(hHalf / hypotLength));

        for (int i = 0, n = pathLabels.size(); i < n; ++i) {

            a.drawStringCentered(pathLabels.get(i), x + caseWidth / 2, y);
            x += caseWidth;

            // for all but last case (since it doesn't need vertical separators)
            if (i < n - 1) {
                // calc. amount of pixels that current point is above link end
                int adjacent = (int) Math.abs(linkAngleTan * (x - linkX));
                a.drawLine(x, y - adjacent, x, y + hHalf);
            }

        }

        return h;
    }
}
