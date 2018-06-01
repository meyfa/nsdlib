package nsdlib.rendering.parts;

import nsdlib.elements.NSDElement;
import nsdlib.rendering.Size;
import nsdlib.rendering.renderer.RenderAdapter;
import nsdlib.rendering.renderer.RenderContext;


public class MockRenderPart extends RenderPart
{
    Size sizeToUse = new Size(40, 20);

    boolean layoutCalled;

    boolean renderCalled;
    int renderX, renderY, renderW;

    public MockRenderPart()
    {
        super();
    }

    public MockRenderPart(NSDElement source)
    {
        super(source);
    }

    @Override
    public void layout(RenderContext ctx)
    {
        this.layoutCalled = true;
    }

    @Override
    public Size getSize()
    {
        return sizeToUse;
    }

    @Override
    public void render(RenderAdapter<?> adapter, int x, int y, int w)
    {
        this.renderCalled = true;
        this.renderX = x;
        this.renderY = y;
        this.renderW = w;
    }
}
