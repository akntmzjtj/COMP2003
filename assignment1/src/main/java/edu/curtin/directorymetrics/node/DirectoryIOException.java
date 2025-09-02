package edu.curtin.directorymetrics.node;

/**
 * Exception for DirectoryIO
 */
public class DirectoryIOException extends Exception
{
    /**
     * Constructs DirectoryIO exception using given message.
     *
     * @param msg The message as to why the exception was thrown.
     */
    public DirectoryIOException(String msg)
    {
        super(msg);
    }

    /**
     * Constructs DirectoryIO exception using a given message and includes a trail
     *
     * @param msg The message as to why the exception was thrown.
     * @param cause For exception chaining
     */
    public DirectoryIOException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
