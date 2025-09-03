package edu.curtin.directorymetrics.tree.node;

import java.io.File;

import edu.curtin.directorymetrics.criteria.Criteria;
import edu.curtin.directorymetrics.criteria.LineMatch;

public abstract class FileNode extends Node
{
    private LineMatch[] matches;

    public FileNode(File file)
    {
        super(file);

        this.matches = new LineMatch[0];
    }

    protected LineMatch[] getMatches()
    {
        return this.matches;
    }

    @Override
    protected int searchMatchesRecurse(Criteria c)
    {
        // Grab matches using criteria
        this.matches = c.findMatchInFile(getFile());

        // Update matchesCount
        setMatchesCount(this.matches.length);

        return this.matches.length;
    }

    @Override
    public void displayMatches(String indent)
    {
        if(getMatchesCount() > 0)
        {
            System.out.println(formatMatches(indent));
        }
    }

    protected abstract String formatMatches(String indent);
}
