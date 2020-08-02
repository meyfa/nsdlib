package nsdlib.reader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class NSDReaderExceptionTest
{
    @Test
    public void storesMessage()
    {
        NSDReaderException obj = new NSDReaderException("test message");

        assertEquals("test message", obj.getMessage());
    }

    @Test
    public void storesCause()
    {
        RuntimeException cause = new RuntimeException("foo");
        NSDReaderException obj = new NSDReaderException(cause);

        assertSame(cause, obj.getCause());
    }

    @Test
    public void storesMessageAndCause()
    {
        RuntimeException cause = new RuntimeException("foo");
        NSDReaderException obj = new NSDReaderException("test message", cause);

        assertEquals("test message", obj.getMessage());
        assertSame(cause, obj.getCause());
    }
}
