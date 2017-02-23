package nsdlib.reader;

import java.io.InputStream;

import nsdlib.elements.NSDRoot;


/**
 * Interface for NS diagram readers. Offers one method that accepts an input
 * stream and returns the structogram read.
 */
public interface NSDReader
{
    /**
     * Reads a structogram from the given input stream and returns it.
     * 
     * @param in The stream to read from.
     * @return The structogram that was read.
     * 
     * @throws NSDReaderException If there is an error.
     */
    NSDRoot read(InputStream in) throws NSDReaderException;
}
