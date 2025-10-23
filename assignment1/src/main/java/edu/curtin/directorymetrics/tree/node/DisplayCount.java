package edu.curtin.directorymetrics.tree.node;

public class DisplayCount implements DisplayFormat
{
    public DisplayCount()
    {
    }

    public void printDirectory(DirectoryNode dir, String indent)
    {
        System.out.print(indent + dir.getFile().getName() + ": " + dir
            .getMatchesCount() + " line");
        if(dir.getMatchesCount() > 1)
        {
            System.out.print("s");
        }
        System.out.println();
    }

    public void printFile(FileNode file, String indent)
    {
        System.out.print(indent + file.getFile().getName() + ": " + file
            .getMatchesCount() + " line");
        if(file.getMatchesCount() > 1)
        {
            System.out.print("s");
        }
        System.out.println();
    }
}
