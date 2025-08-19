package edu.curtin.directorymetrics;

import java.io.File;

public class FileNode implements Node
{
    // private final static String INDENT = "   ";
    private File file;

    public FileNode(File file)
    {
        this.file = file;
    }

    @Override
    public void display(String indent) {
        // System.out.println(indent + file.getName() + ":");
        System.out.println(indent + file.getName());

        // Display contents of file that matches regex
    }
}
