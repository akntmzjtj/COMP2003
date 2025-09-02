package edu.curtin.image;

import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        Random rand = new Random();
        ImageData image = new ImageData(10, 5);
        for(int row = 0; row < image.getHeight(); row++)
        {
            for(int col = 0; col < image.getWidth(); col++)
            {
                image.setPixel(col, row, rand.nextInt(0, 2));
            }
        }

        ImageOperation rotate = new Rotate();
        ImageOperation invert = new Invert();
        ImageOperation scale = new Scale();

        System.out.println(image);
        System.out.println();

        image = rotate.runOperation(image);

        System.out.println(image);
        System.out.println();

        image = rotate.runOperation(image);

        System.out.println(image);
        System.out.println();

        // image = invert.runOperation(image);

        // System.out.println(image);
        // System.out.println();

        image = scale.runOperation(image);

        System.out.println(image);
        System.out.println();
    }
}
