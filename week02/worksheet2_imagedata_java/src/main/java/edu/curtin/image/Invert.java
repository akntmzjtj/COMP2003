package edu.curtin.image;

public class Invert extends ImageOperation
{
    protected int getNewWidth(ImageData oldImage)
    {
        return oldImage.getWidth();
    }

    protected int getNewHeight(ImageData oldImage)
    {
        return oldImage.getHeight();
    }

    protected void setNewPixel(
        ImageData newImage, ImageData oldImage, int x, int y
    )
    {
        newImage.setPixel(x, y, ~oldImage.getPixel(x, y));
    }
}
