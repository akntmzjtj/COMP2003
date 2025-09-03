package edu.curtin.directorymetrics.node;

import java.io.File;

public class DirectoryNodeCount extends DirectoryNode
{
    public DirectoryNodeCount(File file)
    {
        super(file);
    }

    @Override
    public String formatName(String indent)
    {
        return indent + getFile().getName() + ": "
            + getMatchesCount();
    }
}
