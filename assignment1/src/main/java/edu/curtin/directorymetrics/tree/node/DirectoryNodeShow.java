package edu.curtin.directorymetrics.tree.node;

import java.io.File;

public class DirectoryNodeShow extends DirectoryNode
{
    public DirectoryNodeShow(File file)
    {
        super(file);
    }

    @Override
    public String formatName(String indent)
    {
        return indent + getFile().getName() + ":";
    }
}
