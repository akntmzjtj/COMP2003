package edu.curtin.directorymetrics.tree;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Queue;

import edu.curtin.directorymetrics.tree.node.Node;
import edu.curtin.directorymetrics.tree.node.DirectoryNode;
import edu.curtin.directorymetrics.tree.node.FileNode;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * A class used for forming a tree using the Node interface and its composite
 * and leaf implementations.
 *
 * @author Joshua Orbon 20636948
 */
public class NodeIO
{
    /**
     * Creates a tree of Node objects by recursively adding each directory/file
     * as a child of a DirectoryNode object.
     *
     * @param dir     String containing the path to the directory
     * @param isCount Flag for whether storing 'count' or 'show' implementations
     *                of the FileNode and DirectoryNode
     * @return
     * @throws NodeIOException When the provided directory does not exist or
     *                         points to a file
     */
    public static Node readDirectory(String dir) throws NodeIOException
    {
        // Create File object with directory given
        File rootDir = new File(dir);

        // Throw exception if 'dir' does not exist or is not a directory
        if(!rootDir.exists() || !rootDir.isDirectory())
        {
            throw new NodeIOException(
                "Directory provided does not exist or is not a directory.");
        }

        // Temporary map to create directory tree
        Map<String, DirectoryNode> map = new HashMap<>();

        // Add new DirectoryNode obj (depending on bool)
        DirectoryNode rootDirNode = new DirectoryNode(rootDir);
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
