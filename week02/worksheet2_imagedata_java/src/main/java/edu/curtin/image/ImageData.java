package edu.curtin.image;

/* Don't modify this particular code.
 * (It's not the interesting bit!)
 */
public class ImageData
{
    private int[][] image;
    private int width;
    private int height;

    public ImageData(int width, int height)
    {
        this.width = width;
        this.height = height;

        image = new int[height][width];
    }

    public void setPixel(int x, int y, int value)
    {
        image[y][x] = value;
    }

    public int getPixel(int x, int y)
    {
        return image[y][x];
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public String toString()
    {
        String out = "";
        for(int y = 0; y < this.height - 1; y++)
        {
            for(int x = 0; x < this.width; x++)
            {
                out += image[y][x];
            }
            out += "\n";
        }

        for(int i = 0; i < this.width; i++) {
            out += image[this.height - 1][i];
        }

        return out;
    }
}
