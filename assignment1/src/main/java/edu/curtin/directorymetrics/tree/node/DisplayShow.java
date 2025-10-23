package edu.curtin.directorymetrics.tree.node;

import edu.curtin.directorymetrics.criteria.LineMatch;

public class DisplayShow implements DisplayFormat
{
    public DisplayShow()
    {
    }

    public void printDirectory(DirectoryNode dir, String indent)
    {
        System.out.println(indent + dir.getFile().getName() + ":");
    }

    public void printFile(FileNode file, String indent)
    {
        System.out.println(indent + file.getFile().getName() + ":");
        for(LineMatch m : file.getMatches())
        {
            // Add line
            System.out.println(indent + Node.INDENT + m.getLineNumber() + " "
                + m.getContent());
        }
    }
}
