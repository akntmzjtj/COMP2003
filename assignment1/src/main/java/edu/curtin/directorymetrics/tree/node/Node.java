package edu.curtin.directorymetrics.tree.node;

import java.io.File;

import edu.curtin.directorymetrics.criteria.Criteria;

/**
 * Node is an abstract class used to represent both directories and files. A
 * subclass that represents the directories is implemented to allow for a tree
 * to be formed. The abstract methods must be defined to recurse through the
 * tree.
 */
public abstract class Node
{
    protected final static String INDENT = "   ";

    protected int matchesCount;
    protected File file;

    /**
     * Constructor for the subclasses of Node
     *
     * @param file File object containing either a directory or file
     */
    public Node(File file)
    {
        this.file = file;
        this.matchesCount = 0;
    }

    /**
     * Function to reset the matchesCount before search
     */
    protected final void resetMatchesCount()
    {
        this.matchesCount = 0;
    }

    /**
     * Recursively search for matches in files, using a Criteria object to check
     * the matches.
     *
     * @param c Criteria object that manages the list of inclusions and
     *          exclusions to be checked against
     */
    public abstract void searchMatches(Criteria c);

    /**
     * Displays the directory by recursing through the tree.
     *
     * @param indent Contains the number of whitespace for indentation
     */
    protected abstract void displayMatches(String indent);

    /**
     * A function wrapper to run the displayMatches() function recursively.
     */
    public final void displayMatches()
    {
        displayMatches("");
    }
}
