package nsdlib.rendering.parts;

import nsdlib.rendering.RenderColor;
import nsdlib.rendering.renderer.RenderAdapter;
import nsdlib.rendering.renderer.RenderContext;


public class MockRenderAdapter extends RenderAdapter<Void>
{
    boolean fillRectCalled;

    public MockRenderAdapter(RenderContext ctx)
    {
        super(ctx);
    }

    @Override
    public Void finish()
    {
        return null;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2)
    {
    }

    @Override
    public void drawRect(int x, int y, int w, int h)
    {
    }

    @Override
    public void fillRect(int x, int y, int w, int h, RenderColor col)
    {
        this.fillRectCalled = true;
    }

    @Override
    protected void drawStringAt(String s, int x, int y)
    {
    }
}
