package edu.curtin.image;

public abstract class ImageOperation
{
    protected abstract int getNewWidth(ImageData oldImage);

    protected abstract int getNewHeight(ImageData oldImage);

    protected abstract void setNewPixel(ImageData newImage, ImageData oldImage, int x, int y);

    public ImageData runOperation(ImageData oldImage)
    {
        // Grab new height
        int newWidth = getNewWidth(oldImage);
        int newHeight = getNewHeight(oldImage);

        ImageData newImage = new ImageData(newWidth, newHeight);

        for(int y = 0; y < newHeight; y++)
        {
            for(int x = 0; x < newWidth; x++)
            {
                setNewPixel(newImage, oldImage, x, y);
            }
        }
        return newImage;
    }
}
