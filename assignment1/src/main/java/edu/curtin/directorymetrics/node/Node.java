package edu.curtin.directorymetrics.node;

import edu.curtin.directorymetrics.criteria.Criteria;

/**
 * Node interface used to represent both directories and files
 */
public interface Node
{
    /**
     * Function to return the total matches count within a file/directory
     *
     * @return matchesCount
     */
    int getMatchesCount();

    /**
     * Recursively search for matches in files, using a Criteria object to check
     * the matches.
     *
     * @param c Criteria object that manages the list of inclusions and
     *          exclusions to be checked against
     */
    void searchMatches(Criteria c);

    /**
     * Displays the directory by recursing through the tree.
     *
     * @param indent Contains the number of whitespace for indentation
     */
    void displayMatches(String indent);

    /**
     * A function wrapper to run the displayMatches() function recursively.
     */
    default void displayMatches()
    {
        displayMatches("");
    }
}
