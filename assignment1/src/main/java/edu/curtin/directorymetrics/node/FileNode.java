package edu.curtin.directorymetrics.node;

import java.io.File;

import edu.curtin.directorymetrics.criteria.Criteria;
import edu.curtin.directorymetrics.criteria.LineMatch;

public abstract class FileNode extends Node
{
    protected LineMatch[] matches;

    public FileNode(File file)
    {
        super(file);

        this.matches = new LineMatch[0];
    }

    @Override
    public void searchMatches(Criteria c)
    {
        // Grab matches using criteria
        this.matches = c.findMatchInFile(this.file);

        // Update matchesCount
        this.matchesCount = this.matches.length;
    }

    @Override
    public void displayMatches(String indent)
    {
        if(this.matchesCount > 0)
        {
            System.out.println(formatMatches(indent));
        }
    }

    protected abstract String formatMatches(String indent);
}
