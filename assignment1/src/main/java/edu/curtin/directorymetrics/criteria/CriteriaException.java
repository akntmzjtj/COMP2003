package edu.curtin.directorymetrics.criteria;

/**
 * Exception for Criteria
 *
 * @author Joshua Orbon 20636948
 */
public class CriteriaException extends Exception
{
    /**
     * Constructs Criteria exception using given message.
     *
     * @param msg The message as to why the exception was thrown.
     */
    public CriteriaException(String msg)
    {
        super(msg);
    }

    /**
     * Constructs Criteria exception using a given message and includes a trail
     *
     * @param msg   The message as to why the exception was thrown.
     * @param cause For exception chaining
     */
    public CriteriaException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
