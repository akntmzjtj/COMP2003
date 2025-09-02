package edu.curtin.directorymetrics.criteria;

/**
 * A class used to store the line number and the contents of the line from a
 * file.
 */
public class LineMatch
{
    private final int lineNumber;
    private final String content;

    /**
     * Constructor for LineMatch class
     *
     * @param lineNumber
     * @param content
     */
    public LineMatch(int lineNumber, String content)
    {
        // Should not be changed after instantiation
        this.lineNumber = lineNumber;
        this.content = content;
    }

    /**
     * Getter for lineNumber
     * @return lineNumber
     */
    public int getLineNumber()
    {
        return lineNumber;
    }

    /**
     * Getter for content of line
     * @return content
     */
    public String getContent()
    {
        return content;
    }
}
