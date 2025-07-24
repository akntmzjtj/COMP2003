package edu.curtin.stopwatch;

import java.io.*;
import java.util.*;

/**
 * Displays a stopwatch at the terminal.
 */
public class StopWatch 
{
    public static void main(String[] args)
    {
        System.out.println();
        System.out.println("OOSE Worksheet 0: 'Stopwatch' demo app, console version: successfully running!");
        System.out.println("(This is the console version. If you're trying to test JavaFX, then you must download and run the JavaFX version.)");
        System.out.println();
        try
        {
            @SuppressWarnings("PMD.CloseResource") // No need to close standard input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Press enter to begin stopwatch");
            br.readLine();
            long startTime = System.currentTimeMillis();
            
            System.out.println("Press enter to end stopwatch");
            while(!br.ready())
            {
                long time = System.currentTimeMillis() - startTime;
                long mins = time / 60000;
                long secs = (time / 1000) % 60;
                long millis = time % 1000;
                
                System.out.printf("\r%d:%02d.%03d", mins, secs, millis);
                
                try
                {
                    Thread.sleep(1);
                }
                catch(InterruptedException e)
                {
                    throw new AssertionError(e); // Ought to be impossible.
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Error in standard input: " + e.getMessage());
        }
    }
}
