package edu.curtin.directorymetrics;

import java.io.File;
import java.util.Stack;

public class FileNode implements Node
{
    private final static String INDENT = "   ";
    private File file;

    public FileNode(File file)
    {
        this.file = file;
    }

    @Override
    public void searchMatches(String indent, Stack<String> path, Criteria c, ReportSearch r)
    {
        // Grab matches using criteria
        LineMatch[] matches = c.findMatchInFile(this.file);

        // If file doesn't satisfy criteria, don't print
        if(matches != null && matches.length > 0)
        {
            // Add file to pathname
            path.add(String.format("%s%s:", indent, this.file.getName()));

            // Print path
            // System.out.print(pathname);
            for(String s : path)
            {
                System.out.print(s);
            }

            // Print report
            r.printReport(matches, indent + INDENT);

            // Reset pathname
            path.removeAllElements();
        }
    }
}
