package edu.curtin.oose.assignment2.diagnostic;

/**
 * Exception for DiagnosticWriter
 *
 * @author Joshua Orbon 20636948
 */
public class DiagnosticWriterException extends Exception
{
    /**
     * Constructs DiagnosticWriter exception using given message.
     *
     * @param msg The message as to why the exception was thrown.
     */
    public DiagnosticWriterException(String msg)
    {
        super(msg);
    }

    /**
     * Constructs DiagnosticWriter exception using a given message and includes
     * a trail
     *
     * @param msg   The message as to why the exception was thrown.
     * @param cause For exception chaining
     */
    public DiagnosticWriterException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
