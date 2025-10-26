package edu.curtin.oose.assignment2.probe;

/**
 * Exception for ProbeList
 *
 * @author Joshua Orbon 20636948
 */
public class ProbeListException extends Exception
{
    /**
     * Constructs ProbeList exception using given message.
     *
     * @param msg The message as to why the exception was thrown.
     */
    public ProbeListException(String msg)
    {
        super(msg);
    }

    /**
     * Constructs ProbeList exception using a given message and includes a trail
     *
     * @param msg   The message as to why the exception was thrown.
     * @param cause For exception chaining
     */
    public ProbeListException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
