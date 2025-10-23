package edu.curtin.directorymetrics.tree.node;

import java.io.File;

import edu.curtin.directorymetrics.criteria.Criteria;
import edu.curtin.directorymetrics.criteria.LineMatch;

/**
 * The 'leaf' template class for Node. This class stores the files that are
 * matched against the Criteria object. The subclasses must implement how the
 * matches are formatted for display.
 *
 * @author Joshua Orbon 20636948
 */
public class FileNode extends Node
{
    private LineMatch[] matches;

    /**
     * Constructor for FileNode. Must be called by subclass.
     *
     * @param file The directory that is stored
     * @throws IllegalArgumentException The File object imported is not a file
     */
    public FileNode(File file)
    {
        super(file);

        if(!file.isFile())
        {
            throw new IllegalArgumentException("File object is not a file.");
        }

        this.matches = new LineMatch[0];
    }

    /**
     * Get array of LineMatch objects containing lines that satisfy the Criteria
     * object
     *
     * @return LineMatch[]
     */
    protected LineMatch[] getMatches()
    {
        return this.matches;
    }

    /**
     * Finds the total number of matches in a file using Criteria object and
     * returns the total.
     *
     * @param c Criteria object containing list of inclusions and exclusions
     * @return total number of matches
     */
    @Override
    protected int searchMatchesRecurse(Criteria c)
    {
        // Grab matches using criteria
        this.matches = c.findMatchInFile(getFile());

        // Update matchesCount
        setMatchesCount(this.matches.length);

        return this.matches.length;
    }

    /**
     * Displays the matches in the file
     *
     * @param indent The number of whitespace to print before the content.
     */
    @Override
    protected void displayMatches(DisplayFormat display, String indent)
    {
        if(getMatchesCount() > 0)
        {
            display.printFile(this, indent);
        }
    }
}
