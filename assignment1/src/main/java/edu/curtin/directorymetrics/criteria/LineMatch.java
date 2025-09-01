package edu.curtin.directorymetrics.criteria;

public class LineMatch
{
    private final int lineNumber;
    private final String content;

    public LineMatch(int lineNumber, String content)
    {
        // Should not be changed after instantiation
        this.lineNumber = lineNumber;
        this.content = content;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public String getContent()
    {
        return content;
    }
}
