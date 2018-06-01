package nsdlib.reader;

/**
 * Exception thrown when there is a problem while reading an NS diagram.
 */
public class NSDReaderException extends Exception
{
    private static final long serialVersionUID = 2663938031546601992L;

    /**
     * Constructs an exception with the given detail message.
     *
     * @param message The detail message.
     */
    public NSDReaderException(String message)
    {
        super(message);
    }

    /**
     * Constructs an exception with the given throwable as its cause.
     *
     * @param cause The throwable that caused this exception to be thrown.
     */
    public NSDReaderException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs an exception with the given detail message and the given
     * cause.
     *
     * @param message The detail message.
     * @param cause The throwable that caused this exception to be thrown.
     */
    public NSDReaderException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
