package edu.curtin.directorymetrics.tree.node;

import java.io.File;

/**
 * Implementation of DirectoryNode composite template. Format of the name only
 * involves the name of the 'file'.
 */
public class DirectoryNodeShow extends DirectoryNode
{
    /**
     * Constructor for DirectoryNodeShow.
     *
     * @param file File object that should point to a directory. Template class
     *             handles when it is not
     */
    public DirectoryNodeShow(File file)
    {
        super(file);
    }

    /**
     * Formats the name of the directory. Only shows the name of the directory.
     *
     * @param indent The number of whitespace to print before the content.
     * @return formatted string
     */
    @Override
    public String formatName(String indent)
    {
        return indent + getFile().getName() + ":";
    }
}
