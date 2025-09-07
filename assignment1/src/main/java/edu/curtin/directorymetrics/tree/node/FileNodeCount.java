package edu.curtin.directorymetrics.tree.node;

import java.io.File;

/**
 * Implementation of FileNode leaf template. Format of the matches only include
 * the name of the file and the total number of matches.
 */
public class FileNodeCount extends FileNode
{
    /**
     * Constructor for FileNodeCount.
     *
     * @param file File object that should point to a file. Template class
     *             handles when it is not
     */
    public FileNodeCount(File file)
    {
        super(file);
    }

    /**
     * Formats the line matches of the file for printing, which only has the
     * name of the file and the total number of matches.
     *
     * @param indent The number of whitespace to print before the content.
     * @return formatted string
     */
    @Override
    protected String formatMatches(String indent)
    {
        String out = indent + getFile().getName() + ": " + getMatchesCount()
            + " line";

        if(getMatchesCount() > 1)
        {
            out += "s";
        }

        return out;
    }
}
