package edu.curtin.directorymetrics.tree.node;

import java.io.File;
import java.util.Queue;

import edu.curtin.directorymetrics.criteria.Criteria;

import java.util.LinkedList;

public abstract class DirectoryNode extends Node
{
    private Queue<Node> directories;
    private Queue<Node> files;

    public DirectoryNode(File file)
    {
        super(file);

        this.directories = new LinkedList<>();
        this.files = new LinkedList<>();
    }

    @Override
    public void searchMatches(Criteria c)
    {
        // Reset matchesCount
        this.resetMatchesCount();

        // Recurse into directories
        for(Node dir : this.directories)
        {
            dir.searchMatches(c);
            this.matchesCount += dir.matchesCount;
        }

        // Recurse into files
        for(Node file : files)
        {
            file.searchMatches(c);
            this.matchesCount += file.matchesCount;
        }
    }

    @Override
    protected void displayMatches(String indent)
    {
        if(this.matchesCount > 0)
        {
            System.out.println(formatName(indent));

            // Recurse into directories
            for(Node dir : this.directories)
            {
                dir.displayMatches(indent + INDENT);
            }

            // Recurse into files
            for(Node file : files)
            {
                file.displayMatches(indent + INDENT);
            }
        }
    }

    protected abstract String formatName(String indent);

    public final void addDirectory(Node node)
    {
        this.directories.add(node);
    }

    public final void addFile(Node node)
    {
        this.files.add(node);
    }
}
