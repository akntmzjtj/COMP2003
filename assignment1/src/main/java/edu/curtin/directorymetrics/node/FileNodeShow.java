package edu.curtin.directorymetrics.node;

import java.io.File;

import edu.curtin.directorymetrics.criteria.LineMatch;

public class FileNodeShow extends FileNode
{
    public FileNodeShow(File file)
    {
        super(file);
    }

    @Override
    protected String formatMatches(String indent)
    {
        String out = "";

        // Add filename
        out += indent + this.file.getName() + ":\n";

        // Print matches per line
        for(LineMatch m : this.matches)
        {
            // Add line
            out += indent + INDENT + m.getLineNumber() + " " + m.getContent()
                + "\n";
        }

        return out;
    }
}
