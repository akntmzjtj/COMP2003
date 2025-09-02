package edu.curtin.image;

public class Rotate extends ImageOperation
{
    @Override
    protected int getNewWidth(ImageData oldImage)
    {
        return oldImage.getHeight();
    }

    @Override
    protected int getNewHeight(ImageData oldImage)
    {
        return oldImage.getWidth();
    }

    @Override
    protected void setNewPixel(
        ImageData newImage, ImageData oldImage, int x, int y
    )
    {
        int newPixel = oldImage.getPixel(newImage.getHeight() - 1 - y, x);
        newImage.setPixel(x, y, newPixel);
    }
}
