package edu.curtin.directorymetrics.tree.node;

public interface DisplayFormat
{
    void printDirectory(DirectoryNode dir, String indent);
    void printFile(FileNode file, String indent);
}
