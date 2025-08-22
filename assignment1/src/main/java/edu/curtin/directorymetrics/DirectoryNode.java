package edu.curtin.directorymetrics;

import java.io.File;
import java.util.Queue;
import java.util.Stack;
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
    public void searchMatches(String indent, Stack<String> path, Criteria c, ReportSearch r)
    {
        String currentPath = String.format("%s%s:\n", indent, this.file.getName());

        // Add folder to path
        path.push(currentPath);

        // System.out.println(indent + file.getName() + " (" + directories.size()
        //     + "):");

        // Recurse into directories
        for(Node dir : this.directories)
        {
            dir.searchMatches(indent + INDENT, path, c, r);
        }

        // Recurse into files
        for(Node file : files)
        {
            file.searchMatches(indent + INDENT, path, c, r);
        }

        // If recursion has not found any matches, remove directory from path
        if(!path.isEmpty() && path.peek().equals(currentPath))
        {
            path.pop();
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
