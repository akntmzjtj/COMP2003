package edu.curtin.directorymetrics.tree.node;

import java.io.File;

/**
 * Implementation of DirectoryNode composite template. Format of the name
 * involves the total number of matches as well.
 */
public class DirectoryNodeCount extends DirectoryNode
{
    /**
     * Constructor for DirectoryNodeCount.
     *
     * @param file File object that should point to a directory. Template class
     *             handles when it is not
     */
    public DirectoryNodeCount(File file)
    {
        super(file);
    }

    /**
     * Formats the name of the directory. Adds number of total matches at the
     * end.
     *
     * @param indent The number of whitespace to print before the content.
     * @return formatted string
     */
    @Override
    public String formatName(String indent)
    {
        return indent + getFile().getName() + ": " + getMatchesCount();
    }
}
