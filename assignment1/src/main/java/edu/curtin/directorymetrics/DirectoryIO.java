package edu.curtin.directorymetrics;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Queue;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class DirectoryIO
{
    public Node readDirectory(String dir, Scanner debug)
        throws IllegalArgumentException
    {
        // Create File object with directory given
        File rootDir = new File(dir);

        // Throw exception if 'dir' does not exist or is not a directory
        if(!rootDir.exists() || !rootDir.isDirectory())
        {
            throw new IllegalArgumentException(
                "Directory provided does not exist or is not a directory.");
        }

        // Temporary map to create directory tree and add first entry
        Map<String, DirectoryNode> map = new HashMap<>();
        map.put(rootDir.getPath(), new DirectoryNode(rootDir));

        // Queue for directories to recurse into
        Queue<File> queue = new LinkedList<>();
        queue.add(rootDir);

        while(!queue.isEmpty())
        {
            File temp = queue.poll();
            File[] tempFiles = temp.listFiles();
            Arrays.sort(tempFiles);

            for(File file : tempFiles)
            {
                // If it's a directory,
                if(file.isDirectory())
                {
                    // Store in queue
                    queue.add(file);

                    // Add to parent directory
                    DirectoryNode dirNode = new DirectoryNode(file);
                    map.get(temp.getPath()).addDirectory(dirNode);

                    // Add to temporary map
                    map.put(file.getPath(), dirNode);
                }
                else
                {
                    // Add to parent directory as a file
                    FileNode fileNode = new FileNode(file);
                    map.get(temp.getPath()).addFile(fileNode);
                }
            }
        }

        // Return object
        return map.get(rootDir.getPath());
    }
}
