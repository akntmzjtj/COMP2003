package edu.curtin.directorymetrics.tree;

/**
 * Exception for NodeIO
 */
public class NodeIOException extends Exception
{
    /**
     * Constructs NodeIO exception using given message.
     *
     * @param msg The message as to why the exception was thrown.
     */
    public NodeIOException(String msg)
    {
        super(msg);
    }

    /**
     * Constructs NodeIO exception using a given message and includes a trail
     *
     * @param msg The message as to why the exception was thrown.
     * @param cause For exception chaining
     */
    public NodeIOException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
