package edu.curtin.image;

public class Scale extends ImageOperation
{
    protected int getNewWidth(ImageData oldImage)
    {
        return oldImage.getWidth() / 2;
    }

    protected int getNewHeight(ImageData oldImage)
    {
        return oldImage.getHeight() / 2;
    }

    protected void setNewPixel(
        ImageData newImage, ImageData oldImage, int x, int y
    )
    {
        newImage.setPixel(x, y, oldImage.getPixel(x * 2, y * 2));
    }
}
