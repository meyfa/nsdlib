package nsdlib.reader;

/**
 * Exception thrown when there is a problem while reading an NS diagram.
 */
public class NSDReaderException extends Exception
{
    private static final long serialVersionUID = 1068601401868877009L;

    /**
     * Constructs an empty exception.
     */
    public NSDReaderException()
    {
    }

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

    /**
     * Constructs an exception with the given detail message, the given cause, a
     * flag for whether suppression is enabled, and a flag for whether the stack
     * trace shall be writable.
     *
     * @param message The detail message.
     * @param cause The throwable that caused this exception to be thrown.
     * @param enableSuppression Whether suppression shall be enabled.
     * @param writableStackTrace Whether the stack trace shall be writable.
     */
    public NSDReaderException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
