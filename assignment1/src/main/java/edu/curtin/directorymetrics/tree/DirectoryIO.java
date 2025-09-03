package edu.curtin.directorymetrics.tree;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Queue;

import edu.curtin.directorymetrics.tree.node.Node;
import edu.curtin.directorymetrics.tree.node.DirectoryNode;
import edu.curtin.directorymetrics.tree.node.DirectoryNodeShow;
import edu.curtin.directorymetrics.tree.node.DirectoryNodeCount;
import edu.curtin.directorymetrics.tree.node.FileNode;
import edu.curtin.directorymetrics.tree.node.FileNodeShow;
import edu.curtin.directorymetrics.tree.node.FileNodeCount;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * A class used for forming a tree using the Node interface and its composite
 * and leaf implementations.
 */
public class DirectoryIO
{
    /**
     * Creates a tree of Node objects by recursively adding each directory/file
     * as a child of a DirectoryNode object.
     *
     * @param dir     String containing the path to the directory
     * @param isCount Flag for whether storing 'count' or 'show' implementations
     *                of the FileNode and DirectoryNode
     * @return
     * @throws DirectoryIOException When the provided directory does not exist
     *                              or points to a file
     */
    public static Node readDirectory(String dir, boolean isCount)
        throws DirectoryIOException
    {
        // Create File object with directory given
        File rootDir = new File(dir);

        // Throw exception if 'dir' does not exist or is not a directory
        if(!rootDir.exists() || !rootDir.isDirectory())
        {
            throw new DirectoryIOException(
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
