package edu.curtin.directorymetrics.node;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Queue;
import java.util.HashMap;
import java.util.LinkedList;

public class DirectoryIO
{
    public Node readDirectory(String dir, boolean isCount)
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

        // Temporary map to create directory tree
        Map<String, DirectoryNode> map = new HashMap<>();

        // Add new DirectoryNode obj (depending on bool)
        DirectoryNode rootDirNode;
        if(isCount)
        {
            rootDirNode = new DirectoryNodeCount(rootDir);
        }
        else
        {
            rootDirNode = new DirectoryNodeShow(rootDir);
        }
        map.put(rootDir.getPath(), rootDirNode);

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
                    DirectoryNode dirNode;
                    if(isCount)
                    {
                        dirNode = new DirectoryNodeCount(file);
                    }
                    else
                    {
                        dirNode = new DirectoryNodeShow(file);
                    }
                    map.get(temp.getPath()).addDirectory(dirNode);

                    // Add to temporary map
                    map.put(file.getPath(), dirNode);
                }
                else
                {
                    // Add to parent directory as a file
                    FileNode fileNode;
                    if(isCount)
                    {
                        fileNode = new FileNodeCount(file);
                    }
                    else
                    {
                        fileNode = new FileNodeShow(file);
                    }
                    map.get(temp.getPath()).addFile(fileNode);
                }
            }
        }

        // Return object
        return map.get(rootDir.getPath());
    }
}
