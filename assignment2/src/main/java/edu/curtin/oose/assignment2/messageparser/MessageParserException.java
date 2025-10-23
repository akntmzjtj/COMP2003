package edu.curtin.oose.assignment2.messageparser;

/**
 * Exception for MessageParser
 *
 * @author Joshua Orbon 20636948
 */
public class MessageParserException extends Exception
{
    /**
     * Constructs MessageParser exception using given message.
     *
     * @param msg The message as to why the exception was thrown.
     */
    public MessageParserException(String msg)
    {
        super(msg);
    }

    /**
     * Constructs MessageParser exception using a given message and includes a trail
     *
     * @param msg The message as to why the exception was thrown.
     * @param cause For exception chaining
     */
    public MessageParserException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
