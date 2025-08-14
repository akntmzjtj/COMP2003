package edu.curtin.productfinder;

/**
 * An exception thrown when the catalogue file format is discovered to be incorrect.
 */
public class CatalogueFormatException extends Exception
{
    public CatalogueFormatException(String msg)
    {
        super(msg);
    }

    public CatalogueFormatException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
