package nsdlib.rendering.renderer;

import nsdlib.rendering.RenderColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class RenderAdapterTest
{
    private static class RenderAdapterMock extends RenderAdapter<Void>
    {
        private boolean drawStringAtCalled;
        private String drawStringAtS;
        private int drawStringAtX, drawStringAtY;

        public RenderAdapterMock(RenderContext ctx)
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
        }

        @Override
        protected void drawStringAt(String s, int x, int y)
        {
            this.drawStringAtCalled = true;
            this.drawStringAtS = s;
            this.drawStringAtX = x;
            this.drawStringAtY = y;
        }
    }

    @Test
    public void drawStringLeftUsesCorrectCoordinates()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5, (s) -> 8);
        RenderAdapterMock obj = new RenderAdapterMock(ctx);

        obj.drawStringLeft("Hello World!", 40, 60);

        assertTrue(obj.drawStringAtCalled);
        assertEquals("Hello World!", obj.drawStringAtS);
        assertEquals(40 /*x*/ + 8 /*padH*/, obj.drawStringAtX);
        assertEquals(60 /*y*/ + 10 /*padV*/ + 8 /*h*/, obj.drawStringAtY);
    }

    @Test
    public void drawStringLeftIgnoresNull()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5, (s) -> 8);
        RenderAdapterMock obj = new RenderAdapterMock(ctx);

        obj.drawStringLeft(null, 40, 60);

        assertFalse(obj.drawStringAtCalled);
    }

    @Test
    public void drawStringCenteredUsesCorrectCoordinates()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5, (s) -> 8);
        RenderAdapterMock obj = new RenderAdapterMock(ctx);

        obj.drawStringCentered("Hello World!", 40, 60);

        assertTrue(obj.drawStringAtCalled);
        assertEquals("Hello World!", obj.drawStringAtS);
        assertEquals(40 /*x*/ - 30 /*h/2*/, obj.drawStringAtX);
        assertEquals(60 /*y*/ + 10 /*padV*/ + 8 /*h*/, obj.drawStringAtY);
    }

    @Test
    public void drawStringCenteredIgnoresNull()
    {
        RenderContext ctx = new RenderContext(8, 10, (s) -> s.length() * 5, (s) -> 8);
        RenderAdapterMock obj = new RenderAdapterMock(ctx);

        obj.drawStringCentered(null, 40, 60);

        assertFalse(obj.drawStringAtCalled);
    }
}
