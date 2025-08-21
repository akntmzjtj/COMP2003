package edu.curtin.directorymetrics;

import java.io.File;
import java.util.Queue;
import java.util.LinkedList;

public class DirectoryNode implements Node
{
    private final static String INDENT = "   ";
    private File file;
    private Queue<Node> directories;
    private Queue<Node> files;

    public DirectoryNode(File file)
    {
        this.file = file;
        this.directories = new LinkedList<>();
        this.files = new LinkedList<>();
    }

    @Override
    public void searchMatches(String indent, String pathname, Criteria c, ReportSearch r)
    {
        // Add folder to path
        pathname += String.format("%s%s:\n", indent, this.file.getName());

        // System.out.println(indent + file.getName() + " (" + directories.size()
        //     + "):");

        // Recurse into directories
        for(Node dir : this.directories)
        {
            dir.searchMatches(indent + INDENT, pathname, c, r);
        }

        // Recurse into files
        for(Node file : files)
        {
            file.searchMatches(indent + INDENT, pathname, c, r);
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
