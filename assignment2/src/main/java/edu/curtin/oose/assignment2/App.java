package edu.curtin.oose.assignment2;

/**
 * Entry point into the application. To change the package, and/or the name of this class, make
 * sure to update the 'mainClass = ...' line in build.gradle.
 */
public class App
{
    public static void main(String[] args)
    {
        System.out.println(Hello.getHello());

        CommsGenerator cg = new CommsGenerator(10);

        System.out.println(cg.nextMessage());
    }
}
