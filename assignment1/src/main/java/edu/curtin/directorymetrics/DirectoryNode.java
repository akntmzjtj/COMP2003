package edu.curtin.directorymetrics;

import java.io.File;
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;

public class DirectoryNode implements Node
{
    private final static String INDENT = "   ";
    private File file;
    private Queue<Node> directories;
    private Queue<Node> files;
    private int matchesCount;

    public DirectoryNode(File file)
    {
        this.file = file;
        this.directories = new LinkedList<>();
        this.files = new LinkedList<>();
        this.matchesCount = 0;
    }

    public int getMatchesCount()
    {
        return this.matchesCount;
    }

    @Override
    public void searchMatches(Criteria c)
    {
        // Recurse into directories
        for(Node dir : this.directories)
        {
            dir.searchMatches(c);
            this.matchesCount += dir.getMatchesCount();
        }

        // Recurse into files
        for(Node file : files)
        {
            file.searchMatches(c);
            this.matchesCount += file.getMatchesCount();
        }
    }

    @Override
    public void displayMatches(String indent)
    {
        if(this.matchesCount > 0)
        {
            System.out.println(indent + this.file.getName() + ": "
                + this.matchesCount);

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

    public void addDirectory(Node node)
    {
        this.directories.add(node);
    }

    public void addFile(Node node)
    {
        this.files.add(node);
    }
}
