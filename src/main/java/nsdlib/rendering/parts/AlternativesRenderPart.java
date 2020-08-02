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
    private int headingHeight;

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
        this.pathLabels = Collections.unmodifiableList(new ArrayList<>(pathLabels));

        this.content = new ContainerRenderPart(Orientation.HORIZONTAL, pathContents);
    }

    @Override
    public RenderPart findForSource(NSDElement source)
    {
        return source == getSource() ? this : content.findForSource(source);
    }

    @Override
    public void layout(RenderContext ctx)
    {
        content.layout(ctx);

        Size box = ctx.box(label);
        Size contentSize = content.getSize();

        headingHeight = box.height * 2;

        int width = Math.max(box.width * pathLabels.size(), contentSize.width);
        int height = headingHeight + contentSize.height;

        size = new Size(width, height);
    }

    @Override
    public Size getSize()
    {
        return size;
    }

    @Override
    public void render(RenderAdapter<?> adapter, int x, int y, int w)
    {
        adapter.fillRect(x, y, w, headingHeight, getBackground());

        y += drawHeading(adapter, x, y, w);
        content.render(adapter, x, y, w);
    }

    private int drawHeading(RenderAdapter<?> a, int x, int y, int w)
    {
        a.drawRect(x, y, w, headingHeight);

        int triangleHeight = headingHeight / 2;
        int caseWidth = w / pathLabels.size();
        int lastSepX = x + w - caseWidth;

        a.drawLine(x, y, lastSepX, y + triangleHeight);
        a.drawLine(lastSepX, y + triangleHeight, x + w, y);

        a.drawStringCentered(label, lastSepX, y);

        y += triangleHeight;

        // a^2 + b^2 = c^2
        int dx = lastSepX - x, dy = headingHeight;
        double hypotLength = Math.sqrt(dx * dx + dy * dy);

        // tan of angle between x-axis and hypotenuse
        double linkAngleTan = Math.tan(Math.asin(triangleHeight / hypotLength));

        for (int i = 0, n = pathLabels.size(); i < n; ++i) {
            a.drawStringCentered(pathLabels.get(i), x + caseWidth / 2, y);
            x += caseWidth;

            // for all but last case (since it doesn't need vertical separators)
            if (i < n - 1) {
                // calc. amount of pixels that current point is above link end
                int adjacent = (int) Math.abs(linkAngleTan * (x - lastSepX));
                a.drawLine(x, y - adjacent, x, y + triangleHeight);
            }
        }

        return headingHeight;
    }
}
