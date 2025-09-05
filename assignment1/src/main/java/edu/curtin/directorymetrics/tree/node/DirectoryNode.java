package edu.curtin.directorymetrics.tree.node;

import java.io.File;
import java.util.Queue;

import edu.curtin.directorymetrics.criteria.Criteria;

import java.util.LinkedList;

/**
 * The 'composite' class for Node. This class provides all the necessary methods
 * for creating a 'tree' structure and recursing through it. The subclasses must
 * implement a way to display the directory that 'file' points to.
 */
public abstract class DirectoryNode extends Node
{
    private Queue<Node> directories;
    private Queue<Node> files;

    /**
     * Constructor for DirectoryNode. Must be called by subclass.
     *
     * @param file The directory that is stored
     * @throws IllegalArgumentException The File object imported is not a
     *                                  directory
     */
    public DirectoryNode(File file)
    {
        super(file);

        if(!file.isDirectory())
        {
            throw new IllegalArgumentException(
                "File object is not a directory.");
        }

        this.directories = new LinkedList<>();
        this.files = new LinkedList<>();
    }

    /**
     * Recurses through list of directories and files to find matches using
     * Criteria object.
     *
     * @param c The Criteria object containing list of inclusions and
     *          exclusions.
     * @return The total number of matches found
     */
    @Override
    public int searchMatchesRecurse(Criteria c)
    {
        resetMatchesCount();

        int total = 0;

        // Recurse into directories
        for(Node dir : this.directories)
        {
            total += dir.searchMatchesRecurse(c);
            // setMatchesCount(this.getMatchesCount() + dir.getMatchesCount());
        }

        // Recurse into files
        for(Node file : files)
        {
            total += file.searchMatchesRecurse(c);
            // setMatchesCount(this.getMatchesCount() + file.getMatchesCount());
        }

        // Update totalMatchesCount
        setMatchesCount(total);

        return total;
    }

    /**
     * Recursive function to display the tree of directories and files.
     *
     * @param indent The number of whitespace to print before the content.
     */
    @Override
    protected void displayMatches(String indent)
    {
        if(getMatchesCount() > 0)
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

    /**
     * Formats the name of the directory
     *
     * @param indent The number of whitespace to print before the content.
     * @return formatted string
     */
    protected abstract String formatName(String indent);

    /**
     * Adds a directory to the Node list
     *
     * @param node
     * @throws IllegalArgumentException Node object does not represent a
     *                                  directory.
     */
    public final void addDirectory(Node node)
    {
        if(!node.getFile().isDirectory())
        {
            throw new IllegalArgumentException(
                "Node imported does not represent a directory.");
        }

        this.directories.add(node);
    }

    /**
     * Adds a file to the Node list
     *
     * @param node
     * @throws IllegalArgumentException Node object does not represent a file.
     */
    public final void addFile(Node node)
    {
        if(!node.getFile().isFile())
        {
            throw new IllegalArgumentException(
                "Node imported does not represent a file.");
        }

        this.files.add(node);
    }
}
