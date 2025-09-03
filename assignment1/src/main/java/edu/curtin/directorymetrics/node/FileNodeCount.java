package edu.curtin.directorymetrics.node;

import java.io.File;

public class FileNodeCount extends FileNode
{
    public FileNodeCount(File file)
    {
        super(file);
    }

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

        // return String.format("%s%s: %d", indent, this.file.getName(),
        // this.matches.length);
    }
}
