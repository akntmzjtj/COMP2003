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
    public void display(String indent)
    {
        System.out.println(indent + file.getName() + " (" + directories.size()
            + "):");

        // Recurse into directories
        for(Node dir : this.directories)
        {
            dir.display(indent + INDENT);
        }

        // Recurse into files
        for(Node file : files)
        {
            file.display(indent + INDENT);
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
