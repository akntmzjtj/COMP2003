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

    private int matchesCount;
    private File file;

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
     * Function to return the total matches count within a file/directory
     *
     * @return matchesCount
     */
    protected final int getMatchesCount()
    {
        return this.matchesCount;
    }

    /**
     * Update the matches count
     *
     * @param count updated matchesCount
     * @throws IllegalArgumentException When count is negative
     */
    protected final void setMatchesCount(int count)
    {
        if(count < 0)
        {
            throw new IllegalArgumentException(
                "Number of matches cannot be negative.");
        }

        this.matchesCount = count;
    }

    /**
     * Resets matchesCount
     */
    protected final void resetMatchesCount()
    {
        this.matchesCount = 0;
    }

    /**
     * Returns File object
     *
     * @return this.file - File object
     */
    protected final File getFile()
    {
        return this.file;
    }

    /**
     * Recursively search for matches in files, using a Criteria object to check
     * the matches.
     *
     * @param c Criteria object that manages the list of inclusions and
     *          exclusions to be checked against
     * @return total number of matches found
     */
    protected abstract int searchMatchesRecurse(Criteria c);

    /**
     * A function wrapper to run searchMatchesRecurse() recursively
     *
     * @param c Criteria object that manages the list of inclusions and
     *          exclusion to be checked against
     */
    public final void searchMatches(Criteria c)
    {
        searchMatchesRecurse(c);
    }

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
