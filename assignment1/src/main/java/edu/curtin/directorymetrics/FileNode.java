package edu.curtin.directorymetrics;

import java.io.File;
import java.util.List;

public class FileNode implements Node
{
    private final static String INDENT = "   ";
    private File file;
    private int matchesCount;

    public FileNode(File file)
    {
        this.file = file;
        this.matchesCount = 0;
    }

    public int getMatchesCount()
    {
        return this.matchesCount;
    }

    public void setMatchesCount(int matchesCount)
    {
        this.matchesCount = matchesCount;
    }

    @Override
    public void searchMatches(Criteria c)
    {
        // Grab matches using criteria
        LineMatch[] matches = c.findMatchInFile(this.file);

        // Update matchesCount
        this.matchesCount = matches.length;
    }

    @Override
    public void displayMatches(String indent)
    {
        if(this.matchesCount > 0)
        {
            System.out.println(indent + this.file.getName() + ": "
                + this.matchesCount);
        }
    }
}
