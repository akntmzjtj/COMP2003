package edu.curtin.directorymetrics.tree.node;

import java.io.File;

import edu.curtin.directorymetrics.criteria.LineMatch;

/**
 * Implementation of FileNode leaf template. Format of the matches include the
 * name of the file and each line match.
 */
public class FileNodeShow extends FileNode
{
    /**
     * Constructor for FileNodeShow.
     *
     * @param file File object that should point to a file. Template class
     *             handles when it is not
     */
    public FileNodeShow(File file)
    {
        super(file);
    }

    /**
     * Formats the line matches of the file for printing, which has the name of
     * the file and the each line match.
     *
     * @param indent The number of whitespace to print before the content.
     * @return formatted string
     */
    @Override
    protected String formatMatches(String indent)
    {
        String out = "";

        // Add filename
        out += indent + getFile().getName() + ":\n";

        // Print matches per line
        for(LineMatch m : getMatches())
        {
            // Add line
            out += indent + INDENT + m.getLineNumber() + " " + m.getContent()
                + "\n";
        }

        return out;
    }
}
