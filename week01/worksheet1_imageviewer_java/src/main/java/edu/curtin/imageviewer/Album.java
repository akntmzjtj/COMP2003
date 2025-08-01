package edu.curtin.imageviewer;

import java.util.*;

/**
 * Represents an album of images.
 */
public class Album
{
    private ArrayList<ImageRecord> images;
    private int current;

    public Album()
    {
        this.images = new ArrayList<ImageRecord>();
        this.current = 0;
    }

    public void addImage(ImageRecord image)
    {
        this.images.add(image);
    }

    public ImageRecord getCurrentImage()
    {
        return this.images.get(current);
    }

    public ImageRecord getPreviousImage()
    {
        if(this.current == 0)
        {
            // Go to end of album
            this.current = images.size() - 1;
        }
        else
        {
            this.current--;
        }

        return this.images.get(current);
    }

    public ImageRecord getNextImage()
    {
        if(current == images.size() - 1)
        {
            // Go to start of album
            this.current = 0;
        }
        else
        {
            this.current++;
        }

        return this.images.get(current);
    }

}
